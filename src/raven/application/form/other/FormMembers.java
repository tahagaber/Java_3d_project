package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * FormMembers - صفحة عرض أعضاء الفريق بتصميم ملعب كرة قدم
 *
 * الصفحة بتعرض:
 * 1. المدير الفني (Head Coach) في panel علوي
 * 2. ملعب كرة قدم مع اللاعيبة في تشكيل 1-2-2
 * 3. تفاصيل كل لاعب عند الضغط عليه
 *
 * @author Taha Gaber
 */
public class FormMembers extends JPanel {

    /**
     * Constructor - بيعمل initialize للصفحة
     */
    public FormMembers() {
        init();
    }

    /**
     * تهيئة الصفحة الرئيسية
     * بتضيف panel المدير الفني و panel الملعب
     */
    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[grow]", "[][grow]"));

        // قسم المدير الفني
        JPanel coachPanel = createCoachPanel();
        add(coachPanel, "wrap,growx,height 120!");

        // قسم ملعب الكرة
        JPanel fieldPanel = createFootballField();
        add(fieldPanel, "grow");
    }

    /**
     * إنشاء panel المدير الفني
     * بيعرض صورة المدير مع بياناته وإحصائيات الفريق
     *
     * @return JPanel يحتوي على معلومات المدير الفني
     */
    private JPanel createCoachPanel() {
        JPanel panel = new JPanel(new MigLayout("fill,insets 20", "[100!]15[]push[]", "[]"));

        // تصميم الـ panel
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                        "background:darken(@background,3%)");

        // صورة المدير الفني
        JPanel coachPhoto = createCoachPhoto("src/raven/icon/png/Taha (3).jpg");
        panel.add(coachPhoto);

        // معلومات المدير الفني
        JPanel infoPanel = new JPanel(new MigLayout("fill,insets 0", "[]", "[]5[]5[]"));
        infoPanel.setOpaque(false);

        JLabel lblRole = new JLabel("HEAD COACH");
        lblRole.putClientProperty(FlatClientProperties.STYLE,
                "font:bold;" +
                        "foreground:$Component.accentColor");

        JLabel lblName = new JLabel("Taha Gaber");
        lblName.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +4");

        JLabel lblDesc = new JLabel("Technical Director & Project Manager");
        lblDesc.putClientProperty(FlatClientProperties.STYLE,
                "foreground:lighten(@foreground,30%)");

        infoPanel.add(lblRole, "wrap");
        infoPanel.add(lblName, "wrap");
        infoPanel.add(lblDesc);

        panel.add(infoPanel);

        // الإحصائيات (عدد اللاعيبة، الصفحات، نسبة النجاح)
        JPanel statsPanel = new JPanel(new MigLayout("fill,insets 10", "[]20[]20[]", "[]"));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatLabel("5", "Players"));
        statsPanel.add(createStatLabel("15", "Pages"));
        statsPanel.add(createStatLabel("100%", "Success"));

        panel.add(statsPanel);

        // إضافة Mouse Listener للضغط على المدير
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCoachDetails();
            }
        });

        return panel;
    }

    /**
     * إنشاء صورة المدير الفني
     * الصورة بتكون مربعة مع حواف دائرية
     *
     * @param imagePath مسار ملف الصورة
     * @return JPanel يحتوي على الصورة
     */
    private JPanel createCoachPhoto(String imagePath) {
        JPanel photo = new JPanel() {
            private BufferedImage img;

            {
                try {
                    img = ImageIO.read(new File(imagePath));
                } catch (Exception e) {
                    img = null;
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int size = 100;
                int x = 1;
                int y = -45 ;

                // رسم البورد حول الصورة
                g2.setColor(new Color(100, 100, 100));
                g2.fillRoundRect(x-3, y-3, size+6, size+6, 15, 15);

                if (img != null) {
                    // رسم الصورة مع Clipping دائري
                    g2.setClip(new java.awt.geom.RoundRectangle2D.Double(x, y, size, size, 12, 12));
                    g2.drawImage(img, x, y, size, size, null);
                } else {
                    // عرض placeholder لو الصورة مش موجودة
                    g2.setColor(new Color(80, 80, 80));
                    g2.fillRoundRect(x, y, size, size, 12, 12);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 30));
                    g2.drawString("?", x + 28, y + 52);
                }

                g2.dispose();
            }
        };

        photo.setOpaque(false);
        return photo;
    }

    /**
     * إنشاء label للإحصائيات
     * بيعرض رقم في الأعلى و label تحتيه
     *
     * @param value القيمة (الرقم)
     * @param label التسمية
     * @return JPanel يحتوي على الإحصائية
     */
    private JPanel createStatLabel(String value, String label) {
        JPanel panel = new JPanel(new MigLayout("fill,insets 0", "[center]", "[][]"));
        panel.setOpaque(false);

        JLabel lblValue = new JLabel(value);
        lblValue.putClientProperty(FlatClientProperties.STYLE,
                "font:bold +3;" +
                        "foreground:$Component.accentColor");

        JLabel lblLabel = new JLabel(label);
        lblLabel.putClientProperty(FlatClientProperties.STYLE,
                "font:-1;" +
                        "foreground:lighten(@foreground,30%)");

        panel.add(lblValue, "wrap");
        panel.add(lblLabel);

        return panel;
    }

    /**
     * إنشاء ملعب كرة القدم
     * بيرسم الملعب مع كل الخطوط والدوائر
     * ويضيف اللاعيبة في تشكيل 1-2-2
     *
     * @return JPanel يمثل الملعب مع اللاعيبة
     */
    private JPanel createFootballField() {
        JPanel field = new JPanel(new MigLayout("fill,insets 25",
                "[center][center][center][center][center]",
                "[]30[]30[]30[]")) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // خلفية الملعب (تدرج أخضر)
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(18, 95, 28),
                        0, getHeight(), new Color(12, 75, 20)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // خطوط العشب (stripes)
                g2.setColor(new Color(0, 0, 0, 12));
                for (int i = 0; i < getHeight(); i += 40) {
                    g2.fillRect(0, i, getWidth(), 20);
                }

                // خطوط الملعب
                g2.setColor(new Color(255, 255, 255, 100));
                g2.setStroke(new BasicStroke(2f));

                // الحدود الخارجية
                g2.drawRoundRect(10, 10, getWidth()-20, getHeight()-20, 12, 12);

                // خط المنتصف
                int centerY = getHeight()/2;
                g2.drawLine(10, centerY, getWidth()-10, centerY);

                // دائرة المنتصف
                int circleSize = 70;
                g2.drawOval(getWidth()/2 - circleSize/2,
                        centerY - circleSize/2, circleSize, circleSize);
                g2.fillOval(getWidth()/2 - 3, centerY - 3, 6, 6);

                // منطقة الجزاء العلوية
                int penW = 180, penH = 55;
                g2.drawRect(getWidth()/2 - penW/2, 10, penW, penH);
                g2.drawRect(getWidth()/2 - 100/2, 10, 100, 28);

                // منطقة الجزاء السفلية
                g2.drawRect(getWidth()/2 - penW/2,
                        getHeight() - 10 - penH, penW, penH);
                g2.drawRect(getWidth()/2 - 100/2,
                        getHeight() - 10 - 28, 100, 28);

                // نقاط الجزاء
                g2.fillOval(getWidth()/2 - 3, 48, 6, 6);
                g2.fillOval(getWidth()/2 - 3, getHeight() - 48, 6, 6);

                // أقواس الأركان
                g2.drawArc(2, 2, 30, 30, 0, 90);
                g2.drawArc(getWidth()-32, 2, 30, 30, 90, 90);
                g2.drawArc(2, getHeight()-32, 30, 30, 270, 90);
                g2.drawArc(getWidth()-32, getHeight()-32, 30, 30, 180, 90);

                g2.dispose();
            }
        };

        field.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        // عنوان الملعب
        JLabel title = new JLabel("DEV STADIUM - MATCH DAY");
        title.setForeground(new Color(255, 255, 255, 180));
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +6");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        field.add(title, "cell 0 0 5 1,growx");

        // التشكيل
        JLabel formation = new JLabel("Formation: 1-2-2");
        formation.setForeground(new Color(255, 255, 255, 130));
        formation.putClientProperty(FlatClientProperties.STYLE, "font:italic -1");
        formation.setHorizontalAlignment(SwingConstants.CENTER);
        field.add(formation, "cell 0 1 5 1,growx");

        // حارس المرمى (الكابتن) - رقم 1
        field.add(createPlayerCard("Tasneem", "GK", "1",
                        "src/main/resources/images/ahmed.jpg", true,
                        new String[]{"Dashboard", "Authentication", "Settings"}),
                "cell 2 2");

        // المدافعين - أرقام 4 و 5
        field.add(createPlayerCard("Ibrahim", "DF", "4",
                        "src/main/resources/images/omar.jpg", false,
                        new String[]{"API Development", "Database", "Reports"}),
                "cell 1 3");

        field.add(createPlayerCard("Menna", "DF", "5",
                        "src/main/resources/images/mahmoud.jpg", false,
                        new String[]{"UI Components", "Interface", "Members"}),
                "cell 3 3");

        // المهاجمين - أرقام 9 و 10
        field.add(createPlayerCard("Noureen", "FW", "9",
                        "src/main/resources/images/youssef.jpg", false,
                        new String[]{"Mobile App", "Notifications", "Analytics"}),
                "cell 1 4");

        field.add(createPlayerCard("Muhammad Hamdy", "FW", "10",
                        "src/main/resources/images/karim.jpg", false,
                        new String[]{"Deployment", "CI/CD", "Monitoring"}),
                "cell 3 4");

        return field;
    }

    /**
     * إنشاء كارد اللاعب
     * بيعرض صورة اللاعب + اسمه + مركزه
     *
     * @param name اسم اللاعب
     * @param pos المركز (GK, DF, FW)
     * @param num رقم القميص
     * @param imgPath مسار صورة اللاعب
     * @param captain هل هو الكابتن ولا لأ
     * @param pages الصفحات اللي مسؤول عنها
     * @return JPanel يمثل كارد اللاعب
     */
    private JPanel createPlayerCard(String name, String pos, String num,
                                    String imgPath, boolean captain, String[] pages) {

        JPanel card = new JPanel(new MigLayout("fill,insets 0", "[center]", "[][][]"));
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // صورة اللاعب
        JPanel photo = createPlayerPhoto(imgPath, num, captain);
        card.add(photo, "wrap,width 80!,height 80!");

        // اسم اللاعب
        JLabel lblName = new JLabel("<html><center>" + name + "</center></html>");
        lblName.setForeground(Color.WHITE);
        lblName.putClientProperty(FlatClientProperties.STYLE, "font:bold -2");
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblName, "wrap,width 110!,gaptop 5");

        // مركز اللاعب
        JLabel lblPos = new JLabel(pos);
        lblPos.setForeground(Color.WHITE);
        lblPos.setOpaque(true);
        lblPos.setBackground(new Color(0, 0, 0, 160));
        lblPos.putClientProperty(FlatClientProperties.STYLE,
                "font:bold -3;arc:8");
        lblPos.setHorizontalAlignment(SwingConstants.CENTER);
        lblPos.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        card.add(lblPos, "gaptop 3");

        // تأثير الـ hover والضغط
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblPos.setBackground(new Color(255, 215, 0, 180));
                lblPos.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblPos.setBackground(new Color(0, 0, 0, 160));
                lblPos.setForeground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showPlayerStats(name, pos, num, pages, captain);
            }
        });

        return card;
    }

    /**
     * إنشاء صورة اللاعب
     * صورة دائرية مع رقم القميص وشارة الكابتن (إن وجدت)
     *
     * @param path مسار الصورة
     * @param num رقم القميص
     * @param captain هل هو الكابتن
     * @return JPanel يحتوي على الصورة
     */
    private JPanel createPlayerPhoto(String path, String num, boolean captain) {
        JPanel photo = new JPanel() {
            private BufferedImage img;

            {
                try {
                    img = ImageIO.read(new File(path));
                } catch (Exception e) {
                    img = null;
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int size = 72;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                // رسم الظل
                g2.setColor(new Color(0, 0, 0, 60));
                g2.fillOval(x, y+2, size+2, size+2);

                // رسم الحلقة البيضاء
                g2.setColor(Color.WHITE);
                g2.fillOval(x-3, y-3, size+6, size+6);

                if (img != null) {
                    // رسم الصورة داخل دائرة
                    Ellipse2D.Double circle = new Ellipse2D.Double(x, y, size, size);
                    g2.setClip(circle);
                    g2.drawImage(img, x, y, size, size, null);
                    g2.setClip(null);
                } else {
                    // placeholder لو الصورة مش موجودة
                    g2.setColor(new Color(70, 70, 70));
                    g2.fillOval(x, y, size, size);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 26));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString("?", x + (size - fm.stringWidth("?"))/2,
                            y + (size + fm.getAscent())/2 - 2);
                }

                // رسم badge رقم القميص
                g2.setColor(new Color(0, 35, 110));
                g2.fillRoundRect(x + size - 22, y + size - 22, 22, 22, 5, 5);
                g2.setColor(new Color(255, 215, 0));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(x + size - 22, y + size - 22, 22, 22, 5, 5);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(num, x + size - 22 + (22 - fm.stringWidth(num))/2,
                        y + size - 22 + 15);

                // رسم شارة الكابتن (C)
                if (captain) {
                    g2.setColor(new Color(255, 215, 0));
                    int[] xp = {x - 5, x + 3, x + 3, x - 5};
                    int[] yp = {y + 20, y + 17, y + 40, y + 43};
                    g2.fillPolygon(xp, yp, 4);
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.BOLD, 13));
                    g2.drawString("C", x - 2, y + 33);
                }

                g2.dispose();
            }
        };

        photo.setOpaque(false);
        return photo;
    }

    /**
     * عرض تفاصيل المدير الفني
     * بيطلع message dialog بالمعلومات
     */
    private void showCoachDetails() {
        JOptionPane.showMessageDialog(this,
                "Taha Gaber- Head Coach\n\n" +
                        "Role: Technical Director & Project Manager\n" +
                        "Experience: 3+ years in software development\n" +
                        "Specialization: Team Leadership & Architecture\n\n" +
                        "Leading the development team to success!",
                "Coach Profile",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * عرض إحصائيات اللاعب
     * بيطلع dialog فيه تفاصيل اللاعب والصفحات اللي مسؤول عنها
     *
     * @param name اسم اللاعب
     * @param pos المركز
     * @param num رقم القميص
     * @param pages الصفحات المسؤول عنها
     * @param captain هل هو الكابتن
     */
    private void showPlayerStats(String name, String pos, String num,
                                 String[] pages, boolean captain) {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Player Profile", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel(new MigLayout("fill,insets 0", "[grow]", "[][grow]"));

        // الـ Header (رأس النافذة)
        JPanel header = new JPanel(new MigLayout("fill,insets 25", "[]15[]push[]", "[][]")) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(18, 95, 28),
                        0, getHeight(), new Color(12, 75, 20));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };

        JLabel lblNum = new JLabel("#" + num);
        lblNum.setForeground(new Color(255, 215, 0));
        lblNum.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");

        JLabel lblName = new JLabel(name);
        lblName.setForeground(Color.WHITE);
        lblName.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");

        header.add(lblNum);
        header.add(lblName);

        // badge الكابتن
        if (captain) {
            JLabel badge = new JLabel(" 👑 CAPTAIN ");
            badge.setOpaque(true);
            badge.setBackground(new Color(255, 215, 0));
            badge.setForeground(Color.BLACK);
            badge.putClientProperty(FlatClientProperties.STYLE, "font:bold;arc:8");
            badge.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            header.add(badge, "wrap");
        } else {
            header.add(new JLabel(), "wrap");
        }

        JLabel lblPos = new JLabel("POSITION: " + pos);
        lblPos.setForeground(new Color(255, 255, 255, 180));
        lblPos.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        header.add(lblPos, "span 3");

        content.add(header, "wrap,growx,height 110!");

        // الـ Body (محتوى النافذة)
        JPanel body = new JPanel(new MigLayout("fill,insets 25", "[grow]", "[][]"));

        JLabel title = new JLabel("⚽ ASSIGNED PAGES");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        body.add(title, "wrap,gapbottom 12");

        // panel الصفحات
        JPanel pagesPanel = new JPanel(new MigLayout("fillx,insets 15", "[grow]", "[]8[]8[]"));
        pagesPanel.putClientProperty(FlatClientProperties.STYLE,
                "arc:12;background:darken(@background,3%)");

        // عرض كل صفحة مع علامة صح
        for (String page : pages) {
            JPanel row = new JPanel(new MigLayout("fill,insets 8", "[][]", "[]"));
            row.setOpaque(false);

            JLabel check = new JLabel("✓");
            check.setForeground(new Color(34, 197, 94));
            check.putClientProperty(FlatClientProperties.STYLE, "font:bold +1");

            JLabel pageLabel = new JLabel(page);
            pageLabel.putClientProperty(FlatClientProperties.STYLE, "font:+1");

            row.add(check);
            row.add(pageLabel);
            pagesPanel.add(row, "wrap,growx");
        }

        body.add(pagesPanel, "grow,wrap");

        // زر الإغلاق
        JButton btnClose = new JButton("CLOSE");
        btnClose.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;borderWidth:0;focusWidth:0;" +
                        "background:$Component.accentColor;foreground:#fff;font:bold");
        btnClose.addActionListener(e -> dialog.dispose());
        body.add(btnClose, "width 130!,height 40!,gaptop 20");

        content.add(body, "grow");
        dialog.add(content);
        dialog.setVisible(true);
    }
}