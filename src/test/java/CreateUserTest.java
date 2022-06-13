import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CreateUserTest {

    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
    }

    @After
    public void tearDown() {
        int statusCode = userClient.loginUser(UserCredentials.from(user)).extract().statusCode();
        if (statusCode == 200) {
            String accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Создание пользователя")
    public void userSuccessfullyCreatedTest() {
        ValidatableResponse response = userClient.createUser(user);
        int statusCode = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");

        assertEquals("Отличный от ожидаемого код ответа", 200, statusCode);
        assertTrue("Пользователь не создан", isUserCreated);
    }

    @Test
    @DisplayName("Создание идентичного пользователя")
    public void cannotCreateSameUserTest() {
        userClient.createUser(user);
        ValidatableResponse response = userClient.createUser(user);
        int statusCode = response.extract().statusCode();
        boolean isUserWithSameDataNotCreated = response.extract().path("message").equals("User already exists");

        assertEquals("Отличный от ожидаемого код ответа", 403, statusCode);
        assertTrue("Создан пользователь с существующими данными", isUserWithSameDataNotCreated);
    }
}
