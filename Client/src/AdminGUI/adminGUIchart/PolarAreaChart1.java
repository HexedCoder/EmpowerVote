package AdminGUI.adminGUIchart;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 * Custom component for displaying a polar area chart with animation effects.
 */
public class PolarAreaChart1 extends javax.swing.JComponent {

    // Variables declaration
    private final List<ModelPolarArea> list = new ArrayList<>();  // List to store data for the chart
    private double maxValues;  // Maximum value to scale the chart
    private double totalValues;  // Total sum of all values
    private final int PADDING_BOTTON = 30;  // Padding for bottom space
    private final Animator animator;  // Animator for chart animation
    private float animate;  // Current animation state (fraction)
    public static String theTitle;  // Title of the chart

    private javax.swing.JPanel panel;
    private javax.swing.JLabel title;

    /**
     * Creates a new instance of PolarAreaChart1 and initializes components.
     */
    public PolarAreaChart1() {
        initComponents();
        setBackground(Color.WHITE);
        setForeground(Color.WHITE);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animate = fraction;
                repaint();
            }
        };
        animator = new Animator(800, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    } // End constructor

    /**
     * Paints the chart, rendering the polar area chart and other elements.
     */
    @Override
    public void paint(Graphics grphcs) {
        if (isOpaque()) {
            grphcs.setColor(getBackground());
            grphcs.fillRect(0, 0, getWidth(), getHeight());
        }
        createChart(grphcs);
        super.paint(grphcs);
    } // End paint

    /**
     * Creates the chart with the given graphics.
     */
    private void createChart(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight() - PADDING_BOTTON;
        int space = 5;
        int size = Math.min(width, height) - space;
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!list.isEmpty()) {
            DecimalFormat df = new DecimalFormat("#,##0.##");
            double startAngle = 90;
            for (ModelPolarArea data : list) {
                g2.setColor(data.getColor());
                double angle = valuesToAngle(data.getValues());
                double rs = valuesToRealSize(data.getValues(), size) * animate;
                Shape s = createShape(startAngle, angle, rs);
                g2.fill(s);
                g2.setComposite(AlphaComposite.Clear);
                g2.setStroke(new BasicStroke(3f));
                g2.draw(s);
                g2.setComposite(AlphaComposite.SrcOver);
                startAngle += angle;
                drawValues(g2, df.format(data.getValues()), startAngle - angle / 2, rs / 4);
            }
        } else {
            g2.setColor(new Color(200, 200, 200));
            g2.drawOval(x, y, size, size);
        }
        g2.dispose();
        grphcs.drawImage(img, 0, 0, null);
    } // End createChart

    /**
     * Draws the values of the chart at the correct position.
     */
    private void drawValues(Graphics2D g2, String values, double angle, double rs) {
        int centerx = getWidth() / 2;
        int centery = (getHeight() - PADDING_BOTTON) / 2;
        Point p = getLocation(angle, rs);
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(values, g2);
        double x = (centerx + p.x) - (r2.getWidth() / 2);
        double y = (centery - p.y) + (ft.getAscent() / 2);
        g2.drawString(values, (int) x, (int) y);
    } // End drawValues

    /**
     * Creates a shape for the chart slice based on the angle and size.
     */
    private Shape createShape(double start, double end, double values) {
        int width = getWidth();
        int height = getHeight();
        double x = (width - values) / 2;
        double y = (height - values) / 2;
        Shape shape = new Arc2D.Double(x, y, values, values, start, end, Arc2D.PIE);
        return shape;
    } // End createShape

    /**
     * Converts the values to a real size for rendering the chart.
     */
    private double valuesToRealSize(double values, int size) {
        double n = values * 100 / maxValues;
        return n * size / 100;
    } // End valuesToRealSize

    /**
     * Converts the values to an angle for rendering the chart slice.
     */
    private double valuesToAngle(double values) {
        double n = values * 100 / totalValues;
        return n * 360 / 100;
    } // End valuesToAngle

    /**
     * Calculates the location of a point based on the angle and radius.
     */
    private Point getLocation(double angle, double rs) {
        double x = Math.cos(Math.toRadians(angle)) * rs;
        double y = Math.sin(Math.toRadians(angle)) * rs;
        return new Point((int) x, (int) y);
    } // End getLocation

    /**
     * Calculates the maximum values and total values from the data list.
     */
    private void calculateValues(ModelPolarArea data) {
        maxValues = Math.max(maxValues, data.getValues());
        totalValues = 0;
        for (ModelPolarArea n : list) {
            totalValues += n.getValues();
        }
    } // End calculateValues

    /**
     * Adds a new item to the chart.
     */
    public void addItem(ModelPolarArea data) {
        list.add(data);
        calculateValues(data);
        repaint();
        PolarAreaLabel label = new PolarAreaLabel();
        label.setText(data.getName());
        label.setBackground(data.getColor());
        panel.add(label);
        panel.repaint();
        panel.revalidate();
    } // End addItem

    /**
     * Starts the chart animation.
     */
    public void start() {
        if (!animator.isRunning()) {
            animator.start();
        }
    } // End start

    /**
     * Clears all data from the chart.
     */
    public void clear() {
        list.clear();
        panel.removeAll();
        panel.repaint();
        panel.revalidate();
        repaint();
    } // End clear

    /**
     * Sets the title for the chart.
     */
    public void setTitle(String theTitle) {
        title.setText(theTitle);
    } // End setTitle

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        title = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        panel.setOpaque(false);
        panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        title.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Elections");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    } // End initComponents
} // End PolarAreaChart1