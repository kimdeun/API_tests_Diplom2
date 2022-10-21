import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class RegisteringAUserTest extends BaseTest {

    @Test
    @DisplayName("Проверяем, что пользователя можно зарегистрировать")
    public void registeringAUser() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
    }

    @Test
    @DisplayName("Проверяем, что создать существующего пользователя нельзя")
    public void registeringAnExistingUser() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("success", equalTo(false))
                .and().statusCode(403);
    }

    @Test
    @DisplayName("Проверяем, что нельзя создать пользователя без почты")
    public void registeringAUserWithoutEmail() {
        userEmail = "";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Проверяем, что нельзя создать пользователя без пароля")
    public void registeringAUserWithoutPassword() {
        userPassword = "";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Проверяем, что нельзя создать пользователя без имени")
    public void registeringAUserWithoutName() {
        nameOfUser = "";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("success", equalTo(false));
    }
}
