package Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class KeepReadingWidget extends JPanel {

    private final Color PRIMARY_BG = UIManager.getBoolean("laf.dark") ? new Color(50, 55, 72) : Color.WHITE;
    private final Color FONT_COLOR = UIManager.getBoolean("laf.dark") ? Color.WHITE : new Color(30, 30, 35);
    private final int ARC = 15; // Border Radius للكارد

    public KeepReadingWidget() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBackground(PRIMARY_BG);

        // حجم مبدئي (سيتم تمديده بواسطة GridBagLayout في الصفحة الرئيسية)
        setPreferredSize(new Dimension(450, 480));
        setMinimumSize(new Dimension(300, 480));

        // بناء المحتوى الداخلي للكارد
        buildUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // رسم خلفية الكارد بحواف دائرية
        g2.setColor(PRIMARY_BG);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC, ARC));

        g2.dispose();
        super.paintComponent(g); // لضمان رسم المكونات الأب
    }

    @Override
    public boolean isOpaque() {
        return false; // نستخدم paintComponent للرسم
    }

    private void buildUI() {
        // 1. العنوان "Keep Reading" و "See All"
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Keep Reading");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(FONT_COLOR);
        header.add(title, BorderLayout.WEST);

        JLabel seeAll = new JLabel("See All");
        seeAll.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seeAll.setForeground(new Color(255, 165, 0)); // لون برتقالي لرابط
        header.add(seeAll, BorderLayout.EAST);

        add(header);

        // 2. إضافة صفوف الكتب (3 صفوف كأمثلة)
        addBookRow("Inter Beauty", "Anna Leary", new Color(220, 100, 100), 80, 210);
        addBookRow("Beauty of Joles", "Anna Leary", new Color(255, 190, 80), 80, 210);
        addBookRow("Welcome to Jungle", "Anna Leary", new Color(80, 100, 220), 80, 210);

        // مساحة فارغة في الأسفل
        add(Box.createVerticalGlue());
    }

    private void addBookRow(String title, String author, Color bookColor, int value, int total) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 1. صورة الغلاف (دائرة بسيطة بدلاً من الصورة)
        JPanel coverPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // رسم مستطيل بزوايا دائرية
                g2.setColor(bookColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                // رسم دائرة كنص وهمي داخل الكتاب
                g2.setColor(Color.WHITE);
                g2.fillOval(getWidth()/4, getHeight()/3, getWidth()/2, getHeight()/2);

                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(50, 70); // حجم غلاف الكتاب
            }
        };
        coverPanel.setOpaque(false);
        row.add(coverPanel, BorderLayout.WEST);

        // 2. تفاصيل الكتاب وشريط التقدم
        JPanel detailPanel = new JPanel();
        detailPanel.setOpaque(false);
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        titleLabel.setForeground(FONT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel authorLabel = new JLabel(author);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        authorLabel.setForeground(FONT_COLOR.darker().darker()); // لون باهت قليلاً
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // شريط التقدم
        JProgressBar progressBar = new JProgressBar(0, total);
        progressBar.setValue(value);
        progressBar.setPreferredSize(new Dimension(200, 5));
        progressBar.setOpaque(false);
        progressBar.setBorderPainted(false);
        progressBar.setBackground(new Color(200, 200, 200, 100)); // لون خلفية الشريط
        progressBar.setForeground(new Color(255, 165, 0)); // لون التقدم
        progressBar.setStringPainted(false);
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5)); // يمتد أفقياً
        progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);

        // إضافة المكونات
        detailPanel.add(titleLabel);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        detailPanel.add(authorLabel);
        detailPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        detailPanel.add(progressBar);

        row.add(detailPanel, BorderLayout.CENTER);

        // 3. النسبة المئوية في اليمين
        JLabel percentLabel = new JLabel(value + "/" + total);
        percentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        percentLabel.setForeground(FONT_COLOR.darker().darker());
        row.add(percentLabel, BorderLayout.EAST);

        add(row);
    }
}