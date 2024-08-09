import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter; // Import MouseAdapter
import java.awt.event.MouseEvent; // Import MouseEvent
import java.sql.*;
import java.util.ArrayList;

public class ChelseaFCApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private String currentEmail; // To store the email of the user who registered

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChelseaFCApp().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Chelsea FC App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add screens to cardPanel
        cardPanel.add(createLoginScreen(), "Login");
        cardPanel.add(createRegisterScreen(), "Register");
        cardPanel.add(createPersonalInfoScreen(), "Personal Info");
        cardPanel.add(createMainMenu(), "Main Menu");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    private JPanel createLoginScreen() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Convert Graphics to Graphics2D
                Graphics2D g2d = (Graphics2D) g.create();

                // Load the background image
                ImageIcon backgroundImage = new ImageIcon("E:/Chelsea/Chelsea/cl_final_trophy_lift.jpg");
                Image image = backgroundImage.getImage();

                // Set the opacity level (0.0f - 1.0f)
                float opacity = 0.5f;  // 50% opacity
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

                // Draw the image with reduced opacity
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);

                // Dispose of the graphics object
                g2d.dispose();
            }
        };
        panel.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false); // Make the title panel transparent

        JLabel titleLabel = new JLabel("Welcome to the World of Chelsea");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 90));
        titleLabel.setForeground(Color.BLACK); // White with slight transparency
        titlePanel.add(titleLabel);

        panel.add(titlePanel, BorderLayout.NORTH);

        // Center Panel for Logo and Form
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Chelsea Logo
        JLabel logoLabel = new JLabel(new ImageIcon("E:\\Chelsea\\Chelsea\\Chelsea_FC.svg.png"));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Adds padding around the logo
        centerPanel.add(logoLabel);

        // Form Panel (for username and password fields)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between elements
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        centerPanel.add(formPanel);

        // Button Panel (for login and register buttons)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginButton.setBackground(new Color(21, 57, 162));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // Remove the focus border for a cleaner look
        buttonPanel.add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        registerButton.setBackground(new Color(21, 57, 162));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        buttonPanel.add(registerButton);

        centerPanel.add(buttonPanel);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Action for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chelsea_fc?serverTimezone=UTC", "LIT", "liquid@123");
                    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        cardLayout.show(cardPanel, "Main Menu");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }

                    conn.close();
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Action for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Register");
            }
        });

        return panel;
    }


    private JPanel createRegisterScreen() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Convert Graphics to Graphics2D for better control
                Graphics2D g2d = (Graphics2D) g.create();

                // Load and draw the background image with opacity
                ImageIcon backgroundImage = new ImageIcon("E:\\Chelsea\\Chelsea\\chelsea_stadium.jpg");
                Image image = backgroundImage.getImage();
                float opacity = 0.5f; // 50% opacity for a more subtle effect
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);

                // Dispose of the graphics object
                g2d.dispose();
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 255, 255)); // White color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across columns
        panel.add(titleLabel, gbc);

        // Create form panel with rounded corners and background color
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(true);
        formPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2, true)); // Blue border with rounded corners
        formPanel.setPreferredSize(new Dimension(400, 300)); // Fixed size for the form panel

        // Form Components
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.gridwidth = 1; // Reset to default
        gbc.weightx = 0.5;

        // First Name
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(firstNameLabel, gbc);

        JTextField firstNameField = new JTextField(20);
        firstNameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        // Surname
        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(surnameLabel, gbc);

        JTextField surnameField = new JTextField(20);
        surnameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(surnameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        registerButton.setBackground(new Color(0, 102, 204)); // Blue color
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(150, 40)); // Fixed size for the button
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across columns
        formPanel.add(registerButton, gbc);

        // Add form panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(formPanel, gbc);

        // Action for register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chelsea_fc", "LIT", "liquid@123");
                    String query = "INSERT INTO users (first_name, surname, password, email) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, firstName);
                    stmt.setString(2, surname);
                    stmt.setString(3, password);
                    stmt.setString(4, email);
                    stmt.executeUpdate();
                    conn.close();

                    // Set the current email to be used in personal info screen
                    currentEmail = email;

                    // Switch to the personal info screen after registration
                    cardLayout.show(cardPanel, "Personal Info");

                } catch (SQLException ex) {
                    ex.printStackTrace(); // Print the full error message
                }
            }
        });

        return panel;
    }

    private JPanel createPersonalInfoScreen() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Convert Graphics to Graphics2D for better control
                Graphics2D g2d = (Graphics2D) g.create();

                // Load and draw the background image with full opacity
                ImageIcon backgroundImage = new ImageIcon("E:\\Chelsea\\Chelsea\\per.jpg");
                Image image = backgroundImage.getImage();
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);

                // Dispose of the graphics object
                g2d.dispose();
            }
        };
        panel.setLayout(new BorderLayout());

        // Create container panel with semi-transparent white background
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setOpaque(true);
        containerPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around container
        containerPanel.setPreferredSize(new Dimension(500, 400)); // Adjust size as needed

        // Title Panel with centered Logo and Title
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false); // Make the title panel transparent

        // Chelsea Logo
        ImageIcon logoIcon = new ImageIcon("E:\\Chelsea\\Chelsea\\Chelsea_FC.svg.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setOpaque(false);
        titlePanel.add(logoLabel, BorderLayout.CENTER);

        // Title Text
        JLabel titleLabel = new JLabel("Personal Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 102, 204)); // Chelsea Blue color
        titleLabel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        // Add title panel to container panel
        containerPanel.add(titlePanel, BorderLayout.NORTH);

        // Create form panel with rounded corners and background color
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(true);
        formPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2, true)); // Blue border with rounded corners

        // Form Components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        usernameLabel.setForeground(new Color(0, 102, 204)); // Chelsea Blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Date of Birth
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        dobLabel.setForeground(new Color(0, 102, 204)); // Chelsea Blue color
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(dobLabel, gbc);

        JTextField dobField = new JTextField(20);
        dobField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(dobField, gbc);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        genderLabel.setForeground(new Color(0, 102, 204)); // Chelsea Blue color
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(genderLabel, gbc);

        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderComboBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(genderComboBox, gbc);

        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        updateButton.setBackground(new Color(0, 102, 204)); // Blue color
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setPreferredSize(new Dimension(150, 40)); // Fixed size for the button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across columns
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(updateButton, gbc);

        // Add form panel to container panel
        containerPanel.add(formPanel, BorderLayout.CENTER);

        // Add container panel to the main panel
        panel.add(containerPanel, BorderLayout.CENTER);

        // Action for update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String dob = dobField.getText();
                String gender = (String) genderComboBox.getSelectedItem();

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chelsea_fc", "LIT", "liquid@123");
                    String query = "UPDATE users SET username = ?, dob = ?, gender = ? WHERE email = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, username);
                    stmt.setString(2, dob);
                    stmt.setString(3, gender);
                    stmt.setString(4, currentEmail);
                    stmt.executeUpdate();
                    conn.close();

                    // After updating personal info, redirect to the login screen
                    cardLayout.show(cardPanel, "login");

                } catch (SQLException ex) {
                    ex.printStackTrace(); // Print the full error message
                }
            }
        });

        return panel;
    }

    private JPanel createMainMenu() {
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("6000.jpg");
                Image scaledImage = backgroundImage.getImage().getScaledInstance(
                        getWidth(), getHeight(), Image.SCALE_SMOOTH
                );
                g.drawImage(scaledImage, 0, 0, this);
            }
        };

        // Create a container for the menu with a semi-transparent white background
        JPanel menuContainer = new JPanel();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.setOpaque(true);
        menuContainer.setBackground(new Color(255, 255, 255, 204)); // 80% opacity
        menuContainer.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32)); // Padding
        menuContainer.setMaximumSize(new Dimension(400, 600)); // Max width and height

        // Add Chelsea logo
        ImageIcon logoIcon = new ImageIcon("Chelsea_FC.svg.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0)); // Bottom margin
        menuContainer.add(logoLabel);

        // Add welcome title
        JLabel welcomeLabel = new JLabel("Welcome to Chelsea FC");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(30, 64, 175)); // Dark blue color
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 32, 0)); // Bottom margin
        menuContainer.add(welcomeLabel);

        // Create buttons with functionality
        JButton fixturesButton = createStyledButton("Fixtures");
        JButton trackGoalsButton = createStyledButton("Track Goals");
        JButton matchResultsButton = createStyledButton("Match Results");
        JButton opponentsButton = createStyledButton("Opponents");
        JButton logoutButton = createStyledButton("Logout");

        fixturesButton.addActionListener(e -> showTable("Fixtures", "SELECT * FROM chelsea_ucl_2020_21"));
        trackGoalsButton.addActionListener(e -> showTable("Track Goals", "SELECT * FROM chelsea_ucl_goals"));
        matchResultsButton.addActionListener(e -> showTable("Match Results", "SELECT * FROM chelsea_champions_league_matches"));
        opponentsButton.addActionListener(e -> showTable("Opponents", "SELECT * FROM chelsea_opponents"));
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "You have been logged out.");
                showLoginScreen();
            }
        });

        // Add buttons to container
        JButton[] buttons = {fixturesButton, trackGoalsButton, matchResultsButton, opponentsButton, logoutButton};
        for (JButton button : buttons) {
            menuContainer.add(button);
            menuContainer.add(Box.createRigidArea(new Dimension(0, 24))); // Spacing between buttons
        }

        // Center the menuContainer within the background
        mainPanel.add(menuContainer, BorderLayout.CENTER);

        return mainPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (!text.equals("Logout")) {
                    GradientPaint gp = new GradientPaint(0, 0, new Color(29, 78, 216), getWidth(), 0, new Color(30, 64, 175));
                    g2.setPaint(gp);
                } else {
                    g2.setColor(new Color(220, 38, 38)); // Red color for Logout
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(400, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // Helper method to display a table in a new JFrame
    private void showTable(String title, String query) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chelsea_fc", "LIT", "liquid@123");
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Get column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            // Get data
            ArrayList<String[]> dataList = new ArrayList<>();
            while (rs.next()) {
                String[] rowData = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getString(i);
                }
                dataList.add(rowData);
            }
            String[][] data = new String[dataList.size()][columnCount];
            dataList.toArray(data);

            // Close the connection
            conn.close();

            // Create and display JTable
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame tableFrame = new JFrame(title);
            tableFrame.setSize(800, 600);
            tableFrame.add(scrollPane);
            tableFrame.setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to show the login screen
    private void showLoginScreen() {
        // Replace with actual login panel setup
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, "Login");
    }
}