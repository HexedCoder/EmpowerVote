package userGUIsupport;

import userGUIcomponent.CongressPanel;
import userGUIcomponent.CouncilPanel;
import userGUIcomponent.GovernorPanel;
import userGUIcomponent.MayorPanel;
import userGUIcomponent.PresidentPanel;
import userGUIcomponent.SenatorPanel;

import java.util.*;
import main.StartupUser;

/**
 * PanelUpdater is responsible for reading the candidate data from a file
 * and updating the corresponding panels with candidate names.
 */
public class PanelUpdater {

    // Instance variables
    private MayorPanel mayorPanel;
    private CouncilPanel councilPanel;
    private GovernorPanel governorPanel;
    private SenatorPanel senatorPanel;
    private PresidentPanel presidentPanel;
    private CongressPanel congressPanel;

    /**
     * Constructor for initializing the PanelUpdater with the election panels.
     *
     * @param aThis        The reference to the StartupUser.
     * @param mayorPanel   The MayorPanel instance.
     * @param councilPanel The CouncilPanel instance.
     * @param governorPanel The GovernorPanel instance.
     * @param senatorPanel The SenatorPanel instance.
     * @param presidentPanel The PresidentPanel instance.
     * @param congressPanel The CongressPanel instance.
     */
    public PanelUpdater(StartupUser aThis, MayorPanel mayorPanel, CouncilPanel councilPanel,
                        GovernorPanel governorPanel, SenatorPanel senatorPanel,
                        PresidentPanel presidentPanel, CongressPanel congressPanel) {
        this.mayorPanel = mayorPanel;
        this.councilPanel = councilPanel;
        this.governorPanel = governorPanel;
        this.senatorPanel = senatorPanel;
        this.presidentPanel = presidentPanel;
        this.congressPanel = congressPanel;
    } // End PanelUpdater constructor

    /**
     * Updates the election panels with the candidates read from a file.
     *
     * @param userInfo The String containing the candidate information.
     */
    public void updatePanelsFromFile(String userInfo) {
        String[] lines = userInfo.split("\\n");
            for (String line : lines) {
                line = line.trim(); // Remove extra spaces

                if (line.contains(":")) {  // Position line
                    String position = line.split(":")[0].trim(); // Extract position (before the colon)
                    List<String> candidates = new ArrayList<>();

                    // Extract the candidates from the rest of the line
                    String candidateLine = line.split(":")[1].trim();
                    String[] candidateArray = candidateLine.split("\\t"); // Split by spaces

                    // Add candidates to the list
                    candidates.addAll(Arrays.asList(candidateArray));

                    // Make sure there are exactly 3 candidates, or handle cases where there are fewer
                    if (candidates.size() == 3) {
                        String Candidate1 = candidates.get(0);
                        String Candidate2 = candidates.get(1);
                        String Candidate3 = candidates.get(2);

                        // Update the respective panel based on the position
                        switch (position) {
                            case "Mayor" -> {
                                mayorPanel.setCandidate1(Candidate1);
                                mayorPanel.setCandidate2(Candidate2);
                                mayorPanel.setCandidate3(Candidate3);
                            }
                            case "Council" -> {
                                councilPanel.setCandidate1(Candidate1);
                                councilPanel.setCandidate2(Candidate2);
                                councilPanel.setCandidate3(Candidate3);
                            }
                            case "Governor" -> {
                                governorPanel.setCandidate1(Candidate1);
                                governorPanel.setCandidate2(Candidate2);
                                governorPanel.setCandidate3(Candidate3);
                            }
                            case "Senator" -> {
                                senatorPanel.setCandidate1(Candidate1);
                                senatorPanel.setCandidate2(Candidate2);
                                senatorPanel.setCandidate3(Candidate3);
                            }
                            case "President" -> {
                                presidentPanel.setCandidate1(Candidate1);
                                presidentPanel.setCandidate2(Candidate2);
                                presidentPanel.setCandidate3(Candidate3);
                            }
                            case "Congress" -> {
                                congressPanel.setCandidate1(Candidate1);
                                congressPanel.setCandidate2(Candidate2);
                                congressPanel.setCandidate3(Candidate3);
                            }
                        }
                    } else {
                        System.out.println("Error: Less than 3 candidates found for " + position);
                    }
                }
            }
    } // End updatePanelsFromFile
} // End PanelUpdater