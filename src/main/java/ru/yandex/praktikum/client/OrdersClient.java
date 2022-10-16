package ru.yandex.praktikum.client;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.*;

import static io.restassured.RestAssured.given;

public class OrdersClient extends RestClient{
    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String ACCEPT_ORDER = "/api/v1/orders/accept/{id}?courierId={courierId}";
    private static final String ACCEPT_ORDER_WITH_EMPTY_COURIER_ID_NEGATIVE = "/api/v1/orders/accept/{id}";
    private static final String ACCEPT_ORDER_WITH_EMPTY_ORDER_ID_NEGATIVE = "/api/v1/orders/accept/courierId={courierId}";
    private static final String GET_ORDER_BY_TRACK = "/api/v1/orders/track?t={t}";
    private static final String GET_ORDER_WITHOUT_TRACK_NEGATIVE = "api/v1/orders/track";
    private static final String GET_ORDER_LIST_WITH_COURIER_ID = "/api/v1/orders?courierId={courierID}";
    private static final String CANCEL_ORDER = "/api/v1/orders/cancel";
    private static final String FINISH_ORDER = "/api/v1/orders/finish/{id}";

    // create order
    public ValidatableResponse createOrder(CreateOrderRequest createOrderRequest){
        return given()
                .spec(getDefaultRequestSpec())
                .body(createOrderRequest)
                .post(CREATE_ORDER)
                .then();
    }

    // accept order
    public ValidatableResponse acceptOrder(AcceptOrderRequest acceptOrderRequest, int id, int courierId){
        return given()
                .spec(getDefaultRequestSpec())
                .body(acceptOrderRequest)
                .pathParam("id", id)
                .pathParam("courierId", courierId)
                .put(ACCEPT_ORDER)
                .then();
    }

    public ValidatableResponse acceptOrderWithoutCourierIDNegative(AcceptOrderRequest acceptOrderRequest, int orderID){
        return given()
                .spec(getDefaultRequestSpec())
                .body(acceptOrderRequest)
                .pathParam("id", orderID)
                .put(ACCEPT_ORDER_WITH_EMPTY_COURIER_ID_NEGATIVE)
                .then();
    }

    public ValidatableResponse acceptOrderWithoutOrderIDNegative(AcceptOrderRequest acceptOrderRequest, int courierID){
        return given()
                .spec(getDefaultRequestSpec())
                .body(acceptOrderRequest)
                .pathParam("courierId", courierID)
                .put( ACCEPT_ORDER_WITH_EMPTY_ORDER_ID_NEGATIVE)
                .then();
    }

    // get order by track
    public ValidatableResponse getOrderByTrack(GetOrderRequest getOrderRequest, int track){
        return given()
                .spec(getDefaultRequestSpec())
                .body(getOrderRequest)
                .pathParam("t", track)
                .get(GET_ORDER_BY_TRACK)
                .then();
    }

    public ValidatableResponse getOrderByTrackWithoutTrackNegative(GetOrderRequest getOrderRequest){
        return given()
                .spec(getDefaultRequestSpec())
                .body(getOrderRequest)
                .get(GET_ORDER_WITHOUT_TRACK_NEGATIVE)
                .then();
    }

    // get order list
    public ValidatableResponse getOrderListWithCourierID(GetOrdersListRequest getOrdersListRequest, Integer courierID){
        return given()
                .spec(getDefaultRequestSpec())
                .body(getOrdersListRequest)
                .pathParam("courierID", courierID)
                .get(GET_ORDER_LIST_WITH_COURIER_ID)
                .then();
    }

    // cancel order
    public ValidatableResponse cancelOrder(CancelOrderRequest cancelOrderRequest){
        return given()
                .spec(getDefaultRequestSpec())
                .body(cancelOrderRequest)
                .put(CANCEL_ORDER)
                .then();
    }

    // finish order
    public ValidatableResponse finishOrder(FinishOrderRequest finishOrderRequest){
        return given()
                .spec(getDefaultRequestSpec())
                .body(finishOrderRequest)
                .put(FINISH_ORDER)
                .then();
    }
}
