package LoginGUI.java.support;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Font;
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
 * Button class that extends JButton with custom animation effects on press.
 * <p>
 * The button animates a circular effect when pressed, with configurable effect color.
 *
 * @author Marc
 */
public class Button extends JButton {

    private Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
    private Color effectColor = new Color(255, 255, 255);

    /**
     * Gets the effect color for the button press animation.
     *
     * @return the effect color
     */
    public Color getEffectColor() {
        return effectColor;
    } // getEffectColor

    /**
     * Sets the effect color for the button press animation.
     *
     * @param effectColor the color to set for the effect
     */
    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    } // setEffectColor

    /**
     * Constructs a Button with custom animation effects.
     */
    public Button() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                // Initialize animation parameters when mouse is pressed
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            } // mousePressed
        });

        // Define the timing behavior for the animation
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                repaint(); // Trigger repaint during animation
            } // timingEvent
        };

        // Set up the animator
        animator = new Animator(700, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
    } // Button

    /**
     * Custom painting of the button, including the animation effect.
     *
     * @param graphics the graphics context
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, height, height); // Draw rounded rectangle

        // Dynamically adjust font size based on button height
        int fontSize = Math.max(12, height / 3); // Ensure a minimum font size of 12
        setFont(new Font("sansserif", Font.BOLD, fontSize));
        
        if (pressedPoint != null) {
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize); // Draw animated circle
        }
        g2.dispose();
        graphics.drawImage(img, 0, 0, null);
        super.paintComponent(graphics); // Call the superclass method for default rendering
    } // paintComponent
} // Button
