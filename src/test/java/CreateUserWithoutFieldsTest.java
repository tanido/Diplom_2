import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class CreateUserWithoutFieldsTest {

    private UserClient userClient;
    private final User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        int statusCode = userClient.loginUser(UserCredentials.from(user)).extract().statusCode();
        if (statusCode == 200) {
            String accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
            userClient.deleteUser(accessToken);
        }
    }

    public CreateUserWithoutFieldsTest(User user) {
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][] {
                {User.createUserWithoutEmail()},
                {User.createUserWithoutPassword()},
                {User.createUserWithoutName()}
        };
    }

    @Test
    @DisplayName("Создание пользователя не со всеми полями")
    public void createUserWithoutFieldsTest() {
        ValidatableResponse response = userClient.createUser(user);
        int actualStatusCode = response.extract().statusCode();
        int expectedStatusCode = 403;
        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Email, password and name are required fields";

        assertEquals("Отличный от ожидаемого код ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Неверное сообщение об ошибке", expectedErrorMessage, actualErrorMessage);
    }

}
