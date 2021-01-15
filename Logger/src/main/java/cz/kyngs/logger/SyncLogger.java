/*
 * MIT License
 *
 * Copyright (c) 2020 kyngs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cz.kyngs.logger;

import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author kyngs
 */
public class SyncLogger implements Logger {

    private final DateTimeFormatter formatter;
    private final PrintStream originalErr;
    private final PrintStream originalOut;
    private final boolean debug;
    private final Set<Consumer<String>> inputListeners;

    protected SyncLogger(boolean debug, PrintStream originalErr, PrintStream originalOut, @Nullable Consumer<String> onCommandReceived) {
        this.originalErr = originalErr;
        this.originalOut = originalOut;
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
        inputListeners = new HashSet<>();
        System.setErr(new PrintStream(new ConsoleStream(Level.ERROR, true, this)));
        System.setOut(new PrintStream(new ConsoleStream(Level.INFO, false, this)));
        this.debug = debug;
        if (onCommandReceived != null) addInputListener(onCommandReceived);
        new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String in = scanner.nextLine();
                synchronized (inputListeners) {
                    for (Consumer<String> onCommandReceivedListener : inputListeners) {
                        onCommandReceivedListener.accept(in);
                    }
                }
            }

        }, "Console Reader Thread").start();
    }

    /**
     * @param message    Message
     * @param throwables Throwables
     */
    @Override
    public void info(String message, Throwable... throwables) {
        write(message, Level.INFO, throwables);
    }

    /**
     * @param message    Debug message
     * @param throwables Throwables
     */
    @Override
    public void debug(String message, Throwable... throwables) {
        if (debug) write(message, Level.DEBUG, throwables);
    }

    @Override
    public void addInputListener(Consumer<String> listener) {
        synchronized (inputListeners) {
            inputListeners.add(listener);
        }
    }

    /**
     * @param message    warning message
     * @param throwables Throwables
     */
    @Override
    public void warn(String message, Throwable... throwables) {
        write(message, Level.WARN, throwables);
    }

    /**
     * @param message    Error message
     * @param throwables Throwables
     */
    @Override
    public void error(String message, Throwable... throwables) {
        write(message, Level.ERROR, throwables);
    }

    /**
     * @param message    Message
     * @param level      Level
     * @param throwables throwables
     */
    private void write(String message, Level level, Throwable... throwables) {
        write(message, level, 3, throwables);
    }

    /**
     * @param message         Message
     * @param level           Level
     * @param reflectionLevel StackTrace level
     * @param throwables      Throwables
     */
    protected void write(String message, Level level, int reflectionLevel, StackTraceElement[] stackTrace, Thread thread, Throwable... throwables) {
        if (reflectionLevel != -1) {
            StackTraceElement pos = stackTrace[reflectionLevel];
            message = String.format("[%s] [%s] [%s/%s] [%s]: %s\n", LocalDateTime.now().format(formatter), thread.getName(), pos.getClassName().split("\\.")[pos.getClassName().split("\\.").length - 1], pos.getMethodName(), level.name(), message);
        } else {
            message = String.format("[%s] [%s] [%s]: %s\n", LocalDateTime.now().format(formatter), thread.getName(), level.name(), message);
        }
        if (level == Level.ERROR) {
            originalErr.print(message);
        } else {
            originalOut.print(message);
        }

        for (Throwable throwable : throwables) {
            if (throwable == null || throwable.getMessage() == null) continue;
            write(String.format("%s: %s", throwable.getClass().getName(), throwable.getMessage()), level, reflectionLevel + 1);
            for (StackTraceElement trace : throwable.getStackTrace()) {

                write(String.format("\t%s", trace.toString()), level, reflectionLevel + 1);

            }
            write(message, level, reflectionLevel + 1, throwable.getCause());
        }

    }

    protected void write(String message, Level leve, int reflectionLevel, Throwable... throwables) {
        write(message, leve, reflectionLevel, new Throwable().getStackTrace(), Thread.currentThread(), throwables);
    }


}

