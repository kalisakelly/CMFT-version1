package com.example.demo.controller;
import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import java.util.Collection;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    // Create car
    @PostMapping
    public Car createCar(@RequestBody Map<String, Object> body) {
        return service.createCar(
            body.get("brand").toString(),
            body.get("model").toString(),
            (int) body.get("year")
        );
    }

    // List cars
    @GetMapping
    public Collection<Car> listCars() {
        return service.getCars();
    }

    // Add fuel
    @PostMapping("/{id}/fuel")
    public void addFuel(
            @PathVariable int id,
            @RequestBody Map<String, Object> body) {

        service.addFuel(
            id,
            ((Number) body.get("liters")).doubleValue(),
            ((Number) body.get("price")).doubleValue(),
            ((Number) body.get("odometer")).doubleValue()
        );
    }

    // Fuel stats
    @GetMapping("/{id}/fuel/stats")
    public Map<String, Double> stats(@PathVariable int id) {
        return service.getFuelStats(id);
    }

    // Update car
    @PutMapping("/{id}")
    public Car updateCar(@PathVariable int id, @RequestBody Map<String, Object> body) {
        return service.updateCar(
            id,
            body.get("brand").toString(),
            body.get("model").toString(),
            (int) body.get("year")
        );
    }

    // Delete car
    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable int id) {
        service.deleteCar(id);
        return "Car deleted successfully";
    }

}
