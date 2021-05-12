package pl.writech.week4.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.writech.week4.model.Car;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private int counter = 4;

    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public Optional<Car> getCarById(long id) {
        return cars.stream()
                .filter(car -> car.getId() == id)
                .findFirst();
    }

    public Optional<Car> getCarByColor(String color) {
        return cars.stream()
                .filter(car -> car.getColor().equalsIgnoreCase(color))
                .findFirst();
    }

    public boolean isModifiedCar(Car car) {
        Optional<Car> optionalCar = getCarById(car.getId());
        if (optionalCar.isPresent()) {
            cars.remove(optionalCar.get());
            Car updatedCar = new Car(car.getId(), car.getMark(), car.getModel(), car.getColor());
            return cars.add(updatedCar);
        }
        return false;
    }

    public boolean isRemovedCar(long id) {
        Optional<Car> optionalCar = getCarById(id);
        return optionalCar.map(car -> cars.remove(car))
                .orElse(false);
    }

    public boolean isAddedCar(Car car) {
        car.setId(counter++);
        return cars.add(car);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void prepareCars() {
        cars = new ArrayList<>();
        cars.add(new Car(1L, "Audi", "A3", "black"));
        cars.add(new Car(2L, "Audi", "A5", "red"));
        cars.add(new Car(3L, "Ford", "focus", "blue"));
    }

}
