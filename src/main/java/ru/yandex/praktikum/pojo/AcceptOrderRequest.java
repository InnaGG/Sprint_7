package ru.yandex.praktikum.pojo;

public class AcceptOrderRequest {
    private int id;
    private int courierId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }
}
