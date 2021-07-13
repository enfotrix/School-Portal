package com.example.css;

public class Model_Reports {
    private String notificatioID,Date,detail,heading,type,name;

    public Model_Reports(String notificatioID, String date, String detail, String heading, String type, String name) {
        this.notificatioID = notificatioID;
        Date = date;
        this.detail = detail;
        this.heading = heading;
        this.type = type;
        this.name = name;
    }

    public String getNotificatioID() {
        return notificatioID;
    }

    public void setNotificatioID(String notificatioID) {
        this.notificatioID = notificatioID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
