import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class BetDAO {
    public void placeRandomBet(int accountId) throws SQLException {
        // Generate random sports, teams, odds, and result
        String sport = getRandomSport();
        String team = getRandomTeam(sport);
        double odds = getRandomOdds();
        boolean result = getRandomResult();
        double amount = 10.0; // Example bet amount

        // Calculate winnings and losses based on the result
        double winnings = result ? amount * odds : 0.0;
        double losses = result ? 0.0 : getRandomLosses(); // Calculate random losses

        // Update account balance and record the bet
        String updateAccountSql = "UPDATE accounts SET balance = balance + ?, winnings = winnings + ?, losses = losses + ? WHERE id = ?";
        String recordBetSql = "INSERT INTO bets (account_id, sport, team, amount, type, odds, result) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            // Update account balance
            try (PreparedStatement updateStmt = conn.prepareStatement(updateAccountSql, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement recordStmt = conn.prepareStatement(recordBetSql)) {

                // Subtract losses from balance and update winnings if won
                updateStmt.setDouble(1, result ? winnings : -losses); // Add winnings or deduct amount (for losses)
                updateStmt.setDouble(2, result ? winnings : 0.0); // Add winnings only if result is true (won)
                updateStmt.setDouble(3, result ? 0.0 : -losses); // Subtract losses only if result is false (lost)
                updateStmt.setInt(4, accountId);
                updateStmt.executeUpdate();

                // Record the bet
                recordStmt.setInt(1, accountId);
                recordStmt.setString(2, sport);
                recordStmt.setString(3, team);
                recordStmt.setDouble(4, amount);
                recordStmt.setString(5, "default");
                recordStmt.setDouble(6, odds);
                recordStmt.setBoolean(7, result);
                recordStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void placeSpecificBet(int accountId, String sport, String team, double amount, double odds, boolean result) throws SQLException {
        // Record the bet with user-specified parameters
        String sql = "INSERT INTO bets (account_id, sport, team, amount, type, odds, result) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setString(2, sport);
            pstmt.setString(3, team);
            pstmt.setDouble(4, amount);
            pstmt.setString(5, "specific");
            pstmt.setDouble(6, odds);
            pstmt.setBoolean(7, result);
            pstmt.executeUpdate();
        }
    }

    private String getRandomSport() {
        String[] sports = {"Football", "Basketball", "Tennis", "Baseball", "Soccer"};
        Random random = new Random();
        return sports[random.nextInt(sports.length)];
    }

    private String getRandomTeam(String sport) {
        return sport + " Team";
    }

    private double getRandomOdds() {
        Random random = new Random();
        return 1.5 + random.nextDouble() * 4.0;
    }

    private boolean getRandomResult() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private double getRandomLosses() {
        Random random = new Random();
        return random.nextDouble() * 10.0;
    }
}