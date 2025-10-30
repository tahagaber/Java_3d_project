package raven.application.form.other;

import javax.swing.*;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import Widget.Profile;
import raven.application.widget.SearchBar;
import Widget.ReadingDemographicPanel;
import Widget.PopularAuthorWidget;

public class FormDashboard extends JPanel {

    // إعدادات Padding
    private static final Insets TOP_PANEL_PADDING = new Insets(20, 30, 0, 30);
    private static final Insets BOTTOM_PANEL_PADDING = new Insets(10, 30, 30, 30);

    // 💡 الأحجام المخصصة للتحكم في الحجم الأولي للودجت
    private static final Dimension SEARCH_SIZE = new Dimension(380, 40);
    private static final Dimension PROFILE_SIZE = new Dimension(220, 38);
    private static final Dimension READING_WIDGET_SIZE = new Dimension(720, 300); // العرض الذي تريده للودجت اليسرى
    private static final Dimension AUTHOR_WIDGET_SIZE = new Dimension(400, 300); // العرض الذي تريده للودجت اليمنى

    // قيمة البادنج الإضافية للدفع للأسفل
    private static final int EXTRA_VERTICAL_SPACE = 100;
    private static final int READING_WIDGET_TOP_SHIFT = 110;
    private static final int HORIZONTAL_WIDGET_GAP = 20;

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

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(
                TOP_PANEL_PADDING.top,
                TOP_PANEL_PADDING.left,
                TOP_PANEL_PADDING.bottom,
                TOP_PANEL_PADDING.right
        ));

        // Search bar
        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchWrapper.setOpaque(false);

        SearchBar searchBar = new SearchBar("Search eBooks by title, author, or keyword");
        searchBar.setPreferredSize(SEARCH_SIZE);
        searchBar.setEndIcon("/raven/icon/png/more-horizontal-rectangle-svgrepo-com.png", 20, 20);
        searchBar.showEndIcon(true);
        searchWrapper.add(searchBar);
        topPanel.add(searchWrapper, BorderLayout.WEST);

        // Profile
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

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(
                BOTTOM_PANEL_PADDING.top,
                BOTTOM_PANEL_PADDING.left,
                BOTTOM_PANEL_PADDING.bottom,
                BOTTOM_PANEL_PADDING.right
        ));

        // العنوان

        // حاوية الـ Widgets (GridBagLayout)
        JPanel contentWrapper = new JPanel(new GridBagLayout());
        contentWrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weighty = 0.0;

        // 1. ReadingDemographicPanel (الودجت اليسرى)
        ReadingDemographicPanel readingPanel = new ReadingDemographicPanel();

        // 💡 نستخدم setPreferredSize لتحديد الحجم الأولي (720x300)
        readingPanel.setPreferredSize(READING_WIDGET_SIZE);

        // 💡 لضمان أنها لا تنكمش أقل من هذا الحجم إذا كانت المساحة ضيقة
        readingPanel.setMinimumSize(READING_WIDGET_SIZE);

        readingPanel.setDonutOffset(10, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // 💡 تتمدد وتأخذ المساحة الإضافية
        gbc.insets = new Insets(READING_WIDGET_TOP_SHIFT, 0, 0, HORIZONTAL_WIDGET_GAP);
        contentWrapper.add(readingPanel, gbc);

        // 2. PopularAuthorWidget (الودجت اليمنى)
        PopularAuthorWidget authorWidget = new PopularAuthorWidget();

        // 💡 نستخدم setPreferredSize للتحكم في عرضها المحدد (400x300)
        authorWidget.setPreferredSize(AUTHOR_WIDGET_SIZE);
        authorWidget.setMinimumSize(AUTHOR_WIDGET_SIZE);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0; // 💡 لا تتمدد، تحافظ على حجمها المفضل/الأدنى
        gbc.insets = new Insets(0, 0, 0, 0);
        contentWrapper.add(authorWidget, gbc);

        bottomPanel.add(contentWrapper, BorderLayout.CENTER);

        return bottomPanel;
    }

    private void initComponents() {
        lb = new JLabel("Dashboard", SwingConstants.CENTER);
        // 🐞 تم إصلاح الخطأ: تغيير 'color' إلى 'foreground'
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font; foreground:$textColor;");
    }
}