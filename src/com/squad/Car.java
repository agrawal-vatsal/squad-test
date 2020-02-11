package com.squad;

public class Car {

    // This specifies the unique registration Id of the car
    private String registrationId;

    // This specifies the age of the driver
    private Integer driverAge;

    public Car(String registrationId, Integer driverAge) {
        this.registrationId = registrationId;
        this.driverAge = driverAge;
    }


    /* This method is overridden because the car is being searched by the hashmap
    and the hashmap uses the hashcode to calculate the bucket of the object.
     */
    @Override
    public int hashCode() {
        return this.registrationId.hashCode();
    }


    /* This method is overriden because the cars have a unique id in the form of registration Id
    and hence the registration Id can be used to compare whether or not the two objects of the class
    belong to the same car or not.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Car) {
            return this.registrationId.equals(((Car) o).registrationId);
        }
        return false;
    }

    public int getDriverAge() {
        return this.driverAge;
    }

    public String getRegistrationId() {
        return this.registrationId;
    }
}
