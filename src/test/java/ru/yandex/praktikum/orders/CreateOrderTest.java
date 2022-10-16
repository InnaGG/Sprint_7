package ru.yandex.praktikum.orders;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.OrdersClient;
import ru.yandex.praktikum.pojo.CreateOrderRequest;

import static ru.yandex.praktikum.generator.CreateOrderRequestGenerator.createRandomOrderRequestNoColor;

public class CreateOrderTest {
    private OrdersClient ordersClient;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    public void createOrderWithoutColor() {
        CreateOrderRequest createOrderRequest = createRandomOrderRequestNoColor();
        ordersClient.createOrder(createOrderRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("track", Matchers.notNullValue());
    }
}
