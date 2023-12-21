import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        User currentUser = null;

        Scanner scanner = new Scanner(System.in);
        boolean isLoggedIn = false;

        System.out.println("Library Management System");
        while (true) {
            if (!isLoggedIn) {
                System.out.println("Please log in");
                System.out.print("-Username: ");
                String inputUsername = scanner.nextLine();
                System.out.print("-Password: ");
                String inputPassword = scanner.nextLine();
                currentUser = library.loginAccount(inputUsername, inputPassword);

                if (currentUser != null) {
                    System.out.println("Logged in successfully.");
                    isLoggedIn = true;
                }
            } else {
                System.out.println("1. Logout");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        isLoggedIn = false;
                        System.out.println("Logged out.");
                        break;
                    case 2:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            }
        }
    }
}