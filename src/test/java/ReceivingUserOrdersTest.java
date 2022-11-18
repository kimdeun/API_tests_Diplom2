import api.order.ReceivingUserOrdersClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ReceivingUserOrdersTest extends BaseTest {
    ReceivingUserOrdersClient receivingUserOrdersClient = new ReceivingUserOrdersClient();

    @Test
    @DisplayName("Проверяем, что можно сделать заказ авторизованным пользователем")
    public void creatingAnOrderWithAuthorizedUser() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());
        bearerToken = response.extract().body().path("accessToken");
        tokenForRequest = bearerToken.substring(7);
        authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                .statusCode(200);

        receivingUserOrdersClient.getResponseWithUsersOrders(tokenForRequest)
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("orders", notNullValue());
    }

    @Test
    @DisplayName("Проверяем, что нельзя сделать заказ авторизованным пользователем")
    public void creatingAnOrderWithoutAuthorizedUser() {
        response = authClient.getResponseForRegisteringNewUser(userEmail, userPassword, nameOfUser)
                .assertThat()
                .body("accessToken", notNullValue());

        receivingUserOrdersClient.getResponseOfUsersOrdersFromUnauthorizedUser()
                .statusCode(401)
                .assertThat().body("success", equalTo(false));
    }
}
