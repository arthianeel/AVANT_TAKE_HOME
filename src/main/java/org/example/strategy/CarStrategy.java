package org.example.strategy;

import org.example.model.*;
import org.example.model.enums.SpotType;
import java.util.*;

public class CarStrategy implements ParkingStrategy {
    @Override
    public List<ParkingSpot> findSpots(List<Row> rows, Vehicle vehicle) {
        for (Row row : rows) {
            for (ParkingSpot spot : row.getSpots()) {
                if (spot.isFree() && spot.getType() == SpotType.REGULAR) {
                    return Collections.singletonList(spot);
                }
            }
        }
        throw new RuntimeException("No available regular spot for car");
    }
}
