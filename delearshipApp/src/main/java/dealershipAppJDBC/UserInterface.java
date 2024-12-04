package dealershipAppJDBC;

import model.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.text.MessageFormat;

public class UserInterface {
    private final DealershipFileManager fileManager;
    private final ContractDataManager contractDataManager;
    private Dealership dealership;
    private final Scanner scanner;
    private static final ResourceBundle rB = ResourceBundle.getBundle("messages");

    public UserInterface() {

        fileManager = new DealershipFileManager();
        contractDataManager = new ContractDataManager();
        scanner = new Scanner(System.in);
    }

    private void init() {

        dealership = fileManager.getDealership("./src/main/resources/inventory.csv", "D & B Used Cars", "111 Old Benbrook Rd", "817-555-5555");
        List<Contract> contracts = contractDataManager.getContract("./src/main/resources/contracts.csv");
        contracts.forEach(dealership::addContract);
    }

    public void display() {
        init();

        boolean exit = false;
        while (!exit) {


            displayMenu();
            int input = getUserInput();
            exit = processInput(input);
        }
    }

    private void displayMenu() {
        System.out.println(rB.getString("menu.borderLine"));
        System.out.println(rB.getString("menu.title"));

        String header = MessageFormat.format(rB.getString("menu.header"),
                dealership.getName(),
                dealership.getAddress(),
                dealership.getPhone()
        );
        System.out.println(header);

        System.out.println(rB.getString("menu.borderLine"));

        for (int i = Integer.parseInt(rB.getString("first.option")); i <= Integer.parseInt(rB.getString("last.option")); i++) {
            String optionKey = "menu.option" + i;
            System.out.println(rB.getString(optionKey));
        }
        System.out.print(rB.getString("menu.prompt"));
    }

    private int getUserInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(rB.getString("error.input"));
            return -1;
        }
    }

    private boolean processInput(int input) {
        switch (input) {
            case 1:
                getAllVehicles();
                return false;
            case 2:
                addVehicleRequest();
                return false;
            case 3:
                removeVehicle();
                return false;
            case 4:
                searchByPrice();
                return false;
            case 5:
                searchByMakeAndModel();
                return false;
            case 6:
                searchByColor();
                return false;
            case 7:
                searchByType();
                return false;
            case 8:
                searchByMileage();
                return false;
            case 9:
                searchByYear();
                return false;
            case 10:
                getAllContracts();
                return false;
            case 11:
                addContractRequest();
                return false;
            case 12:
                System.out.println(rB.getString("exit.output"));
                return true;
            default:
                System.out.println(rB.getString("error.input"));
                return false;
        }
    }

    public void getAllVehicles() {
        headerDisplay();
        dealership.getAllVehicles().forEach(System.out::println);
    }

    public void getAllContracts() {
        List<Contract> contracts = dealership.getAllContracts();
        if (contracts.isEmpty()) {
            System.out.println(rB.getString("contracts.error"));
        } else {
            headerDisplayContracts();
            contracts.forEach(contract -> System.out.println(contract.getRepresentation()));
        }
    }

    public void addContractRequest() {
        try {
            String dateOfContract = promptForString(rB.getString("contract.date"));
            String customerName = promptForString(rB.getString("contract.name"));
            String customerEmail = promptForString(rB.getString("contract.email"));

            int vin = promptForInt(rB.getString("contract.vin")); // to get the vehicle
            Vehicle vehicleSold = dealership.getAllVehicles().stream()
                    .filter(vehicle -> vehicle.getVin() == vin)
                    .findFirst()
                    .orElse(null);

            if (vehicleSold == null) {
                System.out.println(rB.getString("contract.vin.error"));
                return;
            }

            String contractType = promptForString(rB.getString("contract.type")).toLowerCase();

            Contract newContract;
            if (contractType.equals("sale")) {
                boolean isFinance = promptForString(rB.getString("contract.finance")).equalsIgnoreCase("yes");

                newContract = new SalesContract(dateOfContract, customerName, customerEmail, vehicleSold, 0.0, 0.0, 0.0, isFinance);

            } else if (contractType.equals("lease")) {

                newContract = new LeaseContract(dateOfContract, customerName, customerEmail, vehicleSold, 0.0, 0.0);

            } else {
                System.out.println(rB.getString("error.contract.type"));
                return;
            }
            dealership.addContract(newContract);
            List<Contract> updatedContracts = dealership.getAllContracts();
            contractDataManager.saveContracts(updatedContracts, "./src/main/resources/contracts.csv");
            dealership.removeVehicle(vehicleSold);
            saveDealershipData();
            System.out.println(rB.getString("contract.added"));

        } catch (NumberFormatException e) {
            System.out.println(rB.getString("contract.error1"));

        }
    }

    private void addVehicleRequest() {
        try {
            int id = promptForInt(rB.getString("vin.request"));
            int year = promptForInt(rB.getString("year.request"));
            String make = promptForString(rB.getString("make.request"));
            String model = promptForString(rB.getString("model.request"));
            String type = promptForString(rB.getString("type.request"));
            String color = promptForString(rB.getString("color.request"));
            int mileage = promptForInt(rB.getString("mileage.request"));
            double price = promptForDouble(rB.getString("price.request"));

            Vehicle newVehicle = new Vehicle(id, year, make, model, type, color, mileage, price);
            dealership.addVehicle(newVehicle);
            System.out.println(rB.getString("added.request"));
            saveDealershipData();
        } catch (NumberFormatException e) {
            System.out.println(rB.getString("request.error"));
        }
    }

    private void removeVehicle() {
        try {
            int vin = promptForInt(rB.getString("remove.vin.request"));
            Vehicle vehicleToRemove = dealership.getAllVehicles().stream()
                    .filter(vehicle -> vehicle.getVin() == vin)
                    .findFirst()
                    .orElse(null);

            if (vehicleToRemove != null) {
                dealership.removeVehicle(vehicleToRemove);
                System.out.println((rB.getString("remove1")) + vin + (rB.getString("remove2")));
                saveDealershipData();
            } else {
                System.out.println(rB.getString("remove.error1"));
            }
        } catch (NumberFormatException e) {
            System.out.println(rB.getString("remove.error2"));
        }
    }

    private void searchByPrice() {
        double minPrice = promptForDouble(rB.getString("search.price1"));
        double maxPrice = promptForDouble(rB.getString("search.price2"));
        headerDisplay();
        dealership.getVehiclesByPrice(minPrice, maxPrice).forEach(System.out::println);
    }


    private void searchByMakeAndModel() {
        String make = promptForString(rB.getString("search.make"));
        String model = promptForString(rB.getString("search.model"));
        headerDisplay();
        dealership.getVehiclesByMakeModel(make, model).forEach(System.out::println);
    }

    private void searchByColor() {
        String color = promptForString(rB.getString("search.color"));
        headerDisplay();
        dealership.getVehiclesByColor(color).forEach(System.out::println);
    }

    private void searchByType() {
        String type = promptForString(rB.getString("search.type"));
        headerDisplay();
        dealership.getVehiclesByType(type).forEach(System.out::println);
    }

    private void searchByMileage() {
        int minMileage = promptForInt(rB.getString("search.mileage1"));
        int maxMileage = promptForInt(rB.getString("search.mileage2"));
        headerDisplay();
        dealership.getVehiclesByMileage(minMileage, maxMileage).forEach(System.out::println);

    }

    private void searchByYear() {
        int year = promptForInt(rB.getString("search.year"));
        headerDisplay();
        dealership.getVehiclesByYear(year).forEach(System.out::println);
    }

    //helpers methods to clean the code:

    public void headerDisplay() {
        System.out.println(rB.getString("header.display"));
    }

    private void headerDisplayContracts() {
        //TODO: modify the display of this header to match the values

        System.out.println("""
                --------------------------------------------------------------------------------------------
                                                All Contracts
                Date       Customer Name   Customer Email           Vehicle Sold
                --------------------------------------------------------------------------------------------
                """);
    }

    /* these methods help  for those repetitive actions, the simple actions that you always need to do
      help to reduce visual noise
   */


    private int promptForInt(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    private String promptForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private double promptForDouble(String message) {
        System.out.print(message);
        return Double.parseDouble(scanner.nextLine());
    }

    private void saveDealershipData() {
        fileManager.saveDealership(dealership, "./src/main/resources/inventory.csv");
    }


}
