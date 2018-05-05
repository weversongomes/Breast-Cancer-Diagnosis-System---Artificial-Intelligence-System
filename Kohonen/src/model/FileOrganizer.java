package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @author Weverson S. Gomes
 *
 */
public class FileOrganizer {

    /**
     * Read the specified file e pass it to an matrix
     *
     * @param path
     * @param width , height
     * @return an matrix with all the patterns of the file
     * @throws IOException
     */
    public static double[][] fileToMatrix(String path, String separator) throws IOException {
        int qtdLinha = FileOrganizer.getNumberOfLines(path);
        int width;
        double[][] matrizAprendizado;

        // Read the file
        try {
            BufferedReader bfRd = new BufferedReader(new FileReader(path));
            String st;
            st = bfRd.readLine();
            String sst[] = st.split(separator);
            width = sst.length;
            matrizAprendizado = new double[qtdLinha][width];
            System.out.println("LARGURA EH: " + width);
            bfRd.close();
            
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            int i = 0;
            while (in.ready()) {
                str = in.readLine();
                String s[] = str.split(separator);
                for (int j = 0; j < width; j++) {
                    matrizAprendizado[i][j] = Double.parseDouble(s[j]);
                }
                i++;
            }
            in.close();
        } catch (IOException e) {
            throw new IOException(
                    "Erro! Nao foi possivel ler o arquivo de treinamento da rede.");
        }
        return matrizAprendizado;
    }

    public static void matrixToFile(String separator, double[][] matrix, String path) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            StringBuilder aux;
            String str[] = new String[matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                aux = new StringBuilder();
                for (int j = 0; j < matrix[0].length; j++) {
                    aux.append(matrix[i][j]);
                    if (j < matrix[0].length - 1) {
                        aux.append(separator);
                    }
                }
                out.write(aux.toString() + "\n");
            }
            out.close();
        } catch (IOException e) {
        }
    }

    /**
     *
     * @param column
     * @param file
     * @param newFile
     */
    public static void deleteColumn(int column, String file, String newFile, String separator) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
            String str;
            while (in.ready()) {
                str = in.readLine();
                String s[] = str.split(separator);
                int count = 0;
                for (String temp : s) {
                    if (count != column) {
                        out.write(temp);
                        if (column != s.length - 1) {
                            if (count != s.length - 1) {
                                out.write(separator);
                            }
                        } else {
                            if (count != s.length - 2) {
                                out.write(separator);
                            }
                        }
                    }
                    count++;
                }
                out.write("\n");
            }
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar deletar do arquivo a coluna expecificada");
        }
    }

    /**
     * Take line when the specified column is equals to a specified value
     *
     * @param column - the specified column
     * @param value - the value searched
     * @param file - the path of the file
     * @param newFile - the new file with the selected lines
     * @param separator - the type of the separator used on file
     */
    public static void takeLineWhereColumnEqualsTo(int column, double value, String file, String newFile, String separator) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
            String str;
            while (in.ready()) {
                str = in.readLine();
                String s[] = str.split(separator);
                if (Double.parseDouble(s[column]) == value) {
                    out.write(str + "\n");
                }
            }
            in.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

 /**
     * Take line when the specified column is equals to a specified value
     *
     * @param column - the specified column
     * @param value - the value searched
     * @param file - the path of the file
     * @param newFile - the new file with the selected lines
     * @param separator - the type of the separator used on file
     */
    public static void takeLineWhereColumnEqualsTo(int column, String value, String file, String newFile, String separator) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
            String str;
            while (in.ready()) {
                str = in.readLine();
                String s[] = str.split(separator);
                if (s[column].equals(value)) {
                    out.write(str + "\n");
                }
            }
            in.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
    
    /**
     * Delete lines that has a specific simbol
     * @param file
     * @param newFile
     * @param value
     * @param separator 
     */
    public static void deleteLinesThatHave(String file, String newFile, String value, String separator) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
            String str;
            boolean curinga;
            while (in.ready()) {
                curinga = false;
                str = in.readLine();
                String s[] = str.split(separator);
                for (String temp : s) {
                    if (temp.equals(value)) {
                        curinga = true;
                    }
                }
                if (curinga == false) {
                    out.write(str + "\n");
                }
            }
            in.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    /**
     * Normalize the file
     * @param matrix
     * @return 
     */
    public static double[][] normalize(double[][] matrix) {
        double[] aux = new double[matrix[0].length];
        for (int i = 0; i < aux.length; i++) {
            aux[i] = 0;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (aux[i] < matrix[j][i]) {
                    aux[i] = matrix[j][i];
                }
            }
        }
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] = matrix[j][i] / aux[i];
            }
        }
        return matrix;
    }
     
    public static void generateTrainAndTest() {
                try {
                    double matrix[][];
                    String separator = ",";
                    FileOrganizer.deleteColumn(4, "dados_iris/iris.txt", "dados_iris/unclassified.txt", separator);
                    matrix = FileOrganizer.fileToMatrix("dados_iris/unclassified.txt", separator);
                    FileOrganizer.normalize(matrix);
                    FileOrganizer.matrixToFile(separator, matrix, "dados_iris/normalizado.txt");
                } catch (IOException e) {
                    System.out.println("Algo deu errado");
                }
    }
    
    public static int getNumberOfLines(String path) {
        int qtdLinha = 0;
        try {
        File arquivoLeitura = new File(path);
        LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(arquivoLeitura));
        linhaLeitura.skip(arquivoLeitura.length());
        qtdLinha = linhaLeitura.getLineNumber() + 1;
        linhaLeitura.close();
        } catch (IOException e) {
            return qtdLinha;
        }
        return qtdLinha;
    }
}
