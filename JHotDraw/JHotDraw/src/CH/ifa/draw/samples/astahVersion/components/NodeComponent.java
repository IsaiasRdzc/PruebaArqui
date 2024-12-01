package CH.ifa.draw.samples.astahVersion.components;

import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;
import CH.ifa.draw.figures.*;
import CH.ifa.draw.util.*;

import java.util.*;
import java.util.List;
import java.awt.*;

public class NodeComponent extends TextFigure {
    private static final int BORDER = 6;
    private static final int HEADER_HEIGHT = 20; // Altura del encabezado
    private List<Connector> fConnectors;
    private boolean fConnectorsVisible;

    public NodeComponent() {
        initialize();
        fConnectors = null;
    }

    @Override
    public Rectangle displayBox() {
        Rectangle box = super.displayBox();
        int d = BORDER;
        box.grow(d, d);
        box.height += HEADER_HEIGHT; // Expandir para incluir el encabezado
        return box;
    }

    @Override
    public boolean containsPoint(int x, int y) {
        // Agregar margen adicional para los conectores
        if (fConnectorsVisible) {
            Rectangle r = displayBox();
            int d = LocatorConnector.SIZE / 2;
            r.grow(d, d);
            return r.contains(x, y);
        }
        return super.containsPoint(x, y);
    }

    @Override
    public void draw(Graphics g) {
        Rectangle r = displayBox();

        // Dibujar el encabezado
        g.setColor(getFrameColor());
        g.drawRect(r.x, r.y, r.width - 1, HEADER_HEIGHT);
        g.setFont(getFont());
        g.drawString(getText(), r.x + BORDER, r.y + HEADER_HEIGHT - 5);

        // Dibujar el cuerpo
        g.drawRect(r.x, r.y + HEADER_HEIGHT, r.width - 1, r.height - HEADER_HEIGHT - 1);

        // Dibujar conectores si son visibles
        drawConnectors(g);
    }

    @Override
    public HandleEnumeration handles() {
        ConnectionFigure prototype = new LineConnection();
        List<Handle> handles = CollectionsFactory.current().createList();
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
            Iterator<Connector> iter = connectors();
            while (iter.hasNext()) {
                iter.next().draw(g);
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

    private Iterator<Connector> connectors() {
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
        // Encuentra el conector más cercano
        long min = Long.MAX_VALUE;
        Connector closest = null;
        Iterator<Connector> iter = connectors();
        while (iter.hasNext()) {
            Connector c = iter.next();
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
        setText("ENTITY"); // Texto inicial predeterminado
        Font fb = new Font("Helvetica", Font.BOLD, 12);
        setFont(fb);
        createConnectors();
    }

    @Override
    public Figure getRepresentingFigure() {
        return this;
    }
}
