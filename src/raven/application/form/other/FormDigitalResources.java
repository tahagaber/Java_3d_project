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
 * Digital Resources Management
 * @author Raven
 */
public class FormDigitalResources extends JPanel {

    private JPanel ebooksPanel;
    private JPanel audioPanel;
    private JPanel videosPanel;
    private JPanel documentsPanel;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> formatCombo;
    private JTextField searchField;
    private List<DigitalResource> allResources;

    public FormDigitalResources() {
        initSampleData();
        initComponents();
        displayAllResources();
    }

    private void initSampleData() {
        allResources = new ArrayList<>();

        // eBooks
        allResources.add(new DigitalResource("The Lean Startup", "Eric Ries", "eBook", "PDF", "Business", "4.5", "2.3 MB", "Available"));
        allResources.add(new DigitalResource("Clean Code", "Robert Martin", "eBook", "EPUB", "Technology", "4.8", "3.1 MB", "Available"));
        allResources.add(new DigitalResource("Atomic Habits", "James Clear", "eBook", "PDF", "Self-Help", "4.7", "1.8 MB", "Available"));
        allResources.add(new DigitalResource("Sapiens", "Yuval Noah Harari", "eBook", "EPUB", "History", "4.6", "4.2 MB", "Available"));

        // Audio Books
        allResources.add(new DigitalResource("The 7 Habits", "Stephen Covey", "Audio", "MP3", "Self-Help", "4.9", "156 MB", "Available"));
        allResources.add(new DigitalResource("Rich Dad Poor Dad", "Robert Kiyosaki", "Audio", "MP3", "Finance", "4.4", "142 MB", "Available"));
        allResources.add(new DigitalResource("Thinking Fast and Slow", "Daniel Kahneman", "Audio", "MP3", "Psychology", "4.6", "198 MB", "Available"));

        // Video Courses
        allResources.add(new DigitalResource("Java Programming", "John Doe", "Video", "MP4", "Technology", "4.7", "1.2 GB", "Available"));
        allResources.add(new DigitalResource("Digital Marketing", "Jane Smith", "Video", "MP4", "Business", "4.5", "890 MB", "Available"));
        allResources.add(new DigitalResource("Data Science Basics", "Mike Johnson", "Video", "MP4", "Technology", "4.8", "1.5 GB", "Available"));

        // Documents
        allResources.add(new DigitalResource("Research Methods", "Academic Press", "Document", "PDF", "Education", "4.3", "5.6 MB", "Available"));
        allResources.add(new DigitalResource("Financial Reports 2024", "Library Admin", "Document", "XLSX", "Finance", "4.0", "892 KB", "Available"));
        allResources.add(new DigitalResource("Library Guidelines", "Library Board", "Document", "DOCX", "Reference", "4.2", "1.1 MB", "Available"));
    }

    private void displayAllResources() {
        ebooksPanel.removeAll();
        audioPanel.removeAll();
        videosPanel.removeAll();
        documentsPanel.removeAll();

        for (DigitalResource resource : allResources) {
            JPanel card = createResourceCard(resource);

            switch (resource.type) {
                case "eBook":
                    ebooksPanel.add(card);
                    ebooksPanel.add(Box.createVerticalStrut(15));
                    break;
                case "Audio":
                    audioPanel.add(card);
                    audioPanel.add(Box.createVerticalStrut(15));
                    break;
                case "Video":
                    videosPanel.add(card);
                    videosPanel.add(Box.createVerticalStrut(15));
                    break;
                case "Document":
                    documentsPanel.add(card);
                    documentsPanel.add(Box.createVerticalStrut(15));
                    break;
            }
        }

        ebooksPanel.revalidate();
        ebooksPanel.repaint();
        audioPanel.revalidate();
        audioPanel.repaint();
        videosPanel.revalidate();
        videosPanel.repaint();
        documentsPanel.revalidate();
        documentsPanel.repaint();
    }

    private void filterResources() {
        String category = (String) categoryCombo.getSelectedItem();
        String format = (String) formatCombo.getSelectedItem();
        String search = searchField.getText().toLowerCase().trim();

        ebooksPanel.removeAll();
        audioPanel.removeAll();
        videosPanel.removeAll();
        documentsPanel.removeAll();

        for (DigitalResource resource : allResources) {
            boolean matchesSearch = search.isEmpty() ||
                    resource.title.toLowerCase().contains(search) ||
                    resource.author.toLowerCase().contains(search);

            boolean matchesCategory = category.equals("All Categories") ||
                    resource.category.equals(category);

            boolean matchesFormat = format.equals("All Formats") ||
                    resource.format.equals(format);

            if (matchesSearch && matchesCategory && matchesFormat) {
                JPanel card = createResourceCard(resource);

                switch (resource.type) {
                    case "eBook":
                        ebooksPanel.add(card);
                        ebooksPanel.add(Box.createVerticalStrut(15));
                        break;
                    case "Audio":
                        audioPanel.add(card);
                        audioPanel.add(Box.createVerticalStrut(15));
                        break;
                    case "Video":
                        videosPanel.add(card);
                        videosPanel.add(Box.createVerticalStrut(15));
                        break;
                    case "Document":
                        documentsPanel.add(card);
                        documentsPanel.add(Box.createVerticalStrut(15));
                        break;
                }
            }
        }

        ebooksPanel.revalidate();
        ebooksPanel.repaint();
        audioPanel.revalidate();
        audioPanel.repaint();
        videosPanel.revalidate();
        videosPanel.repaint();
        documentsPanel.revalidate();
        documentsPanel.repaint();
    }

    private JPanel createResourceCard(DigitalResource resource) {
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBorder(new EmptyBorder(20, 24, 20, 24));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "background:darken($Panel.background,3%)");

        // Left: Icon and Info
        JPanel leftPanel = new JPanel(new BorderLayout(16, 0));
        leftPanel.setOpaque(false);

        // Resource Icon
        JPanel iconWrapper = new JPanel(new GridBagLayout());
        iconWrapper.setPreferredSize(new Dimension(70, 70));
        iconWrapper.setOpaque(true);

        Color iconBg = switch (resource.type) {
            case "eBook" -> new Color(219, 234, 254);
            case "Audio" -> new Color(254, 249, 195);
            case "Video" -> new Color(254, 226, 226);
            case "Document" -> new Color(220, 252, 231);
            default -> new Color(243, 244, 246);
        };

        iconWrapper.setBackground(iconBg);
        iconWrapper.putClientProperty(FlatClientProperties.STYLE, "arc:16");

        String emoji = switch (resource.type) {
            case "eBook" -> "📖";
            case "Audio" -> "🎧";
            case "Video" -> "🎬";
            case "Document" -> "📄";
            default -> "📁";
        };

        JLabel icon = new JLabel(emoji);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
        iconWrapper.add(icon);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(resource.title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        authorPanel.setOpaque(false);
        authorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel authorIcon = new JLabel("✍");
        authorIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));

        JLabel authorLabel = new JLabel(resource.author);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        authorLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        authorPanel.add(authorIcon);
        authorPanel.add(authorLabel);

        // Details Panel
        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        detailsPanel.setOpaque(false);
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel formatLabel = new JLabel("📋 " + resource.format);
        formatLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formatLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        JLabel sizeLabel = new JLabel("💾 " + resource.size);
        sizeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sizeLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        JLabel ratingLabel = new JLabel("⭐ " + resource.rating);
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ratingLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        detailsPanel.add(formatLabel);
        detailsPanel.add(sizeLabel);
        detailsPanel.add(ratingLabel);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(authorPanel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(detailsPanel);

        leftPanel.add(iconWrapper, BorderLayout.WEST);
        leftPanel.add(infoPanel, BorderLayout.CENTER);

        // Right: Category and Actions
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        // Category Badge
        JLabel categoryBadge = createCategoryBadge(resource.category);
        categoryBadge.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Status
        JLabel statusLabel = new JLabel("✓ " + resource.status);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        statusLabel.setForeground(new Color(34, 197, 94));
        statusLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton viewBtn = new JButton("👁 View");
        viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        viewBtn.setFocusPainted(false);
        viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "borderWidth:0;" +
                        "background:darken($Panel.background,8%)");
        viewBtn.addActionListener(e -> onViewResource(resource));

        JButton downloadBtn = new JButton("⬇ Download");
        downloadBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        downloadBtn.setFocusPainted(false);
        downloadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        downloadBtn.setForeground(Color.WHITE);
        downloadBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "borderWidth:0;" +
                        "background:@accentColor");
        downloadBtn.addActionListener(e -> onDownloadResource(resource));

        buttonPanel.add(viewBtn);
        buttonPanel.add(downloadBtn);

        rightPanel.add(categoryBadge);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(statusLabel);
        rightPanel.add(Box.createVerticalStrut(16));
        rightPanel.add(buttonPanel);

        card.add(leftPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        // Hover Effect
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

    private JLabel createCategoryBadge(String category) {
        JLabel badge = new JLabel(category);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setOpaque(true);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setBorder(new EmptyBorder(5, 12, 5, 12));
        badge.putClientProperty(FlatClientProperties.STYLE, "arc:6");

        switch (category) {
            case "Technology":
                badge.setForeground(new Color(30, 64, 175));
                badge.setBackground(new Color(219, 234, 254));
                break;
            case "Business":
                badge.setForeground(new Color(133, 77, 14));
                badge.setBackground(new Color(254, 243, 199));
                break;
            case "Self-Help":
                badge.setForeground(new Color(136, 19, 55));
                badge.setBackground(new Color(252, 231, 243));
                break;
            case "History":
                badge.setForeground(new Color(101, 163, 13));
                badge.setBackground(new Color(236, 252, 203));
                break;
            case "Finance":
                badge.setForeground(new Color(5, 150, 105));
                badge.setBackground(new Color(209, 250, 229));
                break;
            case "Psychology":
                badge.setForeground(new Color(126, 34, 206));
                badge.setBackground(new Color(237, 233, 254));
                break;
            case "Education":
                badge.setForeground(new Color(194, 65, 12));
                badge.setBackground(new Color(254, 215, 170));
                break;
            default:
                badge.setForeground(new Color(71, 85, 105));
                badge.setBackground(new Color(226, 232, 240));
                break;
        }

        return badge;
    }

    private void onViewResource(DigitalResource resource) {
        JOptionPane.showMessageDialog(this,
                "Title: " + resource.title + "\n" +
                        "Author: " + resource.author + "\n" +
                        "Type: " + resource.type + "\n" +
                        "Format: " + resource.format + "\n" +
                        "Category: " + resource.category + "\n" +
                        "Size: " + resource.size + "\n" +
                        "Rating: " + resource.rating + " ⭐",
                "Resource Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void onDownloadResource(DigitalResource resource) {
        // Simulate download progress
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JDialog progressDialog = new JDialog();
        progressDialog.setTitle("Downloading " + resource.title);
        progressDialog.setModal(false);
        progressDialog.setSize(400, 100);
        progressDialog.setLocationRelativeTo(this);
        progressDialog.add(progressBar);
        progressDialog.setVisible(true);

        Timer timer = new Timer(50, null);
        final int[] progress = {0};

        timer.addActionListener(e -> {
            progress[0] += 5;
            progressBar.setValue(progress[0]);

            if (progress[0] >= 100) {
                timer.stop();
                progressDialog.dispose();
                JOptionPane.showMessageDialog(this,
                        "\"" + resource.title + "\" downloaded successfully! ✓",
                        "Download Complete",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        timer.start();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 15));

        JLabel titleLabel = new JLabel("📚 Digital Resources Library");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Stats
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));

        long ebookCount = allResources.stream().filter(r -> r.type.equals("eBook")).count();
        long audioCount = allResources.stream().filter(r -> r.type.equals("Audio")).count();
        long videoCount = allResources.stream().filter(r -> r.type.equals("Video")).count();
        long docCount = allResources.stream().filter(r -> r.type.equals("Document")).count();

        statsPanel.add(createStatLabel("eBooks: " + ebookCount, new Color(59, 130, 246)));
        statsPanel.add(createStatLabel("Audio: " + audioCount, new Color(245, 158, 11)));
        statsPanel.add(createStatLabel("Videos: " + videoCount, new Color(239, 68, 68)));
        statsPanel.add(createStatLabel("Documents: " + docCount, new Color(34, 197, 94)));

        // Search and Filters
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));

        searchField = new JTextField(25);
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search resources...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc:8;margin:6,10,6,10");
        searchField.addActionListener(e -> filterResources());

        JButton searchBtn = new JButton("🔍");
        searchBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff;" +
                        "borderWidth:0");
        searchBtn.addActionListener(e -> filterResources());

        categoryCombo = new JComboBox<>(new String[]{
                "All Categories", "Technology", "Business", "Self-Help",
                "History", "Finance", "Psychology", "Education", "Reference"
        });
        categoryCombo.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        categoryCombo.addActionListener(e -> filterResources());

        formatCombo = new JComboBox<>(new String[]{
                "All Formats", "PDF", "EPUB", "MP3", "MP4", "DOCX", "XLSX"
        });
        formatCombo.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        formatCombo.addActionListener(e -> filterResources());

        controlPanel.add(searchField);
        controlPanel.add(searchBtn);
        controlPanel.add(categoryCombo);
        controlPanel.add(formatCombo);

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

        // eBooks Tab
        ebooksPanel = new JPanel();
        ebooksPanel.setLayout(new BoxLayout(ebooksPanel, BoxLayout.Y_AXIS));
        JScrollPane ebooksScroll = new JScrollPane(ebooksPanel);
        ebooksScroll.setBorder(null);
        ebooksScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("📖 eBooks (" + ebookCount + ")", ebooksScroll);

        // Audio Tab
        audioPanel = new JPanel();
        audioPanel.setLayout(new BoxLayout(audioPanel, BoxLayout.Y_AXIS));
        JScrollPane audioScroll = new JScrollPane(audioPanel);
        audioScroll.setBorder(null);
        audioScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("🎧 Audio (" + audioCount + ")", audioScroll);

        // Videos Tab
        videosPanel = new JPanel();
        videosPanel.setLayout(new BoxLayout(videosPanel, BoxLayout.Y_AXIS));
        JScrollPane videosScroll = new JScrollPane(videosPanel);
        videosScroll.setBorder(null);
        videosScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("🎬 Videos (" + videoCount + ")", videosScroll);

        // Documents Tab
        documentsPanel = new JPanel();
        documentsPanel.setLayout(new BoxLayout(documentsPanel, BoxLayout.Y_AXIS));
        JScrollPane docsScroll = new JScrollPane(documentsPanel);
        docsScroll.setBorder(null);
        docsScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("📄 Documents (" + docCount + ")", docsScroll);

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JLabel createStatLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(color);
        return label;
    }

    private static class DigitalResource {
        String title;
        String author;
        String type;
        String format;
        String category;
        String rating;
        String size;
        String status;

        DigitalResource(String title, String author, String type, String format,
                        String category, String rating, String size, String status) {
            this.title = title;
            this.author = author;
            this.type = type;
            this.format = format;
            this.category = category;
            this.rating = rating;
            this.size = size;
            this.status = status;
        }
    }
}