package nuber.students;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Booking {

    private static final AtomicInteger uniqueId = new AtomicInteger(0);  // for generating unique, sequential IDs
    
    private final int bookingId;
    private final NuberDispatch dispatch;
    private final Passenger passenger;
    private Date startTime;
    private Driver driver;

    public Booking(NuberDispatch dispatch, Passenger passenger) {
        this.bookingId = uniqueId.incrementAndGet();
        this.dispatch = dispatch;
        this.passenger = passenger;
        this.startTime = new Date();
    }

    /**
     * Commence the booking by allocating a driver and taking the passenger to their destination.
     */
    public BookingResult call() throws InterruptedException {
        driver = dispatch.allocateDriver();  // Assuming there's a method to allocate a driver
        driver.pickUpPassenger(passenger, bookingId);
        driver.driveToDestination();
        
        // Calculate the total duration
        long duration = new Date().getTime() - startTime.getTime();
        
        // Return the booking result
        return new BookingResult(bookingId, passenger, driver, duration);
    }
}
