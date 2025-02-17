package AdminGUI.adminGUIchart;

import java.awt.Color;

/**
 * Model class representing a data entry for a polar area chart.
 */
public class ModelPolarArea {

    // Instance variables
    private Color color; // Color for the chart segment
    private String name; // Name of the chart segment
    private double values; // Value of the chart segment

    /**
     * Constructor to initialize the ModelPolarArea with a color, name, and value.
     * @param color The color of the chart segment.
     * @param name The name of the chart segment.
     * @param values The value of the chart segment.
     */
    public ModelPolarArea(Color color, String name, double values) {
        this.color = color;
        this.name = name;
        this.values = values;
    } // End ModelPolarArea constructor

    /**
     * Default constructor.
     */
    public ModelPolarArea() {
    } // End ModelPolarArea

    /**
     * Gets the color of the chart segment.
     * @return The color of the chart segment.
     */
    public Color getColor() {
        return color;
    } // End getColor

    /**
     * Sets the color of the chart segment.
     * @param color The color to be set.
     */
    public void setColor(Color color) {
        this.color = color;
    } // End setColor

    /**
     * Gets the name of the chart segment.
     * @return The name of the chart segment.
     */
    public String getName() {
        return name;
    } // End getName

    /**
     * Sets the name of the chart segment.
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    } // End setName

    /**
     * Gets the value of the chart segment.
     * @return The value of the chart segment.
     */
    public double getValues() {
        return values;
    } // End getValues

    /**
     * Sets the value of the chart segment.
     * @param values The value to be set.
     */
    public void setValues(double values) {
        this.values = values;
    } // End setValues
} // End ModelPolarArea