package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.pojo.AcceptOrderRequest;

public class AcceptOrderGenerator {
    public static AcceptOrderRequest getAcceptOrderRequest(int orderID, int courierID){
        AcceptOrderRequest acceptOrderRequest = new AcceptOrderRequest();
        acceptOrderRequest.setId(orderID);
        acceptOrderRequest.setCourierId(courierID);
        return acceptOrderRequest;
    }

    public static AcceptOrderRequest getAcceptOrderRequestWithoutCourierIDNegative(int orderID){
        AcceptOrderRequest acceptOrderRequest = new AcceptOrderRequest();
        acceptOrderRequest.setId(orderID);
        return acceptOrderRequest;
    }

    public static AcceptOrderRequest getAcceptOrderRequestWithoutOrderIDNegative(int courierID){
        AcceptOrderRequest acceptOrderRequest = new AcceptOrderRequest();
        acceptOrderRequest.setCourierId(courierID);
        return acceptOrderRequest;
    }
}
