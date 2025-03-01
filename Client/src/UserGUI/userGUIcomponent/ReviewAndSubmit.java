
package userGUIcomponent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import EmpowerVoteClient.LanguageManager;
import userGUIsupport.Button;

import static LoginGUI.java.main.StartupLogin.serverOut;

/**
 * ReviewAndSubmit panel for reviewing and submitting election votes.
 */
public class ReviewAndSubmit extends javax.swing.JPanel {

    // Instance variables
    private String mayorFinal;
    private String councilFinal;
    private String governorFinal;
    private String senatorFinal;
    private String presidentFinal;
    private String congressFinal;
    private javax.swing.JLabel mayorPanelSelection;
    private javax.swing.JLabel councilPanelSelection;
    private javax.swing.JLabel governorPanelSelection;
    private javax.swing.JLabel senatorPanelSelection;
    private javax.swing.JLabel presidentPanelSelection;
    private javax.swing.JLabel congressPanelSelection;
    private javax.swing.JLayeredPane areYouSure;
    private javax.swing.JPanel congratsPanel;
    private String [] goBackTexts = new String[]{
            // English
            "Go Back",
            // Spanish
            "Regresar",
            // Russian
            "Вернуться"
    };
    private String [] confirmTexts = new String[]{
            // English
            "Confirm",
            // Spanish
            "Confirmar",
            // Russian
            "Подтвердить"
    };
    private String [] warnSubmit = new String[]{
            // English
            "You will not be able to change your selections once you hit confirm",
            // Spanish
            "No podrás cambiar tus selecciones una vez que confirmes",
            // Russian
            "Вы не сможете изменить свой выбор после подтверждения"
    };
    private String [] confirmAndSubmit = new String[]{
            // English
            "Are you sure you would like to submit?",
            // Spanish
            "¿Estás seguro de que quieres enviar?",
            // Russian
            "Вы уверены, что хотите отправить?"
    };
    private String [] submitTexts = new String[]{
            // English
            "Submit",
            // Spanish
            "Enviar",
            // Russian
            "Отправить"
    };
    private String [] thankYouTexts = new String[]{
            // English
            "THANK YOU!",
            // Spanish
            "¡GRACIAS!",
            // Russian
            "СПАСИБО!"
    };
    private String [] voteThenSubmitText = new String[]{
            // English
            "Vote then Submit",
            // Spanish
            "Votar y luego enviar",
            // Russian
            "Проголосуйте, затем отправьте"
    };
    private String [] noneSelected = new String[3];
    private final String [] reviewAndSubmitTexts = new String[]{
            // English
            "Review and Submit",
            // Spanish
            "Revisar y Enviar",
            // Russian
            "Проверить и отправить"
    };
    private final String[][] reviewTexts = {
            // English
            {
                "Mayor:",
                "Council:",
                "Governor:",
                "Senator:",
                "President:",
                "Congress:"
            },
            // Spanish
            {
                "Alcalde:",
                "Consejo:",
                "Gobernador:",
                "Senador:",
                "Presidente:",
                "Congreso:"
            },
            // Russian
            {
                "Мэр:",
                "Совет:",
                "Губернатор:",
                "Сенатор:",
                "Президент:",
                "Конгресс:"
            }
    };
    private int language;

    // jLabels
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;

    /**
     * Creates new form ReviewAndSubmit.
     */
    public ReviewAndSubmit() {
        initComponents();

        setBackground(Color.WHITE); // Set background color
        System.out.println("Language: " + language);

        noneSelected = new String[]{
                // English
                "No Selection Entered",
                // Spanish
                "No se ha seleccionado",
                // Russian
                "Выбор не введен"
        };

        congratsPanel.setVisible(false);

        Button back = new Button();
        back.setBackground(new Color(150,10,45));
        back.setForeground(new Color(250,250,250));
        back.setText(goBackTexts[language]);
        back.setBounds(267, 330, 75, 23);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                areYouSure.setVisible(false);
            }
        });
        back.setVisible(true);
        areYouSure.add(back);


        Button confirm = new Button();
        confirm.setBackground(new Color(150,10,45));
        confirm.setForeground(new Color(250,250,250));
        confirm.setText(confirmTexts[language]);
        confirm.setBounds(368, 330, 75, 23);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                areYouSure.setVisible(false);
            }
        });
        confirm.setVisible(true);
        areYouSure.add(confirm);

        areYouSure.setVisible(false);

        Button cmd = new Button();
        cmd.setBackground(new Color(30,95,156));
        cmd.setForeground(new Color(250,250,250));
        cmd.setText(submitTexts[language]);
        cmd.setBounds(294, 486, 140, 40);
        add(cmd);
        cmd.setVisible(true);
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (submitResults(evt)) {
                    // return array of names to the main class
                    cmd.setText(thankYouTexts[language]);
                    // Create array of selected candidates
                    String votingString = mayorFinal + "\t" + councilFinal + "\t" + governorFinal +
                            "\t" + senatorFinal + "\t" + presidentFinal + "\t" +
                            congressFinal;

                    serverOut.println(votingString);
                    cmd.setEnabled(false);
                } else
                    cmd.setText(voteThenSubmitText[language]);
            }
        });
        // Set default selections
        mayorPanelSelection.setText(noneSelected[language]);
        councilPanelSelection.setText(noneSelected[language]);
        governorPanelSelection.setText(noneSelected[language]);
        senatorPanelSelection.setText(noneSelected[language]);
        presidentPanelSelection.setText(noneSelected[language]);
        congressPanelSelection.setText(noneSelected[language]);
    } // End ReviewAndSubmit constructor

    /**
     * Updates the Mayor selection.
     */
    public void updateMayor(String select) {
        mayorPanelSelection.setText(select);
        mayorFinal = select;
    } // End updateMayor

    /**
     * Updates the Council selection.
     */
    public void updateCouncil(String select) {
        councilPanelSelection.setText(select);
        councilFinal = select;
    } // End updateCouncil

    /**
     * Updates the Governor selection.
     */
    public void updateGovernor(String select) {
        governorPanelSelection.setText(select);
        governorFinal = select;
    } // End updateGovernor

    /**
     * Updates the Senator selection.
     */
    public void updateSenator(String select) {
        senatorPanelSelection.setText(select);
        senatorFinal = select;
    } // End updateSenator

    /**
     * Updates the President selection.
     */
    public void updatePresident(String select) {
        presidentPanelSelection.setText(select);
        presidentFinal = select;
    } // End updatePresident

    /**
     * Updates the Congress selection.
     */
    public void updateCongress(String select) {
        congressPanelSelection.setText(select);
        congressFinal = select;
    } // End updateCongress

    /**
     * Displays the confirmation dialog for submitting the results.
     */
    public boolean submitResults(ActionEvent evt) {
        if (mayorFinal == null || councilFinal == null || governorFinal == null || senatorFinal == null || presidentFinal == null || congressFinal == null) {
            return false;
        }
        areYouSure.setVisible(true);
        return true;
    } // End submitResults

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        congratsPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        areYouSure = new javax.swing.JLayeredPane();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        congressPanelSelection = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mayorPanelSelection = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        councilPanelSelection = new javax.swing.JLabel();
        governorPanelSelection = new javax.swing.JLabel();
        senatorPanelSelection = new javax.swing.JLabel();
        presidentPanelSelection = new javax.swing.JLabel();
        language = LanguageManager.getInstance().getLanguageIndex();

        setLayout(null);

        congratsPanel.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(thankYouTexts[language]);
        congratsPanel.add(jLabel2);
        jLabel2.setBounds(0, 180, 730, 80);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(thankYouTexts[language]);
        congratsPanel.add(jLabel3);
        jLabel3.setBounds(0, 260, 730, 80);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon("resources/vote background.png")); // NOI18N
        congratsPanel.add(jLabel12);
        jLabel12.setBounds(0, 0, 730, 550);

        add(congratsPanel);
        congratsPanel.setBounds(0, 0, 730, 550);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(reviewAndSubmitTexts[language]);
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(jLabel1);
        jLabel1.setBounds(6, 6, 717, 90);

        areYouSure.setBackground(new java.awt.Color(30, 95, 156));
        areYouSure.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(175, 0, 51), 3));
        areYouSure.setOpaque(true);

        jLabel10.setBackground(new java.awt.Color(0, 153, 153));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText(warnSubmit[language]);
        areYouSure.add(jLabel10);
        jLabel10.setBounds(0, 150, 710, 60);

        jLabel11.setBackground(new java.awt.Color(0, 153, 153));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText(confirmAndSubmit[language]);
        areYouSure.add(jLabel11);
        jLabel11.setBounds(0, 80, 710, 60);

        add(areYouSure);
        areYouSure.setBounds(10, 100, 710, 440);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText(reviewTexts[language][5]);
        add(jLabel4);
        jLabel4.setBounds(220, 330, 120, 30);

        congressPanelSelection.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        congressPanelSelection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        congressPanelSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(congressPanelSelection);
        congressPanelSelection.setBounds(340, 330, 220, 30);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText(reviewTexts[language][0]);
        add(jLabel5);
        jLabel5.setBounds(220, 130, 120, 30);

        mayorPanelSelection.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mayorPanelSelection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mayorPanelSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(mayorPanelSelection);
        mayorPanelSelection.setBounds(340, 130, 220, 30);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText(reviewTexts[language][1]);
        add(jLabel6);
        jLabel6.setBounds(220, 170, 120, 30);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText(reviewTexts[language][2]);
        add(jLabel7);
        jLabel7.setBounds(220, 210, 120, 30);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText(reviewTexts[language][3]);
        add(jLabel8);
        jLabel8.setBounds(220, 250, 120, 30);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText(reviewTexts[language][4]);
        add(jLabel9);
        jLabel9.setBounds(220, 290, 120, 30);

        councilPanelSelection.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        councilPanelSelection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        councilPanelSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(councilPanelSelection);
        councilPanelSelection.setBounds(340, 170, 220, 30);

        governorPanelSelection.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        governorPanelSelection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        governorPanelSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(governorPanelSelection);
        governorPanelSelection.setBounds(340, 210, 220, 30);

        senatorPanelSelection.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        senatorPanelSelection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        senatorPanelSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(senatorPanelSelection);
        senatorPanelSelection.setBounds(340, 250, 220, 30);

        presidentPanelSelection.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        presidentPanelSelection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        presidentPanelSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        add(presidentPanelSelection);
        presidentPanelSelection.setBounds(340, 290, 220, 30);
    } // End initComponents
} // End ReviewAndSubmit
