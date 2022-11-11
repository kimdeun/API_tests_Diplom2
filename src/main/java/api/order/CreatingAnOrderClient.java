package api.order;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CreatingAnOrderClient {
    @Step("Получаем ответ после создания заказа")
    public ValidatableResponse getResponseForCreatingAnOrder(String tokenForRequest, String jsonForRequest) {
        return given()
                .log().all()
                .header("Authorization", "Bearer " + tokenForRequest)
                .header("Content-type", "application/json")
                .body(jsonForRequest)
                .post(EndPoints.ordersDataEndPoint)
                .then().log().all();
    }

    @Step("Получаем ответ после создания заказа неавторизованным пользователем")
    public ValidatableResponse getResponseForCreatingAnOrderWithoutUserAuthorization(String jsonForRequest) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jsonForRequest)
                .post(EndPoints.ordersDataEndPoint)
                .then().log().all();
    }
}