import java.time.LocalDate;
import java.util.ArrayList;

public abstract class User {
    protected String login;
    protected String password;
    protected String name;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }


}
