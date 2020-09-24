package cz.kyngs.logger.command;

import java.util.function.Consumer;

public interface CommandHandler {

    void registerCommand(Command command, String commandName);

    void register(String name, CommandExecutor commandExecutor);

    CommandExecutionResult execCommand(CommandSender sender, String content);

    void onUnknownCommandFoundAction(Consumer<CommandSender> onUnknownCommandFoundAction);

}
