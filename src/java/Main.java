import book.loader.BookLoader;
import interaction.ConsoleInteractionSession;
import library.Library;
import library.LibraryInfoWriter;

import java.nio.file.Path;

/**
 * Главный класс для запуска приложения.
 */
public class Main {

    private static final String PATH_TO_DATABASE = "src/resources/books.csv";
    private static final String PATH_TO_FILE_RESULT = "src/resources/result.txt";

    public static void main(String[] args) {
        Library library = new Library();
        BookLoader.loadBooks(Path.of(PATH_TO_DATABASE)).sorted().forEach(library::add);
        LibraryInfoWriter.writeInfoAboutBooks(library, PATH_TO_FILE_RESULT);
        var interactionSession = new ConsoleInteractionSession(library);
        interactionSession.start();
    }

}
