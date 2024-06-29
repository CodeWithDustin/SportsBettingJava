public class Bet {
    private int id;
    private int accountId;
    private String sport;
    private String team;
    private double amount;
    private String type;
    private double odds;
    private boolean result;

    public Bet(int id, int accountId, String sport, String team, double amount, String type, double odds, boolean result) {
        this.id = id;
        this.accountId = accountId;
        this.sport = sport;
        this.team = team;
        this.amount = amount;
        this.type = type;
        this.odds = odds;
        this.result = result;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
