package cz.kyngs.logger;

import cz.kyngs.logger.utils.ThreadFactoryBuilder;
import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;
import java.time.LocalTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncLogger extends SyncLogger {

    private final Executor executor;

    protected AsyncLogger(boolean debug, PrintStream originalErr, PrintStream originalOut, @Nullable Consumer<String> onCommandReceived, int threadCount) {
        super(debug, originalErr, originalOut, onCommandReceived);
        executor = Executors.newFixedThreadPool(threadCount, new ThreadFactoryBuilder().setNameFormat("Async Logger Thread #%d").build());
    }

    @Override
    protected void write(String message, Level level, int reflectionLevel, Throwable... throwables) {
        Thread thread = Thread.currentThread();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        LocalTime time = LocalTime.now();
        executor.execute(() -> super.write(message, level, reflectionLevel, stackTrace, time, thread, throwables));
    }
}
