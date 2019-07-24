package com.beadcore.employeenotification.model;

public class AttendanceObject {

    String time;
    String date;
    String device;
    String latitude;
    String longitude;

    public AttendanceObject(){

    }

    public AttendanceObject(String time,String date,String device,String latitude,String longitude){
        this.time = time;
        this.date = date;
        this.device = device;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getDevice() {
        return device;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
