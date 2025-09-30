package org.example.strategy;

import org.example.model.*;
import org.example.model.enums.SpotType;

import java.util.*;

public interface ParkingStrategy {
    List<ParkingSpot> findSpots(List<Row> rows, Vehicle vehicle,
                                Map<SpotType, LinkedHashSet<ParkingSpot>> freeSpotsByType);}