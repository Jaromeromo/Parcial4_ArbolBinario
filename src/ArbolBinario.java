import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ArbolBinario {

    private Nodo raiz;
    private int criterio;

    public ArbolBinario(Nodo raiz) {
        this.raiz = raiz;
    }

    public int getCriterio() {
        return criterio;
    }

    public void setCriterio(int criterio) {
        this.criterio = criterio;
    }

    public ArbolBinario() {
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void agregarNodo(Nodo n) {
        if (raiz == null) {
            raiz = n;
            return;
        }

        Nodo actual = raiz;
        Nodo padre;
        while (true) {
            padre = actual;
            if (n.getDocumento().equals(actual.getDocumento())) {
                actual = actual.derecho;
                if (actual == null) {
                    padre.derecho = n;
                    return;
                }
            } else if (Documento.esMayor(n.getDocumento(), actual.getDocumento(), criterio)) {
                actual = actual.derecho;
                if (actual == null) {
                    padre.derecho = n;
                    return;
                }
            } else {
                actual = actual.izquierdo;
                if (actual == null) {
                    padre.izquierdo = n;
                    return;
                }
            }
        }
    }

    // Método para mostrar todos los nodos del árbol
    public void mostrar(JTable tbl) {
        List<String[]> datos = new ArrayList<>();
        mostrarRecursivo(raiz, datos);
        
        String[][] datosArray = datos.toArray(new String[0][Documento.encabezados.length]);
        DefaultTableModel dtm = new DefaultTableModel(datosArray, Documento.encabezados);
        tbl.setModel(dtm);
    }

    // Método recursivo para mostrar todos los nodos
    private void mostrarRecursivo(Nodo nodo, List<String[]> datos) {
        if (nodo == null) return;

        mostrarRecursivo(nodo.izquierdo, datos);
        String[] fila = {
            nodo.getDocumento().getApellido1(),
            nodo.getDocumento().getApellido2(),
            nodo.getDocumento().getNombre(),
            nodo.getDocumento().getDocumento()
        };
        datos.add(fila);
        mostrarRecursivo(nodo.derecho, datos);
    }

    // Método para buscar nodos por palabra clave y mostrar todos los resultados encontrados
    public void filtrarPorPalabraClave(JTable tbl, String palabraClave) {
        List<String[]> resultados = new ArrayList<>();
        buscarRecursivo(raiz, palabraClave, resultados);

        String[][] resultadosArray = resultados.toArray(new String[0][Documento.encabezados.length]);
        DefaultTableModel dtm = new DefaultTableModel(resultadosArray, Documento.encabezados);
        tbl.setModel(dtm);
    }

    // Método recursivo para buscar nodos que contienen la palabra clave
    private void buscarRecursivo(Nodo nodo, String palabraClave, List<String[]> resultados) {
        if (nodo == null) return;

        // Añade el nodo a los resultados si contiene la palabra clave
        if (nodo.getDocumento().contienePalabra(palabraClave)) {
            String[] fila = {
                nodo.getDocumento().getApellido1(),
                nodo.getDocumento().getApellido2(),
                nodo.getDocumento().getNombre(),
                nodo.getDocumento().getDocumento()
            };
            resultados.add(fila);
        }

        // Llamadas recursivas para explorar ambos lados del árbol
        buscarRecursivo(nodo.izquierdo, palabraClave, resultados);
        buscarRecursivo(nodo.derecho, palabraClave, resultados);
    }
}


