package org.example.strategy;

import org.example.model.*;
import org.example.model.enums.SpotType;
import java.util.*;


public class VanStrategy implements ParkingStrategy {
    @Override
    public List<ParkingSpot> findSpots(List<Row> rows, Vehicle vehicle) {
        for (Row row : rows) {
            List<ParkingSpot> spots = row.getSpots();
            for (int i = 0; i < spots.size() - 1; i++) {
                ParkingSpot s1 = spots.get(i);
                ParkingSpot s2 = spots.get(i+1);
                if (s1.isFree() && s2.isFree() &&
                        s1.getType() == SpotType.REGULAR &&
                        s2.getType() == SpotType.REGULAR) {
                    return Arrays.asList(s1, s2);
                }
            }
        }
        throw new RuntimeException("No available contiguous regular spots for van");
    }
}
