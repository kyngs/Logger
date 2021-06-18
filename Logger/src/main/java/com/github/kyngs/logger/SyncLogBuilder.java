package com.github.kyngs.logger;

import com.github.kyngs.logger.vars.*;
import org.fusesource.jansi.Ansi;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.github.kyngs.logger.LogLevel.ERROR;
import static com.github.kyngs.logger.LogLevel.WARN;
import static com.github.kyngs.logger.util.StackTraceInfo.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class SyncLogBuilder {

    private static final Map<Character, Ansi.Color> COLOR_MAP;
    private static final Map<String, LogVar> LOG_VARS;
    private static final String PATTERN;
    private static final boolean ALLOW_ANSI;
    private static final Map<LogLevel, Ansi.Color> CHANGE_COLOR_ON_LEVEL;

    static {
        COLOR_MAP = new HashMap<>();

        COLOR_MAP.put('0', BLACK);
        COLOR_MAP.put('c', RED);
        COLOR_MAP.put('a', GREEN);
        COLOR_MAP.put('e', YELLOW);
        COLOR_MAP.put('1', BLUE);
        COLOR_MAP.put('5', MAGENTA);
        COLOR_MAP.put('b', CYAN);
        COLOR_MAP.put('f', WHITE);
        COLOR_MAP.put('r', DEFAULT);

        LOG_VARS = new HashMap<>();

        LOG_VARS.put("%date%", new DateVar(DateTimeFormatter.ofPattern("dd:MM:yy")));
        LOG_VARS.put("%level%", new LevelVar());
        LOG_VARS.put("%line%", new StackTraceVar(LINE));
        LOG_VARS.put("%method%", new StackTraceVar(METHOD));
        LOG_VARS.put("%class%", new StackTraceVar(CLASS));
        LOG_VARS.put("%thread%", new ThreadVar());
        LOG_VARS.put("%time%", new TimeVar(DateTimeFormatter.ofPattern("HH:mm:ss:SSS")));

        CHANGE_COLOR_ON_LEVEL = new HashMap<>();

        CHANGE_COLOR_ON_LEVEL.put(WARN, YELLOW);
        CHANGE_COLOR_ON_LEVEL.put(ERROR, RED);

        ALLOW_ANSI = true;

        PATTERN = "[%date%] [%time%] [%thread%] [%class%/%method%:%line%] [%level%] ";

    }

    protected Map<Character, Ansi.Color> colorMap;
    protected Map<String, LogVar> logVars;
    protected String pattern;
    protected boolean allowAnsi;
    protected Map<LogLevel, Ansi.Color> changeColorOnLevel;

    protected SyncLogBuilder() {
        colorMap = new HashMap<>(COLOR_MAP);
        logVars = new HashMap<>(LOG_VARS);
        pattern = PATTERN;
        allowAnsi = ALLOW_ANSI;
        changeColorOnLevel = CHANGE_COLOR_ON_LEVEL;
    }

    public Map<Character, Ansi.Color> getColorMap() {
        return colorMap;
    }

    public SyncLogBuilder setColorMap(Map<Character, Ansi.Color> colorMap) {
        this.colorMap = colorMap;
        return this;
    }

    public Map<String, LogVar> getLogVars() {
        return logVars;
    }

    public SyncLogBuilder setLogVars(Map<String, LogVar> logVars) {
        this.logVars = logVars;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public SyncLogBuilder setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public boolean allowAnsi() {
        return allowAnsi;
    }

    public SyncLogBuilder setAllowAnsi(boolean allowAnsi) {
        this.allowAnsi = allowAnsi;
        return this;
    }

    public Logger build() {
        return new SyncLogger(allowAnsi, pattern, colorMap, logVars, changeColorOnLevel);
    }

}
