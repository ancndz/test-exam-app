package library;

import book.Book;
import book.Bookshelf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Библиотека.
 */
public class Library {

    private final Map<Character, List<Bookshelf>> data = new TreeMap<>();
    private Map<Character, String> numbersOfShelvesForChar;
    private int countOfShelves;

    public boolean add(Book book) {
        numbersOfShelvesForChar = null;
        return getShelfForBook(book.getFirstCharacterForComparing()).add(book);
    }

    public List<Bookshelf> getShelves(char firstChar) {
        return data.get(firstChar);
    }

    public Bookshelf getShelf(int number) {
        return data.values().stream().flatMap(List::stream)
                .filter(shelf -> shelf.getNumber() == number).findAny().orElse(null);
    }

    public Set<Character> getAllFirstCharacters() {
        return data.keySet();
    }

    public Long getCountOfBooks(char firstChar) {
        if (data.containsKey(firstChar))
            return data.get(firstChar).stream().flatMap(Bookshelf::getBooks).count();
        else return null;
    }

    public String getBookshelvesNumbersRepresentationForCharacter(char firstChar) {
        if (numbersOfShelvesForChar == null)
            initShelvesRepresentation();

        return numbersOfShelvesForChar.get(firstChar);
    }

    private void initShelvesRepresentation() {
        numbersOfShelvesForChar = new HashMap<>(data.size() + 1);

        getAllFirstCharacters().forEach(character ->
                numbersOfShelvesForChar.put(character,
                        data.get(character).stream()
                                .map(Bookshelf::getNumber)
                                .map(String::valueOf)
                                .collect(Collectors.joining(", "))));
    }

    private boolean containsCharacter(char firstChar) {
        return data.containsKey(firstChar);
    }

    private Bookshelf getShelfForBook(char firstChar) {
        List<Bookshelf> shelves;
        Bookshelf resultBookshelf;
        if (containsCharacter(firstChar)) {
            shelves = getShelves(firstChar);
            Bookshelf currentLastBookshelf = shelves.getLast();
            if (currentLastBookshelf.hasFreeSpace()) {
                resultBookshelf = currentLastBookshelf;
            } else {
                Bookshelf newBookshelf = new Bookshelf(++countOfShelves);
                shelves.add(newBookshelf);
                resultBookshelf = newBookshelf;
            }
        } else {
            resultBookshelf = new Bookshelf(++countOfShelves);
            shelves = new ArrayList<>();
            shelves.add(resultBookshelf);
            data.put(firstChar, shelves);
        }
        return resultBookshelf;
    }

}
