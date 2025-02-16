package UserGUI.java.userGUIsupport;

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

/**
 * Custom UI for ScrollBar that mimics the style of Windows 11.
 */
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
     * Creates a new UI for the ScrollBar.
     * @param c The component to apply the UI to.
     * @return A new instance of ScrollBarWin11UI.
     */
    public static ComponentUI createUI(JComponent c) {
        return new ScrollBarWin11UI();
    } // End createUI

    /**
     * Constructor for ScrollBarWin11UI. Initializes mouse events and animator.
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
     * Installs the UI on the given component and sets up the initial configuration.
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
     * Starts the animation for showing or hiding the scrollbar.
     * @param show A boolean indicating whether the scrollbar should be shown or hidden.
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
     * Initializes the animator used for the scrollbar's animation.
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
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        animator.setStartDelay(100);
    } // End initAnimator

    /**
     * Creates the button for increasing the scroll position.
     * @param orientation The orientation of the scrollbar.
     * @return The increase button.
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new ScrollButton(scrollbar.getOrientation(), true);
    } // End createIncreaseButton

    /**
     * Creates the button for decreasing the scroll position.
     * @param orientation The orientation of the scrollbar.
     * @return The decrease button.
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new ScrollButton(scrollbar.getOrientation(), false);
    } // End createDecreaseButton

    /**
     * Paints the track of the scrollbar.
     * @param g The graphics context to paint the track.
     * @param c The component the track is part of.
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
     * Paints the thumb of the scrollbar.
     * @param g The graphics context to paint the thumb.
     * @param c The component the thumb is part of.
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

    /**
     * Inner class representing a scroll button.
     */
    private class ScrollButton extends JButton {

        private final int orientation;
        private final boolean isIncrease;
        private final Shape arrow;
        private boolean mouseHover;
        private boolean mousePress;

        /**
         * Constructor for the ScrollButton class.
         * @param orientation The orientation of the scrollbar.
         * @param isIncrease Whether this button is for increasing or decreasing.
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
         * Paints the component.
         * @param g The graphics context to paint the component.
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
    } // End ScrollButton
} // End ScrollBarWin11UI