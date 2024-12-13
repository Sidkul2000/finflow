package com.java.project.FinFlow.GUIs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.java.project.FinFlow.models.Records;

public class ExpenseTableModel extends DefaultTableModel {
    private List<Records> recordsList;
    private int UPDATE_COLUMN = 5;
    private int DELETE_COLUMN = 6;

    public ExpenseTableModel(Object[] columnNames) {
        super(columnNames, 0); // Start with zero rows and define columns from array
        this.recordsList = new ArrayList<>();
    }

    public void addRecord(Records record) {
        Object[] row = new Object[]{
        	record.getRecord_id(),
            record.getCategory().getCategory_name(),
            record.getAmount(),
            record.getTimestamp().toString().replace('T', ' '),
            record.getAmount(),
            "Update",
            "Delete"
        };
        super.addRow(row);
        recordsList.add(record);
    }

    public Records getRecordAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < recordsList.size()) {
            return recordsList.get(rowIndex);
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == UPDATE_COLUMN || columnIndex == DELETE_COLUMN) {
            return JButton.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Only the button column should be editable
        return column == UPDATE_COLUMN || column == DELETE_COLUMN;
    }
}


