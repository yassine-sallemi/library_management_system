import java.time.LocalDate;

public class Borrow extends History{
    private int hasToReturnAfter;

    public Borrow(Customer customer, String borrowedBookId, LocalDate date, int hasToReturnAfter) {
        this.customer = customer;
        this.borrowedBookId = borrowedBookId;
        this.date = date;
        this.hasToReturnAfter = hasToReturnAfter;
    }

    public int getHasToReturnAfter() {
        return hasToReturnAfter;
    }

    @Override
    void displayRecord() {
        LocalDate returnDate = date.plusDays(hasToReturnAfter);
        System.out.print(date + ": " + customer.getName() + " (" + customer.getLogin() + ") borrowed a book (ID: " + borrowedBookId +")");
        System.out.println("\tTo be returned before or on " + returnDate);
    }
}
