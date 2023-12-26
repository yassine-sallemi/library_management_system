import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public Boolean isAvailable() {
        return quantity > 0;
    }

    public static Book searchById(List<Book> books, String id) {
        for (Book book : books) {
            if (book.getId().equals(id.toUpperCase())) {
                return book;
            }
        }
        return null;
    }

    public static ArrayList<Book> populate() {
        String textFile = "books.txt"; // Replace with your text file path
        ArrayList<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookInfo = line.split(","); // Assuming fields are separated by commas

                if (bookInfo.length >= 4) {
                    String id = bookInfo[0];
                    String title = bookInfo[1];
                    String author = bookInfo[2];
                    int quantity = Integer.parseInt(bookInfo[3]);

                    Book book = new Book(id, title, author, quantity);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
