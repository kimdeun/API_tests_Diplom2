import api.data.DataAboutIngredientsClient;
import api.order.CreatingAnOrderClient;
import io.qameta.allure.junit4.DisplayName;
import model.Ingredients;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreatingAnOrderTest extends BaseTest {
    CreatingAnOrderClient creatingAnOrderClient = new CreatingAnOrderClient();
    Ingredients ingredients = new Ingredients();

    @Test
    @DisplayName("Проверяем, что можно сделать заказ авторизованным пользователем")
    public void creatingAnOrderWithAuthorizedUser() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);

        String[] arrayOfIngredientsId = ingredients.creatingListOfIngredientsId().toArray(String[]::new);

        creatingAnOrderClient.getResponseForCreatingAnOrder(tokenForRequest, "{\"ingredients\": [\"" + arrayOfIngredientsId[0] + "\", \"" + arrayOfIngredientsId[1] + "\", \"" + arrayOfIngredientsId[2] + "\"]}")
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверяем, что можно сделать заказ не авторизованным пользователем")
    public void creatingAnOrderWithoutUserAuthorization() {
        String[] arrayOfIngredientsId = ingredients.creatingListOfIngredientsId().toArray(String[]::new);

        creatingAnOrderClient.getResponseForCreatingAnOrderWithoutUserAuthorization("{\"ingredients\": [\"" + arrayOfIngredientsId[0] + "\", \"" + arrayOfIngredientsId[1] + "\", \"" + arrayOfIngredientsId[2] + "\"]}")
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверяем, что можно сделать заказ c ингридиентами")
    public void creatingAnOrderWithIngredients() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);

        String[] arrayOfIngredientsId = ingredients.creatingListOfIngredientsId().toArray(String[]::new);

        creatingAnOrderClient.getResponseForCreatingAnOrder(tokenForRequest, "{\"ingredients\": [\"" + arrayOfIngredientsId[0] + "\", \"" + arrayOfIngredientsId[1] + "\", \"" + arrayOfIngredientsId[2] + "\"]}")
                .assertThat().body("order.ingredients._id[0]", equalTo(arrayOfIngredientsId[0]))
                .assertThat().body("order.ingredients._id[1]", equalTo(arrayOfIngredientsId[1]))
                .assertThat().body("order.ingredients._id[2]", equalTo(arrayOfIngredientsId[2]));
    }

    @Test
    @DisplayName("Проверяем, что нельзя сделать заказ без ингридиентов")
    public void creatingAnOrderWithoutIngredients() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);

        creatingAnOrderClient.getResponseForCreatingAnOrder(tokenForRequest, "{\"ingredients\": []}")
                .assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Проверяем, что нельзя сделать заказ с неверным хешем ингридиентов")
    public void creatingAnOrderWithIncorrectHashOfIngredients() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);

        creatingAnOrderClient.getResponseForCreatingAnOrder(tokenForRequest, "{\"ingredients\": [\"11111\", \"22222\", \"33333\"]}")
                .assertThat().statusCode(500);
    }
}
