package org.example.service;

import org.example.model.*;
import org.example.model.enums.SpotType;
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

    public ParkingLotStatus getStatus() {
        int total = 0, free = 0;
        int regularTotal = 0, regularFree = 0;
        int compactTotal = 0, compactFree = 0;
        int vansParked = 0;

        List<RowStatus> rowStatuses = new ArrayList<>();

        int rowIndex = 1;
        for (Row row : rows) {
            int rowTotal = 0, rowFree = 0;
            for (ParkingSpot s : row.getSpots()) {
                rowTotal++;
                total++;
                if (s.getType() == SpotType.REGULAR) regularTotal++;
                else compactTotal++;

                if (s.isFree()) {
                    rowFree++;
                    free++;
                    if (s.getType() == SpotType.REGULAR) regularFree++;
                    else compactFree++;
                }
            }
            rowStatuses.add(new RowStatus("R" + rowIndex, rowTotal, rowFree));
            rowIndex++;
        }

        for (List<ParkingSpot> allocated : allocations.values()) {
            if (allocated.size() == 2) vansParked++;
        }

        boolean isFull = free == 0;
        boolean isEmpty = free == total;
        boolean allRegularFull = regularFree == 0;
        boolean allCompactFull = compactFree == 0;

        return new ParkingLotStatus(total, free, regularTotal, regularFree,
                compactTotal, compactFree, isFull, isEmpty,
                allRegularFull, allCompactFull, vansParked, rowStatuses);
    }
}
