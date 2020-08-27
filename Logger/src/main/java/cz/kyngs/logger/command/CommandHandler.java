package cz.kyngs.logger.command;

public interface CommandHandler {

    void registerCommand(Command command, String commandName);

    CommandExecutionResult execCommand(CommandSender sender, String content);
}
