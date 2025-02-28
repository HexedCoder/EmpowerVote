
package EmpowerVoteClient;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Marc
 */
public class LanguageManager {
    private static LanguageManager instance;
    private int languageIndex = 0; // Default to English
    private final List<LanguageChangeListener> listeners = new ArrayList<>();

    private LanguageManager() {}

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public int getLanguageIndex() {
        return languageIndex;
    }

    public void setLanguageIndex(int index) {
        if (index >= 0 && index < 3) { // Ensure valid index
            this.languageIndex = index;
            notifyListeners();
        }
    }

    public void addListener(LanguageChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (LanguageChangeListener listener : listeners) {
            listener.onLanguageChange(languageIndex);
        }
    }

    public interface LanguageChangeListener {
        void onLanguageChange(int newIndex);
    }
}
