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

        // Update button text
        mayorButton.setText(texts[languageIndex][0]);
        councilButton.setText(texts[languageIndex][1]);
        governorButton.setText(texts[languageIndex][2]);
        senatorButton.setText(texts[languageIndex][3]);
        presidentButton.setText(texts[languageIndex][4]);
        congressButton.setText(texts[languageIndex][5]);

        // Translate the chart title if it matches one of the predefined titles
        if (!currentChartTitle.isEmpty()) {
            for (int i = 0; i < texts[0].length; i++) {
                if (currentChartTitle.equals(texts[0][i])) {
                    polarAreaChart1.setTitle(texts[languageIndex][i]);
                    break;
                }
            }
        }
    } // End updateText

    /**
     * Handles language change event and updates the UI accordingly.
     *
     * @param newIndex The new language index.
     */
    @Override
    public void onLanguageChange(int newIndex) {
        updateText(newIndex);
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
    } // End initComponents
} // End StatisticsPanel
