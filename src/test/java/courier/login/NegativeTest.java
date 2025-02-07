package courier.login;

import app.BaseTest;
import params.CourierApi;
import params.CreateCourier;
import params.LoginCourier;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class NegativeTest extends BaseTest {
    private String login;
    private String password;
    private int code;
    private String message;

    public NegativeTest(String login, String password, int code, String message) {
        this.login = login;
        this.password = password;
        this.code = code;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"", "4567", 400, "Недостаточно данных для входа"},
                {"magic", "", 400, "Недостаточно данных для входа"},
                {"", "", 400, "Недостаточно данных для входа"},
                {"magical", "4567", 404, "Учетная запись не найдена"},
                {"magic", "6789", 404, "Учетная запись не найдена"}
        };
    }

    @Before
    public void setUp() {
        baseTestURL();
        CourierApi.createCourier(new CreateCourier("magic", "4567", "one"));
    }

    @Step("Отправка логина с некорректными данными")
    public Response login() {
        LoginCourier courier = new LoginCourier(login, password);
        Response response = CourierApi.loginCourier(courier);
        return response;
    }

    @Test
    @DisplayName("Логин курьера - негативный кейс")
    public void testNegativeLoginCase() {
        Response response = login();
        response
                .then().assertThat().statusCode(code).body("message", equalTo(message));
    }

    @After
    public void deleteCourier() {
        CourierApi.deleteTestCourier("magic", "4567");
    }
}
