package book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Книжная полка.
 */
public class Bookshelf {

    private static final int MAX_SIZE = 10;
    private final List<Book> books = new ArrayList<>(MAX_SIZE + 1);
    private Character shelfCharacter;
    private int number;

    public Bookshelf(int number) {
        this.number = number;
    }

    public boolean canAddBook(Book book) {
        return hasFreeSpace() && Objects.equals(book.getFirstCharacterForComparing(), shelfCharacter);
    }

    public boolean hasFreeSpace() {
        return books.size() < MAX_SIZE;
    }

    public boolean add(Book book) {
        if (shelfCharacter == null)
            shelfCharacter = book.getFirstCharacterForComparing();

        if (canAddBook(book)) {
            books.add(book);
            book.setNumberOfShelf(getNumber());
            Collections.sort(books);
            return true;
        }
        return false;
    }

    public Stream<Book> getBooks() {
        return books.stream();
    }

    public Book getBook(int index) {
        if (index >= 0 && index < books.size()) {
            return books.get(index);
        }
        return null;
    }

    public int size() {
        return books.size();
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
