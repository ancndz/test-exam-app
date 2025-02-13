package library;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;


public class LibraryInfoWriter {

    private static final String MESSAGE_FORMAT_FOR_MANY = "Книги на \"{0}\" расположены на полках № {1}. Количество книг - {2}";
    private static final String MESSAGE_FORMAT_FOR_ONE = "Книги на \"{0}\" расположены на полке № {1}. Количество книг - {2}";

    public static void writeInfoAboutBooks(Library library, String pathToResultFile) {
        File resultFile = new File(pathToResultFile);

        try (FileWriter fileWriter = new FileWriter(resultFile, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            library.getAllFirstCharacters().forEach(firstChar -> {
                try {
                    String infoForWrite = getInfoAboutGroup(library, firstChar);
                    if (infoForWrite != null) {
                        bufferedWriter.write(infoForWrite);
                        bufferedWriter.newLine();
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка при попытке записать информацию о библиотеке в файл", e);
                }
            });
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getInfoAboutGroup(Library library, Character firstChar) {
        String shelvesRepresentation = library.getBookshelvesNumbersRepresentationForCharacter(firstChar);
        if (shelvesRepresentation == null)
            return null;
        long countOfBooks = library.getCountOfBooks(firstChar);
        return MessageFormat.format(getRepresentationPattern(shelvesRepresentation),
                firstChar, shelvesRepresentation, countOfBooks);
    }

    private static String getRepresentationPattern(String shelvesRepresentation) {
        return shelvesRepresentation.contains(", ") ? MESSAGE_FORMAT_FOR_MANY : MESSAGE_FORMAT_FOR_ONE;
    }

}
