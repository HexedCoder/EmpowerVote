package main;

import menu.Menu;
import userGUIcomponent.CongressPanel;
import userGUIcomponent.CouncilPanel;
import userGUIcomponent.DefaultPanel;
import userGUIcomponent.GovernorPanel;
import userGUIcomponent.MayorPanel;
import userGUIcomponent.PresidentPanel;
import userGUIcomponent.ReviewAndSubmit;
import userGUIcomponent.SenatorPanel;
import menu.MenuEvent;
import userGUIsupport.PanelUpdater;
import userGUIsupport.Header;
import userGUIsupport.ScrollPaneWin11;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Component;
import java.io.IOException;

/**
 * StartupUser is the main class responsible for the user interface,
 * managing the different panels, and handling user interactions in the application.
 */
public class StartupUser extends javax.swing.JFrame {

    // Panel components for different election categories
    private MayorPanel mayorPanel;
    private CouncilPanel councilPanel;
    private GovernorPanel governorPanel;
    private SenatorPanel senatorPanel;
    private PresidentPanel presidentPanel;
    private CongressPanel congressPanel;
    private ReviewAndSubmit reviewAndSubmit;

    // UI components
    private javax.swing.JPanel body;
    private Header header1;
    private javax.swing.JPanel jPanel1;
    private Menu menu2;
    private ScrollPaneWin11 scrollPaneWin111;

    // Socket communication components
    private String userString;

    private Point pressedPoint; // Stores initial click position

    /**
     * Initializes the StartupUser interface, establishes the socket connection,
     * and sets up the initial UI and panels.
     *
     * @param userString String for the user data.
     */
    public StartupUser(String userString) {
        userString = userString;

        initComponents();

        // Make frame draggable by pressing and dragging the header
        header1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressedPoint = e.getPoint(); // Store click position
            }
        });

        header1.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (pressedPoint != null) {
                    // Calculate new window position
                    int x = getLocation().x + e.getX() - pressedPoint.x;
                    int y = getLocation().y + e.getY() - pressedPoint.y;
                    setLocation(x, y); // Move frame
                }
            }
        });

        // Initialize all panels
        mayorPanel = new MayorPanel();
        councilPanel = new CouncilPanel();
        governorPanel = new GovernorPanel();
        senatorPanel = new SenatorPanel();
        presidentPanel = new PresidentPanel();
        congressPanel = new CongressPanel();
        reviewAndSubmit = new ReviewAndSubmit();

        showForm(new DefaultPanel());

        // Update panels from file and pass each relevant panel
        PanelUpdater panelUpdater = new PanelUpdater(this, mayorPanel, councilPanel, governorPanel, senatorPanel, presidentPanel, congressPanel);
        panelUpdater.updatePanelsFromFile(userString);

        // Set menu event handling
        menu2.setEvent(new MenuEvent() {
            @Override
            public void selected(int index, int subIndex) {
                // Handle menu selections based on index and subIndex
                switch (index) {
                    case 0:
                        if (subIndex == 0) {
                            showForm(new DefaultPanel());
                        }
                        break;
                    case 1:
                        if (subIndex == 1) {
                            mayorPanel.setReviewAndSubmit(reviewAndSubmit);
                            showForm(mayorPanel);
                        } else if (subIndex == 2) {
                            councilPanel.setReviewAndSubmit(reviewAndSubmit);
                            showForm(councilPanel);
                        }
                        break;
                    case 2:
                        if (subIndex == 1) {
                            governorPanel.setReviewAndSubmit(reviewAndSubmit);
                            showForm(governorPanel);
                        } else if (subIndex == 2) {
                            senatorPanel.setReviewAndSubmit(reviewAndSubmit);
                            showForm(senatorPanel);
                        }
                        break;
                    case 3:
                        if (subIndex == 1) {
                            presidentPanel.setReviewAndSubmit(reviewAndSubmit);
                            showForm(presidentPanel);
                        } else if (subIndex == 2) {
                            congressPanel.setReviewAndSubmit(reviewAndSubmit);
                            showForm(congressPanel);
                        }
                        break;
                    case 4:
                        if (subIndex == 0) {
                            showForm(reviewAndSubmit);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    } // End StartupUser

    /**
     * Updates the displayed form inside the main panel.
     *
     * @param com The component to display in the main body.
     */
    private void showForm(Component com) {
        body.removeAll();
        body.add(com);
        body.repaint();
        body.revalidate();
    } // End showForm

    /**
     * Initializes the components and layout of the StartupUser UI.
     */
    @SuppressWarnings("unchecked")
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
} // End StartupUser
