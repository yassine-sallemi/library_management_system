import java.time.LocalDate;
public class GiveBack extends History{
    private Boolean lateFee;
    public GiveBack(Customer customer, String borrowedBookId, LocalDate date, Boolean lateFee) {
        this.customer = customer;
        this.borrowedBookId = borrowedBookId;
        this.date = date;
        this.lateFee = lateFee;
    }

    @Override
    void displayRecord() {
        System.out.print(date + ": " + customer.name + " (" + customer.login + ") returned a book (ID: " + borrowedBookId +")");
        if(lateFee)
            System.out.println("\t(Has to pay late fees.)");
        else
            System.out.println("\t(Returned on time.)");
    }
}
