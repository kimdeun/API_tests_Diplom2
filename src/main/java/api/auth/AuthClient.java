package api.auth;

import constants.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.User;

import static io.restassured.RestAssured.given;

public class AuthClient {

    @Step("Получаем ответ после регистрации пользователя")
    public ValidatableResponse getResponseForRegisteringNewUser(String email, String password, String name) {
        User user = new User(email, password, name);
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(EndPoints.registerEndPoint)
                .then().log().all();
    }

    @Step("Получаем ответ после авторизации пользователя")
    public ValidatableResponse getResponseForAuthorizationNewUser(String email, String password) {
        User user = new User(email, password);
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(user)
                .post(EndPoints.loginEndPoint)
                .then().log().all();
    }
}
