package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.pojo.GetOrdersListRequest;

public class GetOrderListRequestGenerator {
    public static GetOrdersListRequest generateGetOrderListRequestWithCourierID(Integer courierID){
        GetOrdersListRequest getOrdersListRequest = new GetOrdersListRequest();
        getOrdersListRequest.setCourierId(courierID);
        return getOrdersListRequest;
    }
}
