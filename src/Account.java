public class Account {
    private User user;
    private double balance;
    private double winnings;
    private double losses;

    public Account(User user) {
        this.user = user;
        this.balance = 0;
        this.winnings = 0;
        this.losses = 0;
    }

    public User getUser() {
        return user;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    public void addWinnings(double amount) {
        this.winnings += amount;
        this.balance += amount;
    }

    public void addLosses(double amount) {
        this.losses += amount;
    }

    public double getWinnings() {
        return winnings;
    }

    public double getLosses() {
        return losses;
    }
}
