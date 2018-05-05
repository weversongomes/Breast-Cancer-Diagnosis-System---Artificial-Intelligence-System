package model;

/**
 *
 * @author Weverson Gomes
 */
public class Calc {

    public static int[][] KMeans(int clusters, double[][] winner, int width) {
        double[][] centroide = new double[clusters][width];
        double[][] distanceToTheCentroid;
        
        /* Cada linha indica o cluster ao qual ela está mais próximo,
         * só uma coluna pode conter o valor 1, o restante vale 0
         * numero de colunas = numero de clusters
         */
        int[][] classificationMatrix;
        int[][] aux;
        
        boolean changed = true;
        
        distanceToTheCentroid = new double[winner.length][clusters];
        classificationMatrix = new int[winner.length][clusters];
        aux = new int[winner.length][clusters];

        //1º. Determine the centroids coordinate
        for (int i = 0; i < clusters; i++) {
            centroide[i] = winner[i];
        }
        while (changed) {
            changed = false;
            
            //2º. Determine the distance of each object to the centroids
            for (int k = 0; k < centroide.length; k++) {
                for (int i = 0; i < winner.length; i++) {
                    double sum = 0;
                    for (int j = 0; j < winner[0].length; j++) {
                        sum = sum + Math.pow(winner[i][j] - centroide[k][j], 2);
                    }
                    distanceToTheCentroid[i][k] = Math.sqrt(sum);
                }
            }

            //3º. Group the objects based on minimun distance
            for (int i = 0; i < distanceToTheCentroid.length; i++) {
                double minimun = 999999;
                for (int j = 0; j < distanceToTheCentroid[0].length; j++) {
                    aux[i][j] = 0;//aproveitando laço para inicializar a matriz aux
                    if (distanceToTheCentroid[i][j] < minimun) {
                        minimun = distanceToTheCentroid[i][j];
                        classificationMatrix[i][j] = 1;
                        if (j != 0) {
                            for (int g = 0; g < j; g++) {
                                classificationMatrix[i][g] = 0;
                            }
                        }
                    }
                }
            }
            
            System.out.println("MATRIZ DE CLASSIFICAÇÃO");
            for (int i = 0; i < classificationMatrix.length; i++) {
                for (int j = 0; j < classificationMatrix[0].length; j++) {
                    System.out.print(classificationMatrix[i][j]+" ");
                }
                System.out.println();
            }

            int[] numOfObjects = new int[clusters];//numero de objetos em cada grupo
            //initialization
            for (int k = 0; k < clusters; k++) {
                numOfObjects[k] = 0;
            }

            //4º. Determine centroids
            for (int i = 0; i < classificationMatrix[0].length; i++) {
                double sum[] = new double[width];//soma de pesos dos neuronios
                double average[] = new double[width];//media de pesos dos neuronios

                //initialization
                for (int k = 0; k < width; k++) {
                    sum[k] = 0;
                }

                for (int j = 0; j < classificationMatrix.length; j++) {
                    if (classificationMatrix[j][i] == 1) {
                        numOfObjects[i]++;
                        for (int k = 0; k < width; k++) {
                            sum[k] = sum[k] + winner[j][k];
                        }
                    }
                }
                for (int k = 0; k < width; k++) {
                    average[k] = sum[k] / numOfObjects[i];
                }
                centroide[i] = average;
                for (int j = 0; j < classificationMatrix.length; j++) {
                    if (classificationMatrix[j][i] != aux[j][i]) {
                        changed = true;
                    }
                }
            }
            aux = classificationMatrix;
        }

        return classificationMatrix;
    }
}