package cz.kyngs.logger.command;

import cz.kyngs.logger.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LogCommandHandler implements CommandHandler, Console {

    private final Map<String, Command> commandMap;
    private final Logger logger;

    public LogCommandHandler(Logger logger) {
        this.logger = logger;
        commandMap = new HashMap<>();
        logger.addCommandListener(this);
    }

    @Override
    public void register(String name, CommandExecutor commandExecutor) {
        registerCommand(new LogCommand(commandExecutor), name);
    }

    @Override
    public void registerCommand(Command command, String commandName) {
        commandMap.put(commandName, command);
    }

    private Command getCommand(String command) {
        return commandMap.get(command);
    }

    @Override
    public CommandExecutionResult execCommand(CommandSender sender, String content) {
        String[] args = content.split(" ");
        Command command = getCommand(args[0]);
        if (command == null || command.getCommandExecutor() == null) return CommandExecutionResult.UNKNOWN_COMMAND;
        if (args.length >= 2) {
            args = Arrays.copyOfRange(args, 1, args.length);
        } else {
            args = new String[0];
        }
        try {
            command.getCommandExecutor().exec(args, sender);
        } catch (Exception e) {
            logger.warn("Error occurred while performing command!", e);
            return CommandExecutionResult.COMMAND_ERROR;
        }
        return CommandExecutionResult.SUCCESS;
    }

    @Override
    public void accept(String s) {
        execCommand(this, s);
    }

    @Override
    public void sendMessage(String message) {
        logger.info(message);
    }

    @Override
    public String getName() {
        return "CONSOLE";
    }
}
