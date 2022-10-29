package api.data;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ChangingDataClient {
    @Step("Получаем ответ с текущими данными пользователя")
    public ValidatableResponse getResponseWithActualData(String tokenForRequest) {
        return given()
                .log().all()
                .header("Authorization", "Bearer " + tokenForRequest)
                .get("/api/auth/user")
                .then().log().all();
    }
    @Step("Обновляем данные пользователя")
    public ValidatableResponse getResponseForUpdatingData(String tokenForRequest, String jsonForRequest) {
        return given()
                .log().all()
                .header("Authorization", "Bearer " + tokenForRequest)
                .header("Content-type", "application/json")
                .body(jsonForRequest)
                .patch("/api/auth/user")
                .then().log().all();
    }

    @Step("Обновляем данные пользователя без авторизации")
    public ValidatableResponse getResponseForUpdatingDataWithoutAuthorization(String jsonForRequest) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jsonForRequest)
                .patch("/api/auth/user")
                .then().log().all();
    }
}
