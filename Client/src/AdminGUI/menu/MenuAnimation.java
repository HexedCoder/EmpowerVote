package AdminGUI.menu;

import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * Handles animation for showing and hiding menu components.
 * This class uses an Animator to animate the appearance of menu items.
 */
public class MenuAnimation {

    /**
     * Animates the showing or hiding of a menu item.
     *
     * @param component The component to animate.
     * @param item The menu item associated with the component.
     * @param layout The layout manager that controls the component's layout.
     * @param show Whether to show or hide the component.
     */
    public static void showMenu(Component component, MenuItem item, MigLayout layout, boolean show) {
        int height = component.getPreferredSize().height;

        // Create an animator to animate the height change of the component
        Animator animator = new Animator(300, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float f = show ? fraction : 1f - fraction;
                layout.setComponentConstraints(component, "h " + height * f + "!");
                item.setAnimate(f);
                component.revalidate();
                item.repaint();
            }
        });

        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
    } // End showMenu
} // End MenuAnimation