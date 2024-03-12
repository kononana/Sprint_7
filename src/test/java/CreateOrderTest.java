import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
ArrayList<String> colors;

public CreateOrderTest(ArrayList<String> colors) {
    this.colors = colors;
}
@Parameterized.Parameters
public static Object[][] colorsData() {
    return new Object[][] {
            {new ArrayList<>(List.of("Black"))},
            {new ArrayList<>(List.of("Grey"))},
            {new ArrayList<>(List.of("Grey", "Black"))},
            {new ArrayList<>(List.of())},
    };
}
OrderCreate orderCreate = new OrderCreate("Nina", "Froost", "Lenina, 23", 34, "+7 800 235 23 23", 5, "2024-05-08", "no comments");
OrderAPI orderAPI = new OrderAPI();
    @Before
    public void setup() {
        RestAssured.baseURI =  "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Description("create order with different colors: success, status code 201 and contains track number")
    public void createNewOrder() {
        orderCreate.setColor(colors);
        Response response = createOrder(orderCreate);
        checkOrdersNotNull(response);
    }

    @Step ("Create new order")
    public Response createOrder(OrderCreate orderCreate) {
        Response response = orderAPI.createOrder(orderCreate);
        return response;
    }

    @Step("Check that order list is not null")
    public void checkOrdersNotNull(Response response){
        response
                .then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }

}






