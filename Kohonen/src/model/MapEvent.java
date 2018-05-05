package model;

import java.util.EventObject;

/**
 *
 * @author Weverson S. Gomes
 */
public class MapEvent extends EventObject {
    private int epocas = 0;
    public MapEvent(Map source) {
        super(source);
    }
    
    public MapEvent(Map source, int epocas) {
        this(source);
        this.epocas = epocas;
    }
    
    public int getEpocas() {
        return epocas;
    }
}
