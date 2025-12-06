package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage Patrons - Simple Table View
 * @author Raven
 */
public class FormManagePatrons extends JPanel {

    private JTable patronsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private JComboBox<String> typeFilter;
    private List<Patron> allPatrons;

    public FormManagePatrons() {
        initSampleData();
        initComponents();
        loadTableData();
    }

    private void initSampleData() {
        allPatrons = new ArrayList<>();

        allPatrons.add(new Patron("P001", "Sarah Johnson", "sarah.j@email.com", "+1 555-0101", "Student", "Active", "15"));
        allPatrons.add(new Patron("P002", "Michael Chen", "m.chen@email.com", "+1 555-0102", "Faculty", "Active", "28"));
        allPatrons.add(new Patron("P003", "Emily Davis", "emily.d@email.com", "+1 555-0103", "Student", "Active", "8"));
        allPatrons.add(new Patron("P004", "James Wilson", "j.wilson@email.com", "+1 555-0104", "Staff", "Suspended", "3"));
        allPatrons.add(new Patron("P005", "Lisa Wang", "lisa.w@email.com", "+1 555-0105", "Student", "Active", "22"));
        allPatrons.add(new Patron("P006", "David Park", "d.park@email.com", "+1 555-0106", "Public", "Active", "5"));
        allPatrons.add(new Patron("P007", "Maria Garcia", "maria.g@email.com", "+1 555-0107", "Faculty", "Active", "31"));
        allPatrons.add(new Patron("P008", "John Smith", "j.smith@email.com", "+1 555-0108", "Student", "Inactive", "0"));
        allPatrons.add(new Patron("P009", "Anna Lee", "anna.l@email.com", "+1 555-0109", "Staff", "Active", "12"));
        allPatrons.add(new Patron("P010", "Tom Brown", "t.brown@email.com", "+1 555-0110", "Public", "Active", "7"));
        allPatrons.add(new Patron("P011", "Sophie Martin", "sophie.m@email.com", "+1 555-0111", "Student", "Active", "19"));
        allPatrons.add(new Patron("P012", "Alex Kumar", "alex.k@email.com", "+1 555-0112", "Faculty", "Active", "25"));
    }

    private void loadTableData() {
        tableModel.setRowCount(0);

        String search = searchField.getText().toLowerCase().trim();
        String statusF = (String) statusFilter.getSelectedItem();
        String typeF = (String) typeFilter.getSelectedItem();

        for (Patron patron : allPatrons) {
            boolean matchesSearch = search.isEmpty() ||
                    patron.name.toLowerCase().contains(search) ||
                    patron.id.toLowerCase().contains(search) ||
                    patron.email.toLowerCase().contains(search);

            boolean matchesStatus = statusF.equals("All Status") || patron.status.equals(statusF);
            boolean matchesType = typeF.equals("All Types") || patron.type.equals(typeF);

            if (matchesSearch && matchesStatus && matchesType) {
                tableModel.addRow(new Object[]{
                        patron.id,
                        patron.name,
                        patron.email,
                        patron.phone,
                        patron.type,
                        patron.status,
                        patron.booksCount
                });
            }
        }
    }

    private void viewPatronDetails(int row) {
        if (row < 0) return;

        String id = (String) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);
        String email = (String) tableModel.getValueAt(row, 2);
        String phone = (String) tableModel.getValueAt(row, 3);
        String type = (String) tableModel.getValueAt(row, 4);
        String status = (String) tableModel.getValueAt(row, 5);
        String books = (String) tableModel.getValueAt(row, 6);

        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        panel.add(createInfoLabel("Patron ID:"));
        panel.add(createValueLabel(id));
        panel.add(createInfoLabel("Full Name:"));
        panel.add(createValueLabel(name));
        panel.add(createInfoLabel("Email:"));
        panel.add(createValueLabel(email));
        panel.add(createInfoLabel("Phone:"));
        panel.add(createValueLabel(phone));
        panel.add(createInfoLabel("Type:"));
        panel.add(createValueLabel(type));
        panel.add(createInfoLabel("Status:"));
        panel.add(createValueLabel(status));
        panel.add(createInfoLabel("Books Borrowed:"));
        panel.add(createValueLabel(books));

        JOptionPane.showMessageDialog(this, panel,
                "Patron Details", JOptionPane.PLAIN_MESSAGE);
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }

    private void editPatron(int row) {
        if (row < 0) return;

        JOptionPane.showMessageDialog(this,
                "Edit functionality for: " + tableModel.getValueAt(row, 1),
                "Edit Patron",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deletePatron(int row) {
        if (row < 0) return;

        String name = (String) tableModel.getValueAt(row, 1);
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete patron: " + name + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            String id = (String) tableModel.getValueAt(row, 0);
            allPatrons.removeIf(p -> p.id.equals(id));
            loadTableData();
            JOptionPane.showMessageDialog(this,
                    "Patron deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addNewPatron() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Student", "Faculty", "Staff", "Public"});

        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Type:"));
        panel.add(typeCombo);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Add New Patron", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String type = (String) typeCombo.getSelectedItem();

            if (!name.isEmpty() && !email.isEmpty()) {
                String newId = "P" + String.format("%03d", allPatrons.size() + 1);
                allPatrons.add(new Patron(newId, name, email, phone, type, "Active", "0"));
                loadTableData();
                JOptionPane.showMessageDialog(this,
                        "Patron added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));

        JLabel titleLabel = new JLabel("👥 Manage Patrons");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Stats
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));

        long activeCount = allPatrons.stream().filter(p -> p.status.equals("Active")).count();
        long totalCount = allPatrons.size();

        statsPanel.add(createStatLabel("Total: " + totalCount, new Color(59, 130, 246)));
        statsPanel.add(createStatLabel("Active: " + activeCount, new Color(34, 197, 94)));
        statsPanel.add(createStatLabel("Suspended: " + (totalCount - activeCount), new Color(239, 68, 68)));

        // Controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));

        searchField = new JTextField(25);
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by name, ID or email...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc:8;margin:6,10,6,10");
        searchField.addActionListener(e -> loadTableData());

        JButton searchBtn = new JButton("🔍");
        searchBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;background:@accentColor;foreground:#ffffff;borderWidth:0");
        searchBtn.addActionListener(e -> loadTableData());

        statusFilter = new JComboBox<>(new String[]{"All Status", "Active", "Inactive", "Suspended"});
        statusFilter.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        statusFilter.addActionListener(e -> loadTableData());

        typeFilter = new JComboBox<>(new String[]{"All Types", "Student", "Faculty", "Staff", "Public"});
        typeFilter.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        typeFilter.addActionListener(e -> loadTableData());

        JButton addBtn = new JButton("➕ Add Patron");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;background:@accentColor;foreground:#ffffff;borderWidth:0");
        addBtn.addActionListener(e -> addNewPatron());

        controlPanel.add(searchField);
        controlPanel.add(searchBtn);
        controlPanel.add(statusFilter);
        controlPanel.add(typeFilter);
        controlPanel.add(addBtn);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.add(titleLabel, BorderLayout.WEST);
        titleRow.add(statsPanel, BorderLayout.EAST);

        titleRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(titleRow);
        topPanel.add(Box.createVerticalStrut(15));
        topPanel.add(controlPanel);

        headerPanel.add(topPanel, BorderLayout.CENTER);

        // Table
        String[] columns = {"ID", "Name", "Email", "Phone", "Type", "Status", "Books"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patronsTable = new JTable(tableModel);
        patronsTable.setRowHeight(45);
        patronsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        patronsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patronsTable.setShowVerticalLines(false);
        patronsTable.setIntercellSpacing(new Dimension(0, 8));
        patronsTable.putClientProperty(FlatClientProperties.STYLE,
                "selectionBackground:lighten(@accentColor,20%);selectionForeground:@foreground");

        // Header styling
        JTableHeader header = patronsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.putClientProperty(FlatClientProperties.STYLE,
                "background:darken($Panel.background,5%)");

        // Column widths
        patronsTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        patronsTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        patronsTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        patronsTable.getColumnModel().getColumn(3).setPreferredWidth(130);
        patronsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        patronsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        patronsTable.getColumnModel().getColumn(6).setPreferredWidth(80);

        // Status column renderer
        patronsTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String status = (String) value;
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("Segoe UI", Font.BOLD, 11));

                if (!isSelected) {
                    switch (status) {
                        case "Active":
                            label.setForeground(new Color(5, 150, 105));
                            label.setBackground(new Color(209, 250, 229));
                            break;
                        case "Inactive":
                            label.setForeground(new Color(71, 85, 105));
                            label.setBackground(new Color(226, 232, 240));
                            break;
                        case "Suspended":
                            label.setForeground(new Color(220, 38, 38));
                            label.setBackground(new Color(254, 226, 226));
                            break;
                    }
                }

                return label;
            }
        });

        // Double click to view
        patronsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    viewPatronDetails(patronsTable.getSelectedRow());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(patronsTable);
        scrollPane.setBorder(null);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, "arc:12");

        // Action panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton viewBtn = new JButton("👁 View Details");
        viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        viewBtn.setFocusPainted(false);
        viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;borderWidth:0;background:darken($Panel.background,8%)");
        viewBtn.addActionListener(e -> viewPatronDetails(patronsTable.getSelectedRow()));

        JButton editBtn = new JButton("✏ Edit");
        editBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        editBtn.setFocusPainted(false);
        editBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;borderWidth:0;background:darken($Panel.background,8%)");
        editBtn.addActionListener(e -> editPatron(patronsTable.getSelectedRow()));

        JButton deleteBtn = new JButton("🗑 Delete");
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.setFocusPainted(false);
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.setForeground(new Color(220, 38, 38));
        deleteBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;borderWidth:0;background:darken($Panel.background,8%)");
        deleteBtn.addActionListener(e -> deletePatron(patronsTable.getSelectedRow()));

        actionPanel.add(viewBtn);
        actionPanel.add(editBtn);
        actionPanel.add(deleteBtn);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private JLabel createStatLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(color);
        return label;
    }

    private static class Patron {
        String id;
        String name;
        String email;
        String phone;
        String type;
        String status;
        String booksCount;

        Patron(String id, String name, String email, String phone,
               String type, String status, String booksCount) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.type = type;
            this.status = status;
            this.booksCount = booksCount;
        }
    }
}