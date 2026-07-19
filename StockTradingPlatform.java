import java.util.*;

class Stock {
    String name;
    double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void updatePrice() {
        double change = (Math.random() * 200) - 100;
        price += change;

        if (price < 100)
            price = 100;
    }
}

class Transaction {
    String type;
    String stockName;
    int shares;
    double amount;

    public Transaction(String type, String stockName, int shares, double amount) {
        this.type = type;
        this.stockName = stockName;
        this.shares = shares;
        this.amount = amount;
    }

    public void display() {
        System.out.printf("%-5s %-10s Shares:%3d Amount: ₹%.2f\n",
                type, stockName, shares, amount);
    }
}

class User {
    String name;
    double balance;
    double initialBalance;

    HashMap<String, Integer> portfolio;
    ArrayList<Transaction> history;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.initialBalance = balance;
        portfolio = new HashMap<>();
        history = new ArrayList<>();
    }

    public void buyStock(Stock stock, int shares) {

        if (shares <= 0) {
            System.out.println("Invalid number of shares!");
            return;
        }

        double cost = shares * stock.price;

        if (cost > balance) {
            System.out.println("Insufficient Balance!");
            return;
        }

        balance -= cost;

        portfolio.put(stock.name,
                portfolio.getOrDefault(stock.name, 0) + shares);

        history.add(new Transaction("BUY", stock.name, shares, cost));

        System.out.println("Stock Purchased Successfully!");
    }

    public void sellStock(Stock stock, int shares) {

        if (shares <= 0) {
            System.out.println("Invalid number of shares!");
            return;
        }

        int owned = portfolio.getOrDefault(stock.name, 0);

        if (shares > owned) {
            System.out.println("Not enough shares!");
            return;
        }

        double amount = shares * stock.price;

        balance += amount;

        portfolio.put(stock.name, owned - shares);

        history.add(new Transaction("SELL", stock.name, shares, amount));

        System.out.println("Stock Sold Successfully!");
    }
}
public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Stock> market = new ArrayList<>();

        market.add(new Stock("TCS", 3500));
        market.add(new Stock("Infosys", 1600));
        market.add(new Stock("Reliance", 2800));
        market.add(new Stock("HDFC", 1700));
        market.add(new Stock("Wipro", 550));
        HashMap<String, User> users = new HashMap<>();

        System.out.print("Enter User Name: ");
        String userName = sc.nextLine();

        User user = new User(userName, 100000);

        int choice;

        do {

            System.out.println("\n========== STOCK TRADING PLATFORM ==========");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.println("\n------ Market Data ------");

                    for (int i = 0; i < market.size(); i++) {

                        market.get(i).updatePrice();

                        System.out.printf("%d. %-10s ₹%.2f\n",
                                i + 1,
                                market.get(i).name,
                                market.get(i).price);
                    }

                    break;

                case 2:

                    System.out.println("\nSelect Stock");

                    for (int i = 0; i < market.size(); i++) {

                        System.out.printf("%d. %-10s ₹%.2f\n",
                                i + 1,
                                market.get(i).name,
                                market.get(i).price);
                    }

                    System.out.print("Enter Stock Number: ");
                    int buyStock = sc.nextInt();

                    if (buyStock < 1 || buyStock > market.size()) {
                        System.out.println("Invalid Stock!");
                        break;
                    }

                    System.out.print("Enter Shares: ");
                    int buyShares = sc.nextInt();

                    user.buyStock(market.get(buyStock - 1), buyShares);

                    break;

                case 3:

                    System.out.println("\nSelect Stock");

                    for (int i = 0; i < market.size(); i++) {

                        System.out.printf("%d. %-10s ₹%.2f\n",
                                i + 1,
                                market.get(i).name,
                                market.get(i).price);
                    }

                    System.out.print("Enter Stock Number: ");
                    int sellStock = sc.nextInt();

                    if (sellStock < 1 || sellStock > market.size()) {
                        System.out.println("Invalid Stock!");
                        break;
                    }

                    System.out.print("Enter Shares: ");
                    int sellShares = sc.nextInt();

                    user.sellStock(market.get(sellStock - 1), sellShares);

                    break;

                case 4:

                    System.out.println("\n========== PORTFOLIO ==========");

                    double stockValue = 0;

                    for (Stock s : market) {

                        int shares = user.portfolio.getOrDefault(s.name, 0);

                        if (shares > 0) {

                            double value = shares * s.price;

                            stockValue += value;

                            System.out.printf("%-10s Shares:%3d Value: ₹%.2f\n",
                                    s.name,
                                    shares,
                                    value);
                        }
                    }

                    System.out.println("--------------------------------");

                    System.out.printf("Cash Balance : ₹%.2f\n", user.balance);
                    System.out.printf("Stock Value  : ₹%.2f\n", stockValue);
                    System.out.printf("Net Worth    : ₹%.2f\n", user.balance + stockValue);

                    double profit = (user.balance + stockValue) - user.initialBalance;

                    System.out.printf("Profit/Loss  : ₹%.2f\n", profit);

                    break;

                case 5:

                    System.out.println("\n====== TRANSACTION HISTORY ======");

                    if (user.history.isEmpty()) {

                        System.out.println("No Transactions Yet!");

                    } else {

                        for (Transaction t : user.history) {
                            t.display();
                        }
                    }

                    break;

                case 6:

                    System.out.println("Thank You For Using Stock Trading Platform!");

                    break;

                default:

                    System.out.println("Invalid Choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}