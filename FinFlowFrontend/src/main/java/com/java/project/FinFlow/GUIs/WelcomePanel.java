package com.java.project.FinFlow.GUIs;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(CardLayout cardLayout, JPanel cards){
        // Init panel
        setLayout(new BorderLayout());
        add(new JLabel("Welcome to Finflow!", JLabel.CENTER), BorderLayout.CENTER);
        // Buttons
        JButton toLogin = new JButton("Login");
        JButton toRegister = new JButton("Register");
        JButton toExpenses = new JButton("Expenses");
        toLogin.addActionListener(e->{
            cardLayout.show(cards, "login");
        });
        toExpenses.addActionListener(e->{
            cardLayout.show(cards, "expenses");
        });
        // Putting Buttons at the SOUTH
        GridLayout buttonLayout = new GridLayout(1,2);
        JPanel buttonPanel = new JPanel(buttonLayout);
        buttonPanel.add(toLogin);
        buttonPanel.add(toRegister);
        buttonPanel.add(toExpenses);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
