package UserGUI.java.userGUIcomponent;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import UserGUI.java.userGUIcomponent.ReviewAndSubmit;

/**
 * SenatorPanel handles the selection of candidates for the Senator election.
 */
public class SenatorPanel extends JPanel {

    // Instance variables
    private ReviewAndSubmit reviewAndSubmit;
    private JRadioButton Candidate1;
    private JRadioButton Candidate2;
    private JRadioButton Candidate3;
    private ButtonGroup buttonGroup1;
    private JLabel jLabel1;

    /**
     * Creates new form SenatorPanel
     */
    public SenatorPanel() {
        initComponents();
    } // End SenatorPanel constructor

    /**
     * Sets the ReviewAndSubmit instance for the panel.
     */
    public void setReviewAndSubmit(ReviewAndSubmit reviewAndSubmit) {
        this.reviewAndSubmit = reviewAndSubmit;
    } // End setReviewAndSubmit

    /**
     * Sets the text for the first candidate.
     */
    public void setCandidate1(String candidate) {
        Candidate1.setText(candidate);
    } // End setCandidate1

    /**
     * Sets the text for the second candidate.
     */
    public void setCandidate2(String candidate) {
        Candidate2.setText(candidate);
    } // End setCandidate2

    /**
     * Sets the text for the third candidate.
     */
    public void setCandidate3(String candidate) {
        Candidate3.setText(candidate);
    } // End setCandidate3

    /**
     * Initializes the components of the SenatorPanel.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        buttonGroup1 = new ButtonGroup();
        jLabel1 = new JLabel();
        Candidate1 = new JRadioButton();
        Candidate2 = new JRadioButton();
        Candidate3 = new JRadioButton();

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(JLabel.CENTER);
        jLabel1.setText("Senator");
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
    } // End initComponents

    /**
     * Handles the action when Candidate1 is selected.
     */
    private void Candidate1ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_Candidate1ActionPerformed
        reviewAndSubmit.updateSenator(Candidate1.getText()); // Call instance method
    } // End Candidate1ActionPerformed

    /**
     * Handles the action when Candidate2 is selected.
     */
    private void Candidate2ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_Candidate2ActionPerformed
        reviewAndSubmit.updateSenator(Candidate2.getText()); // Call instance method
    } // End Candidate2ActionPerformed

    /**
     * Handles the action when Candidate3 is selected.
     */
    private void Candidate3ActionPerformed(java.awt.event.ActionEvent evt) { //GEN-FIRST:event_Candidate3ActionPerformed
        reviewAndSubmit.updateSenator(Candidate3.getText()); // Call instance method
    } // End Candidate3ActionPerformed
} // End SenatorPanel