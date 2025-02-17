package userGUIsupport;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * The RippleEffect class is used to create a ripple animation effect on a component when clicked.
 */
public class RippleEffect {

    // Instance variables
    private final Component component;
    private Color rippleColor = new Color(255, 255, 255);
    private List<Effect> effects;

    /**
     * Constructor for the RippleEffect class.
     * Initializes the effect and adds a mouse listener to capture clicks.
     *
     * @param component The component to apply the ripple effect on.
     */
    public RippleEffect(Component component) {
        this.component = component;
        init(); // Initialize the effect
    } // End RippleEffect

    /**
     * Initializes the list of effects and adds a mouse listener to capture mouse press events.
     */
    private void init() {
        effects = new ArrayList<>();
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    addEffect(e.getPoint()); // Add a new effect at the mouse press location
                }
            }
        });
    } // End init

    /**
     * Adds a ripple effect at the specified location.
     *
     * @param location The point where the ripple effect should be added.
     */
    public void addEffect(Point location) {
        effects.add(new Effect(component, location)); // Add a new effect to the list
    } // End addEffect

    /**
     * Renders the ripple effect on the given graphics context.
     *
     * @param g      The graphics context to render the effect on.
     * @param contain The shape within which the effect should be contained.
     */
    public void render(Graphics g, Shape contain) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            if (effect != null) {
                effect.render(g2, contain); // Render each active effect
            }
        }
        g2.dispose();
    } // End render

    /**
     * Sets the color of the ripple effect.
     *
     * @param rippleColor The color to set for the ripple effect.
     */
    public void setRippleColor(Color rippleColor) {
        this.rippleColor = rippleColor;
    } // End setRippleColor

    /**
     * Gets the current color of the ripple effect.
     *
     * @return The current color of the ripple effect.
     */
    public Color getRippleColor() {
        return rippleColor;
    } // End getRippleColor

    /**
     * The Effect class represents a single ripple effect that animates over time.
     */
    private class Effect {

        private final Component component;
        private final Point location;
        private Animator animator;
        private float animate;

        /**
         * Constructor for the Effect class.
         * Initializes the effect and starts the animation.
         *
         * @param component The component to apply the effect on.
         * @param location The point where the effect originates.
         */
        public Effect(Component component, Point location) {
            this.component = component;
            this.location = location;
            init(); // Initialize the animation
        } // End Effect constructor

        /**
         * Initializes the animator for the effect and starts the animation.
         */
        private void init() {
            animator = new Animator(500, new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    animate = fraction;
                    component.repaint(); // Repaint the component during the animation
                }

                @Override
                public void end() {
                    effects.remove(Effect.this); // Remove the effect when animation ends
                }
            });
            animator.setResolution(5);
            animator.setDeceleration(.5f);
            animator.setAcceleration(.5f);
            animator.start(); // Start the animation
        } // End init

        /**
         * Renders the ripple effect on the given graphics context.
         *
         * @param g2      The graphics context to render the effect on.
         * @param contain The shape within which the effect should be contained.
         */
        public void render(Graphics2D g2, Shape contain) {
            Area area = new Area(contain);
            area.intersect(new Area(getShape(getSize(contain.getBounds2D())))); // Intersect the ripple with the component bounds
            g2.setColor(rippleColor);
            float alpha = 0.3f;
            if (animate >= 0.7f) {
                double t = animate - 0.7f;
                alpha = (float) (alpha - (alpha * (t / 0.3f)));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(area); // Fill the area with the ripple effect
        } // End render

        /**
         * Calculates the shape for the ripple effect based on the size.
         *
         * @param size The size to scale the ripple effect.
         * @return The shape of the ripple effect (ellipse).
         */
        private Shape getShape(double size) {
            double s = size * animate;
            double x = location.getX();
            double y = location.getY();
            Shape shape = new Ellipse2D.Double(x - s, y - s, s * 2, s * 2); // Create an ellipse for the ripple
            return shape;
        } // End getShape

        /**
         * Gets the size of the ripple effect based on the component bounds.
         *
         * @param rec The bounds of the component.
         * @return The size of the ripple effect.
         */
        private double getSize(Rectangle2D rec) {
            double size;
            if (rec.getWidth() > rec.getHeight()) {
                if (location.getX() < rec.getWidth() / 2) {
                    size = rec.getWidth() - location.getX();
                } else {
                    size = location.getX();
                }
            } else {
                if (location.getY() < rec.getHeight() / 2) {
                    size = rec.getHeight() - location.getY();
                } else {
                    size = location.getY();
                }
            }
            return size + (size * 0.1f); // Return the size with a small margin
        } // End getSize
    } // End Effect
} // End RippleEffect