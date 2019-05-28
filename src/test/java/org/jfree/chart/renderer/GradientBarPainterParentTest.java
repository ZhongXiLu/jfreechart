package org.jfree.chart.renderer;


import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.chart.ui.RectangleEdge;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link GradientBarPainterParent} class.
 */
public class GradientBarPainterParentTest {
    @Test
    public void testCreateShadowNotNull() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D result = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,5,RectangleEdge.RIGHT,true);
        assertNotNull(result);
    }

    @Test
    public void testCreateShadowRight() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D result = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,10,RectangleEdge.RIGHT,false);
        assertTrue(result.getX()==25);
        assertTrue(result.getY()==30);
        assertTrue(result.getWidth()==50);
        assertTrue(result.getHeight()==20);

        Rectangle2D result2 = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,10,RectangleEdge.RIGHT,true);
        assertTrue(result2.getX()==25);
        assertTrue(result2.getY()==30);
        assertTrue(result2.getWidth()==45);
        assertTrue(result2.getHeight()==20);
    }

    @Test
    public void testCreateShadowBottom() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D result = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,10,RectangleEdge.BOTTOM,false);
        assertTrue(result.getX()==25);
        assertTrue(result.getY()==30);
        assertTrue(result.getWidth()==50);
        assertTrue(result.getHeight()==20);

        Rectangle2D result2 = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,10,RectangleEdge.BOTTOM,true);
        assertTrue(result2.getX()==25);
        assertTrue(result2.getY()==30);
        assertTrue(result2.getWidth()==50);
        assertTrue(result2.getHeight()==10);
    }

    @Test
    public void testCreateShadowLeft() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D result = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,10,RectangleEdge.LEFT,false);
        assertTrue(result.getX()==25);
        assertTrue(result.getY()==30);
        assertTrue(result.getWidth()==50);
        assertTrue(result.getHeight()==20);

        Rectangle2D result2 = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,10,RectangleEdge.LEFT,true);
        assertTrue(result2.getX()==20);
        assertTrue(result2.getY()==30);
        assertTrue(result2.getWidth()==55);
        assertTrue(result2.getHeight()==20);
    }

    @Test
    public void testCreateShadowTop() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D result = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,10,RectangleEdge.TOP,false);
        assertTrue(result.getX()==25);
        assertTrue(result.getY()==30);
        assertTrue(result.getWidth()==50);
        assertTrue(result.getHeight()==20);

        Rectangle2D result2 = gbp.createShadow(new Rectangle2D.Double(20,20,50,20),5,10,RectangleEdge.TOP,true);
        assertTrue(result2.getX()==25);
        assertTrue(result2.getY()==20);
        assertTrue(result2.getWidth()==50);
        assertTrue(result2.getHeight()==30);
    }

    @Test
    public void testSplitVerticalBarNotNull() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D[] result = gbp.splitVerticalBar(new Rectangle2D.Double(20,20,50,20),0.10, 0.20, 0.80);
        assertNotNull(result);
        assertTrue(result.length==4);
    }

    @Test
    public void testSplitVerticalBarValues() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D[] result = gbp.splitVerticalBar(new Rectangle2D.Double(20,20,50,20),0.10, 0.20, 0.80);
        assertTrue(result[0].getX()<result[1].getX());
        assertTrue(result[1].getX()<result[2].getX());
        assertTrue(result[2].getX()<result[3].getX());
        assertTrue(result[0].getY()==result[1].getY());
        assertTrue(result[1].getY()==result[2].getY());
        assertTrue(result[2].getY()==result[3].getY());
    }

    @Test
    public void testSplitHorizontalBarNotNull() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D[] result = gbp.splitHorizontalBar(new Rectangle2D.Double(20,20,50,20),0.10, 0.20, 0.80);
        assertNotNull(result);
        assertTrue(result.length==4);
    }

    @Test
    public void testSplitHorizontalBarValues() {
        GradientBarPainter gbp = new GradientBarPainter();
        Rectangle2D[] result = gbp.splitHorizontalBar(new Rectangle2D.Double(20,20,20,50),0.10, 0.20, 0.80);
        assertTrue(result[0].getY()<result[1].getY());
        assertTrue(result[1].getY()<result[2].getY());
        assertTrue(result[2].getY()<result[3].getY());
        assertTrue(result[0].getX()==result[1].getX());
        assertTrue(result[1].getX()==result[2].getX());
        assertTrue(result[2].getX()==result[3].getX());
    }
}
