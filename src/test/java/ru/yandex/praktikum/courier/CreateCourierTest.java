package ru.yandex.praktikum.courier;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.generator.LoginRequestGenerator;
import ru.yandex.praktikum.pojo.CourierRequest;
import ru.yandex.praktikum.pojo.DeleteCourierRequest;
import ru.yandex.praktikum.pojo.LoginCourierRequest;

import static ru.yandex.praktikum.generator.CourierRequestGenerator.getRandomCourierRequest;
import static ru.yandex.praktikum.generator.CourierRequestGenerator.getRandomCourierRequestWithoutLoginValue;


public class CreateCourierTest {
    private CourierClient courierClient;
    private Integer id;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        if(id != null) {
            DeleteCourierRequest deleteCourierRequest = new DeleteCourierRequest();
            deleteCourierRequest.setId(id.toString());
            courierClient.delete(deleteCourierRequest, id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .and()
                    .body("ok", Matchers.equalTo(true));
        }
    }

    @Test
    public void createCourier() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", Matchers.equalTo(true));

        LoginCourierRequest loginCourierRequest = LoginRequestGenerator.from(randomCourierRequest);

        id = courierClient.login(loginCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", Matchers.notNullValue())
                .extract()
                .path("id");
    }

    @Test
    public void createTwoSameCourierNegativeTest() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", Matchers.equalTo(true));

        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", Matchers.equalTo("Этот логин уже используется"));

        LoginCourierRequest loginCourierRequest = LoginRequestGenerator.from(randomCourierRequest);

        id = courierClient.login(loginCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", Matchers.notNullValue())
                .extract()
                .path("id");

    }

    @Test
    public void createCourierWithMissingMandatoryParametersNegativeTest() {
        CourierRequest randomCourierRequest = getRandomCourierRequestWithoutLoginValue();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    public void createTwoCouriersWithTheSameLoginNegativeTest(){
        CourierRequest randomCourierRequestFirst = getRandomCourierRequest();
        courierClient.create(randomCourierRequestFirst)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", Matchers.equalTo(true));

        CourierRequest randomCourierRequestSecond = getRandomCourierRequest();
        randomCourierRequestSecond.setLogin(randomCourierRequestFirst.getLogin());

        courierClient.create(randomCourierRequestSecond)
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", Matchers.equalTo("Этот логин уже используется"));

        LoginCourierRequest loginCourierRequest = LoginRequestGenerator.from(randomCourierRequestFirst);

        id = courierClient.login(loginCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", Matchers.notNullValue())
                .extract()
                .path("id");
    }

}
