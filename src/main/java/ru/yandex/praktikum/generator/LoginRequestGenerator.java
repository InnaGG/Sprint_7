package ru.yandex.praktikum.generator;

import ru.yandex.praktikum.pojo.CourierRequest;
import ru.yandex.praktikum.pojo.LoginCourierRequest;

public class LoginRequestGenerator {
    public static LoginCourierRequest from(CourierRequest courierRequest){
        LoginCourierRequest loginCourierRequest = new LoginCourierRequest();
        loginCourierRequest.setLogin(courierRequest.getLogin());
        loginCourierRequest.setPassword(courierRequest.getPassword());
        return loginCourierRequest;
    }

    public static LoginCourierRequest generateLoginRequestWithRequiredValues(String login, String password){
        LoginCourierRequest loginCourierRequest = new LoginCourierRequest();
        loginCourierRequest.setLogin(login);
        loginCourierRequest.setPassword(password);
        return loginCourierRequest;
    }
}
