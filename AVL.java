import java.util.NoSuchElementException;

/**
 * @author José Luis Aguilar Nucamendi y Andres Díaz de León Valdés Esta clase
 *         realiza un árbol AVL
 */
public class AVL<E extends Comparable<E>> {

    // * ---------------- Atributos ----------------

    private Nodo<E> root;
    private int size;

    // * ---------------- Constructor ----------------

    public AVL() {
        this.root = null;
        this.size = 0;
    }

    // * ---------------- Main ----------------

    public static void main(String[] args) {
        AVL avl = new AVL();
        avl.insertar(4);
        avl.insertar(1);
        avl.insertar(6);
        avl.insertar(8);
        avl.insertar(6);
        System.out.println(avl.buscar(6).dato);
    }

    // * ---------------- Métodos ----------------

    // llama al metodo de insertar con el nodo the root para comparar
    public void insertar(E valor) {
        Nodo<E> tmp = this.root;
        insertar(valor, tmp);
        // if else con compare para mover el current a izq o der
        // cuando no se encuentre hay que llamar a inserta de nuevo
    }

    // Todo Optimizar el codigo
    // Todo en un if aparte manejar la creación de los nodos en vez de tenor dos
    // Todo repetidos. Tal vez se necesite un nodo Prev;

    // En este se encuentra el lugar del nodo para insertar
    private void insertar(E valor, Nodo<E> tmp) {
        if (this.size == 0) {
            this.root = new Nodo<E>(valor);
            System.out.println("Root asignado");
            this.size++;
            return;
        }

        int cmp = valor.compareTo(tmp.dato);

        if (cmp < 0) {
            if (tmp.izq == null) {
                tmp.izq = new Nodo<E>(valor);
                System.out.println(valor + " Se puso a la izquierda de " + tmp.dato);
                this.size++;
                return;
            }
            insertar(valor, tmp.izq);
        } else if (cmp > 0) {
            if (tmp.der == null) {
                tmp.der = new Nodo<E>(valor);
                System.out.println(valor + " Se puso a la derecha de " + tmp.dato);
                this.size++;
                return;
            }
            insertar(valor, tmp.der);
        } else { // Caso donde el elemento ya existe en el árbol
            System.out.println("El Elemento ya existe");
            return;
        }
    }

    public Nodo<E> buscar(E val) {
        if (this.size == 0)
            throw new NoSuchElementException("El arbol esta vacio");

        Nodo<E> tmp = this.root;
        return buscar(val, tmp);
    }

    private Nodo<E> buscar(E valor, Nodo<E> tmp) {
        int cmp = valor.compareTo(tmp.dato);
        if (tmp.dato == null) {
            return null;
        }

        if (cmp < 0) {
            buscar(valor, tmp.izq);
        } else if (cmp > 0) {
            buscar(valor, tmp.der);
        }
        return tmp;
    }

    // Este metodo encuentra el nodo del valor dado y lo iguala a un nodo tmp
    public void inorden(E val) {
        Nodo<E> tmp = buscar(val);
        inorden(val, tmp);
    }

    // con el nodo temporal reccoremos el arbol iniciando desde al valor dado
    private void inorden(E val, Nodo<E> tmp) {

    }

}

// * ---------------- Clase Nodo ----------------

class Nodo<E extends Comparable<E>> {

    Nodo<E> izq;
    Nodo<E> der;
    E dato;
    int altura;
    int fb;

    public Nodo(E dato) {
        this(dato, null, null);
    }

    public Nodo(E dato, Nodo<E> izquierda, Nodo<E> derecha) {
        this.dato = dato;
        this.izq = izq;
        this.der = der;
    }
}