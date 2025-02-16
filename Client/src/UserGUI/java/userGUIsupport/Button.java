package UserGUI.java.userGUIsupport;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * Button class that extends JButton with ripple effect and animation on click.
 */
public class Button extends JButton {

    // Instance variables
    private Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
    private Color effectColor = new Color(255, 255, 255); // Default white effect color

    /**
     * Gets the effect color of the button.
     *
     * @return The effect color.
     */
    public Color getEffectColor() {
        return effectColor;
    } // End getEffectColor

    /**
     * Sets the effect color of the button.
     *
     * @param effectColor The effect color to set.
     */
    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    } // End setEffectColor

    /**
     * Button constructor to initialize the button properties and animations.
     */
    public Button() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;

                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }
        });

        // Define the animation target behavior
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                repaint();
            }
        };

        // Create the animator with the timing target and define the animation speed
        animator = new Animator(700, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
    } // End Button constructor

    /**
     * Paints the button component with the ripple effect.
     *
     * @param graphics The graphics context used for painting the button.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();

        // Create an image to paint the button background and ripple effect
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill the button with the background color
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, height, height);

        // Draw the ripple effect if the button was pressed
        if (pressedPoint != null) {
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2),
                    (int) animatSize, (int) animatSize);
        }

        g2.dispose();
        graphics.drawImage(img, 0, 0, null);
        super.paintComponent(graphics);
    } // End paintComponent
} // End Button