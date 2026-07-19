import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PortfolioWindow extends JFrame {

    public PortfolioWindow(HashMap<String, Integer> portfolio, ArrayList<Stock> stocks) {

        setTitle("TradeZilla - Portfolio");
        setSize(400, 450);
        setLocationRelativeTo(null);

        Color bg = new Color(18,18,18);
        Color card = new Color(30,30,30);
        Color accent = new Color(0,173,181);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(card);

        double total = 0;

        for (String key : portfolio.keySet()) {
            int qty = portfolio.get(key);
            double price = 0;

            for (Stock s : stocks) {
                if (s.name.equals(key)) {
                    price = s.price;
                    break;
                }
            }

            double value = qty * price;
            total += value;

            JLabel label = new JLabel(
                key + "  | Qty: " + qty + "  | ₹" + String.format("%.2f", value)
            );

            label.setForeground(Color.WHITE);
            label.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            panel.add(label);
        }

        JLabel totalLabel = new JLabel("Total Value: ₹" + String.format("%.2f", total));
        totalLabel.setForeground(accent);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(totalLabel);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);

        add(scroll);
        getContentPane().setBackground(bg);

        setVisible(true);
    }
}