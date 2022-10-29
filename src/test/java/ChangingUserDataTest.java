import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ChangingUserDataTest extends BaseTest {
    @Test
    @DisplayName("Проверяем, что можно изменить логин у авторизованного пользователя")
    public void changingAuthorizedUserEmail() {
        String newUserEmailForRequest = RandomStringUtils.randomAlphanumeric(7) + "@gmail.com";
        String jsonForRequest = "{\"email\": " + "\"" + newUserEmailForRequest + "\"" + "}";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        String currentEmail = changingDataClient.getResponseWithActualData(tokenForRequest)
                .extract().body().path("user.email");
        changingDataClient.getResponseForUpdatingData(tokenForRequest, jsonForRequest)
                .statusCode(200);
        String newEmail = changingDataClient.getResponseWithActualData(tokenForRequest)
                .extract().body().path("user.email");
        Assert.assertNotEquals(currentEmail, newEmail);
    }

    @Test
    @DisplayName("Проверяем, что можно изменить пароль у авторизованного пользователя")
    public void changingAuthorizedUserPassword() {
        String newUserPasswordForRequest = RandomStringUtils.randomAlphanumeric(7);
        String jsonForRequest = "{\"password\": " + "\"" + newUserPasswordForRequest + "\"" + "}";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        changingDataClient.getResponseForUpdatingData(tokenForRequest, jsonForRequest)
                .statusCode(200);
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(401);
        authClient.getResponseForAuthorizationNewUser(userEmail, newUserPasswordForRequest)
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверяем, что можно изменить имя у авторизованного пользователя")
    public void changingAuthorizedUserName() {
        String newUserNameForRequest = RandomStringUtils.randomAlphanumeric(7);
        String jsonForRequest = "{\"name\": " + "\"" + newUserNameForRequest + "\"" + "}";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        String currentName = changingDataClient.getResponseWithActualData(tokenForRequest)
                .extract().body().path("user.name");
        changingDataClient.getResponseForUpdatingData(tokenForRequest, jsonForRequest)
                .statusCode(200);
        String newName = changingDataClient.getResponseWithActualData(tokenForRequest)
                .extract().body().path("user.name");
        Assert.assertNotEquals(currentName, newName);
    }

    @Test
    @DisplayName("Проверяем, что нельзя изменить логин у НЕ авторизованного пользователя")
    public void changingUserLogin() {
        String newUserLoginForRequest = RandomStringUtils.randomAlphanumeric(7) + "@gmail.com";
        String jsonForRequest = "{\"email\": " + "\"" + newUserLoginForRequest + "\"" + "}";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        String currentEmail = changingDataClient.getResponseWithActualData(tokenForRequest)
                .extract().body().path("user.email");
        changingDataClient.getResponseForUpdatingDataWithoutAuthorization(jsonForRequest)
                .statusCode(401).assertThat().body("success", equalTo(false));
        String newEmail = changingDataClient.getResponseWithActualData(tokenForRequest)
                .extract().body().path("user.email");
        Assert.assertEquals(currentEmail, newEmail);
    }

    @Test
    @DisplayName("Проверяем, что нельзя изменить пароль у НЕ авторизованного пользователя")
    public void changingUserPassword() {
        String newUserPasswordForRequest = RandomStringUtils.randomAlphanumeric(7);
        String jsonForRequest = "{\"password\": " + "\"" + newUserPasswordForRequest + "\"" + "}";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        changingDataClient.getResponseForUpdatingDataWithoutAuthorization(jsonForRequest)
                .statusCode(401).assertThat().body("success", equalTo(false));
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);
        authClient.getResponseForAuthorizationNewUser(userEmail, newUserPasswordForRequest)
                .statusCode(401);
    }

    @Test
    @DisplayName("Проверяем, что нельзя изменить имя у НЕ авторизованного пользователя")
    public void changingUserName() {
        String newUserNameForRequest = RandomStringUtils.randomAlphanumeric(7);
        String jsonForRequest = "{\"name\": " + "\"" + newUserNameForRequest + "\"" + "}";
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        String currentName = changingDataClient.getResponseWithActualData(tokenForRequest)
                .extract().body().path("user.name");
        changingDataClient.getResponseForUpdatingDataWithoutAuthorization(jsonForRequest)
                .statusCode(401).assertThat().body("success", equalTo(false));
        String newName = changingDataClient.getResponseWithActualData(tokenForRequest)
                .extract().body().path("user.name");
        Assert.assertEquals(currentName, newName);
    }
}
