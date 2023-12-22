import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Library {
    private final ArrayList<Admin> admins = Admin.populate();
    private final ArrayList<Customer> customers = Customer.populate();
    private final ArrayList<Book> books = Book.populate();
    private final ArrayList<History> historyLogs = new ArrayList<>();
    private User currentUser = null;

    // Menu
    void startMenu(){
        System.out.println("Library Management System");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (currentUser == null) {
                System.out.println("Please log in");
                System.out.print("- Username: ");
                String inputLogin = scanner.nextLine();
                System.out.print("- Password: ");
                String inputPassword = scanner.nextLine();
                currentUser = loginAccount(inputLogin, inputPassword);

            } else {
                if (currentUser instanceof Admin){
                    adminMenu();
                } else if (currentUser instanceof Customer){
                    customerMenu();
                }
            }

        }
    }

     void adminMenu(){
        Scanner scanner = new Scanner(System.in);
        String name;
        String login;
        String password;

        String id;
        String title;
        String author;
        int quantity;

        while(true){
            System.out.println("1. Add a new customer");
            System.out.println("2. Add a new book");
            System.out.println("3. Add copies of an existing book");
            System.out.println("4. Remove an existing book");
            System.out.println("------");
            System.out.println("5. Add an admin");
            System.out.println("6. History logs");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("- Name: ");
                    name = scanner.nextLine();
                    System.out.print("- Username: ");
                    login = scanner.nextLine();
                    System.out.print("- Password: ");
                    password = scanner.nextLine();

                    createAccount(login,password,name,false);
                    break;
                case 2:
                    System.out.print("- Book ID: ");
                    id = scanner.nextLine();
                    System.out.print("- Title: ");
                    title = scanner.nextLine();
                    System.out.print("- Author: ");
                    author = scanner.nextLine();
                    System.out.print("- Initial quantity: ");
                    quantity = scanner.nextInt();
                    addBook(id,title,author,quantity);
                    break;
                case 3:
                    System.out.print("- Book ID: ");
                    id = scanner.nextLine();
                    System.out.print("- Number of new copies: ");
                    quantity = scanner.nextInt();
                    increaseBookQuantity(id,quantity);
                    break;
                case 4:
                    System.out.print("- Book ID: ");
                    id = scanner.nextLine();
                    removeBook(id);
                    break;
                case 5:
                    System.out.print("- Name: ");
                    name = scanner.nextLine();
                    System.out.print("- Username: ");
                    login = scanner.nextLine();
                    System.out.print("- Password: ");
                    password = scanner.nextLine();

                    createAccount(login,password,name,true);

                    break;
                case 6:
                    History.displayHistory(historyLogs);
                    return;
                case 7:
                    currentUser = null;
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }

    }

    void customerMenu(){
        Scanner scanner = new Scanner(System.in);
        String id;
        LocalDate date = null;

        while(true){
            System.out.println("1. Borrow a book");
            System.out.println("2. Return a book");
            System.out.println("3. Display all available books");
            System.out.println("4. Show history");
            System.out.println("------");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("- Book ID: ");
                    id = scanner.nextLine();
                    System.out.print("- Enter today's date (YYYY-MM-JJ): ");
                    try {
                        date = LocalDate.parse(scanner.nextLine());
                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Please enter a date in the format YYYY-MM-DD.");
                    }
                    Borrow borrow = currentUser.borrowBook(books, id, date);
                    historyLogs.add(borrow);
                    break;
                case 2:
                    System.out.print("- Book ID: ");
                    id = scanner.nextLine();
                    System.out.print("- Enter today's date (YYYY-MM-JJ): ");
                    try {
                        date = LocalDate.parse(scanner.nextLine());
                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Please enter a date in the format YYYY-MM-DD.");
                    }
                    GiveBack giveBack = currentUser.giveBackBook(books, id, date);
                    historyLogs.add(giveBack);
                    break;
                case 3:
                    Book.displayBooks(books);
                    break;
                case 4:
                    History.displayHistoryUser(historyLogs, (Customer) currentUser);
                    break;
                case 5:
                    currentUser = null;
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    // Users management
    void createAccount(String login, String password, String name, boolean isAdmin){

        // Check if current user is admin
        if(!isAdmin(currentUser)){
            System.out.println("Current user is not an administrator.");
            return;
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
                System.out.println("This username is already used, try another one.");
                return;
            }
        }

        // Check if password is at least 4 characters
        if(password.length() < 4){
            System.out.println("Password should be at least 4 characters.");
            return;
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
            if(Objects.equals(book.getId(), id.toUpperCase())){
                System.out.println("This book id already exists.");
                return;
            }
        }
        Book book = new Book(id, title, author, quantity);
        books.add(book);
        System.out.println("New book added.");
    }

    void increaseBookQuantity(String id, int quantity){
        for (Book book : books){
            if(Objects.equals(book.getId(), id.toUpperCase())){
                book.setQuantity(book.getQuantity() + quantity);
                System.out.println("More copies added.");
                return;
            }
        }
        System.out.println("This book does not exist.");

    }

    void removeBook(String id){
        for (Book book : books){
            if(Objects.equals(book.getId(), id.toUpperCase())){
                books.remove(book);
                System.out.println("Book removed.");
                return;
            }
        }
        System.out.println("This book does not exist.");
    }
}
