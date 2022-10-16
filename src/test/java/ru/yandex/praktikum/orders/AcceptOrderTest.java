package ru.yandex.praktikum.orders;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.OrdersClient;
import ru.yandex.praktikum.pojo.AcceptOrderRequest;

import java.util.Random;

import static ru.yandex.praktikum.generator.AcceptOrderGenerator.*;

public class AcceptOrderTest {
    private StepsForOrderAPI stepsForOrderAPI;
    private OrdersClient ordersClient;

    @Before
    public void setUp() {
        stepsForOrderAPI = new StepsForOrderAPI();
        ordersClient = new OrdersClient();
    }

    @Test
    public void acceptOrderPositive() {
        Integer track = stepsForOrderAPI.createAnOrderAndReturnATrack();
        Integer orderId = stepsForOrderAPI.getAnOrderID(track);
        Integer courierId = stepsForOrderAPI.createCourierAndReturnID();

        //accept an order
        stepsForOrderAPI.acceptOrderWithCourierID(orderId, courierId);

        //delete a courier
        stepsForOrderAPI.deleteACourier(courierId);
    }

    @Test
    public void acceptOrderWithoutCourierIdNegative(){
        Integer track = stepsForOrderAPI.createAnOrderAndReturnATrack();
        Integer orderId = stepsForOrderAPI.getAnOrderID(track);

        AcceptOrderRequest acceptOrderRequest = getAcceptOrderRequestWithoutCourierIDNegative(orderId);
        ordersClient.acceptOrderWithoutCourierIDNegative(acceptOrderRequest, orderId)
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.equalTo("Недостаточно данных для поиска"));

    }

    @Test
    public void acceptOrderWithIncorrectCourierIDNegative(){
        Integer track = stepsForOrderAPI.createAnOrderAndReturnATrack();
        Integer orderId = stepsForOrderAPI.getAnOrderID(track);
        int courierId = new Random().nextInt();

        //accept an order
        AcceptOrderRequest acceptOrderRequest = getAcceptOrderRequest(orderId, courierId);
        ordersClient.acceptOrder(acceptOrderRequest, orderId, courierId)
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Курьера с таким id не существует"));

    }

    @Test
    public void acceptOrderWithIncorrectOrderIDNegative(){
        int orderId = new Random().nextInt();
        Integer courierId = stepsForOrderAPI.createCourierAndReturnID();

        //accept an order
        AcceptOrderRequest acceptOrderRequest = getAcceptOrderRequest(orderId, courierId);
        ordersClient.acceptOrder(acceptOrderRequest, orderId, courierId)
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", Matchers.equalTo("Заказа с таким id не существует"));

        //delete a courier
        stepsForOrderAPI.deleteACourier(courierId);
    }

    @Test
    public void acceptOrderWithoutOrderIDNegative(){
        Integer courierId = stepsForOrderAPI.createCourierAndReturnID();

        //accept an order
        AcceptOrderRequest acceptOrderRequest = getAcceptOrderRequestWithoutOrderIDNegative(courierId);
        ordersClient.acceptOrderWithoutOrderIDNegative(acceptOrderRequest, courierId)
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", Matchers.equalTo("Недостаточно данных для поиска"));

        //delete a courier
        stepsForOrderAPI.deleteACourier(courierId);
    }

}

