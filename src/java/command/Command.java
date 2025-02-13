package command;

import java.util.stream.Stream;

import static util.ColorUtil.RESET;
import static util.ColorUtil.YELLOW;

/**
 * Команды для взаимодействия с приложением.
 */
public enum Command {
    GROUPINFO("groupinfo", "Вывести информацию о группе книг (номера полок и количество книг)", true),
    SHELFINFO("shelfinfo", "Вывести информацию о полке", true),
    BOOKSINFO("booksinfo", "Вывести информацию о книгах", false),
    HELP("help", "Вывести информацию о доступных командах", false),
    EXIT("exit", "Завершить работу", false);

    private final String command;
    private final String description;
    private final boolean hasArgument;

    Command(String command, String description, boolean hasArgument) {
        this.command = command;
        this.description = description;
        this.hasArgument = hasArgument;
    }

    public static Command recognizeCommand(String value) {
        return Stream.of(Command.values())
                .filter(cmnd -> cmnd.getCommand().equals(value) ||
                        cmnd.getCommand().toUpperCase().equals(value))
                .findAny().orElse(null);
    }

    public String getDescription() {
        return description + " - " + YELLOW + command + RESET + " " + getAdditionalDescription();
    }

    private String getAdditionalDescription() {
        if (hasArgument)
            return "<argument>";
        else return "";
    }

    public String getCommand() {
        return command;
    }

}
