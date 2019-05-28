package org.jfree.chart.renderer;

import org.jfree.chart.ui.RectangleEdge;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public abstract class GradientBarPainterParent {


    /**
     * Creates a shadow for the bar.
     *
     * @param bar  the bar shape.
     * @param xOffset  the x-offset for the shadow.
     * @param yOffset  the y-offset for the shadow.
     * @param base  the edge that is the base of the bar.
     * @param pegShadow  peg the shadow to the base?
     *
     * @return A rectangle for the shadow.
     */
    protected Rectangle2D createShadow(RectangularShape bar, double xOffset,
                                     double yOffset, RectangleEdge base, boolean pegShadow) {
        double x0 = bar.getMinX();
        double x1 = bar.getMaxX();
        double y0 = bar.getMinY();
        double y1 = bar.getMaxY();
        if (base == RectangleEdge.TOP) {
            x0 += xOffset;
            x1 += xOffset;
            if (!pegShadow) {
                y0 += yOffset;
            }
            y1 += yOffset;
        }
        else if (base == RectangleEdge.BOTTOM) {
            x0 += xOffset;
            x1 += xOffset;
            y0 += yOffset;
            if (!pegShadow) {
                y1 += yOffset;
            }
        }
        else if (base == RectangleEdge.LEFT) {
            if (!pegShadow) {
                x0 += xOffset;
            }
            x1 += xOffset;
            y0 += yOffset;
            y1 += yOffset;
        }
        else if (base == RectangleEdge.RIGHT) {
            x0 += xOffset;
            if (!pegShadow) {
                x1 += xOffset;
            }
            y0 += yOffset;
            y1 += yOffset;
        }
        return new Rectangle2D.Double(x0, y0, (x1 - x0), (y1 - y0));
    }

    /**
     * Splits a bar into subregions (elsewhere, these subregions will have
     * different gradients applied to them).
     *
     * @param bar  the bar shape.
     * @param a  the first division.
     * @param b  the second division.
     * @param c  the third division.
     *
     * @return An array containing four subregions.
     */
    protected Rectangle2D[] splitVerticalBar(RectangularShape bar, double a,
                                           double b, double c) {
        Rectangle2D[] result = new Rectangle2D[4];
        double x0 = bar.getMinX();
        double x1 = Math.rint(x0 + (bar.getWidth() * a));
        double x2 = Math.rint(x0 + (bar.getWidth() * b));
        double x3 = Math.rint(x0 + (bar.getWidth() * c));
        result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(),
                x1 - x0, bar.getHeight());
        result[1] = new Rectangle2D.Double(x1, bar.getMinY(), x2 - x1,
                bar.getHeight());
        result[2] = new Rectangle2D.Double(x2, bar.getMinY(), x3 - x2,
                bar.getHeight());
        result[3] = new Rectangle2D.Double(x3, bar.getMinY(),
                bar.getMaxX() - x3, bar.getHeight());
        return result;
    }

    /**
     * Splits a bar into subregions (elsewhere, these subregions will have
     * different gradients applied to them).
     *
     * @param bar  the bar shape.
     * @param a  the first division.
     * @param b  the second division.
     * @param c  the third division.
     *
     * @return An array containing four subregions.
     */
    protected Rectangle2D[] splitHorizontalBar(RectangularShape bar, double a,
                                             double b, double c) {
        Rectangle2D[] result = new Rectangle2D[4];
        double y0 = bar.getMinY();
        double y1 = Math.rint(y0 + (bar.getHeight() * a));
        double y2 = Math.rint(y0 + (bar.getHeight() * b));
        double y3 = Math.rint(y0 + (bar.getHeight() * c));
        result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(),
                bar.getWidth(), y1 - y0);
        result[1] = new Rectangle2D.Double(bar.getMinX(), y1, bar.getWidth(),
                y2 - y1);
        result[2] = new Rectangle2D.Double(bar.getMinX(), y2, bar.getWidth(),
                y3 - y2);
        result[3] = new Rectangle2D.Double(bar.getMinX(), y3, bar.getWidth(),
                bar.getMaxY() - y3);
        return result;
    }
}
