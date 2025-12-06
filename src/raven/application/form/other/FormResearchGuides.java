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
 * Research Guides Management
 * @author Raven
 */
public class FormResearchGuides extends JPanel {

    private JPanel academicPanel;
    private JPanel sciencePanel;
    private JPanel businessPanel;
    private JPanel artsPanel;
    private JComboBox<String> difficultyCombo;
    private JComboBox<String> subjectCombo;
    private JTextField searchField;
    private List<ResearchGuide> allGuides;

    public FormResearchGuides() {
        initSampleData();
        initComponents();
        displayAllGuides();
    }

    private void initSampleData() {
        allGuides = new ArrayList<>();

        // Academic Guides
        allGuides.add(new ResearchGuide("Academic Writing Guide", "Dr. Sarah Johnson", "Academic", "English", "Beginner", "45 min", "12,543", "4.8", "Last updated: Dec 2024"));
        allGuides.add(new ResearchGuide("Critical Thinking Methods", "Prof. Michael Chen", "Academic", "Philosophy", "Intermediate", "60 min", "8,921", "4.6", "Last updated: Nov 2024"));
        allGuides.add(new ResearchGuide("Research Methodology", "Dr. Emily Davis", "Academic", "Research", "Advanced", "90 min", "15,234", "4.9", "Last updated: Dec 2024"));
        allGuides.add(new ResearchGuide("Citation & References", "James Wilson", "Academic", "Writing", "Beginner", "30 min", "20,145", "4.7", "Last updated: Oct 2024"));

        // Science Guides
        allGuides.add(new ResearchGuide("Biology Research Methods", "Dr. Lisa Wang", "Science", "Biology", "Intermediate", "75 min", "9,432", "4.5", "Last updated: Nov 2024"));
        allGuides.add(new ResearchGuide("Chemistry Lab Guide", "Prof. Tom Brown", "Science", "Chemistry", "Advanced", "120 min", "6,789", "4.8", "Last updated: Dec 2024"));
        allGuides.add(new ResearchGuide("Physics Experiments", "Dr. Anna Lee", "Science", "Physics", "Intermediate", "85 min", "7,654", "4.6", "Last updated: Sep 2024"));
        allGuides.add(new ResearchGuide("Environmental Studies", "Maria Garcia", "Science", "Environment", "Beginner", "50 min", "11,234", "4.7", "Last updated: Dec 2024"));

        // Business Guides
        allGuides.add(new ResearchGuide("Market Research Guide", "David Park", "Business", "Marketing", "Intermediate", "65 min", "14,567", "4.9", "Last updated: Nov 2024"));
        allGuides.add(new ResearchGuide("Financial Analysis", "Robert Taylor", "Business", "Finance", "Advanced", "95 min", "10,234", "4.8", "Last updated: Dec 2024"));
        allGuides.add(new ResearchGuide("Business Plan Writing", "Jennifer Lee", "Business", "Management", "Beginner", "55 min", "18,765", "4.6", "Last updated: Oct 2024"));
        allGuides.add(new ResearchGuide("Data Analytics Guide", "Alex Kumar", "Business", "Analytics", "Intermediate", "70 min", "13,456", "4.7", "Last updated: Dec 2024"));

        // Arts & Humanities
        allGuides.add(new ResearchGuide("Art History Research", "Sophie Martin", "Arts", "History", "Intermediate", "60 min", "5,432", "4.5", "Last updated: Nov 2024"));
        allGuides.add(new ResearchGuide("Literary Analysis", "Christopher Davis", "Arts", "Literature", "Advanced", "80 min", "8,765", "4.8", "Last updated: Dec 2024"));
        allGuides.add(new ResearchGuide("Music Theory Basics", "Patricia Wilson", "Arts", "Music", "Beginner", "45 min", "9,876", "4.6", "Last updated: Oct 2024"));
    }

    private void displayAllGuides() {
        academicPanel.removeAll();
        sciencePanel.removeAll();
        businessPanel.removeAll();
        artsPanel.removeAll();

        for (ResearchGuide guide : allGuides) {
            JPanel card = createGuideCard(guide);

            switch (guide.category) {
                case "Academic":
                    academicPanel.add(card);
                    academicPanel.add(Box.createVerticalStrut(15));
                    break;
                case "Science":
                    sciencePanel.add(card);
                    sciencePanel.add(Box.createVerticalStrut(15));
                    break;
                case "Business":
                    businessPanel.add(card);
                    businessPanel.add(Box.createVerticalStrut(15));
                    break;
                case "Arts":
                    artsPanel.add(card);
                    artsPanel.add(Box.createVerticalStrut(15));
                    break;
            }
        }

        academicPanel.revalidate();
        academicPanel.repaint();
        sciencePanel.revalidate();
        sciencePanel.repaint();
        businessPanel.revalidate();
        businessPanel.repaint();
        artsPanel.revalidate();
        artsPanel.repaint();
    }

    private void filterGuides() {
        String difficulty = (String) difficultyCombo.getSelectedItem();
        String subject = (String) subjectCombo.getSelectedItem();
        String search = searchField.getText().toLowerCase().trim();

        academicPanel.removeAll();
        sciencePanel.removeAll();
        businessPanel.removeAll();
        artsPanel.removeAll();

        for (ResearchGuide guide : allGuides) {
            boolean matchesSearch = search.isEmpty() ||
                    guide.title.toLowerCase().contains(search) ||
                    guide.author.toLowerCase().contains(search) ||
                    guide.subject.toLowerCase().contains(search);

            boolean matchesDifficulty = difficulty.equals("All Levels") ||
                    guide.difficulty.equals(difficulty);

            boolean matchesSubject = subject.equals("All Subjects") ||
                    guide.subject.equals(subject);

            if (matchesSearch && matchesDifficulty && matchesSubject) {
                JPanel card = createGuideCard(guide);

                switch (guide.category) {
                    case "Academic":
                        academicPanel.add(card);
                        academicPanel.add(Box.createVerticalStrut(15));
                        break;
                    case "Science":
                        sciencePanel.add(card);
                        sciencePanel.add(Box.createVerticalStrut(15));
                        break;
                    case "Business":
                        businessPanel.add(card);
                        businessPanel.add(Box.createVerticalStrut(15));
                        break;
                    case "Arts":
                        artsPanel.add(card);
                        artsPanel.add(Box.createVerticalStrut(15));
                        break;
                }
            }
        }

        academicPanel.revalidate();
        academicPanel.repaint();
        sciencePanel.revalidate();
        sciencePanel.repaint();
        businessPanel.revalidate();
        businessPanel.repaint();
        artsPanel.revalidate();
        artsPanel.repaint();
    }

    private JPanel createGuideCard(ResearchGuide guide) {
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBorder(new EmptyBorder(20, 24, 20, 24));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "background:darken($Panel.background,3%)");

        // Left: Icon and Info
        JPanel leftPanel = new JPanel(new BorderLayout(16, 0));
        leftPanel.setOpaque(false);

        // Guide Icon
        JPanel iconWrapper = new JPanel(new GridBagLayout());
        iconWrapper.setPreferredSize(new Dimension(70, 70));
        iconWrapper.setOpaque(true);

        Color iconBg = switch (guide.category) {
            case "Academic" -> new Color(219, 234, 254);
            case "Science" -> new Color(220, 252, 231);
            case "Business" -> new Color(254, 249, 195);
            case "Arts" -> new Color(252, 231, 243);
            default -> new Color(243, 244, 246);
        };

        iconWrapper.setBackground(iconBg);
        iconWrapper.putClientProperty(FlatClientProperties.STYLE, "arc:16");

        String emoji = switch (guide.category) {
            case "Academic" -> "📚";
            case "Science" -> "🔬";
            case "Business" -> "💼";
            case "Arts" -> "🎨";
            default -> "📖";
        };

        JLabel icon = new JLabel(emoji);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
        iconWrapper.add(icon);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(guide.title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        authorPanel.setOpaque(false);
        authorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel authorIcon = new JLabel("👤");
        authorIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));

        JLabel authorLabel = new JLabel(guide.author);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        authorLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        authorPanel.add(authorIcon);
        authorPanel.add(authorLabel);

        // Details Panel
        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        detailsPanel.setOpaque(false);
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel timeLabel = new JLabel("⏱ " + guide.duration);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        JLabel viewsLabel = new JLabel("👁 " + guide.views);
        viewsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        viewsLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        JLabel ratingLabel = new JLabel("⭐ " + guide.rating);
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ratingLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        detailsPanel.add(timeLabel);
        detailsPanel.add(viewsLabel);
        detailsPanel.add(ratingLabel);

        // Updated Info
        JLabel updatedLabel = new JLabel(guide.lastUpdated);
        updatedLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        updatedLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");
        updatedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(authorPanel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(detailsPanel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(updatedLabel);

        leftPanel.add(iconWrapper, BorderLayout.WEST);
        leftPanel.add(infoPanel, BorderLayout.CENTER);

        // Right: Badges and Actions
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        // Subject Badge
        JLabel subjectBadge = createSubjectBadge(guide.subject);
        subjectBadge.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Difficulty Badge
        JLabel difficultyBadge = createDifficultyBadge(guide.difficulty);
        difficultyBadge.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton viewBtn = new JButton("👁 View Guide");
        viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        viewBtn.setFocusPainted(false);
        viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "borderWidth:0;" +
                        "background:darken($Panel.background,8%)");
        viewBtn.addActionListener(e -> onViewGuide(guide));

        JButton startBtn = new JButton("▶ Start Learning");
        startBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        startBtn.setFocusPainted(false);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startBtn.setForeground(Color.WHITE);
        startBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;" +
                        "borderWidth:0;" +
                        "background:@accentColor");
        startBtn.addActionListener(e -> onStartGuide(guide));

        buttonPanel.add(viewBtn);
        buttonPanel.add(startBtn);

        rightPanel.add(subjectBadge);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(difficultyBadge);
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

    private JLabel createSubjectBadge(String subject) {
        JLabel badge = new JLabel(subject);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setOpaque(true);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setBorder(new EmptyBorder(5, 12, 5, 12));
        badge.putClientProperty(FlatClientProperties.STYLE, "arc:6");

        switch (subject) {
            case "English", "Writing":
                badge.setForeground(new Color(30, 64, 175));
                badge.setBackground(new Color(219, 234, 254));
                break;
            case "Biology", "Chemistry", "Physics", "Environment":
                badge.setForeground(new Color(5, 150, 105));
                badge.setBackground(new Color(209, 250, 229));
                break;
            case "Marketing", "Finance", "Management", "Analytics":
                badge.setForeground(new Color(133, 77, 14));
                badge.setBackground(new Color(254, 243, 199));
                break;
            case "History", "Literature", "Music":
                badge.setForeground(new Color(136, 19, 55));
                badge.setBackground(new Color(252, 231, 243));
                break;
            default:
                badge.setForeground(new Color(71, 85, 105));
                badge.setBackground(new Color(226, 232, 240));
                break;
        }

        return badge;
    }

    private JLabel createDifficultyBadge(String difficulty) {
        JLabel badge = new JLabel(difficulty);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setOpaque(true);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setBorder(new EmptyBorder(5, 12, 5, 12));
        badge.putClientProperty(FlatClientProperties.STYLE, "arc:6");

        switch (difficulty) {
            case "Beginner":
                badge.setForeground(new Color(5, 150, 105));
                badge.setBackground(new Color(209, 250, 229));
                break;
            case "Intermediate":
                badge.setForeground(new Color(245, 158, 11));
                badge.setBackground(new Color(254, 243, 199));
                break;
            case "Advanced":
                badge.setForeground(new Color(220, 38, 38));
                badge.setBackground(new Color(254, 226, 226));
                break;
            default:
                badge.setForeground(new Color(71, 85, 105));
                badge.setBackground(new Color(226, 232, 240));
                break;
        }

        return badge;
    }

    private void onViewGuide(ResearchGuide guide) {
        JOptionPane.showMessageDialog(this,
                "Title: " + guide.title + "\n" +
                        "Author: " + guide.author + "\n" +
                        "Category: " + guide.category + "\n" +
                        "Subject: " + guide.subject + "\n" +
                        "Difficulty: " + guide.difficulty + "\n" +
                        "Duration: " + guide.duration + "\n" +
                        "Views: " + guide.views + "\n" +
                        "Rating: " + guide.rating + " ⭐\n" +
                        guide.lastUpdated,
                "Guide Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void onStartGuide(ResearchGuide guide) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Loading guide...");

        JDialog loadingDialog = new JDialog();
        loadingDialog.setTitle("Starting " + guide.title);
        loadingDialog.setModal(false);
        loadingDialog.setSize(450, 100);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.add(progressBar);
        loadingDialog.setVisible(true);

        Timer timer = new Timer(30, null);
        final int[] progress = {0};

        timer.addActionListener(e -> {
            progress[0] += 2;
            progressBar.setValue(progress[0]);

            if (progress[0] >= 100) {
                timer.stop();
                loadingDialog.dispose();
                JOptionPane.showMessageDialog(this,
                        "Welcome to \"" + guide.title + "\"!\n\n" +
                                "Duration: " + guide.duration + "\n" +
                                "Difficulty: " + guide.difficulty + "\n\n" +
                                "Ready to start your learning journey? 🎓",
                        "Guide Ready",
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

        JLabel titleLabel = new JLabel("🎓 Research Guides Library");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Stats
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));

        long academicCount = allGuides.stream().filter(g -> g.category.equals("Academic")).count();
        long scienceCount = allGuides.stream().filter(g -> g.category.equals("Science")).count();
        long businessCount = allGuides.stream().filter(g -> g.category.equals("Business")).count();
        long artsCount = allGuides.stream().filter(g -> g.category.equals("Arts")).count();

        statsPanel.add(createStatLabel("Academic: " + academicCount, new Color(59, 130, 246)));
        statsPanel.add(createStatLabel("Science: " + scienceCount, new Color(34, 197, 94)));
        statsPanel.add(createStatLabel("Business: " + businessCount, new Color(245, 158, 11)));
        statsPanel.add(createStatLabel("Arts: " + artsCount, new Color(236, 72, 153)));

        // Search and Filters
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));

        searchField = new JTextField(25);
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search guides...");
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc:8;margin:6,10,6,10");
        searchField.addActionListener(e -> filterGuides());

        JButton searchBtn = new JButton("🔍");
        searchBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;" +
                        "background:@accentColor;" +
                        "foreground:#ffffff;" +
                        "borderWidth:0");
        searchBtn.addActionListener(e -> filterGuides());

        difficultyCombo = new JComboBox<>(new String[]{
                "All Levels", "Beginner", "Intermediate", "Advanced"
        });
        difficultyCombo.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        difficultyCombo.addActionListener(e -> filterGuides());

        subjectCombo = new JComboBox<>(new String[]{
                "All Subjects", "English", "Writing", "Philosophy", "Research",
                "Biology", "Chemistry", "Physics", "Environment",
                "Marketing", "Finance", "Management", "Analytics",
                "History", "Literature", "Music"
        });
        subjectCombo.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        subjectCombo.addActionListener(e -> filterGuides());

        controlPanel.add(searchField);
        controlPanel.add(searchBtn);
        controlPanel.add(difficultyCombo);
        controlPanel.add(subjectCombo);

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

        // Academic Tab
        academicPanel = new JPanel();
        academicPanel.setLayout(new BoxLayout(academicPanel, BoxLayout.Y_AXIS));
        JScrollPane academicScroll = new JScrollPane(academicPanel);
        academicScroll.setBorder(null);
        academicScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("📚 Academic (" + academicCount + ")", academicScroll);

        // Science Tab
        sciencePanel = new JPanel();
        sciencePanel.setLayout(new BoxLayout(sciencePanel, BoxLayout.Y_AXIS));
        JScrollPane scienceScroll = new JScrollPane(sciencePanel);
        scienceScroll.setBorder(null);
        scienceScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("🔬 Science (" + scienceCount + ")", scienceScroll);

        // Business Tab
        businessPanel = new JPanel();
        businessPanel.setLayout(new BoxLayout(businessPanel, BoxLayout.Y_AXIS));
        JScrollPane businessScroll = new JScrollPane(businessPanel);
        businessScroll.setBorder(null);
        businessScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("💼 Business (" + businessCount + ")", businessScroll);

        // Arts Tab
        artsPanel = new JPanel();
        artsPanel.setLayout(new BoxLayout(artsPanel, BoxLayout.Y_AXIS));
        JScrollPane artsScroll = new JScrollPane(artsPanel);
        artsScroll.setBorder(null);
        artsScroll.getVerticalScrollBar().setUnitIncrement(16);
        tabbedPane.addTab("🎨 Arts (" + artsCount + ")", artsScroll);

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JLabel createStatLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(color);
        return label;
    }

    private static class ResearchGuide {
        String title;
        String author;
        String category;
        String subject;
        String difficulty;
        String duration;
        String views;
        String rating;
        String lastUpdated;

        ResearchGuide(String title, String author, String category, String subject,
                      String difficulty, String duration, String views, String rating, String lastUpdated) {
            this.title = title;
            this.author = author;
            this.category = category;
            this.subject = subject;
            this.difficulty = difficulty;
            this.duration = duration;
            this.views = views;
            this.rating = rating;
            this.lastUpdated = lastUpdated;
        }
    }
}