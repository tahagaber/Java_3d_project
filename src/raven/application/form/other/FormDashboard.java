package raven.application.form.other;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import com.formdev.flatlaf.FlatClientProperties;
import raven.toast.Notifications;
import Widget.Card;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.*;
import Widget.Table;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import java.util.ArrayList;
import javax.swing.BoxLayout;


/**
 *
 * @author Raven
 */

public class FormDashboard extends JPanel {

    private JScrollPane scroll; // للـ cardStrip المتحرك
    private JPanel cardStrip;   // الحاوية للكروت المتحركة

    public FormDashboard() {
        initComponents();
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");

        // ===== الكروت الرئيسية =====
        Card card1 = new Card();
        card1.setPreferredSize(new Dimension(250, 120));
        card1.setTitle("Students");
        card1.setValue("1,250");
        card1.setPercentage("+12%");
        card1.setGradient(new Color(33, 150, 243), new Color(156, 39, 176));

        Card card2 = new Card();
        card2.setPreferredSize(new Dimension(250, 120));
        card2.setTitle("Courses");
        card2.setValue("56");
        card2.setPercentage("+5%");
        card2.setGradient(new Color(255, 87, 34), new Color(255, 193, 7));

        Card card3 = new Card();
        card3.setPreferredSize(new Dimension(250, 120));
        card3.setTitle("Revenue");
        card3.setValue("$12,540");
        card3.setPercentage("+8%");
        card3.setGradient(new Color(76, 175, 80), new Color(139, 195, 74));

        Card card4 = new Card();
        card4.setPreferredSize(new Dimension(250, 120));
        card4.setTitle("Staff");
        card4.setValue("24");
        card4.setPercentage("+3%");
        card4.setGradient(new Color(63, 81, 181), new Color(103, 58, 183));

        JPanel panelCards = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelCards.setOpaque(false);
        panelCards.add(card1);
        panelCards.add(card2);
        panelCards.add(card3);
        panelCards.add(card4);
        panelCards.setPreferredSize(new Dimension(1000, 150));

// ===== الجدول وتفاصيل الطلاب (تصغير لأسفل) =====
        Table table1 = new Table();
        JScrollPane scrollTable = new JScrollPane(table1);
// قلل ارتفاع الجدول
        scrollTable.setPreferredSize(new Dimension(600, 150)); // بدل 180 أو 200
        scrollTable.setBorder(BorderFactory.createTitledBorder("Students List"));

        JTextArea detailsArea = new JTextArea(8, 25); // قلل عدد الأسطر
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollDetails = new JScrollPane(detailsArea);
        scrollDetails.setPreferredSize(new Dimension(300, 150)); // قلل ارتفاع الـ JTextArea
        scrollDetails.setBorder(BorderFactory.createTitledBorder("Student Details"));

// Panel الجدول والنص
        JPanel panelTableAndDetails = new JPanel(new BorderLayout(10, 8));
        panelTableAndDetails.setOpaque(false);
        panelTableAndDetails.add(scrollTable, BorderLayout.CENTER);
        panelTableAndDetails.add(scrollDetails, BorderLayout.EAST);
// قلل ارتفاع الحاوية الكلي حتى ينزل للأسفل
        panelTableAndDetails.setPreferredSize(new Dimension(1000, 180));

        // ===== الكروت المتحركة =====
        cardStrip = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        cardStrip.setOpaque(false);

        ArrayList<JLabel> labels = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            JLabel card = new JLabel("Member " + i, SwingConstants.CENTER);
            card.setPreferredSize(new Dimension(120, 120));
            card.setOpaque(true);
            card.setBackground(new Color(100 + i * 15, 150, 200 - i * 10));
            card.setForeground(Color.WHITE);
            card.setFont(new Font("Segoe UI", Font.BOLD, 14));
            card.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
            cardStrip.add(card);
            labels.add(card);
        }

        // نسخة ثانية لإنشاء تأثير الحلقة
        for (JLabel lbl : labels) {
            JLabel clone = new JLabel(lbl.getText(), SwingConstants.CENTER);
            clone.setPreferredSize(lbl.getPreferredSize());
            clone.setOpaque(true);
            clone.setBackground(lbl.getBackground());
            clone.setForeground(lbl.getForeground());
            clone.setFont(lbl.getFont());
            clone.setBorder(lbl.getBorder());
            cardStrip.add(clone);
        }

        scroll = new JScrollPane(cardStrip);
        scroll.setPreferredSize(new Dimension(1000, 250));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createTitledBorder("Team Members"));

        javax.swing.Timer moveTimer = new javax.swing.Timer(30, e -> {
            JScrollBar hBar = scroll.getHorizontalScrollBar();
            int newValue = hBar.getValue() + 2;
            if (newValue >= hBar.getMaximum() / 2) {
                hBar.setValue(newValue - hBar.getMaximum() / 2);
            } else {
                hBar.setValue(newValue);
            }
        });
        moveTimer.start();

        // ===== Panel رئيسي مع ترتيب عمودي =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        // الترتيب الجديد: كروت رئيسية → كروت متحركة → الجدول + نص
        panelCards.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTableAndDetails.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(panelCards);
        mainPanel.add(scroll);
        mainPanel.add(panelTableAndDetails);

        // ===== بيانات الجدول =====
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {1, "Tasnim", "Software", 3.8, "Active"},
                        {2, "Taha", "Software", 3.8, "Active"},
                        {3, "Nourin", "Software", 3.8, "Active"},
                        {4, "Ibrahim", "Software", 3.8, "Active"},
                        {5, "Menna", "Software", 3.8, "Active"},
                },
                new String[]{"ID", "Name", "Department", "GPA", "Status"}
        );
        table1.setModel(model);

        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    Object id = table1.getValueAt(row, 0);
                    Object name = table1.getValueAt(row, 1);
                    Object dept = table1.getValueAt(row, 2);

                    detailsArea.setText("🧑‍🎓 Student Details\n"
                            + "--------------------------\n"
                            + "ID: " + id + "\n"
                            + "Name: " + name + "\n"
                            + "Department: " + dept + "\n"
                            + "GPA: 3.8\n"
                            + "Status: Active\n");
                }
            }
        });

        // ===== إضافة mainPanel للواجهة =====
        setLayout(new BorderLayout());
        add(lb, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb = new javax.swing.JLabel();

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb.setText("Dashboard");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb)
                .addContainerGap(592, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lb;
    // End of variables declaration//GEN-END:variables
}
