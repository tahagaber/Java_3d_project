package Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class KeepReadingWidget extends JPanel {

    // 💡 المتغيرات الجديدة التي سيتم تعيين قيمها بناءً على الثيم
    private Color CARD_BG;
    private Color FONT_COLOR;
    private Color SECONDARY_FONT_COLOR;
    private Color BORDER_COLOR;

    // الألوان القديمة المستخدمة سابقًا
    private Color PRIMARY_BG; // سنستخدمها فقط لـ paintComponent
    private final int ARC = 15; // Border Radius للكارد

    // التحكم في حجم غلاف الكتاب (الكارد الملون بجوار العنوان)
    private static final Dimension COVER_SIZE = new Dimension(200, 150); // الحجم الكبير المطلوب

    public KeepReadingWidget() {
        // 💡 استدعاء الدالة لتعيين الألوان عند بناء الودجت
        updateThemeColors();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        // setBackground(PRIMARY_BG); // تم إزالة هذا السطر لأن الرسم يتم في paintComponent

        // الأبعاد القياسية للودجت
        setPreferredSize(new Dimension(300, 300));
        setMinimumSize(new Dimension(300, 300));

        // بناء المحتوى الداخلي للكارد
        buildUI();
    }

    // 💡 الدالة المضافة للتحكم في الألوان بناءً على الثيم
    private void updateThemeColors() {
        boolean dark = UIManager.getBoolean("laf.dark");

        if (dark) {
            // الوضع الداكن
            CARD_BG = new Color(50, 55, 72);
            FONT_COLOR = Color.WHITE;
            SECONDARY_FONT_COLOR = new Color(150, 150, 150);
            BORDER_COLOR = new Color(70, 70, 70);
            PRIMARY_BG = CARD_BG; // تم تعيينه للرسم
        } else {
            // الوضع الفاتح
            // ملاحظة: قد تحتاج لضبط الألوان الفاتحة لتظهر بشكل جيد، خاصة الخلفية
            CARD_BG = new Color(100, 127, 188); // تم تغيير اللون ليكون أبيض تقليدي لوضع الفاتح
            FONT_COLOR = Color.WHITE;
            SECONDARY_FONT_COLOR = new Color(100, 100, 100);
            BORDER_COLOR = new Color(220, 220, 220);
            PRIMARY_BG = CARD_BG; // تم تعيينه للرسم
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // رسم خلفية الكارد بحواف دائرية باستخدام اللون المحدث
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
        // 1. العنوان "Keep Reading" و "See All"
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Keep Reading");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(FONT_COLOR); // 💡 استخدام اللون المحدث
        header.add(title, BorderLayout.WEST);

        JLabel seeAll = new JLabel("See All");
        seeAll.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seeAll.setForeground(new Color(255, 165, 0));
        header.add(seeAll, BorderLayout.EAST);

        add(header);

        // 2. إضافة صفوف الكتب (3 صفوف كأمثلة)
        addBookRow("Inter Beauty", "Anna Leary", new Color(220, 100, 100), 80, 210);
        addBookRow("Beauty of Joles", "Anna Leary", new Color(255, 190, 80), 150, 210);
        addBookRow("Welcome to Jungle", "Anna Leary", new Color(80, 100, 220), 40, 210);

        add(Box.createVerticalGlue());
    }

    private void addBookRow(String title, String author, Color bookColor, int value, int total) {
        // ... (باقي الكود كما هو)
        JPanel mainRowContainer = new JPanel();
        mainRowContainer.setOpaque(false);
        mainRowContainer.setLayout(new BoxLayout(mainRowContainer, BoxLayout.Y_AXIS));
        mainRowContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel topDetailsPanel = new JPanel(new GridBagLayout());
        topDetailsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;

        // =================================== 1. صورة الغلاف (الكارد) ===================================
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
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 15);
        topDetailsPanel.add(coverPanel, gbc);

        // =================================== 2. تفاصيل الكتاب والنص (العنوان والمؤلف والنسبة) ===================================
        JPanel textAndPercentPanel = new JPanel(new GridBagLayout());
        textAndPercentPanel.setOpaque(false);
        GridBagConstraints textGBC = new GridBagConstraints();

        // 2a. تفاصيل الكتاب (العنوان والمؤلف)
        JPanel detailTextPanel = new JPanel();
        detailTextPanel.setOpaque(false);
        detailTextPanel.setLayout(new BoxLayout(detailTextPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        titleLabel.setForeground(FONT_COLOR); // 💡 استخدام اللون المحدث
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel authorLabel = new JLabel(author);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        authorLabel.setForeground(SECONDARY_FONT_COLOR); // 💡 استخدام اللون الثانوي المحدث
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        detailTextPanel.add(titleLabel);
        detailTextPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        detailTextPanel.add(authorLabel);

        textGBC.gridx = 0;
        textGBC.gridy = 0;
        textGBC.weightx = 1.0;
        textGBC.anchor = GridBagConstraints.WEST;
        textGBC.fill = GridBagConstraints.HORIZONTAL;
        textAndPercentPanel.add(detailTextPanel, textGBC);


        // 2b. النسبة المئوية في اليمين
        JLabel percentLabel = new JLabel(value + "/" + total);
        percentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        percentLabel.setForeground(SECONDARY_FONT_COLOR); // 💡 استخدام اللون الثانوي المحدث

        textGBC.gridx = 1;
        textGBC.gridy = 0;
        textGBC.weightx = 0.0;
        textGBC.anchor = GridBagConstraints.NORTHWEST;
        textGBC.insets = new Insets(0, 10, 0, 0);
        textAndPercentPanel.add(percentLabel, textGBC);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        topDetailsPanel.add(textAndPercentPanel, gbc);

        // =================================== 3. شريط التقدم (تحت الكل) ===================================
        JProgressBar progressBar = new JProgressBar(0, total);
        progressBar.setValue(value);
        progressBar.setPreferredSize(new Dimension(200, 5));
        progressBar.setOpaque(false);
        progressBar.setBorderPainted(false);
        progressBar.setBackground(BORDER_COLOR); // 💡 استخدام لون الحدود كخلفية للشريط
        progressBar.setForeground(new Color(255, 165, 0));
        progressBar.setStringPainted(false);
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
        progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainRowContainer.add(topDetailsPanel);
        mainRowContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        mainRowContainer.add(progressBar);

        mainRowContainer.add(Box.createRigidArea(new Dimension(0, 5)));

        add(mainRowContainer);
    }
}