package cz.kyngs.logger.command;

import org.jetbrains.annotations.Nullable;

public class LogCommand implements Command {

    private CommandExecutor commandExecutor;

    public LogCommand(@Nullable CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    @Override
    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}
