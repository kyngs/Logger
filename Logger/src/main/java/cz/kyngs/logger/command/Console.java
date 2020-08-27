package cz.kyngs.logger.command;

import java.util.function.Consumer;

public interface Console extends CommandSender, Consumer<String> {
}
