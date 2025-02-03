
package component;



/**
 *
 * @author Marc
 */
public class GovernorPanel extends javax.swing.JPanel {
    
    private ReviewAndSubmit reviewAndSubmit;
    /**
     * Creates new form GovernorPanel
     */
    public GovernorPanel() {
        initComponents();
    }
    
    public void setReviewAndSubmit(ReviewAndSubmit reviewAndSubmit) {
        this.reviewAndSubmit = reviewAndSubmit;
    }
     
    public void setCandidate1(String candidate) {
        Candidate1.setText(candidate);
    }

    public void setCandidate2(String candidate) {
        Candidate2.setText(candidate);
    }

    public void setCandidate3(String candidate) {
        Candidate3.setText(candidate);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        Candidate1 = new javax.swing.JRadioButton();
        Candidate2 = new javax.swing.JRadioButton();
        Candidate3 = new javax.swing.JRadioButton();

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Governor");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        Candidate1.setBackground(new java.awt.Color(250, 250, 250));
        buttonGroup1.add(Candidate1);
        Candidate1.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        Candidate1.setText("Candidate1");
        Candidate1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Candidate1.setBorderPainted(true);
        Candidate1.setContentAreaFilled(false);
        Candidate1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Candidate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Candidate1ActionPerformed(evt);
            }
        });

        Candidate2.setBackground(new java.awt.Color(250, 250, 250));
        buttonGroup1.add(Candidate2);
        Candidate2.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        Candidate2.setText("Candidate2");
        Candidate2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Candidate2.setBorderPainted(true);
        Candidate2.setContentAreaFilled(false);
        Candidate2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Candidate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Candidate2ActionPerformed(evt);
            }
        });

        Candidate3.setBackground(new java.awt.Color(250, 250, 250));
        buttonGroup1.add(Candidate3);
        Candidate3.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        Candidate3.setText("Candidate3");
        Candidate3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Candidate3.setBorderPainted(true);
        Candidate3.setContentAreaFilled(false);
        Candidate3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Candidate3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Candidate3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(264, 264, 264)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(Candidate1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Candidate2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Candidate3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(265, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(450, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(204, 204, 204)
                    .addComponent(Candidate1)
                    .addGap(18, 18, 18)
                    .addComponent(Candidate2)
                    .addGap(18, 18, 18)
                    .addComponent(Candidate3)
                    .addContainerGap(204, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void Candidate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Candidate1ActionPerformed
       reviewAndSubmit.updateGovernor(Candidate1.getText()); // Call instance method
    }//GEN-LAST:event_Candidate1ActionPerformed

    private void Candidate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Candidate2ActionPerformed
       reviewAndSubmit.updateGovernor(Candidate2.getText()); // Call instance method
    }//GEN-LAST:event_Candidate2ActionPerformed

    private void Candidate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Candidate3ActionPerformed
       reviewAndSubmit.updateGovernor(Candidate3.getText()); // Call instance method
    }//GEN-LAST:event_Candidate3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Candidate1;
    private javax.swing.JRadioButton Candidate2;
    private javax.swing.JRadioButton Candidate3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}