import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class JobSyncGUI extends JFrame {

    // =====================================================
    // CURRENT USER
    // =====================================================

    private User currentUser;

    // Loads and saves job postings (jobs.txt) as FullTimeJob / PartTimeJob objects
    private final JobManager jobManager = new JobManager();

    // =====================================================
    // FONTS
    // =====================================================

    private final Font titleFont =
            new Font("Arial", Font.BOLD, 28);

    private final Font headingFont =
            new Font("Arial", Font.BOLD, 20);

    private final Font normalFont =
            new Font("Arial", Font.PLAIN, 14);

    private final Color pageColor = new Color(246, 248, 251);
    private final Color brandColor = new Color(24, 47, 75);
    private final Color accentColor = new Color(37, 132, 119);
    private final Color textColor = new Color(35, 48, 63);
    private final Color mutedColor = new Color(105, 119, 134);
    private final Color borderColor = new Color(222, 229, 236);
    private final Map<String, Object> savedWorkspaceDefaults =
            new LinkedHashMap<>();
    private boolean workspaceThemeApplied;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public JobSyncGUI() {

        setTitle("JobSync");
        setSize(1000, 700);
        setMinimumSize(new Dimension(860, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        UIManager.put("Label.foreground", textColor);
        UIManager.put("Panel.background", pageColor);
        UIManager.put("Button.font", normalFont);
        UIManager.put("TextField.font", normalFont);
        UIManager.put("PasswordField.font", normalFont);
        UIManager.put("ComboBox.font", normalFont);

        showLoginPanel();
    }

    // =====================================================
    // MAIN PANEL
    // =====================================================

    private JPanel createMainPanel() {

        JPanel panel = new JPanel(new BorderLayout(24, 24));
        panel.setBackground(pageColor);
        panel.setBorder(BorderFactory.createEmptyBorder(32, 36, 32, 36));

        return panel;
    }

    private JPanel createBrandPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(brandColor);
        panel.setBorder(BorderFactory.createEmptyBorder(46, 38, 38, 34));
        panel.setPreferredSize(new Dimension(340, 0));

        JLabel brand = new JLabel("JOBSYNC");
        brand.setFont(new Font("Arial", Font.BOLD, 28));
        brand.setForeground(Color.WHITE);

        JLabel eyebrow = new JLabel("YOUR NEXT CHAPTER STARTS HERE");
        eyebrow.setFont(new Font("Arial", Font.BOLD, 11));
        eyebrow.setForeground(new Color(131, 211, 194));

        JLabel headline = new JLabel(
                "<html>Find the right<br>fit. Build what's<br>next.</html>"
        );
        headline.setFont(new Font("Arial", Font.BOLD, 31));
        headline.setForeground(Color.WHITE);


        panel.add(brand);
        panel.add(Box.createVerticalStrut(70));
        panel.add(eyebrow);
        panel.add(Box.createVerticalStrut(16));
        panel.add(headline);
        panel.add(Box.createVerticalStrut(18));

        return panel;
    }

    private JPanel createAuthPage(
            String heading,
            String description,
            JPanel formPanel,
            JPanel buttonPanel) {

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(pageColor);
        page.add(createBrandPanel(), BorderLayout.WEST);

        JPanel card = new JPanel(new BorderLayout(0, 24));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(34, 38, 30, 38)
        ));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setBackground(Color.WHITE);

        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 26));
        headingLabel.setForeground(brandColor);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(normalFont);
        descriptionLabel.setForeground(mutedColor);

        headingPanel.add(headingLabel);
        headingPanel.add(Box.createVerticalStrut(8));
        headingPanel.add(descriptionLabel);
        card.add(headingPanel, BorderLayout.NORTH);

        formPanel.setBackground(Color.WHITE);
        card.add(formPanel, BorderLayout.CENTER);
        buttonPanel.setBackground(Color.WHITE);
        card.add(buttonPanel, BorderLayout.SOUTH);

        JPanel cardWrapper = new JPanel(new GridBagLayout());
        cardWrapper.setBackground(pageColor);
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(28, 48, 28, 48));
        cardWrapper.add(card);
        page.add(cardWrapper, BorderLayout.CENTER);
        return page;
    }

    private void addAuthField(
            JPanel panel,
            String labelText,
            JComponent field,
            int row) {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row * 2;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 7, 0);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(label, constraints);

        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 18, 0);
        styleInput(field);
        panel.add(field, constraints);
    }

    private void styleInput(JComponent field) {
        field.setFont(normalFont);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(320, 42));
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(primary ? accentColor : Color.WHITE);
        button.setForeground(primary ? Color.WHITE : brandColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primary ? accentColor : borderColor),
                BorderFactory.createEmptyBorder(11, 18, 11, 18)
        ));
    }

    private JPanel createDashboardPanel(
            String title,
            String description,
            String fullName,
            JButton[] actions) {

        JPanel panel = new JPanel(new BorderLayout(24, 24)) {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D g2 = (Graphics2D) graphics.create();
                g2.setRenderingHint(
                        RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY
                );
                g2.setPaint(new GradientPaint(
                        0, 0, new Color(233, 243, 245),
                        getWidth(), getHeight(), new Color(248, 250, 253)
                ));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 38, 26, 38));

        String dashboardRole = title.startsWith("Applicant")
                ? "APPLICANT WORKSPACE" : "EMPLOYER WORKSPACE";
        GradientPanel hero = new GradientPanel(
                new Color(24, 55, 78),
                new Color(35, 132, 119)
        );
        hero.setLayout(new BorderLayout(20, 0));
        hero.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 28));

        JPanel heroCopy = new JPanel();
        heroCopy.setLayout(new BoxLayout(heroCopy, BoxLayout.Y_AXIS));
        heroCopy.setOpaque(false);

        JLabel eyebrow = new JLabel(dashboardRole);
        eyebrow.setFont(new Font("Arial", Font.BOLD, 11));
        eyebrow.setForeground(new Color(174, 229, 217));

        JLabel titleLabel = new JLabel("Hello, " + fullName);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(224, 237, 241));

        heroCopy.add(eyebrow);
        heroCopy.add(Box.createVerticalStrut(8));
        heroCopy.add(titleLabel);
        heroCopy.add(Box.createVerticalStrut(7));
        heroCopy.add(descriptionLabel);
        hero.add(heroCopy, BorderLayout.CENTER);

        JPanel heroActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        heroActions.setOpaque(false);
        for (JButton action : actions) {
            if ("Logout".equalsIgnoreCase(action.getText())) {
                action.setFont(new Font("Arial", Font.BOLD, 12));
                action.setForeground(Color.WHITE);
                action.setBackground(new Color(255, 255, 255, 28));
                action.setOpaque(false);
                action.setFocusPainted(false);
                action.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                action.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(218, 239, 237, 170)
                        ),
                        BorderFactory.createEmptyBorder(9, 15, 9, 15)
                ));
                heroActions.add(action);
                break;
            }
        }
        hero.add(heroActions, BorderLayout.EAST);
        panel.add(hero, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(0, 14));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        JPanel sectionHeading = new JPanel(new BorderLayout());
        sectionHeading.setOpaque(false);
        JLabel sectionTitle = new JLabel("Your workspace");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 20));
        sectionTitle.setForeground(brandColor);
        JLabel sectionHint = new JLabel("Choose an action to get started");
        sectionHint.setFont(new Font("Arial", Font.PLAIN, 13));
        sectionHint.setForeground(mutedColor);
        sectionHeading.add(sectionTitle, BorderLayout.WEST);
        sectionHeading.add(sectionHint, BorderLayout.EAST);
        content.add(sectionHeading, BorderLayout.NORTH);

        JPanel actionPanel = new JPanel(new GridLayout(0, 3, 16, 16));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        for (JButton action : actions) {
            if ("Logout".equalsIgnoreCase(action.getText())) {
                continue;
            }
            actionPanel.add(createActionCard(action));
        }
        JScrollPane cardsScrollPane = new JScrollPane(actionPanel);
        cardsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        cardsScrollPane.setOpaque(false);
        cardsScrollPane.getViewport().setOpaque(false);
        cardsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        content.add(cardsScrollPane, BorderLayout.CENTER);
        JLabel footer = new JLabel("JOBSYNC  |  CAREER CONNECTIONS");
        footer.setFont(new Font("Arial", Font.BOLD, 10));
        footer.setForeground(new Color(126, 144, 158));
        content.add(footer, BorderLayout.SOUTH);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createActionCard(JButton action) {
        String label = action.getText();
        String[] cardDetails = getDashboardCardDetails(label);

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(17, 18, 16, 18)
        ));

        JPanel cardTop = new JPanel(new BorderLayout());
        cardTop.setOpaque(false);
        JLabel badge = new JLabel(cardDetails[0], SwingConstants.CENTER);
        badge.setFont(new Font("Arial", Font.BOLD, 12));
        badge.setForeground(accentColor);
        badge.setOpaque(true);
        badge.setBackground(new Color(225, 243, 239));
        badge.setPreferredSize(new Dimension(36, 36));
        cardTop.add(badge, BorderLayout.WEST);

        JLabel arrow = new JLabel("OPEN");
        arrow.setFont(new Font("Arial", Font.BOLD, 10));
        arrow.setForeground(accentColor);
        cardTop.add(arrow, BorderLayout.EAST);
        card.add(cardTop, BorderLayout.NORTH);

        action.setText("<html><div style='width:190px'>"
                + "<span style='font-size:15pt;font-weight:bold;color:#182f4b'>"
                + escapeHtml(label)
                + "</span><br><span style='font-size:10pt;color:#697786'>"
                + escapeHtml(cardDetails[1])
                + "</span></div></html>");
        action.setFont(normalFont);
        action.setHorizontalAlignment(SwingConstants.LEFT);
        action.setVerticalAlignment(SwingConstants.TOP);
        action.setBackground(Color.WHITE);
        action.setForeground(brandColor);
        action.setOpaque(false);
        action.setContentAreaFilled(false);
        action.setFocusPainted(false);
        action.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        action.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.add(action, BorderLayout.CENTER);
        card.setPreferredSize(new Dimension(220, 145));

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                setActionCardHover(card, true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                setActionCardHover(card, false);
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                action.doClick();
            }
        });
        action.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                setActionCardHover(card, true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                setActionCardHover(card, card.getMousePosition() != null);
            }
        });
        arrow.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                action.doClick();
            }
        });
        cardTop.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                action.doClick();
            }
        });
        badge.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                action.doClick();
            }
        });
        return card;
    }

    private void setActionCardHover(JPanel card, boolean hovered) {
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        hovered ? accentColor : borderColor,
                        hovered ? 2 : 1
                ),
                BorderFactory.createEmptyBorder(
                        hovered ? 16 : 17,
                        hovered ? 17 : 18,
                        hovered ? 15 : 16,
                        hovered ? 17 : 18
                )
        ));
        card.setBackground(hovered ? new Color(252, 255, 254) : Color.WHITE);
    }

    private String[] getDashboardCardDetails(String label) {
        if ("Search & Apply Jobs".equals(label)) {
            return new String[]{"01", "Explore openings that match your goals."};
        }
        if ("My Applications".equals(label) || "Applications".equals(label)) {
            return new String[]{"02", "Review application progress and details."};
        }
        if ("My Interviews".equals(label) || "Schedule Interview".equals(label)) {
            return new String[]{"03", "Keep interviews and next steps on track."};
        }
        if ("My Resume".equals(label)) {
            return new String[]{"04", "Build a profile employers can discover."};
        }
        if ("My Profile".equals(label)) {
            return new String[]{"05", "Manage your account information."};
        }
        if ("Post Job".equals(label)) {
            return new String[]{"01", "Share a new opportunity with candidates."};
        }
        if ("My Jobs".equals(label)) {
            return new String[]{"02", "View the roles you have posted."};
        }
        if ("Review Application".equals(label)) {
            return new String[]{"04", "Review candidates and update decisions."};
        }
        if ("Update Job".equals(label)) {
            return new String[]{"06", "Edit details on an open position."};
        }
        if ("Close Job".equals(label)) {
            return new String[]{"07", "Close a role that is no longer open."};
        }
        return new String[]{"", "Open this workspace feature."};
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private void applyWorkspaceTheme() {
        if (workspaceThemeApplied) {
            return;
        }

        String[] keys = {
                "Panel.background",
                "Label.foreground",
                "OptionPane.background",
                "OptionPane.messageForeground",
                "Button.background",
                "Button.foreground",
                "Button.focus",
                "TextField.background",
                "TextField.foreground",
                "TextArea.background",
                "TextArea.foreground",
                "ComboBox.background",
                "ComboBox.foreground",
                "ScrollPane.background",
                "TitledBorder.titleColor"
        };
        for (String key : keys) {
            savedWorkspaceDefaults.put(key, UIManager.get(key));
        }

        UIManager.put("Panel.background", pageColor);
        UIManager.put("Label.foreground", textColor);
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", textColor);
        UIManager.put("Button.background", accentColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", new Color(179, 219, 210));
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", textColor);
        UIManager.put("TextArea.background", Color.WHITE);
        UIManager.put("TextArea.foreground", textColor);
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", textColor);
        UIManager.put("ScrollPane.background", Color.WHITE);
        UIManager.put("TitledBorder.titleColor", brandColor);
        workspaceThemeApplied = true;
    }

    private void restoreLoginTheme() {
        if (!workspaceThemeApplied) {
            return;
        }

        for (Map.Entry<String, Object> entry : savedWorkspaceDefaults.entrySet()) {
            if (entry.getValue() == null) {
                UIManager.getDefaults().remove(entry.getKey());
            } else {
                UIManager.put(entry.getKey(), entry.getValue());
            }
        }
        savedWorkspaceDefaults.clear();
        workspaceThemeApplied = false;
    }

    private void styleWorkspaceTree(Component component) {
        if (component instanceof JLabel) {
            component.setForeground(textColor);
            component.setFont(normalFont);
        } else if (component instanceof JTextField
                || component instanceof JPasswordField
                || component instanceof JComboBox) {
            JComponent input = (JComponent) component;
            input.setFont(normalFont);
            input.setForeground(textColor);
            input.setBackground(Color.WHITE);
            input.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(borderColor),
                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
            ));
        } else if (component instanceof JTextArea) {
            component.setFont(normalFont);
            component.setForeground(textColor);
            component.setBackground(Color.WHITE);
        } else if (component instanceof JButton) {
            JButton button = (JButton) component;
            styleButton(button, isPrimaryAction(button.getText()));
        }

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                styleWorkspaceTree(child);
            }
        }
    }

    private boolean isPrimaryAction(String text) {
        String normalized = text == null ? "" : text.toLowerCase();
        return normalized.contains("save")
                || normalized.contains("create")
                || normalized.contains("apply")
                || normalized.contains("update")
                || normalized.contains("schedule")
                || normalized.contains("post")
                || normalized.equals("ok");
    }

    private static class GradientPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        private final Color startColor;
        private final Color endColor;

        private GradientPanel(Color startColor, Color endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            g2.setPaint(new GradientPaint(
                    0, 0, startColor, getWidth(), getHeight(), endColor
            ));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
            g2.dispose();
            super.paintComponent(graphics);
        }
    }
    // =====================================================
    // LOGIN
    // =====================================================


    private void showLoginPanel() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        addAuthField(formPanel, "Username", usernameField, 0);
        addAuthField(formPanel, "Password", passwordField, 1);

        JButton loginButton = new JButton("Sign in");
        JButton registerButton = new JButton("Create an account");
        styleButton(loginButton, true);
        styleButton(registerButton, false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        loginButton.addActionListener(e -> {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                login(username, password);
        });

        registerButton.addActionListener(
                e -> showRegisterPanel()
        );

        JPanel mainPanel = createAuthPage(
                "Welcome back",
                "Sign in to continue to your JobSync workspace.",
                formPanel,
                buttonPanel
        );
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    // =====================================================
    // LOGIN LOGIC
    // =====================================================

    private void login(
            String username,
            String password) {

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        List<String> users =
                FileHandler.readAll("users.txt");

        for (String line : users) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length < 5) {
                continue;
            }

            String storedUsername = parts[1];
            String storedPassword = parts[2];
            String fullName = parts[3];
            String role = parts[4];

            if (storedUsername.equalsIgnoreCase(username)
                    && storedPassword.equals(password)) {

                int userId;

                try {

                    userId =
                            Integer.parseInt(parts[0]);

                } catch (NumberFormatException e) {

                    showError("Invalid user data.");
                    return;
                }

                if (role.equalsIgnoreCase("APPLICANT")) {

                    Applicant applicant =
                            new Applicant(
                                    userId,
                                    storedUsername,
                                    storedPassword,
                                    fullName
                            );

                                        if (parts.length == 6 || parts.length == 9 || parts.length >= 20) {
                                                int ageIndex = parts.length >= 20 ? 19
                                                                : parts.length == 9 ? 8 : 5;
                                                try {
                                                        applicant.setAge(Integer.parseInt(parts[ageIndex]));
                                                } catch (NumberFormatException ignored) {
                                                }
                                        }

                if (parts.length >= 19) {

                // Load the new resume format
                Resume resume = new Resume(
                        parts[5],                       // Full name
                        parts[6],                       // Email
                        parts[7],                       // Mobile number
                        parts[8],                       // Address
                        decodeMultilineText(parts[9]),  // Profile summary
                        decodeMultilineText(parts[10]), // Education
                        parts[11],                      // Skill 1
                        parts[12],                      // Skill 2
                        parts[13],                      // Skill 3
                        parts[14],                      // Skill 4
                        parts[15],                      // Skill 5
                        parts[16],                      // Experience title
                        parts[17],                      // Organization
                        decodeMultilineText(parts[18])  // Experience description
                );

                applicant.setResume(resume);

                } else if (parts.length >= 8) {

                // Keep compatibility with older saved resumes
                Resume resume = new Resume(
                        parts[5],
                        parts[6],
                        parts[7]
                );

                applicant.setResume(resume);
                }

                    currentUser = applicant;

                    JOptionPane.showMessageDialog(
                            this,
                            "Welcome, " + fullName + "!",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    showApplicantDashboard(applicant);
                    return;
                }

                if (role.equalsIgnoreCase("EMPLOYER")) {

                    Employer employer =
                            new Employer(
                                    userId,
                                    storedUsername,
                                    storedPassword,
                                    fullName
                            );

                                        if (parts.length >= 6) {
                                                try {
                                                        employer.setAge(Integer.parseInt(parts[parts.length - 1]));
                                                } catch (NumberFormatException ignored) {
                                                }
                                        }

                    currentUser = employer;

                    JOptionPane.showMessageDialog(
                            this,
                            "Welcome, " + fullName + "!",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    showEmployerDashboard(employer);
                    return;
                }
            }
        }

        showError("Incorrect username or password.");
    }

    // =====================================================
    // REGISTER PANEL
    // =====================================================

    private void showRegisterPanel() {

        JPanel formPanel = new JPanel(new GridBagLayout());
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField ageField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(
                new String[]{"Applicant", "Employer"}
        );

        addAuthField(formPanel, "Full name", nameField, 0);
        addAuthField(formPanel, "Username", usernameField, 1);
        addAuthField(formPanel, "Age", ageField, 2);
        addAuthField(formPanel, "Password", passwordField, 3);
        addAuthField(formPanel, "Account type", roleBox, 4);

        JButton registerButton = new JButton("Create account");
        JButton backButton = new JButton("Back to sign in");
        styleButton(registerButton, true);
        styleButton(backButton, false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        registerButton.addActionListener(e -> {

            String fullName =
                    nameField.getText().trim();

            String username =
                    usernameField.getText().trim();

            String age =
                    ageField.getText().trim();

            String password =
                    new String(
                            passwordField.getPassword()
                    );

            String role =
                    (String) roleBox.getSelectedItem();

            registerUser(
                    fullName,
                    username,
                    age,
                    password,
                    role
            );
        });

        backButton.addActionListener(
                e -> showLoginPanel()
        );

        JPanel mainPanel = createAuthPage(
                "Create your account",
                "Join JobSync as an applicant or employer.",
                formPanel,
                buttonPanel
        );
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    // =====================================================
    // REGISTER LOGIC
    // =====================================================

    private void registerUser(
            String fullName,
            String username,
            String ageText,
            String password,
            String role) {

        if (fullName.isEmpty()
                || username.isEmpty()
                || ageText.isEmpty()
                || password.isEmpty()) {

            showWarning(
                    "Please complete all fields."
            );

            return;
        }

                int age;
                try {
                        age = Integer.parseInt(ageText);
                } catch (NumberFormatException e) {
                        showWarning("Please enter a valid age.");
                        return;
                }

                if (age < 1 || age > 120) {
                        showWarning("Age must be between 1 and 120.");
                        return;
                }

        List<String> users =
                FileHandler.readAll("users.txt");

        for (String line : users) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 2
                    && parts[1].equalsIgnoreCase(username)) {

                showWarning(
                        "Username already exists."
                );

                return;
            }
        }

        int newUserId =
                getNextId(users);

        String userType;

        if ("Applicant".equals(role)) {
            userType = "APPLICANT";
        } else {
            userType = "EMPLOYER";
        }

        String userRecord =
                newUserId
                + "|"
                + cleanText(username)
                + "|"
                + cleanText(password)
                + "|"
                + cleanText(fullName)
                + "|"
                + userType
                + "|"
                + age;

        FileHandler.appendLine(
                "users.txt",
                userRecord
        );

        JOptionPane.showMessageDialog(
                this,
                "Account created successfully!\n"
                + "Your User ID is: "
                + newUserId,
                "JobSync",
                JOptionPane.INFORMATION_MESSAGE
        );

        showLoginPanel();
    }

    // =====================================================
    // APPLICANT DASHBOARD
    // =====================================================

    private void showApplicantDashboard(
            Applicant applicant) {

        applyWorkspaceTheme();

        JButton profileButton =
                new JButton("My Profile");

        JButton resumeButton =
                new JButton("My Resume");

        JButton searchButton =
                new JButton("Search & Apply Jobs");

        JButton applicationsButton =
                new JButton("My Applications");

        JButton interviewsButton =
                new JButton("My Interviews");

        JButton logoutButton =
                new JButton("Logout");

        JPanel mainPanel = createDashboardPanel(
                "Applicant dashboard",
                "Everything you need for your next opportunity.",
                applicant.getFullName(),
                new JButton[]{
                        searchButton,
                        applicationsButton,
                        interviewsButton,
                        resumeButton,
                        profileButton,
                        logoutButton
                }
        );

        profileButton.addActionListener(
                e -> showUserProfile(applicant)
        );

        resumeButton.addActionListener(
                e -> showApplicantResumeEditor(applicant)
        );

        searchButton.addActionListener(
                e -> showSearchAndApplyJobs(applicant)
        );

        applicationsButton.addActionListener(
                e -> showApplications(applicant)
        );

        interviewsButton.addActionListener(
                e -> showInterviews(applicant)
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    // =====================================================
    // USER PROFILE
    // =====================================================

    private void showUserProfile(User user) {

        String message =
                "===== MY PROFILE =====\n\n"
                + "User ID: " + user.getUserId() + "\n"
                + "Full Name: " + user.getFullName() + "\n"
                + "Username: " + user.getUsername() + "\n"
                + "Age: " + (user.getAge() > 0
                        ? user.getAge() : "Not provided") + "\n"
                + "Account Type: "
                + (user instanceof Applicant ? "Applicant" : "Employer");

        JTextArea profileArea = new JTextArea(message);
        profileArea.setEditable(false);
        profileArea.setFont(normalFont);
        profileArea.setBackground(UIManager.getColor("Panel.background"));
        profileArea.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(profileArea, BorderLayout.CENTER);

        JButton editButton = new JButton("Edit");
        JButton okayButton = new JButton("Okay");

        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(editButton);
        buttonPanel.add(okayButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(this, "My Profile", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        styleWorkspaceTree(panel);
        dialog.add(panel);
        dialog.setSize(380, 280);
        dialog.setLocationRelativeTo(this);

        editButton.addActionListener(e -> {
                dialog.dispose();
                editUserProfile(user);
        });

        okayButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    // =====================================================
    // EDIT USER PROFILE
    // =====================================================


    private void editUserProfile(User user) {

        JTextField nameField =
                new JTextField(user.getFullName(), 20);

        JTextField usernameField =
                new JTextField(user.getUsername(), 20);

        JPasswordField newPasswordField =
                new JPasswordField(20);

        JPasswordField confirmPasswordField =
                new JPasswordField(20);

        JPanel panel = new JPanel(new GridLayout(8, 1, 5, 5));

        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("New Password (leave blank to keep current):"));
        panel.add(newPasswordField);

        panel.add(new JLabel("Confirm New Password:"));
        panel.add(confirmPasswordField);
        styleWorkspaceTree(panel);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Edit Profile",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
                return;
        }

        String newName = nameField.getText().trim();
        String newUsername = usernameField.getText().trim();
        String newPassword =
                new String(newPasswordField.getPassword());
        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        if (newName.isEmpty() || newUsername.isEmpty()) {
                showWarning("Full name and username cannot be empty.");
                return;
    }

        for (String line : FileHandler.readAll("users.txt")) {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 2
                    && !parts[0].equals(String.valueOf(user.getUserId()))
                    && parts[1].equalsIgnoreCase(newUsername)) {
                showWarning("Username already exists.");
                return;
            }
        }

    // Only change the password if the user entered one.
    if (!newPassword.isEmpty()) {

        if (newPassword.length() < 6) {
            showWarning("Password must be at least 6 characters long.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showWarning("Passwords do not match.");
            return;
        }

                user.setPassword(newPassword);
    }

        user.setFullName(newName);
        user.setUsername(newUsername);

        updateUserRecord(user);

    showMessage(
            "Profile updated successfully.",
            "JobSync"
    );

    // Refresh the dashboard to show the updated name.
        if (user instanceof Applicant) {
                showApplicantDashboard((Applicant) user);
        } else {
                showEmployerDashboard((Employer) user);
        }
}

    // =====================================================
    // RESUME EDITOR
    // =====================================================
 

    private void showApplicantResumeEditor(Applicant applicant) {

        Resume resume = applicant.getResume();

        JDialog dialog = new JDialog(this, "My Resume", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout(10, 10));

        // ==================== RESUME FIELDS ====================

        // Full Name
        JTextField fullNameField = new JTextField(
                resume != null
                        && resume.getFullName() != null
                        && !resume.getFullName().isEmpty()
                        ? resume.getFullName()
                        : applicant.getFullName()
        );

        // Profile Summary
        JTextArea summaryArea = new JTextArea(4, 30);
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);

        // Personal Information
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();

        // Education
        JTextArea educationArea = new JTextArea(4, 30);
        educationArea.setLineWrap(true);
        educationArea.setWrapStyleWord(true);

        // Skills
        JTextField skill1Field = new JTextField();
        JTextField skill2Field = new JTextField();
        JTextField skill3Field = new JTextField();
        JTextField skill4Field = new JTextField();
        JTextField skill5Field = new JTextField();

        // Experience
        JTextArea experienceArea = new JTextArea(5, 30);
        experienceArea.setLineWrap(true);
        experienceArea.setWrapStyleWord(true);

        // ==================== LOAD SAVED INFORMATION ====================

        if (resume != null) {
                emailField.setText(resume.getEmail());
                phoneField.setText(resume.getPhone());
                addressField.setText(resume.getLocation());

                summaryArea.setText(resume.getSummary());
                educationArea.setText(resume.getEducation());

                skill1Field.setText(resume.getSkill1());
                skill2Field.setText(resume.getSkill2());
                skill3Field.setText(resume.getSkill3());
                skill4Field.setText(resume.getSkill4());
                skill5Field.setText(resume.getSkill5());

                experienceArea.setText(resume.getExperienceDescription());
        }

        // ==================== MAIN FORM ====================

        JPanel formPanel = new JPanel();
        formPanel.setLayout(
                new BoxLayout(formPanel, BoxLayout.Y_AXIS)
        );
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        // Main heading
        JLabel titleLabel = new JLabel("RESUME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(18));

        // ==================== FULL NAME SECTION ====================

        JPanel namePanel = new JPanel(new BorderLayout(8, 8));
        namePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "FULL NAME"
        ));
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        fullNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        fullNameField.setPreferredSize(new Dimension(300, 35));

        namePanel.add(fullNameField, BorderLayout.CENTER);
        namePanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 75)
        );

        formPanel.add(namePanel);
        formPanel.add(Box.createVerticalStrut(15));

        // ==================== PROFILE SUMMARY ====================

        JPanel summaryPanel = new JPanel(new BorderLayout(8, 8));
        summaryPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "PROFILE SUMMARY"
        ));
        summaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPanel.add(
                new JScrollPane(summaryArea),
                BorderLayout.CENTER
        );
        summaryPanel.setPreferredSize(new Dimension(700, 130));
        summaryPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 150)
        );

        formPanel.add(summaryPanel);
        formPanel.add(Box.createVerticalStrut(12));

        // ==================== PERSONAL INFORMATION ====================

        JPanel personalPanel = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );
        personalPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "PERSONAL INFO"
        ));
        personalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        personalPanel.add(new JLabel("Mobile Number:"));
        personalPanel.add(phoneField);

        personalPanel.add(new JLabel("Address:"));
        personalPanel.add(addressField);

        personalPanel.add(new JLabel("Email:"));
        personalPanel.add(emailField);

        personalPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 150)
        );

        formPanel.add(personalPanel);
        formPanel.add(Box.createVerticalStrut(12));

        // ==================== EDUCATION ====================

        JPanel educationPanel = new JPanel(
                new BorderLayout(8, 8)
        );
        educationPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "EDUCATION"
        ));
        educationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        educationPanel.add(
                new JScrollPane(educationArea),
                BorderLayout.CENTER
        );
        educationPanel.setPreferredSize(new Dimension(700, 130));
        educationPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 150)
        );

        formPanel.add(educationPanel);
        formPanel.add(Box.createVerticalStrut(12));

        // ==================== SKILLS ====================

        JPanel skillsPanel = new JPanel(
                new GridLayout(5, 2, 10, 8)
        );
        skillsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "SKILLS"
        ));
        skillsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        skillsPanel.add(new JLabel("Skill 1:"));
        skillsPanel.add(skill1Field);

        skillsPanel.add(new JLabel("Skill 2:"));
        skillsPanel.add(skill2Field);

        skillsPanel.add(new JLabel("Skill 3:"));
        skillsPanel.add(skill3Field);

        skillsPanel.add(new JLabel("Skill 4:"));
        skillsPanel.add(skill4Field);

        skillsPanel.add(new JLabel("Skill 5:"));
        skillsPanel.add(skill5Field);

        skillsPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 200)
        );

        formPanel.add(skillsPanel);
        formPanel.add(Box.createVerticalStrut(12));

        // ==================== EXPERIENCE ====================

        JPanel experiencePanel = new JPanel(
                new BorderLayout(8, 8)
        );
        experiencePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "EXPERIENCE"
        ));
        experiencePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        experiencePanel.add(
                new JScrollPane(experienceArea),
                BorderLayout.CENTER
        );
        experiencePanel.setPreferredSize(new Dimension(700, 160));
        experiencePanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 180)
        );

        formPanel.add(experiencePanel);

        // ==================== SCROLLABLE FORM ====================

        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        formScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        formScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        dialog.add(formScrollPane, BorderLayout.CENTER);

        // ==================== BUTTONS ====================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 10)
        );

        JButton saveButton = new JButton("Save Resume");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // ==================== SAVE RESUME ====================

        saveButton.addActionListener(e -> {

                Resume newResume = new Resume(
                        cleanText(fullNameField.getText().trim()),
                        cleanText(emailField.getText().trim()),
                        cleanText(phoneField.getText().trim()),
                        cleanText(addressField.getText().trim()),
                        summaryArea.getText().trim(),
                        educationArea.getText().trim(),
                        cleanText(skill1Field.getText().trim()),
                        cleanText(skill2Field.getText().trim()),
                        cleanText(skill3Field.getText().trim()),
                        cleanText(skill4Field.getText().trim()),
                        cleanText(skill5Field.getText().trim()),
                        "",
                        "",
                        experienceArea.getText().trim()
                );

                applicant.setResume(newResume);
                updateUserRecord(applicant);

                dialog.dispose();
        });

        // Close without saving
        cancelButton.addActionListener(e -> dialog.dispose());

        // ==================== WINDOW SIZE ====================

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int width = Math.min(900, screenSize.width - 60);
        int height = Math.min(780, screenSize.height - 80);

        dialog.setMinimumSize(new Dimension(500, 450));
        dialog.setSize(width, height);
        dialog.setResizable(true);
        dialog.setLocationRelativeTo(this);
        styleWorkspaceTree(dialog.getContentPane());
        dialog.setVisible(true);
    }

    // =====================================================
    // SEARCH AND APPLY JOBS
    // =====================================================

    // The applicant searches first, then picks one of the matching jobs.
    // Cancelling the result list goes back to the search form;
    // cancelling the search form closes the screen.
    private void showSearchAndApplyJobs(
            Applicant applicant) {

        if (jobManager.getOpenJobs().isEmpty()) {

            showMessage(
                    "There are currently no open jobs.",
                    "Search Jobs"
            );

            return;
        }

        while (true) {

            ArrayList<JobPosting> matches =
                    askSearchCriteria();

            if (matches == null) {
                return;
            }

            if (matches.isEmpty()) {

                showMessage(
                        "No jobs matched your search.",
                        "Search Jobs"
                );

                continue;
            }

            JobPosting selectedJob =
                    chooseJob(matches);

            if (selectedJob == null) {
                continue;
            }

            showJobDetailsAndApply(
                    applicant,
                    selectedJob
            );

            return;
        }
    }

    // Shows the search form. Each "Search by" choice calls a different
    // version of search() in JobManager (method overloading).
    // Returns the matching jobs, or null if the applicant cancelled.
    private ArrayList<JobPosting> askSearchCriteria() {

        JComboBox<String> searchByBox =
                new JComboBox<>(
                        new String[]{
                                "Job Title",
                                "Job Title and Location",
                                "Minimum Monthly Pay",
                                "Show All Open Jobs"
                        }
                );

        JTextField titleField =
                new JTextField();

        JTextField locationField =
                new JTextField();

        JTextField payField =
                new JTextField();

        // Only the fields needed for the chosen search are enabled
        searchByBox.addActionListener(e -> {

            int mode =
                    searchByBox.getSelectedIndex();

            titleField.setEnabled(
                    mode == 0 || mode == 1
            );

            locationField.setEnabled(
                    mode == 1
            );

            payField.setEnabled(
                    mode == 2
            );
        });

        locationField.setEnabled(false);
        payField.setEnabled(false);

        JPanel panel =
                new JPanel(
                        new GridLayout(8, 1, 5, 5)
                );

        panel.add(new JLabel("Search by:"));
        panel.add(searchByBox);

        panel.add(new JLabel("Job Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Location:"));
        panel.add(locationField);

        panel.add(new JLabel("Minimum Monthly Pay (PHP):"));
        panel.add(payField);

        while (true) {

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            panel,
                            "Search Jobs",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

            if (result
                    != JOptionPane.OK_OPTION) {

                return null;
            }

            int mode =
                    searchByBox.getSelectedIndex();

            String title =
                    titleField.getText().trim();

            String location =
                    locationField.getText().trim();

            if (mode == 0) {

                if (title.isEmpty()) {

                    showWarning(
                            "Please enter a job title."
                    );

                    continue;
                }

                return jobManager.search(title);
            }

            if (mode == 1) {

                if (title.isEmpty()
                        || location.isEmpty()) {

                    showWarning(
                            "Please enter a job title and a location."
                    );

                    continue;
                }

                return jobManager.search(title, location);
            }

            if (mode == 2) {

                double minimumPay;

                try {

                    minimumPay =
                            Double.parseDouble(
                                    payField.getText().trim()
                            );

                    if (minimumPay < 0) {
                        throw new NumberFormatException();
                    }

                } catch (NumberFormatException e) {

                    showWarning(
                            "Please enter a valid minimum monthly pay."
                    );

                    continue;
                }

                return jobManager.search(minimumPay);
            }

            return jobManager.getOpenJobs();
        }
    }

    // Lets the applicant pick one job from the search results.
    // Returns null if the applicant cancelled.
    private JobPosting chooseJob(
            ArrayList<JobPosting> jobs) {

        String[] choices =
                new String[jobs.size()];

        for (int i = 0;
             i < jobs.size();
             i++) {

            JobPosting job =
                    jobs.get(i);

            choices[i] =
                    job.getJobId()
                    + " - "
                    + job.getTitle()
                    + " | "
                    + job.getLocation()
                    + " | "
                    + job.getJobType()
                    + " | PHP "
                    + String.format(
                            "%,.2f",
                            job.computeMonthlySalary()
                    )
                    + "/month";
        }

        JComboBox<String> jobBox =
                new JComboBox<>(choices);

        JPanel panel =
                new JPanel(
                        new GridLayout(2, 1, 5, 5)
                );

        panel.add(
                new JLabel(
                        "Found "
                        + jobs.size()
                        + " job(s). Select a job:"
                )
        );

        panel.add(jobBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Search Results",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return null;
        }

        return jobs.get(
                jobBox.getSelectedIndex()
        );
    }

    // =====================================================
    // JOB DETAILS AND APPLY
    // =====================================================

    // The details text comes from the job object itself, so a FullTimeJob
    // and a PartTimeJob each show their own type, pay and benefits.
    private void showJobDetailsAndApply(
            Applicant applicant,
            JobPosting job) {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "===== JOB DETAILS =====\n\n"
                        + job.getDetails()
                        + "\n\nWould you like to apply for this job?",
                        "Job Details",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice
                == JOptionPane.YES_OPTION) {

            applyForJob(
                    applicant,
                    job.getJobId()
            );
        }
    }

    // =====================================================
    // APPLY FOR JOB
    // =====================================================

    private void applyForJob(
            Applicant applicant,
            int jobId) {

        List<String> applications =
                FileHandler.readAll(
                        "applications.txt"
                );

        for (String line : applications) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 5) {

                try {

                    int applicantId =
                            Integer.parseInt(parts[1]);

                    int existingJobId =
                            Integer.parseInt(parts[2]);

                    if (applicantId
                            == applicant.getUserId()
                            && existingJobId
                            == jobId
                            && !parts[4].equalsIgnoreCase(
                                    "WITHDRAWN")) {

                        showWarning(
                                "You have already applied for this job."
                        );

                        return;
                    }

                } catch (NumberFormatException ignored) {
                }
            }
        }

        int applicationId =
                getNextId(applications);

        String applicationRecord =
                applicationId
                + "|"
                + applicant.getUserId()
                + "|"
                + jobId
                + "|"
                + LocalDate.now()
                + "|PENDING";

        FileHandler.appendLine(
                "applications.txt",
                applicationRecord
        );

        showMessage(
                "Your application has been submitted successfully!",
                "Application"
        );
    }

    // =====================================================
    // APPLICATIONS
    // =====================================================

    private void showApplications(
            Applicant applicant) {

        List<String> applications =
                FileHandler.readAll(
                        "applications.txt"
                );

        List<String> myApplications =
                new ArrayList<>();

        for (String line : applications) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 5) {

                try {

                    if (Integer.parseInt(parts[1])
                            == applicant.getUserId()) {

                        myApplications.add(line);
                    }

                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (myApplications.isEmpty()) {

            showMessage(
                    "You have no applications yet.",
                    "My Applications"
            );

            return;
        }

        String[] choices =
                new String[myApplications.size()];

        for (int i = 0;
             i < myApplications.size();
             i++) {

            String[] parts =
                    myApplications.get(i)
                            .split("\\|", -1);

            choices[i] =
                    "Application "
                    + parts[0]
                    + " | Job "
                    + parts[2]
                    + " | "
                    + parts[4];
        }

        JComboBox<String> applicationBox =
                new JComboBox<>(choices);

        JPanel panel =
                new JPanel(
                        new GridLayout(2, 1, 5, 5)
                );

        panel.add(
                new JLabel(
                        "Select an application:"
                )
        );

        panel.add(applicationBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "My Applications",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return;
        }

        String selected =
                myApplications.get(
                        applicationBox.getSelectedIndex()
                );

        String[] parts =
                selected.split("\\|", -1);

        String message =
                "===== APPLICATION =====\n\n"
                + "Application ID: "
                + parts[0]
                + "\n"
                + "Job ID: "
                + parts[2]
                + "\n"
                + "Application Date: "
                + parts[3]
                + "\n"
                + "Status: "
                + parts[4];

        if (parts[4].equalsIgnoreCase("PENDING")) {

            int withdraw =
                    JOptionPane.showConfirmDialog(
                            this,
                            message
                            + "\n\nDo you want to withdraw this application?",
                            "Application Details",
                            JOptionPane.YES_NO_OPTION
                    );

            if (withdraw
                    == JOptionPane.YES_OPTION) {

                withdrawApplication(
                        Integer.parseInt(parts[0])
                );
            }

        } else {

            showMessage(
                    message,
                    "Application Details"
            );
        }
    }

    // =====================================================
    // WITHDRAW APPLICATION
    // =====================================================

    private void withdrawApplication(
            int applicationId) {

        List<String> applications =
                FileHandler.readAll(
                        "applications.txt"
                );

        List<String> updated =
                new ArrayList<>();

        boolean changed = false;

        for (String line : applications) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 5) {

                try {

                    int id =
                            Integer.parseInt(parts[0]);

                    if (id == applicationId
                            && parts[4].equalsIgnoreCase(
                                    "PENDING")) {

                        parts[4] = "WITHDRAWN";

                        line = joinParts(parts);

                        changed = true;
                    }

                } catch (NumberFormatException ignored) {
                }
            }

            updated.add(line);
        }

        FileHandler.writeAll(
                "applications.txt",
                updated
        );

        if (changed) {

            showMessage(
                    "Application withdrawn successfully.",
                    "JobSync"
            );

        } else {

            showWarning(
                    "Application could not be withdrawn."
            );
        }
    }

    // =====================================================
    // APPLICANT INTERVIEWS
    // =====================================================

    private void showInterviews(
            Applicant applicant) {

        List<String> appointments =
                FileHandler.readAll(
                        "appointments.txt"
                );

        List<String> myAppointments =
                new ArrayList<>();

        for (String line : appointments) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 8) {

                try {

                    if (Integer.parseInt(parts[3])
                            == applicant.getUserId()) {

                        myAppointments.add(line);
                    }

                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (myAppointments.isEmpty()) {

            showMessage(
                    "You have no interview appointments.",
                    "My Interviews"
            );

            return;
        }

        String[] choices =
                new String[myAppointments.size()];

        for (int i = 0;
             i < myAppointments.size();
             i++) {

            String[] parts =
                    myAppointments.get(i)
                            .split("\\|", -1);

            choices[i] =
                    "Appointment "
                    + parts[0]
                    + " | "
                    + parts[4]
                    + " "
                    + parts[5]
                    + " | "
                    + parts[7];
        }

        JComboBox<String> appointmentBox =
                new JComboBox<>(choices);

        JPanel panel =
                new JPanel(
                        new GridLayout(2, 1, 5, 5)
                );

        panel.add(
                new JLabel(
                        "Select an interview:"
                )
        );

        panel.add(appointmentBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "My Interviews",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return;
        }

        String selected =
                myAppointments.get(
                        appointmentBox.getSelectedIndex()
                );

        String[] parts =
                selected.split("\\|", -1);

        String details =
                "===== INTERVIEW =====\n\n"
                + "Appointment ID: "
                + parts[0]
                + "\n"
                + "Application ID: "
                + parts[1]
                + "\n"
                + "Date: "
                + parts[4]
                + "\n"
                + "Time: "
                + parts[5]
                + "\n"
                + "Place: "
                + parts[6]
                + "\n"
                + "Status: "
                + parts[7];

        if (parts[7].equalsIgnoreCase("SCHEDULED")) {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            details
                            + "\n\nCancel this interview?",
                            "Interview Details",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice
                    == JOptionPane.YES_OPTION) {

                cancelAppointment(
                        Integer.parseInt(parts[0])
                );
            }

        } else {

            showMessage(
                    details,
                    "Interview Details"
            );
        }
    }

    // =====================================================
    // EMPLOYER DASHBOARD
    // =====================================================

    private void showEmployerDashboard(
            Employer employer) {

        applyWorkspaceTheme();

        JButton postJobButton =
                new JButton("Post Job");

        JButton myJobsButton =
                new JButton("My Jobs");

        JButton updateJobButton =
                new JButton("Update Job");

        JButton closeJobButton =
                new JButton("Close Job");

        JButton applicationsButton =
                new JButton("Applications");

        JButton reviewButton =
                new JButton("Review Application");

        JButton interviewButton =
                new JButton("Schedule Interview");

        JButton profileButton =
                new JButton("My Profile");

        JButton logoutButton =
                new JButton("Logout");

        JPanel mainPanel = createDashboardPanel(
                "Employer dashboard",
                "Manage your roles and meet your next great hire.",
                employer.getFullName(),
                new JButton[]{
                        postJobButton,
                        myJobsButton,
                        applicationsButton,
                        reviewButton,
                        interviewButton,
                        updateJobButton,
                        closeJobButton,
                        profileButton,
                        logoutButton
                }
        );

        postJobButton.addActionListener(
                e -> postJob(employer)
        );

        myJobsButton.addActionListener(
                e -> showEmployerJobs(employer)
        );

        updateJobButton.addActionListener(
                e -> updateJob(employer)
        );

        closeJobButton.addActionListener(
                e -> closeJob(employer)
        );

        applicationsButton.addActionListener(
                e -> showEmployerApplications(employer)
        );

        reviewButton.addActionListener(
                e -> reviewApplication(employer)
        );

        interviewButton.addActionListener(
                e -> manageInterview(employer)
        );

        profileButton.addActionListener(
                e -> showUserProfile(employer)
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    // =====================================================
    // POST JOB
    // =====================================================

    private void postJob(
            Employer employer) {

        JTextField titleField =
                new JTextField();

        JTextField locationField =
                new JTextField();

        JTextField salaryField =
                new JTextField();

        JTextField descriptionField =
                new JTextField();

        JTextField skillsField =
                new JTextField();

        JComboBox<String> typeBox =
                new JComboBox<>(
                        new String[]{
                                "FULL-TIME",
                                "PART-TIME"
                        }
                );

        JTextField hoursField =
                new JTextField("160");

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                14,
                                1,
                                5,
                                5
                        )
                );

        panel.add(
                new JLabel("Job Title:")
        );
        panel.add(titleField);

        panel.add(
                new JLabel("Location:")
        );
        panel.add(locationField);

        panel.add(
                new JLabel("Salary:")
        );
        panel.add(salaryField);

        panel.add(
                new JLabel("Description:")
        );
        panel.add(descriptionField);

        panel.add(
                new JLabel("Required Skills:")
        );
        panel.add(skillsField);

        panel.add(
                new JLabel("Job Type:")
        );
        panel.add(typeBox);

        panel.add(
                new JLabel("Hours per Month:")
        );
        panel.add(hoursField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Post New Job",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return;
        }

        String title =
                titleField.getText().trim();

        String location =
                locationField.getText().trim();

        String description =
                descriptionField.getText().trim();

        String skills =
                skillsField.getText().trim();

        if (title.isEmpty()
                || location.isEmpty()
                || description.isEmpty()
                || skills.isEmpty()) {

            showWarning(
                    "Please complete all job fields."
            );

            return;
        }

        double salary;
        double hours;

        try {

            salary =
                    Double.parseDouble(
                            salaryField.getText().trim()
                    );

            hours =
                    Double.parseDouble(
                            hoursField.getText().trim()
                    );

            if (salary < 0 || hours <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            showWarning(
                    "Please enter valid salary and hours."
            );

            return;
        }

        int jobId =
                jobManager.getNextJobId();

        String type =
                (String) typeBox.getSelectedItem();

        // The job type decides which class is created.
        // Both are used through the JobPosting type (polymorphism).
        JobPosting job;

        if ("PART-TIME".equals(type)) {

            job = new PartTimeJob(
                    jobId,
                    employer.getUserId(),
                    title,
                    location,
                    salary,
                    description,
                    skills,
                    hours
            );

        } else {

            job = new FullTimeJob(
                    jobId,
                    employer.getUserId(),
                    title,
                    location,
                    salary,
                    description,
                    skills,
                    hours
            );
        }

        jobManager.addJob(job);

        showMessage(
                "Job posted successfully!\nJob ID: "
                + jobId
                + "\nType: "
                + job.getJobType()
                + "\nEstimated Monthly Pay: "
                + JobPosting.formatMoney(
                        job.computeMonthlySalary()
                ),
                "Post Job"
        );
    }

    // =====================================================
    // EMPLOYER JOBS
    // =====================================================

    private void showEmployerJobs(
            Employer employer) {

        ArrayList<JobPosting> myJobs =
                jobManager.getJobsByEmployer(
                        employer.getUserId(),
                        false
                );

        if (myJobs.isEmpty()) {

            showMessage(
                    "You have not posted any jobs yet.",
                    "My Jobs"
            );

            return;
        }

        StringBuilder result =
                new StringBuilder();

        result.append(
                "===== MY JOB POSTS =====\n\n"
        );

        for (JobPosting job : myJobs) {

            result.append(job.getDetails());
            result.append("\n");
            result.append(
                    "-----------------------------\n"
            );
        }

        showTextDialog(
                "My Jobs",
                result.toString()
        );
    }

    // =====================================================
    // UPDATE JOB
    // =====================================================

    private void updateJob(
            Employer employer) {

        ArrayList<JobPosting> myOpenJobs =
                jobManager.getJobsByEmployer(
                        employer.getUserId(),
                        true
                );

        if (myOpenJobs.isEmpty()) {

            showMessage(
                    "You have no open jobs to update.",
                    "Update Job"
            );

            return;
        }

        String[] choices =
                new String[myOpenJobs.size()];

        for (int i = 0;
             i < myOpenJobs.size();
             i++) {

            JobPosting job =
                    myOpenJobs.get(i);

            choices[i] =
                    job.getJobId()
                    + " - "
                    + job.getTitle();
        }

        JComboBox<String> jobBox =
                new JComboBox<>(choices);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        jobBox,
                        "Select Job to Update",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return;
        }

        JobPosting job =
                myOpenJobs.get(
                        jobBox.getSelectedIndex()
                );

        JTextField titleField =
                new JTextField(job.getTitle());

        JTextField locationField =
                new JTextField(job.getLocation());

        JTextField salaryField =
                new JTextField(
                        String.valueOf(job.getSalary())
                );

        JTextField descriptionField =
                new JTextField(job.getDescription());

        JTextField skillsField =
                new JTextField(job.getRequiredSkills());

        JTextField hoursField =
                new JTextField(
                        String.valueOf(job.getHoursPerMonth())
                );

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                12,
                                1,
                                5,
                                5
                        )
                );

        panel.add(new JLabel("Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Location:"));
        panel.add(locationField);

        panel.add(
                new JLabel("Salary (full-time: per month, part-time: per hour):")
        );
        panel.add(salaryField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        panel.add(new JLabel("Required Skills:"));
        panel.add(skillsField);

        panel.add(new JLabel("Hours per Month:"));
        panel.add(hoursField);

        int save =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Update Job",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (save
                != JOptionPane.OK_OPTION) {

            return;
        }

        String title =
                titleField.getText().trim();

        String location =
                locationField.getText().trim();

        String description =
                descriptionField.getText().trim();

        String skills =
                skillsField.getText().trim();

        if (title.isEmpty()
                || location.isEmpty()
                || description.isEmpty()
                || skills.isEmpty()) {

            showWarning(
                    "Please complete all job fields."
            );

            return;
        }

        double salary;
        double hours;

        try {

            salary =
                    Double.parseDouble(
                            salaryField.getText().trim()
                    );

            hours =
                    Double.parseDouble(
                            hoursField.getText().trim()
                    );

            if (salary < 0 || hours <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            showWarning(
                    "Please enter valid salary and hours."
            );

            return;
        }

        job.setTitle(title);
        job.setLocation(location);
        job.setSalary(salary);
        job.setDescription(description);
        job.setRequiredSkills(skills);
        job.setHoursPerMonth(hours);

        jobManager.updateJob(job);

        showMessage(
                "Job updated successfully.",
                "Update Job"
        );
    }

    // =====================================================
    // CLOSE JOB
    // =====================================================

    private void closeJob(
            Employer employer) {

        ArrayList<JobPosting> myOpenJobs =
                jobManager.getJobsByEmployer(
                        employer.getUserId(),
                        true
                );

        if (myOpenJobs.isEmpty()) {

            showMessage(
                    "You have no open jobs to close.",
                    "Close Job"
            );

            return;
        }

        String[] choices =
                new String[myOpenJobs.size()];

        for (int i = 0;
             i < myOpenJobs.size();
             i++) {

            JobPosting job =
                    myOpenJobs.get(i);

            choices[i] =
                    job.getJobId()
                    + " - "
                    + job.getTitle();
        }

        JComboBox<String> jobBox =
                new JComboBox<>(choices);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        jobBox,
                        "Select Job to Close",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return;
        }

        JobPosting job =
                myOpenJobs.get(
                        jobBox.getSelectedIndex()
                );

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Close job: "
                        + job.getTitle()
                        + "?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm
                != JOptionPane.YES_OPTION) {

            return;
        }

        jobManager.deleteJob(job.getJobId());

        showMessage(
                "Job closed successfully.",
                "Close Job"
        );
    }

    // =====================================================
    // EMPLOYER APPLICATIONS
    // =====================================================

    private void showEmployerApplications(
            Employer employer) {

        List<String> applications =
                getEmployerApplications(employer);

        if (applications.isEmpty()) {

            showMessage(
                    "There are no applications for your jobs.",
                    "Applications"
            );

            return;
        }

        StringBuilder result =
                new StringBuilder();

        result.append(
                "===== APPLICATIONS =====\n\n"
        );

        for (String line : applications) {

            String[] parts =
                    line.split("\\|", -1);

            String applicantName =
                    getUserName(
                            Integer.parseInt(parts[1])
                    );

            String jobTitle =
                    getJobTitle(
                            Integer.parseInt(parts[2])
                    );

            result.append(
                    "Application ID: "
                    + parts[0]
                    + "\n"
            );

            result.append(
                    "Applicant: "
                    + applicantName
                    + "\n"
            );

            result.append(
                    "Job: "
                    + jobTitle
                    + "\n"
            );

            result.append(
                    "Date: "
                    + parts[3]
                    + "\n"
            );

            result.append(
                    "Status: "
                    + parts[4]
                    + "\n"
            );

            result.append(
                    "-----------------------------\n"
            );
        }

        showTextDialog(
                "Applications",
                result.toString()
        );
    }

    // =====================================================
    // REVIEW APPLICATION
    // =====================================================

    private void reviewApplication(
            Employer employer) {

        List<String> applications =
                getEmployerApplications(employer);

        List<String> pending =
                new ArrayList<>();

        for (String line : applications) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 5
                    && parts[4].equalsIgnoreCase("PENDING")) {

                pending.add(line);
            }
        }

        if (pending.isEmpty()) {

            showMessage(
                    "There are no pending applications.",
                    "Review Application"
            );

            return;
        }

        String[] choices =
                new String[pending.size()];

        for (int i = 0;
             i < pending.size();
             i++) {

            String[] parts =
                    pending.get(i)
                            .split("\\|", -1);

            choices[i] =
                    "Application "
                    + parts[0]
                    + " - "
                    + getUserName(
                            Integer.parseInt(parts[1])
                    )
                    + " - Job "
                    + parts[2];
        }

        JComboBox<String> applicationBox =
                new JComboBox<>(choices);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        applicationBox,
                        "Review Application",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return;
        }

        String selected =
                pending.get(
                        applicationBox.getSelectedIndex()
                );

        String[] parts =
                selected.split("\\|", -1);

        String applicantName =
                getUserName(
                        Integer.parseInt(parts[1])
                );

        String jobTitle =
                getJobTitle(
                        Integer.parseInt(parts[2])
                );

        Object[] options = {
                "ACCEPT",
                "REJECT",
                "CANCEL"
        };

        int choice =
                JOptionPane.showOptionDialog(
                        this,
                        "Applicant: "
                        + applicantName
                        + "\nJob: "
                        + jobTitle
                        + "\n\nChoose a decision:",
                        "Review Application",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        if (choice == 0) {

            updateApplicationStatus(
                    Integer.parseInt(parts[0]),
                    "ACCEPTED"
            );

            showMessage(
                    "Application accepted.",
                    "Review Application"
            );

        } else if (choice == 1) {

            updateApplicationStatus(
                    Integer.parseInt(parts[0]),
                    "REJECTED"
            );

            showMessage(
                    "Application rejected.",
                    "Review Application"
            );
        }
    }

    // =====================================================
    // SCHEDULE / MANAGE INTERVIEW
    // =====================================================

    private void manageInterview(
            Employer employer) {

        List<String> accepted =
                getEmployerApplications(employer);

        List<String> acceptedApplications =
                new ArrayList<>();

        for (String line : accepted) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 5
                    && parts[4].equalsIgnoreCase("ACCEPTED")) {

                acceptedApplications.add(line);
            }
        }

        if (acceptedApplications.isEmpty()) {

            showMessage(
                    "There are no accepted applications ready for interview scheduling.",
                    "Schedule Interview"
            );

            return;
        }

        String[] choices =
                new String[
                        acceptedApplications.size()
                ];

        for (int i = 0;
             i < acceptedApplications.size();
             i++) {

            String[] parts =
                    acceptedApplications.get(i)
                            .split("\\|", -1);

            choices[i] =
                    "Application "
                    + parts[0]
                    + " - "
                    + getUserName(
                            Integer.parseInt(parts[1])
                    )
                    + " - "
                    + getJobTitle(
                            Integer.parseInt(parts[2])
                    );
        }

        JComboBox<String> applicationBox =
                new JComboBox<>(choices);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        applicationBox,
                        "Select Application",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return;
        }

        String application =
                acceptedApplications.get(
                        applicationBox.getSelectedIndex()
                );

        String[] appParts =
                application.split("\\|", -1);

        int applicationId =
                Integer.parseInt(appParts[0]);

        manageSelectedInterview(
                employer,
                applicationId,
                Integer.parseInt(appParts[1])
        );
    }

    // =====================================================
    // SELECTED INTERVIEW
    // =====================================================

    private void manageSelectedInterview(
            Employer employer,
            int applicationId,
            int applicantId) {

        List<String> appointments =
                FileHandler.readAll(
                        "appointments.txt"
                );

        String existingAppointment = null;

        for (String line : appointments) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 8) {

                try {

                    if (Integer.parseInt(parts[1])
                            == applicationId
                            && !parts[7].equalsIgnoreCase(
                                    "CANCELLED")) {

                        existingAppointment = line;
                        break;
                    }

                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (existingAppointment != null) {

            String[] parts =
                    existingAppointment.split("\\|", -1);

            String[] options = {
                    "RESCHEDULE",
                    "CANCEL",
                    "VIEW",
                    "BACK"
            };

            int choice =
                    JOptionPane.showOptionDialog(
                            this,
                            "Interview already exists.\n\n"
                            + "Date: "
                            + parts[4]
                            + "\n"
                            + "Time: "
                            + parts[5]
                            + "\n"
                            + "Place: "
                            + parts[6]
                            + "\n"
                            + "Status: "
                            + parts[7],
                            "Interview",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );

            if (choice == 0) {

                createOrUpdateAppointment(
                        Integer.parseInt(parts[0]),
                        applicationId,
                        employer.getUserId(),
                        applicantId
                );

            } else if (choice == 1) {

                cancelAppointment(
                        Integer.parseInt(parts[0])
                );

            } else if (choice == 2) {

                showMessage(
                        "Date: "
                        + parts[4]
                        + "\nTime: "
                        + parts[5]
                        + "\nPlace: "
                        + parts[6]
                        + "\nStatus: "
                        + parts[7],
                        "Interview"
                );
            }

            return;
        }

        createOrUpdateAppointment(
                -1,
                applicationId,
                employer.getUserId(),
                applicantId
        );
    }

    // =====================================================
    // CREATE / UPDATE APPOINTMENT
    // =====================================================

    private void createOrUpdateAppointment(
            int appointmentId,
            int applicationId,
            int employerId,
            int applicantId) {

        JTextField dateField =
                new JTextField(
                        LocalDate.now()
                                .plusDays(1)
                                .toString()
                );

        JTextField timeField =
                new JTextField("09:00 AM");

        JTextField placeField =
                new JTextField("Employer Office");

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                6,
                                1,
                                5,
                                5
                        )
                );

        panel.add(
                new JLabel("Interview Date (YYYY-MM-DD):")
        );

        panel.add(dateField);

        panel.add(
                new JLabel("Interview Time:")
        );

        panel.add(timeField);

        panel.add(
                new JLabel("Interview Place:")
        );

        panel.add(placeField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        appointmentId == -1
                                ? "Schedule Interview"
                                : "Reschedule Interview",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result
                != JOptionPane.OK_OPTION) {

            return;
        }

        String date =
                dateField.getText().trim();

        String time =
                timeField.getText().trim();

        String place =
                placeField.getText().trim();

        if (date.isEmpty()
                || time.isEmpty()
                || place.isEmpty()) {

            showWarning(
                    "Please complete all interview fields."
            );

            return;
        }

        List<String> appointments =
                FileHandler.readAll(
                        "appointments.txt"
                );

        if (appointmentId == -1) {

            int newId =
                    getNextId(appointments);

            String record =
                    newId
                    + "|"
                    + applicationId
                    + "|"
                    + employerId
                    + "|"
                    + applicantId
                    + "|"
                    + cleanText(date)
                    + "|"
                    + cleanText(time)
                    + "|"
                    + cleanText(place)
                    + "|SCHEDULED";

            FileHandler.appendLine(
                    "appointments.txt",
                    record
            );

            updateApplicationStatus(
                    applicationId,
                    "INTERVIEW"
            );

            showMessage(
                    "Interview scheduled successfully.",
                    "Interview"
            );

        } else {

            List<String> updated =
                    new ArrayList<>();

            for (String line : appointments) {

                String[] parts =
                        line.split("\\|", -1);

                if (parts.length >= 8) {

                    try {

                        if (Integer.parseInt(parts[0])
                                == appointmentId) {

                            parts[4] =
                                    cleanText(date);

                            parts[5] =
                                    cleanText(time);

                            parts[6] =
                                    cleanText(place);

                            parts[7] =
                                    "SCHEDULED";

                            line =
                                    joinParts(parts);
                        }

                    } catch (NumberFormatException ignored) {
                    }
                }

                updated.add(line);
            }

            FileHandler.writeAll(
                    "appointments.txt",
                    updated
            );

            showMessage(
                    "Interview rescheduled successfully.",
                    "Interview"
            );
        }
    }

    // =====================================================
    // CANCEL APPOINTMENT
    // =====================================================

    private void cancelAppointment(
            int appointmentId) {

        List<String> appointments =
                FileHandler.readAll(
                        "appointments.txt"
                );

        List<String> updated =
                new ArrayList<>();

        boolean changed = false;

        for (String line : appointments) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 8) {

                try {

                    if (Integer.parseInt(parts[0])
                            == appointmentId) {

                        parts[7] = "CANCELLED";

                        line =
                                joinParts(parts);

                        changed = true;
                    }

                } catch (NumberFormatException ignored) {
                }
            }

            updated.add(line);
        }

        FileHandler.writeAll(
                "appointments.txt",
                updated
        );

        if (changed) {

            showMessage(
                    "Interview cancelled.",
                    "Interview"
            );
        }
    }

    // =====================================================
    // GET EMPLOYER APPLICATIONS
    // =====================================================

    private List<String> getEmployerApplications(
            Employer employer) {

        List<String> applications =
                FileHandler.readAll(
                        "applications.txt"
                );

        // IDs of every job posted by this employer
        List<Integer> myJobIds =
                new ArrayList<>();

        for (JobPosting job
                : jobManager.getJobsByEmployer(
                        employer.getUserId(),
                        false)) {

            myJobIds.add(job.getJobId());
        }

        List<String> result =
                new ArrayList<>();

        for (String application : applications) {

            String[] appParts =
                    application.split("\\|", -1);

            if (appParts.length < 5) {
                continue;
            }

            try {

                int jobId =
                        Integer.parseInt(
                                appParts[2]
                        );

                if (myJobIds.contains(jobId)) {

                    result.add(application);
                }

            } catch (NumberFormatException ignored) {
            }
        }

        return result;
    }

    // =====================================================
    // UPDATE APPLICATION STATUS
    // =====================================================

    private void updateApplicationStatus(
            int applicationId,
            String newStatus) {

        List<String> applications =
                FileHandler.readAll(
                        "applications.txt"
                );

        List<String> updated =
                new ArrayList<>();

        for (String line : applications) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 5) {

                try {

                    if (Integer.parseInt(parts[0])
                            == applicationId) {

                        parts[4] =
                                newStatus;

                        line =
                                joinParts(parts);
                    }

                } catch (NumberFormatException ignored) {
                }
            }

            updated.add(line);
        }

        FileHandler.writeAll(
                "applications.txt",
                updated
        );
    }

    // =====================================================
    // GET JOB TITLE
    // =====================================================

    private String getJobTitle(
            int jobId) {

        JobPosting job =
                jobManager.findJob(jobId);

        if (job == null) {
            return "Unknown Job";
        }

        return job.getTitle();
    }

    // =====================================================
    // GET USER NAME
    // =====================================================

    private String getUserName(
            int userId) {

        List<String> users =
                FileHandler.readAll(
                        "users.txt"
                );

        for (String line : users) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 4) {

                try {

                    if (Integer.parseInt(parts[0])
                            == userId) {

                        return parts[3];
                    }

                } catch (NumberFormatException ignored) {
                }
            }
        }

        return "Unknown User";
    }

    // =====================================================
    // UPDATE USER RECORD
    // =====================================================

    private void updateUserRecord(
            User user) {

        List<String> users =
                FileHandler.readAll(
                        "users.txt"
                );

        List<String> updated =
                new ArrayList<>();

        for (String line : users) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length >= 5) {

                try {

                    int id =
                            Integer.parseInt(parts[0]);

                    if (id == user.getUserId()) {

                        if (user instanceof Applicant) {

                            Applicant applicant =
                                    (Applicant) user;

                            StringBuilder record =
                                    new StringBuilder();

                            record.append(
                                    user.getUserId()
                            );

                            record.append("|");
                            record.append(
                                    cleanText(
                                            user.getUsername()
                                    )
                            );

                            record.append("|");
                            record.append(
                                    cleanText(
                                            user.getPassword()
                                    )
                            );

                            record.append("|");
                            record.append(
                                    cleanText(
                                            user.getFullName()
                                    )
                            );

                            record.append(
                                    "|APPLICANT"
                            );

                            Resume resume =
                                    applicant.getResume();


                                if (resume != null) {

                                record.append("|").append(cleanText(resume.getFullName()));
                                record.append("|").append(cleanText(resume.getEmail()));
                                record.append("|").append(cleanText(resume.getPhone()));
                                record.append("|").append(cleanText(resume.getLocation()));
                                record.append("|").append(encodeMultilineText(resume.getSummary()));
                                record.append("|").append(encodeMultilineText(resume.getEducation()));

                                record.append("|").append(cleanText(resume.getSkill1()));
                                record.append("|").append(cleanText(resume.getSkill2()));
                                record.append("|").append(cleanText(resume.getSkill3()));
                                record.append("|").append(cleanText(resume.getSkill4()));
                                record.append("|").append(cleanText(resume.getSkill5()));

                                record.append("|").append(cleanText(resume.getExperienceTitle()));
                                record.append("|").append(cleanText(resume.getOrganization()));
                                record.append("|").append(encodeMultilineText(resume.getExperienceDescription()));
                                }

                                                        record.append("|").append(user.getAge());

                            line =
                                    record.toString();

                        } else {

                            line =
                                    user.getUserId()
                                    + "|"
                                    + cleanText(
                                            user.getUsername()
                                    )
                                    + "|"
                                    + cleanText(
                                            user.getPassword()
                                    )
                                    + "|"
                                    + cleanText(
                                            user.getFullName()
                                    )
                                    + "|EMPLOYER"
                                    + "|"
                                    + user.getAge();
                        }
                    }

                } catch (NumberFormatException ignored) {
                }
            }

            updated.add(line);
        }

        FileHandler.writeAll(
                "users.txt",
                updated
        );
    }

    // =====================================================
    // NEXT ID
    // =====================================================

    private int getNextId(
            List<String> records) {

        int highestId = 0;

        for (String line : records) {

            String[] parts =
                    line.split("\\|", -1);

            if (parts.length == 0) {
                continue;
            }

            try {

                int id =
                        Integer.parseInt(
                                parts[0]
                        );

                if (id > highestId) {
                    highestId = id;
                }

            } catch (NumberFormatException ignored) {
            }
        }

        return highestId + 1;
    }

    // =====================================================
    // JOIN FILE RECORD
    // =====================================================

    private String joinParts(
            String[] parts) {

        StringBuilder result =
                new StringBuilder();

        for (int i = 0;
             i < parts.length;
             i++) {

            if (i > 0) {
                result.append("|");
            }

            result.append(
                    cleanText(parts[i])
            );
        }

        return result.toString();
    }

    // =====================================================
    // CLEAN TEXT FOR TXT FILE
    // =====================================================

    private String cleanText(
            String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("|", "/")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    // =====================================================
    // GENERAL MESSAGE
    // =====================================================

    private void showMessage(
            String message,
            String title) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // WARNING
    // =====================================================

    private void showWarning(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "JobSync",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // =====================================================
    // ERROR
    // =====================================================

    private void showError(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "JobSync Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // =====================================================
    // LOGOUT
    // =====================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice
                == JOptionPane.YES_OPTION) {

            currentUser = null;
            restoreLoginTheme();

            showLoginPanel();
        }
    }

    // =====================================================
    // TEXT DIALOG
    // =====================================================

        private void showTextDialog(
                String title,
                String text) {
 
        JTextArea textArea =
                new JTextArea(text);

        textArea.setEditable(false);
        textArea.setFont(normalFont);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        showResizableDialog(
                title,
                scrollPane,
                500,
                300
        );
    }

    private void showResizableDialog(
                String title,
                JComponent component,
                int width,
                int height) {

        JDialog dialog = new JDialog(
                this,
                title,
                true
        );

        dialog.setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        dialog.setResizable(true);

        dialog.add(component);
        styleWorkspaceTree(dialog.getContentPane());

        dialog.setSize(width, height);

        dialog.setLocationRelativeTo(this);

        dialog.setVisible(true);
     }

     
     private String encodeMultilineText(String text) {
        if (text == null) {
                text = "";
        }

        String safeText = text.replace("|", "/");

        return "B64:" + Base64.getEncoder().encodeToString(
                safeText.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String decodeMultilineText(String text) {
        if (text == null || !text.startsWith("B64:")) {
                // Supports older resume records
                return text == null ? "" : text;
        }

        try {
                byte[] decodedBytes = Base64.getDecoder().decode(
                        text.substring(4)
                );

                return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
                return text;
        }
    }
}