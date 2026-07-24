import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public Login() {

        setTitle("TradeZilla - Login");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Colors
        Color bg = new Color(18, 18, 18);
        Color card = new Color(30, 30, 30);
        Color accent = new Color(0, 173, 181);
        Color text = Color.WHITE;

        getContentPane().setBackground(bg);

        // Card Panel
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 260));
        panel.setBackground(card);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("TradeZilla", JLabel.CENTER);
        title.setForeground(accent);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(title, gbc);

        // Subtitle
        JLabel subtitle = new JLabel("Welcome Back", JLabel.CENTER);
        subtitle.setForeground(text);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridy = 1;
        panel.add(subtitle, gbc);

        // Username
        usernameField = new JTextField();
        styleField(usernameField, text, card);

        gbc.gridy = 2;
        panel.add(usernameField, gbc);

        // Password
        passwordField = new JPasswordField();
        styleField(passwordField, text, card);

        gbc.gridy = 3;
        panel.add(passwordField, gbc);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(accent);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);

        gbc.gridy = 4;
        panel.add(loginBtn, gbc);

        add(panel);

        // Action
        loginBtn.addActionListener(e -> login());

        setVisible(true);
    }

    // Input styling
    void styleField(JTextField field, Color text, Color bg) {
        field.setBackground(bg);
        field.setForeground(text);
        field.setCaretColor(text);
        field.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 173, 181)),
                field instanceof JPasswordField ? "Password" : "Username",
                0, 0,
                new Font("Segoe UI", Font.PLAIN, 12),
                text
        ));
    }

    void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // username.toLowerCase();
        // System.out.println(username);

        if (username.equals("weekday traders") && password.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Welcome, " + username + "!");
            dispose();
            new Stockmarket(username);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
