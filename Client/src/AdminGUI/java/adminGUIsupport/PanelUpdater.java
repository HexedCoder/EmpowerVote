
package AdminGUI.java.adminGUIsupport;

import AdminGUI.java.adminGUIcomponent.CongressPanel;
import AdminGUI.java.adminGUIcomponent.CouncilPanel;
import AdminGUI.java.adminGUIcomponent.GovernorPanel;
import AdminGUI.java.adminGUIcomponent.MayorPanel;
import AdminGUI.java.adminGUIcomponent.PresidentPanel;
import AdminGUI.java.adminGUIcomponent.SenatorPanel;
import java.io.*;
import java.util.*;
import AdminGUI.java.main.StartupAdmin;

public class PanelUpdater {
    
    private final MayorPanel mayorPanel;
    private final CouncilPanel councilPanel;
    private final GovernorPanel governorPanel;
    private final SenatorPanel senatorPanel;
    private final PresidentPanel presidentPanel;
    private final CongressPanel congressPanel;

    // Update constructor to take all relevant panel instances
    public PanelUpdater(StartupAdmin aThis, MayorPanel mayorPanel, CouncilPanel councilPanel, 
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
                List<String> tokens = new ArrayList<>(Arrays.asList(line.split(":")[1].trim().split("\\s+")));

                if (tokens.size() >= 6) { // Ensure we have at least three name-vote pairs
                    String candidate1 = tokens.get(0);
                    int candidate1Vote = Integer.parseInt(tokens.get(1));
                    String candidate1Votes = Integer.toString(candidate1Vote);

                    String candidate2 = tokens.get(2);
                    int candidate2Vote = Integer.parseInt(tokens.get(3));
                    String candidate2Votes = Integer.toString(candidate2Vote);

                    String candidate3 = tokens.get(4);
                    int candidate3Vote = Integer.parseInt(tokens.get(5));
                    String candidate3Votes = Integer.toString(candidate3Vote);

                    // Update the respective panel based on the position
                    if ("Mayor".equals(position)) {
                        mayorPanel.setCandidate1(candidate1, candidate1Votes);
                        mayorPanel.setCandidate2(candidate2, candidate2Votes);
                        mayorPanel.setCandidate3(candidate3, candidate3Votes);
                    } else if ("Council".equals(position)) {
                        councilPanel.setCandidate1(candidate1, candidate1Votes);
                        councilPanel.setCandidate2(candidate2, candidate2Votes);
                        councilPanel.setCandidate3(candidate3, candidate3Votes);
                    } else if ("Governor".equals(position)) {
                        governorPanel.setCandidate1(candidate1, candidate1Votes);
                        governorPanel.setCandidate2(candidate2, candidate2Votes);
                        governorPanel.setCandidate3(candidate3, candidate3Votes);
                    } else if ("Senator".equals(position)) {
                        senatorPanel.setCandidate1(candidate1, candidate1Votes);
                        senatorPanel.setCandidate2(candidate2, candidate2Votes);
                        senatorPanel.setCandidate3(candidate3, candidate3Votes);
                    } else if ("President".equals(position)) {
                        presidentPanel.setCandidate1(candidate1, candidate1Votes);
                        presidentPanel.setCandidate2(candidate2, candidate2Votes);
                        presidentPanel.setCandidate3(candidate3, candidate3Votes);
                    } else if ("Congress".equals(position)) {
                        congressPanel.setCandidate1(candidate1, candidate1Votes);
                        congressPanel.setCandidate2(candidate2, candidate2Votes);
                        congressPanel.setCandidate3(candidate3, candidate3Votes);
                    }
                } else {
                    System.out.println("Error: Incorrect format or missing data for " + position);
                }
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}

}


   

    
