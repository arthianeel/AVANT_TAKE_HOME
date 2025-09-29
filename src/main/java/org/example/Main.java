package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import org.example.model.Car;
import org.example.model.enums.SpotType;
import org.example.service.ParkingLot;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);


        Car car = new Car("C1");

        List<String> allocated = lot.park(car);
        System.out.println("Car C1 allocated to spots: " + allocated);
    }
}