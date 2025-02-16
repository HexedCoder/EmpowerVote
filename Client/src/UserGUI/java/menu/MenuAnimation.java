package UserGUI.java.menu;

import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * MenuAnimation class handles the animation of menu items' visibility
 * using a smooth transition for showing and hiding submenus.
 */
public class MenuAnimation {

    /**
     * Animates the visibility of the menu component with a smooth transition.
     *
     * @param component The menu component to animate.
     * @param item      The menu item that triggered the animation.
     * @param layout    The layout manager to apply the animation constraints.
     * @param show      A boolean indicating whether to show or hide the component.
     */
    public static void showMenu(Component component, MenuItem item, MigLayout layout, boolean show) {
        int height = component.getPreferredSize().height;

        // Create the animator for the height transition
        Animator animator = new Animator(300, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                // Determine the current fraction based on whether the component is being shown or hidden
                float f = show ? fraction : 1f - fraction;

                // Apply the calculated height to the layout constraints
                layout.setComponentConstraints(component, "h " + height * f + "!");

                // Animate the menu item appearance as well
                item.setAnimate(f);

                // Revalidate and repaint the component and item
                component.revalidate();
                item.repaint();
            }
        });

        // Set animation resolution and easing functions
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);

        // Start the animation
        animator.start();
    } // End showMenu
} // End MenuAnimation