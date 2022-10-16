package ru.yandex.praktikum.generator;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.pojo.CourierRequest;

public class CourierRequestGenerator {
    public static CourierRequest getRandomCourierRequest(){
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword(RandomStringUtils.randomAlphabetic(10));
        return courierRequest;
    }

    public static CourierRequest getRandomCourierRequestWithoutLoginValue(){
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword(RandomStringUtils.randomAlphabetic(10));
        return courierRequest;
    }

    public static CourierRequest getCourierRequestWithRequiredValues(String firstName, String login, String password){
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setFirstName(firstName);
        courierRequest.setLogin(login);
        courierRequest.setPassword(password);
        return courierRequest;
    }
}
