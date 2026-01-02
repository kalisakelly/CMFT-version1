package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.demo.model.Car;
import com.example.demo.model.Fuel;

@Service
public class CarService {

    private final Map<Integer, Car> cars = new HashMap<>();
    private final Map<Integer, List<Fuel>> fuels = new HashMap<>();
    private int carCounter = 0;

    /**
     * Creates a new car.
     * @param brand Car brand
     * @param model Car model
     * @param year Car year (must be > 1885)
     * @return Created Car object
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Car createCar(String brand, String model, int year) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be empty.");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be empty.");
        }
        if (year < 1886) { // First car invented around 1886
            throw new IllegalArgumentException("Year must be >= 1886.");
        }

        carCounter++;
        Car car = new Car(carCounter, brand, model, year);
        cars.put(carCounter, car);
        return car;
    }

    /**
     * Returns all cars in the system.
     * @return Collection of cars
     */
    public Collection<Car> getCars() {
        return cars.values();
    }

    /**
     * Adds fuel record to a car.
     * @param carId ID of the car
     * @param liters Fuel amount (must be > 0)
     * @param price Price of the fuel (must be >= 0)
     * @param distance Distance traveled (must be > 0)
     * @throws NoSuchElementException if car does not exist
     * @throws IllegalArgumentException if input values are invalid
     */
    public void addFuel(int carId, double liters, double price, double distance) {
        Car car = cars.get(carId);
        if (car == null) {
            throw new NoSuchElementException("Car with ID " + carId + " does not exist.");
        }
        if (liters <= 0) {
            throw new IllegalArgumentException("Liters must be greater than 0.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be greater than 0.");
        }

        fuels.computeIfAbsent(carId, k -> new ArrayList<>())
              .add(new Fuel(carId, liters, price, distance));
    }

    /**
     * Computes fuel statistics for a car.
     * @param carId Car ID
     * @return Map with totalFuel, totalCost, averageConsumption
     * @throws NoSuchElementException if car does not exist
     */
    public Map<String, Double> getFuelStats(int carId) {
        if (!cars.containsKey(carId)) {
            throw new NoSuchElementException("Car with ID " + carId + " does not exist.");
        }

        List<Fuel> list = fuels.getOrDefault(carId, Collections.emptyList());

        if (list.isEmpty()) {
            // Return 0 stats instead of crashing
            Map<String, Double> emptyStats = new HashMap<>();
            emptyStats.put("totalFuel", 0.0);
            emptyStats.put("totalCost", 0.0);
            emptyStats.put("averageConsumption", 0.0);
            return emptyStats;
        }

        double totalFuel = 0;
        double totalCost = 0;
        double totalDistance = 0;

        for (Fuel f : list) {
            totalFuel += f.getLiters();
            totalCost += f.getPrice();
            totalDistance += f.getDistanceKm();
        }

        double avgConsumption = totalDistance == 0 ? 0 : (totalFuel / totalDistance) * 100;

        Map<String, Double> stats = new HashMap<>();
        stats.put("totalFuel", totalFuel);
        stats.put("totalCost", totalCost);
        stats.put("averageConsumption", avgConsumption);

        return stats;
    }

    public Car updateCar(int id, String brand, String model, int year) {
    Car car = cars.get(id);
    if (car == null) throw new NoSuchElementException("Car not found.");
    car.setBrand(brand);
    car.setModel(model);
    car.setYear(year);
    return car;
}

public void deleteCar(int id) {
    if (!cars.containsKey(id)) throw new NoSuchElementException("Car not found.");
    cars.remove(id);
    fuels.remove(id); // remove fuel records too
}
}
