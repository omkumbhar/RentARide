package com.omkar.kumbhar.rentaride.Method;

public class OrderModel {
    String OrderID;
    String RentPaid;
    String FromTime;
    String TillTime;
    String ImgUrl;

    public OrderModel() {
    }

    public OrderModel(String orderID, String rentPaid, String fromTime, String tillTime, String imgUrl) {
        OrderID = orderID;
        RentPaid = rentPaid;
        FromTime = fromTime;
        TillTime = tillTime;
        ImgUrl = imgUrl;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getRentPaid() {
        return RentPaid;
    }

    public void setRentPaid(String rentPaid) {
        RentPaid = rentPaid;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getTillTime() {
        return TillTime;
    }

    public void setTillTime(String tillTime) {
        TillTime = tillTime;
    }


}
