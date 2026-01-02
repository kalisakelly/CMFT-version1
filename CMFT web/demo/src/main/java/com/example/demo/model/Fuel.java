package com.example.demo.model;

public class Fuel {
    private int carId;
    private double liters;
    private double price;
    private double distanceKm;

    public Fuel(int carId, double liters, double price, double distanceKm) {
        this.carId = carId;
        this.liters = liters;
        this.price = price;
        this.distanceKm = distanceKm;
    }
    // getters & setters
    public int getCarId() {
        return carId;
    }
    public void setCarId(int carId) {
        this.carId = carId;
    }
    public double getLiters() {
        return liters;
    }
    public void setLiters(double liters) {
        this.liters = liters;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getDistanceKm() {
        return distanceKm;
    }
    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }
}