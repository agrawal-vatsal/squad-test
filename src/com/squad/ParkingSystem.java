package com.squad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class ParkingSystem {
    private HashMap<Car, Integer> carToSlotMap;
    private HashMap<Integer, HashSet<Integer>> ageToSlotChart;
    private HashMap<Integer, Car> slotToCarChart;
    private PriorityQueue<Integer> slotsAvailable;
    private int size;


    /* The constructor of the ParkingSystem class initializes all the variables of the class.
        The complexity of this constructor is O(n) where n is the size of the parking lot.
     */
    public ParkingSystem(int n) {
        ArrayList<Integer> slots = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            slots.add(i);
        slotsAvailable = new PriorityQueue<>(slots);
        carToSlotMap = new HashMap<>();
        slotToCarChart = new HashMap<>();
        ageToSlotChart = new HashMap<>();
        size = n;
    }


    /*
        This method is being used to add a new car to the parking lot.
        The user must ensure that there is enough space in the parking lot or not
        using the checkAvailabilty method of the same class.
        If the space is not checked then it could lead to an Exception in the code
        The complexity of this method is O(logn) where n is the size of the lot
     */
    public int addCar(String registrationId, Integer driverAge) {
        int spot = getSlot();
        Car car = new Car(registrationId, driverAge);
        carToSlotMap.put(car, spot);
        slotToCarChart.put(spot, car);
        if (!ageToSlotChart.containsKey(driverAge)) {
            ageToSlotChart.put(driverAge, new HashSet<>());
        }
        ageToSlotChart.get(driverAge).add(spot);
        return spot;
    }

    /*
        This method checks whether there is empty space available in the parking lot or not.
        It's time complexity is O(1)
     */

    public boolean checkAvailability() {
        return !slotsAvailable.isEmpty();
    }

    /*
        This method is used to take acquire a slot in the parking lot
        It's time complexity is O(logn)
     */

    private int getSlot() {
        return slotsAvailable.poll();
    }

    /*
        This method is used to get the list of slots that are acquired by people of certain age.
        It's time complexity is O(1)
     */

    public ArrayList<Integer> getSlotNumbersByAge(int driverAge) {
        if (ageToSlotChart.get(driverAge) == null)
            return null;
        return new ArrayList<>(ageToSlotChart.get(driverAge));
    }

    /*
        The method is used to find the slot number of a cark parked in the parking lot.
        It returns null if the car is not found in the parking lot.
        The time complexity of this method is O(1)
     */

    public Integer getSlotNumberByCar(String registrationId) {
        Car car = new Car(registrationId, 0);
        return carToSlotMap.getOrDefault(car, null);
    }

    /*
        This method is used to leave any spot in the parking lot.
        It gives the object of the car present at that slot and if it already empty then it returns null
        The complexity of this method is O(logn).
        It must be ensured by the user that before vacating any slot in the parking
        lot to check whether the slot is valid or not by using the isInRangeMethod
        of the same class failing which it will lead to unwanted results
    */

    public Car vacateSlot(int slot) {
        Car car = slotToCarChart.get(slot);
        if (car == null)
            return null;
        slotToCarChart.remove(slot);
        HashSet<Integer> slots = ageToSlotChart.get(car.getDriverAge());
        ageToSlotChart.get(car.getDriverAge()).remove(slot);
        slots.remove(slot);
        if (slots.isEmpty())
            ageToSlotChart.remove(car.getDriverAge());
        carToSlotMap.remove(car);
        slotsAvailable.add(slot);
        return car;
    }

    /*
        This method checks whether or not the slot is valid or not.
        It's time complexity is O(1).
     */

    public boolean isInRange(int slot) {
        return slot <= size;
    }

    /*
        This method returns the list of registration Id's of those cars whose driver's age has been provided.
        If there is no driver with that age then it will return null
        It's time complexity is O(1)
     */

    public ArrayList<String> getRegistrationNumberByAge(int driverAge) {
        ArrayList<Integer> slotNumbersByAge = getSlotNumbersByAge(driverAge);
        if (slotNumbersByAge == null)
            return null;
        ArrayList<String> registrationIds = new ArrayList<>();
        for (Integer slot : slotNumbersByAge) {
            registrationIds.add(slotToCarChart.get(slot).getRegistrationId());
        }
        if (registrationIds.isEmpty())
            return null;
        return registrationIds;
    }
}
