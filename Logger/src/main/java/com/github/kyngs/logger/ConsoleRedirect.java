package com.github.kyngs.logger;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicReference;

public class ConsoleRedirect extends OutputStream {

    private final Logger logger;
    private final LogLevel level;
    private final AtomicReference<StringBuilder> line;

    public ConsoleRedirect(Logger logger, LogLevel level) {
        this.logger = logger;
        this.level = level;
        line = new AtomicReference<>(new StringBuilder());
    }

    @Override
    public void write(int b) throws IOException {
        char c = (char) b;
        if (b == '\n') {
            synchronized (line) {
                logger.log(line.get(), level, 12, Thread.currentThread(), LocalTime.now());
                line.set(new StringBuilder());
            }
        } else {
            synchronized (line) {
                line.get().append(c);
            }
        }
    }
}
