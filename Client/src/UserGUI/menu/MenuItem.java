package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import userGUIsupport.RippleEffect;
import userGUIsupport.ShadowRenderer;

/**
 * MenuItem class represents an individual item in the menu.
 * It supports ripple effect, shadow rendering, and submenu handling.
 */
public class MenuItem extends JButton {

    // Variables for menu item properties
    private int index;
    private boolean subMenuAble;
    private float animate;
    private int subMenuIndex;
    private int length;

    // Graphics and visual effects variables
    private RippleEffect rippleEffect;
    private BufferedImage shadow;
    private int shadowSize = 10;
    private int shadowWidth;

    // Getter and Setter methods
    public float getAnimate() {
        return animate;
    }

    public void setAnimate(float animate) {
        this.animate = animate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isSubMenuAble() {
        return subMenuAble;
    }

    public void setSubMenuAble(boolean subMenuAble) {
        this.subMenuAble = subMenuAble;
    }

    public int getSubMenuIndex() {
        return subMenuIndex;
    }

    public void setSubMenuIndex(int subMenuIndex) {
        this.subMenuIndex = subMenuIndex;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Constructor for MenuItem.
     *
     * @param name         The name of the menu item.
     * @param index        The index of the menu item.
     * @param subMenuAble  Indicates whether the item can have a submenu.
     */
    public MenuItem(String name, int index, boolean subMenuAble) {
        super(name);
        this.index = index;
        this.subMenuAble = subMenuAble;
        setContentAreaFilled(false);
        setForeground(new Color(230, 230, 230));
        
        // Increase text size for better readability
        setFont(new Font("sansserif", Font.BOLD, 13)); // Larger font size
        
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(new EmptyBorder(9, 10, 9, 10));

        // Initialize ripple effect
        rippleEffect = new RippleEffect(this);
        rippleEffect.setRippleColor(new Color(220, 220, 220));
    } // End MenuItem

    /**
     * Creates the shadow image for the menu item.
     */
    private void createShadowImage() {
        int width = getWidth();
        int height = 5;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Double(0, 0, width, height));
        shadow = new ShadowRenderer(shadowSize, 0.2f, Color.BLACK).createShadow(img);
        g2.dispose();
    } // End createShadowImage

    /**
     * Initializes the submenu with specified index and length.
     *
     * @param subMenuIndex The index of the submenu.
     * @param length       The length of the submenu.
     */
    public void initSubMenu(int subMenuIndex, int length) {
        this.subMenuIndex = subMenuIndex;
        this.length = length;
        setBorder(new EmptyBorder(12, 35, 12, 15)); // Increased padding
        setBackground(new Color(53,90,125));

        // Change sub-menu text to black for better contrast
        setForeground(Color.BLACK);
        
        setOpaque(true);
    } // End initSubMenu

    /**
     * Paints the component with specific visual effects such as shadow and submenu arrows.
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw submenu item shadow and lines
        if (length != 0) {
            g2.setColor(new Color(255, 255, 255));
            if (subMenuIndex == 1) {
                g2.drawImage(shadow, -shadowSize, -20, null);
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            } else if (subMenuIndex == length - 1) {
                g2.drawImage(shadow, -shadowSize, getHeight() - 6, null);
                g2.drawLine(18, 0, 18, getHeight() / 2);
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            } else {
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            }
        } else if (subMenuAble) {
            // Draw arrow for submenu
            g2.setColor(getForeground());
            int arrowWidth = 8;
            int arrowHeight = 4;
            Path2D p = new Path2D.Double();
            p.moveTo(0, arrowHeight * animate);
            p.lineTo(arrowWidth / 2, (1f - animate) * arrowHeight);
            p.lineTo(arrowWidth, arrowHeight * animate);
            g2.translate(getWidth() - arrowWidth - 15, (getHeight() - arrowHeight) / 2);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.draw(p);
        }

        g2.dispose();

        // Ensure that the ripple effect is drawn
        rippleEffect.render(grphcs, new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
    } // End paintComponent

    /**
     * Overrides setBounds to update the shadow image when the bounds change.
     */
    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        createShadowImage();
    } // End setBounds
} // End MenuItem
