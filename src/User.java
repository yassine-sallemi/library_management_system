import java.time.LocalDate;
import java.util.ArrayList;

public abstract class User {
    protected String login;
    protected String password;
    protected String name;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    Borrow borrowBook(ArrayList<Book> books, String bookId, LocalDate date){
        Book book = Book.searchById(books, bookId);
        if (book == null){
            System.out.println("This book doesn't exist.");
            return null;
        }
        if (!book.isAvailable()){
            System.out.println("This book isn't available right now.");
            return null;
        }
        book.setQuantity(book.getQuantity() - 1);
        return new Borrow((Customer) this,bookId,date,3);
    }

    GiveBack giveBackBook(ArrayList<Book> books, String bookId, LocalDate date){
        Book book = Book.searchById(books, bookId);
        if (book == null){
            System.out.println("This book doesn't exist.");
            return null;
        }
        book.setQuantity(book.getQuantity() + 1);
        return new GiveBack((Customer) this,bookId,date);

    }

}
