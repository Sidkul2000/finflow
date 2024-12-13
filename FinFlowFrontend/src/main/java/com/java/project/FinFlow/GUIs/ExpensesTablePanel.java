package com.java.project.FinFlow.GUIs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.java.project.FinFlow.services.RecordService;

import java.awt.*;
import java.time.LocalDate;

import com.java.project.FinFlow.mapper.mappers;
import com.java.project.FinFlow.models.Categories;
import com.java.project.FinFlow.models.Records;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExpensesTablePanel extends JPanel {
	private JButton expensesButton;
    private JLabel headingLabel;
    private JComboBox<Categories> categoryComboBox;
    private DatePicker fromDateDatePicker;
    private DatePicker toDateDatePicker;
    private JButton applyFiltersButton;
    private JTable expensesTable;
    private DefaultTableModel tableModel;
    private RecordService recordService = new RecordService();

    final private CardLayout cardLayout;
    final private JPanel cards;
    final private Long userId;
    
    private Categories selectedCategory;
    
    public ExpensesTablePanel(CardLayout cardLayout, JPanel cards, Long userId) {
        this.cardLayout = cardLayout;
        this.cards = cards;
        this.userId = userId;
        
        initializeUI();
        fetchCategories();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Heading
        headingLabel = new JLabel("Expenses Overview", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headingLabel, BorderLayout.NORTH);
        
        expensesButton = new JButton("Load Expenses");
        add(expensesButton, BorderLayout.SOUTH);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        categoryComboBox = new JComboBox<>();
        fromDateDatePicker = createDatePicker("From Date");
        toDateDatePicker = createDatePicker("To Date");
        applyFiltersButton = new JButton("Apply Filters");
        
        filterPanel.add(categoryComboBox);
        filterPanel.add(fromDateDatePicker);
        filterPanel.add(toDateDatePicker);
        filterPanel.add(applyFiltersButton);
        
        add(filterPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Category", "Amount", "Date", "Description", "Update", "Delete"};
        Object[][] data = {};
        
        tableModel = new ExpenseTableModel(columnNames);
        expensesTable = new JTable(tableModel);
        
        CompletableFuture<List<Categories>> futureCategories = recordService.getCategories();
        
        expensesTable.getColumn("Update").setCellRenderer(new ButtonRenderer());
        expensesTable.getColumn("Update").setCellEditor(new ButtonEditor(new JCheckBox(), expensesTable, futureCategories.join(), this));
        
        expensesTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        expensesTable.getColumn("Delete").setCellEditor(new DeleteButtonEditor(new JCheckBox(), expensesTable, this));
        
        JScrollPane scrollPane = new JScrollPane(expensesTable);
        add(scrollPane, BorderLayout.CENTER);

        setupActions();
    }
    
    private DatePicker createDatePicker(String buttonText) {
        DatePickerSettings settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("yyyy-MM-dd");
        DatePicker datePicker = new DatePicker(settings);
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setText(buttonText);
        return datePicker;
    }
    
    public void updateTable(List<Records> records) {
        SwingUtilities.invokeLater(() -> {
        	ExpenseTableModel model = (ExpenseTableModel) expensesTable.getModel();
            model.setRowCount(0);

            for (Records record : records) {
                model.addRecord(record);
            }
        });
    }

    private void setupActions() {
        expensesButton.addActionListener(e -> fetchExpensesData());
        
        categoryComboBox.addActionListener(e -> {
            selectedCategory = (Categories) categoryComboBox.getSelectedItem();
            System.out.println("selectedCategory: " + selectedCategory);
        });
        
        applyFiltersButton.addActionListener(e -> applyFilters());
    }
    
    private void applyFilters() {
    	System.out.println("apply filters - selected category: " + categoryComboBox.getSelectedItem());
    	Categories selectedCategory = (Categories) categoryComboBox.getSelectedItem();
        LocalDate fromDate = fromDateDatePicker.getDate();
        LocalDate toDate = toDateDatePicker.getDate();
        
        if (selectedCategory != null || fromDate != null || toDate != null) {
            fetchFilteredData(selectedCategory.getId() == -1L ? null : selectedCategory.getId(), fromDate, toDate);
        } else {
            JOptionPane.showMessageDialog(this, "Please select all filters", "Error", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    public void fetchExpensesData() {
    	recordService.getAllRecordsByUserId(userId)
				     .thenAccept(jsonData -> {
			            try {
			            	System.out.println("------fetchExpenseData: jsonData: " + jsonData);
			                List<Records> records = mappers.parseRecordList(jsonData);
			                System.out.println("------fetchExpenseData: records: " + records);
			                updateTable(records);
			            } catch (Exception e) {
			                e.printStackTrace();
			                JOptionPane.showMessageDialog(this, "Failed to parse expenses data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			            }
		        	 })
    				 .exceptionally(e -> {
    					 e.printStackTrace();
    					 JOptionPane.showMessageDialog(this, "Failed to fetch expenses data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    					 return null;
    				 });
    }
    
    public void fetchCategories() {
        recordService.getCategories()
            .thenAccept(categories -> SwingUtilities.invokeLater(() -> {
                categoryComboBox.removeAllItems();
                categoryComboBox.addItem(new Categories(-1L, "No Selection"));
                for (Categories category : categories) {
                    categoryComboBox.addItem(category);
                }
            }))
            .exceptionally(e -> {
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(this, "Failed to fetch categories: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE)
                );
                return null;
            });
    }
    
    public void fetchFilteredData(Long categoryId, LocalDate fromDate, LocalDate toDate) {
        recordService.getFilteredRecordsByUserId(userId, categoryId, fromDate, toDate)
			        .thenAccept(jsonData -> {
									            try {
									                List<Records> records = mappers.parseRecordList(jsonData);
									                updateTable(records);
									            } catch (Exception e) {
									                e.printStackTrace();
									                JOptionPane.showMessageDialog(this, "Failed to parse expenses data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
									            }
								        	 })
					.exceptionally(e -> {
											SwingUtilities.invokeLater(() ->
																			JOptionPane.showMessageDialog(this, "Failed to fetch data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE)
																	  );
											return null;
									});
    }
}
