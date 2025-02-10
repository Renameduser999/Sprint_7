package order;

import app.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static params.OrderApi.listOrders;

public class TestListOrders extends BaseTest {


    @Before
    public void setUp() {
        baseTestURL();
    }


    @Test
    @DisplayName("Проверка получения списка заказов")
    public void testListOrders() {
        Response response = listOrders();
        response
                .then().statusCode(SC_OK).assertThat().body("orders.status", hasItems(0));
    }
}

