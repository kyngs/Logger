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

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kyngs
 */
public final class Logger {

    private final DateTimeFormatter formatter;
    private final PrintStream originalErr;
    private final PrintStream originalOut;
    private final boolean debug;

    /**
     * Only constructor of Logger.
     * @param debug debug state.
     * @param originalErr Original System.err
     * @param originalOut Original System.out
     * @see System#out
     * @see System#err
     */

    protected Logger(boolean debug, PrintStream originalErr, PrintStream originalOut) {
        this.originalErr = originalErr;
        this.originalOut = originalOut;
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
        System.setErr(new PrintStream(new ConsoleStream(Level.ERROR, true, this)));
        System.setOut(new PrintStream(new ConsoleStream(Level.INFO, false, this)));
        this.debug = debug;
    }

    /**
     * @param message Message
     * @param throwables Throwables
     */
    public void info(String message, Throwable... throwables) {
        write(message, Level.INFO, throwables);
    }

    /**
     *
     * @param message Debug message
     * @param throwables Throwables
     */
    public void debug(String message, Throwable... throwables) {
        if (debug) write(message, Level.DEBUG, throwables);
    }

    /**
     *
     * @param message warning message
     * @param throwables Throwables
     */
    public void warn(String message, Throwable... throwables) {
        write(message, Level.WARN, throwables);
    }

    /**
     *
     * @param message Error message
     * @param throwables Throwables
     */
    public void error(String message, Throwable... throwables) {
        write(message, Level.ERROR, throwables);
    }

    /**
     *
     * @param message Message
     * @param level Level
     * @param throwables throwables
     */
    private void write(String message, Level level, Throwable... throwables) {
        write(message, level, 3, throwables);
    }

    /**
     * @param message Message
     * @param level Level
     * @param reflectionLevel StackTrace level
     * @param throwables Throwables
     */
    protected void write(String message, Level level, int reflectionLevel, Throwable... throwables) {
        if (reflectionLevel != -1){
            StackTraceElement pos = new Throwable().getStackTrace()[reflectionLevel];
            message = String.format("[%s] [%s] [%s/%s] [%s]: %s\n", LocalDateTime.now().format(formatter), Thread.currentThread().getName(), pos.getClassName().split("\\.")[pos.getClassName().split("\\.").length - 1], pos.getMethodName(), level.name(), message);
        } else {
            message = String.format("[%s] [%s] [%s]: %s\n", LocalDateTime.now().format(formatter), Thread.currentThread().getName(), level.name(), message);
        }
        if (level == Level.ERROR) {
            originalErr.print(message);
        } else {
            originalOut.print(message);
        }

        for (Throwable throwable : throwables) {
            if (throwable == null || throwable.getMessage() == null) continue;
            write(String.format("%s: %s", throwable.getClass().getName(), throwable.getMessage()), level, reflectionLevel +1);
            for (StackTraceElement trace : throwable.getStackTrace()) {

                write(String.format("\t%s", trace.toString()), level, reflectionLevel +1);

            }
            write(message, level, reflectionLevel + 1, throwable.getCause());
        }

    }

}

