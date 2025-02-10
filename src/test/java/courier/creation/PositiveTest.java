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


import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class PositiveTest extends BaseTest {
    private String login;
    private String password;
    private String firstName;


    public PositiveTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"pechkin", "1234", "Ivan"},
                {"pechkin2", "1234", "Sany"}
        };
    }

    @Before
    public void setUp() {
        baseTestURL();
    }

    @Step("Регистрация")
    public Response signIn() {
        CreateCourier courier = new CreateCourier(login, password, firstName);
        Response response = CourierApi.createCourier(courier);
        return response;
    }

    @Test
    @DisplayName("Создание курьера")
    public void createPositiveTest() {
        Response response = signIn();
        response
                .then().assertThat().statusCode(SC_CREATED).body("ok", equalTo(true));
    }

    @After
    public void deleteCourier() {
        CourierApi.deleteTestCourier(login, password);
    }
}
