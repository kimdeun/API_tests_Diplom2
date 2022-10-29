import api.data.DataAboutIngredientsClient;
import api.order.CreatingAnOrderClient;
import io.qameta.allure.junit4.DisplayName;
import model.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreatingAnOrderTest extends BaseTest {
    DataAboutIngredientsClient dataAboutIngredientsClient = new DataAboutIngredientsClient();
    CreatingAnOrderClient creatingAnOrderClient = new CreatingAnOrderClient();

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

        List<Data> data = dataAboutIngredientsClient.getResponseWithIngredients().getData();
        int sizeOfIngredients = data.size();

        List<String> listOfIngredientsId = new ArrayList<>();
        List<String> listOfIngredients = new ArrayList<>();

        for (int i = 0; i < sizeOfIngredients; i++) {
            int indexOfRandomIngredient = (int) (Math.random() * sizeOfIngredients);
            if (data.get(indexOfRandomIngredient).getType().equals("bun") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("bun")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            } else if (data.get(indexOfRandomIngredient).getType().equals("sauce") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("sauce")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            } else if (data.get(indexOfRandomIngredient).getType().equals("main") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("main")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            }
        }

        String[] stringArray = listOfIngredientsId.toArray(String[]::new);

        creatingAnOrderClient.getResponseForCreatingAnOrder(tokenForRequest, "{\"ingredients\": [\"" + stringArray[0] + "\", \"" + stringArray[1] + "\", \"" + stringArray[2] + "\"]}")
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверяем, что можно сделать заказ не авторизованным пользователем")
    public void creatingAnOrderWithoutUserAuthorization() {
        List<Data> data = dataAboutIngredientsClient.getResponseWithIngredients().getData();
        int sizeOfIngredients = data.size();

        List<String> listOfIngredientsId = new ArrayList<>();
        List<String> listOfIngredients = new ArrayList<>();

        for (int i = 0; i < sizeOfIngredients; i++) {
            int indexOfRandomIngredient = (int) (Math.random() * sizeOfIngredients);
            if (data.get(indexOfRandomIngredient).getType().equals("bun") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("bun")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            } else if (data.get(indexOfRandomIngredient).getType().equals("sauce") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("sauce")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            } else if (data.get(indexOfRandomIngredient).getType().equals("main") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("main")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            }
        }

        String[] stringArray = listOfIngredientsId.toArray(String[]::new);

        creatingAnOrderClient.getResponseForCreatingAnOrderWithoutUserAuthorization("{\"ingredients\": [\"" + stringArray[0] + "\", \"" + stringArray[1] + "\", \"" + stringArray[2] + "\"]}")
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

        List<Data> data = dataAboutIngredientsClient.getResponseWithIngredients().getData();
        int sizeOfIngredients = data.size();

        List<String> listOfIngredientsId = new ArrayList<>();
        List<String> listOfIngredients = new ArrayList<>();

        for (int i = 0; i < sizeOfIngredients; i++) {
            int indexOfRandomIngredient = (int) (Math.random() * sizeOfIngredients);
            if (data.get(indexOfRandomIngredient).getType().equals("bun") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("bun")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            } else if (data.get(indexOfRandomIngredient).getType().equals("sauce") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("sauce")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            } else if (data.get(indexOfRandomIngredient).getType().equals("main") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("main")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            }
        }

        String[] arrayOfIngredientsId = listOfIngredientsId.toArray(String[]::new);

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
