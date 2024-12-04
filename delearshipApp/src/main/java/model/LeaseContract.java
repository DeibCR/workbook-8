package model;

import java.util.ResourceBundle;

public class LeaseContract extends Contract {
    private final double expectedEndingValuePercentage;
    private final double leaseFee;

    private static final ResourceBundle resourceBundle= ResourceBundle.getBundle("contract_config");// Using resource bundle to provide flexibility and maintainability

    public LeaseContract(String dateOfContract, String customerName, String customerEmail, Vehicle vehicleSold, double expectedEndingValuePercentage, double leaseFee) {
        super(dateOfContract, customerName, customerEmail, vehicleSold);
        this.expectedEndingValuePercentage = vehicleSold.getPrice() *
                Double.parseDouble(resourceBundle.getString("lease.expected.ending.value.percentage"));
        this.leaseFee = vehicleSold.getPrice() *
                Double.parseDouble(resourceBundle.getString("lease.fee"));
    }

    public double getExpectedEndingValuePercentage() {
        return expectedEndingValuePercentage;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    @Override
    public double getTotalPrice() {
        return (getVehicleSold().getPrice()-expectedEndingValuePercentage)+leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        double interestRate = Double.parseDouble(resourceBundle.getString("lease.monthly.payment.interest"));
        int termMonths = Integer.parseInt(resourceBundle.getString("lease.monthly.payment.term"));


        double principalAmount = getVehicleSold().getPrice() - expectedEndingValuePercentage;
        return (principalAmount + leaseFee * interestRate) / termMonths;
    }

    @Override
    public String getRepresentation() {
        //return String.format("LEASE|%s|%s|%s|%s|%.2f|%.2f|%.2f",
               // getDateOfContract(), getCustomerName(), getCustomerEmail(),
               // getVehicleSold().toString(), expectedEndingValuePercentage, leaseFee, getTotalPrice());
        return String.format("LEASE|%s|%s|%s|%s|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f",
                getDateOfContract(),
                getCustomerName(),
                getCustomerEmail(),
                getVehicleSold().getVin(),
                getVehicleSold().getYear(),
                getVehicleSold().getMake(),
                getVehicleSold().getModel(),
                getVehicleSold().getVehicleType(),
                getVehicleSold().getColor(),
                getVehicleSold().getOdometer(),
                getVehicleSold().getPrice(),
                getExpectedEndingValuePercentage(),
                getLeaseFee(),
                getTotalPrice(),
                getMonthlyPayment());
    }
}
