import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Documento {

    private final String apellido1;
    private final String apellido2;
    private final String nombre;
    private final String documento;

    public Documento(String apellido1, String apellido2, String nombre, String documento) {
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nombre = nombre;
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return apellido1 + " " + apellido2 + " " + nombre;
    }

    public boolean contienePalabra(String palabraClave) {
        String palabra = palabraClave.toLowerCase();
        return apellido1.toLowerCase().contains(palabra) ||
               apellido2.toLowerCase().contains(palabra) ||
               nombre.toLowerCase().contains(palabra) ||
               documento.toLowerCase().contains(palabra);
    }

    public static List<Documento> documentos = new ArrayList<>();
    public static String[] encabezados;

    public static void obtenerDatosDesdeArchivo(String nombreArchivo) {
        documentos.clear();
        BufferedReader br = Archivo.abrirArchivo(nombreArchivo);
        if (br != null) {
            try {
                String linea = br.readLine();
                encabezados = linea.split(";");
                linea = br.readLine();
                while (linea != null) {
                    String[] textos = linea.split(";");
                    if (textos.length >= 4) {
                        Documento d = new Documento(textos[0], textos[1], textos[2], textos[3]);
                        documentos.add(d);
                    }
                    linea = br.readLine();
                }
            } catch (IOException ex) {
                // Manejo de excepciones
            }
        }
    }

    public static void mostrarDatos(JTable tbl) {
        String[][] datos = null;
        if (!documentos.isEmpty()) {
            datos = new String[documentos.size()][encabezados.length];
            for (int i = 0; i < documentos.size(); i++) {
                datos[i][0] = documentos.get(i).apellido1;
                datos[i][1] = documentos.get(i).apellido2;
                datos[i][2] = documentos.get(i).nombre;
                datos[i][3] = documentos.get(i).documento;
            }
        }
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);
    }

    // Método para intercambiar elementos en la lista de documentos
    private static void intercambiar(int i, int j) {
        Documento temp = documentos.get(i);
        documentos.set(i, documentos.get(j));
        documentos.set(j, temp);
    }

    public static boolean esMayor(Documento d1, Documento d2, int criterio) {
        return switch (criterio) {
            case 0 -> d1.getNombreCompleto().compareTo(d2.getNombreCompleto()) > 0;
            case 1 -> d1.getDocumento().compareTo(d2.getDocumento()) > 0;
            default -> false;
        };
    }
    
    // Ordenamiento Burbuja
    public static void ordenarBurbuja(int criterio) {
        for (int i = 0; i < documentos.size() - 1; i++) {
            for (int j = 0; j < documentos.size() - i - 1; j++) {
                if (esMayor(documentos.get(j), documentos.get(j + 1), criterio)) {
                    intercambiar(j, j + 1);
                }
            }
        }
    }

    // Ordenamiento Rápido
    public static void ordenarRapido(int inicio, int fin, int criterio) {
        if (inicio >= fin) {
            return;
        }
        int pivoteIndex = particion(inicio, fin, criterio);
        ordenarRapido(inicio, pivoteIndex - 1, criterio);
        ordenarRapido(pivoteIndex + 1, fin, criterio);
    }

    private static int particion(int inicio, int fin, int criterio) {
        Documento pivote = documentos.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (!esMayor(documentos.get(j), pivote, criterio)) {
                i++;
                intercambiar(i, j);
            }
        }
        intercambiar(i + 1, fin);
        return i + 1;
    }

    // Ordenamiento por Inserción
    public static void ordenarInsercion(int criterio) {
        for (int i = 1; i < documentos.size(); i++) {
            Documento key = documentos.get(i);
            int j = i - 1;

            while (j >= 0 && esMayor(documentos.get(j), key, criterio)) {
                documentos.set(j + 1, documentos.get(j));
                j = j - 1;
            }
            documentos.set(j + 1, key);
        }
    }

    // Método que busca y devuelve el primer dato que contenga la palabra clave
    public static Documento buscarUnResultado(String palabraClave) {
        for (Documento documento : documentos) {
            if (documento.contienePalabra(palabraClave)) {
                return documento;
            }
        }
        return null;
    }

        // Método para mostrar un solo documento en la tabla
        public static void mostrarDatoUnico(JTable tbl, Documento documento) {
            String[][] datos = new String[1][encabezados.length];
            datos[0][0] = documento.getApellido1();
            datos[0][1] = documento.getApellido2();
            datos[0][2] = documento.getNombre();
            datos[0][3] = documento.getDocumento();
    
            DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
            tbl.setModel(dtm);
        }

    public static ArbolBinario getArbolBinario(int criterio) {
        ArbolBinario ab = new ArbolBinario();
        ab.setCriterio(criterio);
        for (Documento d : documentos) {
            ab.agregarNodo(new Nodo(d));
        }
        return ab;
    }
}


