package dealershipAppJDBC;

import model.Contract;
import model.LeaseContract;
import model.SalesContract;
import model.Vehicle;

import java.io.BufferedReader;
import java.io.*;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;

public class ContractDataManager {

    public List<Contract> getContract(String fileName) {
        List<Contract> contracts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                //contract type
                String contractType = data[0];
                String dateOfContract = data[1];
                String customerName = data[2];
                String customerEmail = data[3];

                //vehicle
                int vin = Integer.parseInt(data[4]);
                int year = Integer.parseInt(data[5]);
                String make = data[6];
                String model = data[7];
                String vehicleType = data[8];
                String color = data[9];
                int odometer = Integer.parseInt(data[10]);
                double price = Double.parseDouble(data[11]);
                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

                //contract base on type

                if ("SALE".equalsIgnoreCase(contractType)) {
                    double salesTax = Double.parseDouble(data[12]);
                    double recordingFee = Double.parseDouble(data[13]);
                    double processingFee = Double.parseDouble(data[14]);
                    boolean isFinance = "YES".equalsIgnoreCase(data[15]);

                    Contract saleContract = new SalesContract(dateOfContract, customerName, customerEmail, vehicle,
                            salesTax, recordingFee, processingFee, isFinance);
                    contracts.add(saleContract);
                } else if ("LEASE".equalsIgnoreCase(contractType)) {
                    double expectedEndingValuePercentage = Double.parseDouble(data[12]);
                    double leaseFee = Double.parseDouble(data[13]);

                    Contract leaseContract = new LeaseContract(dateOfContract, customerName, customerEmail, vehicle,
                            expectedEndingValuePercentage, leaseFee);
                    contracts.add(leaseContract);

                }

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading the contract file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return contracts;

    }

    public void saveContracts(List<Contract> contracts, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (Contract contract : contracts) {
                String data;
                if (contract instanceof SalesContract) {
                    SalesContract saleContract = (SalesContract) contract;
                    data = String.format("SALE|%s|%s|%s|%s|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%b|%.2f",
                            saleContract.getDateOfContract(),
                            saleContract.getCustomerName(),
                            saleContract.getCustomerEmail(),
                            saleContract.getVehicleSold().getVin(),
                            saleContract.getVehicleSold().getYear(),
                            saleContract.getVehicleSold().getMake(),
                            saleContract.getVehicleSold().getModel(),
                            saleContract.getVehicleSold().getVehicleType(),
                            saleContract.getVehicleSold().getColor(),
                            saleContract.getVehicleSold().getOdometer(),
                            saleContract.getVehicleSold().getPrice(),
                            saleContract.getSalesTax(),
                            saleContract.getRecordingFee(),
                            saleContract.getProcessingFee(),
                            saleContract.isFinance()? "YES" : "NO", //TODO: FIX THE DISPLAY FOR YES/NO instead of TRUE/FALSE
                            saleContract.getTotalPrice());
                } else if (contract instanceof LeaseContract) {
                    LeaseContract leaseContract = (LeaseContract) contract;
                    data = String.format("LEASE|%s|%s|%s|%s|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f",
                            leaseContract.getDateOfContract(),
                            leaseContract.getCustomerName(),
                            leaseContract.getCustomerEmail(),
                            leaseContract.getVehicleSold().getVin(),
                            leaseContract.getVehicleSold().getYear(),
                            leaseContract.getVehicleSold().getMake(),
                            leaseContract.getVehicleSold().getModel(),
                            leaseContract.getVehicleSold().getVehicleType(),
                            leaseContract.getVehicleSold().getColor(),
                            leaseContract.getVehicleSold().getOdometer(),
                            leaseContract.getVehicleSold().getPrice(),
                            leaseContract.getExpectedEndingValuePercentage(),
                            leaseContract.getLeaseFee());
                } else {
                    continue;
                }
                bw.write(data);
                bw.newLine();
                bw.close();
            }

        } catch (IOException e) {
            System.out.println("Error saving contracts to file: " + e.getMessage());
        }
    }
}
