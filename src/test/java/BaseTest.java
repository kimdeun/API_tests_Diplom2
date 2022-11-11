import api.auth.AuthClient;
import api.data.ChangingDataClient;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;
import java.util.HashSet;

public class BaseTest {
    AuthClient authClient = new AuthClient();
    ChangingDataClient changingDataClient = new ChangingDataClient();
    String userEmail = RandomStringUtils.randomAlphanumeric(7) + "@gmail.com";
    String userPassword = RandomStringUtils.randomAlphabetic(7);
    String nameOfUser = RandomStringUtils.randomAlphabetic(7);
    ValidatableResponse response;
    String bearerToken;
    String tokenForRequest;
    HashMap<String, String> mapWithJson = new HashMap<>();
    String newUserEmailForRequest = RandomStringUtils.randomAlphanumeric(7) + "@gmail.com";
    String newUserPasswordForRequest = RandomStringUtils.randomAlphabetic(7);
    String newNameOfUserForRequest = RandomStringUtils.randomAlphabetic(7);

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @After
    public void tearDown() {
        if (tokenForRequest != null) {
            changingDataClient.getResponseForDeletingUser(tokenForRequest)
                    .statusCode(202);
        }
    }
}
