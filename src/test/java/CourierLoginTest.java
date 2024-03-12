import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;


public class CourierLoginTest {
    public final String login = "kon31";
    public final String password = "12345678";
    public final String firstName = "Oleg";

    LoginCourier loginCourier = new LoginCourier(login, password);
    CreateCourier createCourier = new CreateCourier(login, password, firstName);
    CourierAPI courierAPI = new CourierAPI();

    @Before
    public void setup() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        courierAPI.createNewCourierAPI(createCourier);
    }

    @After
    public void tearDown() {
        try {
            int courierId = courierIdLogin(loginCourier);
            deleteCourier(courierId);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    @Test
    @Description("successful login. Status: 200 and return id")
    public void loginOk() {
        Response response = courierLogin(loginCourier);
        checkIdNotNull(response);
    }

    @Test
    @Description("login with wrong password")
    public void loginWrongPassword() {
        loginCourier.setPassword("wrong");
        Response response = courierLogin(loginCourier);
        checkResponseMessageToExpected(response, 404, "Учетная запись не найдена");
    }

    @Test
    @Description("login with wrong login")
    public void loginWrongLogin() {
        loginCourier.setLogin("wrong");
        Response response = courierLogin(loginCourier);
        checkResponseMessageToExpected(response, 404, "Учетная запись не найдена");
    }

    @Test
    @Description("login without password")
    public void loginWithoutPassword() {
        loginCourier.setPassword("");
        Response response = courierLogin(loginCourier);
        checkResponseMessageToExpected(response, 400, "Недостаточно данных для входа");
    }

    @Test
    @Description("login without login")
    public void loginWithoutLogin() {
        loginCourier.setLogin("");
        Response response = courierLogin(loginCourier);
        checkResponseMessageToExpected(response, 400, "Недостаточно данных для входа");
    }


    // шаг "Авторизация курьера":
    @Step("Login courier")
    public Response courierLogin(LoginCourier loginCourier){
        Response response = courierAPI.loginCourierAPI(loginCourier);
        return response;
    }
 // Шаг "создать курьера"
    @Step("Create courier")
    public Response courierCreate(CreateCourier createCourier){
        Response response = courierAPI.createNewCourierAPI(createCourier);
        return response;
    }

    // Шаг "Получить id курьера
    @Step("return  courier id")
    public int courierIdLogin(LoginCourier loginCourier){
        courierAPI.loginCourierAPI(loginCourier);
        int id = courierAPI.getCourierId(loginCourier);
        return id;
    }

    // шаг "Удалить курьера":
    @Step("Delete courier by  id")
    public void deleteCourier(int courierId){
         courierAPI.deleteCourier(courierId);

    }
    @Step("Check id response message is equal to expected message")
    public void checkResponseMessageToExpected(Response response, int statusCode, String someText) {
        response
                .then()
                .statusCode(statusCode)
                .and()
                .assertThat()
                .body("message", is(someText));

    }

    @Step("Check that courier id is not null")
    public void checkIdNotNull(Response response){
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }


}

