package CH.ifa.draw.samples.astahVersion.components;

import CH.ifa.draw.figures.AbstractLineDecoration;
import CH.ifa.draw.figures.LineConnection;

public class DecoratedLineConnection extends LineConnection {

    /**
     * Constructor polimórfico que permite definir los decoradores de inicio y fin.
     *
     * @param startDecorador Decorador para el inicio de la línea.
     * @param endDecorador   Decorador para el final de la línea.
     */
    public DecoratedLineConnection(AbstractLineDecoration startDecorador, AbstractLineDecoration endDecorador) {
        super(); // Llama al constructor por defecto de LineConnection

        // Configurar los decoradores de inicio y fin usando los parámetros polimórficos
        setStartDecoration(startDecorador);
        setEndDecoration(endDecorador);
    }
}
