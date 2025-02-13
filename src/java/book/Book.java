package book;

import java.util.Comparator;
import java.util.Objects;

/**
 * Книга.
 */
public class Book implements Comparable<Book> {

    private final String title;

    private final int year;

    private final String authorName;

    private int numberOfShelf;

    public Book(String title, int year, String authorName) {
        this.title = title;
        this.year = year;
        this.authorName = authorName;
    }

    public Book(String title, int year, String authorName, int numberOfShelf) {
        this.title = title;
        this.year = year;
        this.authorName = authorName;
        this.numberOfShelf = numberOfShelf;
    }

    public Character getFirstCharacterForComparing() {
        return Character.toUpperCase(title.equals("") ? authorName.charAt(0) : title.charAt(0));
    }

    @Override
    public int compareTo(Book o) {
        return Comparator.comparing(Book::getFirstCharacterForComparing)
                .thenComparing(Book::getYear)
                .compare(this, o);
    }

    public void setNumberOfShelf(int numberOfShelf) {
        this.numberOfShelf = numberOfShelf;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getNumberOfShelf() {
        return numberOfShelf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Objects.equals(title, book.title) && Objects.equals(authorName, book.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, authorName);
    }

    @Override
    public String toString() {
        String title = "".equals(getTitle()) ? "-" : getTitle();
        return "Наименование: " + title + ", Год: " + year + ", Автор: " + authorName;
    }

}