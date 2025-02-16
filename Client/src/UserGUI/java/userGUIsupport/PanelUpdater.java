
package UserGUI.java.userGUIsupport;

import UserGUI.java.userGUIcomponent.CongressPanel;
import UserGUI.java.userGUIcomponent.CouncilPanel;
import UserGUI.java.userGUIcomponent.GovernorPanel;
import UserGUI.java.userGUIcomponent.MayorPanel;
import UserGUI.java.userGUIcomponent.PresidentPanel;
import UserGUI.java.userGUIcomponent.SenatorPanel;
import java.io.*;
import java.util.*;
import UserGUI.java.main.StartupUser;

public class PanelUpdater {
    
    private MayorPanel mayorPanel;
    private CouncilPanel councilPanel;
    private GovernorPanel governorPanel;
    private SenatorPanel senatorPanel;
    private PresidentPanel presidentPanel;
    private CongressPanel congressPanel;

    // Update constructor to take all relevant panel instances
    public PanelUpdater(StartupUser aThis, MayorPanel mayorPanel, CouncilPanel councilPanel, 
        GovernorPanel governorPanel, SenatorPanel senatorPanel,
        PresidentPanel presidentPanel, CongressPanel congressPanel) {
            this.mayorPanel = mayorPanel;
            this.councilPanel = councilPanel;
            this.governorPanel = governorPanel;
            this.senatorPanel = senatorPanel;
            this.presidentPanel = presidentPanel;
            this.congressPanel = congressPanel;
        }

    public void updatePanelsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove extra spaces

                if (line.contains(":")) {  // Position line
                    String position = line.split(":")[0].trim(); // Extract position (before the colon)
                    List<String> candidates = new ArrayList<>();

                    // Extract the candidates from the rest of the line
                    String candidateLine = line.split(":")[1].trim();
                    String[] candidateArray = candidateLine.split("\\s+"); // Split by spaces
                    
                    // Add candidates to the list
                    candidates.addAll(Arrays.asList(candidateArray));

                    // Make sure there are exactly 3 candidates, or handle cases where there are fewer
                    if (candidates.size() == 3) {
                        String Candidate1 = candidates.get(0);
                        String Candidate2 = candidates.get(1);
                        String Candidate3 = candidates.get(2);

                        // Update the respective panel based on the position
                        if ("Mayor".equals(position)) {
                            mayorPanel.setCandidate1(Candidate1);
                            mayorPanel.setCandidate2(Candidate2);
                            mayorPanel.setCandidate3(Candidate3);
                        } else if ("Council".equals(position)) {
                            councilPanel.setCandidate1(Candidate1);
                            councilPanel.setCandidate2(Candidate2);
                            councilPanel.setCandidate3(Candidate3);
                        } else if ("Governor".equals(position)) {
                            governorPanel.setCandidate1(Candidate1);
                            governorPanel.setCandidate2(Candidate2);
                            governorPanel.setCandidate3(Candidate3);
                        } else if ("Senator".equals(position)) {
                            senatorPanel.setCandidate1(Candidate1);
                            senatorPanel.setCandidate2(Candidate2);
                            senatorPanel.setCandidate3(Candidate3);
                        } else if ("President".equals(position)) {
                            presidentPanel.setCandidate1(Candidate1);
                            presidentPanel.setCandidate2(Candidate2);
                            presidentPanel.setCandidate3(Candidate3);
                        } else if ("Congress".equals(position)) {
                            congressPanel.setCandidate1(Candidate1);
                            congressPanel.setCandidate2(Candidate2);
                            congressPanel.setCandidate3(Candidate3);
                        }
                    } else {
                        System.out.println("Error: Less than 3 candidates found for " + position);
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}


   

    
