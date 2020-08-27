package cz.kyngs.logger.command;

public interface Command {

    CommandExecutor getCommandExecutor();

    void setCommandExecutor(CommandExecutor commandExecutor);

}
