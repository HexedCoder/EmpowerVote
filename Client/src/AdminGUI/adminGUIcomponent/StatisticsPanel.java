package AdminGUI.adminGUIcomponent;

import AdminGUI.adminGUIchart.ModelPolarArea;
import EmpowerVoteClient.LanguageManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;

/**
 * A panel displaying various election statistics using buttons for different panels
 * and a PolarAreaChart to display results in chart form.
 */
public class StatisticsPanel extends javax.swing.JPanel implements LanguageManager.LanguageChangeListener {

    // Panel variables
    private MayorPanel mayorPanel;
    private CouncilPanel councilPanel;
    private GovernorPanel governorPanel;
    private SenatorPanel senatorPanel;
    private PresidentPanel presidentPanel;
    private CongressPanel congressPanel;

    // Chart variables
    private AdminGUI.adminGUIchart.PolarAreaChart1 polarAreaChart1;

    // UI components
    private javax.swing.JButton mayorButton;
    private javax.swing.JButton councilButton;
    private javax.swing.JButton governorButton;
    private javax.swing.JButton senatorButton;
    private javax.swing.JButton presidentButton;
    private javax.swing.JButton congressButton;

    // Stores the current chart title for language updates
    private String currentChartTitle = "";

    /**
     * Constructor to initialize the components and register for language updates.
     */
    public StatisticsPanel() {
        initComponents();
        LanguageManager.getInstance().addListener(this); // Register for language updates
        updateText(LanguageManager.getInstance().getLanguageIndex()); // Set initial text
    } // End StatisticsPanel

    /**
     * Updates the text of UI components based on the selected language.
     *
     * @param languageIndex The index representing the selected language.
     */
    private void updateText(int languageIndex) {
        // Language-specific translations
        String[][] texts = {
            // English
            { "Mayor", "City Council", "Governor", "Senator", "President", "Congress" },
            // Spanish
            { "Alcalde", "Concejo Municipal", "Gobernador", "Senador", "Presidente", "Congreso" },
            // Russian
            { "Мэр", "Городской совет", "Губернатор", "Сенатор", "Президент", "Конгресс" }
        };

        // Default chart title translations.
        String[] defaultChartTitles = { "Elections", "Elecciones", "Выборы" };

        // Update button text
        mayorButton.setText(texts[languageIndex][0]);
        councilButton.setText(texts[languageIndex][1]);
        governorButton.setText(texts[languageIndex][2]);
        senatorButton.setText(texts[languageIndex][3]);
        presidentButton.setText(texts[languageIndex][4]);
        congressButton.setText(texts[languageIndex][5]);

        // Update the chart title.
        // If a chart title has been set and it matches one of our predefined button texts, translate it.
        if (!currentChartTitle.isEmpty()) {
            int index = -1;
            for (int i = 0; i < texts[0].length; i++) {
                if (currentChartTitle.equals(texts[0][i]) ||
                    currentChartTitle.equals(texts[1][i]) ||
                    currentChartTitle.equals(texts[2][i])) {
                    index = i;
                    break;
                }
            }
        if (index != -1) {
            polarAreaChart1.setTitle(texts[languageIndex][index]);
            currentChartTitle = texts[languageIndex][index]; // update stored title
        } else {
            // If the current title doesn't match any button title,
            // assume it's the default "Elections" and update accordingly.
            polarAreaChart1.setTitle(defaultChartTitles[languageIndex]);
            currentChartTitle = defaultChartTitles[languageIndex];
            }
        } else {
            // No chart title has been set yet, so set the default title.
            polarAreaChart1.setTitle(defaultChartTitles[languageIndex]);
            currentChartTitle = defaultChartTitles[languageIndex];
            }
    } // End onLanguageChange

    /**
     * Sets the chart data based on the selected panel.
     *
     * @param title The title of the chart.
     * @param panel The panel containing the election data.
     */
    private void setChartData(String title, Object panel) {
        polarAreaChart1.clear();

        if (panel instanceof MayorPanel) {
            MayorPanel p = (MayorPanel) panel;
            polarAreaChart1.addItem(new ModelPolarArea(new Color(170,45,45), p.getCandidate1(), p.getCandidate1Votes()));
            polarAreaChart1.addItem(new ModelPolarArea(new Color(190,135,135), p.getCandidate2(), p.getCandidate2Votes()));
            polarAreaChart1.addItem(new ModelPolarArea(new Color(90,4,4), p.getCandidate3(), p.getCandidate3Votes()));
        } else if (panel instanceof CouncilPanel) {
            CouncilPanel p = (CouncilPanel) panel;
            polarAreaChart1.addItem(new ModelPolarArea(new Color(25,35,220), p.getCandidate1(), p.getCandidate1Votes()));
            polarAreaChart1.addItem(new ModelPolarArea(new Color(114,118,195), p.getCandidate2(), p.getCandidate2Votes()));
            polarAreaChart1.addItem(new ModelPolarArea(new Color(87,218,237), p.getCandidate3(), p.getCandidate3Votes()));
        }
        // Add more else-if cases for other panels...

        polarAreaChart1.setTitle(title);
        currentChartTitle = title; // Store for language updates
        polarAreaChart1.start();
    } // End setChartData

    /**
     * Sets the MayorPanel for statistics display.
     *
     * @param mayorPanel The MayorPanel instance.
     */
    public void setStatisticsPanel(MayorPanel mayorPanel) {
        this.mayorPanel = mayorPanel;
    } // End setStatisticsPanel(MayorPanel)

    /**
     * Sets the CouncilPanel for statistics display.
     *
     * @param councilPanel The CouncilPanel instance.
     */
    public void setStatisticsPanel(CouncilPanel councilPanel) {
        this.councilPanel = councilPanel;
    } // End setStatisticsPanel(CouncilPanel)

    /**
     * Sets the GovernorPanel for statistics display.
     *
     * @param governorPanel The GovernorPanel instance.
     */
    public void setStatisticsPanel(GovernorPanel governorPanel) {
        this.governorPanel = governorPanel;
    } // End setStatisticsPanel(GovernorPanel)

    /**
     * Sets the SenatorPanel for statistics display.
     *
     * @param senatorPanel The SenatorPanel instance.
     */
    public void setStatisticsPanel(SenatorPanel senatorPanel) {
        this.senatorPanel = senatorPanel;
    } // End setStatisticsPanel(SenatorPanel)

    /**
     * Sets the PresidentPanel for statistics display.
     *
     * @param presidentPanel The PresidentPanel instance.
     */
    public void setStatisticsPanel(PresidentPanel presidentPanel) {
        this.presidentPanel = presidentPanel;
    } // End setStatisticsPanel(PresidentPanel)

    /**
     * Sets the CongressPanel for statistics display.
     *
     * @param congressPanel The CongressPanel instance.
     */
    public void setStatisticsPanel(CongressPanel congressPanel) {
        this.congressPanel = congressPanel;
    } // End setStatisticsPanel(CongressPanel)

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mayorButton = new javax.swing.JButton();
        polarAreaChart1 = new AdminGUI.adminGUIchart.PolarAreaChart1();
        councilButton = new javax.swing.JButton();
        governorButton = new javax.swing.JButton();
        presidentButton = new javax.swing.JButton();
        congressButton = new javax.swing.JButton();
        senatorButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });

        mayorButton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        mayorButton.setText("Mayor");
        mayorButton.setContentAreaFilled(false);
        mayorButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mayorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mayorButtonActionPerformed(evt);
            }
        });

        councilButton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        councilButton.setText("Council");
        councilButton.setContentAreaFilled(false);
        councilButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        councilButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                councilButtonActionPerformed(evt);
            }
        });

        governorButton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        governorButton.setText("Governor");
        governorButton.setContentAreaFilled(false);
        governorButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        governorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                governorButtonActionPerformed(evt);
            }
        });

        presidentButton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        presidentButton.setText("President");
        presidentButton.setContentAreaFilled(false);
        presidentButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        presidentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presidentButtonActionPerformed(evt);
            }
        });

        congressButton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        congressButton.setText("Congress");
        congressButton.setContentAreaFilled(false);
        congressButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        congressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                congressButtonActionPerformed(evt);
            }
        });

        senatorButton.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        senatorButton.setText("Senator");
        senatorButton.setContentAreaFilled(false);
        senatorButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        senatorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                senatorButtonActionPerformed(evt);
            }
        });

        // Create a panel for buttons with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Centered buttons with spacing
        buttonPanel.setOpaque(false); // Keep background transparent
        buttonPanel.add(mayorButton);
        buttonPanel.add(councilButton);
        buttonPanel.add(governorButton);
        buttonPanel.add(senatorButton);
        buttonPanel.add(presidentButton);
        buttonPanel.add(congressButton);

        // Set the layout of the main panel to BorderLayout
        setLayout(new BorderLayout(10, 10)); // 10px spacing
        add(polarAreaChart1, BorderLayout.CENTER); // Chart in the center
        add(buttonPanel, BorderLayout.SOUTH); // Buttons at the bottom
    }
    // </editor-fold>//GEN-END:initComponents


    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered


    }//GEN-LAST:event_formMouseEntered

    private void mayorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mayorButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(170,45,45), mayorPanel.getCandidate1(), mayorPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(190,135,135), mayorPanel.getCandidate2(), mayorPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(90,4,4), mayorPanel.getCandidate3(), mayorPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("Mayor");
        // Use the button text (already translated) for the chart title.
        polarAreaChart1.setTitle(mayorButton.getText());
        currentChartTitle = mayorButton.getText();

        polarAreaChart1.start();

    }//GEN-LAST:event_mayorButtonActionPerformed

    private void councilButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_councilButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(25,35,220), councilPanel.getCandidate1(), councilPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(114,118,195), councilPanel.getCandidate2(), councilPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(87,218,237), councilPanel.getCandidate3(), councilPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("City Council");
        // Use the button text (already translated) for the chart title.
        polarAreaChart1.setTitle(councilButton.getText());
        currentChartTitle = councilButton.getText();

        polarAreaChart1.start();
    }//GEN-LAST:event_councilButtonActionPerformed

    private void governorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_governorButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(20,175,32), governorPanel.getCandidate1(), governorPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(110,180,115), governorPanel.getCandidate2(), governorPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(10,82,17), governorPanel.getCandidate3(), governorPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("Governor");
        // Use the button text (already translated) for the chart title.
        polarAreaChart1.setTitle(governorButton.getText());
        currentChartTitle = governorButton.getText();

        polarAreaChart1.start();
    }//GEN-LAST:event_governorButtonActionPerformed

    private void presidentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presidentButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(230,175,11), presidentPanel.getCandidate1(), presidentPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(163,128,21), presidentPanel.getCandidate2(), presidentPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(214,194,135), presidentPanel.getCandidate3(), presidentPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("President");
        // Use the button text (already translated) for the chart title.
        polarAreaChart1.setTitle(presidentButton.getText());
        currentChartTitle = presidentButton.getText();

        polarAreaChart1.start();
    }//GEN-LAST:event_presidentButtonActionPerformed

    private void congressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_congressButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(7,97,107), congressPanel.getCandidate1(), congressPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(21,190,209), congressPanel.getCandidate2(), congressPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(148,203,209), congressPanel.getCandidate3(), congressPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("Congress");
        // Use the button text (already translated) for the chart title.
        polarAreaChart1.setTitle(congressButton.getText());
        currentChartTitle = congressButton.getText();

        polarAreaChart1.start();
    }//GEN-LAST:event_congressButtonActionPerformed

    @Override
    public void onLanguageChange(int newIndex) {
        // Call the updateText method to update UI based on the selected language index
        updateText(newIndex);
    }

    private void senatorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_senatorButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(58,11,82), senatorPanel.getCandidate1(), senatorPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(175,67,237), senatorPanel.getCandidate2(), senatorPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(184,134,209), senatorPanel.getCandidate3(), senatorPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("Senator");
        // Use the button text (already translated) for the chart title.
        polarAreaChart1.setTitle(senatorButton.getText());
        currentChartTitle = senatorButton.getText();

        polarAreaChart1.start();
    }//GEN-LAST:event_senatorButtonActio`nPerformed
} // End StatisticsPanel
