package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Enhanced Book Details UI - Dark/Light Mode Compatible
 * @author Raven
 */
public class FormRead extends javax.swing.JPanel {

    private JLabel stockLabel;
    private JSpinner quantitySpinner;

    public FormRead() {
        initComponents();
    }

    private void onAddToCart() {
        int qty = (Integer) quantitySpinner.getValue();
        JOptionPane.showMessageDialog(this,
                qty + " book(s) added to cart! 🛒",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void onBuyNow() {
        int qty = (Integer) quantitySpinner.getValue();
        JOptionPane.showMessageDialog(this,
                "Buying " + qty + " book(s)...",
                "Checkout",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void onShare() {
        JOptionPane.showMessageDialog(this, "Share link copied to clipboard!", "Share", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onCompare() {
        JOptionPane.showMessageDialog(this, "Added to comparison list", "Compare", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel container = new JPanel(new BorderLayout(20, 20));
        container.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Top Navigation Panel (Search + Breadcrumb)
        JPanel topNav = new JPanel(new BorderLayout(20, 15));

        // Search Bar
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));

        JTextField searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search for books, authors, or categories...");
        searchField.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "margin:8,12,8,12");
        searchField.setPreferredSize(new Dimension(0, 42));

        JButton searchBtn = new JButton("🔍");
        searchBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        searchBtn.setPreferredSize(new Dimension(50, 42));
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff;" +
                        "borderWidth:0");
        searchBtn.addActionListener(e -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Searching for: " + query, "Search", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        // Breadcrumb
        JPanel breadcrumb = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel bc1 = new JLabel("Home");
        bc1.putClientProperty(FlatClientProperties.STYLE, "foreground:$Component.accentColor");
        bc1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel bc2 = new JLabel(" / ");
        bc2.putClientProperty(FlatClientProperties.STYLE, "foreground:$Component.borderColor");
        JLabel bc3 = new JLabel("Books");
        bc3.putClientProperty(FlatClientProperties.STYLE, "foreground:$Component.accentColor");
        bc3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel bc4 = new JLabel(" / ");
        bc4.putClientProperty(FlatClientProperties.STYLE, "foreground:$Component.borderColor");
        JLabel bc5 = new JLabel("The Great Gatsby");
        bc5.setFont(new Font("Segoe UI", Font.BOLD, 13));

        breadcrumb.add(bc1);
        breadcrumb.add(bc2);
        breadcrumb.add(bc3);
        breadcrumb.add(bc4);
        breadcrumb.add(bc5);

        topNav.add(searchPanel, BorderLayout.NORTH);
        topNav.add(breadcrumb, BorderLayout.SOUTH);

        // Main Card Panel
        JPanel mainCard = new JPanel(new BorderLayout(30, 30));
        mainCard.setBorder(new EmptyBorder(35, 35, 35, 35));
        mainCard.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        // Top Section
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 40);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Book Cover with Badge
        JPanel coverContainer = new JPanel(null);
        coverContainer.setPreferredSize(new Dimension(260, 380));

        JLabel coverLabel = new JLabel("📖");
        coverLabel.setBounds(10, 10, 240, 340);
        coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 110));
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coverLabel.setVerticalAlignment(SwingConstants.CENTER);
        coverLabel.setOpaque(true);
        coverLabel.putClientProperty(FlatClientProperties.STYLE,
                "arc:15;" +
                        "background:darken($Panel.background,3%);" +
                        "border:1,1,1,1,$Component.borderColor");

        // Bestseller Badge
        JLabel badge = new JLabel(" 🔥 Bestseller ");
        badge.setBounds(20, 15, 100, 30);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBackground(new Color(255, 87, 34));
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.putClientProperty(FlatClientProperties.STYLE, "arc:6");

        coverContainer.add(coverLabel);
        coverContainer.add(badge);

        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(coverContainer, gbc);

        // Book Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        // Category Tag
        JLabel categoryTag = new JLabel(" Classic Literature ");
        categoryTag.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categoryTag.putClientProperty(FlatClientProperties.STYLE,
                "arc:5;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff");
        categoryTag.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("The Great Gatsby");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Author with Icon
        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        authorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel authorIcon = new JLabel("✍️");
        JLabel authorLabel = new JLabel("F. Scott Fitzgerald");
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        authorLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");
        authorPanel.add(authorIcon);
        authorPanel.add(authorLabel);

        // Rating with detailed breakdown
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        ratingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel starsLabel = new JLabel("⭐⭐⭐⭐⭐");
        starsLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        JLabel ratingText = new JLabel("4.5");
        ratingText.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel reviewCount = new JLabel("(2,450 reviews)");
        reviewCount.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reviewCount.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");
        reviewCount.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ratingPanel.add(starsLabel);
        ratingPanel.add(ratingText);
        ratingPanel.add(reviewCount);

        // Price Section
        JPanel priceSection = new JPanel();
        priceSection.setLayout(new BoxLayout(priceSection, BoxLayout.Y_AXIS));
        priceSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel priceRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        JLabel priceLabel = new JLabel("$12.99");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        priceLabel.setForeground(new Color(40, 167, 69));

        JLabel oldPrice = new JLabel("<html><strike>$18.99</strike></html>");
        oldPrice.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        oldPrice.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JLabel discount = new JLabel(" -32% ");
        discount.setFont(new Font("Segoe UI", Font.BOLD, 12));
        discount.setForeground(new Color(220, 53, 69));
        discount.setOpaque(true);
        discount.setBackground(new Color(255, 235, 238));
        discount.putClientProperty(FlatClientProperties.STYLE, "arc:5");

        priceRow.add(priceLabel);
        priceRow.add(oldPrice);
        priceRow.add(discount);
        priceSection.add(priceRow);

        // Stock Status
        stockLabel = new JLabel("✓ In Stock (45 available)");
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        stockLabel.setForeground(new Color(40, 167, 69));
        stockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Description
        JTextArea descArea = new JTextArea(
                "A timeless tale of wealth, love, and the American Dream set in the Jazz Age. " +
                        "Follow Jay Gatsby's quest to reunite with his lost love in this literary " +
                        "masterpiece that captures the essence of an era. Perfect for literature lovers " +
                        "and students alike."
        );
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descArea.setBorder(null);
        descArea.setMaximumSize(new Dimension(550, 120));
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descArea.setOpaque(false);

        // Book Details Grid
        JPanel detailsGrid = new JPanel(new GridLayout(2, 2, 20, 12));
        detailsGrid.setBorder(new EmptyBorder(15, 15, 15, 15));
        detailsGrid.setMaximumSize(new Dimension(550, 100));
        detailsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsGrid.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "background:darken($Panel.background,3%)");

        addDetailItem(detailsGrid, "📚 Format", "Hardcover");
        addDetailItem(detailsGrid, "📄 Pages", "180 pages");
        addDetailItem(detailsGrid, "🌍 Language", "English");
        addDetailItem(detailsGrid, "📅 Published", "April 1925");

        // Quantity Selector
        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        qtyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 45, 1));
        quantitySpinner.setPreferredSize(new Dimension(80, 35));
        quantitySpinner.putClientProperty(FlatClientProperties.STYLE, "arc:8");

        qtyPanel.add(qtyLabel);
        qtyPanel.add(quantitySpinner);

        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton buyBtn = new JButton("💳 Buy Now");
        buyBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        buyBtn.setPreferredSize(new Dimension(160, 48));
        buyBtn.setFocusPainted(false);
        buyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buyBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff;" +
                        "borderWidth:0");
        buyBtn.addActionListener(e -> onBuyNow());

        JButton cartBtn = new JButton("🛒 Add to Cart");
        cartBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cartBtn.setPreferredSize(new Dimension(160, 48));
        cartBtn.setFocusPainted(false);
        cartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartBtn.setForeground(Color.WHITE);
        cartBtn.setBackground(new Color(40, 167, 69));
        cartBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "borderWidth:0");
        cartBtn.addActionListener(e -> onAddToCart());

        JButton wishBtn = new JButton("♥");
        wishBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        wishBtn.setPreferredSize(new Dimension(48, 48));
        wishBtn.setFocusPainted(false);
        wishBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        wishBtn.setForeground(new Color(220, 53, 69));
        wishBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "borderColor:#dc3545;" +
                        "borderWidth:2");

        buttonPanel.add(buyBtn);
        buttonPanel.add(cartBtn);
        buttonPanel.add(wishBtn);

        // Secondary Actions
        JPanel secondaryActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        secondaryActions.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton shareBtn = createIconButton("🔗 Share", e -> onShare());
        JButton compareBtn = createIconButton("⚖️ Compare", e -> onCompare());

        secondaryActions.add(shareBtn);
        secondaryActions.add(compareBtn);

        // Add all to info panel
        infoPanel.add(categoryTag);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(authorPanel);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(ratingPanel);
        infoPanel.add(Box.createVerticalStrut(18));
        infoPanel.add(priceSection);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(stockLabel);
        infoPanel.add(Box.createVerticalStrut(18));
        infoPanel.add(descArea);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(detailsGrid);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(qtyPanel);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(buttonPanel);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(secondaryActions);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(infoPanel, gbc);

        // Reviews Section
        JPanel reviewsSection = createReviewsSection();

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(topPanel);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(reviewsSection);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainCard.add(scrollPane, BorderLayout.CENTER);

        container.add(topNav, BorderLayout.NORTH);
        container.add(mainCard, BorderLayout.CENTER);
        add(container, BorderLayout.CENTER);
    }

    private JButton createIconButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        btn.addActionListener(action);
        return btn;
    }

    private void addDetailItem(JPanel panel, String label, String value) {
        JPanel item = new JPanel(new BorderLayout(5, 0));
        item.setOpaque(false);

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelComp.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 13));

        item.add(labelComp, BorderLayout.NORTH);
        item.add(valueComp, BorderLayout.SOUTH);
        panel.add(item);
    }

    private JPanel createReviewsSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel header = new JLabel("⭐ Customer Reviews");
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        section.add(header);
        section.add(Box.createVerticalStrut(25));

        section.add(createReview("Sarah M.", 5, "2 days ago",
                "An absolute masterpiece! Beautiful prose and timeless story.", true));
        section.add(Box.createVerticalStrut(20));

        section.add(createReview("John D.", 4, "1 week ago",
                "Great book with deep symbolism. Highly recommended!", false));
        section.add(Box.createVerticalStrut(20));

        section.add(createReview("Emily R.", 5, "2 weeks ago",
                "One of my all-time favorites. The Jazz Age setting is perfect.", true));

        return section;
    }

    private JPanel createReview(String name, int stars, String time, String comment, boolean verified) {
        JPanel review = new JPanel();
        review.setLayout(new BoxLayout(review, BoxLayout.Y_AXIS));
        review.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIManager.getColor("Component.borderColor")),
                new EmptyBorder(0, 0, 20, 0)
        ));
        review.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        if (verified) {
            JLabel verifiedBadge = new JLabel(" ✓ Verified ");
            verifiedBadge.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            verifiedBadge.setForeground(new Color(40, 167, 69));
            verifiedBadge.setOpaque(true);
            verifiedBadge.setBackground(new Color(212, 237, 218));
            verifiedBadge.putClientProperty(FlatClientProperties.STYLE, "arc:4");
            headerPanel.add(nameLabel);
            headerPanel.add(verifiedBadge);
        } else {
            headerPanel.add(nameLabel);
        }

        String starString = "⭐".repeat(stars);
        JLabel starsLabel = new JLabel(starString);
        starsLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        JLabel timeLabel = new JLabel("• " + time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        timeLabel.putClientProperty(FlatClientProperties.STYLE, "foreground:$Label.disabledForeground");

        headerPanel.add(starsLabel);
        headerPanel.add(timeLabel);

        JLabel commentLabel = new JLabel("<html>" + comment + "</html>");
        commentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        commentLabel.setBorder(new EmptyBorder(8, 0, 0, 0));

        review.add(headerPanel);
        review.add(commentLabel);

        return review;
    }
}