package api.order;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CreatingAnOrderClient {
    @Step("Получаем ответ после создания заказа")
    public ValidatableResponse getResponseForCreatingAnOrder(String tokenForRequest, HashMap<String, String[]> hashMap) {
        return given()
                .log().all()
                .header("Authorization", "Bearer " + tokenForRequest)
                .header("Content-type", "application/json")
                .body(hashMap)
                .post(EndPoints.ordersDataEndPoint)
                .then().log().all();
    }

    @Step("Получаем ответ после создания заказа неавторизованным пользователем")
    public ValidatableResponse getResponseForCreatingAnOrderWithoutUserAuthorization(HashMap<String, String[]> hashMap) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(hashMap)
                .post(EndPoints.ordersDataEndPoint)
                .then().log().all();
    }
}