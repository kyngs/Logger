package cz.kyngs.logger;

import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncLogger extends SyncLogger {

    private final Executor executor;

    protected AsyncLogger(boolean debug, PrintStream originalErr, PrintStream originalOut, @Nullable Consumer<String> onCommandReceived, int threadCount) {
        super(debug, originalErr, originalOut, onCommandReceived);
        executor = Executors.newFixedThreadPool(threadCount, r -> new Thread(r, "Async Logger Thread"));
    }

    @Override
    protected void write(String message, Level level, int reflectionLevel, Throwable... throwables) {
        executor.execute(() -> super.write(message, level, reflectionLevel, throwables));
    }
}
