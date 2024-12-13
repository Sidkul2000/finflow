package com.java.project.FinFlow.GUIs;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    final private CardLayout cardLayout;
    final private JPanel cards;

    public LoginPanel(CardLayout cardLayout, JPanel cards) {
        this.cards = cards;
        this.cardLayout = cardLayout;
        // Use GridBagLayout for flexible control of layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 1; // Set grid width
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Username Label and TextField
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0

        Font textFont = new Font("Arial", Font.PLAIN, 18);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(textFont);
        add(usernameLabel, gbc);

        usernameField = new JTextField(13);
        usernameField.setFont(textFont);
        gbc.gridx = 1; // Column 1
        add(usernameField, gbc);

        // Password Label and PasswordField
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(textFont);
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(13);
        passwordField.setFont(textFont);
        gbc.gridx = 1; // Column 1
        add(passwordField, gbc);

        // Buttons
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;

        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        loginButton.addActionListener(e -> login());
        cancelButton.addActionListener(e -> backToWelcome());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);
    }

    private void backToWelcome() {
        cardLayout.show(cards, "welcome");
    }

    private void login() {

    }
}
