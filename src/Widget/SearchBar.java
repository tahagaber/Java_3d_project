package raven.application.widget;

import javax.swing.*;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * مكون SearchBar قابل لإعادة الاستخدام
 * استخدام:
 *    SearchBar sb = new SearchBar();
 *    myPanel.add(sb, BorderLayout.NORTH);
 */
public class SearchBar extends JPanel {

    private JTextField searchField;
    private JLabel searchIconLabel;
    private JLabel endIconLabel; // أيقونة على الطرف الأيمن

    public SearchBar() {
        this("Search...");
    }

    public SearchBar(String placeholder) {
        initSearchBar(placeholder);
    }

    private void initSearchBar(String placeholder) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(340, 48));

        // ====== background panel ======
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color bg = (getParent() != null) ? getParent().getBackground() : Color.WHITE;
                    g2.setColor(bg);
                    int arc = 20;
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                    g2.setColor(new Color(200, 200, 200));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
                } finally {
                    g2.dispose();
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout(8, 0));
        backgroundPanel.setOpaque(true);
        backgroundPanel.setPreferredSize(new Dimension(320, 40));

        // ====== أيقونة البحث (يسار) ======
        searchIconLabel = new JLabel(loadIcon("/raven/icon/png/search (2).png", 18, 18));
        searchIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel iconWrapper = new JPanel(new BorderLayout());
        iconWrapper.setOpaque(false);
        iconWrapper.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 8));
        iconWrapper.add(searchIconLabel, BorderLayout.CENTER);

        // ====== حقل النص ======
        searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, placeholder);
        searchField.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setOpaque(false);
        searchField.setCaretColor(Color.DARK_GRAY);
        searchField.setForeground(new Color(60, 60, 65));

        // ====== أيقونة النهاية (مثل More) ======
        endIconLabel = new JLabel(loadIcon("/raven/icon/png/more-horizontal-rectangle-svgrepo-com.png", 20, 20));
        endIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endIconLabel.setOpaque(false);
        JPanel endIconWrapper = new JPanel(new BorderLayout());
        endIconWrapper.setOpaque(false);
        endIconWrapper.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        endIconWrapper.add(endIconLabel, BorderLayout.CENTER);

        // ====== تجميع ======
        backgroundPanel.add(iconWrapper, BorderLayout.WEST);
        backgroundPanel.add(searchField, BorderLayout.CENTER);
        backgroundPanel.add(endIconWrapper, BorderLayout.EAST);

        add(backgroundPanel, BorderLayout.CENTER);
    }

    /**
     * تحميل أيقونة PNG مع تحسين الجودة عند إعادة القياس
     */
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

    /** إرجاع النص الموجود في السيرش */
    public String getSearchText() {
        return searchField.getText();
    }

    /** تعيين نص داخل السيرش */
    public void setSearchText(String text) {
        searchField.setText(text);
    }

    /** الوصول لحقل النص لو حبيت تضيف Listener */
    public JTextField getSearchField() {
        return searchField;
    }

    /** تغيير أيقونة النهاية */
    public void setEndIcon(String resourcePath, int w, int h) {
        Icon ic = loadIcon(resourcePath, w, h);
        endIconLabel.setIcon(ic);
    }

    /** إظهار أو إخفاء أيقونة النهاية */
    public void showEndIcon(boolean show) {
        endIconLabel.setVisible(show);
    }
}
