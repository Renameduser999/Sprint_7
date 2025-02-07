package courier.login;

import app.BaseTest;
import params.CourierApi;
import params.LoginCourier;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PositiveTest extends BaseTest {

    @Before
    public void setUp() {
        baseTestURL();
        CourierApi.createTestCourier("magic", "4567", "one");
    }

    @Step("Авторизация курьера")
    public Response login() {
        LoginCourier courier = new LoginCourier("magic", "4567");
        Response response = CourierApi.loginCourier(courier);
        return response;
    }

    @Test
    @DisplayName("Успешный логин")
    public void testPositiveLoginCase() {
        Response response = login();
        response
                .then().assertThat().statusCode(SC_OK).body("id", notNullValue());
    }

    @After
    public void deleteCourier() {
        CourierApi.deleteTestCourier("magic", "4567");
    }
}

