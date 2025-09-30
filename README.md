# Parking Lot System (LLD)

## Overview
This project is a **Parking Lot System** implemented in **Java 17** using **Maven**. It models a parking lot with **rows** and **spots** of type **REGULAR** and **COMPACT**, and supports different vehicle types: **Car**, **Motorcycle**, and **Van**.

The solution follows **Object-Oriented Design principles**, applies the **Strategy Pattern** for vehicle-specific parking rules and allocates in Row-major order, a deterministic algorithm, and returns structured status information via a **DTO (`ParkingLotStatus`)**.

We have also optimized allocation with **HashMaps** for O(1) operations in most cases.

---

##  How to Build and Run

### Prerequisites
- **Java 17** installed (`java -version` should show 17).
- **Maven** installed (`mvn -version`).

### Build
```bash
mvn clean install
```

### Run Demo
```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

### Run Tests
```bash
mvn test
```

---

##  Design Choices

### Core Classes
- **Vehicle hierarchy** (`Car`, `Motorcycle`, `Van`) extends abstract `Vehicle`.
- **ParkingSpot** and **Row** model the physical structure with **SpotType** (REGULAR/COMPACT).
- **ParkingLot** orchestrates parking, removal, and status queries.

### Strategy Pattern
- Each vehicle type has unique parking rules:
    - **CarStrategy** → must use Regular spots.
    - **MotorcycleStrategy** → can park in any spot.
    - **VanStrategy** → requires two contiguous Regular spots.
- The **Strategy Pattern** isolates these rules and keeps `ParkingLot` clean and follows Open Closed Principle.
- **ParkingStrategyFactory** selects the correct strategy at runtime.

## HashMap-Based Allocation

- `Map<SpotType, LinkedHashSet<ParkingSpot>>` stores free spots.
    - Car/Motorcycle allocations use O(1) add/remove.
    - Vans still require a scan for contiguous pairs (O(N)).
- Balanced between **performance** and **simplicity**.
  
  ### Complexity Analysis
| Operation                  | Complexity | Notes                                   |
|----------------------------|------------|-----------------------------------------|
| Car park/remove            | O(1)       | Direct lookup in HashMap + LinkedHashSet |
| Motorcycle park/remove     | O(1)       | Prefers Compact, fallback Regular        |
| Van park (2 contiguous)    | O(N)       | Requires row scan for adjacency          |
| Status (totals/free count) | O(1)       | From tracked HashMap sets                |
| Status (van count)         | O(N)       | Checks allocations for size == 2         |

### DTOs
- **ParkingLotStatus** is a **Data Transfer Object** reporting:
    - Total and free spots (overall + by type).
    - Lot flags: full/empty, all regular full, all compact full.
    - Vans parked.
    - Per-row breakdown (`RowStatus`).
- Provides type-safety, clarity and extensibility.

### Assumptions
- The parking lot configuration is fixed at initialization.
- Users wont try to use the same ID for different vehicle which is why safeguards against duplicate ids are not present.
- Only vans will ever occupy exactly 2 spots.
- If we try to remove a car that doesn't exist, we just return `false`.
- Only one thread will call `park()` or `remove()` at a time.
---

## Why This Design?
- **SOLID Principles**  
  - SRP → Each class handles one concern (e.g., Vehicle ≠ ParkingLot logic).  
  - OCP → Add new vehicle/strategy without touching ParkingLot.  
  - DIP → ParkingLot depends only on the ParkingStrategy interface.  
- **Algorithmic Efficiency** → Optimized Cars/Motorcycles to O(1), deliberately left Vans O(N) for simplicity.  
- **Extensibility** → Add Trucks or new SpotTypes with minimal changes.  
- **Clarity** → DTOs (`ParkingLotStatus`, `RowStatus`) cleanly separate state reporting.  
- **Testability** → JUnit 5 validates strategies, removal, re-parking, and status.

---

  ## Design Trade-offs
- Chose O(1) allocation for Cars/Motorcycles via HashMap, but left Van allocation O(N) to avoid overengineering.  
- Used RuntimeException for simplicity; a real system would use typed exceptions with error codes.  
- Opted for row-major fairness over round-robin or best-fit; simpler but less efficient.  
- Kept in-memory state for speed and clarity; real-world systems require persistence + concurrency controls.

---

## Potential Improvements
- **Allocation DTO**: Instead of returning `List<String>`, introduce an `Allocation` DTO with vehicleId, spots, and status message.
- **Config-driven strategies**: Vehicle/spot rules could be externalized to a rules engine or config files for more flexibility.
- **Use Round Robin Allocation** : For better use of space and prevent congestion.
- **Multi-lot support**: A `ParkingLotManager` could manage multiple lots.
- **Multi-vehicle support**: Can extend the application to have more vehicles, e.g: Bus
- **Multiple-spot type Support**: Can add more spot types, e.g: EV, Handicapped
- **Persistence layer**: Store lot state in a database for durability.
- **REST API**: Expose parking operations as endpoints for integration with frontends.
- **Graceful Error Handling**: For the `remove()` operation, we can return a custom exception instead of returning `false`. For `park()` , if no spot is found, we can return a custom exception with status code and detailed error message instead of a `RuntimeException` 
---

## Project Structure
```
src/main/java/org/example/
 ├── model/
 │    ├── Vehicle.java, Car.java, Motorcycle.java, Van.java
 │    ├── ParkingSpot.java, Row.java
 │    └── enums/SpotType.java
 │
 ├── strategy/
 │    ├── ParkingStrategy.java
 │    ├── CarStrategy.java, MotorcycleStrategy.java, VanStrategy.java
 │    └── ParkingStrategyFactory.java
 │
 ├── service/
 │    ├── ParkingLot.java
 │    ├── ParkingLotStatus.java
 │    └── RowStatus.java
 │
 └── Main.java
```
Decided to follow this project structure because it does a clean separation of concerns and follows SOLID principles.
- `model/` contains the domain objects which represent the real world.
- `strategy/` contains allocation algorithms, each vehicle has a different strategy.
- `service/` contains business logic of the system
- `Main.java` is the entry point for demo/testing.

  **CLASS DIAGRAM**

 <img width="3840" height="1816" alt="Class-diagram-parking-lot _ Mermaid Chart-2025-09-30-024426" src="https://github.com/user-attachments/assets/674c502d-da98-40d1-b35c-4cba23fd0dce" />

 **SEQUENCE DIAGRAM FOR PARKING CAR**
 <img width="3840" height="2197" alt="Untitled diagram _ Mermaid Chart-2025-09-30-063231" src="https://github.com/user-attachments/assets/c19ecb05-caf1-46d0-a362-6f3b0f7938ed" />


## 🧪 Testing
Tests in `src/test/java/org/example/` cover:
- ✅ Cars, Motorcycles, Vans allocation strategies  
- ✅ Edge cases: no spots, full lot, re-parking same vehicle  
- ✅ Van contiguous allocation (success and failure)  
- ✅ Status reporting (overall, per-row, vans)  
- Future: stress test with thousands of vehicles, concurrency tests


Run tests:
```bash
mvn test
```
