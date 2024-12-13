package com.java.project.FinFlow.GUIs;

import javax.swing.*;

import com.java.project.FinFlow.models.Categories;
import com.java.project.FinFlow.models.Records;
import com.java.project.FinFlow.services.RecordService;

import java.awt.*;

public class UpdateDialog extends JDialog {
	private ExpensesTablePanel parentPanel;
    private final JTextField amountField;
    private final JComboBox<Categories> categoryComboBox;
    private final JButton updateButton;
    private final Records record;
    private final RecordService recordService = new RecordService();

    public UpdateDialog(Frame owner, Records record, java.util.List<Categories> categories, ExpensesTablePanel parentPanel) {
        super(owner, "Update Record", true);
        this.record = record;
        this.parentPanel = parentPanel;

        setLayout(new GridLayout(0, 2));

        add(new JLabel("Amount:"));
        amountField = new JTextField(String.valueOf(record.getAmount()));
        add(amountField);

        add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>();
        for (Categories category : categories) {
            categoryComboBox.addItem(category);
        }
        categoryComboBox.setSelectedItem(record.getCategory());
        add(categoryComboBox);

        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateRecord());
        add(updateButton);

        pack();
        setLocationRelativeTo(owner);
    }

    private void updateRecord() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            Categories selectedCategory = (Categories) categoryComboBox.getSelectedItem();

            record.setAmount(amount);
            record.setCategory(selectedCategory);
            System.out.println("------e-wcds-cds-dsc-ds-Amount: " + amount + "selectedCategoryId: " + selectedCategory.getId());
            recordService.updateRecord(record.getRecord_id().toString(), amount, selectedCategory.getId()).thenRun(() -> {
                JOptionPane.showMessageDialog(this, "Record updated successfully.");
                parentPanel.fetchExpensesData();
                dispose();
            }).exceptionally(ex -> {
                JOptionPane.showMessageDialog(this, "Failed to update record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });
            JOptionPane.showMessageDialog(this, "Record updated successfully.");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

