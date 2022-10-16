package ru.yandex.praktikum.client;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.CourierRequest;
import ru.yandex.praktikum.pojo.DeleteCourierRequest;
import ru.yandex.praktikum.pojo.LoginCourierRequest;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient{
    private static final String COURIER = "/api/v1/courier";
    private static final String COURIER_LOGIN = "/api/v1/courier/login";
    private static final String DELETE_COURIER = "/api/v1/courier/{id}";
    private static final String DELETE_COURIER_WITHOUT_ID_NEGATIVE = "/api/v1/courier/";

    // create courier in system
    public ValidatableResponse create(CourierRequest courierRequest){
        return given()
                .spec(getDefaultRequestSpec())
                .body(courierRequest)
                .post(COURIER)
                .then();
    }

    // login courier
    public ValidatableResponse login(LoginCourierRequest loginCourierRequest){
        return given()
                .spec(getDefaultRequestSpec())
                .body(loginCourierRequest)
                .post(COURIER_LOGIN)
                .then();
    }

    // delete courier
    public ValidatableResponse delete(DeleteCourierRequest deleteCourierRequest, Integer id){
        return given()
                .spec(getDefaultRequestSpec())
                .body(deleteCourierRequest)
                .pathParam("id", id.toString())
                .delete(DELETE_COURIER)
                .then();
    }

    // delete courier
    public ValidatableResponse deleteWithoutIDNegative(DeleteCourierRequest deleteCourierRequest){
        return given()
                .spec(getDefaultRequestSpec())
                .body(deleteCourierRequest)
                .delete(DELETE_COURIER_WITHOUT_ID_NEGATIVE)
                .then();
    }

}
