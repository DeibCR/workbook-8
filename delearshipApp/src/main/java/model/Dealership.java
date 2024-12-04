package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Dealership {
    private final String name;
    private final String address;
    private final String phone;

    private final ArrayList<Vehicle> inventory;
    private final ArrayList<Contract> contracts;



    public Dealership(String name, String address, String phone, ArrayList<Vehicle> inventory) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = inventory;
        this.contracts = new ArrayList<>();
    }



    public List<Vehicle> getAllVehicles(){
        List<Vehicle> reversedVehicles = new ArrayList<>(inventory);
        Collections.reverse(reversedVehicles);

        return reversedVehicles;
    }

    public void addVehicle(Vehicle vehicle){
        if (vehicle == null){
            throw new IllegalArgumentException("a vehicle cannot be null");

        }
        inventory.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle){
        if (vehicle != null && inventory.contains(vehicle)){
            inventory.remove(vehicle);
        }else {
            throw  new IllegalArgumentException("Vehicle not found");
        }

    }

    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getPrice() >= min && vehicle.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getMake().equalsIgnoreCase(make) && vehicle.getModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByYear(int year) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getYear() == year)
                .collect(Collectors.toList());
    }


    public List<Vehicle> getVehiclesByColor(String color){
        return inventory.stream()
                .filter(vehicle -> vehicle.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getOdometer() >= min && vehicle.getOdometer() <= max)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByType(String vehicleType) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getVehicleType().equalsIgnoreCase(vehicleType))
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void addContract(Contract contract) {
        if (contract == null) {
            throw new IllegalArgumentException("A contract cannot be null");
        }
        contracts.add(contract);
    }

    public List<Contract> getAllContracts() {
        return new ArrayList<>(contracts);
    }
}


