package api.order;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ReceivingUserOrdersClient {
    @Step("Получаем ответ с заказами пользователя")
    public ValidatableResponse getResponseWithUsersOrders(String tokenForRequest) {
        return given()
                .log().all()
                .header("Authorization", "Bearer " + tokenForRequest)
                .get(EndPoints.ordersDataEndPoint)
                .then().log().all();
    }

    @Step("Получаем ответ с заказами неавторизованного пользователя")
    public ValidatableResponse getResponseOfUsersOrdersFromUnauthorizedUser() {
        return given()
                .log().all()
                .get(EndPoints.ordersDataEndPoint)
                .then().log().all();
    }
}
