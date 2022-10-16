package ru.yandex.praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;
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

public class LoginCourierTest {
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
    public void loginCourier() {
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
    public void loginWithIncorrectPasswordNegativeTest(){
        CourierRequest randomCourierRequestCorrect = getRandomCourierRequest();
        courierClient.create(randomCourierRequestCorrect)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", Matchers.equalTo(true));

        LoginCourierRequest loginCourierRequest = LoginRequestGenerator.from(randomCourierRequestCorrect);

        id = courierClient.login(loginCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", Matchers.notNullValue())
                .extract()
                .path("id");

        LoginCourierRequest loginCourierRequestWithIncorrectPassword = LoginRequestGenerator.generateLoginRequestWithRequiredValues(randomCourierRequestCorrect.getLogin(), RandomStringUtils.randomAlphabetic(10));

        courierClient.login(loginCourierRequestWithIncorrectPassword)
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithIncorrectLoginNegativeTest(){
        CourierRequest randomCourierRequestCorrect = getRandomCourierRequest();
        courierClient.create(randomCourierRequestCorrect)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", Matchers.equalTo(true));

        LoginCourierRequest loginCourierRequest = LoginRequestGenerator.from(randomCourierRequestCorrect);

        id = courierClient.login(loginCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", Matchers.notNullValue())
                .extract()
                .path("id");


        LoginCourierRequest loginCourierRequestWithIncorrectLogin = LoginRequestGenerator.generateLoginRequestWithRequiredValues(RandomStringUtils.randomAlphabetic(10), randomCourierRequestCorrect.getPassword());

        courierClient.login(loginCourierRequestWithIncorrectLogin)
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithIncorrectLoginAndPasswordNegativeTest(){

        LoginCourierRequest loginCourierRequestWithIncorrectAndPasswordLogin = LoginRequestGenerator.generateLoginRequestWithRequiredValues(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));

        courierClient.login(loginCourierRequestWithIncorrectAndPasswordLogin)
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }
}
