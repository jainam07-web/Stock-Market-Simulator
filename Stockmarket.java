import javax.swing.*;
import java.awt.*;
import java.util.*;
class Stock {
        String name;
        double price;
//  Separate graph history for every stock
    ArrayList<Double> history = new ArrayList<>();
public Stock(String name, double price) {
        this.name = name;
        this.price = price;
 generateInitialHistory();
    }
 //  Initial graph values
    void generateInitialHistory() {
double temp = price;
 for (int i = 0; i < 30; i++) {
temp += (Math.random() - 0.5) * 6;
            if (temp < 10) {
                temp = 10;
            }
            history.add(temp);
        }
    }
    //  Update stock price
    public void updatePrice() {
        double change = (Math.random() - 0.5) * 10;
        price = Math.max(10, price + change);
        history.add(price);
       // keep latest 30 points only
        if (history.size() > 30) {
            history.remove(0);
        }
    }
}
public class Stockmarket extends JFrame {
    String username;
    ArrayList<Stock> stocks = new ArrayList<>();
    HashMap<String, Integer> portfolio = new HashMap<>();
    // 💰 ACCOUNT
    double balance = 10000;
    //  Actual invested money
    double investedAmount = 10000;
    DefaultListModel<String> stockModel = new DefaultListModel<>();
    JList<String> stockList = new JList<>(stockModel);
    JLabel balanceLabel = new JLabel();
    //  Profit / Loss Label
    JLabel profitLossLabel = new JLabel();
    //  GRAPH PANEL
    StockGraph graphPanel = new StockGraph();
    public Stockmarket(String username) {
        this.username = username;
        setTitle("TradeZilla");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        // THEME
        Color bg = new Color(18,18,18);
        Color card = new Color(30,30,30);
        Color accent = new Color(0,173,181);
        Color text = Color.WHITE;
        getContentPane().setBackground(bg);
        //  STOCK DATA
        stocks.add(new Stock("Reliance", 120));
        stocks.add(new Stock("TCS", 90));
        stocks.add(new Stock("Adani", 150));
        stocks.add(new Stock("Suzlon", 110));
        stocks.add(new Stock("Daawat", 80));
        //  HEADER
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(bg);
        header.setBorder(
               BorderFactory.createEmptyBorder(10,10,10,10)
        );
        JLabel title = new JLabel("TradeZilla Dashboard");
        title.setForeground(accent);
        title.setFont(
                new Font("Segoe UI", Font.BOLD, 22)
        );
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcome = new JLabel(
                "Welcome, " + username
        );
        welcome.setForeground(text);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceLabel.setForeground(text);
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profitLossLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(welcome);
        header.add(Box.createVerticalStrut(5));
        header.add(balanceLabel);
        header.add(profitLossLabel);
        // LEFT PANEL
        JPanel leftCard = createCard(
                "Stocks",
                card,
                accent
        );
        leftCard.setLayout(new BorderLayout());
        stockList.setBackground(card);
        stockList.setForeground(text);
        stockList.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        stockList.setFixedCellHeight(30);
        stockList.setSelectionBackground(accent);
        stockList.setSelectionForeground(Color.BLACK);
        updateStockDisplay();
        leftCard.add(
                new JScrollPane(stockList),
                BorderLayout.CENTER
        );
        //  CENTER PANEL
        JPanel centerCard = createCard(
                "Stock Graph",
                card,
                accent
        );
        centerCard.setLayout(new BorderLayout());

        graphPanel.setPreferredSize(
                new Dimension(500,350)
        );
        centerCard.add(graphPanel, BorderLayout.CENTER);
        //  BUTTON BAR
        JPanel buttonBar = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        10
                )
        );
        buttonBar.setBackground(bg);
        JButton buy = createButton(
                "Buy",
                accent
        );
        JButton sell = createButton(
                "Sell",
                new Color(255,140,0)
        );
        JButton next = createButton(
                "Next Day",
                new Color(0,200,83)
        );
        JButton deposit = createButton(
                "Deposit",
                new Color(100,149,237)
        );
        JButton withdraw = createButton(
                "Withdraw",
                new Color(220,20,60)
        );
        JButton portfolioBtn = createButton(
                "Portfolio",
                new Color(255,215,0)
        );
        buttonBar.add(buy);
        buttonBar.add(sell);
        buttonBar.add(next);
        buttonBar.add(deposit);
        buttonBar.add(withdraw);
        buttonBar.add(portfolioBtn);
        // 🎯 ACTIONS
        buy.addActionListener(e -> buyStock());
        sell.addActionListener(e -> sellStock());
        next.addActionListener(e -> nextDay());
        deposit.addActionListener(e -> deposit());
        withdraw.addActionListener(e -> withdraw());
        portfolioBtn.addActionListener(
                e -> new PortfolioWindow(portfolio, stocks)
        );
        //  STOCK GRAPH UPDATE
        stockList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = stockList.getSelectedIndex();
                if (index != -1) {
                    Stock selectedStock = stocks.get(index);
                    graphPanel.setGraphData(
                            selectedStock.history
                    );
                }
            }
        });
        //  ADD LAYOUT
        add(header, BorderLayout.NORTH);
        add(leftCard, BorderLayout.WEST);
        add(centerCard, BorderLayout.CENTER);
        add(buttonBar, BorderLayout.SOUTH);
       updateBalance();
        updateProfitLoss();
        setVisible(true);
    }
    //  CARD STYLE
    JPanel createCard(
            String title,
            Color bg,
            Color accent
    ) {
        JPanel panel = new JPanel();
        panel.setBackground(bg);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accent,1),
                        BorderFactory.createEmptyBorder(
                                10,10,10,10
                        )
                )
        );
       panel.setPreferredSize(
                new Dimension(250,0)
        );
        JLabel label = new JLabel(
                title,
                JLabel.CENTER
        );
        label.setForeground(accent);
        label.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
       panel.setLayout(new BorderLayout());

        panel.add(label, BorderLayout.NORTH);

        return panel;
    }

    //  BUTTON STYLE
    JButton createButton(String text, Color color) {

        JButton btn = new JButton(text);

        btn.setBackground(color);

        btn.setForeground(Color.BLACK);

        btn.setFocusPainted(false);

        btn.setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        return btn;
    }

    // 📊 UPDATE STOCK DISPLAY
    void updateStockDisplay() {

        stockModel.clear();

        for (Stock s : stocks) {

            stockModel.addElement(
                    s.name +
                    "  |  ₹" +
                    String.format("%.2f", s.price)
            );
        }
    }

    // 💰 UPDATE BALANCE
    void updateBalance() {

        balanceLabel.setText(
                "Balance: ₹" +
                String.format("%.2f", balance)
        );
    }

    // ✅ PROFIT / LOSS
    void updateProfitLoss() {

        double stockValue = 0;

        for (String key : portfolio.keySet()) {

            int qty = portfolio.get(key);

            for (Stock s : stocks) {

                if (s.name.equals(key)) {

                    stockValue += s.price * qty;
                }
            }
        }

        double totalAssets = balance + stockValue;

        // ✅ compare against adjusted invested amount
        double profitLoss =
                totalAssets - investedAmount;

        if (profitLoss > 0) {

            profitLossLabel.setForeground(Color.GREEN);

            profitLossLabel.setText(
                    "Profit: +₹" +
                    String.format("%.2f", profitLoss)
            );

        } else if (profitLoss < 0) {

            profitLossLabel.setForeground(Color.RED);

            profitLossLabel.setText(
                    "Loss: -₹" +
                    String.format(
                            "%.2f",
                            Math.abs(profitLoss)
                    )
            );

        } else {

            profitLossLabel.setForeground(Color.WHITE);

            profitLossLabel.setText(
                    "No Profit No Loss"
            );
        }
    }

    // 📅 NEXT DAY
    void nextDay() {

        for (Stock s : stocks) {

            s.updatePrice();
        }

        updateStockDisplay();

        updateProfitLoss();

        // refresh selected stock graph
        int index = stockList.getSelectedIndex();

        if (index != -1) {

            graphPanel.setGraphData(
                    stocks.get(index).history
            );
        }
    }

    // 🟢 BUY
    void buyStock() {

        int i = stockList.getSelectedIndex();

        if (i == -1) return;

        Stock s = stocks.get(i);

        String in = JOptionPane.showInputDialog(
                "Qty:"
        );

        try {

            int q = Integer.parseInt(in);

            double cost = q * s.price;

            if (balance >= cost) {

                balance -= cost;

                portfolio.put(
                        s.name,
                        portfolio.getOrDefault(
                                s.name,
                                0
                        ) + q
                );
            }

        } catch(Exception ignored){}

        updateBalance();

        updateProfitLoss();
    }

    // 🔴 SELL
    void sellStock() {

        int i = stockList.getSelectedIndex();

        if (i == -1) return;

        Stock s = stocks.get(i);

        String in = JOptionPane.showInputDialog(
                "Qty:"
        );

        try {

            int q = Integer.parseInt(in);

            int owned =
                    portfolio.getOrDefault(
                            s.name,
                            0
                    );

            if (owned >= q) {

                balance += q * s.price;

                portfolio.put(
                        s.name,
                        owned - q
                );
            }
        } catch(Exception ignored){}
        updateBalance();
        updateProfitLoss();
    }
    // 💵 DEPOSIT
    void deposit() {
        String in = JOptionPane.showInputDialog(
                "Amount:"
        );
        try {
            double amt =
                    Double.parseDouble(in);

            balance += amt;
            // ✅ increase invested amount
            investedAmount += amt;
        } catch(Exception ignored){}
        updateBalance();
        updateProfitLoss();
    }
    // 💸 WITHDRAW
    void withdraw() {
        String in = JOptionPane.showInputDialog(
               "Amount:"
        );
        try {
            double amt =
                    Double.parseDouble(in);
            if (amt <= balance) {
                balance -= amt;
                // ✅ reduce invested amount
               investedAmount -= amt;
            }
        } catch(Exception ignored){}
        updateBalance();
        updateProfitLoss();
    }
}