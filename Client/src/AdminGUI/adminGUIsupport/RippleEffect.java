package AdminGUI.adminGUIsupport;

/**
 * The RippleEffect class creates a visual ripple effect when the user clicks on the component.
 * This effect expands outward from the point of click with a fading animation.
 */
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

public class RippleEffect {

    // Instance variables
    private final Component component;
    private Color rippleColor = new Color(255, 255, 255);
    private List<Effect> effects;

    /**
     * Constructs a RippleEffect for the given component.
     *
     * @param component The component that will display the ripple effect.
     */
    public RippleEffect(Component component) {
        this.component = component;
        init();
    } // End constructor

    /**
     * Initializes the effect and adds the mouse listener to the component.
     */
    private void init() {
        effects = new ArrayList<>();
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    addEffect(e.getPoint());
                }
            }
        });
    } // End init

    /**
     * Adds a ripple effect at the given location.
     *
     * @param location The location of the mouse click.
     */
    public void addEffect(Point location) {
        effects.add(new Effect(component, location));
    } // End addEffect

    /**
     * Renders the ripple effect on the component.
     *
     * @param g The Graphics object used for rendering.
     * @param contain The area in which the effect is rendered.
     */
    public void render(Graphics g, Shape contain) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Effect effect : effects) {
            if (effect != null) {
                effect.render(g2, contain);
            }
        }
        g2.dispose();
    } // End render

    private class Effect {

        private final Component component;
        private final Point location;
        private Animator animator;
        private float animate;

        /**
         * Constructs a new Effect at the given location.
         *
         * @param component The component that will display the effect.
         * @param location The location of the mouse click.
         */
        public Effect(Component component, Point location) {
            this.component = component;
            this.location = location;
            init();
        } // End Effect constructor

        /**
         * Initializes the animation for the ripple effect.
         */
        private void init() {
            animator = new Animator(500, new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    animate = fraction;
                    component.repaint();
                }

                @Override
                public void end() {
                    effects.remove(Effect.this);
                }
            });
            animator.setResolution(5);
            animator.setDeceleration(0.5f);
            animator.setAcceleration(0.5f);
            animator.start();
        } // End init

        /**
         * Renders the ripple effect on the given Graphics object.
         *
         * @param g2 The Graphics2D object used for rendering.
         * @param contain The area in which the effect is rendered.
         */
        public void render(Graphics2D g2, Shape contain) {
            Area area = new Area(contain);
            area.intersect(new Area(getShape(getSize(contain.getBounds2D()))));
            g2.setColor(rippleColor);
            float alpha = 0.3f;
            if (animate >= 0.7f) {
                double t = animate - 0.7f;
                alpha = (float) (alpha - (alpha * (t / 0.3f)));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(area);
        } // End render

        /**
         * Calculates the shape of the ripple based on its size.
         *
         * @param size The size of the ripple.
         * @return The shape of the ripple.
         */
        private Shape getShape(double size) {
            double s = size * animate;
            double x = location.getX();
            double y = location.getY();
            return new Ellipse2D.Double(x - s, y - s, s * 2, s * 2);
        } // End getShape

        /**
         * Calculates the size of the ripple effect.
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
            return size + (size * 0.1f);
        } // End getSize
    } // End Effect class

    /**
     * Sets the color of the ripple effect.
     *
     * @param rippleColor The color of the ripple.
     */
    public void setRippleColor(Color rippleColor) {
        this.rippleColor = rippleColor;
    } // End setRippleColor

    /**
     * Gets the current color of the ripple effect.
     *
     * @return The current ripple color.
     */
    public Color getRippleColor() {
        return rippleColor;
    } // End getRippleColor
} // End RippleEffect class