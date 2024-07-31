package model;

public class User {
    private String userName;
    private String password;
    private String email;
    private String mobileNumber;
    private String dob;
    private String gender;
    private double balance;

    public User(){

    }

    public User(String userName, String password, String email, String mobileNumber, String dob, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.dob = dob;
        this.gender = gender;
        this.balance = 0.0;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public void displayUserInformation() {
        System.out.println("User Information--------------------------");
        System.out.println("Username: " + this.userName);
        System.out.println("email: " + this.email);
        System.out.println("Mobile Number: " + this.mobileNumber);
        System.out.println("DOB: " + this.dob);
        System.out.println("Gender: " + this.gender);
    }
}
