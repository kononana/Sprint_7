import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersListTest {
    OrderAPI orderAPI = new OrderAPI();
    @Before
    public void setup() {
        RestAssured.baseURI =  "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Description("Receive list of orders, not null value")
    public void getOrdersList(){
        Response response = orderAPI.getOrders();
        checkOrdersNotNull(response);
    }

    @Step("Check that order list is not null")
    public void checkOrdersNotNull(Response response){
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }

}
