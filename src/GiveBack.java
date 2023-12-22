import java.time.LocalDate;

public class GiveBack extends History{

    public GiveBack(Customer customer, String borrowedBookId, LocalDate date) {
        this.customer = customer;
        this.borrowedBookId = borrowedBookId;
        this.date = date;
    }

    @Override
    void displayRecord() {
        System.out.print(date + ": " + customer.name + " (" + customer.login + ") returned a book (ID: " + borrowedBookId +")");
    }
}
