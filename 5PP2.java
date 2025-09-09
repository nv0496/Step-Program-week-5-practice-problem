public class SecureBankAccount {
    // Private fields
    private String accountNumber;
    private double balance;
    private int pin;
    private boolean isLocked;
    private int failedAttempts;

    // Private constants
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final double MIN_BALANCE = 0.0;

    // Constructor
    public SecureBankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = Math.max(initialBalance, MIN_BALANCE);
        this.pin = 0; // Must be set separately
        this.isLocked = false;
        this.failedAttempts = 0;
    }

    // Account Info Methods
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        if (isLocked) {
            System.out.println("Account is locked. Cannot access balance.");
            return -1;
        }
        return balance;
    }

    public boolean isAccountLocked() {
        return isLocked;
    }

    // Security Methods
    public boolean setPin(int oldPin, int newPin) {
        if (this.pin == 0 || this.pin == oldPin) {
            this.pin = newPin;
            System.out.println("PIN set/changed successfully.");
            return true;
        }
        System.out.println("Incorrect old PIN.");
        return false;
    }

    public boolean validatePin(int enteredPin) {
        if (isLocked) {
            System.out.println("Account is locked.");
            return false;
        }
        if (enteredPin == pin) {
            resetFailedAttempts();
            return true;
        } else {
            incrementFailedAttempts();
            System.out.println("Invalid PIN.");
            return false;
        }
    }

    public boolean unlockAccount(int correctPin) {
        if (isLocked && correctPin == pin) {
            isLocked = false;
            resetFailedAttempts();
            System.out.println("Account unlocked.");
            return true;
        }
        System.out.println("Failed to unlock account.");
        return false;
    }

    // Transaction Methods
    public boolean deposit(double amount, int pin) {
        if (validatePin(pin)) {
            if (amount > 0) {
                balance += amount;
                System.out.println("Deposited: " + amount);
                return true;
            }
        }
        return false;
    }

    public boolean withdraw(double amount, int pin) {
        if (validatePin(pin)) {
            if (amount > 0 && balance - amount >= MIN_BALANCE) {
                balance -= amount;
                System.out.println("Withdrawn: " + amount);
                return true;
            } else {
                System.out.println("Insufficient funds.");
            }
        }
        return false;
    }

    public boolean transfer(SecureBankAccount target, double amount, int pin) {
        if (withdraw(amount, pin)) {
            target.deposit(amount, target.pin);
            System.out.println("Transferred " + amount + " to " + target.getAccountNumber());
            return true;
        }
        return false;
    }

    // Private Helper Methods
    private void lockAccount() {
        isLocked = true;
        System.out.println("Account locked due to multiple failed attempts!");
    }

    private void resetFailedAttempts() {
        failedAttempts = 0;
    }

    private void incrementFailedAttempts() {
        failedAttempts++;
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            lockAccount();
        }
    }

    // Main Test
    public static void main(String[] args) {
        SecureBankAccount acc1 = new SecureBankAccount("ACC1001", 500);
        SecureBankAccount acc2 = new SecureBankAccount("ACC2002", 1000);

        acc1.setPin(0, 1234);
        acc2.setPin(0, 5678);

        acc1.deposit(200, 1234);
        acc1.withdraw(100, 1234);
        acc2.deposit(500, 5678);

        acc1.transfer(acc2, 200, 1234);

        acc1.withdraw(50, 9999);
        acc1.withdraw(50, 8888);
        acc1.withdraw(50, 7777);

        acc1.withdraw(50, 1234);

        acc1.unlockAccount(1234);
        acc1.withdraw(50, 1234);

        acc1.withdraw(10000, 1234);
    }
}
