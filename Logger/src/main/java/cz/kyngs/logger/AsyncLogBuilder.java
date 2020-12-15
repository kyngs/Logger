package cz.kyngs.logger;

public class AsyncLogBuilder extends SyncLogBuilder {

    private static final int DEFAULT_THREAD_COUNT;

    static {
        DEFAULT_THREAD_COUNT = 4;
    }

    private int threadCount;

    protected AsyncLogBuilder() {
        super();
        threadCount = DEFAULT_THREAD_COUNT;
    }

    public AsyncLogBuilder setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        return this;
    }

    @Override
    public Logger build() {
        return new AsyncLogger(debug, err, out, onInputReceived, threadCount);
    }
}
