package ;

import org.example.model.Car;
import org.example.model.enums.SpotType;
import org.example.service.ParkingLot;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


public class ParkingLotCarTest {

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
}