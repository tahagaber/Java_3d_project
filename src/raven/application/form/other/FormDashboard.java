package raven.application.form.other;

import javax.swing.*;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;

import Widget.Profile;
import raven.application.widget.SearchBar;
import Widget.ReadingDemographicPanel;
import Widget.KeepReadingWidget;
import Widget.PopularAuthorWidget;

public class FormDashboard extends JPanel {

    // إعدادات Padding
    private static final Insets TOP_PANEL_PADDING = new Insets(20, 30, 0, 30);
    private static final Insets BOTTOM_PANEL_PADDING = new Insets(200, 30, 30, 30);

    // أحجام المكونات العلوية
    private static final Dimension SEARCH_SIZE = new Dimension(380, 40);
    private static final Dimension PROFILE_SIZE = new Dimension(220, 38);

    // الإزاحات والتباعد
    private static final int EXTRA_VERTICAL_SPACE = 300;
    private static final int READING_WIDGET_TOP_SHIFT = 500;
    private static final int HORIZONTAL_WIDGET_GAP = 10;
    private static final int VERTICAL_WIDGET_GAP = 12;

    // حجم الودجت اليسرى (للاستجابة)
    private static final Dimension LEFT_WIDGET_SIZE = new Dimension(750, 300);

    // 💡 التغيير: زيادة عرض الودجات اليمنى إلى 450 لتناسب الغلاف الجديد (200)
    private static final Dimension RIGHT_WIDGET_SIZE = new Dimension(450, 335);

    private JLabel lb;

    public FormDashboard() {
        initComponents();

        setLayout(new BorderLayout(0, 10));
        setOpaque(false);

        add(createTopPanel(), BorderLayout.NORTH);

        JPanel centerSpacer = new JPanel(new BorderLayout());
        centerSpacer.setOpaque(false);
        centerSpacer.setBorder(BorderFactory.createEmptyBorder(EXTRA_VERTICAL_SPACE, 0, 0, 0));
        centerSpacer.add(Box.createVerticalGlue(), BorderLayout.CENTER);
        add(centerSpacer, BorderLayout.CENTER);

        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // ... (createTopPanel لم يتغير) ...
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(
                TOP_PANEL_PADDING.top,
                TOP_PANEL_PADDING.left,
                TOP_PANEL_PADDING.bottom,
                TOP_PANEL_PADDING.right
        ));

        // 🔍 Search bar
        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchWrapper.setOpaque(false);

        SearchBar searchBar = new SearchBar("Search eBooks by title, author, or keyword");
        searchBar.setPreferredSize(SEARCH_SIZE);
        searchBar.setEndIcon("/raven/icon/png/more-horizontal-rectangle-svgrepo-com.png", 20, 20);
        searchBar.showEndIcon(true);
        searchWrapper.add(searchBar);
        topPanel.add(searchWrapper, BorderLayout.WEST);

        // 👤 Profile
        Profile profileWidget = new Profile();
        profileWidget.setPreferredSize(PROFILE_SIZE);
        profileWidget.setBorderColor(new Color(255, 255, 255, 120));
        profileWidget.setCornerRadius(15);

        JPanel profileWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        profileWrapper.setOpaque(false);
        profileWrapper.add(profileWidget);

        topPanel.add(profileWrapper, BorderLayout.EAST);

        return topPanel;
    }


    // ======================= الودجات السفلية (اليسار + اليمين) =======================
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(
                BOTTOM_PANEL_PADDING.top,
                BOTTOM_PANEL_PADDING.left,
                BOTTOM_PANEL_PADDING.bottom,
                BOTTOM_PANEL_PADDING.right
        ));

        JPanel contentWrapper = new JPanel(new GridBagLayout());
        contentWrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // ====================================================================
        // 💡 1. Vertical Glue (الماص العلوي في العمود الأيمن)
        // ====================================================================
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentWrapper.add(Box.createVerticalGlue(), gbc);

        // ====================================================================
        // ======== 2. ReadingDemographicPanel (الودجت اليسرى) ========
        // ====================================================================
        ReadingDemographicPanel readingPanel = new ReadingDemographicPanel();
        readingPanel.setDonutOffset(10, 0);

        // الاستجابة الكاملة للودجت اليسرى
        readingPanel.setMinimumSize(LEFT_WIDGET_SIZE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(READING_WIDGET_TOP_SHIFT, 0, 0, HORIZONTAL_WIDGET_GAP);
        contentWrapper.add(readingPanel, gbc);

        // ====================================================================
        // ======== 3. KeepReadingWidget (العلوية اليمنى) - حجم ثابت 450x335 ========
        // ====================================================================
        KeepReadingWidget keepReadingWidget = new KeepReadingWidget();
        // 💡 الحجم الجديد 450x335
        keepReadingWidget.setPreferredSize(RIGHT_WIDGET_SIZE);
        keepReadingWidget.setMinimumSize(RIGHT_WIDGET_SIZE);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, VERTICAL_WIDGET_GAP, 0);
        contentWrapper.add(keepReadingWidget, gbc);

        // ====================================================================
        // ======== 4. PopularAuthorWidget (السفلية اليمنى) - حجم ثابت 450x335 ========
        // ====================================================================
        PopularAuthorWidget authorWidget = new PopularAuthorWidget();
        // 💡 الحجم الجديد 450x335
        authorWidget.setPreferredSize(RIGHT_WIDGET_SIZE);
        authorWidget.setMinimumSize(RIGHT_WIDGET_SIZE);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentWrapper.add(authorWidget, gbc);

        // ====================================================================
        // 💡 5. Horizontal Glue (الماص الأفقي) - يمتص أي مساحة أفقية زائدة
        // ====================================================================
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        bottomPanel.add(contentWrapper, BorderLayout.CENTER);
        return bottomPanel;
    }

    private void initComponents() {
        lb = new JLabel("Dashboard", SwingConstants.CENTER);
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font; foreground:$textColor;");
    }
}