package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.pojo.GetOrderRequest;

public class GetOrderRequestGenerator {
    public static GetOrderRequest generateGetOrderRequest(int track){
        GetOrderRequest getOrderRequest = new GetOrderRequest();
        getOrderRequest.setT(track);
        return getOrderRequest;
    }

    public static GetOrderRequest generateGetOrderRequestWithoutTrackNegative(){
        GetOrderRequest getOrderRequest = new GetOrderRequest();
        return getOrderRequest;
    }
}
