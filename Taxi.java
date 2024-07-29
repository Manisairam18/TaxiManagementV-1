package Taxibookingversion1;

import java.util.*;

public class Taxi {

    private static int taxiCount = 0; // Taxi number
    int id;
    private boolean booked; // Taxi booked or not
    char currentSpot; // Where taxi is now
    int freeTime; // When taxi becomes free
    int totalEarnings; // Total earnings of taxi
    private List<String> trips; // All details of all trips by this taxi

    public Taxi() {
        booked = false;
        currentSpot = 'A'; // Start point A
        freeTime = 6; // Example 6 AM
        totalEarnings = 0;
        trips = new ArrayList<>();
        taxiCount++;
        id = taxiCount;
    }

    public void setDetails(boolean booked, char currentSpot, int freeTime, int totalEarnings, String tripDetail) {
        this.booked = booked;
        this.currentSpot = currentSpot;
        this.freeTime = freeTime;
        this.totalEarnings = totalEarnings;
        this.trips.add(tripDetail);
    }

    public void printDetails() {
        // Print all trip details
        System.out.println("Taxi ID    Booking ID    Customer ID    From    To    Pickup Time    Drop Time    Amount");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (String trip : trips) {
            System.out.println(String.format("%-9d %-12s", id, trip));
        }
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    public void printTaxiDetails() {
        // Print total earnings and taxi details like current location and free time
        System.out.println(String.format("Taxi ID: %d | Total Earnings: %d | Current Spot: %c | Free Time: %d",
                id, totalEarnings, currentSpot, freeTime));
    }
}
