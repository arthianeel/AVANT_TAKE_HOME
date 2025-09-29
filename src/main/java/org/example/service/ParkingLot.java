package org.example.service;

import org.example.model.*;
import org.example.model.enums.SpotType;
import java.util.*;

public class ParkingLot {
    private final List<Row> rows;
    private final Map<String, ParkingSpot> allocations;

    public ParkingLot(List<List<SpotType>> config) {
        rows = new ArrayList<>();
        for (int i = 0; i < config.size(); i++) {
            rows.add(new Row(i + 1, config.get(i)));
        }
        allocations = new HashMap<>();
    }

    public List<String> park(Car car) {
        if (allocations.containsKey(car.getId())) {
            return Collections.singletonList(allocations.get(car.getId()).getId());
        }

        for (Row row : rows) {
            for (ParkingSpot spot : row.getSpots()) {
                if (spot.isFree() && spot.getType() == SpotType.REGULAR) {
                    spot.occupy(car.getId());
                    allocations.put(car.getId(), spot);
                    return Collections.singletonList(spot.getId());
                }
            }
        }
        throw new RuntimeException("No available regular spot for car");
    }

    public List<String> park(Motorcycle moto) {
        if (allocations.containsKey(moto.getId())) {
            return Collections.singletonList(allocations.get(moto.getId()).getId());
        }

        for (Row row : rows) {
            for (ParkingSpot spot : row.getSpots()) {
                if (spot.isFree()) {
                    spot.occupy(moto.getId());
                    allocations.put(moto.getId(), spot);
                    return Collections.singletonList(spot.getId());
                }
            }
        }
        throw new RuntimeException("No available spot for motorcycle");
    }
}
