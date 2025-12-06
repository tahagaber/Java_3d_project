package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Borrowed Books Management with Enhanced UI and Animations
 * @author Raven
 */
public class FormBorrowedBooks extends javax.swing.JPanel {

    private JPanel borrowedPanel;
    private JPanel overduePanel;
    private JPanel returnedPanel;
    private JComboBox<String> filterCombo;
    private JTextField searchField;
    private List<BorrowRecord> allRecords;
    private Timer pulseTimer;

    public FormBorrowedBooks() {
        initSampleData();
        initComponents();
        displayAllRecords();
        startAnimations();
    }

    private void initSampleData() {
        allRecords = new ArrayList<>();

        // Currently Borrowed (Active)
        allRecords.add(new BorrowRecord("Sarah Johnson", "The Great Gatsby", "2024-01-15", "2024-02-15", "Active", 5));
        allRecords.add(new BorrowRecord("Mike Chen", "1984", "2024-01-20", "2024-02-20", "Active", 10));
        allRecords.add(new BorrowRecord("Emily Davis", "Harry Potter", "2024-01-25", "2024-02-25", "Active", 15));
        allRecords.add(new BorrowRecord("Alex Kumar", "The Hobbit", "2024-01-28", "2024-02-28", "Active", 18));

        // Overdue
        allRecords.add(new BorrowRecord("John Smith", "To Kill a Mockingbird", "2023-12-10", "2024-01-10", "Overdue", -5));
        allRecords.add(new BorrowRecord("Lisa Wang", "Pride and Prejudice", "2023-12-15", "2024-01-15", "Overdue", -10));
        allRecords.add(new BorrowRecord("Tom Brown", "Animal Farm", "2023-12-01", "2024-01-01", "Overdue", -15));

        // Returned
        allRecords.add(new BorrowRecord("Anna Lee", "Dune", "2024-01-05", "2024-02-05", "Returned", 0));
        allRecords.add(new BorrowRecord("David Park", "Brave New World", "2024-01-08", "2024-02-08", "Returned", 0));
        allRecords.add(new BorrowRecord("Maria Garcia", "Jane Eyre", "2024-01-10", "2024-02-10", "Returned", 0));
        allRecords.add(new BorrowRecord("James Wilson", "The Alchemist", "2024-01-12", "2024-02-12", "Returned", 0));
    }

    private void startAnimations() {
        // Pulse animation for overdue items
        pulseTimer = new Timer(1000, e -> {
            Component[] components = overduePanel.getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    comp.repaint();
                }
            }
        });
        pulseTimer.start();
    }

    private void displayAllRecords() {
        borrowedPanel.removeAll();
        overduePanel.removeAll();
        returnedPanel.removeAll();

        for (BorrowRecord record : allRecords) {
            JPanel card = createBorrowCard(record);

            switch (record.status) {
                case "Active":
                    borrowedPanel.add(card);
                    break;
                case "Overdue":
                    overduePanel.add(card);
                    break;
                case "Returned":
                    returnedPanel.add(card);
                    break;
            }
        }

        borrowedPanel.revalidate();
        borrowedPanel.repaint();
        overduePanel.revalidate();
        overduePanel.repaint();
        returnedPanel.revalidate();
        returnedPanel.repaint();
    }

    private void filterRecords() {
        String filter = (String) filterCombo.getSelectedItem();
        String search = searchField.getText().toLowerCase().trim();

        borrowedPanel.removeAll();
        overduePanel.removeAll();
        returnedPanel.removeAll();

        for (BorrowRecord record : allRecords) {
            boolean matchesSearch = search.isEmpty() ||
                    record.userName.toLowerCase().contains(search) ||
                    record.bookTitle.toLowerCase().contains(search);

            boolean matchesFilter = filter.equals("All Status") ||
                    record.status.equals(filter);

            if (matchesSearch && matchesFilter) {
                JPanel card = createBorrowCard(record);

                switch (record.status) {
                    case "Active":
                        borrowedPanel.add(card);
                        break;
                    case "Overdue":
                        overduePanel.add(card);
                        break;
                    case "Returned":
                        returnedPanel.add(card);
                        break;
                }
            }
        }

        borrowedPanel.revalidate();
        borrowedPanel.repaint();
        overduePanel.revalidate();
        overduePanel.repaint();
        returnedPanel.revalidate();
        returnedPanel.repaint();
    }

    private JPanel createBorrowCard(BorrowRecord record) {
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBorder(new EmptyBorder(20, 24, 20, 24));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "background:darken($Panel.background,3%)");

        // Left: Book Icon and Info
        JPanel leftPanel = new JPanel(new BorderLayout(16, 0));
        leftPanel.setOpaque(false);

        // Enhanced Book Icon with gradient-like effect
        JPanel iconWrapper = new JPanel(new GridBagLayout());
        iconWrapper.setPreferredSize(new Dimension(70, 70));
        iconWrapper.setOpaque(true);

        Color iconBg = switch (record.status) {
            case "Active" -> new Color(220, 252, 231);
            case "Overdue" -> new Color(254, 242, 242);
            case "Returned" -> new Color(241, 245, 249);
            default -> new Color(243, 244, 246);
        };

        iconWrapper.setBackground(iconBg);
        iconWrapper.putClientProperty(FlatClientProperties.STYLE, "arc:16");

        JLabel bookIcon = new JLabel("📚");
        bookIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
        iconWrapper.add(bookIcon);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(record.bookTitle);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        userPanel.setOpaque(false);
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));

        JLabel userLabel = new JLabel(record.userName);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        userPanel.add(userIcon);
        userPanel.add(userLabel);

        // Enhanced dates with better spacing and styling
        JPanel datesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        datesPanel.setOpaque(false);
        datesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel borrowedLabel = new JLabel("📅 " + record.borrowDate);
        borrowedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        borrowedLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JLabel arrowLabel = new JLabel("→");
        arrowLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        arrowLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Component.accentColor");

        JLabel dueLabel = new JLabel(record.dueDate);
        dueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dueLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        datesPanel.add(borrowedLabel);
        datesPanel.add(arrowLabel);
        datesPanel.add(dueLabel);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(userPanel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(datesPanel);

        leftPanel.add(iconWrapper, BorderLayout.WEST);
        leftPanel.add(infoPanel, BorderLayout.CENTER);

        // Right: Status and Actions
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        // Enhanced Status Badge with shadow effect
        JLabel statusBadge = createStatusBadge(record);
        statusBadge.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Days info with better styling
        JLabel daysLabel = createDaysLabel(record);
        daysLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Enhanced Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        if (record.status.equals("Active") || record.status.equals("Overdue")) {
            JButton renewBtn = new JButton("🔄 Renew");
            renewBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            renewBtn.setFocusPainted(false);
            renewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            renewBtn.putClientProperty(FlatClientProperties.STYLE,
                    "arc:10;" +
                            "borderWidth:0;" +
                            "background:darken($Panel.background,8%)");
            renewBtn.addActionListener(e -> onRenewBook(record));

            JButton returnBtn = new JButton("✓ Return Book");
            returnBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            returnBtn.setFocusPainted(false);
            returnBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            returnBtn.setForeground(Color.WHITE);
            returnBtn.setBackground(new Color(34, 197, 94));
            returnBtn.putClientProperty(FlatClientProperties.STYLE,
                    "arc:10;" +
                            "borderWidth:0");
            returnBtn.addActionListener(e -> onReturnBook(record, card));

            buttonPanel.add(renewBtn);
            buttonPanel.add(returnBtn);
        } else {
            JButton viewBtn = new JButton("👁 View Details");
            viewBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            viewBtn.setFocusPainted(false);
            viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            viewBtn.putClientProperty(FlatClientProperties.STYLE,
                    "arc:10;" +
                            "borderWidth:0;" +
                            "background:darken($Panel.background,8%)");
            viewBtn.addActionListener(e -> onViewRecord(record));

            buttonPanel.add(viewBtn);
        }

        rightPanel.add(statusBadge);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(daysLabel);
        rightPanel.add(Box.createVerticalStrut(16));
        rightPanel.add(buttonPanel);

        card.add(leftPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        // Enhanced hover animation
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.putClientProperty(FlatClientProperties.STYLE,
                        "arc:20;" +
                                "background:darken($Panel.background,5%)");
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.putClientProperty(FlatClientProperties.STYLE,
                        "arc:20;" +
                                "background:darken($Panel.background,3%)");
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return card;
    }

    private JLabel createStatusBadge(BorrowRecord record) {
        JLabel badge = new JLabel();
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setOpaque(true);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setBorder(new EmptyBorder(6, 16, 6, 16));
        badge.putClientProperty(FlatClientProperties.STYLE, "arc:8");

        switch (record.status) {
            case "Active":
                badge.setForeground(new Color(5, 150, 105));
                badge.setBackground(new Color(209, 250, 229));
                badge.setText("✓ Active");
                break;
            case "Overdue":
                badge.setForeground(new Color(185, 28, 28));
                badge.setBackground(new Color(254, 226, 226));
                badge.setText("⚠ Overdue");
                break;
            case "Returned":
                badge.setForeground(new Color(71, 85, 105));
                badge.setBackground(new Color(226, 232, 240));
                badge.setText("✓ Returned");
                break;
        }

        return badge;
    }

    private JLabel createDaysLabel(BorrowRecord record) {
        JLabel label = new JLabel();
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

        if (record.status.equals("Returned")) {
            label.setText("✓ Completed");
            label.setForeground(new Color(100, 116, 139));
        } else if (record.daysRemaining > 0) {
            label.setText(record.daysRemaining + " days left");
            label.setForeground(new Color(59, 130, 246));
        } else if (record.daysRemaining < 0) {
            label.setText(Math.abs(record.daysRemaining) + " days overdue");
            label.setForeground(new Color(239, 68, 68));
        } else {
            label.setText("⏰ Due today!");
            label.setForeground(new Color(245, 158, 11));
        }

        return label;
    }

    private void onReturnBook(BorrowRecord record, JPanel card) {
        // Fade out animation
        Timer fadeTimer = new Timer(30, null);
        final float[] alpha = {1.0f};

        fadeTimer.addActionListener(e -> {
            alpha[0] -= 0.1f;
            if (alpha[0] <= 0) {
                fadeTimer.stop();
                record.status = "Returned";
                record.daysRemaining = 0;
                displayAllRecords();
                JOptionPane.showMessageDialog(this,
                        "\"" + record.bookTitle + "\" has been returned successfully! ✓",
                        "Book Returned",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            card.repaint();
        });

        fadeTimer.start();
    }

    private void onRenewBook(BorrowRecord record) {
        record.daysRemaining += 14;
        if (record.status.equals("Overdue")) {
            record.status = "Active";
        }
        displayAllRecords();
        JOptionPane.showMessageDialog(this,
                "Book renewed for 14 more days! 🔄",
                "Renewal Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void onViewRecord(BorrowRecord record) {
        JOptionPane.showMessageDialog(this,
                "Book: " + record.bookTitle + "\n" +
                        "Borrower: " + record.userName + "\n" +
                        "Borrowed: " + record.borrowDate + "\n" +
                        "Due Date: " + record.dueDate + "\n" +
                        "Status: " + record.status,
                "Record Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));

        JLabel titleLabel = new JLabel("📚 Borrowed Books Management");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Stats
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));

        long activeCount = allRecords.stream().filter(r -> r.status.equals("Active")).count();
        long overdueCount = allRecords.stream().filter(r -> r.status.equals("Overdue")).count();
        long returnedCount = allRecords.stream().filter(r -> r.status.equals("Returned")).count();

        statsPanel.add(createStatLabel("Active: " + activeCount, new Color(16, 185, 129)));
        statsPanel.add(createStatLabel("Overdue: " + overdueCount, new Color(220, 53, 69)));
        statsPanel.add(createStatLabel("Returned: " + returnedCount, new Color(108, 117, 125)));

        // Search and Filter
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));

        searchField = new JTextField(25);
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by user or book...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc:8;margin:6,10,6,10");
        searchField.addActionListener(e -> filterRecords());

        JButton searchBtn = new JButton("🔍");
        searchBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff;" +
                        "borderWidth:0");
        searchBtn.addActionListener(e -> filterRecords());

        filterCombo = new JComboBox<>(new String[]{"All Status", "Active", "Overdue", "Returned"});
        filterCombo.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        filterCombo.addActionListener(e -> filterRecords());

        controlPanel.add(searchField);
        controlPanel.add(searchBtn);
        controlPanel.add(filterCombo);

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

        // Tabbed Content
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.putClientProperty(FlatClientProperties.STYLE, "tabHeight:40");

        // Active Tab
        borrowedPanel = new JPanel();
        borrowedPanel.setLayout(new BoxLayout(borrowedPanel, BoxLayout.Y_AXIS));
        JScrollPane borrowedScroll = new JScrollPane(borrowedPanel);
        borrowedScroll.setBorder(null);
        borrowedScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("✓ Active (" + activeCount + ")", borrowedScroll);

        // Overdue Tab
        overduePanel = new JPanel();
        overduePanel.setLayout(new BoxLayout(overduePanel, BoxLayout.Y_AXIS));
        JScrollPane overdueScroll = new JScrollPane(overduePanel);
        overdueScroll.setBorder(null);
        overdueScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("⚠ Overdue (" + overdueCount + ")", overdueScroll);

        // Returned Tab
        returnedPanel = new JPanel();
        returnedPanel.setLayout(new BoxLayout(returnedPanel, BoxLayout.Y_AXIS));
        JScrollPane returnedScroll = new JScrollPane(returnedPanel);
        returnedScroll.setBorder(null);
        returnedScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("✓ Returned (" + returnedCount + ")", returnedScroll);

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JLabel createStatLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(color);
        return label;
    }

    private static class BorrowRecord {
        String userName;
        String bookTitle;
        String borrowDate;
        String dueDate;
        String status;
        int daysRemaining;

        BorrowRecord(String userName, String bookTitle, String borrowDate, String dueDate, String status, int daysRemaining) {
            this.userName = userName;
            this.bookTitle = bookTitle;
            this.borrowDate = borrowDate;
            this.dueDate = dueDate;
            this.status = status;
            this.daysRemaining = daysRemaining;
        }
    }

    public static class FormAccount {
    }
}