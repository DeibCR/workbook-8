package forms;

import model.Dealership;
import dealershipAppJDBC.DealershipFileManager;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DealershipGUI extends JFrame {
    private JPanel mainPanel;
    private JButton viewVehiclesButton;
    private JButton addVehicleButton;
    private JButton removeVehicleButton;
    private JButton searchByPriceButton;
    private JButton searchByYearButton;
    private JButton searchByColorButton;
    private JButton searchByTypeButton;
    private JButton searchByMakeAndButton;
    private JButton searchByMileageButton;
    private Dealership dealership;
    private DealershipFileManager fileManager;


    public DealershipGUI() {
        fileManager = new DealershipFileManager();
        dealership = fileManager.getDealership("./src/main/resources/inventory.csv", "D & B Used Cars", "111 Old Benbrook Rd", "817-555-5555");

        setTitle("Dealership Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLayout(new BorderLayout());
        setContentPane(mainPanel);
        setVisible(true);


        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 2));


        viewVehiclesButton.addActionListener(e -> {
            JTextArea textArea = new JTextArea(15, 30);
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                textArea.append(vehicle.toString() + "\n");
            }
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300)); // Width x Height
            JOptionPane.showMessageDialog(null, scrollPane, "All Vehicles", JOptionPane.INFORMATION_MESSAGE);

        });


        addVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField vinField = new JTextField();
                JTextField yearField = new JTextField();
                JTextField makeField = new JTextField();
                JTextField modelField = new JTextField();
                JTextField typeField = new JTextField();
                JTextField colorField = new JTextField();
                JTextField odometerField = new JTextField();
                JTextField priceField = new JTextField();

                Object[] fields = {
                        "VIN:", vinField,
                        "Year:", yearField,
                        "Make:", makeField,
                        "Model:", modelField,
                        "Type:", typeField,
                        "Color:", colorField,
                        "Odometer:", odometerField,
                        "Price:", priceField
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Add New Vehicle", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        Vehicle vehicle = new Vehicle(
                                Integer.parseInt(vinField.getText()),
                                Integer.parseInt(yearField.getText()),
                                makeField.getText(),
                                modelField.getText(),
                                typeField.getText(),
                                colorField.getText(),
                                Integer.parseInt(odometerField.getText()),
                                Double.parseDouble(priceField.getText())
                        );
                        dealership.addVehicle(vehicle);
                        JOptionPane.showMessageDialog(null, "Vehicle added successfully.");
                        fileManager.saveDealership(dealership, "./src/main/resources/inventory.csv");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        removeVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vin = JOptionPane.showInputDialog("Enter VIN of the vehicle to remove:");
                if (vin != null) {
                    Vehicle vehicleToRemove = null;
                    for (Vehicle vehicle : dealership.getAllVehicles()) {
                        if (String.valueOf(vehicle.getVin()).equals(vin)) {
                            vehicleToRemove = vehicle;
                            break;
                        }
                    }
                    if (vehicleToRemove != null) {
                        dealership.removeVehicle(vehicleToRemove);
                        JOptionPane.showMessageDialog(null, "Vehicle removed successfully.");
                        fileManager.saveDealership(dealership, "./src/main/resources/inventory.csv");
                    } else {
                        JOptionPane.showMessageDialog(null, "Vehicle not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
        searchByPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField minPriceField = new JTextField();
                JTextField maxPriceField = new JTextField();

                Object[] fields = {
                        "Minimum Price:", minPriceField,
                        "Maximum Price:", maxPriceField
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Search by Price", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        double minPrice = Double.parseDouble(minPriceField.getText());
                        double maxPrice = Double.parseDouble(maxPriceField.getText());

                        JTextArea textArea = new JTextArea(15, 30);
                        dealership.getVehiclesByPrice(minPrice, maxPrice).forEach(vehicle -> textArea.append(vehicle + "\n"));
                        JScrollPane scrollPane = new JScrollPane(textArea);

                        JOptionPane.showMessageDialog(null, scrollPane, "Vehicles in Price Range", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numbers for price range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }


            }
        });
        searchByYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        searchByColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchByTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchByMileageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchByMakeAndButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DealershipGUI gui = new DealershipGUI();
            gui.setVisible(true);
        });
    }


}
