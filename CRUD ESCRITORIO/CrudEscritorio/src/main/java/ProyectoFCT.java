
import controlador.mainViewController;
import javax.swing.UIManager;



public class ProyectoFCT {

    public static void main(String[] args) {
         //Cambiamos el look and feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (Exception e) {
        }
        mainViewController.iniciar();
    }
}
