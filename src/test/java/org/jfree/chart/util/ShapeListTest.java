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
 * ------------------
 * ShapeListTest.java
 * ------------------
 *
 */

package org.jfree.chart.util;

import org.jfree.chart.TestUtils;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.junit.Assert.*;

/**
 * Tests for the {@link ShapeList} class.
 */
public class ShapeListTest {

    /**
     * Check the simple getter and setter methods.
     */
    @Test
    public void testSimpleGetterAndSetter() {
        ShapeList shapeList = new ShapeList();

        assertNull(shapeList.getShape(100));

        for (int i = 0; i < 10; i++) {
            Shape shape = new Rectangle2D.Double(i, -i, i, i);
            shapeList.setShape(i, shape);
            assertEquals(shape, shapeList.getShape(i));
        }

        Shape shape = new Rectangle2D.Double(-3.0, -3.0, 6.0, 6.0);
        shapeList.setShape(1000, shape);
        assertEquals(shape, shapeList.getShape(1000));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() {
        ShapeList s1 = new ShapeList();
        try {
            ShapeList s2 = (ShapeList) s1.clone();
            assertTrue(s1 != s2);
            assertTrue(s1.getClass() == s2.getClass());
            assertTrue(s1.equals(s2));
        } catch (CloneNotSupportedException e) {
            // should not happen
            assertTrue(false);
        }
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        ShapeList s1 = new ShapeList();
        ShapeList s2 = (ShapeList) TestUtils.serialised(s1);
        assertEquals(s1, s2);
    }

}
