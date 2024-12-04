package model;

import model.Contract;
import model.Vehicle;

import java.util.ResourceBundle;

public class SalesContract extends Contract {
    private  final double salesTax;
    private final double recordingFee;
    private final double processingFee;
    private final boolean isFinance;
    private static final ResourceBundle resourceBundle= ResourceBundle.getBundle("contract_config");

    public SalesContract(String dateOfContract, String customerName, String customerEmail, Vehicle vehicleSold, double salesTax, double recordingFee, double processingFee, boolean isFinance) {
        super(dateOfContract, customerName, customerEmail, vehicleSold);
        this.salesTax = Double.parseDouble(resourceBundle.getString("sales.tax"));
        this.recordingFee = Double.parseDouble(resourceBundle.getString("recording.fee"));
        this.processingFee = vehicleSold.getPrice()>10000 ?
                Double.parseDouble(resourceBundle.getString("processing.fee.under10000")) :
                Double.parseDouble(resourceBundle.getString("processing.fee.over10000"));

        this.isFinance = isFinance;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }


    public double getProcessingFee() {
        return processingFee;
    }


    public boolean isFinance() {
        return isFinance;
    }



    @Override
    public double getTotalPrice() {
        double basePrice= getVehicleSold().getPrice();
        double total= basePrice + ( basePrice * salesTax )+ recordingFee+processingFee;
        return total;
    }

    @Override
    public double getMonthlyPayment() {
        if (!isFinance) {
            return 0;
        }
        double interestRate = getVehicleSold().getPrice() >= 10000
                ? Double.parseDouble(resourceBundle.getString("monthly.payment.interest.over10000"))
                : Double.parseDouble(resourceBundle.getString("monthly.payment.interest.under10000"));
        int termMonths = getVehicleSold().getPrice() >= 10000
                ? Integer.parseInt(resourceBundle.getString("monthly.payment.term.over10000"))
                : Integer.parseInt(resourceBundle.getString("monthly.payment.term.under10000"));

        return (getVehicleSold().getPrice() * (1 + interestRate)) / termMonths;

    }


    @Override
    public String getRepresentation() {
       // return String.format("SALE|%s|%s|%s|%d|%s|%d|%.2f|%.2f|%.2f|%.2f|%b",
               //// getDateOfContract(), getCustomerName(), getCustomerEmail(),
               // getVehicleSold().getVin(), getVehicleSold().getMake(),
                //getVehicleSold().getYear(), salesTax, recordingFee, processingFee, getTotalPrice(), isFinance);
        Vehicle vehicle = getVehicleSold();
        return String.format("SALE|%s|%s|%s|%s|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                getDateOfContract(),
                getCustomerName(),
                getCustomerEmail(),
                vehicle.getVin(),
                vehicle.getYear(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getVehicleType(),
                vehicle.getColor(),
                vehicle.getOdometer(),
                vehicle.getPrice(),
                getSalesTax(),
                getRecordingFee(),
                getProcessingFee(),
                getTotalPrice(),
                "NO",
                0.00 );
    }
}

