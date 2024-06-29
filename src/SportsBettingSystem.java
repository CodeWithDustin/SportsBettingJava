import java.sql.SQLException;
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class SportsBettingSystem {
    private static UserDAO userDAO = new UserDAO();
    private static AccountDAO accountDAO = new AccountDAO();
    private static BetDAO betDAO = new BetDAO();
    private static Random random = new Random(); // Random generator for odds and results
    private static User currentUser = null; // Track current user session

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initial interaction loop
        while (true) {
            System.out.println("Welcome to the Sports Betting System!");

            // Prompt user to log in or create an account
            System.out.println("1. Log in");
            System.out.println("2. Create an account");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (currentUser != null) {
                        System.out.println("Already logged in as " + currentUser.getUsername() + ".");
                    } else {
                        login(scanner);
                    }
                    break;
                case 2:
                    createAccount(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            // After each operation, check if user is logged in to display further options
            if (currentUser != null) {
                handleUserOperations(currentUser, scanner);
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine().trim();

        System.out.println("Enter password:");
        String password = scanner.nextLine().trim();

        try {
            User user = userDAO.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                currentUser = user; // Set current user session
                System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createAccount(Scanner scanner) {
        System.out.println("Enter new username:");
        String newUsername = scanner.nextLine().trim();

        System.out.println("Enter password:");
        String newPassword = scanner.nextLine().trim();

        try {
            // Check if username already exists
            if (userDAO.getUserByUsername(newUsername) != null) {
                System.out.println("Username already exists. Please choose a different username.");
                return;
            }

            // Create new user and associated account details
            User newUser = userDAO.createUser(newUsername, newPassword);
            if (newUser != null) {
                System.out.println("Account created successfully. You can now log in.");
            } else {
                System.out.println("Failed to create account. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void handleUserOperations(User user, Scanner scanner) {
        // Example operations after login
        while (true) {
            System.out.println("Select an operation:");
            System.out.println("1. Place a random bet");
            System.out.println("2. Place a bet on a specific matchup");
            System.out.println("3. View account details");
            System.out.println("4. Update account information");
            System.out.println("5. Delete account");
            System.out.println("6. Log out");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    placeRandomBet(user);
                    break;
                case 2:
                    placeSpecificBet(user, scanner);
                    break;
                case 3:
                    viewAccountDetails(user);
                    break;
                case 4:
                    updateAccount(user, scanner);
                    break;
                case 5:
                    deleteAccount(user);
                    return; // Exit handleUserOperations loop after deleting account
                case 6:
                    System.out.println("Logging out...");
                    currentUser = null; // Clear current user session
                    return; // Exit handleUserOperations loop after logging out
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void placeRandomBet(User user) {
        try {
            int accountId = getAccountIdForUser(user.getId());
            String sport = getRandomSport();
            String team = getRandomTeam(sport);
            double odds = getRandomOdds();
            boolean result = getRandomResult();
            double amount = 10.0; // Example amount

            // Place the specific bet with randomly generated values
            betDAO.placeRandomBet(accountId);

            // Update and display account details after bet
            updateAccountDetailsAfterBet(user);
            System.out.println("Bet placed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getRandomSport() {
        String[] sports = {"Football", "Soccer", "Baseball"};
        return sports[random.nextInt(sports.length)];
    }

    private static String getRandomTeam(String sport) {
        String[] teams;
        switch (sport) {
            case "Football":
                teams = new String[]{"Michigan", "Texas", "Alabama"};
                break;
            case "Soccer":
                teams = new String[]{"Real Madrid", "Manchester City", "Barcelona"};
                break;
            case "Baseball":
                teams = new String[]{"New York Yankees", "Baltimore Orioles", "Boston Red Sox"};
                break;
            default:
                teams = new String[]{"Team 1", "Team 2", "Team 3"};
                break;
        }
        return teams[random.nextInt(teams.length)];
    }

    private static double getRandomOdds() {
        return 1.5 + random.nextDouble() * 4.0; // Example odds
    }

    private static boolean getRandomResult() {
        return random.nextBoolean(); // Example result
    }

    private static void placeSpecificBet(User user, Scanner scanner) {
        try {
            System.out.println("Select a sport:");
            System.out.println("1. Football");
            System.out.println("2. Soccer");
            System.out.println("3. Baseball");

            int sportChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String sport;
            switch (sportChoice) {
                case 1:
                    sport = "Football";
                    break;
                case 2:
                    sport = "Soccer";
                    break;
                case 3:
                    sport = "Baseball";
                    break;
                default:
                    System.out.println("Invalid sport choice. Returning to main menu.");
                    return;
            }

            // Simulate a matchup (random teams)
            String team1 = getRandomTeam(sport);
            String team2 = getRandomTeam(sport);

            System.out.println("Matchup: " + team1 + " vs. " + team2);

            System.out.println("Choose a team to bet on:");
            System.out.println("1. " + team1);
            System.out.println("2. " + team2);

            int teamChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String selectedTeam = (teamChoice == 1) ? team1 : team2;

            // Generate random odds for the selected matchup
            double odds = getRandomOdds();

            // Simulate the bet result (random win/loss)
            boolean result = getRandomResult();

            // Calculate amount to bet (example: 10.0)
            double amount = 10.0;

            // Place the specific bet with randomly generated values
            betDAO.placeSpecificBet(getAccountIdForUser(user.getId()), sport, selectedTeam, amount, odds, result);

            System.out.println("Bet placed successfully on " + selectedTeam + " in " + sport);
            if (result) {
                System.out.println("Congratulations, your team won!");
            } else {
                System.out.println("Sorry, your team lost.");
            }

            // Update and display account details after bet
            updateAccountDetailsAfterBet(user);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid option.");
            scanner.nextLine(); // Consume invalid input
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateAccountDetailsAfterBet(User user) {
        try {
            Account account = accountDAO.getAccountByUserId(user.getId());
            if (account != null) {
                // Refresh account details from the database
                account = accountDAO.getAccountByUserId(user.getId());

                // Display updated account details
                System.out.println("Updated Account Details:");
                System.out.println("Account Balance: " + account.getBalance());
                System.out.println("Winnings: " + account.getWinnings());
                System.out.println("Losses: " + account.getLosses());
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getAccountIdForUser(int userId) throws SQLException {
        Account account = accountDAO.getAccountByUserId(userId);
        if (account != null) {
            return account.getId();
        } else {
            throw new SQLException("Account not found for user ID: " + userId);
        }
    }

    private static void viewAccountDetails(User user) {
        try {
            Account account = accountDAO.getAccountByUserId(user.getId());
            if (account != null) {
                System.out.println("Account Balance: " + account.getBalance());
                System.out.println("Winnings: " + account.getWinnings());
                System.out.println("Losses: " + account.getLosses());
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateAccount(User user, Scanner scanner) {
        System.out.println("Enter new balance:");
        double newBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter new winnings:");
        double newWinnings = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter new losses:");
        double newLosses = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        try {
            int accountId = getAccountIdForUser(user.getId());
            accountDAO.updateAccount(accountId, newBalance, newWinnings, newLosses);
            System.out.println("Account updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAccount(User user) {
        try {
            int accountId = getAccountIdForUser(user.getId());
            accountDAO.deleteAccount(accountId);
            userDAO.deleteUser(user.getId());
            System.out.println("Account deleted successfully.");
            currentUser = null; // Clear current user session after deleting account
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
