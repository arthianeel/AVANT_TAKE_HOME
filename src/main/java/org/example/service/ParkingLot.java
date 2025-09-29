package org.example.service;

import org.example.model.*;
import org.example.strategy.*;
import java.util.*;

public class ParkingLot {
    private final List<Row> rows;
    private final Map<String, List<ParkingSpot>> allocations;

    public ParkingLot(List<List<org.example.model.enums.SpotType>> config) {
        rows = new ArrayList<>();
        for (int i = 0; i < config.size(); i++) {
            rows.add(new Row(i + 1, config.get(i)));
        }
        allocations = new HashMap<>();
    }

    public List<String> park(Vehicle vehicle) {
        if (allocations.containsKey(vehicle.getId())) {
            return allocations.get(vehicle.getId()).stream().map(ParkingSpot::getId).toList();
        }

        ParkingStrategy strategy = ParkingStrategyFactory.getStrategy(vehicle);
        List<ParkingSpot> chosen = strategy.findSpots(rows, vehicle);

        for (ParkingSpot s : chosen) {
            s.occupy(vehicle.getId());
        }
        allocations.put(vehicle.getId(), chosen);

        return chosen.stream().map(ParkingSpot::getId).toList();
    }

    public boolean remove(String vehicleId) {
        if (!allocations.containsKey(vehicleId)) return false;
        for (ParkingSpot s : allocations.get(vehicleId)) {
            s.free();
        }
        allocations.remove(vehicleId);
        return true;
    }
}
