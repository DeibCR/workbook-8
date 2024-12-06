package dao;

import model.Vehicle;

import java.util.List;

public interface VehicleDAO {

    Vehicle findVehicleByPrice(double maxPrice, double minPrice);
    Vehicle findVehicleByMakeModel(String make, String model);
    Vehicle findVehicleByYear(int maxYear, int minYear);
    Vehicle findVehicleByColor(String color);
    Vehicle findVehicleByOdometer(int maxOdometer, int minOdometer);
    Vehicle findVehicleByType(String vehicleType);

    List<Vehicle> findAllVehicles();

}
