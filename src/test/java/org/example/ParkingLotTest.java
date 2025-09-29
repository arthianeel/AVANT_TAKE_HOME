package org.example;

import org.example.model.Car;
import org.example.model.Motorcycle;
import org.example.model.enums.SpotType;
import org.example.service.ParkingLot;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


public class ParkingLotTest {

    @Test
    public void testCarParksInFirstRegularSpot() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);
        Car car = new Car("C1");

        List<String> allocated = lot.park(car);

        assertEquals(1, allocated.size());
        assertEquals("R1-1", allocated.get(0));
    }


    @Test
    public void testCarAlreadyParkedReturnsSameSpot() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);
        Car car = new Car("C1");

        List<String> firstAllocation = lot.park(car);
        List<String> secondAllocation = lot.park(car);

        assertEquals(firstAllocation, secondAllocation);
        assertEquals("R1-1", secondAllocation.get(0));
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
    public void testMotorcycleAlreadyParkedReturnsSameSpot() {
        List<List<SpotType>> config = new ArrayList<>();
        config.add(Arrays.asList(SpotType.REGULAR, SpotType.COMPACT));
        ParkingLot lot = new ParkingLot(config);
        Motorcycle moto = new Motorcycle("M1");

        List<String> firstAllocation = lot.park(moto);
        List<String> secondAllocation = lot.park(moto);

        assertEquals(firstAllocation, secondAllocation);
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
}