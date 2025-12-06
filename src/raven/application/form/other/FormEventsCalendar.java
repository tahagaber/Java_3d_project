package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

/**
 * Event Calendar with Month View
 * @author Raven
 */
public class FormEventsCalendar extends JPanel {

    private JPanel calendarGrid;
    private JPanel upcomingPanel;
    private JLabel monthYearLabel;
    private YearMonth currentMonth;
    private Map<LocalDate, List<Event>> eventsByDate;
    private LocalDate selectedDate;

    public FormEventsCalendar() {
        currentMonth = YearMonth.now();
        selectedDate = LocalDate.now();
        initSampleEvents();
        initComponents();
        updateCalendar();
        updateUpcomingEvents();
    }

    private void initSampleEvents() {
        eventsByDate = new HashMap<>();
        LocalDate today = LocalDate.now();

        // This week events
        addEvent(today, "Book Club Meeting", "14:00", "Conference Room A", "Meeting");
        addEvent(today, "New Books Display", "10:00", "Main Hall", "Exhibition");
        addEvent(today.plusDays(1), "Author Talk: John Doe", "16:00", "Auditorium", "Workshop");
        addEvent(today.plusDays(2), "Children's Story Time", "11:00", "Kids Section", "Activity");
        addEvent(today.plusDays(3), "Digital Resources Workshop", "15:00", "Computer Lab", "Workshop");

        // Next week events
        addEvent(today.plusDays(7), "Library Anniversary", "09:00", "All Areas", "Special");
        addEvent(today.plusDays(8), "Research Methods Seminar", "13:00", "Room 201", "Seminar");
        addEvent(today.plusDays(9), "Book Fair 2024", "10:00", "Main Hall", "Exhibition");
        addEvent(today.plusDays(10), "Teen Reading Challenge", "14:30", "Youth Zone", "Activity");

        // Later events
        addEvent(today.plusDays(14), "Academic Writing Workshop", "10:00", "Room 105", "Workshop");
        addEvent(today.plusDays(15), "Poetry Reading Night", "18:00", "Garden Area", "Special");
        addEvent(today.plusDays(16), "Library Tour for Schools", "09:00", "Reception", "Tour");
        addEvent(today.plusDays(20), "Monthly Book Exchange", "11:00", "Community Space", "Activity");
        addEvent(today.plusDays(21), "Database Training", "14:00", "Computer Lab", "Training");
    }

    private void addEvent(LocalDate date, String title, String time, String location, String type) {
        eventsByDate.putIfAbsent(date, new ArrayList<>());
        eventsByDate.get(date).add(new Event(title, time, location, type, date));
    }

    private void updateCalendar() {
        calendarGrid.removeAll();

        // Month/Year header
        monthYearLabel.setText(currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                + " " + currentMonth.getYear());

        // Day headers
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            dayLabel.putClientProperty(FlatClientProperties.STYLE,
                    "foreground:$Label.disabledForeground");
            calendarGrid.add(dayLabel);
        }

        // Calendar days
        LocalDate firstOfMonth = currentMonth.atDay(1);
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentMonth.lengthOfMonth();

        // Empty cells before first day
        for (int i = 0; i < startDayOfWeek; i++) {
            calendarGrid.add(new JPanel());
        }

        // Days of month
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentMonth.atDay(day);
            JPanel dayCell = createDayCell(date);
            calendarGrid.add(dayCell);
        }

        calendarGrid.revalidate();
        calendarGrid.repaint();
    }

    private JPanel createDayCell(LocalDate date) {
        JPanel cell = new JPanel();
        cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
        cell.setBorder(new EmptyBorder(8, 8, 8, 8));

        boolean isToday = date.equals(LocalDate.now());
        boolean isSelected = date.equals(selectedDate);
        boolean hasEvents = eventsByDate.containsKey(date);

        // Background color
        if (isToday) {
            cell.putClientProperty(FlatClientProperties.STYLE,
                    "arc:12;background:lighten(@accentColor,20%)");
        } else if (isSelected) {
            cell.putClientProperty(FlatClientProperties.STYLE,
                    "arc:12;background:@accentColor");
        } else if (hasEvents) {
            cell.putClientProperty(FlatClientProperties.STYLE,
                    "arc:12;background:darken($Panel.background,5%)");
        } else {
            cell.putClientProperty(FlatClientProperties.STYLE,
                    "arc:12;background:darken($Panel.background,3%)");
        }

        // Day number
        JLabel dayNum = new JLabel(String.valueOf(date.getDayOfMonth()), SwingConstants.CENTER);
        dayNum.setFont(new Font("Segoe UI", isToday ? Font.BOLD : Font.PLAIN, 16));
        if (isToday) {
            dayNum.setForeground(new Color(59, 130, 246));
        } else if (isSelected) {
            dayNum.setForeground(Color.WHITE);
        }
        dayNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        cell.add(dayNum);

        // Event indicators
        if (hasEvents) {
            cell.add(Box.createVerticalStrut(6));
            JPanel dotsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
            dotsPanel.setOpaque(false);

            List<Event> events = eventsByDate.get(date);
            int dotCount = Math.min(events.size(), 3);

            for (int i = 0; i < dotCount; i++) {
                JLabel dot = new JLabel("●");
                dot.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                dot.setForeground(getEventColor(events.get(i).type));
                dotsPanel.add(dot);
            }

            cell.add(dotsPanel);

            // Event count
            if (events.size() > 0) {
                JLabel countLabel = new JLabel(events.size() + " event" + (events.size() > 1 ? "s" : ""));
                countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                countLabel.putClientProperty(FlatClientProperties.STYLE,
                        "foreground:$Label.disabledForeground");
                countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                cell.add(Box.createVerticalStrut(2));
                cell.add(countLabel);
            }
        }

        // Click handler
        cell.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedDate = date;
                updateCalendar();
                updateUpcomingEvents();
                if (hasEvents) {
                    showDayEvents(date);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isSelected) {
                    cell.putClientProperty(FlatClientProperties.STYLE,
                            "arc:12;background:darken($Panel.background,8%)");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelected) {
                    if (isToday) {
                        cell.putClientProperty(FlatClientProperties.STYLE,
                                "arc:12;background:lighten(@accentColor,20%)");
                    } else if (hasEvents) {
                        cell.putClientProperty(FlatClientProperties.STYLE,
                                "arc:12;background:darken($Panel.background,5%)");
                    } else {
                        cell.putClientProperty(FlatClientProperties.STYLE,
                                "arc:12;background:darken($Panel.background,3%)");
                    }
                }
            }
        });

        return cell;
    }

    private void showDayEvents(LocalDate date) {
        List<Event> events = eventsByDate.get(date);
        if (events == null || events.isEmpty()) return;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (Event event : events) {
            panel.add(createEventInfoPanel(event));
            panel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(450, 300));
        scrollPane.setBorder(null);

        JOptionPane.showMessageDialog(this, scrollPane,
                "Events on " + date.format(java.time.format.DateTimeFormatter.ofPattern("MMMM d, yyyy")),
                JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel createEventInfoPanel(Event event) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:12;background:darken($Panel.background,3%)");

        // Left: Type indicator
        JPanel colorBar = new JPanel();
        colorBar.setPreferredSize(new Dimension(4, 50));
        colorBar.setBackground(getEventColor(event.type));
        colorBar.putClientProperty(FlatClientProperties.STYLE, "arc:2");
        panel.add(colorBar, BorderLayout.WEST);

        // Center: Event details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(event.title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel timeLabel = new JLabel("🕐 " + event.time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel locationLabel = new JLabel("📍 " + event.location);
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        locationLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");
        locationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createVerticalStrut(4));
        detailsPanel.add(timeLabel);
        detailsPanel.add(Box.createVerticalStrut(2));
        detailsPanel.add(locationLabel);

        panel.add(detailsPanel, BorderLayout.CENTER);

        // Right: Type badge
        JLabel typeBadge = createTypeBadge(event.type);
        panel.add(typeBadge, BorderLayout.EAST);

        return panel;
    }

    private void updateUpcomingEvents() {
        upcomingPanel.removeAll();

        List<Event> upcomingEvents = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Get events from today onwards
        for (int i = 0; i < 30; i++) {
            LocalDate date = today.plusDays(i);
            if (eventsByDate.containsKey(date)) {
                upcomingEvents.addAll(eventsByDate.get(date));
            }
        }

        // Sort by date
        upcomingEvents.sort((e1, e2) -> e1.date.compareTo(e2.date));

        // Display first 8 events
        int count = 0;
        for (Event event : upcomingEvents) {
            if (count >= 8) break;
            upcomingPanel.add(createUpcomingEventCard(event));
            upcomingPanel.add(Box.createVerticalStrut(12));
            count++;
        }

        if (upcomingEvents.isEmpty()) {
            JLabel noEvents = new JLabel("No upcoming events");
            noEvents.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noEvents.putClientProperty(FlatClientProperties.STYLE,
                    "foreground:$Label.disabledForeground");
            upcomingPanel.add(noEvents);
        }

        upcomingPanel.revalidate();
        upcomingPanel.repaint();
    }

    private JPanel createUpcomingEventCard(Event event) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBorder(new EmptyBorder(14, 16, 14, 16));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc:16;background:darken($Panel.background,3%)");

        // Left: Date box
        JPanel dateBox = new JPanel();
        dateBox.setLayout(new BoxLayout(dateBox, BoxLayout.Y_AXIS));
        dateBox.setPreferredSize(new Dimension(60, 60));
        dateBox.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;background:@accentColor");

        JLabel monthLabel = new JLabel(event.date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase());
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        monthLabel.setForeground(Color.WHITE);
        monthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dayLabel = new JLabel(String.valueOf(event.date.getDayOfMonth()));
        dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dateBox.add(Box.createVerticalGlue());
        dateBox.add(monthLabel);
        dateBox.add(Box.createVerticalStrut(2));
        dateBox.add(dayLabel);
        dateBox.add(Box.createVerticalGlue());

        // Center: Event info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(event.title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        detailsPanel.setOpaque(false);
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel timeLabel = new JLabel("🕐 " + event.time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        JLabel locationLabel = new JLabel("📍 " + event.location);
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        locationLabel.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        detailsPanel.add(timeLabel);
        detailsPanel.add(locationLabel);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(detailsPanel);

        // Right: Type badge
        JLabel typeBadge = createTypeBadge(event.type);

        card.add(dateBox, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(typeBadge, BorderLayout.EAST);

        // Hover effect
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.putClientProperty(FlatClientProperties.STYLE,
                        "arc:16;background:darken($Panel.background,5%)");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.putClientProperty(FlatClientProperties.STYLE,
                        "arc:16;background:darken($Panel.background,3%)");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                showDayEvents(event.date);
            }
        });

        return card;
    }

    private JLabel createTypeBadge(String type) {
        JLabel badge = new JLabel(type);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setOpaque(true);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setBorder(new EmptyBorder(4, 10, 4, 10));
        badge.putClientProperty(FlatClientProperties.STYLE, "arc:6");
        badge.setForeground(getEventColor(type));
        badge.setBackground(getEventColor(type).brighter().brighter());
        return badge;
    }

    private Color getEventColor(String type) {
        return switch (type) {
            case "Workshop" -> new Color(59, 130, 246);
            case "Seminar" -> new Color(139, 92, 246);
            case "Meeting" -> new Color(34, 197, 94);
            case "Activity" -> new Color(245, 158, 11);
            case "Exhibition" -> new Color(236, 72, 153);
            case "Special" -> new Color(220, 38, 38);
            case "Training" -> new Color(16, 185, 129);
            case "Tour" -> new Color(14, 165, 233);
            default -> new Color(100, 116, 139);
        };
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("📅 Event Calendar");
        titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        String[] types = {"Workshop", "Seminar", "Meeting", "Activity", "Exhibition", "Special"};
        for (String type : types) {
            JPanel legendItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            legendItem.setOpaque(false);

            JLabel dot = new JLabel("●");
            dot.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            dot.setForeground(getEventColor(type));

            JLabel label = new JLabel(type);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            label.putClientProperty(FlatClientProperties.STYLE,
                    "foreground:$Label.disabledForeground");

            legendItem.add(dot);
            legendItem.add(label);
            legendPanel.add(legendItem);
        }

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(legendPanel, BorderLayout.EAST);

        // Main content split
        JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
        mainPanel.setOpaque(false);

        // Left: Calendar
        JPanel calendarPanel = new JPanel(new BorderLayout(0, 15));
        calendarPanel.setOpaque(false);

        // Calendar controls
        JPanel controlsPanel = new JPanel(new BorderLayout());
        controlsPanel.setOpaque(false);

        JButton prevBtn = new JButton("◀");
        prevBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        prevBtn.setFocusPainted(false);
        prevBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prevBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;borderWidth:0;background:darken($Panel.background,5%)");
        prevBtn.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            updateCalendar();
        });

        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton nextBtn = new JButton("▶");
        nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextBtn.setFocusPainted(false);
        nextBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;borderWidth:0;background:darken($Panel.background,5%)");
        nextBtn.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            updateCalendar();
        });

        JButton todayBtn = new JButton("Today");
        todayBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        todayBtn.setFocusPainted(false);
        todayBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        todayBtn.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;borderWidth:0;background:@accentColor;foreground:#ffffff");
        todayBtn.addActionListener(e -> {
            currentMonth = YearMonth.now();
            selectedDate = LocalDate.now();
            updateCalendar();
            updateUpcomingEvents();
        });

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        navPanel.setOpaque(false);
        navPanel.add(prevBtn);
        navPanel.add(monthYearLabel);
        navPanel.add(nextBtn);

        controlsPanel.add(navPanel, BorderLayout.CENTER);
        controlsPanel.add(todayBtn, BorderLayout.EAST);

        // Calendar grid
        calendarGrid = new JPanel(new GridLayout(0, 7, 8, 8));
        calendarGrid.setOpaque(false);

        calendarPanel.add(controlsPanel, BorderLayout.NORTH);
        calendarPanel.add(calendarGrid, BorderLayout.CENTER);

        // Right: Upcoming events
        JPanel upcomingContainer = new JPanel(new BorderLayout(0, 15));
        upcomingContainer.setPreferredSize(new Dimension(400, 0));
        upcomingContainer.setOpaque(false);

        JLabel upcomingTitle = new JLabel("Upcoming Events");
        upcomingTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        upcomingPanel = new JPanel();
        upcomingPanel.setLayout(new BoxLayout(upcomingPanel, BoxLayout.Y_AXIS));
        upcomingPanel.setOpaque(false);

        JScrollPane upcomingScroll = new JScrollPane(upcomingPanel);
        upcomingScroll.setBorder(null);
        upcomingScroll.getVerticalScrollBar().setUnitIncrement(16);

        upcomingContainer.add(upcomingTitle, BorderLayout.NORTH);
        upcomingContainer.add(upcomingScroll, BorderLayout.CENTER);

        mainPanel.add(calendarPanel, BorderLayout.CENTER);
        mainPanel.add(upcomingContainer, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private static class Event {
        String title;
        String time;
        String location;
        String type;
        LocalDate date;

        Event(String title, String time, String location, String type, LocalDate date) {
            this.title = title;
            this.time = time;
            this.location = location;
            this.type = type;
            this.date = date;
        }
    }
}