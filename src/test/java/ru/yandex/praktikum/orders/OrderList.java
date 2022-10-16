package ru.yandex.praktikum.orders;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.OrdersClient;
import ru.yandex.praktikum.pojo.GetOrdersListRequest;

import static ru.yandex.praktikum.generator.GetOrderListRequestGenerator.generateGetOrderListRequestWithCourierID;

public class OrderList {
    private OrdersClient ordersClient;
    private StepsForOrderAPI stepsForOrderAPI;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
        stepsForOrderAPI = new StepsForOrderAPI();
    }

    @Test
    public void getOrderListWithCourierID(){
        Integer track = stepsForOrderAPI.createAnOrderAndReturnATrack();
        Integer orderId = stepsForOrderAPI.getAnOrderID(track);
        Integer courierId = stepsForOrderAPI.createCourierAndReturnID();
        stepsForOrderAPI.acceptOrderWithCourierID(orderId, courierId);
        GetOrdersListRequest getOrdersListRequest = generateGetOrderListRequestWithCourierID(courierId);
        ordersClient.getOrderListWithCourierID(getOrdersListRequest, courierId)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("orders", Matchers.notNullValue())
                .body("pageInfo", Matchers.notNullValue())
                .body("availableStations", Matchers.notNullValue());

    }
}
