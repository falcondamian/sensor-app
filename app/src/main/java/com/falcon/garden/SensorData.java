package com.falcon.garden;

import java.time.LocalDateTime;

public class SensorData {

    public String sensor;
    public Double airHumidity;
    public Double airTemperature;
    public Double soilHumidity;
    public LocalDateTime collectionDateTime;

    public SensorData(String sensor, Double airHumidity, Double airTemperature, Double soilHumidity, LocalDateTime collectionDateTime) {
        this.sensor = sensor;
        this.airHumidity = airHumidity;
        this.airTemperature = airTemperature;
        this.soilHumidity = soilHumidity;
        this.collectionDateTime = collectionDateTime;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public Double getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(Double airHumidity) {
        this.airHumidity = airHumidity;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Double getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(Double soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public LocalDateTime getCollectionDateTime() {
        return collectionDateTime;
    }

    public void setCollectionDateTime(LocalDateTime collectionDateTime) {
        this.collectionDateTime = collectionDateTime;
    }
}
