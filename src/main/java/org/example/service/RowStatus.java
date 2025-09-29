package org.example.service;

public class RowStatus {
    private final String rowId;
    private final int totalSpots;
    private final int freeSpots;

    public RowStatus(String rowId, int totalSpots, int freeSpots) {
        this.rowId = rowId;
        this.totalSpots = totalSpots;
        this.freeSpots = freeSpots;
    }

    public String getRowId() { return rowId; }
    public int getTotalSpots() { return totalSpots; }
    public int getFreeSpots() { return freeSpots; }
}
