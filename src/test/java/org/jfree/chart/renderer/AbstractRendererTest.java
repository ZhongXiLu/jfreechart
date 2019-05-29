/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * -------------------------
 * AbstractRendererTest.java
 * -------------------------
 * (C) Copyright 2003-2016, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Oct-2003 : Version 1 (DG);
 * 01-Mar-2004 : Added serialization test (DG);
 * 19-Feb-2007 : Added testCloning (DG);
 * 28-Feb-2007 : Added checks for cloning (DG);
 * 17-Jun-2008 : Added new fields to testEquals() and testCloning() (DG);
 * 28-Jan-2009 : Updated testEquals() (DG);
 * 28-Apr-2009 : Updated testEquals() (DG);
 * 25-Apr-2016 : Update testClone() (DG);
 *
 */

package org.jfree.chart.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jfree.chart.plot.*;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.*;

import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.TextAnchor;

/**
 * Tests for the {@link AbstractRenderer} class.
 */
public class AbstractRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        // have to use a concrete subclass...
        BarRenderer r1 = new BarRenderer();
        BarRenderer r2 = new BarRenderer();
        assertTrue(r1.equals(r1));
        assertFalse(r1.equals(new XYPlot()));
        assertTrue(r1.equals(r2));
        assertTrue(r2.equals(r1));
        r2.setDataBoundsIncludesVisibleSeriesOnly(!r1.getDataBoundsIncludesVisibleSeriesOnly());
        assertFalse(r1.equals(r2));
        r2.setDataBoundsIncludesVisibleSeriesOnly(r1.getDataBoundsIncludesVisibleSeriesOnly());
        assertTrue(r1.equals(r2));

        // seriesVisibleList
        r1.setSeriesVisible(2, Boolean.TRUE);
        assertFalse(r1.equals(r2));
        r2.setSeriesVisible(2, Boolean.TRUE);
        assertTrue(r1.equals(r2));

        // defaultSeriesVisible
        r1.setDefaultSeriesVisible(false);
        assertFalse(r1.equals(r2));
        r2.setDefaultSeriesVisible(false);
        assertTrue(r1.equals(r2));

        // seriesVisibleInLegendList
        r1.setSeriesVisibleInLegend(1, Boolean.TRUE);
        assertFalse(r1.equals(r2));
        r2.setSeriesVisibleInLegend(1, Boolean.TRUE);
        assertTrue(r1.equals(r2));

        // defaultSeriesVisibleInLegend
        r1.setDefaultSeriesVisibleInLegend(false);
        assertFalse(r1.equals(r2));
        r2.setDefaultSeriesVisibleInLegend(false);
        assertTrue(r1.equals(r2));

        // paintList
        r1.setSeriesPaint(0, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.WHITE));
        assertFalse(r1.equals(r2));
        r2.setSeriesPaint(0, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.WHITE));
        assertTrue(r1.equals(r2));

        // defaultPaint
        r1.setDefaultPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertFalse(r1.equals(r2));
        r2.setDefaultPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertTrue(r1.equals(r2));

        // fillPaintList
        r1.setSeriesFillPaint(0, new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertFalse(r1.equals(r2));
        r2.setSeriesFillPaint(0, new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertTrue(r1.equals(r2));

        // defaultFillPaint
        r1.setDefaultFillPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertFalse(r1.equals(r2));
        r2.setDefaultFillPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertTrue(r1.equals(r2));

        // outlinePaintList
        r1.setSeriesOutlinePaint(0, new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertFalse(r1.equals(r2));
        r2.setSeriesOutlinePaint(0, new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertTrue(r1.equals(r2));

        // defaultOutlinePaint
        r1.setDefaultOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertFalse(r1.equals(r2));
        r2.setDefaultOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertTrue(r1.equals(r2));

        // stroke
        Stroke s = new BasicStroke(3.21f);

        // strokeList
        r1.setSeriesStroke(1, s);
        assertFalse(r1.equals(r2));
        r2.setSeriesStroke(1, s);
        assertTrue(r1.equals(r2));

        // defaultStroke
        r1.setDefaultStroke(s);
        assertFalse(r1.equals(r2));
        r2.setDefaultStroke(s);
        assertTrue(r1.equals(r2));

        // outlineStrokeList
        r1.setSeriesOutlineStroke(0, s);
        assertFalse(r1.equals(r2));
        r2.setSeriesOutlineStroke(0, s);
        assertTrue(r1.equals(r2));

        // defaultOutlineStroke
        r1.setDefaultOutlineStroke(s);
        assertFalse(r1.equals(r2));
        r2.setDefaultOutlineStroke(s);
        assertTrue(r1.equals(r2));

        // shapeList
        r1.setSeriesShape(1, new Ellipse2D.Double(1, 2, 3, 4));
        assertFalse(r1.equals(r2));
        r2.setSeriesShape(1, new Ellipse2D.Double(1, 2, 3, 4));
        assertTrue(r1.equals(r2));

        // defaultShape
        r1.setDefaultShape(new Ellipse2D.Double(1, 2, 3, 4));
        assertFalse(r1.equals(r2));
        r2.setDefaultShape(new Ellipse2D.Double(1, 2, 3, 4));
        assertTrue(r1.equals(r2));

        // itemLabelsVisibleList
        r1.setSeriesItemLabelsVisible(1, Boolean.TRUE);
        assertFalse(r1.equals(r2));
        r2.setSeriesItemLabelsVisible(1, Boolean.TRUE);
        assertTrue(r1.equals(r2));

        // baseItemLabelsVisible
        r1.setDefaultItemLabelsVisible(true);
        assertFalse(r1.equals(r2));
        r2.setDefaultItemLabelsVisible(true);
        assertTrue(r1.equals(r2));

        // itemLabelFontList
        r1.setSeriesItemLabelFont(1, new Font("Serif", Font.BOLD, 9));
        assertFalse(r1.equals(r2));
        r2.setSeriesItemLabelFont(1, new Font("Serif", Font.BOLD, 9));
        assertTrue(r1.equals(r2));

        // defaultItemLabelFont
        r1.setDefaultItemLabelFont(new Font("Serif", Font.PLAIN, 10));
        assertFalse(r1.equals(r2));
        r2.setDefaultItemLabelFont(new Font("Serif", Font.PLAIN, 10));
        assertTrue(r1.equals(r2));

        // itemLabelPaintList
        r1.setSeriesItemLabelPaint(0, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.GRAY));
        assertFalse(r1.equals(r2));
        r2.setSeriesItemLabelPaint(0, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.GRAY));
        assertTrue(r1.equals(r2));

        // defaultItemLabelPaint
        r1.setDefaultItemLabelPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.GRAY));
        assertFalse(r1.equals(r2));
        r2.setDefaultItemLabelPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.GRAY));
        assertTrue(r1.equals(r2));

        // positiveItemLabelPositionList;
        r1.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition());
        assertFalse(r1.equals(r2));
        r2.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition());
        assertTrue(r1.equals(r2));

        // defaultPositiveItemLabelPosition;
        r1.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_RIGHT));
        assertFalse(r1.equals(r2));
        r2.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_RIGHT));
        assertTrue(r1.equals(r2));

        // negativeItemLabelPositionList;
        r1.setSeriesNegativeItemLabelPosition(1, new ItemLabelPosition(
                ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_RIGHT));
        assertFalse(r1.equals(r2));
        r2.setSeriesNegativeItemLabelPosition(1, new ItemLabelPosition(
                ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_RIGHT));
        assertTrue(r1.equals(r2));

        // defaultNegativeItemLabelPosition;
        r1.setDefaultNegativeItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_RIGHT));
        assertFalse(r1.equals(r2));
        r2.setDefaultNegativeItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE10, TextAnchor.BASELINE_RIGHT));
        assertTrue(r1.equals(r2));

        // itemLabelAnchorOffset
        r1.setItemLabelAnchorOffset(3.0);
        assertFalse(r1.equals(r2));
        r2.setItemLabelAnchorOffset(3.0);
        assertTrue(r1.equals(r2));

        // createEntitiesList;
        r1.setSeriesCreateEntities(0, Boolean.TRUE);
        assertFalse(r1.equals(r2));
        r2.setSeriesCreateEntities(0, Boolean.TRUE);
        assertTrue(r1.equals(r2));

        // baseCreateEntities;
        r1.setDefaultCreateEntities(false);
        assertFalse(r1.equals(r2));
        r2.setDefaultCreateEntities(false);
        assertTrue(r1.equals(r2));

        // legendShape
        r1.setLegendShape(0, new Ellipse2D.Double(1.0, 2.0, 3.0, 4.0));
        assertFalse(r1.equals(r2));
        r2.setLegendShape(0, new Ellipse2D.Double(1.0, 2.0, 3.0, 4.0));
        assertTrue(r1.equals(r2));

        // baseLegendShape
        r1.setDefaultLegendShape(new Ellipse2D.Double(5.0, 6.0, 7.0, 8.0));
        assertFalse(r1.equals(r2));
        r2.setDefaultLegendShape(new Ellipse2D.Double(5.0, 6.0, 7.0, 8.0));
        assertTrue(r1.equals(r2));

        // legendTextFont
        r1.setLegendTextFont(0, new Font("Dialog", Font.PLAIN, 7));
        assertFalse(r1.equals(r2));
        r2.setLegendTextFont(0, new Font("Dialog", Font.PLAIN, 7));
        assertTrue(r1.equals(r2));

        // defaultLegendTextFont
        r1.setDefaultLegendTextFont(new Font("Dialog", Font.PLAIN, 7));
        assertFalse(r1.equals(r2));
        r2.setDefaultLegendTextFont(new Font("Dialog", Font.PLAIN, 7));
        assertTrue(r1.equals(r2));

        // legendTextPaint
        r1.setLegendTextPaint(0, new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertFalse(r1.equals(r2));
        r2.setLegendTextPaint(0, new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertTrue(r1.equals(r2));

        // defaultOutlinePaint
        r1.setDefaultLegendTextPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertFalse(r1.equals(r2));
        r2.setDefaultLegendTextPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertTrue(r1.equals(r2));
    }

    @Test
    public void testEquals_ObjectList() {
        BarRenderer r1 = new BarRenderer();
        r1.setSeriesItemLabelFont(0, new Font(Font.DIALOG, Font.BOLD, 10));
        BarRenderer r2 = new BarRenderer();
        r2.setSeriesItemLabelFont(0, new Font(Font.DIALOG, Font.BOLD, 10));
        assertEquals(r1, r2);
        r2.setSeriesItemLabelFont(1, new Font(Font.DIALOG, Font.PLAIN, 5));
        assertNotEquals(r1, r2);
    }
    
    @Test
    public void testEquals_ObjectList2() {
        BarRenderer r1 = new BarRenderer();
        r1.setLegendTextFont(0, new Font(Font.DIALOG, Font.BOLD, 10));
        BarRenderer r2 = new BarRenderer();
        r2.setLegendTextFont(0, new Font(Font.DIALOG, Font.BOLD, 10));
        assertEquals(r1, r2);
        r2.setLegendTextFont(1, new Font(Font.DIALOG, Font.PLAIN, 5));
        assertNotEquals(r1, r2);
    }

    @Test
    public void testEquals_ObjectList3() {
        BarRenderer r1 = new BarRenderer();
        r1.setSeriesPositiveItemLabelPosition(0, 
                new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        BarRenderer r2 = new BarRenderer();
        r2.setSeriesPositiveItemLabelPosition(0, 
                new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        assertEquals(r1, r2);
        r2.setSeriesPositiveItemLabelPosition(1, 
                new ItemLabelPosition(ItemLabelAnchor.INSIDE1, TextAnchor.CENTER));
        assertNotEquals(r1, r2);
    }

    @Test
    public void testEquals_ObjectList4() {
        BarRenderer r1 = new BarRenderer();
        r1.setSeriesNegativeItemLabelPosition(0, 
                new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        BarRenderer r2 = new BarRenderer();
        r2.setSeriesNegativeItemLabelPosition(0, 
                new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        assertEquals(r1, r2);
        r2.setSeriesNegativeItemLabelPosition(1, 
                new ItemLabelPosition(ItemLabelAnchor.INSIDE1, TextAnchor.CENTER));
        assertNotEquals(r1, r2);
    }

    private static class TestRenderer extends XYLineAndShapeRenderer {
        @Override
        public void setTreatLegendShapeAsLine(boolean flag) {
            super.setTreatLegendShapeAsLine(flag);
        }
    }

    /**
     * Check that the treatLegendShapeAsLine flag is included in the equals()
     * comparison.
     */
    @Test
    public void testEquals2() {
        TestRenderer r1 = new TestRenderer();
        TestRenderer r2 = new TestRenderer();
        assertTrue(r1.equals(r2));
        r1.setTreatLegendShapeAsLine(true);
        assertFalse(r1.equals(r2));
        r2.setTreatLegendShapeAsLine(true);
        assertTrue(r1.equals(r2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        Rectangle2D shape = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        Rectangle2D baseShape = new Rectangle2D.Double(11.0, 12.0, 13.0, 14.0);
        r1.setDefaultShape(baseShape);
        r1.setDefaultLegendShape(new Rectangle(4, 3, 2, 1));
        r1.setDefaultLegendTextFont(new Font("Dialog", Font.PLAIN, 3));
        r1.setDefaultLegendTextPaint(new Color(1, 2, 3));
        r1.setSeriesItemLabelFont(0, new Font(Font.MONOSPACED, Font.BOLD, 13));
        r1.setLegendTextFont(0, new Font(Font.MONOSPACED, Font.BOLD, 14));
        r1.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.TOP_LEFT));
        r1.setSeriesNegativeItemLabelPosition(0, new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        
        LineAndShapeRenderer r2 = (LineAndShapeRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        r1.setSeriesVisible(0, Boolean.FALSE);
        assertFalse(r1.equals(r2));
        r2.setSeriesVisible(0, Boolean.FALSE);
        assertTrue(r1.equals(r2));

        r1.setSeriesVisibleInLegend(0, Boolean.FALSE);
        assertFalse(r1.equals(r2));
        r2.setSeriesVisibleInLegend(0, Boolean.FALSE);
        assertTrue(r1.equals(r2));

        r1.setSeriesPaint(0, Color.BLACK);
        assertFalse(r1.equals(r2));
        r2.setSeriesPaint(0, Color.BLACK);
        assertTrue(r1.equals(r2));

        r1.setSeriesFillPaint(0, Color.YELLOW);
        assertFalse(r1.equals(r2));
        r2.setSeriesFillPaint(0, Color.YELLOW);
        assertTrue(r1.equals(r2));

        r1.setSeriesOutlinePaint(0, Color.YELLOW);
        assertFalse(r1.equals(r2));
        r2.setSeriesOutlinePaint(0, Color.YELLOW);
        assertTrue(r1.equals(r2));

        r1.setSeriesStroke(0, new BasicStroke(2.2f));
        assertFalse(r1.equals(r2));
        r2.setSeriesStroke(0, new BasicStroke(2.2f));
        assertTrue(r1.equals(r2));

        r1.setSeriesOutlineStroke(0, new BasicStroke(2.2f));
        assertFalse(r1.equals(r2));
        r2.setSeriesOutlineStroke(0, new BasicStroke(2.2f));
        assertTrue(r1.equals(r2));

        baseShape.setRect(4.0, 3.0, 2.0, 1.0);
        assertFalse(r1.equals(r2));
        r2.setDefaultShape(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertTrue(r1.equals(r2));

        r1.setSeriesShape(0, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertFalse(r1.equals(r2));
        r2.setSeriesShape(0, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertTrue(r1.equals(r2));

        r1.setSeriesItemLabelsVisible(0, Boolean.TRUE);
        assertFalse(r1.equals(r2));
        r2.setSeriesItemLabelsVisible(0, Boolean.TRUE);
        assertTrue(r1.equals(r2));

        r1.setSeriesItemLabelPaint(0, Color.RED);
        assertFalse(r1.equals(r2));
        r2.setSeriesItemLabelPaint(0, Color.RED);
        assertTrue(r1.equals(r2));
        
        r1.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition());
        assertFalse(r1.equals(r2));
        r2.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition());
        assertTrue(r1.equals(r2));

        r1.setSeriesNegativeItemLabelPosition(0, new ItemLabelPosition());
        assertFalse(r1.equals(r2));
        r2.setSeriesNegativeItemLabelPosition(0, new ItemLabelPosition());
        assertTrue(r1.equals(r2));

        r1.setSeriesCreateEntities(0, Boolean.FALSE);
        assertFalse(r1.equals(r2));
        r2.setSeriesCreateEntities(0, Boolean.FALSE);
        assertTrue(r1.equals(r2));

        r1.setLegendShape(0, new Rectangle(9, 7, 3, 4));
        assertFalse(r1.equals(r2));
        r2.setLegendShape(0, new Rectangle(9, 7, 3, 4));
        assertTrue(r1.equals(r2));

        r1.setDefaultLegendShape(new Rectangle(3, 4, 1, 5));
        assertFalse(r1.equals(r2));
        r2.setDefaultLegendShape(new Rectangle(3, 4, 1, 5));
        assertTrue(r1.equals(r2));

        r1.setLegendTextFont(1, new Font("Dialog", Font.PLAIN, 33));
        assertFalse(r1.equals(r2));
        r2.setLegendTextFont(1, new Font("Dialog", Font.PLAIN, 33));
        assertTrue(r1.equals(r2));

        r1.setDefaultLegendTextFont(new Font("Dialog", Font.PLAIN, 11));
        assertFalse(r1.equals(r2));
        r2.setDefaultLegendTextFont(new Font("Dialog", Font.PLAIN, 11));
        assertTrue(r1.equals(r2));

        r1.setLegendTextPaint(3, Color.RED);
        assertFalse(r1.equals(r2));
        r2.setLegendTextPaint(3, Color.RED);
        assertTrue(r1.equals(r2));

        r1.setDefaultLegendTextPaint(Color.GREEN);
        assertFalse(r1.equals(r2));
        r2.setDefaultLegendTextPaint(Color.GREEN);
        assertTrue(r1.equals(r2));
    }

    /**
     * A utility class for listening to changes to a renderer.
     */
    static class MyRendererChangeListener implements RendererChangeListener {

        /** The last event received. */
        public RendererChangeEvent lastEvent;

        /**
         * Creates a new instance.
         */
        public MyRendererChangeListener() {
            this.lastEvent = null;
        }
        @Override
        public void rendererChanged(RendererChangeEvent event) {
            this.lastEvent = event;
        }
    }

    /**
     * A check for cloning.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        r1.setDefaultPaint(Color.BLUE);
        r1.setDefaultLegendTextPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        LineAndShapeRenderer r2 = (LineAndShapeRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        MyRendererChangeListener listener = new MyRendererChangeListener();
        r2.addChangeListener(listener);
        r2.setDefaultPaint(Color.RED);
        assertTrue(listener.lastEvent.getRenderer() == r2);
        assertFalse(r1.hasListener(listener));
    }

    /**
     * Tests each setter method to ensure that it sends an event notification.
     */
    @Test
    public void testEventNotification() {

        RendererChangeDetector detector = new RendererChangeDetector();
        BarRenderer r1 = new BarRenderer();  // have to use a subclass of
                                             // AbstractRenderer
        r1.addChangeListener(detector);

        // PAINT
        detector.setNotified(false);
        r1.setSeriesPaint(0, Color.RED);
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultPaint(Color.RED);
        assertTrue(detector.getNotified());

        // OUTLINE PAINT
        detector.setNotified(false);
        r1.setSeriesOutlinePaint(0, Color.RED);
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultOutlinePaint(Color.RED);
        assertTrue(detector.getNotified());

        // STROKE
        detector.setNotified(false);
        r1.setSeriesStroke(0, new BasicStroke(1.0f));
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultStroke(new BasicStroke(1.0f));
        assertTrue(detector.getNotified());

        // OUTLINE STROKE
        detector.setNotified(false);
        r1.setSeriesOutlineStroke(0, new BasicStroke(1.0f));
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultOutlineStroke(new BasicStroke(1.0f));
        assertTrue(detector.getNotified());

        // SHAPE
        detector.setNotified(false);
        r1.setSeriesShape(0, new Rectangle2D.Float());
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultShape(new Rectangle2D.Float());
        assertTrue(detector.getNotified());

        // ITEM_LABELS_VISIBLE
        detector.setNotified(false);
        r1.setSeriesItemLabelsVisible(0, Boolean.TRUE);
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultItemLabelsVisible(Boolean.TRUE);
        assertTrue(detector.getNotified());

        // ITEM_LABEL_FONT
        detector.setNotified(false);
        r1.setSeriesItemLabelFont(0, new Font("Serif", Font.PLAIN, 12));
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultItemLabelFont(new Font("Serif", Font.PLAIN, 12));
        assertTrue(detector.getNotified());

        // ITEM_LABEL_PAINT
        detector.setNotified(false);
        r1.setSeriesItemLabelPaint(0, Color.BLUE);
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultItemLabelPaint(Color.BLUE);
        assertTrue(detector.getNotified());

        // POSITIVE ITEM LABEL POSITION
        detector.setNotified(false);
        r1.setSeriesPositiveItemLabelPosition(0, new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        assertTrue(detector.getNotified());

        // NEGATIVE ITEM LABEL ANCHOR
        detector.setNotified(false);
        r1.setSeriesNegativeItemLabelPosition(0, new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        assertTrue(detector.getNotified());

        detector.setNotified(false);
        r1.setDefaultNegativeItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER));
        assertTrue(detector.getNotified());

    }

    /**
     * Serialize an instance, restore it, and check for equality.  In addition,
     * test for a bug that was reported where the listener list is 'null' after
     * deserialization.
     */
    @Test
    public void testSerialization() {
        BarRenderer r1 = new BarRenderer();
        r1.setDefaultLegendTextFont(new Font("Dialog", Font.PLAIN, 4));
        r1.setDefaultLegendTextPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.GREEN));
        r1.setDefaultLegendShape(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        BarRenderer r2 = (BarRenderer) TestUtils.serialised(r1);
        assertEquals(r1, r2);
        try {
            r2.notifyListeners(new RendererChangeEvent(r2));
        }
        catch (NullPointerException e) {
            fail("No exception should be thrown.");  // failed
        }
    }

    /**
     * Some checks for the autoPopulate flag default values.
     */
    @Test
    public void testAutoPopulateFlagDefaults() {
        BarRenderer r = new BarRenderer();
        assertEquals(true, r.getAutoPopulateSeriesPaint());
        assertEquals(false, r.getAutoPopulateSeriesFillPaint());
        assertEquals(false, r.getAutoPopulateSeriesOutlinePaint());
        assertEquals(true, r.getAutoPopulateSeriesStroke());
        assertEquals(false, r.getAutoPopulateSeriesOutlineStroke());
        assertEquals(true, r.getAutoPopulateSeriesShape());
    }

    /**
     * Test some default getters with their default initialized values.
     */
    @Test
    public void testDefaultGetters() {
        BarRenderer r = new BarRenderer();

        assertEquals(2.0, r.getItemLabelAnchorOffset(), 0.0001);
        assertTrue(r.getDefaultCreateEntities());
        assertEquals(3, r.getDefaultEntityRadius());
        assertEquals(new Rectangle2D.Double(-4, -4, 8, 8), r.getDefaultLegendShape());
        assertNull(r.getDefaultLegendTextFont());
        assertNull(r.getDefaultLegendTextPaint());
    }

    /**
     * Some checks for the paint lookup mechanism.
     */
    @Test
    public void testPaintLookup() {
        BarRenderer r = new BarRenderer();
        r.clearSeriesPaints(true);
        assertEquals(Color.BLUE, r.getDefaultPaint());

        // first check that autoPopulate==false works as expected
        r.setAutoPopulateSeriesPaint(false);
        assertEquals(Color.BLUE, r.getItemPaint(0, 0));
        assertEquals(Color.BLUE, r.lookupSeriesPaint(0));
        assertNull(r.getSeriesPaint(0));

        // now check autoPopulate==true
        r.setAutoPopulateSeriesPaint(true);
        /*CategoryPlot plot =*/ new CategoryPlot(null, new CategoryAxis(
                "Category"), new NumberAxis("Value"), r);
        assertEquals(DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE[0],
                r.lookupSeriesPaint(0));
        assertEquals(DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE[0],
                r.getItemPaint(0, 0));
        assertNotNull(r.getSeriesPaint(0));
    }

    /**
     * Some checks for the fill paint lookup mechanism.
     */
    @Test
    public void testFillPaintLookup() {
        BarRenderer r = new BarRenderer();
        assertEquals(Color.WHITE, r.getDefaultFillPaint());

        // first check that autoPopulate==false works as expected
        r.setAutoPopulateSeriesFillPaint(false);
        assertEquals(Color.WHITE, r.lookupSeriesFillPaint(0));
        assertEquals(Color.WHITE, r.getItemFillPaint(0, 0));
        assertNull(r.getSeriesFillPaint(0));

        // now check autoPopulate==true
        r.setAutoPopulateSeriesFillPaint(true);
        /*CategoryPlot plot =*/ new CategoryPlot(null, new CategoryAxis(
                "Category"), new NumberAxis("Value"), r);
        assertEquals(DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE[0],
                r.lookupSeriesFillPaint(0));
        assertEquals(DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE[0],
                r.getItemFillPaint(0, 0));
        assertNotNull(r.getSeriesFillPaint(0));
    }

    /**
     * Some checks for the outline paint lookup mechanism.
     */
    @Test
    public void testOutlinePaintLookup() {
        BarRenderer r = new BarRenderer();
        assertEquals(Color.GRAY, r.getDefaultOutlinePaint());

        // first check that autoPopulate==false works as expected
        r.setAutoPopulateSeriesOutlinePaint(false);
        assertEquals(Color.GRAY, r.lookupSeriesOutlinePaint(0));
        assertEquals(Color.GRAY, r.getItemOutlinePaint(0, 0));
        assertNull(r.getSeriesOutlinePaint(0));

        // now check autoPopulate==true
        r.setAutoPopulateSeriesOutlinePaint(true);
        /*CategoryPlot plot =*/ new CategoryPlot(null, new CategoryAxis(
                "Category"), new NumberAxis("Value"), r);
        assertEquals(DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE[0],
                r.lookupSeriesOutlinePaint(0));
        assertEquals(DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE[0],
                r.getItemOutlinePaint(0, 0));
        assertNotNull(r.getSeriesOutlinePaint(0));
    }

    /**
     * Test setting and getting the shape for a series.
     */
    @Test
    public void testSeriesShape() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();

        assertNull(r.getSeriesShape(0));

        for (int i = 0; i < 10; i++) {
            Rectangle2D shape = new Rectangle2D.Double(i, i, i, i);
            r.setSeriesShape(i, shape);
            assertEquals(shape, r.getSeriesShape(i));
        }

        // Make sure setting series shape overwrites existing shapes in the series
        r.setSeriesItemShape(10, 0, new Ellipse2D.Double(1, 1, 1 ,1));
        r.setSeriesItemShape(10, 1, new Rectangle2D.Double(1, 1, 1, 1));
        r.setSeriesItemShape(10, 2, new RoundRectangle2D.Double(1, 1, 1, 1, 1, 1));

        Ellipse2D ellipse = new Ellipse2D.Double(10, 10, 10 ,10);
        r.setSeriesShape(10, ellipse);
        for (int i = 0; i < 3; i++) {
            assertEquals(ellipse, r.getSeriesItemShape(10, i));
        }
    }

    /**
     * Test setting an item in a series.
     */
    @Test
    public void testSeriesItemShape() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();

        assertNull(r.getSeriesItemShape(0, 0));

        for (int series = 0; series < 10; series++) {
            for (int item = 0; item < 10; item++) {
                Rectangle2D shape = new Rectangle2D.Double(series, series, item, item);
                r.setSeriesItemShape(series, item, shape);
                assertEquals(shape, r.getSeriesItemShape(series, item));
            }
        }
    }

    /**
     * Test getItemShape (and lookupSeriesShape).
     */
    @Test
    public void testGetItemShape() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();

        r.setAutoPopulateSeriesShape(false);
        assertEquals(r.getDefaultShape(), r.getItemShape(0, 0));
        assertEquals(r.getItemShape(0, 0), r.lookupSeriesShape(0));

        r.setAutoPopulateSeriesShape(true);
        assertNull(r.getDrawingSupplier());
        assertEquals(r.getDefaultShape(), r.getItemShape(0, 0));

        CategoryPlot plot = new CategoryPlot();
        r.setPlot(plot);
        assertNotNull(r.getDrawingSupplier());
        DrawingSupplier supplier = new DefaultDrawingSupplier();
        assertEquals(supplier.getNextShape(), r.getItemShape(0, 0));
    }

    /**
     * Test series visibility.
     */
    @Test
    public void testSeriesVisibility() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        assertTrue(r.getDefaultSeriesVisible());

        r.setDefaultSeriesVisible(true);
        assertTrue(r.getItemVisible(0, 0));
        assertTrue(r.isSeriesVisible(0));

        r.setDefaultSeriesVisible(false);
        assertFalse(r.getItemVisible(0, 0));
        assertFalse(r.isSeriesVisible(0));

        r.setSeriesVisible(0, true);
        assertTrue(r.getSeriesVisible(0));
        assertTrue(r.isSeriesVisible(0));
        r.setSeriesVisible(1, false);
        assertFalse(r.getSeriesVisible(1));
        assertFalse(r.isSeriesVisible(1));

    }

    /**
     * Test series visibility in legend.
     */
    @Test
    public void testSeriesVisibilityInLegend() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        assertTrue(r.getDefaultSeriesVisibleInLegend());

        r.setDefaultSeriesVisibleInLegend(true);
        assertTrue(r.isSeriesVisibleInLegend(0));

        r.setDefaultSeriesVisibleInLegend(false);
        assertFalse(r.isSeriesVisibleInLegend(0));

        r.setSeriesVisibleInLegend(0, true);
        assertTrue(r.getSeriesVisibleInLegend(0));
        assertTrue(r.isSeriesVisibleInLegend(0));
        r.setSeriesVisibleInLegend(1, false);
        assertFalse(r.getSeriesVisibleInLegend(1));
        assertFalse(r.isSeriesVisibleInLegend(1));

    }

    /**
     * Test series strokes and outline strokes.
     */
    @Test
    public void testSeriesStroke() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        r.clearSeriesStrokes(true);
        assertEquals(new BasicStroke(1.0f), r.getDefaultStroke());
        assertEquals(new BasicStroke(1.0f), r.getDefaultOutlineStroke());

        r.setAutoPopulateSeriesStroke(false);
        r.setAutoPopulateSeriesOutlineStroke(false);
        assertNull(r.getSeriesStroke(0));
        assertNull(r.getSeriesOutlineStroke(0));
        assertEquals(r.getDefaultStroke(), r.getItemOutlineStroke(0, 0));
        assertEquals(r.getDefaultStroke(), r.lookupSeriesOutlineStroke(0));

        r.setAutoPopulateSeriesStroke(true);
        r.setAutoPopulateSeriesOutlineStroke(true);
        assertNull(r.getSeriesStroke(1));
        assertNull(r.getSeriesOutlineStroke(1));
        assertEquals(r.getDefaultStroke(), r.lookupSeriesStroke(1));
        assertEquals(r.getDefaultStroke(), r.lookupSeriesOutlineStroke(1));

        CategoryPlot plot = new CategoryPlot();
        r.setPlot(plot);
        assertNotNull(r.getDrawingSupplier());
        DrawingSupplier supplier = new DefaultDrawingSupplier();
        assertEquals(supplier.getNextStroke(), r.lookupSeriesStroke(2));
        assertEquals(supplier.getNextOutlineStroke(), r.lookupSeriesOutlineStroke(2));

    }

    /**
     * Test item label visibility.
     */
    @Test
    public void testItemLabelVisibility() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        assertFalse(r.getDefaultItemLabelsVisible());

        assertFalse(r.isSeriesItemLabelsVisible(0));
        assertFalse(r.isItemLabelVisible(0, 0));

        r.setDefaultItemLabelsVisible(true);
        assertTrue(r.isSeriesItemLabelsVisible(0));
        assertTrue(r.isItemLabelVisible(0, 0));

        r.setSeriesItemLabelsVisible(1, true);
        assertTrue(r.isSeriesItemLabelsVisible(1));
        assertTrue(r.isItemLabelVisible(1, 0));
        r.setSeriesItemLabelsVisible(2, false);
        assertFalse(r.isSeriesItemLabelsVisible(2));
        assertFalse(r.isItemLabelVisible(2, 0));
    }

    /**
     * Test item label font.
     */
    @Test
    public void testItemLabelFont() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        assertEquals(new Font("SansSerif", Font.PLAIN, 10), r.getDefaultItemLabelFont());

        assertEquals(r.getDefaultItemLabelFont(), r.getItemLabelFont(0, 0));
        assertNull(r.getSeriesItemLabelFont(0));

        Font comicSans = new Font("ComicSans", Font.PLAIN, 12);
        r.setSeriesItemLabelFont(1 , comicSans);
        assertEquals(comicSans, r.getSeriesItemLabelFont(1));
    }

    /**
     * Test item label paint.
     */
    @Test
    public void testItemLabelPaint() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        assertEquals(Color.BLACK, r.getDefaultItemLabelPaint());

        assertEquals(r.getDefaultItemLabelPaint(), r.getItemLabelPaint(0, 0));
        assertNull(r.getSeriesItemLabelPaint(0));

        Color red = Color.RED;
        r.setSeriesItemLabelPaint(1 , red);
        assertEquals(red, r.getSeriesItemLabelPaint(1));
    }

    /**
     * Test (positive and negative) item label position.
     */
    @Test
    public void testItemLabelPosition() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();

        assertEquals(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER),
                r.getDefaultPositiveItemLabelPosition());
        assertEquals(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER),
                r.getDefaultNegativeItemLabelPosition());

        assertEquals(r.getDefaultPositiveItemLabelPosition(), r.getPositiveItemLabelPosition(0, 0));
        assertEquals(r.getDefaultPositiveItemLabelPosition(), r.getSeriesPositiveItemLabelPosition(0));
        assertEquals(r.getDefaultNegativeItemLabelPosition(), r.getNegativeItemLabelPosition(0, 0));
        assertEquals(r.getDefaultNegativeItemLabelPosition(), r.getSeriesNegativeItemLabelPosition(0));

        ItemLabelPosition pos1 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.TOP_RIGHT);
        ItemLabelPosition pos2 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE2, TextAnchor.BOTTOM_LEFT);
        r.setSeriesPositiveItemLabelPosition(1, pos1);
        r.setSeriesNegativeItemLabelPosition(2, pos2);
        assertEquals(pos1, r.getSeriesPositiveItemLabelPosition(1));
        assertEquals(pos2, r.getSeriesNegativeItemLabelPosition(2));
    }

    /**
     * Test setting and getting dataBoundsIncludesVisibleSeriesOnly.
     */
    @Test
    public void testDataBoundsIncludesVisibleSeriesOnly() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        assertTrue(r.getDataBoundsIncludesVisibleSeriesOnly());
        r.setDataBoundsIncludesVisibleSeriesOnly(false);
        assertFalse(r.getDataBoundsIncludesVisibleSeriesOnly());
    }

    /**
     * Test calculateLabelAnchorPoint.
     */
    @Test
    public void testCalculateLabelAnchorPoint() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();

        double ADJ = Math.cos(Math.PI / 6.0);
        double OPP = Math.sin(Math.PI / 6.0);
        double itemLabelAnchorOffset = r.getItemLabelAnchorOffset();

        assertEquals(new Point2D.Double(7, 7),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.CENTER, 7, 7, null));
        assertEquals(new Point2D.Double(7 + OPP * itemLabelAnchorOffset, 7 - ADJ * itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE1, 7, 7, null));
        assertEquals(new Point2D.Double(7 + ADJ * itemLabelAnchorOffset, 7 - OPP * itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE2, 7, 7, null));
        assertEquals(new Point2D.Double(7 + itemLabelAnchorOffset, 7),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE3, 7, 7, null));
        assertEquals(new Point2D.Double(7 + ADJ * itemLabelAnchorOffset, 7 + OPP * itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE4, 7, 7, null));
        assertEquals(new Point2D.Double(7 + OPP * itemLabelAnchorOffset, 7 + ADJ * itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE5, 7, 7, null));
        assertEquals(new Point2D.Double(7, 7 + itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE6, 7, 7, null));
        assertEquals(new Point2D.Double(7 - OPP * itemLabelAnchorOffset, 7 + ADJ * itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE7, 7, 7, null));
        assertEquals(new Point2D.Double(7 - ADJ * itemLabelAnchorOffset, 7 + OPP * itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE8, 7, 7, null));
        assertEquals(new Point2D.Double(7 - itemLabelAnchorOffset, 7),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE9, 7, 7, null));
        assertEquals(new Point2D.Double(7 - ADJ * itemLabelAnchorOffset, 7 - OPP * itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE10, 7, 7, null));
        assertEquals(new Point2D.Double(7 - OPP * itemLabelAnchorOffset, 7 - ADJ * itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE11, 7, 7, null));
        assertEquals(new Point2D.Double(7, 7 - itemLabelAnchorOffset),
                r.calculateLabelAnchorPoint(ItemLabelAnchor.INSIDE12, 7, 7, null));

        assertEquals(new Point2D.Double(
                7 + 2.0 * OPP * itemLabelAnchorOffset,
                7 - 2.0 * ADJ * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE1, 7, 7, null));
        assertEquals(new Point2D.Double(
                7 + 2.0 * ADJ * itemLabelAnchorOffset,
                7 - 2.0 * OPP * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE2, 7, 7, null));
        assertEquals(new Point2D.Double(7 + 2.0 * itemLabelAnchorOffset,
                7), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE3, 7, 7, null));
        assertEquals(new Point2D.Double(
                7 + 2.0 * ADJ * itemLabelAnchorOffset,
                7 + 2.0 * OPP * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE4, 7, 7, null));
        assertEquals(new Point2D.Double(
                7 + 2.0 * OPP * itemLabelAnchorOffset,
                7 + 2.0 * ADJ * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE5, 7, 7, null));
        assertEquals(new Point2D.Double(7,
                7 + 2.0 * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE6, 7, 7, null));
        assertEquals(new Point2D.Double(
                7 - 2.0 * OPP * itemLabelAnchorOffset,
                7 + 2.0 * ADJ * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE7, 7, 7, null));
        assertEquals(new Point2D.Double(
                7 - 2.0 * ADJ * itemLabelAnchorOffset,
                7 + 2.0 * OPP * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE8, 7, 7, null));
        assertEquals(new Point2D.Double(7 - 2.0 * itemLabelAnchorOffset,
                7), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE9, 7, 7, null));
        assertEquals(new Point2D.Double(
                7 - 2.0 * ADJ * itemLabelAnchorOffset,
                7 - 2.0 * OPP * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE10, 7, 7, null));
        assertEquals(new Point2D.Double(
                7 - 2.0 * OPP * itemLabelAnchorOffset,
                7 - 2.0 * ADJ * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE11, 7, 7, null));
        assertEquals(new Point2D.Double(7,
                7 - 2.0 * itemLabelAnchorOffset), r.calculateLabelAnchorPoint(ItemLabelAnchor.OUTSIDE12, 7, 7, null));
    }
}
