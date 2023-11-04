package nuber.students;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NuberDispatch {

    private final int MAX_DRIVERS = 999;
    private final HashMap<String, NuberRegion> regions;
    private final Queue<Driver> idleDrivers;
    private final Lock driverLock;
    
    private boolean logEvents;

    public NuberDispatch(HashMap<String, Integer> regionInfo, boolean logEvents) {
        this.logEvents = logEvents;
        this.regions = new HashMap<>();
        this.idleDrivers = new LinkedList<>();
        this.driverLock = new ReentrantLock();

        for (String regionName : regionInfo.keySet()) {
            NuberRegion region = new NuberRegion(this, regionName, regionInfo.get(regionName));
            regions.put(regionName, region);
        }
    }

    public void addDriver(Driver driver) {
        driverLock.lock();
        try {
            idleDrivers.offer(driver);
        } finally {
            driverLock.unlock();
        }
    }

    public Driver allocateDriver() throws InterruptedException {
        driverLock.lock();
        try {
            while (idleDrivers.isEmpty()) {
                // Wait for a driver to become available
                // Could use Conditions for better efficiency
                driverLock.unlock();
                Thread.sleep(100);  // Polling interval
                driverLock.lock();
            }
            return idleDrivers.poll();
        } finally {
            driverLock.unlock();
        }
    }

    public void bookPassenger(Passenger passenger, String regionName) throws InterruptedException {
        NuberRegion region = regions.get(regionName);
        if (region != null) {
            region.bookPassenger(passenger);
        }
    }

    public void reportEvent(String event) {
        if (logEvents) {
            System.out.println(event);
        }
    }

	public void shutdown() {
	}
}
