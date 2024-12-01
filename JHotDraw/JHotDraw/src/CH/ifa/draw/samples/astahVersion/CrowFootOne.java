/*
 * @(#)CrowFootOne.java
 *
 * Project: JHotdraw - a GUI framework for technical drawings
 *          http://www.jhotdraw.org
 *          http://jhotdraw.sourceforge.net
 * Copyright: by the original author(s) and all contributors
 * License: Lesser GNU Public License (LGPL)
 *          http://www.opensource.org/licenses/lgpl-license.html
 */

package CH.ifa.draw.samples.astahVersion;

import java.io.*;
import java.awt.*;

import CH.ifa.draw.figures.AbstractLineDecoration;
import CH.ifa.draw.figures.PolyLineFigure;
import CH.ifa.draw.util.*;

/**
 * A line decoration that resembles a "Crow Foot One" shape, commonly used in ER
 * diagrams.
 *
 * @see PolyLineFigure
 *
 * @version <$CURRENT_VERSION$>
 */
public class CrowFootOne extends AbstractLineDecoration {

    private int lineLength; // Length of the perpendicular lines
    private int lineOffset; // Offset distance from the start of the base line

    /*
     * Serialization support.
     */
    private static final long serialVersionUID = -3459171428373823638L;
    private int crowFootOneSerializedDataVersion = 1;

    /**
     * Default constructor with predefined length and offset.
     */
    public CrowFootOne() {
        this(10, 15); // Default length of 10 pixels and offset of 15 pixels
    }

    /**
     * Constructs a "Crow Foot One" decoration with the given line length and
     * offset.
     */
    public CrowFootOne(int lineLength, int lineOffset) {
        this.lineLength = lineLength;
        this.lineOffset = lineOffset;
    }

    /**
     * Calculates the outline of the "Crow Foot One" shape.
     */
    @Override
    public Polygon outline(int x1, int y1, int x2, int y2) {
        // The outline method is retained for compatibility but will not be used for
        // drawing
        return new Polygon(); // Empty implementation for now
    }

    /**
     * Draws the "Crow Foot One" shape as a decoration on the line.
     */
    @Override
    public void draw(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) g;

        // Calculate the direction of the base line
        double direction = Math.atan2(y2 - y1, x2 - x1);

        // Reduce the distance between the two lines by using a factor
        double scaleFactor = 0.8; // Factor to bring the lines closer together

        // Draw the first perpendicular line
        drawPerpendicularLine(g2, x1, y1, direction, (int) (lineOffset * scaleFactor));

        // Draw the second perpendicular line closer to the first
        drawPerpendicularLine(g2, x1, y1, direction, (int) (lineOffset * scaleFactor * 2));
    }

    /**
     * Draws a perpendicular line to the base line at the specified offset.
     */
    private void drawPerpendicularLine(Graphics2D g2, int x1, int y1, double direction, int offset) {
        // Calculate the base point for this perpendicular line
        int baseX = x1 + (int) (offset * Math.cos(direction));
        int baseY = y1 + (int) (offset * Math.sin(direction));

        // Calculate the end points of the perpendicular line
        int perpX1 = baseX + (int) (lineLength / 2.0 * Math.cos(direction + Math.PI / 2));
        int perpY1 = baseY + (int) (lineLength / 2.0 * Math.sin(direction + Math.PI / 2));
        int perpX2 = baseX - (int) (lineLength / 2.0 * Math.cos(direction + Math.PI / 2));
        int perpY2 = baseY - (int) (lineLength / 2.0 * Math.sin(direction + Math.PI / 2));

        // Draw the perpendicular line
        g2.drawLine(perpX1, perpY1, perpX2, perpY2);
    }

    /**
     * Adds a point to the polygon relative to the given radius and angle.
     * This method is retained for compatibility with the original structure.
     */
    private void addPointRelative(Polygon shape, int x, int y, double radius, double angle) {
        shape.addPoint(
                x + (int) (radius * Math.cos(angle)),
                y + (int) (radius * Math.sin(angle)));
    }

    /**
     * Stores the "Crow Foot One" decoration to a StorableOutput.
     */
    public void write(StorableOutput dw) {
        dw.writeInt(lineLength);
        dw.writeInt(lineOffset);
        super.write(dw);
    }

    /**
     * Reads the "Crow Foot One" decoration from a StorableInput.
     */
    public void read(StorableInput dr) throws IOException {
        lineLength = dr.readInt();
        lineOffset = dr.readInt();
        super.read(dr);
    }

    /**
     * Sets the length of the perpendicular lines.
     */
    protected void setLineLength(int newLength) {
        lineLength = newLength;
    }

    /**
     * Returns the length of the perpendicular lines.
     */
    protected int getLineLength() {
        return lineLength;
    }

    /**
     * Sets the offset distance for the perpendicular lines.
     */
    protected void setLineOffset(int newOffset) {
        lineOffset = newOffset;
    }

    /**
     * Returns the offset distance for the perpendicular lines.
     */
    protected int getLineOffset() {
        return lineOffset;
    }
}
