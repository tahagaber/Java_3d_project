package Widget;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Chrats extends JPanel {

    private String title;
    private List<Integer> values;
    private Color barColor = new Color(82, 106, 255);
    private int arc = 15;
    private int padding = 20;

    public Chrats(String title, List<Integer> values) {
        this.title = title;
        this.values = values;

        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildChartPanel(), BorderLayout.CENTER);
    }

    private JComponent buildHeader() {
        JLabel label = new JLabel(title);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16f));
        label.setForeground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(label, BorderLayout.WEST);

        return header;
    }

    private JComponent buildChartPanel() {
        // Panel داخلي يحتوي على ChartCanvas للسحب لو البيانات طويلة
        JPanel chartWrapper = new JPanel(new BorderLayout());
        chartWrapper.setOpaque(false);

        ChartCanvas canvas = new ChartCanvas();
        canvas.setOpaque(false);

        // ScrollPane لو الأعمدة كبيرة
        JScrollPane scrollPane = new JScrollPane(canvas,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        chartWrapper.add(scrollPane, BorderLayout.CENTER);
        return chartWrapper;
    }

    public void setBarColor(Color color) {
        this.barColor = color;
        repaint();
    }

    private class ChartCanvas extends JPanel {

        public ChartCanvas() {
            setOpaque(false);
            setPreferredSize(new Dimension(450, 150)); // default size
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            if (values == null || values.isEmpty()) return;

            int barWidth = Math.max(10, (w - 2 * padding) / values.size());
            int max = values.stream().max(Integer::compareTo).orElse(1);

            int x = padding;

            for (int v : values) {
                int barHeight = (int) ((double) v / max * (h - 2 * padding));

                g2.setColor(barColor);
                g2.fillRoundRect(x, h - barHeight - padding, barWidth, barHeight, 6, 6);

                x += barWidth + 10;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            int width = Math.max(450, values.size() * 30 + 2 * padding);
            int height = 150;
            return new Dimension(width, height);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(45, 48, 62)); // background
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
    }
}
