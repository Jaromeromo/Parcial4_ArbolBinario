import java.io.*;
import javax.swing.*;

public class Archivo {

    // Método para mostrar una ventana que permita elegir un archivo mediante exploración
    public static String elegirArchivo() {
        JFileChooser fc = new JFileChooser();

        if (fc.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            return f.getAbsolutePath();
        } else {
            return "";
        }
    }

    // Método estático para abrir archivos planos
    public static BufferedReader abrirArchivo(String nombreArchivo) {
        File f = new File(nombreArchivo);
        // ¿Existe el archivo?
        if (f.exists()) {
            try {
                // Apertura del archivo usando try-with-resources para asegurar el cierre automático
                FileReader fr = new FileReader(f);
                return new BufferedReader(fr);
            } catch (IOException e) {
                // Sucedió un error que se captura mediante IOException
                return null;
            }
        } else {
            return null;
        }
    } // abrirArchivo

    // Método estático para guardar archivos planos dado un conjunto de líneas
    public static boolean guardarArchivo(String nombreArchivo, String[] lineas) {
        if (lineas != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
                // Usar un bucle mejorado para escribir cada línea
                for (String linea : lineas) {
                    bw.write(linea);
                    bw.newLine();
                }
                return true;
            } catch (IOException e) {
                // Sucedió un error que se captura mediante IOException
                return false;
            }
        } else {
            return false;
        }
    }
}


