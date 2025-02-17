package AdminGUI.adminGUIsupport;

import AdminGUI.adminGUIcomponent.CongressPanel;
import AdminGUI.adminGUIcomponent.CouncilPanel;
import AdminGUI.adminGUIcomponent.GovernorPanel;
import AdminGUI.adminGUIcomponent.MayorPanel;
import AdminGUI.adminGUIcomponent.PresidentPanel;
import AdminGUI.adminGUIcomponent.SenatorPanel;
import java.io.*;
import java.util.*;
import AdminGUI.main.StartupAdmin;

/**
 * The PanelUpdater class is responsible for updating the panels (Mayor, Council, Governor, etc.)
 * with data read from a file. It reads candidate names and votes for each position.
 */
public class PanelUpdater {

    // Panel instances
    private final MayorPanel mayorPanel;
    private final CouncilPanel councilPanel;
    private final GovernorPanel governorPanel;
    private final SenatorPanel senatorPanel;
    private final PresidentPanel presidentPanel;
    private final CongressPanel congressPanel;

    /**
     * Constructor to initialize the PanelUpdater with the relevant panel instances.
     * @param aThis The StartupAdmin instance (not used in this code but included in constructor).
     * @param mayorPanel The MayorPanel instance.
     * @param councilPanel The CouncilPanel instance.
     * @param governorPanel The GovernorPanel instance.
     * @param senatorPanel The SenatorPanel instance.
     * @param presidentPanel The PresidentPanel instance.
     * @param congressPanel The CongressPanel instance.
     *
     */
    public PanelUpdater(StartupAdmin aThis, MayorPanel mayorPanel, CouncilPanel councilPanel,
                        GovernorPanel governorPanel, SenatorPanel senatorPanel,
                        PresidentPanel presidentPanel, CongressPanel congressPanel) {
        this.mayorPanel = mayorPanel;
        this.councilPanel = councilPanel;
        this.governorPanel = governorPanel;
        this.senatorPanel = senatorPanel;
        this.presidentPanel = presidentPanel;
        this.congressPanel = congressPanel;
    } // End PanelUpdater

    /**
     * Reads the data from a file and updates the panels with the candidate names and votes.
     * @param voteInfo The String containing the vote information.
     */
    public void updatePanelsFromString(String voteInfo) {
        String[] lines = voteInfo.split("\\n");

        for (String line : lines) {
            line = line.trim(); // Remove extra spaces

            if (line.contains(":")) {  // Position line
                String position = line.split(":")[0].trim(); // Extract position (before the colon)
                List<String> tokens = new ArrayList<>(Arrays.asList(line.split(":")[1].trim().split("\\t")));

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
                    switch (position) {
                        case "Mayor" -> {
                            mayorPanel.setCandidate1(candidate1, candidate1Votes);
                            mayorPanel.setCandidate2(candidate2, candidate2Votes);
                            mayorPanel.setCandidate3(candidate3, candidate3Votes);
                        }
                        case "Council" -> {
                            councilPanel.setCandidate1(candidate1, candidate1Votes);
                            councilPanel.setCandidate2(candidate2, candidate2Votes);
                            councilPanel.setCandidate3(candidate3, candidate3Votes);
                        }
                        case "Governor" -> {
                            governorPanel.setCandidate1(candidate1, candidate1Votes);
                            governorPanel.setCandidate2(candidate2, candidate2Votes);
                            governorPanel.setCandidate3(candidate3, candidate3Votes);
                        }
                        case "Senator" -> {
                            senatorPanel.setCandidate1(candidate1, candidate1Votes);
                            senatorPanel.setCandidate2(candidate2, candidate2Votes);
                            senatorPanel.setCandidate3(candidate3, candidate3Votes);
                        }
                        case "President" -> {
                            presidentPanel.setCandidate1(candidate1, candidate1Votes);
                            presidentPanel.setCandidate2(candidate2, candidate2Votes);
                            presidentPanel.setCandidate3(candidate3, candidate3Votes);
                        }
                        case "Congress" -> {
                            congressPanel.setCandidate1(candidate1, candidate1Votes);
                            congressPanel.setCandidate2(candidate2, candidate2Votes);
                            congressPanel.setCandidate3(candidate3, candidate3Votes);
                        }
                    }
                } else {
                    System.out.println("Error: Incorrect format or missing data for " + position);
                }
            }
        }
    } // End updatePanelsFromString
} // End PanelUpdater