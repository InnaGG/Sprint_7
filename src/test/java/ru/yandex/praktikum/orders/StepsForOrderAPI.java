package ru.yandex.praktikum.orders;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.client.OrdersClient;
import ru.yandex.praktikum.generator.LoginRequestGenerator;
import ru.yandex.praktikum.pojo.*;

import static ru.yandex.praktikum.generator.AcceptOrderGenerator.getAcceptOrderRequest;
import static ru.yandex.praktikum.generator.CourierRequestGenerator.getRandomCourierRequest;
import static ru.yandex.praktikum.generator.CreateOrderRequestGenerator.createRandomOrderRequestNoColor;
import static ru.yandex.praktikum.generator.GetOrderRequestGenerator.generateGetOrderRequest;

public class StepsForOrderAPI {
    private final CourierClient courierClient = new CourierClient();
    private final OrdersClient ordersClient = new OrdersClient();;

    @Step("Create an order")
    public Integer createAnOrderAndReturnATrack(){
        CreateOrderRequest createOrderRequest = createRandomOrderRequestNoColor();
        return ordersClient.createOrder(createOrderRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("track", Matchers.notNullValue())
                .and()
                .extract()
                .path("track");
    }

    @Step("Receive an order id from get order request")
    public Integer getAnOrderID(Integer track){
        GetOrderRequest getOrderRequest = generateGetOrderRequest(track);
        return ordersClient.getOrderByTrack(getOrderRequest, track)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(Matchers.notNullValue())
                .and()
                .extract()
                .path("order.id");
    }

    @Step("Delete a courier")
    public void deleteACourier(Integer courierId) {
        if (courierId != null) {
            DeleteCourierRequest deleteCourierRequest = new DeleteCourierRequest();
            deleteCourierRequest.setId(courierId.toString());
            courierClient.delete(deleteCourierRequest, courierId)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .and()
                    .body("ok", Matchers.equalTo(true));
        }
    }

    @Step("Create a courier and return it's id")
    public Integer createCourierAndReturnID(){
        // create a courier
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body("ok", Matchers.equalTo(true));

        // login a courier to get courierId
        LoginCourierRequest loginCourierRequest = LoginRequestGenerator.from(randomCourierRequest);

        return courierClient.login(loginCourierRequest)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", Matchers.notNullValue())
                .extract()
                .path("id");

    }

    @Step("Accept an order with courierID")
    public void acceptOrderWithCourierID(Integer orderID, Integer courierID){
        AcceptOrderRequest acceptOrderRequest = getAcceptOrderRequest(orderID, courierID);
        ordersClient.acceptOrder(acceptOrderRequest, orderID, courierID)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("ok", Matchers.equalTo(true));

    }

}
