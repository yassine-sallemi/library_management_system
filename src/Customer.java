import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Customer extends User{
    private boolean premium = false;

    public Customer(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public static Customer getCustomerByLogin(ArrayList<Customer> customers, String customerLogin){
        for(Customer customer: customers){
            if(Objects.equals(customer.getLogin(), customerLogin)){
                return customer;
            }
        }
        return null;
    }

    public static ArrayList<Customer> populate() {
        String textFile = "customers.txt"; // Replace with your text file path
        ArrayList<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] adminInfo = line.split(",");

                if (adminInfo.length >= 3) {
                    String login = adminInfo[0];
                    String password = adminInfo[1];
                    String name = adminInfo[2];

                    Customer customer = new Customer(login, password, name);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
