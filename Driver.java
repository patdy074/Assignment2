package nuber.students;

import java.util.Random;

public class Driver extends Person {

    private Passenger currentPassenger;
    private Random random;

    public Driver(String driverName, int maxSleep) {
        super(driverName, maxSleep);
        this.random = new Random();
    }

    /**
     * Stores the provided passenger as the driver's current passenger and then
     * sleeps the thread for between 0-maxDelay milliseconds.
     * 
     * @param newPassenger Passenger to collect
     * @param maxDelay 
     * @throws InterruptedException
     */
    public void pickUpPassenger(Passenger newPassenger, int maxDelay) throws InterruptedException {
        this.currentPassenger = newPassenger;
        int sleepTime = maxDelay > 0 ? random.nextInt(maxDelay) : 1;
        Thread.sleep(sleepTime);
    }

    /**
     * Sleeps the thread for the amount of time returned by the current 
     * passenger's getTravelTime() function
     * 
     * @throws InterruptedException
     */
    public void driveToDestination() throws InterruptedException {
        Thread.sleep(currentPassenger.getTravelTime());
    }
}
