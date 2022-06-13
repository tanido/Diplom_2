import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class LoginUserTest {

    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Логин пользователя с валидными данными")
    public void positiveUserLoginTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        ValidatableResponse response = userClient.loginUser(UserCredentials.from(user));
        int statusCode = response.extract().statusCode();
        boolean isUserLoggedIn = response.extract().path("success");

        assertEquals("Отличный от ожидаемого код ответа", 200, statusCode);
        assertTrue("Пользователь не залогинен", isUserLoggedIn);
    }

    @Test
    @DisplayName("Логин пользователя с невалидным email")
    public void userLoginWithWrongEmailTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        ValidatableResponse response = userClient.loginUser(UserCredentials.userLoginWithWrongEmail(user));
        int statusCode = response.extract().statusCode();
        boolean isUserLoggedIn = response.extract().path("message").equals("email or password are incorrect");

        assertEquals("Отличный от ожидаемого код ответа", 401, statusCode);
        assertTrue("Неверное сообщение об ошибке или пользователь залогинен", isUserLoggedIn);
    }

    @Test
    @DisplayName("Логин пользователя с невалидным password")
    public void userLoginWithWrongPasswordTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        ValidatableResponse response = userClient.loginUser(UserCredentials.userLoginWithWrongPassword(user));
        int statusCode = response.extract().statusCode();
        boolean isUserLoggedIn = response.extract().path("message").equals("email or password are incorrect");

        assertEquals("Отличный от ожидаемого код ответа", 401, statusCode);
        assertTrue("Неверное сообщение об ошибке или пользователь залогинен", isUserLoggedIn);
    }
}
