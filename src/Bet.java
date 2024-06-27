public class Bet {
    public enum BetType {
        WIN, LOSS, OVER, UNDER
    }

    private String sport;
    private String team;
    private double amount;
    private BetType type;
    private double odds;

    public Bet(String sport, String team, double amount, BetType type, double odds) {
        this.sport = sport;
        this.team = team;
        this.amount = amount;
        this.type = type;
        this.odds = odds;
    }

    public String getSport() {
        return sport;
    }

    public String getTeam() {
        return team;
    }

    public double getAmount() {
        return amount;
    }

    public BetType getType() {
        return type;
    }

    public double getOdds() {
        return odds;
    }
}
