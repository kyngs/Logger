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
 * @see SyncLogger
 */
public class SyncLogBuilder {

    private static final PrintStream DEFAULT_OUT;
    private static final PrintStream DEFAULT_ERR;
    private static final boolean DEFAULT_DEBUG;
    private static final Consumer<String> DEFAULT_ON_COMMAND_RECEIVED;

    static {
        DEFAULT_OUT = System.out;
        DEFAULT_ERR = System.err;
        DEFAULT_DEBUG = false;
        DEFAULT_ON_COMMAND_RECEIVED = null;
    }

    private boolean debug;
    private Consumer<String> onCommandReceived;
    private PrintStream out, err;

    protected SyncLogBuilder() {
        debug = DEFAULT_DEBUG;
        onCommandReceived = DEFAULT_ON_COMMAND_RECEIVED;
        out = DEFAULT_OUT;
        err = DEFAULT_ERR;
    }

    public SyncLogBuilder setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public SyncLogBuilder setOnCommandReceived(Consumer<String> onCommandReceived) {
        this.onCommandReceived = onCommandReceived;
        return this;
    }

    public SyncLogBuilder setOut(PrintStream out) {
        this.out = out;
        return this;
    }

    public SyncLogBuilder setErr(PrintStream err) {
        this.err = err;
        return this;
    }

    public SyncLogger build() {
        return new SyncLogger(debug, err, out, onCommandReceived);
    }

}
