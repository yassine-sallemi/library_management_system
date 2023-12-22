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
}