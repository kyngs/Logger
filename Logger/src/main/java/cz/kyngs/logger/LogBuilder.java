package cz.kyngs.logger;

public class LogBuilder {

    public static AsyncLogBuilder async() {
        return new AsyncLogBuilder();
    }

    public static SyncLogBuilder sync() {
        return new SyncLogBuilder();
    }

}
