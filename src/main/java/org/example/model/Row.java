package org.example.model;

import org.example.model.enums.SpotType;
import java.util.*;

public class Row {
    private final List<ParkingSpot> spots;

    public Row(int rowId, List<SpotType> spotTypes) {
        spots = new ArrayList<>();
        for (int i = 0; i < spotTypes.size(); i++) {
            spots.add(new ParkingSpot("R" + rowId + "-" + (i + 1), spotTypes.get(i)));
        }
    }

    public List<ParkingSpot> getSpots() { return spots; }
}