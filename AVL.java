import java.util.NoSuchElementException;
import java.util.*;

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
        avl.insertar(8);
        avl.insertar(3);
        avl.insertar(6);
        avl.insertar(7);
        avl.insertar(13);
        avl.insertar(11);
        avl.insertar(26);
        avl.insertar(0);
        avl.insertar(14);
        avl.insertar(-2);
        avl.insertar(2);
        avl.insertar(5);

        System.out.println(avl.remover(11));
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
            this.size++;
            return;
        }

        int cmp = valor.compareTo(tmp.dato);

        if (cmp < 0) {
            if (tmp.izq == null) {
                tmp.izq = new Nodo<E>(valor);
                this.size++;
                return;
            }
            insertar(valor, tmp.izq);
        } else if (cmp > 0) {
            if (tmp.der == null) {
                tmp.der = new Nodo<E>(valor);
                this.size++;
                return;
            }
            insertar(valor, tmp.der);
        } else { // Caso donde el elemento ya existe en el árbol
            return;
        }
    }

    /**
     * Método remover en donde el usuario ingresa el dato que quiere eliminar en el
     * árbol
     * 
     * @param dato elemento a eliminar que indicó el usuario
     * @return
     */
    public E remover(E dato) {
        Nodo<E> tmp = this.root;
        return remover(dato, tmp);
    }

    private E remover(E dato, Nodo<E> tmp) {
        if (!contiene(dato))
            throw new NoSuchElementException("El dato no esta");

        int cmp = dato.compareTo(tmp.dato);

        Nodo<E> prev = null;
        if (cmp < 0) {
            prev = tmp;
            return remover(dato, tmp.izq);
        } else if (cmp > 0) {
            prev = tmp;
            return remover(dato, tmp.der);
        }

        // * 3 casos
        if (tmp.izq == null && tmp.der == null) { // Nodo hoja
            tmp.dato = null;
        } else if (tmp.izq == null) { // hay subarbol dereche
            if (prev.der == tmp) {
                prev.der = tmp.der;
            } else {
                prev.izq = tmp.der;
            }
            tmp.der = null;
            tmp.dato = null;

        } else if (tmp.der == null) { // Hay subarbol izquierdo
            if (prev.izq == tmp) {
                prev.izq = tmp.izq;
            } else {
                prev.der = tmp.izq;
            }
            tmp.dato = null;
            tmp.izq = null;
        } else { // Tiene los dos subarboles
            if (prev.izq == tmp) {
                prev.izq = sucesor(tmp.der);
            } else {
                prev.der = sucesor(tmp.der);
            }
            tmp.dato = null;
            tmp.izq = null;
        }
        return dato;
    }

    public Nodo<E> sucesor(Nodo<E> tmp) {
        if (tmp.izq == null)
            return tmp;
        return sucesor(tmp.izq);
    }

    public boolean contiene(E dato) {
        Nodo<E> tmp = this.root;
        return contiene(dato, tmp);
    }

    private boolean contiene(E dato, Nodo<E> tmp) {
        if (tmp == null)
            return false;

        int cmp = dato.compareTo(tmp.dato);
        if (cmp < 0)
            return contiene(dato, tmp.izq);
        else if (cmp > 0)
            return contiene(dato, tmp.der);
        else
            return true;
    }

    public E buscarDato(E valor) {
        if (this.root == null)
            throw new NoSuchElementException("El árbol está vacio");

        Nodo<E> tmp = this.root;
        while (tmp.dato != valor) {
            if (valor.compareTo(tmp.dato) < 0) {
                tmp = tmp.izq;
            } else {
                tmp = tmp.der;
            }
            if (tmp == null) {
                throw new NoSuchElementException("No se encontro el dato en el árbol");
            }
        }
        return tmp.dato;
    }

    public Nodo<E> buscarNodo(E valor) {
        if (this.root == null)
            throw new NoSuchElementException("El árbol está vacio");

        Nodo<E> tmp = this.root;
        while (tmp.dato != valor) {
            if (valor.compareTo(tmp.dato) < 0) {
                tmp = tmp.izq;
            } else {
                tmp = tmp.der;
            }
            if (tmp == null) {
                throw new NoSuchElementException("No se encontró el dato en el árbol");
            }
        }
        return tmp;
    }

    private void actualizar(Nodo<E> nodo) {

        int alturaNodoIzq = (nodo.izq == null) ? -1 : nodo.izq.altura;
        int alturaNodoDer = (nodo.der == null) ? -1 : nodo.der.altura;

        nodo.altura = 1 + Math.max(alturaNodoIzq, alturaNodoDer);
        nodo.fb = alturaNodoDer - alturaNodoIzq;
    }

    private Nodo balance(Nodo nodo) {

        // izq heavy subtree.
        if (nodo.fb == -2) {
            // izq-izq case.
            if (nodo.izq.fb <= 0) {
                // return izqizqCase(node);
            } else {
                // return izqderCase(node);
            }
            // der heavy subtree needs balancing.
        } else if (nodo.fb == +2) {
            // der-der case.
            if (nodo.der.fb >= 0) {
                // return derderCase(node);
                // der-izq case.
            } else {
                // return derizqCase(node);
            }
        }
        // Node either has a balance factor of 0, +1 or -1 which is fine.
        return nodo;
    }

    private Nodo casoIzq(Nodo nodo) {
        return nodo;
    }

    private Nodo casoIzqIzq(Nodo nodo) {
        return nodo;
    }

    private Nodo casoDer(Nodo nodo) {
        return nodo;
    }

    private Nodo casoDerDer(Nodo nodo) {

        return nodo;
    }

    /**
     * Esté método manda a llamar un método que es recursivo que ordena nuestro
     * arbol en el recorrido preorden, raiz, izquierda y derecha
     */
    public void preorden() {
        preorden(this.root);
        System.out.println();
    }

    public void preorden(E valor) {
        Nodo<E> raiz = buscarNodo(valor);
        preorden(raiz);
        System.out.println();
    }

    private void preorden(Nodo<E> current) {
        if (current != null) {
            System.out.print(current.dato + ",");
            preorden(current.izq);
            preorden(current.der);
        }
    }

    /**
     * Esté método que manda a llamar un metodo que es recursivo que ordena nuestro
     * arbol en el recorrido preorden, izquierda,raiz y derecha
     * 
     * @param current nuestor nodo ya que usamos recursivida
     */
    public void inorden() {
        inorden(this.root);
        System.out.println();
    }

    public void inorden(E valor) {
        Nodo<E> raiz = buscarNodo(valor);
        inorden(raiz);
        System.out.println();
    }

    private void inorden(Nodo<E> current) {
        if (current != null) {
            inorden(current.izq);
            System.out.print(current.dato + ", ");
            inorden(current.der);
        }
    }

    /**
     * Esté método que manda a llamar un método es recursivo que ordena nuestro
     * arbol en el recorrido preorden, izquierda, derecha y raiz
     * 
     * @param current nuestor nodo ya que usamos recursivida
     */
    public void postorden() {
        postorden(this.root);
        System.out.println();
    }

    public void postorden(E valor) {
        Nodo<E> raiz = buscarNodo(valor);
        postorden(raiz);
        System.out.println();
    }

    private void postorden(Nodo<E> current) {
        if (current != null) {
            postorden(current.izq);
            postorden(current.der);
            System.out.print(current.dato + ", ");
        }
    }

    /**
     * Metodo que trabaja con el arbol binario, toma el raiz nodo y llama a una
     * funcion sobrecargada que imprimiar el arbol de arriba hacia abajo
     */

    public void nivel() {
        nivel(this.root);
        System.out.println();
    }

    public void nivel(E valor) {
        Nodo<E> raiz = buscarNodo(valor);
        nivel(raiz);
        System.out.println();
    }

    private void nivel(Nodo<E> current) {
        Queue<Nodo<E>> cola = new LinkedList<Nodo<E>>();
        cola.add(current); // agrega el nodo raiz
        while (cola.size() > 0) {
            if (cola.peek().izq != null) // checa si hay un elemento izquierdo primer objeto de la pila
                cola.add(cola.peek().izq); // agrega el valor del hijo izquierdo
            if (cola.peek().der != null) // ve si hay un elemento a su derecha
                cola.add(cola.peek().der); // Agrega el valor del hijo de la derecha
            System.out.print(cola.poll().dato + ",");
        }
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