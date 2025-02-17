package AdminGUI.main;

import AdminGUI.adminGUIcomponent.CongressPanel;
import AdminGUI.adminGUIcomponent.CouncilPanel;
import AdminGUI.adminGUIcomponent.DefaultPanel;
import AdminGUI.adminGUIcomponent.GovernorPanel;
import AdminGUI.adminGUIcomponent.MayorPanel;
import AdminGUI.adminGUIcomponent.PresidentPanel;
import AdminGUI.adminGUIcomponent.SenatorPanel;
import AdminGUI.adminGUIcomponent.StatisticsPanel;
import AdminGUI.adminGUIsupport.Header;
import AdminGUI.adminGUIsupport.ScrollPaneWin11;
import AdminGUI.menu.Menu;
import AdminGUI.menu.MenuEvent;
import AdminGUI.adminGUIsupport.PanelUpdater;
import ClientSocketHandler.ClientSocketHandler;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Main class for the admin startup interface.
 */
public class StartupAdmin extends javax.swing.JFrame {

    // GUI Variables
    private javax.swing.JPanel body;
    private Header header1;
    private javax.swing.JPanel jPanel1;
    private Menu menu2;
    private ScrollPaneWin11 scrollPaneWin111;

    // Panel Variables
    private MayorPanel mayorPanel;
    private CouncilPanel councilPanel;
    private GovernorPanel governorPanel;
    private SenatorPanel senatorPanel;
    private PresidentPanel presidentPanel;
    private CongressPanel congressPanel;
    private StatisticsPanel statisticsPanel;

    // Vote Variables
    private String voteData;

    /**
     * Constructor for StartupAdmin.
     * Initializes the components and sets up the socket connection.
     *
     * @param voteString The vote data string.
     */
    public StartupAdmin(String voteString) {
        voteData = voteString;

        initComponents();

        // Initialize all panels
        mayorPanel = new MayorPanel();
        councilPanel = new CouncilPanel();
        governorPanel = new GovernorPanel();
        senatorPanel = new SenatorPanel();
        presidentPanel = new PresidentPanel();
        congressPanel = new CongressPanel();
        statisticsPanel = new StatisticsPanel();

        showForm(new DefaultPanel());

        // Update panels from file and pass each relevant panel
        PanelUpdater panelUpdater = new PanelUpdater(this, mayorPanel, councilPanel, governorPanel, senatorPanel, presidentPanel, congressPanel);
        System.out.println(System.getProperty("user.dir"));
        panelUpdater.updatePanelsFromString(this.voteData);

        menu2.setEvent(new MenuEvent() {

            @Override
            public void selected(int index, int subIndex) {
                if (index == 0 && subIndex == 0) {
                    showForm(new DefaultPanel());
                } else if (index == 1 && subIndex == 1) {
                    showForm(mayorPanel);
                } else if (index == 1 && subIndex == 2) {
                    showForm(councilPanel);
                } else if (index == 2 && subIndex == 1) {
                    showForm(governorPanel);
                } else if (index == 2 && subIndex == 2) {
                    showForm(senatorPanel);
                } else if (index == 3 && subIndex == 1) {
                    showForm(presidentPanel);
                } else if (index == 3 && subIndex == 2) {
                    showForm(congressPanel);
                } else if (index == 4 && subIndex == 0) {
                    statisticsPanel.setStatisticsPanel(mayorPanel);
                    statisticsPanel.setStatisticsPanel(councilPanel);
                    statisticsPanel.setStatisticsPanel(governorPanel);
                    statisticsPanel.setStatisticsPanel(senatorPanel);
                    statisticsPanel.setStatisticsPanel(presidentPanel);
                    statisticsPanel.setStatisticsPanel(congressPanel);
                    showForm(statisticsPanel);
                }
            }
        });
    } // End StartupAdmin

    /**
     * Updates the main content panel with the specified component.
     *
     * @param com The component to be displayed in the body panel.
     */
    private void showForm(Component com) {
        body.removeAll();
        body.add(com);
        body.repaint();
        body.revalidate();
    } // End showForm

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scrollPaneWin111 = new ScrollPaneWin11();
        menu2 = new Menu();
        header1 = new Header();
        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        scrollPaneWin111.setBorder(null);
        scrollPaneWin111.setViewportView(menu2);

        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(scrollPaneWin111, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPaneWin111, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                                        .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    } // End initComponents
} // End StartupAdmin