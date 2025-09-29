package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import org.example.model.Car;
import org.example.model.Motorcycle;
import org.example.model.Van;
import org.example.model.enums.SpotType;
import org.example.service.ParkingLot;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.REGULAR, SpotType.COMPACT,SpotType.REGULAR, SpotType.REGULAR));
        ParkingLot lot = new ParkingLot(config);

        Car car = new Car("C1");
        Motorcycle moto = new Motorcycle("M1");
        Motorcycle moto1 = new Motorcycle("M2");
        Van van = new Van("V1");

        System.out.println("Car C1 allocated to: " + lot.park(car));
        System.out.println("Motorcycle M1 allocated to: " + lot.park(moto));
        System.out.println("Motorcycle M2 allocated to: " + lot.park(moto1));
        System.out.println("Van V1 allocated to: " + lot.park(van));
        System.out.println("Removing C1: " + lot.remove("C1"));
    }
}