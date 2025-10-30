package Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadingDemographicPanel extends JPanel {

    private final Map<String, Integer> categories = new LinkedHashMap<>();
    private final Map<String, Integer> totals = new LinkedHashMap<>();
    private final Map<String, Double> currentCompletionPercentage = new LinkedHashMap<>();

    private Color PRIMARY_BG;
    private Color FONT_COLOR;
    private Color SECONDARY_BG;

    private Color[] colors;

    private static final double DONUT_INNER_RATIO = 0.85;
    private static final int ARC_ROUNDNESS = 25;

    // 💡 تم تسريع الحركة إلى 1200 مللي ثانية (1.2 ثانية)
    private static final int DONUT_ANIMATION_DURATION = 1200;
    private static final double GAP_ANGLE = 0.0;
    private Timer donutAnimationTimer;
    private long startTime;
    private float animatedAngle = 0; // الزاوية الكلية للدائرة (للتأكد من ظهور الشرائح بالتسلسل)

    private int donutOffsetX = 0;
    private int donutOffsetY = 0;

    private Timer progressAnimationTimer;
    private Map<RoundedProgressBar, Integer> targetValues = new LinkedHashMap<>();
    private long progressStartTime;
    // 💡 تم تسريع الحركة إلى 1200 مللي ثانية (1.2 ثانية)
    private static final int PROGRESS_ANIMATION_DURATION = 1200;

    // 💡 متغيرات جديدة للرسوم المتحركة المتسلسلة
    private List<String> categoryOrder;
    private int currentCategoryIndex = 0;

    public ReadingDemographicPanel() {
        this.colors = new Color[7];

        updateThemeColors();

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));
        setOpaque(false);

        categories.put("Non-Fiction", 9);
        categories.put("Philosophy", 8);
        categories.put("Fiction", 3);
        categories.put("Academic", 6);
        categories.put("Novel", 6);
        categories.put("Career", 20);
        categories.put("Poetry", 2);

        // 💡 حفظ ترتيب الفئات
        categoryOrder = new ArrayList<>(categories.keySet());

        totals.put("Non-Fiction", 16);
        totals.put("Philosophy", 14);
        totals.put("Fiction", 17);
        totals.put("Academic", 18);
        totals.put("Novel", 13);
        totals.put("Career", 25);
        totals.put("Poetry", 9);

        for(String key : categories.keySet()) {
            currentCompletionPercentage.put(key, 0.0);
        }

        setPreferredSize(new Dimension(800, 300));
        buildUI();
        // سنبقي على مؤقت الدائرة الخارجية (animatedAngle) لتحريك الأقواس المتتابعة
        startDonutAnimation();
        startProgressAnimation(); // بدء الحركة المتسلسلة
    }


    private void updateThemeColors() {
        boolean dark = UIManager.getBoolean("laf.dark");

        if (this.colors == null || this.colors.length != 7) {
            this.colors = new Color[7];
        }

        if (dark) {
            PRIMARY_BG = new Color(50, 55, 72);
            SECONDARY_BG = new Color(150, 150, 150, 100);
            FONT_COLOR = Color.WHITE;

            colors[0] = new Color(41, 121, 255);
            colors[1] = new Color(255, 82, 82);
            colors[2] = new Color(255, 202, 40);
            colors[3] = new Color(102, 187, 106);
            colors[4] = new Color(255, 167, 38);
            colors[5] = new Color(171, 71, 188);
            colors[6] = new Color(0, 188, 212);

        } else {
            PRIMARY_BG = new Color(100, 127, 188);
            SECONDARY_BG = new Color(237, 207, 207, 150);
            FONT_COLOR = Color.WHITE;

            colors[0] = new Color(33, 150, 243);
            colors[1] = new Color(244, 67, 54);
            colors[2] = new Color(255, 235, 59);
            colors[3] = new Color(76, 175, 80);
            colors[4] = new Color(255, 152, 0);
            colors[5] = new Color(156, 39, 176);
            colors[6] = new Color(100, 100, 100);
        }
    }


    private void startDonutAnimation() {
        // 💡 نستخدم وقتاً إجمالياً أطول للدائرة ليتناسب مع مجموع الأشرطة المتسلسلة
        startTime = System.currentTimeMillis();
        if (donutAnimationTimer != null && donutAnimationTimer.isRunning()) {
            donutAnimationTimer.stop();
        }
        animatedAngle = 0; // إعادة ضبط الزاوية للبداية

        donutAnimationTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;

                float totalDuration = categoryOrder.size() * PROGRESS_ANIMATION_DURATION;
                float progress = (float) elapsedTime / totalDuration;

                if (progress >= 1.0f) {
                    progress = 1.0f;
                    donutAnimationTimer.stop();
                }

                animatedAngle = progress * 360.0f;
                repaint();
            }
        });
        donutAnimationTimer.start();
    }

    private void startProgressAnimation() {
        if (progressAnimationTimer != null && progressAnimationTimer.isRunning()) {
            progressAnimationTimer.stop();
        }

        if (currentCategoryIndex >= categoryOrder.size()) {
            // انتهت جميع الفئات
            currentCategoryIndex = 0;
            return;
        }

        String currentCategoryName = categoryOrder.get(currentCategoryIndex);

        // العثور على شريط التقدم للفئة الحالية
        RoundedProgressBar currentBar = null;
        int targetValue = 0;

        for (Map.Entry<RoundedProgressBar, Integer> entry : targetValues.entrySet()) {
            if (currentCategoryName.equals(entry.getKey().getClientProperty("CategoryName"))) {
                currentBar = entry.getKey();
                targetValue = entry.getValue();
                break;
            }
        }

        if (currentBar == null) {
            // إذا لم يتم العثور على الشريط، انتقل إلى الفئة التالية
            currentCategoryIndex++;
            startProgressAnimation();
            return;
        }

        progressStartTime = System.currentTimeMillis();
        final RoundedProgressBar barToAnimate = currentBar;
        final int finalTargetValue = targetValue;

        progressAnimationTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - progressStartTime;
                float progress = (float) elapsedTime / PROGRESS_ANIMATION_DURATION;

                if (progress >= 1.0f) {
                    progress = 1.0f;
                    progressAnimationTimer.stop();

                    // 💡 بعد الانتهاء، انتقل إلى الفئة التالية وابدأ الحركة
                    currentCategoryIndex++;
                    startProgressAnimation();
                }

                int animatedValue = (int) (progress * finalTargetValue);
                barToAnimate.setCurrentAnimatedValue(animatedValue);

                // 💡 تحديث قيمة الدائرة بناءً على القيمة المتحركة للشريط الحالي
                currentCompletionPercentage.put(currentCategoryName, (double) (progress * categories.get(currentCategoryName)));

                if (getParent() != null) {
                    getParent().repaint();
                }
            }
        });
        progressAnimationTimer.start();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        updateThemeColors();

        if (getComponents().length > 0) {
            buildUI();
            revalidate();
            repaint();
            // 💡 إعادة تعيين الفهرس عند تحديث الـ UI
            currentCategoryIndex = 0;
            startDonutAnimation();
            startProgressAnimation();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2.setColor(new Color(0, 0, 0, 40));
        g2.fill(new RoundRectangle2D.Float(5, 5, width - 10, height - 10, ARC_ROUNDNESS, ARC_ROUNDNESS));

        g2.setColor(PRIMARY_BG);
        g2.fill(new RoundRectangle2D.Float(0, 0, width - 5, height - 5, ARC_ROUNDNESS, ARC_ROUNDNESS));

        g2.dispose();
    }

    private void buildUI() {
        removeAll();
        targetValues.clear();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

        JPanel leftPanel = createProgressPanel();
        DonutChartPanel rightPanel = new DonutChartPanel(categories, colors, currentCompletionPercentage);

        gbc.weightx = 0.55;
        gbc.gridx = 0;
        add(leftPanel, gbc);

        gbc.weightx = 0.45;
        gbc.gridx = 1;
        add(rightPanel, gbc);

        // 💡 التأكد من أن جميع الأشرطة تبدأ من الصفر في كل مرة يتم فيها بناء الـ UI
        for (Map.Entry<String, Double> entry : currentCompletionPercentage.entrySet()) {
            entry.setValue(0.0);
        }
    }

    private JPanel createProgressPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(categories.size() + 1, 1, 5, 15));
        panel.setOpaque(false);

        JLabel title = new JLabel("Library Reading Overview");
        title.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        title.setForeground(FONT_COLOR);
        panel.add(title);

        int i = 0;
        for (Map.Entry<String, Integer> entry : categories.entrySet()) {
            String name = entry.getKey();
            int value = entry.getValue();

            int categoryTotal = totals.getOrDefault(name, value);
            Color barColor = colors[i % colors.length];

            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.setOpaque(false);

            JLabel label = new JLabel(name);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(FONT_COLOR);

            double percentage = (double) value / categoryTotal * 100.0;
            RoundedProgressBar bar = new RoundedProgressBar(0, 100, barColor, SECONDARY_BG);
            targetValues.put(bar, (int) Math.round(percentage));

            bar.putClientProperty("CategoryName", name);

            bar.setCurrentAnimatedValue(0);

            bar.setPreferredSize(new Dimension(200, 10));

            JPanel centerPanel = new JPanel(new BorderLayout(10, 0));
            centerPanel.setOpaque(false);
            centerPanel.add(label, BorderLayout.WEST);
            centerPanel.add(bar, BorderLayout.CENTER);

            JLabel valueLabel = new JLabel(String.format("%.1f%%", percentage));
            valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            valueLabel.setForeground(FONT_COLOR);
            centerPanel.add(valueLabel, BorderLayout.EAST);

            row.add(centerPanel, BorderLayout.CENTER);
            panel.add(row);
            i++;
        }

        return panel;
    }

    private static class RoundedProgressBar extends JProgressBar {
        private Color fillColor;
        private final Color backgroundColor;
        private int currentAnimatedValue = 0;

        public RoundedProgressBar(int min, int max, Color fillColor, Color backgroundColor) {
            super(min, max);
            this.fillColor = fillColor;
            this.backgroundColor = backgroundColor;
            setOpaque(false);
            setBorderPainted(false);

            setMinimum(min);
            setMaximum(max);
        }

        public void setCurrentAnimatedValue(int value) {
            this.currentAnimatedValue = value;
            repaint();
        }

        @Override
        public double getPercentComplete() {
            double min = getMinimum();
            double max = getMaximum();

            if (max <= min) return 0.0;

            return (currentAnimatedValue - min) / (max - min);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int arc = height;

            g2.setColor(backgroundColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, width, height, arc, arc));

            int filledWidth = (int) (width * getPercentComplete());
            g2.setColor(fillColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, filledWidth, height, arc, arc));

            g2.dispose();
        }
    }

    private class DonutChartPanel extends JPanel {
        private final Map<String, Integer> data;
        private final Color[] colors;
        private final Map<String, Double> currentAnimatedValues; // 💡 يمثل عدد الكتب المكتملة المتحرك

        // 💡 تعريف سماكة الخط وزاوية الاستدارة
        private static final float STROKE_WIDTH = 25f; // سماكة قوس الدونات
        private static final float ARC_SPACING = 3.0f; // فاصل بسيط بين الشرائح (اختياري)

        DonutChartPanel(Map<String, Integer> data, Color[] colors, Map<String, Double> currentAnimatedValues) {
            this.data = data;
            this.colors = colors;
            this.currentAnimatedValues = currentAnimatedValues;
            setOpaque(false);
            setPreferredSize(new Dimension(400, 400));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // 💡 إجمالي الكتب المكتملة فقط
            int totalCompletedValue = data.values().stream().mapToInt(Integer::intValue).sum();

            int size = Math.min(getWidth(), getHeight()) - 20;
            // 💡 لتطبيق Stroke، يجب أن نجعل الدائرة أصغر ونحرك المركز
            int donutRadius = (int) (size - STROKE_WIDTH);
            int centerX = (getWidth() - donutRadius) / 2 + donutOffsetX;
            int centerY = (getHeight() - donutRadius) / 2 + donutOffsetY;

            double currentStartingAngle = 90;
            int i = 0;

            // 💡 إعداد Stroke للزوايا المستديرة
            Stroke roundStroke = new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

            // 💡 نحتاج لـ Stroke آخر لرسم الخلفية الكاملة للدائرة
            Stroke thinStroke = new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

            // 1. رسم خلفية الدائرة (دائرة رمادية كاملة بسماكة الخط)
            g2.setStroke(thinStroke); // نستخدم الـ Stroke الحاد هنا
            g2.setColor(SECONDARY_BG);
            g2.drawOval(centerX, centerY, donutRadius, donutRadius);


            // 2. رسم الأقواس الملونة (المكتملة)
            g2.setStroke(roundStroke); // نعود للـ Stroke المستدير

            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                String categoryName = entry.getKey();
                int categoryValue = entry.getValue();

                // القيمة المتحركة لعدد الكتب المكتملة
                double animatedCompletedCount = currentAnimatedValues.getOrDefault(categoryName, 0.0);

                // 💡 حساب وزن الفئة من الإجمالي الكلي للمكتمل
                double categoryWeight = (double) categoryValue / totalCompletedValue;

                // 💡 الزاوية الكلية التي تشغلها الشريحة لو كانت مكتملة
                double fullCategoryArcAngle = categoryWeight * 360.0;

                // الزاوية الفعلية التي سيتم رسمها (بناءً على القيمة المتحركة / القيمة النهائية)
                double drawArcAngle = (animatedCompletedCount / categoryValue) * fullCategoryArcAngle;

                // 💡 تقليل الزاوية قليلاً لترك فاصل بسيط بين الأقواس (لإظهار الزوايا المستديرة بشكل أفضل)
                if (drawArcAngle > ARC_SPACING) {
                    drawArcAngle -= ARC_SPACING;
                }

                if (drawArcAngle < 0) drawArcAngle = 0;

                // 💡 يتم رسم القوس الملون
                g2.setColor(colors[i % colors.length]);

                // 💡 استخدام drawArc لرسم خط مستدير (Arc with Round Caps)
                g2.drawArc(centerX, centerY, donutRadius, donutRadius, (int) currentStartingAngle, (int) drawArcAngle);

                currentStartingAngle += fullCategoryArcAngle; // الزاوية التي تبدأ منها الشريحة التالية
                i++;
            }

            // 3. رسم الفراغ الداخلي (المركز)
            int innerRadius = donutRadius - (int) STROKE_WIDTH;
            int innerX = centerX + (donutRadius - innerRadius) / 2;
            int innerY = centerY + (donutRadius - innerRadius) / 2;

            g2.setColor(PRIMARY_BG);
            g2.fillOval(innerX, innerY, innerRadius, innerRadius);

            // 4. رسم النص في الوسط
            g2.setColor(FONT_COLOR);
            g2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
            String text = "Books"; // 💡 تغيير النص ليعكس المكتمل
            FontMetrics fm = g2.getFontMetrics();
            int textX = innerX + (innerRadius - fm.stringWidth(text)) / 2;
            int textY = innerY + innerRadius / 2 - 10;
            g2.drawString(text, textX, textY);

            g2.setFont(new Font("Segoe UI", Font.BOLD, 32));
            String totalStr = String.valueOf(totalCompletedValue); // 💡 عرض الإجمالي المكتمل
            fm = g2.getFontMetrics();
            int totalX = innerX + (innerRadius - fm.stringWidth(totalStr)) / 2;
            int totalY = innerY + innerRadius / 2 + 30;
            g2.drawString(totalStr, totalX, totalY);

            g2.dispose();
        }
    }


    public void setDonutOffset(int offsetX, int offsetY) { this.donutOffsetX = offsetX; this.donutOffsetY = offsetY; }

    public void setCategoryValue(String category, int value) {
        if (categories.containsKey(category)) {
            categories.put(category, value);

            buildUI();
            revalidate();
            repaint();
            currentCategoryIndex = 0; // إعادة البدء من الأول
            startDonutAnimation();
            startProgressAnimation();
        }
    }

    public void setPanelSize(Dimension d) { this.setPreferredSize(d); this.revalidate(); }
}