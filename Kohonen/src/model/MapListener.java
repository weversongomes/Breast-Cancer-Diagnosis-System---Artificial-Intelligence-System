package model;

import java.util.EventListener;

/**
 *
 * @author Weverson S. Gomes
 */
public interface MapListener extends EventListener {
    public void train(MapEvent event);
}
