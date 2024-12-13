package com.java.project.FinFlow.GUIs;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.java.project.FinFlow.models.Records;

public class DeleteButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private boolean isPushed;

    public DeleteButtonEditor(JCheckBox checkBox, JTable table, ExpensesTablePanel parentPanel) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            if (isPushed) {
                int row = table.convertRowIndexToModel(table.getEditingRow());
                Records record = ((ExpenseTableModel) table.getModel()).getRecordAt(row);
                DeleteDialog dialog = new DeleteDialog(null, record, parentPanel);
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

