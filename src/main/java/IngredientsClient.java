import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient{

    private static final String INGREDIENT_PATH = "api/ingredients";

    @Step("Получить список ингредиентов")
    public ValidatableResponse getIngredientsList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENT_PATH)
                .then();
    }
}
