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
import java.util.function.Consumer;

/**
 * Simple LogManager
 *
 * @author kyngs
 * @see Logger
 */
public class LogBuilder {

    private static final PrintStream DEFAULT_OUT;
    private static final PrintStream DEFAULT_ERR;
    private static final boolean DEFAULT_DEBUG;
    private static final Consumer<String> DEFAULT_ON_COMMAND_RECIEVED;
    private static final String DEFAULT_CONSOLE_PREFIX;

    static {
        DEFAULT_OUT = System.out;
        DEFAULT_ERR = System.err;
        DEFAULT_DEBUG = false;
        DEFAULT_ON_COMMAND_RECIEVED = null;
        DEFAULT_CONSOLE_PREFIX = "";

    }

    private boolean debug;
    private Consumer<String> onCommandReceived;
    private String consolePrefix;
    private PrintStream out, err;

    public LogBuilder() {
        debug = DEFAULT_DEBUG;
        onCommandReceived = DEFAULT_ON_COMMAND_RECIEVED;
        consolePrefix = DEFAULT_CONSOLE_PREFIX;
        out = DEFAULT_OUT;
        err = DEFAULT_ERR;
    }

    public LogBuilder setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public LogBuilder setOnCommandReceived(Consumer<String> onCommandReceived) {
        this.onCommandReceived = onCommandReceived;
        return this;
    }

    public LogBuilder setConsolePrefix(String consolePrefix) {
        this.consolePrefix = consolePrefix;
        return this;
    }

    public LogBuilder setOut(PrintStream out) {
        this.out = out;
        return this;
    }

    public LogBuilder setErr(PrintStream err) {
        this.err = err;
        return this;
    }

    public Logger build() {
        return new Logger(debug, err, out, onCommandReceived, consolePrefix);
    }

}
