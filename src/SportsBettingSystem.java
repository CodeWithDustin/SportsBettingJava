import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SportsBettingSystem {
    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, Double> odds = new HashMap<>();

    public SportsBettingSystem() {
        // Initialize some sample odds for demonstration
        odds.put("football_teamA_win", 1.5);
        odds.put("football_teamA_loss", 2.5);
        odds.put("football_teamA_over", 1.8);
        odds.put("football_teamA_under", 1.9);

        odds.put("basketball_teamB_win", 1.4);
        odds.put("basketball_teamB_loss", 2.6);
        odds.put("basketball_teamB_over", 1.7);
        odds.put("basketball_teamB_under", 2.0);
    }

    public void createAccount(String username, String password) {
        User user = new User(username, password);
        Account account = new Account(user);
        accounts.put(username, account);
    }

    public Account login(String username, String password) {
        Account account = accounts.get(username);
        if (account != null && account.getUser().getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    public void placeBet(Account account, String sport, String team, double amount, Bet.BetType type) {
        String key = sport + "_" + team + "_" + type.toString().toLowerCase();
        if (!odds.containsKey(key)) {
            System.out.println("Invalid bet. No odds available for this bet type.");
            return;
        }

        double betOdds = odds.get(key);
        if (account.withdraw(amount)) {
            Bet bet = new Bet(sport, team, amount, type, betOdds);
            // For simplicity, let's assume a 50% chance to win
            boolean win = Math.random() < 0.5;
            if (win) {
                account.addWinnings(amount * betOdds);
                System.out.println("Bet won! You won: " + amount * betOdds);
            } else {
                account.addLosses(amount);
                System.out.println("Bet lost.");
            }
        } else {
            System.out.println("Insufficient balance to place bet.");
        }
    }

    public static void main(String[] args) {
        SportsBettingSystem system = new SportsBettingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                system.createAccount(username, password);
                System.out.println("Account created successfully.");
            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                Account account = system.login(username, password);
                if (account != null) {
                    System.out.println("Login successful.");
                    while (true) {
                        System.out.println("1. Deposit");
                        System.out.println("2. Place Bet");
                        System.out.println("3. Check Balance");
                        System.out.println("4. Logout");
                        int action = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        if (action == 1) {
                            System.out.print("Enter amount to deposit: ");
                            double amount = scanner.nextDouble();
                            account.deposit(amount);
                            System.out.println("Deposit successful. Current balance: " + account.getBalance());
                        } else if (action == 2) {
                            System.out.print("Enter sport: ");
                            String sport = scanner.nextLine();
                            System.out.print("Enter team: ");
                            String team = scanner.nextLine();
                            System.out.println("Enter bet type (1: WIN, 2: LOSS, 3: OVER, 4: UNDER): ");
                            int typeChoice = scanner.nextInt();
                            scanner.nextLine(); // consume newline
                            Bet.BetType type = null;
                            switch (typeChoice) {
                                case 1:
                                    type = Bet.BetType.WIN;
                                    break;
                                case 2:
                                    type = Bet.BetType.LOSS;
                                    break;
                                case 3:
                                    type = Bet.BetType.OVER;
                                    break;
                                case 4:
                                    type = Bet.BetType.UNDER;
                                    break;
                                default:
                                    System.out.println("Invalid bet type.");
                                    continue;
                            }
                            System.out.print("Enter amount to bet: ");
                            double amount = scanner.nextDouble();
                            system.placeBet(account, sport, team, amount, type);
                            System.out.println("Bet placed. Current balance: " + account.getBalance());
                        } else if (action == 3) {
                            System.out.println("Current balance: " + account.getBalance());
                            System.out.println("Total winnings: " + account.getWinnings());
                            System.out.println("Total losses: " + account.getLosses());
                        } else if (action == 4) {
                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (choice == 3) {
                break;
            }
        }

        scanner.close();
    }
}
