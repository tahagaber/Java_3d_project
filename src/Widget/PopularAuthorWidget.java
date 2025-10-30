package Widget;

import javax.swing.*;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.FlowLayout;

public class PopularAuthorWidget extends JPanel {

    // المتغيرات التي سيتم تعيينها بواسطة updateThemeColors()
    private Color CARD_BG;
    private Color FONT_COLOR;
    private Color SECONDARY_FONT_COLOR;
    private Color BORDER_COLOR;

    // اللون البرتقالي الثابت لـ "See All" والأزرار
    private final Color ACCENT_COLOR = new Color(255, 165, 0);

    private final int ARC = 15;
    private final int AVATAR_SIZE = 25;

    private final Object[][] data = {
            {"Tasneem", "Software"},
            {"Taha", "Flutter"},
            {"Ibrahim", "Software"},
            {"Menna", "Software"},
            {"Nouren", "Software"},
            {"Muhammad", "Software"}
    };

    // 💡 حاوية المكونات لصفوف البيانات
    private JPanel dataContainer;

    public PopularAuthorWidget() {
        // 💡 تم إضافة استدعاء الدالة المفقودة
        updateThemeColors();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBackground(CARD_BG);

        // تم تعيين الحجم المفضل هنا ليتوافق مع التعديلات في FormDashboard.java
        // تم تعديل الارتفاع ليكون 300 ليتناسب مع لوحات الداشبورد
        setPreferredSize(new Dimension(400, 300));

        // 💡 تطبيق شكل الحدود المستديرة عبر FlatClientProperties
        putClientProperty(FlatClientProperties.STYLE,
                "arc:" + ARC + "; border:line(1," + toHex(BORDER_COLOR) + ")");

        // 💡 استدعاء الدالة المفقودة لبناء الواجهة
        buildUI();
    }

    // ===============================================
    // 💡 الدالة المفقودة: تحديث ألوان الثيم
    // ===============================================
    private void updateThemeColors() {
        boolean dark = UIManager.getBoolean("laf.dark");

        if (dark) {
            // الوضع الداكن
            CARD_BG = new Color(50, 55, 72);
            FONT_COLOR = Color.WHITE;
            SECONDARY_FONT_COLOR = new Color(150, 150, 150);
            BORDER_COLOR = new Color(70, 70, 70);

        } else {
            // الوضع الفاتح
            CARD_BG = new Color(100, 127, 188);
            FONT_COLOR = Color.WHITE;
            SECONDARY_FONT_COLOR = new Color(200, 200, 200);
            BORDER_COLOR = new Color(120, 150, 220);
        }
    }

    // ===============================================
    // 💡 دالة مساعدة لتحويل اللون إلى Hex String
    // ===============================================
    private String toHex(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        while (hex.length() < 6) {
            hex = "0" + hex;
        }
        return "#" + hex;
    }

    // ===============================================
    // 💡 إصلاح دالة updateUI: وتحديث الحدود
    // ===============================================
    @Override
    public void updateUI() {
        super.updateUI();
        updateThemeColors();

        if (CARD_BG != null) {
            setBackground(CARD_BG);
        }

        // 💡 تحديث لون الحدود بالصيغة الصحيحة
        putClientProperty(FlatClientProperties.STYLE,
                "arc:" + ARC + "; border:line(1," + toHex(BORDER_COLOR) + ")");

        Component[] components = getComponents();
        for (Component c : components) {
            if (c instanceof JComponent) {
                ((JComponent) c).updateUI();
            }
        }

        revalidate();
        repaint();
    }

    // ===============================================
    // 💡 إضافة paintComponent لرسم الخلفية المستديرة
    // ===============================================
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // خلفية الكارد
        g2.setColor(CARD_BG);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC, ARC));

        g2.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    // ===============================================
    // 💡 الدالة المفقودة: بناء الواجهة
    // ===============================================
    private void buildUI() {
        // 1. العنوان "Popular Author" و "See All"
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Popular Author");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        title.setForeground(FONT_COLOR);
        header.add(title, BorderLayout.WEST);

        JLabel seeAll = new JLabel("See All");
        seeAll.putClientProperty(FlatClientProperties.STYLE, "font:plain +1");
        seeAll.setForeground(ACCENT_COLOR);
        header.add(seeAll, BorderLayout.EAST);

        add(header);

        // 2. رؤوس الأعمدة
        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setOpaque(false);
        tableHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 15));

        JPanel headerLabels = new JPanel(new GridBagLayout());
        headerLabels.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.gridx = 0;
        gbc.weightx = 0.45;
        headerLabels.add(createHeaderLabel("Name", BorderLayout.WEST), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.30;
        headerLabels.add(createHeaderLabel("Type", BorderLayout.CENTER), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.25;
        headerLabels.add(createHeaderLabel("Details", BorderLayout.EAST), gbc);

        tableHeader.add(headerLabels, BorderLayout.CENTER);
        add(tableHeader);

        // 3. صفوف البيانات - إنشاء لوحة احتواء البيانات
        dataContainer = new JPanel();
        dataContainer.setLayout(new BoxLayout(dataContainer, BoxLayout.Y_AXIS));
        dataContainer.setOpaque(false);

        for (Object[] rowData : data) {
            addAuthorRow((String) rowData[0], (String) rowData[1]);
        }

        add(dataContainer);
        add(Box.createVerticalGlue());
    }

    // 💡 دالة مساعدة لإنشاء عناوين الأعمدة
    private JLabel createHeaderLabel(String text, String alignment) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(SECONDARY_FONT_COLOR);
        return label;
    }

    // 💡 دالة مساعدة لإضافة صف مؤلف
    private void addAuthorRow(String name, String type) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JPanel dataPanel = new JPanel(new GridBagLayout());
        dataPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. الاسم والصورة (العمود 0)
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        namePanel.setOpaque(false);

        // الصورة (Avatar)
        JPanel avatar = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(AVATAR_SIZE, AVATAR_SIZE);
            }
            @Override
            protected void paintComponent(Graphics g) {
                // استخدام لون الـ Button.background الافتراضي لـ FlatLaf (يتغير مع الثيم)
                g.setColor(UIManager.getColor("Button.background"));
                g.fillOval(0, 0, AVATAR_SIZE, AVATAR_SIZE);
                super.paintComponent(g);
            }
        };
        avatar.setOpaque(false);
        namePanel.add(avatar);

        JLabel nameLabel = new JLabel(name);
        nameLabel.putClientProperty(FlatClientProperties.STYLE, "font:plain +1");
        nameLabel.setForeground(FONT_COLOR);
        namePanel.add(nameLabel);

        gbc.gridx = 0;
        gbc.weightx = 0.45;
        gbc.fill = GridBagConstraints.BOTH;
        dataPanel.add(namePanel, gbc);

        // 2. النوع (العمود 1)
        JLabel typeLabel = new JLabel(type);
        typeLabel.putClientProperty(FlatClientProperties.STYLE, "font:plain +1");
        typeLabel.setForeground(FONT_COLOR);

        gbc.gridx = 1;
        gbc.weightx = 0.30;
        gbc.fill = GridBagConstraints.BOTH;
        dataPanel.add(typeLabel, gbc);

        // 3. زر التفاصيل (العمود 2)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);

        JButton detailButton = new JButton("View Details");
        detailButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailButton.setForeground(Color.WHITE);
        detailButton.setBackground(ACCENT_COLOR);
        detailButton.setFocusPainted(false);
        detailButton.setBorderPainted(false);
        detailButton.setPreferredSize(new Dimension(85, 28));
        detailButton.putClientProperty(FlatClientProperties.STYLE, "arc:10;");

        buttonPanel.add(detailButton);

        gbc.gridx = 2;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        dataPanel.add(buttonPanel, gbc);

        row.add(dataPanel, BorderLayout.CENTER);
        dataContainer.add(row);
    }
}