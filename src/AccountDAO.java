import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    public void createAccount(int userId, double balance, double winnings, double losses) throws SQLException {
        String sql = "INSERT INTO accounts (user_id, balance, winnings, losses) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDouble(2, balance);
            pstmt.setDouble(3, winnings);
            pstmt.setDouble(4, losses);
            pstmt.executeUpdate();
        }
    }

    public Account getAccountById(int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getInt("id"), rs.getInt("user_id"),
                            rs.getDouble("balance"), rs.getDouble("winnings"), rs.getDouble("losses"));
                }
            }
        }
        return null;
    }

    public Account getAccountByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getInt("id"), rs.getInt("user_id"),
                            rs.getDouble("balance"), rs.getDouble("winnings"), rs.getDouble("losses"));
                }
            }
        }
        return null;
    }

    public void updateAccount(int id, double balance, double winnings, double losses) throws SQLException {
        String sql = "UPDATE accounts SET balance = ?, winnings = ?, losses = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, balance);
            pstmt.setDouble(2, winnings);
            pstmt.setDouble(3, losses);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteAccount(int id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
