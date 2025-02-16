
package AdminGUI.java.adminGUIcomponent;

import AdminGUI.java.adminGUIchart.ModelPolarArea;
import java.awt.Color;


/**
 *
 * @author Marc
 */
public class StatisticsPanel extends javax.swing.JPanel {
    
    private MayorPanel mayorPanel;
    private CouncilPanel councilPanel;
    private GovernorPanel governorPanel;
    private SenatorPanel senatorPanel;
    private PresidentPanel presidentPanel;
    private CongressPanel congressPanel;
    
    
     public StatisticsPanel() {
        initComponents();
     }
     
     public void setStatisticsPanel(MayorPanel mayorPanel) {
        this.mayorPanel = mayorPanel;
    }
     
     public void setStatisticsPanel(CouncilPanel councilPanel) {
        this.councilPanel = councilPanel;
    }
     
    public void setStatisticsPanel(GovernorPanel governorPanel) {
        this.governorPanel = governorPanel;
    }
    
    public void setStatisticsPanel(SenatorPanel senatorPanel) {
        this.senatorPanel = senatorPanel;
    }
    
    public void setStatisticsPanel(PresidentPanel presidentPanel) {
        this.presidentPanel = presidentPanel;
    }
    
    public void setStatisticsPanel(CongressPanel congressPanel) {
        this.congressPanel = congressPanel;
    }
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mayorButton = new javax.swing.JButton();
        polarAreaChart1 = new AdminGUI.java.adminGUIchart.PolarAreaChart1();
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(mayorButton)
                .addGap(18, 18, 18)
                .addComponent(councilButton)
                .addGap(18, 18, 18)
                .addComponent(governorButton)
                .addGap(18, 18, 18)
                .addComponent(senatorButton)
                .addGap(18, 18, 18)
                .addComponent(presidentButton)
                .addGap(18, 18, 18)
                .addComponent(congressButton)
                .addGap(88, 88, 88))
            .addComponent(polarAreaChart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(polarAreaChart1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mayorButton)
                    .addComponent(councilButton)
                    .addComponent(governorButton)
                    .addComponent(presidentButton)
                    .addComponent(congressButton)
                    .addComponent(senatorButton))
                .addGap(37, 37, 37))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
       
        
    }//GEN-LAST:event_formMouseEntered

    private void mayorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mayorButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(170,45,45), mayorPanel.getCandidate1(), mayorPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(190,135,135), mayorPanel.getCandidate2(), mayorPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(90,4,4), mayorPanel.getCandidate3(), mayorPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("Mayor");
        polarAreaChart1.start();
        
    }//GEN-LAST:event_mayorButtonActionPerformed

    private void councilButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_councilButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(25,35,220), councilPanel.getCandidate1(), councilPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(114,118,195), councilPanel.getCandidate2(), councilPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(87,218,237), councilPanel.getCandidate3(), councilPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("City Council");
        polarAreaChart1.start();
    }//GEN-LAST:event_councilButtonActionPerformed

    private void governorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_governorButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(20,175,32), governorPanel.getCandidate1(), governorPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(110,180,115), governorPanel.getCandidate2(), governorPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(10,82,17), governorPanel.getCandidate3(), governorPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("Governor");
        polarAreaChart1.start();
    }//GEN-LAST:event_governorButtonActionPerformed

    private void presidentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presidentButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(230,175,11), presidentPanel.getCandidate1(), presidentPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(163,128,21), presidentPanel.getCandidate2(), presidentPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(214,194,135), presidentPanel.getCandidate3(), presidentPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("President");
        polarAreaChart1.start();
    }//GEN-LAST:event_presidentButtonActionPerformed

    private void congressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_congressButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(7,97,107), congressPanel.getCandidate1(), congressPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(21,190,209), congressPanel.getCandidate2(), congressPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(148,203,209), congressPanel.getCandidate3(), congressPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("Congress");
        polarAreaChart1.start();
    }//GEN-LAST:event_congressButtonActionPerformed

    private void senatorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_senatorButtonActionPerformed
        polarAreaChart1.clear();
        polarAreaChart1.addItem(new ModelPolarArea(new Color(58,11,82), senatorPanel.getCandidate1(), senatorPanel.getCandidate1Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(175,67,237), senatorPanel.getCandidate2(), senatorPanel.getCandidate2Votes()));
        polarAreaChart1.addItem(new ModelPolarArea(new Color(184,134,209), senatorPanel.getCandidate3(), senatorPanel.getCandidate3Votes()));
        polarAreaChart1.setTitle("Senator");
        polarAreaChart1.start();
    }//GEN-LAST:event_senatorButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton congressButton;
    private javax.swing.JButton councilButton;
    private javax.swing.JButton governorButton;
    private javax.swing.JButton mayorButton;
    private AdminGUI.java.adminGUIchart.PolarAreaChart1 polarAreaChart1;
    private javax.swing.JButton presidentButton;
    private javax.swing.JButton senatorButton;
    // End of variables declaration//GEN-END:variables
}
