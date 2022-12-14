package api.data;

import constants.EndPoints;
import io.qameta.allure.Step;
import model.Ingredients;

import static io.restassured.RestAssured.given;

public class DataAboutIngredientsClient {
    @Step("Получаем ответ с данными об ингредиентах")
    public Ingredients getResponseWithIngredients() {
        return given()
                .log().all()
                .get(EndPoints.ingredientsDataEndPoint)
                .body().as(Ingredients.class);
    }
}
