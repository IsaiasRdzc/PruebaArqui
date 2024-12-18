package CH.ifa.draw.samples.astahVersion.components;

import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;
import CH.ifa.draw.figures.*;
import CH.ifa.draw.util.*;

import java.util.*;
import java.util.List;
import java.awt.*;

/**
 * @version <$CURRENT_VERSION$>
 */
public class NodeText extends TextFigure {
    private static final int BORDER = 6;
    private List fConnectors;
    private boolean fConnectorsVisible;

    public NodeText() {
        initialize();
        fConnectors = null;
    }

    public Rectangle displayBox() {
        Rectangle box = super.displayBox();
        int d = BORDER;
        box.grow(d, d);
        return box;
    }

    public boolean containsPoint(int x, int y) {
        // add slop for connectors
        if (fConnectorsVisible) {
            Rectangle r = displayBox();
            int d = LocatorConnector.SIZE / 2;
            r.grow(d, d);
            return r.contains(x, y);
        }
        return super.containsPoint(x, y);
    }

    public void draw(Graphics g) {
        super.draw(g);
        // Elimina la llamada a drawBorder(g) para no dibujar los bordes
        drawConnectors(g);
    }

    public HandleEnumeration handles() {
        ConnectionFigure prototype = new LineConnection();
        List handles = CollectionsFactory.current().createList();
        handles.add(new ConnectionHandle(this, RelativeLocator.east(), prototype));
        handles.add(new ConnectionHandle(this, RelativeLocator.west(), prototype));
        handles.add(new ConnectionHandle(this, RelativeLocator.south(), prototype));
        handles.add(new ConnectionHandle(this, RelativeLocator.north(), prototype));

        handles.add(new NullHandle(this, RelativeLocator.southEast()));
        handles.add(new NullHandle(this, RelativeLocator.southWest()));
        handles.add(new NullHandle(this, RelativeLocator.northEast()));
        handles.add(new NullHandle(this, RelativeLocator.northWest()));
        return new HandleEnumerator(handles);
    }

    private void drawConnectors(Graphics g) {
        if (fConnectorsVisible) {
            Iterator iter = connectors();
            while (iter.hasNext()) {
                ((Connector) iter.next()).draw(g);
            }
        }
    }

    public void connectorVisibility(boolean isVisible, ConnectionFigure courtingConnection) {
        fConnectorsVisible = isVisible;
        invalidate();
    }

    public Connector connectorAt(int x, int y) {
        return findConnector(x, y);
    }

    private Iterator connectors() {
        if (fConnectors == null) {
            createConnectors();
        }
        return fConnectors.iterator();
    }

    private void createConnectors() {
        fConnectors = CollectionsFactory.current().createList(4);
        fConnectors.add(new LocatorConnector(this, RelativeLocator.north()));
        fConnectors.add(new LocatorConnector(this, RelativeLocator.south()));
        fConnectors.add(new LocatorConnector(this, RelativeLocator.west()));
        fConnectors.add(new LocatorConnector(this, RelativeLocator.east()));
    }

    private Connector findConnector(int x, int y) {
        // return closest connector
        long min = Long.MAX_VALUE;
        Connector closest = null;
        Iterator iter = connectors();
        while (iter.hasNext()) {
            Connector c = (Connector) iter.next();
            Point p2 = Geom.center(c.displayBox());
            long d = Geom.length2(x, y, p2.x, p2.y);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    private void initialize() {
        setText("text");
        Font fb = new Font("Helvetica", Font.BOLD, 12);
        setFont(fb);
        createConnectors();
    }

    /**
     * Usually, a TextHolders is implemented by a Figure subclass. To avoid casting
     * a TextHolder to a Figure this method can be used for polymorphism (in this
     * case, let the (same) object appear to be of another type).
     * Note, that the figure returned is not the figure to which the TextHolder is
     * (and its representing figure) connected.
     * 
     * @return figure responsible for representing the content of this TextHolder
     */
    public Figure getRepresentingFigure() {
        return this;
    }
}
