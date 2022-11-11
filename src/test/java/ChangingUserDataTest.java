import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ChangingUserDataTest extends BaseTest {
    @Override
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        mapWithJson.put("newEmail", "{\"email\": \"" + newUserEmailForRequest + "\"}");
        mapWithJson.put("newPassword", "{\"password\": \"" + newUserPasswordForRequest + "\"}");
        mapWithJson.put("newName", "{\"name\": \"" + newNameOfUserForRequest + "\"}");
    }

    @Test
    @DisplayName("Проверяем, что можно изменить логин у авторизованного пользователя")
    public void changingAuthorizedUserEmail() {
        String jsonForRequest = mapWithJson.get("newEmail");
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
        String jsonForRequest = mapWithJson.get("newPassword");
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
        String jsonForRequest = mapWithJson.get("newName");
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
        String jsonForRequest = mapWithJson.get("newEmail");
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
        String jsonForRequest = mapWithJson.get("newPassword");
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
        String jsonForRequest = mapWithJson.get("newName");
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
