package com.mds.system.littleshop.controller;

import com.mds.system.littleshop.model.Car;
import com.mds.system.littleshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/car")
public class CarController {
    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(value = "create", headers = "Accept=application/json")
    public void createCar(@RequestBody Car car) {
        carService.saveCar(car);
    }

    @GetMapping(value = "all", headers = "Accept=application/json")
    public List<Car> allCars() {
        return carService.findAll();
    }

    @GetMapping(value = "/{id}", headers = "Accept=application/json")
    public Optional<Car> carById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @PutMapping(value = "update", headers = "Accept=application/json")
    public void updateCar(@RequestBody Car car) {
        carService.saveCar(car);
    }

    @DeleteMapping(value = "/{id}", headers = "Accept=application/json")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
    }

    @GetMapping(value = "/{opt}/{value}", headers = "Accept=application/json")
    public List<Car> carByAttribute(@PathVariable String opt, @PathVariable String value) {
        return switch (opt) {
            case "brand" -> carService.findByBrand(value);
            case "model" -> carService.findByModel(value);
            case "color" -> carService.findByColor(value);
            case "year" -> carService.findByYear(Integer.parseInt(value));
            default -> carService.findAll();
        };

    }

}
