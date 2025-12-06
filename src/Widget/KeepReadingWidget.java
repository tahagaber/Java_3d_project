package Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class KeepReadingWidget extends JPanel {

    // 🎨 ألوان ديناميكية حسب الثيم
    private Color CARD_BG;
    private Color FONT_COLOR;
    private Color SECONDARY_FONT_COLOR;
    private Color BORDER_COLOR;
    private Color SECONDARY_BG; // 💡 الخلفية (الجزء المتبقي من التحميل)

    private Color PRIMARY_BG;
    private final int ARC = 15;
    private static final Dimension COVER_SIZE = new Dimension(100, 100);

    public KeepReadingWidget() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setPreferredSize(new Dimension(300, 300));
        setMinimumSize(new Dimension(300, 300));

        // استدعاء الألوان أول مرة
        updateThemeColors();

        // إعادة البناء عند تغيير الثيم
        UIManager.addPropertyChangeListener(e -> {
            if ("lookAndFeel".equals(e.getPropertyName())) {
                updateThemeColors();
                revalidate();
                repaint();
            }
        });

        buildUI();
    }

    /** 💡 تحديث الألوان بناءً على الثيم الحالي **/
    private void updateThemeColors() {
        boolean dark = UIManager.getBoolean("laf.dark");

        if (dark) {
            // 🌙 الوضع الداكن
            CARD_BG = new Color(50, 55, 72);
            FONT_COLOR = Color.WHITE;
            SECONDARY_FONT_COLOR = new Color(160, 160, 160);
            BORDER_COLOR = new Color(80, 80, 80);
            SECONDARY_BG = new Color(150, 150, 150, 100); // ✅ اللون اللي طلبته للجزء المتبقي
            PRIMARY_BG = CARD_BG;
        } else {
            // ☀️ الوضع الفاتح
            CARD_BG = new Color(100, 127, 188);
            FONT_COLOR = Color.WHITE;
            SECONDARY_FONT_COLOR = Color.WHITE;
            BORDER_COLOR = new Color(210, 210, 210);
            SECONDARY_BG = new Color(230, 211, 211, 150);
            PRIMARY_BG = CARD_BG;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(PRIMARY_BG);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC, ARC));
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    private void buildUI() {
        removeAll();

        // ================== Header ==================
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Keep Reading");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(FONT_COLOR);
        header.add(title, BorderLayout.WEST);

        JLabel seeAll = new JLabel("See All");
        seeAll.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seeAll.setForeground(new Color(255, 165, 0));
        header.add(seeAll, BorderLayout.EAST);

        add(header);

        // ================== Book Rows ==================
        addBookRow("Inter Beauty", "Anna Leary", new Color(220, 100, 100), 80, 210);
        addBookRow("Beauty of Joles", "Anna Leary", new Color(255, 190, 80), 150, 210);
        addBookRow("Welcome to Jungle", "Anna Leary", new Color(80, 100, 220), 40, 210);

        add(Box.createVerticalGlue());
    }

    private void addBookRow(String title, String author, Color bookColor, int value, int total) {
        JPanel mainRowContainer = new JPanel();
        mainRowContainer.setOpaque(false);
        mainRowContainer.setLayout(new BoxLayout(mainRowContainer, BoxLayout.Y_AXIS));
        mainRowContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel topDetailsPanel = new JPanel(new GridBagLayout());
        topDetailsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // ===== غلاف الكتاب =====
        JPanel coverPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bookColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.setColor(Color.WHITE);
                g2.fillOval(getWidth()/4, getHeight()/3, getWidth()/2, getHeight()/2);
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return COVER_SIZE;
            }
        };
        coverPanel.setOpaque(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 15);
        topDetailsPanel.add(coverPanel, gbc);

        // ===== النصوص =====
        JPanel textAndPercentPanel = new JPanel(new GridBagLayout());
        textAndPercentPanel.setOpaque(false);
        GridBagConstraints textGBC = new GridBagConstraints();

        JPanel detailTextPanel = new JPanel();
        detailTextPanel.setOpaque(false);
        detailTextPanel.setLayout(new BoxLayout(detailTextPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        titleLabel.setForeground(FONT_COLOR);

        JLabel authorLabel = new JLabel(author);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        authorLabel.setForeground(SECONDARY_FONT_COLOR);

        detailTextPanel.add(titleLabel);
        detailTextPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        detailTextPanel.add(authorLabel);

        textGBC.gridx = 0;
        textGBC.weightx = 1.0;
        textGBC.anchor = GridBagConstraints.WEST;
        textAndPercentPanel.add(detailTextPanel, textGBC);

        JLabel percentLabel = new JLabel(value + "/" + total);
        percentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        percentLabel.setForeground(SECONDARY_FONT_COLOR);

        textGBC.gridx = 1;
        textGBC.weightx = 0.0;
        textGBC.insets = new Insets(0, 10, 0, 0);
        textAndPercentPanel.add(percentLabel, textGBC);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        topDetailsPanel.add(textAndPercentPanel, gbc);

        // ===== شريط التقدم =====
        JProgressBar progressBar = new JProgressBar(0, total);
        progressBar.setValue(value);
        progressBar.setPreferredSize(new Dimension(200, 5));
        progressBar.setOpaque(false);
        progressBar.setBorderPainted(false);
        progressBar.setBackground(SECONDARY_BG); // ✅ الجزء المتبقي من التحميل
        progressBar.setForeground(new Color(255, 165, 0)); // الجزء المكتمل
        progressBar.setStringPainted(false);

        mainRowContainer.add(topDetailsPanel);
        mainRowContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        mainRowContainer.add(progressBar);
        mainRowContainer.add(Box.createRigidArea(new Dimension(0, 5)));

        add(mainRowContainer);
    }
}
