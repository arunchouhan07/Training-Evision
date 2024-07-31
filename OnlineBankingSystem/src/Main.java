import model.User;
import service.RegistrationService;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static RegistrationService registrationService = new RegistrationService();

    public static void main(String[] args) {
        while (true) {
            showMainManu();
        }
    }

    public static void showMainManu() {
        System.out.println("Welcome to the Banking Application");
        System.out.println("Press: ");
        System.out.println("1. For Registration");
        System.out.println("2. For Login");
        System.out.println("3. Exit");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Exiting the application: Thank you!!!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice, please try again");
        }
    }

    public static void register() {
        System.out.println("Enter Username:");
        String username = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        System.out.println("Enter Email:");
        String email = scanner.nextLine();
        System.out.println("Enter Mobile Number:");
        String mobileNumber = scanner.nextLine();
        System.out.println("Enter DOB (YYYY-MM-DD):");
        String dob = scanner.nextLine();
        System.out.println("Enter Gender:");
        String gender = scanner.nextLine();

        boolean registerUser = registrationService.registerUser(username, password, email, mobileNumber, dob, gender);
        if (registerUser) {
            System.out.println();
            System.out.println("------------------------User registered successfully---------------------");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Mobile Number: " + mobileNumber);
            System.out.println("DOB: " + dob);
            System.out.println("Gender: " + gender);
        } else {
            System.out.println("----------------------User already exists. Please Login with " + username+  "---------------------------");
        }
    }

    public static void login() {
        System.out.println("Enter Username");
        String username = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();

        User loginUser = registrationService.loginUser(username, password);

        if (loginUser != null) {
            System.out.print("-------------------Login Successful ");
            loginUser.displayUserInformation();
            while (true)
            showManuForLoggedInUser(loginUser);
        } else {
            System.out.println("Login Failed. Please Enter Correct details");
        }
    }

    private static void showManuForLoggedInUser(User loginUser) {
        while (true) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("----------------Options:----------------------------");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Money");
            System.out.println("Any Key for Return to Main Menu");
            System.out.print("Choose an option: ");

            int i = scanner.nextInt();
            switch (i) {
                case 1:
                    depositMoney(loginUser);
                    return;

                case 2:
                    withdrawMoney(loginUser);
                    return;

                case 3:
                    System.out.println("Your Balance Is : "+loginUser.getBalance());
                    return;

                default:
                    System.exit(0);
            }
        }
    }

    private static void depositMoney(User loginUser) {
        System.out.println("Enter Deposit Money");
        double depositAmount = scanner.nextDouble();
        loginUser.deposit(depositAmount);
        System.out.println("Amount Deposit Successful");
    }

    private static void withdrawMoney(User loginUser) {
        System.out.println("Enter Withdraw Money");
        double withdrawAmount = scanner.nextDouble();
        boolean withdrawSuccess = loginUser.withdraw(withdrawAmount);
        if (withdrawSuccess) {
            System.out.println("Withdraw Successful");
        } else {
            System.out.println("Withdraw Failed Please Enter Correct Amount");
        }
    }

}