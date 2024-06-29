public class Account {
    private int id;
    private int userId;
    private double balance;
    private double winnings;
    private double losses;

    public Account(int id, int userId, double balance, double winnings, double losses) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.winnings = winnings;
        this.losses = losses;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getWinnings() {
        return winnings;
    }

    public void setWinnings(double winnings) {
        this.winnings = winnings;
    }

    public double getLosses() {
        return losses;
    }

    public void setLosses(double losses) {
        this.losses = losses;
    }
}
