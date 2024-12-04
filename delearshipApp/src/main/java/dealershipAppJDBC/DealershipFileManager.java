package dealershipAppJDBC;

import model.Dealership;
import model.Vehicle;

import java.io.*;
import java.util.ArrayList;

public class DealershipFileManager {

    public Dealership getDealership(String filename, String name, String address, String phone) {
        ArrayList<Vehicle> inventory = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                int vin = Integer.parseInt(data[0]);
                int year = Integer.parseInt(data[1]);
                String make = data[2];
                String model = data[3];
                String type = data[4];
                String color = data[5];
                int odometer = Integer.parseInt(data[6]);
                double price = Double.parseDouble(data[7]);
                inventory.add(new Vehicle(vin, year, make, model, type, color, odometer, price));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return new Dealership(name, address, phone, inventory);

    }

    public void saveDealership(Dealership dealerShip, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            bw.write(String.format("%s|%s|%s\n", dealerShip.getName(), dealerShip.getAddress(), dealerShip.getPhone()));

            for (Vehicle vehicle : dealerShip.getAllVehicles()) {
                String data = String.join("|",
                        String.valueOf(vehicle.getVin()),
                        String.valueOf(vehicle.getYear()),
                        vehicle.getMake(),
                        vehicle.getModel(),
                        vehicle.getVehicleType(),
                        vehicle.getColor(),
                        String.valueOf(vehicle.getOdometer()),
                        String.valueOf(vehicle.getPrice())
                );
                bw.write(data);
                bw.newLine();

            }


        } catch (IOException e) {
            System.out.println("Error saving the dealership  to file");
        }
    }
}
