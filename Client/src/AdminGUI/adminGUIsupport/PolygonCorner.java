package AdminGUI.adminGUIsupport;

/**
 * The PolygonCorner class is responsible for creating a rounded polygon path based on a list of points.
 * It calculates rounded corners for a polygon shape using an arc size.
 */
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

public class PolygonCorner {

    // Method to generate a rounded general path from a list of points and arc size
    /**
     * Generates a rounded polygonal path from a list of points with a specified arc size for the corners.
     *
     * @param l The list of points to create the polygon path.
     * @param arcSize The size of the rounded corners.
     * @return A GeneralPath representing the rounded polygon.
     */
    public GeneralPath getRoundedGeneralPathFromPoints(List<Point2D> l, float arcSize) {
        l.add(l.get(0));  // Close the polygon by adding the first point at the end
        l.add(l.get(1));  // Duplicate second point for correct corner calculation

        GeneralPath p = new GeneralPath();
        Point2D startPoint = calculatePoint(l.get(l.size() - 1), l.get(l.size() - 2), arcSize);
        p.moveTo(startPoint.getX(), startPoint.getY());

        // Iterate over the list of points to form the rounded path
        for (int pointIndex = 1; pointIndex < l.size() - 1; pointIndex++) {
            Point2D p1 = l.get(pointIndex - 1);
            Point2D p2 = l.get(pointIndex);
            Point2D p3 = l.get(pointIndex + 1);

            // Calculate and add rounded corner between p1 and p2
            Point2D mPoint = calculatePoint(p1, p2, arcSize);
            p.lineTo(mPoint.getX(), mPoint.getY());

            // Create curve for the rounded corner between p2 and p3
            mPoint = calculatePoint(p3, p2, arcSize);
            p.curveTo(p2.getX(), p2.getY(), p2.getX(), p2.getY(), mPoint.getX(), mPoint.getY());
        }
        return p;
    } // End getRoundedGeneralPathFromPoints

    // Method to calculate the new point for a rounded corner
    /**
     * Calculates a new point for a rounded corner between two points with a specified arc size.
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @param arcSize The size of the arc for the rounded corner.
     * @return A new Point2D representing the calculated point for the rounded corner.
     */
    private Point2D calculatePoint(Point2D p1, Point2D p2, float arcSize) {
        double d1 = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2f) + Math.pow(p1.getY() - p2.getY(), 2f));
        double per = arcSize / d1;
        double d_x = (p1.getX() - p2.getX()) * per;
        double d_y = (p1.getY() - p2.getY()) * per;
        double xx = p2.getX() + d_x;
        double yy = p2.getY() + d_y;
        return new Point.Double(xx, yy);
    } // End calculatePoint
} // End PolygonCorner