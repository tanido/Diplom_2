import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class GetListOfOrdersTest {

    private UserClient userClient;
    private OrderClient orderClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = User.getRandom();
        userClient.createUser(user);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Получение списка заказов авторизованного пользователя")
    public void positiveGetOrdersTest() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        ValidatableResponse response = orderClient.getOrderList(accessToken);
        int statusCode = response.extract().statusCode();
        boolean isListDisplayed = response.extract().path("success");

        assertEquals("Отличный от ожидаемого код ответа", 200, statusCode);
        assertTrue("Список заказов не получен", isListDisplayed);
    }

    @Test
    @DisplayName("Получение списка заказов неавторизованного пользователя")
    public void getOrdersWithoutAuthorizationTest() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        ValidatableResponse response = orderClient.getOrderList(" ");
        int statusCode = response.extract().statusCode();
        boolean isListDisplayed = response.extract().path("message").equals("You should be authorised");

        assertEquals("Отличный от ожидаемого код ответа", 401, statusCode);
        assertTrue("Неверное сообщение об ошибке или список заказов получен", isListDisplayed);
    }
}
