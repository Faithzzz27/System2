import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

    public class LoginRegisterApp {

        private static CardLayout layout = new CardLayout();
        private static JPanel mainPanel = new JPanel(layout);

        private static HashMap<String, String> accounts = new HashMap<>(); // Stores username-password

        public static void main(String[] args) {
            JFrame frame = new JFrame("Login/Register System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 250);
            frame.setLocationRelativeTo(null); // Center the window

            // Add the panels to CardLayout
            mainPanel.add(loginPanel(), "Login");
            mainPanel.add(registerPanel(), "Register");
            mainPanel.add(dashboardPanel(), "Dashboard");

            frame.add(mainPanel);
            frame.setVisible(true);
        }

        private static JPanel loginPanel() {
            JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();

            JButton loginBtn = new JButton("Login");
            JButton toRegisterBtn = new JButton("Register");

            panel.add(new JLabel("Username:"));
            panel.add(userField);
            panel.add(new JLabel("Password:"));
            panel.add(passField);
            panel.add(new JLabel()); // Empty cell
            panel.add(new JLabel());
            panel.add(loginBtn);
            panel.add(toRegisterBtn);

            // Login logic
            loginBtn.addActionListener(e -> {
                String user = userField.getText().trim();
                String pass = new String(passField.getPassword());

                if (accounts.containsKey(user) && accounts.get(user).equals(pass)) {
                    JOptionPane.showMessageDialog(panel, "Login successful!");
                    layout.show(mainPanel, "Dashboard");
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid username or password.");
                }
            });

            toRegisterBtn.addActionListener(e -> layout.show(mainPanel, "Register"));

            return panel;
        }

        private static JPanel registerPanel() {
            JPanel panel = new JPanel(new GridLayout(5, 2, 20, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();

            JButton registerBtn = new JButton("Register");
            JButton toLoginBtn = new JButton("Back to Login");

            panel.add(new JLabel("New Username:"));
            panel.add(userField);
            panel.add(new JLabel("New Password:"));
            panel.add(passField);
            panel.add(new JLabel()); // Empty
            panel.add(new JLabel());
            panel.add(registerBtn);
            panel.add(toLoginBtn);

            // Register logic
            registerBtn.addActionListener(e -> {
                String user = userField.getText().trim();
                String pass = new String(passField.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please fill in all fields.");
                } else if (accounts.containsKey(user)) {
                    JOptionPane.showMessageDialog(panel, "Username already exists.");
                } else {
                    accounts.put(user, pass);
                    JOptionPane.showMessageDialog(panel, "Registration successful!");
                    layout.show(mainPanel, "Login");
                }
            });

            toLoginBtn.addActionListener(e -> layout.show(mainPanel, "Login"));

            return panel;
        }

        private static JPanel dashboardPanel() {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel welcome = new JLabel("Welcome to the Resort and Hotel Reservation", SwingConstants.CENTER);
            panel.add(welcome, BorderLayout.CENTER);
            return panel;
        }
    }

