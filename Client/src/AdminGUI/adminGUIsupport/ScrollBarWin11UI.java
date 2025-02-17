package AdminGUI.adminGUIsupport;

/**
 * A custom ScrollBarUI implementation that mimics the Windows 11 scroll bar.
 * It includes animations for hover, press, and scrollbar thumb movement.
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class ScrollBarWin11UI extends BasicScrollBarUI {

    // Instance variables
    private Animator animator;
    private float animate;
    private boolean show;
    private boolean hover;
    private boolean press;
    private final int scrollSize = 10;
    private final MouseAdapter mouseEvent;

    /**
     * Factory method to create a new instance of ScrollBarWin11UI.
     *
     * @param c The component for which the UI is created.
     * @return The custom ScrollBar UI instance.
     */
    public static ComponentUI createUI(JComponent c) {
        return new ScrollBarWin11UI();
    } // End createUI

    /**
     * Constructor that initializes the mouse events for hover and press actions.
     */
    public ScrollBarWin11UI() {
        mouseEvent = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                press = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
                if (!hover) {
                    start(false);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                if (!show) {
                    start(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                if (!press) {
                    start(false);
                }
            }
        };
    } // End ScrollBarWin11UI constructor

    /**
     * Installs the custom UI on the given component.
     *
     * @param c The component to install the UI on.
     */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setPreferredSize(new Dimension(scrollSize, scrollSize));
        c.addMouseListener(mouseEvent);
        c.setForeground(new Color(220, 220, 220));
        initAnimator();
    } // End installUI

    /**
     * Starts the animation for showing or hiding the scrollbar thumb.
     *
     * @param show Whether to show the scrollbar thumb.
     */
    private void start(boolean show) {
        if (animator.isRunning()) {
            float f = animator.getTimingFraction();
            animator.stop();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0f);
        }
        this.show = show;
        animator.start();
    } // End start

    /**
     * Initializes the animator to handle the animation for the scrollbar thumb.
     */
    private void initAnimator() {
        animator = new Animator(350, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (show) {
                    animate = fraction;
                } else {
                    animate = 1f - fraction;
                }
                if (scrollbar != null) {
                    scrollbar.repaint();
                }
            }
        });
        animator.setResolution(5);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setStartDelay(100);
    } // End initAnimator

    /**
     * Creates the button to increase the scrollbar value.
     *
     * @param orientation The orientation of the scrollbar.
     * @return The button to increase the scrollbar value.
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new ScrollButton(scrollbar.getOrientation(), true);
    } // End createIncreaseButton

    /**
     * Creates the button to decrease the scrollbar value.
     *
     * @param orientation The orientation of the scrollbar.
     * @return The button to decrease the scrollbar value.
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new ScrollButton(scrollbar.getOrientation(), false);
    } // End createDecreaseButton

    /**
     * Paints the scrollbar track.
     *
     * @param g The graphics object used for painting.
     * @param c The component on which the track is painted.
     * @param trackBounds The bounds of the track.
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.SrcOver.derive(animate * 0.08f));
        g2.setColor(scrollbar.getForeground().brighter());
        g2.fill(new Rectangle2D.Double(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height));
        g2.dispose();
    } // End paintTrack

    /**
     * Paints the scrollbar thumb.
     *
     * @param g The graphics object used for painting.
     * @param c The component on which the thumb is painted.
     * @param thumbBounds The bounds of the thumb.
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(scrollbar.getForeground());
        double border = scrollSize * 0.3f - (animate * 1);
        double sp = 10 * animate;
        g2.setComposite(AlphaComposite.SrcOver.derive(1f - (1f - animate * 0.1f) * 0.6f));
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            double width = thumbBounds.getWidth() - border * 2;
            double height = thumbBounds.getHeight() - sp * 2;
            g2.fill(new RoundRectangle2D.Double(thumbBounds.x + border, thumbBounds.y + sp, width, height, width, width));
        } else {
            double width = thumbBounds.getWidth() - sp * 2;
            double height = thumbBounds.getHeight() - border * 2;
            g2.fill(new RoundRectangle2D.Double(thumbBounds.x + sp, thumbBounds.y + border, width, height, height, height));
        }
        g2.dispose();
        g.dispose();
    } // End paintThumb

    private class ScrollButton extends JButton {

        private final int orientation;
        private final boolean isIncrease;
        private final Shape arrow;
        private boolean mouseHover;
        private boolean mousePress;

        /**
         * Constructs a ScrollButton for increasing or decreasing the scrollbar value.
         *
         * @param orientation The orientation of the scrollbar.
         * @param isIncrease Whether the button increases or decreases the scrollbar value.
         */
        public ScrollButton(int orientation, boolean isIncrease) {
            this.orientation = orientation;
            this.isIncrease = isIncrease;
            setContentAreaFilled(false);
            setPreferredSize(new Dimension(scrollSize, scrollSize));
            List<Point2D> points = new ArrayList<>();
            double width = scrollSize * 0.8f;
            double height = scrollSize * 0.7f;
            if (orientation == JScrollBar.VERTICAL) {
                if (isIncrease) {
                    points.add(new Point2D.Double(width / 2, height));
                    points.add(new Point2D.Double(width, 0));
                    points.add(new Point2D.Double(0, 0));
                } else {
                    points.add(new Point2D.Double(width / 2, 0));
                    points.add(new Point2D.Double(width, height));
                    points.add(new Point2D.Double(0, height));
                }
            } else {
                if (isIncrease) {
                    points.add(new Point2D.Double(0, 0));
                    points.add(new Point2D.Double(width, height / 2));
                    points.add(new Point2D.Double(0, height));
                } else {
                    points.add(new Point2D.Double(width, 0));
                    points.add(new Point2D.Double(0, height / 2));
                    points.add(new Point2D.Double(width, height));
                }
            }
            arrow = new PolygonCorner().getRoundedGeneralPathFromPoints(points, scrollSize * 0.5f);
            addMouseListener(mouseEvent);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    mouseHover = true;
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mouseHover = false;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    mousePress = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    mousePress = false;
                }
            });
        } // End ScrollButton constructor

        /**
         * Paints the ScrollButton.
         *
         * @param g The graphics object used for painting.
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setComposite(AlphaComposite.SrcOver.derive(animate * 0.08f));
            g2.setColor(scrollbar.getForeground().brighter());
            int x = 0;
            int y = isIncrease ? 8 : 0;
            int width = getWidth();
            int height = getHeight() - 8;
            if (orientation == JScrollBar.VERTICAL) {
                g2.fill(new Rectangle2D.Double(x, y, width, height));
            } else {
                g2.fill(new Rectangle2D.Double(y, x, height, width));
            }
            g2.setComposite(AlphaComposite.SrcOver.derive(animate));
            if (mousePress) {
                g2.setColor(new Color(180, 180, 180));
            } else if (mouseHover) {
                g2.setColor(new Color(200, 200, 200));
            } else {
                g2.setColor(scrollbar.getForeground());
            }
            double ax = scrollSize * 0.1f;
            double ay = scrollSize * 0.15f;
            g2.translate(ax, ay);
            g2.fill(arrow);
            g2.dispose();
            super.paintComponent(g);
        } // End paintComponent
    } // End ScrollButton class
} // End ScrollBarWin11UI class