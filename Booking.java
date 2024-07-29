package Taxibookingversion1;

import java.util.*;

public class Booking {

    public static void bookTaxi(int customerID, char pickupPoint, char dropPoint, int pickupTime, List<Taxi> freeTaxis) {
        // Initialize variables to find the best taxi
        int minDistance = Integer.MAX_VALUE;
        int distanceBetweenPickupAndDrop = 0;
        int earning = 0;
        int nextFreeTime = 0;
        char nextSpot = 'Z';
        Taxi selectedTaxi = null;
        String tripDetail = "";

        for (Taxi taxi : freeTaxis) {
            // Calculate distance from the taxi's current location to the pickup point
            int distanceToPickup = Math.abs((taxi.currentSpot - '0') - (pickupPoint - '0')) * 15;

            if (distanceToPickup < minDistance) {
                // Calculate distance between pickup and drop points
                distanceBetweenPickupAndDrop = Math.abs((dropPoint - '0') - (pickupPoint - '0')) * 15;
                // Calculate earnings based on the distance
                earning = (distanceBetweenPickupAndDrop - 5) * 10 + 100;

                // Calculate drop time
                int dropTime = pickupTime + distanceBetweenPickupAndDrop / 15;

                // Update taxi details
                nextFreeTime = dropTime;
                nextSpot = dropPoint;

                // Update trip details
                tripDetail = String.format("%d               %d          %c      %c       %d          %d           %d",
                        customerID, customerID, pickupPoint, dropPoint, pickupTime, dropTime, earning);

                // Select the best taxi
                selectedTaxi = taxi;
                minDistance = distanceToPickup;
            }
        }

        if (selectedTaxi != null) {
            // Set details for the selected taxi
            selectedTaxi.setDetails(true, nextSpot, nextFreeTime, selectedTaxi.totalEarnings + earning, tripDetail);
            // Confirm booking
            System.out.println("Taxi " + selectedTaxi.id + " booked successfully.");
        } else {
            System.out.println("No suitable taxi available.");
        }
    }

    public static List<Taxi> createTaxis(int count) {
        List<Taxi> taxis = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Taxi taxi = new Taxi();
            taxis.add(taxi);
        }
        return taxis;
    }

    public static List<Taxi> getFreeTaxis(List<Taxi> taxis, int pickupTime, char pickupPoint) {
        List<Taxi> freeTaxis = new ArrayList<>();
        for (Taxi taxi : taxis) {
            // Check if taxi is free and can reach the pickup point on time
            if (taxi.freeTime <= pickupTime &&
                Math.abs((taxi.currentSpot - '0') - (pickupPoint - '0')) <= pickupTime - taxi.freeTime) {
                freeTaxis.add(taxi);
            }
        }
        return freeTaxis;
    }

    public static void main(String[] args) {
        // Create a list of taxis
        List<Taxi> taxis = createTaxis(4);
        Scanner scanner = new Scanner(System.in);
        int customerID = 1;

        while (true) {
            System.out.println("1 -> Book Taxi");
            System.out.println("2 -> Print Taxi Details");
            System.out.println("3 -> Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Get details from customer
                    System.out.println("Enter Pickup point (A-F):");
                    char pickupPoint = scanner.next().toUpperCase().charAt(0);
                    System.out.println("Enter Drop point (A-F):");
                    char dropPoint = scanner.next().toUpperCase().charAt(0);
                    System.out.println("Enter Pickup time (in minutes):");
                    int pickupTime = scanner.nextInt();

                    // Validate pickup and drop points
                    if (pickupPoint < 'A' || pickupPoint > 'F' || dropPoint < 'A' || dropPoint > 'F') {
                        System.out.println("Invalid pickup or drop point. Valid points are A, B, C, D, E, F. Exiting.");
                        return;
                    }

                    // Get all free taxis that can reach the customer on or before pickup time
                    List<Taxi> freeTaxis = getFreeTaxis(taxis, pickupTime, pickupPoint);

                    if (freeTaxis.isEmpty()) {
                        System.out.println("No available taxis can be allocated. Exiting.");
                        return;
                    }

                    // Sort taxis by total earnings
                    freeTaxis.sort(Comparator.comparingInt(taxi -> taxi.totalEarnings));

                    // Book a taxi
                    bookTaxi(customerID, pickupPoint, dropPoint, pickupTime, freeTaxis);
                    customerID++;
                    break;

                case 2:
                    // Print taxi details
                    for (Taxi taxi : taxis) {
                        taxi.printTaxiDetails();
                    }
                    break;

                case 3:
                    System.out.println("Exiting the application. Thank you!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
