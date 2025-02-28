package AdminGUI.adminGUIcomponent;

import EmpowerVoteClient.LanguageManager;
import javax.swing.JLabel;

/**
 * A panel displaying a welcome message and instructions for the EmpowerVote Admin.
 */
public class DefaultPanel extends javax.swing.JPanel implements LanguageManager.LanguageChangeListener {

    // Instance variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;

    /**
     * Creates a new instance of DefaultPanel and initializes components.
     */
    public DefaultPanel() {
        initComponents();
        LanguageManager.getInstance().addListener(this); // Register for language updates
        updateText(LanguageManager.getInstance().getLanguageIndex()); // Set initial text
    } // End constructor

    private void updateText(int languageIndex) {
        // Language-specific translations
        String[][] texts = {
            // English
            {
                "Welcome EmpowerVote Admin!",
                "Select an election to view results using the menu.",
                "View results in charts using the statistics option.",
                "Exit anytime with the bottom-left exit button."
            },
            // Spanish (Shortened)
            {
                "¡Bienvenido, Admin de EmpowerVote!",
                "Seleccione una elección para ver resultados.",
                "Vea resultados en gráficos con estadísticas.",
                "Salga en cualquier momento con el botón abajo."
            },
            // Russian (Shortened)
            {
                "Добро пожаловать, админ EmpowerVote!",
                "Выберите выборы для просмотра результатов.",
                "Графики доступны в разделе статистики.",
                "Выход через кнопку внизу слева."
            }
        };

        jLabel1.setText(texts[languageIndex][0]); // Welcome message
        jLabel2.setText(texts[languageIndex][1]); // Instructions for viewing results
        jLabel3.setText(texts[languageIndex][2]); // Instructions for statistics
        jLabel4.setText(texts[languageIndex][3]); // Instructions for exiting
    }

    @Override
    public void onLanguageChange(int newIndex) {
        updateText(newIndex);
    }

    /**
     * Initializes the components in the panel.
     * This method sets up the layout and adds the labels with the respective texts.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Welcome EmpowerVote Admin!");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Select any elections you would like view results for using the menu on the left");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("You can also view the results in chart form using the statistics option");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Feel free to exit at any time with the exit button on the bottom left");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 202, Short.MAX_VALUE))
        );
    } // End initComponents
} // End DefaultPanel
