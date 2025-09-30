package org.example.strategy;

import org.example.model.*;
import org.example.model.enums.SpotType;

import java.util.*;

public class MotorcycleStrategy implements ParkingStrategy {
    @Override
    public List<ParkingSpot> findSpots(List<Row> rows, Vehicle vehicle,
                                       Map<SpotType, LinkedHashSet<ParkingSpot>> freeSpotsByType) {
        LinkedHashSet<ParkingSpot> compacts = freeSpotsByType.get(SpotType.COMPACT);
        if (!compacts.isEmpty()) {
            return Collections.singletonList(compacts.iterator().next());
        }

        LinkedHashSet<ParkingSpot> regulars = freeSpotsByType.get(SpotType.REGULAR);
        if (!regulars.isEmpty()) {
            return Collections.singletonList(regulars.iterator().next());
        }

        throw new RuntimeException("No available spot for motorcycle");
    }
}
