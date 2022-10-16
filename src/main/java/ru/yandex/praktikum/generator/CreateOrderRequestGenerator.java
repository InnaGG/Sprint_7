package ru.yandex.praktikum.generator;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.pojo.CreateOrderRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CreateOrderRequestGenerator {
    private static final Random random = new Random();

    public static CreateOrderRequest createRandomOrderRequestNoColor(){
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        createOrderRequest.setLastName(RandomStringUtils.randomAlphabetic(10));
        createOrderRequest.setAddress(RandomStringUtils.randomAlphabetic(10));
        createOrderRequest.setMetroStation(String.valueOf(random.nextInt(454)));
        createOrderRequest.setPhone(RandomStringUtils.randomNumeric(10));
        createOrderRequest.setRentTime(random.nextInt(31));
        createOrderRequest.setDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        createOrderRequest.setComment(RandomStringUtils.randomAlphabetic(20));
        return createOrderRequest;
    }
    public static CreateOrderRequest createRandomOrderRequestWithSpecifiedColor(String[] array){
        CreateOrderRequest createOrderRequest = createRandomOrderRequestNoColor();
        createOrderRequest.setColor(array);
        return createOrderRequest;
    }



}
