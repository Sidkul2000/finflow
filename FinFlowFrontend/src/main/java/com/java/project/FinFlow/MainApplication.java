package com.java.project.FinFlow;

import com.java.project.FinFlow.GUIs.ExpensesTablePanel;
import com.java.project.FinFlow.GUIs.LoginPanel;
import com.java.project.FinFlow.GUIs.WelcomePanel;

import javax.swing.*;
import java.awt.*;

public class MainApplication extends JFrame{
    private CardLayout cardLayout;
    private JPanel cards;

    public MainApplication(){
        setTitle("FinFlow");
        setSize(800,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // initilize a layout and a panel for pages showing and redirecting
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // adding different panels(pages) into the main panel
        cards.add(new WelcomePanel(this.cardLayout, this.cards), "welcome");
        cards.add(new LoginPanel(this.cardLayout, this.cards), "login");
        cards.add(new ExpensesTablePanel(this.cardLayout, this.cards, 1L), "expenses");

        // showing the entry pages
        add(cards);
        cardLayout.show(cards,"welcome");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new MainApplication().setVisible(true));
    }
}
