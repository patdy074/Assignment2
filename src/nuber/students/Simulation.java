package nuber.students;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Future;

public class Simulation {

    public Simulation(HashMap<String, Integer> regions, int maxDrivers, int maxPassengers, int maxSleep, boolean logEvents) throws Exception {
        long start = new Date().getTime();
        System.out.println();  // Print some space in the console
        
        // Create the NuberDispatch with regions
        NuberDispatch dispatch = new NuberDispatch(regions, logEvents);
        
        // Create and add drivers
        for (int i = 0; i < maxDrivers; i++) {
            String driverName = Person.getRandomName();
            Driver driver = new Driver(driverName, maxSleep);
            dispatch.addDriver(driver);
        }
        
        // Create passengers and book them
        Random rand = new Random();
        Object[] regionNames = regions.keySet().toArray();
        for (int i = 0; i < maxPassengers; i++) {
            String passengerName = Person.getRandomName();
            Passenger passenger = new Passenger(passengerName, maxSleep);
            String region = (String) regionNames[rand.nextInt(regionNames.length)];
            dispatch.bookPassenger(passenger, region);
        }
        
        // Assuming some mechanism to wait for all bookings to complete, for instance using Future objects.
        // This part will need refinement based on how bookings are handled in the system.
    }
}
