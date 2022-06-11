import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentials {

    public String email;
    public String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredentials() {
    }

    public String getEmail() {
        return email;
    }

    public UserCredentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.email, user.password);
    }

    public static UserCredentials userLoginWithWrongEmail(User user) {
        return new UserCredentials().setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru").setPassword(user.password);
    }

    public static UserCredentials userLoginWithWrongPassword(User user) {
        return new UserCredentials().setEmail(user.email).setPassword(RandomStringUtils.randomAlphabetic(10));
    }
}
