package com.beadcore.employeenotification.model;

public class EmployeeDetails {
    String device_name;
    String time;
    String employee_name;
    String latitude;
    String longitude;


    public String getDevice_name() {
        return device_name;
    }

    public String getTime() {
        return time;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
