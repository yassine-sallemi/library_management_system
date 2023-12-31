import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Customer extends User{
    private boolean premium ;

    public Customer(String login, String password, String name, boolean premium) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.premium = premium;
    }

    public static Customer getCustomerByLogin(ArrayList<Customer> customers, String customerLogin){
        for(Customer customer: customers){
            if(Objects.equals(customer.getLogin(), customerLogin)){
                return customer;
            }
        }
        return null;
    }

    Borrow borrowBook(ArrayList<History> historyLogs,ArrayList<Book> books, String bookId, LocalDate date){
        Book book = Book.searchById(books, bookId);
        if (book == null){
            System.out.println("This book doesn't exist.");
            return null;
        }
        if (!book.isAvailable()){
            System.out.println("This book isn't available right now.");
            return null;
        }

        for(History history : historyLogs){
            if(history.customer == this){
                if(history instanceof Borrow){
                    System.out.println(name + " is currently borrowing a book.");
                    return null;
                }
            }
        }

        book.setQuantity(book.getQuantity() - 1);
        if (premium){
            return new Borrow(this,bookId,date,14);
        }
        return new Borrow(this,bookId,date,3);
    }

    GiveBack giveBackBook(ArrayList<History> historyLogs, ArrayList<Book> books, String bookId, LocalDate date){
        Book book = Book.searchById(books, bookId);
        bookId = bookId.toUpperCase();
        if (book == null){
            System.out.println("This book doesn't exist.");
            return null;
        }

        Borrow borrow;
        boolean lateFee = false;
        for(History history : historyLogs){
            if(history.customer == this){
                if(history instanceof GiveBack){
                    System.out.println(name + " isn't currently borrowing any book.");
                    return null;
                }
                if(history instanceof Borrow && Objects.equals(book.getId(), bookId)){
                    borrow = (Borrow) history;
                    LocalDate dueDate = borrow.getDate().plusDays(borrow.getHasToReturnAfter());
                    if (date.isAfter(dueDate))
                        lateFee = true;
                    break;
                }
            }
        }

        book.setQuantity(book.getQuantity() + 1);


        return new GiveBack(this,bookId,date,lateFee);

    }

    public static ArrayList<Customer> populate() {
        String textFile = "customers.txt";
        ArrayList<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] adminInfo = line.split(",");

                if (adminInfo.length >= 3) {
                    String login = adminInfo[0];
                    String password = adminInfo[1];
                    String name = adminInfo[2];
                    boolean premium = Boolean.parseBoolean(adminInfo[3]);

                    Customer customer = new Customer(login, password, name, premium);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
