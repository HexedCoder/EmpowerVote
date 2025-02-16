
package AdminGUI.java.adminGUIcomponent;




/**
 *
 * @author Marc
 */
public class MayorPanel extends javax.swing.JPanel {

    
    public static String candidate1;
    public static String candidate2;
    public static String candidate3;
    public static int candidate1Vote;
    public static int candidate2Vote;
    public static int candidate3Vote;
  
    public MayorPanel() {
        initComponents();
    }
    
    
     
    public void setCandidate1(String candidate, String votes) {
        Candidate1.setText(candidate);
        candidate1Votes.setText(votes);
    }

    public void setCandidate2(String candidate, String votes) {
        Candidate2.setText(candidate);
        candidate2Votes.setText(votes);
    }

    public void setCandidate3(String candidate, String votes) {
        Candidate3.setText(candidate);
        candidate3Votes.setText(votes);
    }
    
    public String getCandidate1(){
        candidate1 = Candidate1.getText();
        return candidate1;
    }
    
    public String getCandidate2(){
        candidate2 = Candidate2.getText();
        return candidate2;
    }
    
    public String getCandidate3(){
        candidate3 = Candidate3.getText();
        return candidate3;
    }
    
    public int getCandidate1Votes(){
        candidate1Vote = Integer.parseInt(candidate1Votes.getText());
        return candidate1Vote;
    }
    
    public int getCandidate2Votes(){
        candidate2Vote = Integer.parseInt(candidate2Votes.getText());
        return candidate2Vote;
    }
    
    public int getCandidate3Votes(){
        candidate3Vote = Integer.parseInt(candidate3Votes.getText());
        return candidate3Vote;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Candidate1 = new javax.swing.JLabel();
        candidate1Votes = new javax.swing.JLabel();
        Candidate2 = new javax.swing.JLabel();
        candidate2Votes = new javax.swing.JLabel();
        Candidate3 = new javax.swing.JLabel();
        candidate3Votes = new javax.swing.JLabel();

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("City Mayor");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        Candidate1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Candidate1.setText("jLabel2");
        Candidate1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        candidate1Votes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        candidate1Votes.setText("jLabel2");
        candidate1Votes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Candidate2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Candidate2.setText("jLabel2");
        Candidate2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        candidate2Votes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        candidate2Votes.setText("jLabel2");
        candidate2Votes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Candidate3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Candidate3.setText("jLabel2");
        Candidate3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        candidate3Votes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        candidate3Votes.setText("jLabel2");
        candidate3Votes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
                    .addGap(155, 155, 155)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Candidate2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(candidate2Votes, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Candidate3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(candidate3Votes, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Candidate1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(candidate1Votes, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(156, Short.MAX_VALUE)))
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Candidate1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(candidate1Votes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Candidate2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(candidate2Votes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Candidate3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(candidate3Votes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(204, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Candidate1;
    private javax.swing.JLabel Candidate2;
    private javax.swing.JLabel Candidate3;
    private javax.swing.JLabel candidate1Votes;
    private javax.swing.JLabel candidate2Votes;
    private javax.swing.JLabel candidate3Votes;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    
}

