import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CreateOrderTest {

    private UserClient userClient;
    private OrderClient orderClient;
    private IngredientsClient ingredientsClient;
    private User user;
    private String accessToken;
    List<String> ingredients = new ArrayList<>();

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        ingredientsClient = new IngredientsClient();
        user = User.getRandom();
        userClient.createUser(user);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Создание заказа с валидными данными")
    public void positiveCreateOrderTest() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        ingredients = ingredientsClient.getIngredientsList().extract().path("data._id");
        IngredientsHashes oneIngredient = new IngredientsHashes(ingredients.get(0));
        ValidatableResponse response = orderClient.createOrder(oneIngredient, accessToken);
        int statusCode = response.extract().statusCode();
        boolean isOrderCreated = response.extract().path("success");

        assertEquals("Отличный от ожидаемого код ответа", 200, statusCode);
        assertTrue("Заказ не создан", isOrderCreated);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        ingredients = ingredientsClient.getIngredientsList().extract().path("data._id");
        IngredientsHashes oneIngredient = new IngredientsHashes(ingredients.get(0));
        ValidatableResponse response = orderClient.createOrder(oneIngredient, " ");
        int statusCode = response.extract().statusCode();
        boolean isOrderCreated = response.extract().path("success");

        assertEquals("Отличный от ожидаемого код ответа", 200, statusCode);
        assertTrue("Заказ не создан", isOrderCreated);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        ingredients = ingredientsClient.getIngredientsList().extract().path("data._id");
        IngredientsHashes oneIngredient = new IngredientsHashes(null);
        ValidatableResponse response = orderClient.createOrder(oneIngredient, accessToken);
        int statusCode = response.extract().statusCode();
        boolean isOrderCreated = response.extract().path("message").equals("Ingredient ids must be provided");

        assertEquals("Отличный от ожидаемого код ответа", 400, statusCode);
        assertTrue("Неверное сообщение об ошибке или заказ создан", isOrderCreated);
    }

    @Test
    @DisplayName("Создание заказа с невалидным хешом ингредиента")
    public void createOrderWithInvalidHash() {
        accessToken = userClient.loginUser(UserCredentials.from(user)).extract().path("accessToken");
        IngredientsHashes oneIngredient = new IngredientsHashes(RandomStringUtils.randomAlphabetic(10));
        ValidatableResponse response = orderClient.createOrder(oneIngredient, accessToken);
        int statusCode = response.extract().statusCode();

        assertEquals("Отличный от ожидаемого код ответа", 500, statusCode);
    }
}
