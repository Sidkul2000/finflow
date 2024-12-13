package com.java.project.FinFlow.GUIs;

import javax.swing.*;

import com.java.project.FinFlow.models.Records;
import com.java.project.FinFlow.services.RecordService;

import java.awt.*;

public class DeleteDialog extends JDialog {
    private final JButton deleteButton;
    private final Records record;
    private final RecordService recordService;
    private final ExpensesTablePanel parentPanel;

    public DeleteDialog(Frame owner, Records record, ExpensesTablePanel parentPanel) {
        super(owner, "Delete Record", true);
        this.record = record;
        this.recordService = new RecordService();
        this.parentPanel = parentPanel;

        setLayout(new BorderLayout());
        add(new JLabel("Are you sure you want to delete this record?"), BorderLayout.CENTER);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteRecord());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    private void deleteRecord() {
        recordService.deleteRecord(record.getRecord_id().toString()).thenRun(() -> {
            JOptionPane.showMessageDialog(this, "Record deleted successfully.");
            parentPanel.fetchExpensesData();
            dispose();
        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(this, "Failed to delete record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    }
}
