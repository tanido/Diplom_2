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
    private final int expectedStatusCode;
    private final String expectedErrorMessage;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        int statusCode = userClient.createUser(user).extract().statusCode();
        if (statusCode == 200) {
            String accessToken = userClient.createUser(user).extract().path("accessToken");
            userClient.deleteUser(accessToken);
        }
    }

    public CreateUserWithoutFieldsTest(User user, int expectedStatusCode, String expectedErrorMessage) {
        this.user = user;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][] {
                {User.createUserWithoutEmail(), 403, "Email, password and name are required fields"},
                {User.createUserWithoutPassword(), 403, "Email, password and name are required fields"},
                {User.createUserWithoutName(), 403, "Email, password and name are required fields"}
        };
    }

    @Test
    @DisplayName("Создание пользователя не со всеми полями")
    public void createUserWithoutFieldsTest() {
        ValidatableResponse response = userClient.createUser(user);
        int actualStatusCode = response.extract().statusCode();
        String actualErrorMessage = response.extract().path("message");

        assertEquals("Отличный от ожидаемого код ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Неверное сообщение об ошибке", expectedErrorMessage, actualErrorMessage);
    }

}
