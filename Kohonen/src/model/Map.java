package model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.LinkedList;

/**
 * @author Weverson S. Gomes
 *
 */
public class Map {

    private double neighborhood;
    private double initialWidth, width;
    private int interactions = 0;
    private double lateralDistance;// Distance between winner and excited neuron
    private Neuron[][] map;
    private double initialLearning, learning;
    private boolean isTrained = false;
    private int numAttributes;
    private Neuron[] firstWinners;
    private int size = 0;
    private LinkedList<MapListener> mapListeners = new LinkedList();

    /**
     * @param mapSize
     */
    public Map(int mapSize, int numAttributes) {
        size = mapSize;
        initialWidth = mapSize - 1;
        this.numAttributes = numAttributes;
        map = new Neuron[mapSize][mapSize];
        Neuron temp;
        int count = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                temp = new Neuron(numAttributes);
                temp.setName(count);
                temp.setX(j);
                temp.setY(i);
                this.map[i][j] = temp;
                count++;
            }
        }
        this.setInitialLearning(0.1);
        this.initializeWeights();
    }

    /**
     * @return size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * @return map
     */
    public Neuron[][] getMap() {
        return map;
    }

    /**
     * @return the winners of the train
     */
    public Neuron[] getFirstWinners() {
        return firstWinners;
    }

    /**
     * @return numAttributes
     */
    public int getNumAttributes() {
        return numAttributes;
    }

    /**
     * @return
     */
    public double getInitialLearning() {
        return initialLearning;
    }

    /**
     * @param initialLearning
     */
    private void setInitialLearning(double initialLearning) {
        this.initialLearning = initialLearning;
    }

    /**
     * Initialize the array of weights of each neuron
     */
    private void initializeWeights() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j].initializeWeights();
            }
        }
    }

    /**
     * Adjust the weight vector of all the excited neurons, applying the
     * gaussian function
     *
     * @param winner - the neuron winner for a particular input pattern
     * @param weight - the training pattern
     * @return true if the weight was changed, and false if the weight wasn't
     * changed
     */
    private void adjustWeights(Neuron winner, double[] weight) {
        double[] newWeight;
        int count;
        Neuron excited;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                newWeight = new double[winner.getWeight().length];
                count = 0;
                excited = this.map[i][j];
                for (double oldWeight : excited.getWeight()) {
                    newWeight[count] = (oldWeight + calcLearning()
                            * calcNeighborhood(excited, winner)
                            * (weight[count] - oldWeight));
                    count++;
                }
                excited.setWeight(newWeight);
            }
        }
    }

    /**
     * Calculate the neuron winner
     *
     * @param weight
     * @return the neuron winner
     */
    public Neuron defineWinner(double[] weight) {
        Neuron winner = null;
        int count;
        double sum;
        double euclideanDistance1 = 9999999;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                count = 0;
                sum = 0;
                for (double wt : map[i][j].getWeight()) {
                    sum = (sum + Math.pow(weight[count] - wt, 2));
                    count++;
                }
                double euclideanDistance2 = Math.sqrt(sum);
                if (euclideanDistance2 < euclideanDistance1) {
                    winner = map[i][j];
                    euclideanDistance1 = euclideanDistance2;
                }
            }
        }
        return winner;
    }

    /**
     * Train the map
     *
     * @param path
     * @param numPatterns
     * @return
     * @throws IOException
     */
    public Neuron[] train(String path, String separator) throws IOException {
        int auxCount = 0;

        double[][] matrizAprendizado = FileOrganizer.fileToMatrix(path, separator);
        
        int qtdLinha = 0;
        try {
            File arquivoLeitura = new File(path);
            LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(arquivoLeitura));
            linhaLeitura.skip(arquivoLeitura.length());
            qtdLinha = linhaLeitura.getLineNumber() + 1;
            linhaLeitura.close();
        } catch (IOException e) {
        }
        
        Neuron[] neuronWinner = new Neuron[qtdLinha];
        for (int k = 0; k < qtdLinha; k++) {
            neuronWinner[k] = new Neuron(numAttributes);
            neuronWinner[k].setName(9999);
        }

        while (isTrained == false) {
            auxCount++;

            isTrained = true;
            Neuron winner;
            for (int i = 0; i < qtdLinha; i++) {

                winner = this.defineWinner(matrizAprendizado[i]);

                this.adjustWeights(winner, matrizAprendizado[i]);

                interactions++;

                if (winner.getName() != neuronWinner[i].getName()) {
                    isTrained = false;
                    neuronWinner[i] = winner;
                }
            }
        }
        this.disparaRedeTreinada(auxCount);
        this.isTrained = true;
        firstWinners = neuronWinner;
        return neuronWinner;
    }

    /**
     * @return The width value of the gaussian function
     */
    private double calcWidth() {
        width = initialWidth * Math.exp(-(interactions / (1000 / Math.log(initialWidth))));
        return width;
    }

    /**
     * @param excited
     * @param winner
     * @return neighborhood
     */
    private double calcNeighborhood(Neuron excited, Neuron winner) {
        neighborhood = Math.exp(-(Math.pow(calcLateralDistance(excited, winner), 2) / (2 * Math.pow(calcWidth(), 2))));
        return neighborhood;
    }

    /**
     * @param excited
     * @param winner
     * @return lateral distance of the neurons
     */
    private double calcLateralDistance(Neuron excited, Neuron winner) {
        double sum;
        sum = Math.pow(winner.getX() - excited.getX(), 2) + Math.pow(winner.getY() - excited.getY(), 2);
        lateralDistance = Math.sqrt(sum);

        return lateralDistance;
    }

    /**
     * @return The learning rate
     */
    private double calcLearning() {
        learning = initialLearning * Math.exp(-(interactions / 1000));
        return learning;
    }

    public synchronized void addMapListener(MapListener listener) {
        if (mapListeners.contains(listener)) {
            return;
        }
        this.mapListeners.add(listener);
    }

    private synchronized void disparaRedeTreinada(int numEpocas) {
        for (MapListener listener : this.mapListeners) {
            listener.train(new MapEvent(this, numEpocas));
        }
    }
    
    public boolean isTrained() {
        return this.isTrained;
    }
}
