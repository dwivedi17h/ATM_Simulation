import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class 𝒐𝒊𝒃𝒔𝒊𝒑_𝒕𝒂𝒔𝒌𝒏𝒐_3 {

    static class User {
        private String userID;
        private String userPin;
        private String accountNumber;
        private double balance;
        private ArrayList<String> transactionHistory;

        public User(String userID, String userPin, String accountNumber, double initialBalance) {
            this.userID = userID;
            this.userPin = userPin;
            this.accountNumber = accountNumber;
            this.balance = initialBalance;
            this.transactionHistory = new ArrayList<>();
        }

        public String getUserID() {
            return userID;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public boolean validatePin(String inputPin) {
            return this.userPin.equals(inputPin);
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            balance += amount;
            transactionHistory.add("Deposited: ₹" + amount);
            System.out.println("Deposited $" + amount + " successfully.");
        }

        public void withdraw(double amount) {
            if (amount <= balance) {
                balance -= amount;
                transactionHistory.add("Withdrew: ₹" + amount);
                System.out.println("Withdrew $" + amount + " successfully.");
            } else {
                System.out.println("Insufficient balance.");
            }
        }

        public void transfer(User recipient, double amount, String pin) {
            if (validatePin(pin)) {
                if (amount <= balance) {
                    balance -= amount;
                    recipient.deposit(amount);
                    transactionHistory.add("Transferred: ₹" + amount + " to " + recipient.getUserID() + " (Account: " + recipient.getAccountNumber() + ")");
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Invalid PIN. Transfer failed.");
            }
        }

        public void showTransactionHistory() {
            if (transactionHistory.isEmpty()) {
                System.out.println("No transactions yet.");
            } else {
                System.out.println("Transaction History:");
                for (String transaction : transactionHistory) {
                    System.out.println(transaction);
                }
            }
        }
    }

    static class Authentication {
        private HashMap<String, User> users;

        public Authentication() {
            users = new HashMap<>();
        }

        public void addUser(String userID, String userPin, String accountNumber, double initialBalance) {
            users.put(userID, new User(userID, userPin, accountNumber, initialBalance));
        }

        public User authenticate(String accountNumber, String userID, String userPin) {
            User user = users.get(userID);
            if (user != null && user.validatePin(userPin) && user.getAccountNumber().equals(accountNumber)) {
                return user;
            }
            return null;
        }

        public User findUserByAccountNumber(String accountNumber) {
            for (User user : users.values()) {
                if (user.getAccountNumber().equals(accountNumber)) {
                    return user;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Authentication authentication = new Authentication();

        authentication.addUser("Lakshay", "1234", "ACC1001", 100000);
        authentication.addUser("Vasu", "2345", "ACC1002", 30000);
        authentication.addUser("Aadhya", "5678", "ACC1003", 30000);
        authentication.addUser("Ananya", "9896", "ACC1004", 5000);

        System.out.println("****************** Welcome to the ATM ******************");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter User ID: ");
        String userID = scanner.nextLine();

        System.out.print("Enter User PIN: ");
        String userPin = scanner.nextLine();

        User currentUser = authentication.authenticate(accountNumber, userID, userPin);
        if (currentUser == null) {
            System.out.println("Invalid Account Number, User ID, or PIN.");
            return;
        }

        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    currentUser.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    System.out.print("Enter your PIN to confirm: ");
                    String confirmPin = scanner.next();
                    if (currentUser.validatePin(confirmPin)) {
                        currentUser.withdraw(withdrawAmount);
                    } else {
                        System.out.println("Invalid PIN. Withdrawal failed.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient account number: ");
                    String recipientAccountNumber = scanner.nextLine();
                    User recipient = authentication.findUserByAccountNumber(recipientAccountNumber);
                    if (recipient != null) {
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter your PIN to confirm: ");
                        String transferPin = scanner.nextLine();
                        currentUser.transfer(recipient, transferAmount, transferPin);
                    } else {
                        System.out.println("Recipient account number not found.");
                    }
                    break;
                case 5:
                    System.out.println("Your current balance is: ₹" + currentUser.getBalance());
                    break;
                case 6:
                    System.out.println("Exiting. Thank you for using the ATM.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Check Balance");
        System.out.println("6. Quit");
        System.out.print("Choose an option: ");
    }
}
