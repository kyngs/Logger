package cz.kyngs.logger.command;

public interface CommandHandler {

    void registerCommand(Command command, String commandName);

    void register(String name, CommandExecutor commandExecutor);

    CommandExecutionResult execCommand(CommandSender sender, String content);
}
