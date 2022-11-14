package api.data;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ChangingDataClient {
    @Step("Получаем ответ с текущими данными пользователя")
    public ValidatableResponse getResponseWithActualData(String tokenForRequest) {
        return given()
                .log().all()
                .header("Authorization", "Bearer " + tokenForRequest)
                .get(EndPoints.userDataEndPoint)
                .then().log().all();
    }

    @Step("Обновляем данные пользователя")
    public ValidatableResponse getResponseForUpdatingData(String tokenForRequest, HashMap<String, String> hashMap) {
        return given()
                .log().all()
                .header("Authorization", "Bearer " + tokenForRequest)
                .header("Content-type", "application/json")
                .body(hashMap)
                .patch(EndPoints.userDataEndPoint)
                .then().log().all();
    }

    @Step("Обновляем данные пользователя без авторизации")
    public ValidatableResponse getResponseForUpdatingDataWithoutAuthorization(HashMap<String, String> hashMap) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(hashMap)
                .patch(EndPoints.userDataEndPoint)
                .then().log().all();
    }

    @Step("Удаляем пользователя")
    public ValidatableResponse getResponseForDeletingUser(String tokenForRequest) {
        return given()
                .log().all()
                .header("Authorization", "Bearer " + tokenForRequest)
                .when()
                .delete(EndPoints.userDataEndPoint)
                .then()
                .log().all();
    }
}
