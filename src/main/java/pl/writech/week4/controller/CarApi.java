package pl.writech.week4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.writech.week4.model.Car;
import pl.writech.week4.service.CarService;

import java.util.Optional;

@Controller
public class CarApi {

    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        model.addAttribute("cars", carService.getCars());
        model.addAttribute("car", new Car());
        return "cars";
    }

    @GetMapping("/cars/{id}")
    public String getCarById(@PathVariable long id, Model model) {
        Optional<Car> optionalCar = carService.getCarById(id);

        if(optionalCar.isPresent()){
            model.addAttribute("car", optionalCar.get());
            return "edit-car";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Car> getCarByColor(@PathVariable String color) {
        Optional<Car> optionalCar = carService.getCarByColor(color);

        return optionalCar.map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car) {
        boolean isAdded = carService.isAddedCar(car);
        if (isAdded) {
            return "redirect:/cars";
        }
        return "redirect:/error";
    }

    @PostMapping("/edit")
    public String modifyCar(@ModelAttribute Car requestCar) {
        if (carService.isModifiedCar(requestCar)) {
            return "redirect:/cars";
        }

        return "redirect:/error";
    }

    @DeleteMapping("/delete/{id}")
    public String removeCar(@PathVariable long id) {
        if (carService.isRemovedCar(id)) {
            return "redirect:/cars";
        }

        return "redirect:/error";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }


}
