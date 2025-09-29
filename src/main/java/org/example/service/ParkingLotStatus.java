package org.example.service;

import java.util.List;

public class ParkingLotStatus {
    private final int totalSpots;
    private final int freeSpots;
    private final int regularTotal;
    private final int regularFree;
    private final int compactTotal;
    private final int compactFree;
    private final int vansParked;
    private final boolean isFull;
    private final boolean isEmpty;
    private final boolean allRegularFull;
    private final boolean allCompactFull;

    private final List<RowStatus> rowStatuses; // optional per-row breakdown


    public ParkingLotStatus(int totalSpots, int freeSpots,
                            int regularTotal, int regularFree,
                            int compactTotal, int compactFree,
                            boolean isFull, boolean isEmpty,
                            boolean allRegularFull, boolean allCompactFull,
                            int vansParked, List<RowStatus> rowStatuses) {
        this.totalSpots = totalSpots;
        this.freeSpots = freeSpots;
        this.regularTotal = regularTotal;
        this.regularFree = regularFree;
        this.compactTotal = compactTotal;
        this.compactFree = compactFree;
        this.isFull = isFull;
        this.isEmpty = isEmpty;
        this.allRegularFull = allRegularFull;
        this.allCompactFull = allCompactFull;
        this.vansParked = vansParked;
        this.rowStatuses = rowStatuses;
    }

    // Getters
    public int getTotalSpots() { return totalSpots; }
    public int getFreeSpots() { return freeSpots; }
    public int getRegularTotal() { return regularTotal; }
    public int getRegularFree() { return regularFree; }
    public int getCompactTotal() { return compactTotal; }
    public int getCompactFree() { return compactFree; }
    public boolean isFull() { return isFull; }
    public boolean isEmpty() { return isEmpty; }
    public boolean isAllRegularFull() { return allRegularFull; }
    public boolean isAllCompactFull() { return allCompactFull; }
    public int getVansParked() { return vansParked; }
    public List<RowStatus> getRowStatuses() { return rowStatuses; }
}
