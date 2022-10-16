package ru.yandex.praktikum.courier;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.generator.LoginRequestGenerator;
import ru.yandex.praktikum.pojo.CourierRequest;
import ru.yandex.praktikum.pojo.DeleteCourierRequest;
import ru.yandex.praktikum.pojo.LoginCourierRequest;

import java.util.Random;

import static ru.yandex.praktikum.generator.CourierRequestGenerator.getRandomCourierRequest;

public class DeleteCourierTest {
    private CourierClient courierClient;
    private Integer id;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }


    @Test
    public void deleteCourierPositiveTest(){
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

        DeleteCourierRequest deleteCourierRequest = new DeleteCourierRequest();
        deleteCourierRequest.setId(id.toString());
        courierClient.delete(deleteCourierRequest, id)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("ok", Matchers.equalTo(true));
    }

    @Test
    public void deleteCourierWithIDThatNotExistsNegativeTest(){
        Random random = new Random();
        Integer num = random.nextInt();

        DeleteCourierRequest deleteCourierRequest = new DeleteCourierRequest();
        deleteCourierRequest.setId(num.toString());
        courierClient.delete(deleteCourierRequest, num)
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Курьера с таким id нет."));

    }

    @Test
    public void deleteCourierWithoutIDNegative(){
        DeleteCourierRequest deleteCourierRequest = new DeleteCourierRequest();
        courierClient.deleteWithoutIDNegative(deleteCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.equalTo("Недостаточно данных для удаления курьера"));
    }

}
