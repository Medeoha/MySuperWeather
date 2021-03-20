package com.example.mysuperweather;

public class ForecastWeatherObject { // objet météo pour stocker chaque journée dans une liste par la suite

    String temperature;
    String date;
    String iconid;

    public ForecastWeatherObject(String temperature, String date, String iconid) {
        this.temperature = temperature;
        this.date = date;
        this.iconid = iconid;
    }


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIconid() {
        return iconid;
    }

    public void setIconid(String iconid) {
        this.iconid = iconid;
    }
}
