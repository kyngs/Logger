package cz.kyngs.logger.command;

public enum CommandExecutionResult {

    UNKNOWN_COMMAND("Unknown command!"),
    COMMAND_ERROR("An internal error occurred while attempting to perform this command"),
    SUCCESS(null);

    private final String desc;

    CommandExecutionResult(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
