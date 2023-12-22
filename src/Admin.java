import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Admin extends User{
    public Admin(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public static ArrayList<Admin> populate() {
        String textFile = "admins.txt"; // Replace with your text file path
        ArrayList<Admin> admins = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] adminInfo = line.split(",");

                if (adminInfo.length >= 3) {
                    String login = adminInfo[0];
                    String password = adminInfo[1];
                    String name = adminInfo[2];

                    Admin admin = new Admin(login, password, name);
                    admins.add(admin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }
}
