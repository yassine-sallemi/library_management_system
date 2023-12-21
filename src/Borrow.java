import java.time.LocalDate;
import java.util.ArrayList;

public class Borrow {
    private Customer customer;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    private LocalDate borrowDate;
    private int hasToReturnAfter;

    public Borrow(Customer customer, LocalDate borrowDate, int hasToReturnAfter) {
        this.customer = customer;
        this.borrowDate = borrowDate;
        this.hasToReturnAfter = hasToReturnAfter;
    }


}
