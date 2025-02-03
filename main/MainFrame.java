
package main;

import component.CongressPanel;
import component.CouncilPanel;
import component.DefaultPanel;
import component.GovernorPanel;
import component.MayorPanel;
import component.PresidentPanel;
import component.ReviewAndSubmit;
import component.SenatorPanel;
import java.awt.Component;
import menu.MenuEvent;
import support.PanelUpdater;








/**
 *
 * @author Marc
 */
public class MainFrame extends javax.swing.JFrame {

    private MayorPanel mayorPanel;
    private CouncilPanel councilPanel;
    private GovernorPanel governorPanel;
    private SenatorPanel senatorPanel;
    private PresidentPanel presidentPanel;
    private CongressPanel congressPanel;
    private ReviewAndSubmit reviewAndSubmit;
    
    

    public MainFrame() {
        initComponents();

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
        panelUpdater.updatePanelsFromFile("src/main/resources/Candidates.txt");
        
        
        
        menu2.setEvent(new MenuEvent() {
            
            @Override
            public void selected(int index, int subIndex){
                
                if (index == 0 && subIndex == 0){
                    showForm(new DefaultPanel());
                } else if (index == 1 && subIndex == 1) {
                    mayorPanel.setReviewAndSubmit(reviewAndSubmit);
                    showForm(mayorPanel);  
                } else if (index == 1 && subIndex == 2) {
                    councilPanel.setReviewAndSubmit(reviewAndSubmit);
                    showForm(councilPanel);  
                } else if (index == 2 && subIndex == 1) {
                    governorPanel.setReviewAndSubmit(reviewAndSubmit);
                    showForm(governorPanel); 
                } else if (index == 2 && subIndex == 2) {
                    senatorPanel.setReviewAndSubmit(reviewAndSubmit);
                    showForm(senatorPanel);  
                } else if (index == 3 && subIndex == 1){
                    presidentPanel.setReviewAndSubmit(reviewAndSubmit);
                    showForm(presidentPanel); 
                } else if (index == 3 && subIndex == 2){
                    congressPanel.setReviewAndSubmit(reviewAndSubmit);
                    showForm(congressPanel); 
                } else if (index == 4 && subIndex == 0){
                    showForm(reviewAndSubmit);
                }
            }
        });
    }
    
    //update form
    private void showForm(Component com){
        body.removeAll();
        body.add(com);
        body.repaint();
        body.revalidate();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scrollPaneWin111 = new support.ScrollPaneWin11();
        menu2 = new menu.Menu();
        header1 = new support.Header();
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
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private support.Header header1;
    private javax.swing.JPanel jPanel1;
    private menu.Menu menu2;
    private support.ScrollPaneWin11 scrollPaneWin111;
    // End of variables declaration//GEN-END:variables
}
