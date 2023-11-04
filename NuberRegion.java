package nuber.students;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class NuberRegion {

    private final NuberDispatch dispatch;
    private final String regionName;
    private final int maxSimultaneousJobs;
    private final Semaphore semaphore;
    private final Queue<Booking> bookingQueue;

    public NuberRegion(NuberDispatch dispatch, String regionName, int maxSimultaneousJobs) {
        this.dispatch = dispatch;
        this.regionName = regionName;
        this.maxSimultaneousJobs = maxSimultaneousJobs;
        this.semaphore = new Semaphore(maxSimultaneousJobs, true);  // Fair semaphore
        this.bookingQueue = new LinkedList<>();
    }

    public void bookPassenger(Passenger passenger) throws InterruptedException {
        Booking booking = new Booking(dispatch, passenger);
        bookingQueue.offer(booking);
        
        // Acquire a permit for a booking
        semaphore.acquire();
        try {
            booking.call();
        } finally {
            semaphore.release();
        }
    }
}