import java.util.ArrayList;
import java.util.List;

public class Book {
    private String id;
    private String title;
    private String author;
    private int quantity;

    public Book(String id, String title, String author , int quantity) {
        this.id = id.toUpperCase();
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void displayBook() {
        System.out.println("- " + title + ": (ID:" + id + ")");
        System.out.println("\tWritten by: " + author);
        System.out.println("\tAvailable quantity: " + quantity);
    }

    public static void displayBooks(List<Book> books) {
        for (Book book : books) {
            book.displayBook();
        }
    }

    public static Book searchById(List<Book> books, String id) {
        for (Book book : books) {
            if (book.getId().equals(id.toUpperCase())) {
                return book;
            }
        }
        return null;
    }

    public static List<Book> searchByTitle(List<Book> books, String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public static List<Book> searchByAuthor(List<Book> books, String author) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }
}
