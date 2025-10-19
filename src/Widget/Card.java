package Widget;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Card extends JPanel {

    private JLabel titleLabel;
    private JLabel valueLabel;
    private JLabel percentLabel;

    private Color color1 = new Color(33, 150, 243);
    private Color color2 = new Color(156, 39, 176);

    public Card() {
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);
        setLayout(new java.awt.GridLayout(3, 1));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Title");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));

        valueLabel = new JLabel("0");
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 22));

        percentLabel = new JLabel("+0%");
        percentLabel.setForeground(Color.WHITE);
        percentLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        add(titleLabel);
        add(valueLabel);
        add(percentLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        super.paintComponent(g);
    }

    // === Setters ===
    public void setTitle(String text) {
        titleLabel.setText(text);
    }

    public void setValue(String text) {
        valueLabel.setText(text);
    }

    public void setPercentage(String text) {
        percentLabel.setText(text);
    }

    public void setGradient(Color c1, Color c2) {
        this.color1 = c1;
        this.color2 = c2;
        repaint();
    }
}
