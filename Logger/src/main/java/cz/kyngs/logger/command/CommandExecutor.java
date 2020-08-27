package cz.kyngs.logger.command;

public interface CommandExecutor {

    void exec(String[] args, CommandSender commandSender);

}
