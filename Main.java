import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class User {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

class ATM {
    private Map<String, User> users;
    private User currentUser;
    private Scanner scanner;

    public ATM() {
        this.users = new HashMap<>();
        this.scanner = new Scanner(System.in);
        initializeUsers();
    }

    private void initializeUsers() {
        // Add some sample users for demonstration
        users.put("123456", new User("123456", "1234", 1000.0));
        users.put("789012", new User("789012", "5678", 1500.0));
    }

    public void start() {
        System.out.println("Welcome to OctaNet ATM");

        // User authentication
        authenticateUser();

        // ATM operations
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using OctaNet ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);
    }

    private void authenticateUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        User user = users.get(userId);

        if (user != null && user.getPin().equals(pin)) {
            currentUser = user;
            System.out.println("Authentication successful. Welcome, " + currentUser.getUserId() + "!");
        } else {
            System.out.println("Authentication failed. Please try again.");
            authenticateUser();
        }
    }

    private void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Show Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private void showTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (String transaction : currentUser.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            String transaction = "Withdrawal: -$" + amount;
            currentUser.addTransaction(transaction);
            System.out.println("Withdrawal successful. Remaining balance: $" + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount or insufficient balance. Please try again.");
        }
    }

    private void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            currentUser.setBalance(currentUser.getBalance() + amount);
            String transaction = "Deposit: +$" + amount;
            currentUser.addTransaction(transaction);
            System.out.println("Deposit successful. Updated balance: $" + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount. Please try again.");
        }
    }

    private void transfer() {
        System.out.print("Enter the user ID to transfer to: ");
        String recipientUserId = scanner.nextLine();
        User recipient = users.get(recipientUserId);

        if (recipient != null) {
            System.out.print("Enter the amount to transfer: ");
            double amount = scanner.nextDouble();

            if (amount > 0 && amount <= currentUser.getBalance()) {
                currentUser.setBalance(currentUser.getBalance() - amount);
                recipient.setBalance(recipient.getBalance() + amount);

                String transaction = "Transfer to " + recipientUserId + ": -$" + amount;
                currentUser.addTransaction(transaction);

                String recipientTransaction = "Transfer from " + currentUser.getUserId() + ": +$" + amount;
                recipient.addTransaction(recipientTransaction);

                System.out.println("Transfer successful. Remaining balance: $" + currentUser.getBalance());
            } else {
                System.out.println("Invalid amount or insufficient balance. Please try again.");
            }
        } else {
            System.out.println("Recipient not found. Please enter a valid user ID.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Project: ATM INTERFACE");
        System.out.println("Reference: Sameer Kumar Sahoo (Intern at OctaNet)\n");

        ATM atm = new ATM();
        atm.start();
    }
}
