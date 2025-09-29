package org.example.strategy;

import org.example.model.*;
import java.util.*;

public interface ParkingStrategy {
    List<ParkingSpot> findSpots(List<Row> rows, Vehicle vehicle);
}