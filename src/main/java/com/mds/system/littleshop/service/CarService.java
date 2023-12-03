package com.mds.system.littleshop.service;

import com.mds.system.littleshop.model.Car;
import com.mds.system.littleshop.repository.ICarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private ICarRepository carRepository;

    @Autowired
    public CarService(ICarRepository carRepository) {
        this.carRepository = carRepository;
    }

    //Create and update cars
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    //Get all cars
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    //Get by id
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    //Delete by id
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    //Get my brand
    public List<Car> findByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }
    //Get my model
    public List<Car> findByModel(String model) {
        return carRepository.findByModel(model);
    }
    //Get my color
    public List<Car> findByColor(String color) {
        return carRepository.findByColor(color);
    }
    //Get my year
    public List<Car> findByYear(Integer year) {
        return carRepository.findByYear(year);
    }

}
