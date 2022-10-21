import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class UserAuthorizationTest extends BaseTest {
    @Test
    @DisplayName("Проверяем, что можно авторизоваться под существующим пользователем")
    public void userAuthorizationWithCorrectLoginAndPassword() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверяем, что нельзя авторизоваться с неверным логином")
    public void userAuthorizationWithIncorrectLogin() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        userEmail = "";
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(401);
    }

    @Test
    @DisplayName("Проверяем, что нельзя авторизоваться с неверным логином")
    public void userAuthorizationWithIncorrectPassword() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        userPassword = "";
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(401);
    }
}
