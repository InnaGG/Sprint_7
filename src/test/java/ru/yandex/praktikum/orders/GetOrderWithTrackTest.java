package ru.yandex.praktikum.orders;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.OrdersClient;
import ru.yandex.praktikum.pojo.GetOrderRequest;

import java.util.Random;

import static ru.yandex.praktikum.generator.GetOrderRequestGenerator.generateGetOrderRequest;
import static ru.yandex.praktikum.generator.GetOrderRequestGenerator.generateGetOrderRequestWithoutTrackNegative;

public class GetOrderWithTrackTest {
    private StepsForOrderAPI stepsForOrderAPI;
    private OrdersClient ordersClient;

    @Before
    public void setUp() {
        stepsForOrderAPI = new StepsForOrderAPI();
        ordersClient = new OrdersClient();
    }

    @Test
    public void getOrderByTrackNumberPositive(){
        Integer track = stepsForOrderAPI.createAnOrderAndReturnATrack();
        GetOrderRequest getOrderRequest = generateGetOrderRequest(track);
        ordersClient.getOrderByTrack(getOrderRequest, track)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("order", Matchers.notNullValue());
    }

    @Test
    public void getOrderWithoutTrackNumberNegative(){
        GetOrderRequest getOrderRequest = generateGetOrderRequestWithoutTrackNegative();
        ordersClient.getOrderByTrackWithoutTrackNegative(getOrderRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.equalTo("Недостаточно данных для поиска"));
    }

    @Test
    public void getOrderWithIncorrectTrackNumberNegative(){
        int track = new Random().nextInt();
        GetOrderRequest getOrderRequest = generateGetOrderRequest(track);
        ordersClient.getOrderByTrack(getOrderRequest, track)
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Заказ не найден"));
    }
}
