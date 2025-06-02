import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class LoginRegisterReservationApp {

    private static CardLayout layout = new CardLayout();
    private static JPanel mainPanel = new JPanel(layout);

    private static HashMap<String, String> accounts = new HashMap<>();
    private static DefaultListModel<String> reservationListModel = new DefaultListModel<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hotel Reservation System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(450, 500);
            frame.setLocationRelativeTo(null);

            mainPanel.add(createLoginPanel(), "Login");
            mainPanel.add(createRegisterPanel(), "Register");
            mainPanel.add(createReservationPanel(), "Reservation");

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton goToRegisterBtn = new JButton("Register");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(loginBtn);
        panel.add(goToRegisterBtn);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
                JOptionPane.showMessageDialog(panel, "Login successful!");
                layout.show(mainPanel, "Reservation");
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid username or password.");
            }
        });

        goToRegisterBtn.addActionListener(e -> layout.show(mainPanel, "Register"));

        return panel;
    }

    private static JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton registerBtn = new JButton("Register");
        JButton backToLoginBtn = new JButton("Back");

        panel.add(new JLabel("New Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("New Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(registerBtn);
        panel.add(backToLoginBtn);

        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields.");
            } else if (accounts.containsKey(username)) {
                JOptionPane.showMessageDialog(panel, "Username already exists.");
            } else {
                accounts.put(username, password);
                JOptionPane.showMessageDialog(panel, "Registration successful!");
                layout.show(mainPanel, "Login");
            }
        });

        backToLoginBtn.addActionListener(e -> layout.show(mainPanel, "Login"));

        return panel;
    }

    private static JPanel createReservationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Hotel Reservation Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        JTextField guestNameField = new JTextField();
        JComboBox<String> roomTypeBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        JTextField checkInField = new JTextField("YYYY-MM-DD");
        JTextField checkOutField = new JTextField("YYYY-MM-DD");
        JTextField numGuestsField = new JTextField();
        JButton reserveBtn = new JButton("Reserve");

        formPanel.add(new JLabel("Guest Name:"));
        formPanel.add(guestNameField);
        formPanel.add(new JLabel("Room Type:"));
        formPanel.add(roomTypeBox);
        formPanel.add(new JLabel("Check-in Date:"));
        formPanel.add(checkInField);
        formPanel.add(new JLabel("Check-out Date:"));
        formPanel.add(checkOutField);
        formPanel.add(new JLabel("Number of Guests:"));
        formPanel.add(numGuestsField);
        formPanel.add(new JLabel());
        formPanel.add(reserveBtn);

        JList<String> reservationList = new JList<>(reservationListModel);
        JScrollPane scrollPane = new JScrollPane(reservationList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Reservations Made"));

        reserveBtn.addActionListener(e -> {
            String guest = guestNameField.getText().trim();
            String room = (String) roomTypeBox.getSelectedItem();
            String checkIn = checkInField.getText().trim();
            String checkOut = checkOutField.getText().trim();
            String numGuests = numGuestsField.getText().trim();

            if (guest.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty() || numGuests.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in all fields.");
                return;
            }

            try {
                Integer.parseInt(numGuests);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Number of guests must be a number.");
                return;
            }

            String reservation = "Guest: " + guest + " | Room: " + room + " | " + checkIn + " to " + checkOut + " | Guests: " + numGuests;
            reservationListModel.addElement(reservation);

            JPanel receipt = createReceiptPanel(guest, room, checkIn, checkOut, numGuests);
            mainPanel.add(receipt, "Receipt");
            layout.show(mainPanel, "Receipt");

            guestNameField.setText("");
            checkInField.setText("YYYY-MM-DD");
            checkOutField.setText("YYYY-MM-DD");
            numGuestsField.setText("");
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createReceiptPanel(String guest, String room, String checkIn, String checkOut, String numGuests) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Reservation Receipt", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setText(
                "Reservation Successful!\n\n" +
                        "Guest Name       : " + guest + "\n" +
                        "Room Type        : " + room + "\n" +
                        "Number of Guests : " + numGuests + "\n" +
                        "Check-In         : " + checkIn + "\n" +
                        "Check-Out        : " + checkOut + "\n\n" +
                        "Thank you for booking with us!"
        );

        JButton backBtn = new JButton("Back to Reservations");
        backBtn.addActionListener(e -> layout.show(mainPanel, "Reservation"));

        panel.add(title, BorderLayout.NORTH);
        panel.add(receiptArea, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);

        return panel;
    }
}