package courier.creation;

import app.BaseTest;
import params.CourierApi;
import params.CreateCourier;
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
    private String firstName;
    private int code;
    private String message;


    public NegativeTest(String login, String password, String firstName, int code, String message) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.code = code;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"best", "1234", "Dima", 409, "Этот логин уже используется. Попробуйте другой."},
                {"best", "", "Dima", 400, "Недостаточно данных для создания учетной записи"},
                {"", "1234", "Dima", 400, "Недостаточно данных для создания учетной записи"},
                {"", "", "Dima", 400, "Недостаточно данных для создания учетной записи"}
        };
    }

    @Before
    public void setUp() {
        baseTestURL();
        CourierApi.createCourier(new CreateCourier("best", "1234", "Dima"));
    }

    @Step("Регистрация с неполными/повторяющимися данными")
    public Response signIn() {
        CreateCourier courier = new CreateCourier(login, password, firstName);
        Response response = CourierApi.createCourier(courier);
        return response;
    }

    @Test
    @DisplayName("Создание курьера - негативный кейс")
    public void createNegativeTest() {
        Response response = signIn();
        response
                .then().assertThat().statusCode(code).body("message", equalTo(message));

    }

    @After
    public void deleteCourier() {

        CourierApi.deleteTestCourier("best", "1234");
    }
}
