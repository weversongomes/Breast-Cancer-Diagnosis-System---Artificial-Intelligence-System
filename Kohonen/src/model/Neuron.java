package model;
import java.util.Random;

/**
 * @author Weverson S. Gomes
 *
 */
public class Neuron {
	// Position on map
	private int X, Y;

	// Vetor de pesos do neurônio
	private double[] weight;
	
	// Gerador de números randômicos
	private Random random = new Random();
	
	// posicao no mapa
	private int name;

	/**
	 * Each neuron is created with a number of attributes defined
	 * @param numAttributes
	 */
	public Neuron(int numAttributes) {
		weight = new double[numAttributes];
	}

	/**
	 * @return The X position on map
	 */
	public int getX() {
		return X;
	}

	/**
	 * @return The Y position on map
	 */
	public int getY() {
		return Y;
	}

	/**
	 * @param x
	 */
	public void setX(int x) {
		X = x;
	}

	/**
	 * @param y
	 */
	public void setY(int y) {
		Y = y;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The array of weights
	 */
	public double[] getWeight() {
		return weight;
	}

	/**
	 * Sets the weight vector
	 * @param weight
	 */
	public void setWeight(double[] weight) {
		this.weight = weight;
	}
	
	/**
	 * Initialize the weigths with values between 0 and 1
	 */
	public void initializeWeights() {
		for(int k = 0; k < weight.length; k++) {
			weight[k] = random.nextDouble();
		}
	}
}
