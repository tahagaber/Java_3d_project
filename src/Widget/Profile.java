package Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Profile Widget كامل في ملف واحد
 * - الصورة الشخصية على اليمين
 * - Notification + اللغة على الشمال
 * - بوردير دائري قابل للتخصيص
 * - تحكم كامل في padding ولون البوردير
 */
public class Profile extends JPanel {

    private JLabel profilePicLabel;
    private JLabel notificationLabel;
    private JLabel languageLabel;

    private Color borderColor = new Color(255, 255, 255, 100);
    private int cornerRadius = 15;

    // Padding داخلي
    private int paddingTop = 5;
    private int paddingLeft = 10;
    private int paddingBottom = 5;
    private int paddingRight = 10;

    public Profile() {
        this("/raven/icon/png/Taha (3).jpg",
                "/raven/icon/png/search (2).png",
                "EN");
    }

    public Profile(String profilePicPath, String notificationIconPath, String languageCode) {
        initWidget(profilePicPath, notificationIconPath, languageCode);
    }

    private void initWidget(String profilePicPath, String notificationIconPath, String languageCode) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 60));

        // ===== الصورة الشخصية على اليمين =====
        profilePicLabel = new JLabel(loadRoundedIcon(profilePicPath, 20)); // حجم أصغر
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(profilePicLabel);
        add(rightPanel, BorderLayout.EAST);

        // ===== Notification + اللغة على الشمال =====
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);

        notificationLabel = new JLabel(loadIcon(notificationIconPath, 10, 10));
        leftPanel.add(notificationLabel);

        languageLabel = new JLabel(languageCode);
        languageLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        languageLabel.setForeground(Color.WHITE);
        languageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        leftPanel.add(languageLabel);

        add(leftPanel, BorderLayout.WEST);

        updateBorder();
    }

    // ===== التحكم في البوردير الداخلي =====
    private void updateBorder() {
        setBorder(BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight));
        revalidate();
        repaint();
    }

    public void setPadding(int top, int left, int bottom, int right) {
        this.paddingTop = top;
        this.paddingLeft = left;
        this.paddingBottom = bottom;
        this.paddingRight = right;
        updateBorder();
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        languageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(4, 3, 2, 4)
        ));
        repaint();
    }

    /** Setters لتغيير المحتوى */
    public void setProfilePic(String path) {
        profilePicLabel.setIcon(loadRoundedIcon(path, 30));
    }

    public void setNotificationIcon(String path) {
        notificationLabel.setIcon(loadIcon(path, 24, 24));
    }

    public void setLanguage(String lang) {
        languageLabel.setText(lang);
    }

    /** رسم البوردير الدائري */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        g2.dispose();
        super.paintComponent(g);
    }

    /** تحميل أيقونة PNG */
    private Icon loadIcon(String resourcePath, int w, int h) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) return null;
            Image original = new ImageIcon(url).getImage();
            BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(original, 0, 0, w, h, null);
            g2.dispose();
            return new ImageIcon(resized);
        } catch (Exception e) {
            return null;
        }
    }

    /** تحميل صورة شخصية مدورة */
    private Icon loadRoundedIcon(String resourcePath, int size) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) return null;
            Image original = new ImageIcon(url).getImage();
            BufferedImage masked = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = masked.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, size, size));
            g2.drawImage(original, 0, 0, size, size, null);
            g2.dispose();
            return new ImageIcon(masked);
        } catch (Exception e) {
            return null;
        }
    }
}
