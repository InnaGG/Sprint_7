package ru.yandex.praktikum.orders;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.client.OrdersClient;
import ru.yandex.praktikum.pojo.CreateOrderRequest;

import static ru.yandex.praktikum.generator.CreateOrderRequestGenerator.createRandomOrderRequestWithSpecifiedColor;

@RunWith(Parameterized.class)
public class CreateOrdersTestParameterized {

    private OrdersClient ordersClient;

    private final String[] colorVariants;

    public CreateOrdersTestParameterized(String[] colorVariants){
        this.colorVariants = colorVariants;
    }

    @Parameterized.Parameters()
    public static Object[][] getColorVariantsData() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}}
        };
    }

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    public void createOrderTestParametrized(){
        CreateOrderRequest createOrderRequest = createRandomOrderRequestWithSpecifiedColor(colorVariants);
        ordersClient.createOrder(createOrderRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("track", Matchers.notNullValue());
    }

}
