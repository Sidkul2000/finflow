package com.java.project.FinFlow.GUIs;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.java.project.FinFlow.models.Categories;
import com.java.project.FinFlow.models.Records;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox, JTable table, List<Categories> categories, ExpensesTablePanel parentPanel) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            if (isPushed) {
                int row = table.convertRowIndexToModel(table.getEditingRow());
                Records record = ((ExpenseTableModel) table.getModel()).getRecordAt(row);
                System.out.println("Record to be updated: " + record);
                UpdateDialog dialog = new UpdateDialog(null, record, categories, parentPanel);
                dialog.setVisible(true);
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        button.setText((value == null) ? "" : value.toString());
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        isPushed = false;
        return button.getText();
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}

