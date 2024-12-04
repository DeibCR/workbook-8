package dealershipAppJDBC;

public class MainApp {


    public static void main(String[] args) {
        ContractDataManager contractDataManager = new ContractDataManager();
        UserInterface userInterface = new UserInterface();
        userInterface.display();


    }

}
