package xyz.kyngs.logger;

import org.fusesource.jansi.Ansi;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncLogger extends SyncLogger {

    private final ExecutorService service;

    protected AsyncLogger(boolean allowAnsi, String pattern, Map<Character, Ansi.Color> colorMap, Map<String, LogVar> logVars, Map<LogLevel, Ansi.Color> changeColorOnLevel) {
        super(allowAnsi, pattern, colorMap, logVars, changeColorOnLevel);
        service = Executors.newSingleThreadExecutor();
    }

    @Override
    public void log(Object object, LogLevel level, Throwable stackGen, int stacktraceOffset, Thread thread, LocalTime time, Throwable... throwables) {
        service.submit(() -> super.log(object, level, stackGen, stacktraceOffset, thread, time, throwables));
    }

    @Override
    public void destroy() {
        super.destroy();
        service.shutdown();
        try {
            //noinspection ResultOfMethodCallIgnored
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException ignored) {
        }

    }
}
