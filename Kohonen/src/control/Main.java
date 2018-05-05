package control;

import java.io.IOException;
import javax.swing.UnsupportedLookAndFeelException;
import model.FileOrganizer;
import view.Kohonen;

/**
 *
 * @author Weverson S. Gomes
 */
public class Main {

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException ex) {
        } catch (IllegalAccessException ex) {
        } catch (InstantiationException ex) {
        } catch (ClassNotFoundException ex) {
        }
        Kohonen kohonen = new Kohonen();
        kohonen.setVisible(true);
        //FileOrganizer.deleteColumn(9, "Breast Cancer Wisconsin/onlyMalignant", "Breast Cancer Wisconsin/onlyMalignant.txt", ",");
    }
}
