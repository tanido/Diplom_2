import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentialsChange {

    public String email;
    public String password;
    public String name;

    public UserCredentialsChange(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserCredentialsChange() {
    }

    public String getEmail() {
        return email;
    }

    public UserCredentialsChange setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserCredentialsChange setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserCredentialsChange setName(String name) {
        this.name = name;
        return this;
    }

    public static UserCredentialsChange credentialsChange() {
        return new UserCredentialsChange().setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru").setPassword(RandomStringUtils.randomAlphabetic(10)).setName(RandomStringUtils.randomAlphabetic(10));
    }
}
