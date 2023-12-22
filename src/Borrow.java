import java.time.LocalDate;
import java.util.ArrayList;

public class Borrow extends History{
    private int hasToReturnAfter;

    public Borrow(Customer customer,String borrowedBookId,LocalDate date, int hasToReturnAfter) {
        this.customer = customer;
        this.borrowedBookId = borrowedBookId;
        this.date = date;
        this.hasToReturnAfter = hasToReturnAfter;
    }


    @Override
    void displayRecord() {
        System.out.println(date + ": " + customer.name + " (" + customer.login + ") borrrowed a book (ID: " + borrowedBookId +")");
    }
}
