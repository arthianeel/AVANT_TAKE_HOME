package org.example.model;

import org.example.model.enums.SpotType;


public class ParkingSpot {
    private final String id;
    private final SpotType type;
    private String occupiedBy;

    public ParkingSpot(String id, SpotType type) {
        this.id = id;
        this.type = type;
        this.occupiedBy = null;
    }

    public String getId() { return id; }
    public SpotType getType() { return type; }
    public boolean isFree() { return occupiedBy == null; }
    public void occupy(String vehicleId) { this.occupiedBy = vehicleId; }
    public void free() { this.occupiedBy = null; }
}
