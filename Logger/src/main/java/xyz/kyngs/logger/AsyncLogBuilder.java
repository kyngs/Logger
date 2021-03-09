package xyz.kyngs.logger;

public class AsyncLogBuilder extends SyncLogBuilder {

    @Override
    public Logger build() {
        return new AsyncLogger(allowAnsi, pattern, colorMap, logVars, changeColorOnLevel);
    }
}
