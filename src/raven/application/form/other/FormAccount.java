package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormAccount extends JPanel {

    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtUsername;
    private JPasswordField txtCurrentPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;
    private JLabel lblProfilePic;

    public FormAccount() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        mainPanel.setOpaque(false);

        // Left Column
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setOpaque(false);

        leftColumn.add(createPersonalInfoCard());
        leftColumn.add(Box.createVerticalStrut(20));
        leftColumn.add(createAccountInfoCard());

        // Right Column
        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setOpaque(false);

        rightColumn.add(createSecurityCard());
        rightColumn.add(Box.createVerticalStrut(20));
        rightColumn.add(createActivityCard());

        mainPanel.add(leftColumn);
        mainPanel.add(rightColumn);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Left side - Avatar and Info
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftPanel.setOpaque(false);

        // Avatar
        lblProfilePic = new JLabel();
        lblProfilePic.setPreferredSize(new Dimension(100, 100));
        lblProfilePic.setIcon(createAvatarIcon());
        leftPanel.add(lblProfilePic);

        // User Info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel lblName = new JLabel("Ahmed Mohamed");
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel lblRole = new JLabel("Library Administrator");
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRole.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        JLabel lblMember = new JLabel("Member since January 2024");
        lblMember.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMember.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        infoPanel.add(lblName);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(lblRole);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(lblMember);

        leftPanel.add(infoPanel);

        // Right side - Buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JButton btnChangeAvatar = createStyledButton("Change Avatar", false);
        JButton btnEditProfile = createStyledButton("Edit Profile", true);

        rightPanel.add(btnChangeAvatar);
        rightPanel.add(btnEditProfile);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createPersonalInfoCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 16));

        JLabel title = new JLabel("Personal Information");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        // Full Name
        formPanel.add(createFieldLabel("Full Name"));
        formPanel.add(Box.createVerticalStrut(6));
        txtName = createStyledTextField("Ahmed Mohamed");
        formPanel.add(txtName);
        formPanel.add(Box.createVerticalStrut(16));

        // Email
        formPanel.add(createFieldLabel("Email Address"));
        formPanel.add(Box.createVerticalStrut(6));
        txtEmail = createStyledTextField("ahmed.mohamed@library.com");
        formPanel.add(txtEmail);
        formPanel.add(Box.createVerticalStrut(16));

        // Phone
        formPanel.add(createFieldLabel("Phone Number"));
        formPanel.add(Box.createVerticalStrut(6));
        txtPhone = createStyledTextField("+20 123 456 7890");
        formPanel.add(txtPhone);

        card.add(formPanel, BorderLayout.CENTER);

        // Save Button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setOpaque(false);
        JButton btnSave = createStyledButton("Save Changes", true);
        btnSave.addActionListener(e -> savePersonalInfo());
        btnPanel.add(btnSave);
        card.add(btnPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createAccountInfoCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 16));

        JLabel title = new JLabel("Account Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        // Username
        formPanel.add(createFieldLabel("Username"));
        formPanel.add(Box.createVerticalStrut(6));
        txtUsername = createStyledTextField("ahmed.mohamed");
        txtUsername.setEnabled(false);
        formPanel.add(txtUsername);
        formPanel.add(Box.createVerticalStrut(16));

        // Role Badge
        formPanel.add(createFieldLabel("Account Role"));
        formPanel.add(Box.createVerticalStrut(6));
        JLabel roleBadge = createRoleBadge("Administrator");
        roleBadge.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(roleBadge);

        card.add(formPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createSecurityCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 16));

        JLabel title = new JLabel("Security & Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        // Current Password
        formPanel.add(createFieldLabel("Current Password"));
        formPanel.add(Box.createVerticalStrut(6));
        txtCurrentPassword = createStyledPasswordField();
        formPanel.add(txtCurrentPassword);
        formPanel.add(Box.createVerticalStrut(16));

        // New Password
        formPanel.add(createFieldLabel("New Password"));
        formPanel.add(Box.createVerticalStrut(6));
        txtNewPassword = createStyledPasswordField();
        formPanel.add(txtNewPassword);
        formPanel.add(Box.createVerticalStrut(16));

        // Confirm Password
        formPanel.add(createFieldLabel("Confirm New Password"));
        formPanel.add(Box.createVerticalStrut(6));
        txtConfirmPassword = createStyledPasswordField();
        formPanel.add(txtConfirmPassword);

        card.add(formPanel, BorderLayout.CENTER);

        // Change Password Button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setOpaque(false);
        JButton btnChange = createStyledButton("Change Password", true);
        btnChange.addActionListener(e -> changePassword());
        btnPanel.add(btnChange);
        card.add(btnPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createActivityCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 16));

        JLabel title = new JLabel("Account Activity");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        contentPanel.add(createActivityItem("🔐", "Last Login", "Dec 6, 2025 at 10:30 AM"));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(createActivityItem("📚", "Books Borrowed", "47 books this year"));
        contentPanel.add(Box.createVerticalStrut(12));
        contentPanel.add(createActivityItem("💻", "Active Sessions", "2 devices logged in"));

        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createActivityItem(String icon, String title, String value) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 12, 10, 12));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "arc:10;background:darken($Panel.background,3%)");

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        panel.add(lblIcon, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblValue.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");

        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(lblValue);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    // Helper Methods
    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;background:darken($Panel.background,3%)");
        return card;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.putClientProperty(FlatClientProperties.STYLE,
                "foreground:$Label.disabledForeground");
        return label;
    }

    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text);
        field.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;margin:8,12,8,12");
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;margin:8,12,8,12");
        field.putClientProperty(FlatClientProperties.STYLE + ".showRevealButton", true);
        return field;
    }

    private JButton createStyledButton(String text, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isPrimary) {
            button.putClientProperty(FlatClientProperties.STYLE,
                    "arc:8;" +
                            "borderWidth:0;" +
                            "background:@accentColor;" +
                            "foreground:#ffffff");
        } else {
            button.putClientProperty(FlatClientProperties.STYLE,
                    "arc:8;" +
                            "borderWidth:0;" +
                            "background:darken($Panel.background,8%)");
        }

        return button;
    }

    private JLabel createRoleBadge(String role) {
        JLabel badge = new JLabel("✓ " + role);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(6, 12, 6, 12));
        badge.setForeground(new Color(5, 150, 105));
        badge.setBackground(new Color(209, 250, 229));
        badge.putClientProperty(FlatClientProperties.STYLE, "arc:8");
        return badge;
    }

    private Icon createAvatarIcon() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                Color accent = UIManager.getColor("Component.accentColor");
                if (accent == null) accent = new Color(100, 149, 237);

                GradientPaint gradient = new GradientPaint(
                        x, y, accent,
                        x + 100, y + 100, accent.darker()
                );
                g2.setPaint(gradient);
                g2.fillOval(x, y, 100, 100);

                // User icon
                g2.setColor(Color.WHITE);
                g2.fillOval(x + 30, y + 20, 40, 40);
                g2.fillArc(x + 15, y + 55, 70, 50, 0, -180);

                g2.dispose();
            }

            @Override
            public int getIconWidth() { return 100; }

            @Override
            public int getIconHeight() { return 100; }
        };
    }

    // Action Methods
    private void savePersonalInfo() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid email address",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save to database here
        JOptionPane.showMessageDialog(this,
                "✓ Personal information updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void changePassword() {
        String current = new String(txtCurrentPassword.getPassword());
        String newPass = new String(txtNewPassword.getPassword());
        String confirm = new String(txtConfirmPassword.getPassword());

        if (current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all password fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPass.equals(confirm)) {
            JOptionPane.showMessageDialog(this,
                    "New passwords do not match",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newPass.length() < 8) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 8 characters",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update password in database here
        JOptionPane.showMessageDialog(this,
                "✓ Password changed successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        // Clear fields
        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }
}