import api.auth.AuthClient;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class BaseTest {
    AuthClient authClient = new AuthClient();
    String userEmail = RandomStringUtils.randomAlphanumeric(7) + "@gmail.com";
    String userPassword = RandomStringUtils.randomAlphabetic(7);
    String nameOfUser = RandomStringUtils.randomAlphabetic(7);
    ValidatableResponse response;
    String bearerToken;
    String tokenForRequest;


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @After
    public void tearDown() {
        if (tokenForRequest != null) {
            given()
                    .header("Authorization", "Bearer " + tokenForRequest)
                    .when()
                    .delete("api/auth/user")
                    .then()
                    .statusCode(202);
            authClient.getResponseForAuthorizationNewUser(userEmail, userPassword)
                    .statusCode(401);
        }
    }
}
