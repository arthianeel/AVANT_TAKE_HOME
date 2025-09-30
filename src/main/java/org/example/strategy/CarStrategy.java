package org.example.strategy;

import org.example.model.*;
import org.example.model.enums.SpotType;
import java.util.*;

public class CarStrategy implements ParkingStrategy {
    @Override
    public List<ParkingSpot> findSpots(List<Row> rows, Vehicle vehicle,
                                       Map<SpotType, LinkedHashSet<ParkingSpot>> freeSpotsByType) {
        LinkedHashSet<ParkingSpot> regulars = freeSpotsByType.get(SpotType.REGULAR);
        if (regulars.isEmpty()) throw new RuntimeException("No available regular spot for car");

        ParkingSpot spot = regulars.iterator().next(); // O(1)
        return Collections.singletonList(spot);
    }
}
