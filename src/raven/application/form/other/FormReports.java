package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class FormReports extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public FormReports() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[grow]", "[][grow]"));

        // Header
        JPanel header = createHeader();
        add(header, "wrap,growx");

        // Table
        JPanel tablePanel = createTablePanel();
        add(tablePanel, "grow");
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new MigLayout("fill,insets 15", "[]push[][]", "[]"));

        // Title
        JLabel title = new JLabel("📊 Reports");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");

        // Search Field
        JTextField search = new JTextField(15);
        search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search reports...");
        search.putClientProperty(FlatClientProperties.STYLE,
                "arc:15");

        // Export Button
        JButton btnExport = new JButton("Export");
        btnExport.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "borderWidth:0;" +
                        "focusWidth:0;" +
                        "background:$Component.accentColor;" +
                        "foreground:#fff");

        panel.add(title);
        panel.add(search);
        panel.add(btnExport);

        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:15;" +
                        "background:darken(@background,3%)");

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new MigLayout("fill,insets 15", "[grow]", "[grow]"));

        // Create table
        String[] columns = {"#", "Report Name", "Date", "Type", "Status"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        // Style table
        table.putClientProperty(FlatClientProperties.STYLE,
                "rowHeight:50;" +
                        "showHorizontalLines:true;" +
                        "intercellSpacing:0,1;" +
                        "selectionBackground:lighten(@background,5%);" +
                        "selectionForeground:@foreground");

        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE,
                "height:45;" +
                        "font:bold;" +
                        "background:darken(@background,7%)");

        // Status renderer
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());

        // Center align
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(2).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        // Sample data
        addData();

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scroll, "grow");

        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:15;" +
                        "background:darken(@background,3%)");

        return panel;
    }

    private void addData() {
        model.addRow(new Object[]{"01", "Monthly Sales Report", "Dec 01, 2024", "Sales", "Completed"});
        model.addRow(new Object[]{"02", "Inventory Analysis", "Dec 02, 2024", "Inventory", "Pending"});
        model.addRow(new Object[]{"03", "Customer Feedback", "Dec 03, 2024", "Support", "Completed"});
        model.addRow(new Object[]{"04", "Revenue Summary Q4", "Dec 04, 2024", "Finance", "Progress"});
        model.addRow(new Object[]{"05", "Product Performance", "Dec 05, 2024", "Sales", "Completed"});
        model.addRow(new Object[]{"06", "Marketing Campaign", "Dec 05, 2024", "Marketing", "Pending"});
        model.addRow(new Object[]{"07", "Quarterly Financial", "Dec 06, 2024", "Finance", "Progress"});
    }

    private class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            label.setHorizontalAlignment(CENTER);
            label.setOpaque(true);

            String status = value.toString();

            if (!isSelected) {
                switch (status) {
                    case "Completed":
                        label.setBackground(new Color(16, 185, 129, 25));
                        label.setForeground(new Color(16, 185, 129));
                        label.setText("✓ Completed");
                        break;
                    case "Pending":
                        label.setBackground(new Color(245, 158, 11, 25));
                        label.setForeground(new Color(245, 158, 11));
                        label.setText("⏱ Pending");
                        break;
                    case "Progress":
                        label.setBackground(new Color(59, 130, 246, 25));
                        label.setForeground(new Color(59, 130, 246));
                        label.setText("⟳ Progress");
                        break;
                }
            }

            label.setBorder(new EmptyBorder(8, 12, 8, 12));
            return label;
        }
    }
}