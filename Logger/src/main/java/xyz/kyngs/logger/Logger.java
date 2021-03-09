package xyz.kyngs.logger;

import java.io.PrintStream;
import java.time.LocalTime;

import static xyz.kyngs.logger.LogLevel.*;

public interface Logger {

    PrintStream OUT = System.out, ERR = System.err;

    default void info(Object object, Throwable... throwables) {
        log(object, INFO, 2, Thread.currentThread(), LocalTime.now(), throwables);
    }

    default void warn(Object object, Throwable... throwables) {
        log(object, WARN, 2, Thread.currentThread(), LocalTime.now(), throwables);
    }

    default void error(Object object, Throwable... throwables) {
        log(object, ERROR, 2, Thread.currentThread(), LocalTime.now(), throwables);
    }

    default void debug(Object object, Throwable... throwables) {
        log(object, DEBUG, 2, Thread.currentThread(), LocalTime.now(), throwables);
    }

    default void log(Object object, LogLevel debug, int i, Thread currentThread, LocalTime now, Throwable... throwables) {
        log(object, debug, new Throwable(), i, currentThread, now, throwables);
    }

    void log(Object object, LogLevel level, Throwable stackGen, int stacktraceOffset, Thread thread, LocalTime time, Throwable... throwables);

    void destroy();

}
