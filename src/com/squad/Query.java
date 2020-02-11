package com.squad;

import java.util.ArrayList;

public class Query {

    private static ParkingSystem parkingSystem;

    /*
    This method creates processes each query and calls the required function after knowing the type of query
     */

    public static void process(String query) {
        String[] queryParts = query.split(" ");
        try {
            if ("Create_parking_lot".equals(queryParts[0])) {
                int n = Integer.parseInt(queryParts[1]);
                createParkingLot(n);
            } else if ("Park".equals(queryParts[0])) {
                String registrationId = queryParts[1];
                int driverAge = Integer.parseInt(queryParts[3]);
                parkCar(registrationId, driverAge);
            } else if ("Slot_numbers_for_driver_of_age".equals(queryParts[0])) {
                int driverAge = Integer.parseInt(queryParts[1]);
                slotByAge(driverAge);
            } else if ("Slot_number_for_car_with_number".equals(queryParts[0])) {
                String registrationId = queryParts[1];
                slotByCar(registrationId);
            } else if ("Leave".equals(queryParts[0])) {
                int slot = Integer.parseInt(queryParts[1]);
                vacateSlot(slot);
            } else if ("Vehicle_registration_number_for_driver_of_age".equals(queryParts[0])) {
                int slot = Integer.parseInt(queryParts[1]);
                registrationIdByAge(slot);
            } else {
                System.out.println("Invalid command");
            }
        } catch (Exception e) {
            System.out.println("Invalid command");
        }
    }

    // This method creates a new parking lot system with n size

    private static void createParkingLot(int n) {
        parkingSystem = new ParkingSystem(n);
        System.out.println("Created parking of " + n + " slots");
    }

    /* This method checks whether there is space in the parking lot or not
        and if space is available parks the car at the nearest location to the entry
        and prints it's details
     */

    private static void parkCar(String registrationId, int driverAge) {
        if (!parkingSystem.checkAvailability()) {
            System.out.println("Parking lot already full");
            return;
        }
        int spot = parkingSystem.addCar(registrationId, driverAge);
        System.out.println(
                "Car with vehicle registration number “" + registrationId + "” has been parked at slot number " + spot);
    }

    /*
        This method prints the list of all the slots acquired by drivers of a certain age
        and if there is no driver present then it prints null
     */

    private static void slotByAge(int driverAge) {
        ArrayList<Integer> spots = parkingSystem.getSlotNumbersByAge(driverAge);
        if (spots == null) {
            System.out.println("null");
            return;
        }
        for (int i = 0; i < spots.size(); i++) {
            System.out.print(spots.get(i));
            if (i != spots.size() - 1)
                System.out.print(",");
        }
        System.out.println();
    }

    /*
        This method finds the slot of the parked car based on it's registration Id
        and if no car is parked with that registration Id then it prints null
     */

    private static void slotByCar(String registrationId) {
        Integer spot = parkingSystem.getSlotNumberByCar(registrationId);
        if (spot == null)
            System.out.println("null");
        else System.out.println(spot);
    }

    /*
        This method first checks whether the inputted slot is valid or not
        If it is valid then it vacates that spot, if a car is parked at that slot then the details of the car are printed
     */

    private static void vacateSlot(int slot) {
        if (!parkingSystem.isInRange(slot)) {
            System.out.println("Invalid slot number");
            return;
        }
        Car car = parkingSystem.vacateSlot(slot);
        if (car == null) {
            System.out.println("Slot already vacant");
        } else System.out.println("Slot number " + slot + " vacated, the car with vehicle registration number “"
                                  + car.getRegistrationId()
                                  + "” left the space, the driver of the car was of age " + car.getDriverAge());
    }

    /*
    This method is used to print all the registration numbers of the driver with a certain age
    If there is no driver of that age then null is printed
     */

    private static void registrationIdByAge(int driverAge) {
        ArrayList<String> registrationNumbers = parkingSystem.getRegistrationNumberByAge(driverAge);
        if (registrationNumbers == null) {
            System.out.println("null");
            return;
        }
        for (int i = 0; i < registrationNumbers.size(); i++) {
            System.out.print(registrationNumbers.get(i));
            if (i != registrationNumbers.size() - 1)
                System.out.print(", ");
        }
        System.out.println();
    }
}
