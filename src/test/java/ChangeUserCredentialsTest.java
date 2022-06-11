import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ChangeUserCredentialsTest {

    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
        userClient.createUser(user);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Изменение данных авторизованного пользователя")
    public void changeUserCredentialsTest() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        ValidatableResponse response = userClient.changeUser(UserCredentialsChange.credentialsChange(), accessToken);
        int statusCode = response.extract().statusCode();
        boolean isCredentialChange = response.extract().path("success");

        assertEquals("Отличный от ожидаемого код ответа", 200, statusCode);
        assertTrue("Пользователь не залогинен", isCredentialChange);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void changeUserCredentialsWithoutAuthorizationTest() {
        //accessToken - для удаления пользователя в tearDown
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        ValidatableResponse response = userClient.changeUser(UserCredentialsChange.credentialsChange(), " ");
        int statusCode = response.extract().statusCode();
        boolean isCredentialChange = response.extract().path("message").equals("You should be authorised");

        assertEquals("Отличный от ожидаемого код ответа", 401, statusCode);
        assertTrue("Неверное сообщение об ошибке или пользователь залогинен", isCredentialChange);
    }
}
