package UserGUI.java.userGUIsupport;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * The PolygonCorner class is used to generate rounded polygons from a list of points,
 * applying an arc size for rounded corners.
 */
public class PolygonCorner {

    /**
     * Generates a rounded polygon from a list of points by calculating the rounded corner
     * using the specified arc size.
     *
     * @param l       List of points that define the polygon.
     * @param arcSize The arc size for the rounded corners.
     * @return A GeneralPath object representing the rounded polygon.
     */
    public GeneralPath getRoundedGeneralPathFromPoints(List<Point2D> l, float arcSize) {
        l.add(l.get(0)); // Add the first point to close the polygon
        l.add(l.get(1)); // Add the second point for calculations

        GeneralPath p = new GeneralPath(); // Create a new GeneralPath for the polygon
        Point2D startPoint = calculatePoint(l.get(l.size() - 1), l.get(l.size() - 2), arcSize);
        p.moveTo(startPoint.getX(), startPoint.getY()); // Move to the starting point

        // Iterate through the points to create the path with rounded corners
        for (int pointIndex = 1; pointIndex < l.size() - 1; pointIndex++) {
            Point2D p1 = l.get(pointIndex - 1);
            Point2D p2 = l.get(pointIndex);
            Point2D p3 = l.get(pointIndex + 1);

            Point2D mPoint = calculatePoint(p1, p2, arcSize);
            p.lineTo(mPoint.getX(), mPoint.getY()); // Draw line to the rounded corner
            mPoint = calculatePoint(p3, p2, arcSize);
            p.curveTo(p2.getX(), p2.getY(), p2.getX(), p2.getY(), mPoint.getX(), mPoint.getY()); // Draw curve for the rounded corner
        }
        return p; // Return the GeneralPath of the rounded polygon
    } // End getRoundedGeneralPathFromPoints

    /**
     * Calculates a new point based on the distance between two points and the arc size.
     *
     * @param p1      The first point.
     * @param p2      The second point.
     * @param arcSize The arc size for the rounded corner.
     * @return A Point2D object representing the calculated point.
     */
    private Point2D calculatePoint(Point2D p1, Point2D p2, float arcSize) {
        double d1 = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2f) + Math.pow(p1.getY() - p2.getY(), 2f)); // Calculate the distance between the points
        double per = arcSize / d1; // Calculate the proportion based on arcSize and distance
        double d_x = (p1.getX() - p2.getX()) * per; // Calculate the difference in x direction
        double d_y = (p1.getY() - p2.getY()) * per; // Calculate the difference in y direction
        double xx = p2.getX() + d_x; // Calculate the new x coordinate
        double yy = p2.getY() + d_y; // Calculate the new y coordinate
        return new Point.Double(xx, yy); // Return the new calculated point
    } // End calculatePoint
} // End PolygonCorner