package org.example.strategy;

import org.example.model.*;
import java.util.*;

public class MotorcycleStrategy implements ParkingStrategy {
    @Override
    public List<ParkingSpot> findSpots(List<Row> rows, Vehicle vehicle) {
        for (Row row : rows) {
            for (ParkingSpot spot : row.getSpots()) {
                if (spot.isFree()) {
                    return Collections.singletonList(spot);
                }
            }
        }
        throw new RuntimeException("No available spot for motorcycle");
    }
}
