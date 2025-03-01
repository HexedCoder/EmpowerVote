package AdminGUI.menu;

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
import AdminGUI.adminGUIsupport.RippleEffect;
import AdminGUI.adminGUIsupport.ShadowRenderer;
import java.awt.Font;

/**
 * Represents a menu item that can be added to the menu.
 * This class handles the appearance and interaction with a menu item, including ripple effects, shadows, and sub-menu creation.
 *
 * @author Marc
 */
public class MenuItem extends JButton {

    // Instance variables
    private RippleEffect rippleEffect;
    private BufferedImage shadow;
    private int shadowWidth;
    private int shadowSize = 10;
    private int index;
    private boolean subMenuAble;
    private float animate;
    private int subMenuIndex;
    private int length;

    /**
     * Gets the current animation value.
     *
     * @return the animation value.
     */
    public float getAnimate() {
        return animate;
    } // End getAnimate

    /**
     * Sets the animation value.
     *
     * @param animate the animation value.
     */
    public void setAnimate(float animate) {
        this.animate = animate;
    } // End setAnimate

    /**
     * Gets the index of this menu item.
     *
     * @return the index of the menu item.
     */
    public int getIndex() {
        return index;
    } // End getIndex

    /**
     * Sets the index of this menu item.
     *
     * @param index the index of the menu item.
     */
    public void setIndex(int index) {
        this.index = index;
    } // End setIndex

    /**
     * Checks if this menu item can have a sub-menu.
     *
     * @return true if the item can have a sub-menu, false otherwise.
     */
    public boolean isSubMenuAble() {
        return subMenuAble;
    } // End isSubMenuAble

    /**
     * Sets whether this menu item can have a sub-menu.
     *
     * @param subMenuAble true if the item can have a sub-menu, false otherwise.
     */
    public void setSubMenuAble(boolean subMenuAble) {
        this.subMenuAble = subMenuAble;
    } // End setSubMenuAble

    /**
     * Gets the sub-menu index for this menu item.
     *
     * @return the sub-menu index.
     */
    public int getSubMenuIndex() {
        return subMenuIndex;
    } // End getSubMenuIndex

    /**
     * Sets the sub-menu index for this menu item.
     *
     * @param subMenuIndex the sub-menu index.
     */
    public void setSubMenuIndex(int subMenuIndex) {
        this.subMenuIndex = subMenuIndex;
    } // End setSubMenuIndex

    /**
     * Gets the length of the sub-menu items.
     *
     * @return the length of the sub-menu items.
     */
    public int getLength() {
        return length;
    } // End getLength

    /**
     * Sets the length of the sub-menu items.
     *
     * @param length the length of the sub-menu items.
     */
    public void setLength(int length) {
        this.length = length;
    } // End setLength

    /**
     * Constructs a new MenuItem with the specified name, index, and sub-menu availability.
     *
     * @param name the name of the menu item.
     * @param index the index of the menu item.
     * @param subMenuAble whether the menu item can have a sub-menu.
     */
    public MenuItem(String name, int index, boolean subMenuAble){
        super(name);
        this.index = index;
        this.subMenuAble = subMenuAble;
        setContentAreaFilled(false);

        // Increase text size for better readability
        setFont(new Font("sansserif", Font.BOLD, 13)); // Larger font size
        
        setForeground(new Color(230, 230, 230));
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(new EmptyBorder(9, 10, 9, 10));
        rippleEffect = new RippleEffect(this);
        rippleEffect.setRippleColor(new Color(220, 220, 220));
    } // End MenuItem

    /**
     * Creates a shadow image for the menu item.
     */
    private void createShadowImage(){
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
     * Initializes a sub-menu for this menu item.
     *
     * @param subMenuIndex the index of the sub-menu.
     * @param length the length of the sub-menu.
     */
    public void initSubMenu(int subMenuIndex, int length){
        this.subMenuIndex = subMenuIndex;
        this.length = length;
        setBorder(new EmptyBorder(9, 33, 9, 10));
        setBackground(new Color(53,90,125)); //updated color for contrast 
        setOpaque(true);
    } // End initSubMenu

    /**
     * Paints the component for the menu item, including the shadow, borders, and arrow for sub-menu items.
     *
     * @param grphcs the graphics context to paint on.
     */
    @Override
    protected void paintComponent(Graphics grphcs){
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(length != 0){
            g2.setColor(new Color(255, 255, 255));

            if(subMenuIndex == 1){
                // First Index
                g2.drawImage(shadow, -shadowSize, -20, null);
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            } else if(subMenuIndex == length - 1){
                // Last Index
                g2.drawImage(shadow, -shadowSize, getHeight() - 6, null);
                g2.drawLine(18, 0, 18, getHeight() / 2);
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            } else {
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            }
        } else if (subMenuAble){
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
        rippleEffect.render(grphcs, new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
    } // End paintComponent

    /**
     * Sets the bounds of the menu item and creates the shadow image.
     *
     * @param i the x-coordinate of the top-left corner.
     * @param i1 the y-coordinate of the top-left corner.
     * @param i2 the width of the component.
     * @param i3 the height of the component.
     */
    @Override
    public void setBounds(int i, int i1, int i2, int i3){
        super.setBounds(i, i1, i2, i3);
        createShadowImage();
    } // End setBounds
} // End MenuItem
