package com.github.kyngs.logger;

import com.github.kyngs.logger.util.MapUtil;
import com.github.kyngs.logger.util.PropertyMap;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;
import java.time.LocalTime;
import java.util.Map;

import static org.fusesource.jansi.Ansi.Color.DEFAULT;

public class SyncLogger implements Logger {

    private final boolean ansiInstalled;
    private final PrintStream out, err;
    private final Map<Character, Ansi.Color> colorMap;
    private final Map<String, LogVar> logVars;
    private final Map<LogLevel, Ansi.Color> changeColorOnLevel;
    private final String pattern;

    protected SyncLogger(boolean allowAnsi, String pattern, Map<Character, Ansi.Color> colorMap, Map<String, LogVar> logVars, Map<LogLevel, Ansi.Color> changeColorOnLevel) {

        this.pattern = pattern;
        this.colorMap = colorMap;
        this.logVars = logVars;
        this.changeColorOnLevel = changeColorOnLevel;

        boolean ansiInstalledImpl;

        out = OUT;
        err = ERR;

        if (allowAnsi && System.console() != null) {

            try {
                AnsiConsole.systemInstall();
                ansiInstalledImpl = true;
            } catch (Throwable e) {
                ansiInstalledImpl = false;
            }

        } else {
            ansiInstalledImpl = false;
        }

        ansiInstalled = ansiInstalledImpl;
    }

    @Override
    public void log(Object object, LogLevel level, Throwable stackGen, int stacktraceOffset, Thread thread, LocalTime time, Throwable... throwables) {

        String pattern = this.pattern;

        PropertyMap propertyMap = new PropertyMap();

        propertyMap.put("level", level);
        propertyMap.put("thread", thread);
        propertyMap.put("time", time);
        propertyMap.put("stack_gen", stackGen);
        propertyMap.put("stack_offset", stacktraceOffset);

        for (Map.Entry<String, LogVar> entry : logVars.entrySet()) {
            pattern = pattern.replace(entry.getKey(), entry.getValue().provide(propertyMap));
        }

        Ansi.Color levelColor = changeColorOnLevel.get(level);

        if (levelColor != null) {
            pattern = "§" + MapUtil.getKeyByValue(levelColor, colorMap) + pattern;
        }

        String message = pattern + object.toString();

        if (ansiInstalled) {
            Ansi ansi = Ansi.ansi();

            boolean lastCharKey = false;
            for (char c : message.toCharArray()) {
                if (lastCharKey) {
                    if (c != '§') {
                        Ansi.Color color = colorMap.get(c);
                        if (color != null) ansi.fg(color);
                        lastCharKey = false;
                        continue;
                    }
                    lastCharKey = false;
                } else {
                    if (c == '§') {
                        lastCharKey = true;
                        continue;
                    }
                }
                ansi.a(c);
            }
            ansi.fg(DEFAULT);
            message = ansi.toString();
        } else {
            StringBuilder builder = new StringBuilder();
            boolean lastCharKey = false;
            for (char c : message.toCharArray()) {
                if (lastCharKey) {
                    if (c != '§') {
                        lastCharKey = false;
                        continue;
                    }
                    lastCharKey = false;
                } else {
                    if (c == '§') {
                        lastCharKey = true;
                        continue;
                    }
                }
                builder.append(c);
            }
            message = builder.toString();
        }

        if (level == LogLevel.ERROR) {
            err.println(message);
        } else {
            out.println(message);
        }

    }

    @Override
    public void destroy() {

    }
}
