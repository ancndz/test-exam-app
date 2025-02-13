package interaction;

import book.Bookshelf;
import command.Command;
import library.Library;
import library.LibraryInfoWriter;

import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.Character.toUpperCase;
import static util.ColorUtil.RESET;
import static util.ColorUtil.YELLOW;

/**
 * Предоставляет интерфейс взаимодействия с приложением через консольные команды.
 */
public class ConsoleInteractionSession {

    private static class Messages {

        static final String DONT_UNDERSTAND_MESSAGE = """
                Не удалось распознать команду.
                Используете команду%s help%s для получения информации о доступных командах
                """.formatted(YELLOW, RESET);

        static final String GROUP_DOES_NOT_EXIST_MESSAGE = "Группы книг на такую букву ещё нет в библиотеке";

        static final String SHELF_DOES_NOT_EXIST_MESSAGE = "Полки с таким номером еще нет в библиотеке";

    }

    private final Scanner scanner = new Scanner(System.in);

    private final Library library;

    public ConsoleInteractionSession(Library library) {
        this.library = library;
    }

    public void start() {
        printHelpInfo();

        while (true) {
            String input = scanner.nextLine();
            var args = input.split(" ");
            Command command = Command.recognizeCommand(args[0]);
            switch (command) {
                case GROUPINFO -> printGroupInfo(args);
                case BOOKSINFO -> printInfoAboutBooks();
                case SHELFINFO -> printShelfInfo(args);
                case HELP -> printHelpInfo();
                case EXIT -> {
                    return;
                }
                case null,
                     default -> System.out.println(Messages.DONT_UNDERSTAND_MESSAGE);
            }
        }
    }

    private void printShelfInfo(String[] args) {
        try {
            int numberOfShelf = Integer.parseInt(args[1]);
            Bookshelf bookshelf = library.getShelf(numberOfShelf);
            if (bookshelf == null) {
                System.out.println(Messages.SHELF_DOES_NOT_EXIST_MESSAGE);
            } else {
                bookshelf.getBooks().forEachOrdered(System.out::println);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Для команды " + YELLOW + Command.SHELFINFO.getCommand() + RESET +
                    " через пробел нужно указать номер интересующей полки");
        }
    }

    private void printGroupInfo(String[] args) {
        if (args.length > 1 && args[1].length() == 1) {
            String groupInfo = LibraryInfoWriter.getInfoAboutGroup(library, toUpperCase(args[1].charAt(0)));
            if (groupInfo != null) {
                System.out.println(LibraryInfoWriter.getInfoAboutGroup(library, toUpperCase(args[1].charAt(0))));
            } else System.out.println(Messages.GROUP_DOES_NOT_EXIST_MESSAGE);
        } else {
            library.getAllFirstCharacters().forEach(character ->
                    System.out.println(LibraryInfoWriter.getInfoAboutGroup(library, character)));
        }
    }

    private static void printHelpInfo() {
        Stream.of(Command.values())
                .forEach(command -> System.out.println(command.getDescription()));
    }

    private void printInfoAboutBooks() {
        for (Character character : library.getAllFirstCharacters()) {
            System.out.println("\"" + character + "\":");
            for (Bookshelf bookshelf : library.getShelves(character)) {
                System.out.println("\tПолка № " + bookshelf.getNumber());
                for (int i = 0; i < bookshelf.size(); i++) {
                    System.out.println("\t\t" + (i + 1) + "). " + bookshelf.getBook(i));
                }
            }
        }
    }

}
