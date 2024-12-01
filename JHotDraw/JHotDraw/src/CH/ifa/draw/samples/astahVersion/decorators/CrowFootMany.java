/*
 * @(#)CrowFootMany.java
 *
 * Project: JHotdraw - a GUI framework for technical drawings
 *          http://www.jhotdraw.org
 *          http://jhotdraw.sourceforge.net
 * Copyright: by the original author(s) and all contributors
 * License: Lesser GNU Public License (LGPL)
 *          http://www.opensource.org/licenses/lgpl-license.html
 */

package CH.ifa.draw.samples.astahVersion.decorators;

import java.io.*;
import java.awt.*;

import CH.ifa.draw.figures.AbstractLineDecoration;
import CH.ifa.draw.figures.PolyLineFigure;
import CH.ifa.draw.util.*;

/**
 * A line decoration that resembles a "Crow Foot" shape, commonly used in ER
 * diagrams.
 *
 * @see PolyLineFigure
 *
 * @version <$CURRENT_VERSION$>
 */
public class CrowFootMany extends AbstractLineDecoration {

    private double fAngle; // Divergence angle of the lines
    private double fOuterRadius; // Length of the diverging lines

    /*
     * Serialization support.
     */
    private static final long serialVersionUID = -3459171428373823638L;
    private int crowFootManySerializedDataVersion = 1;

    /**
     * Default constructor with predefined angle and radius.
     */
    public CrowFootMany() {
        this(Math.toRadians(45 * 3.2), 13.5); // Default angle (radians) and length (pixels)
    }

    /**
     * Constructs a "Crow Foot" decoration with the given angle and radius.
     */
    public CrowFootMany(double angle, double outerRadius) {
        setAngle(angle);
        setOuterRadius(outerRadius);
    }

    /**
     * Calculates the outline of the "Crow Foot" shape.
     */
    @Override
    public Polygon outline(int x1, int y1, int x2, int y2) {
        // The outline method is retained for compatibility but will not be used for
        // drawing
        return new Polygon(); // Empty implementation for now
    }

    /**
     * Draws the "Crow Foot" shape as a decoration on the line.
     */
    @Override
    public void draw(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) g;

        // Calcular la dirección de la línea base
        double direction = Math.atan2(y2 - y1, x2 - x1);

        // Calcular el punto ajustado para la flecha
        int offset = -10; // Distancia antes de la flecha
        int adjustedX = x1 - (int) (offset * Math.cos(direction));
        int adjustedY = y1 - (int) (offset * Math.sin(direction));

        // Calcular el punto para la línea perpendicular antes de la flecha
        int perpendicularOffset = -20; // Distancia antes de la flecha donde estará la línea perpendicular
        int perpendicularX = x1 - (int) (perpendicularOffset * Math.cos(direction));
        int perpendicularY = y1 - (int) (perpendicularOffset * Math.sin(direction));

        // Dibujar la línea perpendicular
        drawPerpendicularLine(g2, perpendicularX, perpendicularY, direction);

        // Dibujar la flecha espejeada
        Polygon leftLine = createDivergingLine(adjustedX, adjustedY, direction + fAngle); // Espejado: +fAngle
        g2.drawPolygon(leftLine);

        Polygon rightLine = createDivergingLine(adjustedX, adjustedY, direction - fAngle); // Espejado: -fAngle
        g2.drawPolygon(rightLine);
    }

    /**
     * Dibuja una línea perpendicular a la línea base en la posición especificada.
     */
    private void drawPerpendicularLine(Graphics2D g2, int x, int y, double direction) {
        // Longitud de la línea perpendicular
        int lineLength = 10;

        // Calcular los extremos de la línea perpendicular
        int perpX1 = x + (int) (lineLength / 2.0 * Math.cos(direction + Math.PI / 2));
        int perpY1 = y + (int) (lineLength / 2.0 * Math.sin(direction + Math.PI / 2));
        int perpX2 = x - (int) (lineLength / 2.0 * Math.cos(direction + Math.PI / 2));
        int perpY2 = y - (int) (lineLength / 2.0 * Math.sin(direction + Math.PI / 2));

        // Dibujar la línea
        g2.drawLine(perpX1, perpY1, perpX2, perpY2);
    }

    /**
     * Creates a single diverging line for the "Crow Foot".
     */
    private Polygon createDivergingLine(int x, int y, double angle) {
        Polygon shape = new Polygon();

        // Start at the base point
        shape.addPoint(x, y);

        // Calculate the end point of the line
        int endX = x + (int) (fOuterRadius * Math.cos(angle));
        int endY = y + (int) (fOuterRadius * Math.sin(angle));

        // Add the end point
        shape.addPoint(endX, endY);

        return shape;
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
     * Stores the "Crow Foot" decoration to a StorableOutput.
     */
    public void write(StorableOutput dw) {
        dw.writeDouble(getAngle());
        dw.writeDouble(getOuterRadius());
        super.write(dw);
    }

    /**
     * Reads the "Crow Foot" decoration from a StorableInput.
     */
    public void read(StorableInput dr) throws IOException {
        setAngle(dr.readDouble());
        setOuterRadius(dr.readDouble());
        super.read(dr);
    }

    /**
     * Sets the angle of divergence for the "Crow Foot".
     */
    protected void setAngle(double newAngle) {
        fAngle = newAngle;
    }

    /**
     * Returns the angle of divergence for the "Crow Foot".
     */
    protected double getAngle() {
        return fAngle;
    }

    /**
     * Sets the outer radius (length of the diverging lines).
     */
    protected void setOuterRadius(double newOuterRadius) {
        fOuterRadius = newOuterRadius;
    }

    /**
     * Returns the outer radius (length of the diverging lines).
     */
    protected double getOuterRadius() {
        return fOuterRadius;
    }
}
