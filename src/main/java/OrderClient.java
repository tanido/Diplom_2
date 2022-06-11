import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient{

    private static final String ORDER_PATH = "api/orders";

    @Step("Создать заказ")
    public ValidatableResponse createOrder (IngredientsHashes ingredients, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .body(ingredients)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получить заказы пользователя")
    public ValidatableResponse getOrderList(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
}
