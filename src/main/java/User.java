import org.apache.commons.lang3.RandomStringUtils;

public class User {

    public String email;
    public String password;
    public String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public static User getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(5) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }

    public static User createUserWithoutEmail() {
        return new User().setName(RandomStringUtils.randomAlphabetic(10)).setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public static User createUserWithoutPassword() {
        return new User().setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru").setName(RandomStringUtils.randomAlphabetic(10));
    }

    public static User createUserWithoutName() {
        return new User().setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru").setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
