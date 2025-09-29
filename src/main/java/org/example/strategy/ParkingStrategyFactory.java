package org.example.strategy;

import org.example.model.*;

public class ParkingStrategyFactory {
    public static ParkingStrategy getStrategy(Vehicle vehicle) {
        if (vehicle instanceof Car) return new CarStrategy();
        if (vehicle instanceof Motorcycle) return new MotorcycleStrategy();
        if (vehicle instanceof Van) return new VanStrategy();
        throw new IllegalArgumentException("Unknown vehicle type");
    }
}
