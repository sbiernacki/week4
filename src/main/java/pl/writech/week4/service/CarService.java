package pl.writech.week4.service;

import pl.writech.week4.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> getCars();

    Optional<Car> getCarById(long id);

    boolean isModifiedCar(Car car);

    boolean isRemovedCar(long id);

    boolean isAddedCar(Car car);
}
