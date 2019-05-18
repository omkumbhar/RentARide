package com.omkar.kumbhar.rentaride.Method;

public class ResultModel {
    String CarName;
    String FuleType;
    String ImgUrl;
    String Location;
    String Seater;
    String Rent;
    String DocId;

    public ResultModel() {
    }

    public ResultModel(String carName, String fuleType, String imgUrl, String location, String seater, String rent, String docId) {
        CarName = carName;
        FuleType = fuleType;
        ImgUrl = imgUrl;
        Location = location;
        Seater = seater;
        Rent = rent;
        DocId = docId;
    }

    public String getDocId() {
        return DocId;
    }

    public void setDocId(String docId) {
        DocId = docId;
    }

    public String getRent() {
        return Rent;
    }

    public void setRent(String rent) {
        Rent = rent;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getFuleType() {
        return FuleType;
    }

    public void setFuleType(String fuleType) {
        FuleType = fuleType;
    }


    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getSeater() {
        return Seater;
    }

    public void setSeater(String seater) {
        Seater = seater;
    }
}
