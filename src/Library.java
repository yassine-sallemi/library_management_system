import java.util.ArrayList;
import java.util.Objects;

public class Library {
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();

    public Library() {
        Admin admin = new Admin("admin", "admin","admin");
        admins.add(admin);
    }

    // Users management
    User createAccount(String login, String password, String name, boolean isAdmin, User currentUser){
        // Check if current user is admin
        if(!isAdmin(currentUser)){
            System.out.println("Current user is not an administrator.");
            return null;
        }

        // Check if the new login is unique
        boolean loginIsUnique = true;
        for (Admin admin : admins){
            if(Objects.equals(admin.getLogin(), login)){
                loginIsUnique = false;
                break;
            }
        }
        if(loginIsUnique){
            for (Customer customer : customers){
                if(Objects.equals(customer.getLogin(), login)){
                    loginIsUnique = false;
                    break;
                }
            }
            if(!loginIsUnique){
                System.out.println("This login is already used, try another one.");
                return null;
            }
        }

        // Check if password is at least 4 characters
        if(password.length() < 4){
            System.out.println("Password should be at least 4 characters.");
            return null;
        }

        // Create new User based on its role
        User newUser;
        if(isAdmin){
            System.out.println("New administrator has been added: " + name);
            newUser = new Admin(login,password,name);
            admins.add((Admin) newUser);
        }
        else{
            System.out.println("New customer has been added: " + name);
            newUser = new Customer(login,password,name);
            customers.add((Customer) newUser);
        }
        return newUser;
    }
    User loginAccount(String login, String password){
        // Search for the user in the database based on its login
        User user = null;
        for (Admin admin : admins){
            if(Objects.equals(admin.getLogin(), login)){
                user = admin;
                break;
            }
        }
        if(user == null){
            for (Customer customer : customers){
                if(Objects.equals(customer.getLogin(), login)){
                    user = customer;
                    break;
                }
            }
        }

        // Return null if no user is found
        if(user == null){
            System.out.println("User not found.");
            return null;
        }

        // Check the password and return the user otherwise
        if(!Objects.equals(user.getPassword(), password)){
            System.out.println("The given password is wrong");
            return null;
        }
        System.out.println("Logged in successfully.");
        return user;
    }
    boolean isAdmin(User user){
        return admins.contains(user);
    }

    // Books management
    void addBook(String id, String title, String author, int quantity){
        for (Book book : books){
            if(Objects.equals(book.getId(), id)){
                System.out.println("This book id already exists.");
                return;
            }
        }
        Book book = new Book(id, title, author, quantity);
        books.add(book);
        System.out.println("New book added.");
    }
}
