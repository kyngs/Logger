package cz.kyngs.logger;

import java.util.function.Consumer;

public interface Logger {

    void info(String message, Throwable... throwables);

    void debug(String message, Throwable... throwables);

    void warn(String message, Throwable... throwables);

    void error(String message, Throwable... throwables);

    void addInputListener(Consumer<String> listener);

}
