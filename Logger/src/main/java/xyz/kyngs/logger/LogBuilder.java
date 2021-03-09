package xyz.kyngs.logger;

public class LogBuilder {

    public static SyncLogBuilder sync() {
        return new SyncLogBuilder();
    }

    public static AsyncLogBuilder async() {
        return new AsyncLogBuilder();
    }

}
