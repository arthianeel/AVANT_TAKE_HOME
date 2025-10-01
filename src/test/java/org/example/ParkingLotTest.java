package org.example;

import org.example.model.Car;
import org.example.model.Motorcycle;
import org.example.model.Van;
import org.example.model.enums.SpotType;
import org.example.service.ParkingLot;
import org.example.service.ParkingLotStatus;
import org.example.service.RowStatus;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


public class ParkingLotTest {

    @Test
    public void testEmptyLotStatus() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);

        ParkingLotStatus status = lot.getStatus();
        assertEquals(2, status.getTotalSpots());
        assertEquals(2, status.getFreeSpots());
        assertEquals(1, status.getRegularTotal());
        assertEquals(1, status.getRegularFree());
        assertEquals(1, status.getCompactTotal());
        assertEquals(1, status.getCompactFree());
        assertTrue(status.isEmpty());
        assertFalse(status.isFull());
        assertFalse(status.isAllRegularFull());
        assertFalse(status.isAllCompactFull());
        assertEquals(0, status.getVansParked());
    }
    @Test
    public void testCarParksInFirstRegularSpot() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);
        Car car = new Car("C1");

        List<String> allocated = lot.park(car);

        assertEquals(1, allocated.size());
        assertEquals("R1-1", allocated.get(0));

        ParkingLotStatus status = lot.getStatus();
        assertEquals(3, status.getTotalSpots());
        assertEquals(2, status.getFreeSpots());
        assertEquals(2, status.getRegularTotal());
        assertEquals(1, status.getRegularFree());
        assertEquals(1, status.getCompactTotal());
        assertEquals(1, status.getCompactFree());
        assertFalse(status.isFull());
        assertFalse(status.isEmpty());
    }

    @Test
    public void testLotFullStatus() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);

        Car car = new Car("C1");
        lot.park(car);
        Motorcycle moto = new Motorcycle("M1");
        lot.park(moto);

        ParkingLotStatus status = lot.getStatus();
        assertEquals(2, status.getTotalSpots());
        assertEquals(0, status.getFreeSpots());
        assertTrue(status.isFull());
        assertFalse(status.isEmpty());
        assertTrue(status.isAllRegularFull());
        assertTrue(status.isAllCompactFull());
    }

    @Test
    public void testNoSpotAvailableThrowsException() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Collections.singletonList(SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);
        Car car = new Car("C1");

        Exception exception = assertThrows(RuntimeException.class, () -> lot.park(car));
        assertTrue(exception.getMessage().contains("No available regular spot for car"));
    }

    @Test
    public void testMotorcycleCanParkInCompactSpot() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);

        Car car = new Car("C1");
        lot.park(car);

        Motorcycle moto = new Motorcycle("M1");
        List<String> allocated = lot.park(moto);

        assertEquals(1, allocated.size());
        assertEquals("R1-2", allocated.get(0));
    }


    @Test
    public void testNoSpotForMotorcycleThrowsException() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Collections.singletonList(SpotType.REGULAR));
        ParkingLot lot = new ParkingLot(config);

        Car car = new Car("C1");
        lot.park(car); // occupies the only spot

        Motorcycle moto = new Motorcycle("M1");
        Exception exception = assertThrows(RuntimeException.class, () -> lot.park(moto));
        assertTrue(exception.getMessage().contains("No available spot for motorcycle"));
    }

    @Test
    public void testVanRequiresTwoContiguousRegularSpots() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);

        Van van = new Van("V1");
        List<String> allocated = lot.park(van);

        assertEquals(2, allocated.size());
        assertEquals("R1-1", allocated.get(0));
        assertEquals("R1-2", allocated.get(1));
        ParkingLotStatus status = lot.getStatus();
        assertEquals(3, status.getTotalSpots());
        assertEquals(1, status.getFreeSpots());
        assertEquals(1, status.getVansParked());
    }

    @Test
    public void testVanCannotParkAcrossRows() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Collections.singletonList(SpotType.REGULAR)); // Row 1: only R1-1
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT)); // Row 2: R2-1, R2-2

        ParkingLot lot = new ParkingLot(config);

        Van van = new Van("V1");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> lot.park(van));

        assertEquals("No contiguous regular spots for van", exception.getMessage());
    }

    @Test
    public void testNoContiguousSpotsForVanThrowsException() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT, SpotType.REGULAR));
        ParkingLot lot = new ParkingLot(config);

        Van van = new Van("V1");
        Exception exception = assertThrows(RuntimeException.class, () -> lot.park(van));
        assertTrue(exception.getMessage().contains("No contiguous regular spots for van"));
    }

    @Test
    public void testReParkingSameVehicleReturnsSameAllocation() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.REGULAR));
        ParkingLot lot = new ParkingLot(config);

        Car car = new Car("C1");
        List<String> first = lot.park(car);
        List<String> second = lot.park(car);

        assertEquals(first, second);
    }

    @Test
    public void testCarParksAndRemovesCorrectly() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);

        Car car = new Car("C1");
        List<String> allocated = lot.park(car);
        assertEquals("R1-1", allocated.get(0));

        boolean removed = lot.remove("C1");
        assertTrue(removed);

        Car car2 = new Car("C2");
        List<String> allocated2 = lot.park(car2);
        assertEquals("R1-1", allocated2.get(0));
        removed = lot.remove("C1");
        assertFalse(removed);
    }

    @Test
    public void testRowStatusBreakdown() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.REGULAR));
        config.add(Arrays.asList(SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);

        Car car = new Car("C1");
        lot.park(car);

        ParkingLotStatus status = lot.getStatus();
        List<RowStatus> rows = status.getRowStatuses();
        assertEquals(2, rows.size());
        assertEquals("R1", rows.get(0).getRowId());
        assertEquals(2, rows.get(0).getTotalSpots());
        assertEquals(1, rows.get(0).getFreeSpots());
        assertEquals("R2", rows.get(1).getRowId());
        assertEquals(1, rows.get(1).getTotalSpots());
        assertEquals(1, rows.get(1).getFreeSpots());
    }

}