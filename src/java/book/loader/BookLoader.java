package book.loader;

import book.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;


/**
 * Загрузчик книг из внешних источников.
 */
public class BookLoader {

    private static final String BASE_EXCEPTION_MESSAGE = "Ошибка конвертации данных из источника. Некорректный формат записи данных. Path to source: {0}, Incorrect Data: {1}.";

    public static Stream<Book> loadBooks(Path path) {
        try {
            return Files.readAllLines(path).stream().skip(1).map(line -> {
                var data = line.split(",");
                if (data.length < 3) {
                    throw new RuntimeException(format(BASE_EXCEPTION_MESSAGE, path, data));
                }
                final String title = data[0];
                final int year = Integer.parseInt(data[1]);
                final String authorName = data[2];
                return new Book(title, year, authorName);
            });
        } catch (NumberFormatException e) {
            throw new RuntimeException(BASE_EXCEPTION_MESSAGE + " " + "Вторым аргументом должен идти год выпуска.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
