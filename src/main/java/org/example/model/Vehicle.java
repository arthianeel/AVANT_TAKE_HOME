package org.example.model;

public abstract class Vehicle {
    protected final String id;
    public Vehicle(String id) { this.id = id; }
    public String getId() { return id; }
}
