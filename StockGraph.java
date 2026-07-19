import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class StockGraph extends JPanel {

    ArrayList<Double> prices = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    Random random = new Random();
    public void setGraphData(ArrayList<Double> newPrices) {

    prices.clear();

    prices.addAll(newPrices);

    repaint();
}
    

    public StockGraph() {
        generateData();
    }

    // ✅ Generate graph only once
    public void generateData() {

        // Prevent regenerating random graph again
        if (!prices.isEmpty()) {
            repaint();
            return;
        }

        double price = 100;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");

        for (int i = 0; i < 30; i++) {

            // Small realistic movement
            price += (random.nextDouble() - 0.5) * 6;

            // Prevent negative stock price
            if (price < 20) {
                price = 20;
            }

            prices.add(price);

            dates.add(sdf.format(cal.getTime()));

            cal.add(Calendar.DAY_OF_MONTH, 6);
        }

        repaint();
    }

    // ✅ Add new value instead of generating random graph again
    public void addNewPrice(double newPrice) {

        prices.add(newPrice);

        // Keep only latest 30 values
        if (prices.size() > 30) {
            prices.remove(0);
            dates.remove(0);
        }

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");

        dates.add(sdf.format(cal.getTime()));

        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.setColor(new Color(18,18,18));
        g2.fillRect(0,0,getWidth(),getHeight());

        // Grid
        g2.setColor(new Color(50,50,50));

        for (int i = 0; i < getHeight(); i += 50) {
            g2.drawLine(0, i, getWidth(), i);
        }

        if (prices.size() < 2) return;

        int w = getWidth();
        int h = getHeight();

        double max = Collections.max(prices);
        double min = Collections.min(prices);

        int prevX = 0;
        int prevY = scale(prices.get(0), min, max, h);

        for (int i = 1; i < prices.size(); i++) {

            int x = i * (w - 40) / (prices.size() - 1) + 20;

            int y = scale(prices.get(i), min, max, h);

            // Green if up, red if down
            if (prices.get(i) > prices.get(i - 1)) {
                g2.setColor(Color.GREEN);
            } else {
                g2.setColor(Color.RED);
            }

            g2.setStroke(new BasicStroke(3));

            g2.drawLine(prevX, prevY, x, y);

            // Point circle
            g2.fillOval(x - 3, y - 3, 6, 6);

            prevX = x;
            prevY = y;
        }

        // Labels
        g2.setColor(Color.WHITE);

        g2.drawString("₹" + String.format("%.0f", max), 10, 20);

        g2.drawString("₹" + String.format("%.0f", min), 10, h - 10);

        // Date labels
        for (int i = 0; i < dates.size(); i += 5) {

            int x = i * (w - 40) / (prices.size() - 1) + 20;

            g2.drawString(dates.get(i), x - 15, h - 5);
        }
    }

    int scale(double value, double min, double max, int height) {

        if (max == min) {
            return height / 2;
        }

        return (int)((max - value) / (max - min) * (height - 60)) + 30;
    }
}