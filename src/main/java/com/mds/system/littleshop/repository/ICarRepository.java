package com.mds.system.littleshop.repository;

import com.mds.system.littleshop.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrand(String brand);

    List<Car> findByColor(String color);

    List<Car> findByModel(String model);

    List<Car> findByYear(Integer year);
}
