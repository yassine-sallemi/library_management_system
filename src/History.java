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

                if (bookInfo.length >= 4) {
                    History history = null;
                    String type = bookInfo[0];
                    Customer customer = Customer.getCustomerByLogin(customers,bookInfo[1]);
                    String borrowedBookId = bookInfo[2];
                    LocalDate date = LocalDate.parse(bookInfo[3]);
                    if(Objects.equals(type, "borrow")){
                        history = new Borrow(customer,borrowedBookId,date,3);
                    } else if (Objects.equals(type, "giveback")){
                        history = new GiveBack(customer,borrowedBookId,date);
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