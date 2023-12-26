import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public abstract class History {
    protected Customer customer;
    protected String borrowedBookId;
    protected LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    abstract void displayRecord();

    public static void displayHistoryUser(ArrayList<History> historyLogs, Customer customer) {
        for (History history : historyLogs) {
            if (Objects.equals(history.customer.getLogin(), customer.getLogin())) {
                history.displayRecord();
            }
        }
    }

    public static void displayHistory(ArrayList<History> historyLogs) {
        for (History history : historyLogs) {
            history.displayRecord();
        }
    }

    public static ArrayList<History> populate(ArrayList<Customer> customers) {
        String textFile = "historyLogs.txt"; // Replace with your text file path
        ArrayList<History> historyLogs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookInfo = line.split(","); // Assuming fields are separated by commas

                String type;
                Customer customer;
                String borrowedBookId;
                LocalDate date;
                int hasToReturnAfter;
                boolean lateFee;
                if (bookInfo.length >= 4) {
                    History history = null;

                    type = bookInfo[0];
                    customer = Customer.getCustomerByLogin(customers,bookInfo[1]);
                    borrowedBookId = bookInfo[2];
                    date = LocalDate.parse(bookInfo[3]);
                    if(Objects.equals(type, "borrow")){
                        hasToReturnAfter = Integer.parseInt(bookInfo[4]);
                        history = new Borrow(customer,borrowedBookId,date,hasToReturnAfter);
                    } else if (Objects.equals(type, "giveback")){
                        lateFee = Boolean.parseBoolean(bookInfo[4]);
                        history = new GiveBack(customer,borrowedBookId,date,lateFee);
                    }
                    historyLogs.add(history);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historyLogs;
    }
}