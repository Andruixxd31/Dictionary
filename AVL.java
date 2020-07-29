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
    List<Nodo<E>> hijos;

    // * ---------------- Constructor ----------------

    public AVL() {
        this.root = null;
        this.size = 0;
    }

    // * ---------------- Main ----------------

    public static void main(String[] args) {

        AVL avl = new AVL();
        avl.insertar(8);
        avl.insertar(1);
        avl.insertar(5);
        avl.insertar(7);
        avl.insertar(13);
        avl.insertar(11);
        avl.insertar(26);
        avl.insertar(0);
        avl.insertar(14);
        avl.insertar(2);
        avl.insertar(5);

        // System.out.println(avl.remover(6));
        // avl.preOrder();
        avl.nivel();
    }

    // * ---------------- Métodos ----------------

    private void preOrder(Nodo<E> current) {
        if (current != null) {
            System.out.println(current.dato + ",");
            preOrder(current.izq);
            preOrder(current.der);
        }
    }

    public void preOrder() {
        preOrder(this.root);
        System.out.println();
    }

    // llama al metodo de insertar con el nodo the root para comparar
    public boolean insertar(E valor) {
        if (valor == null)
            return false;
        if (!contiene(valor)) {
            this.root = insertar(valor, root);
            size++;
            return true;
        }
        return false;
    }

    private Nodo<E> insertar(E valor, Nodo<E> tmp) {
        if (tmp == null)
            return new Nodo(valor);

        int cmp = valor.compareTo(tmp.dato);

        if (cmp < 0) {
            tmp.izq = insertar(valor, tmp.izq);

        } else {
            tmp.der = insertar(valor, tmp.der);
        }
        actualizar(tmp);
        return balance(tmp);
    }

    /**
     * Método remover en donde el usuario ingresa el dato que quiere eliminar en el
     * árbol
     * 
     * @param dato elemento a eliminar que indicó el usuario
     * @return
     */
    public boolean remover(E dato) {
        if (dato == null)
            return false;
        if (contiene(dato)) {
            this.root = remover(dato, this.root);
            this.size--;
            return true;
        }
        return false;
    }

    private Nodo<E> remover(E dato, Nodo<E> tmp) {
        if (!contiene(dato))
            throw new NoSuchElementException("El dato no está dentro del árbol");

        int cmp = dato.compareTo(tmp.dato);
        // System.out.println("CMP:" + cmp);

        if (cmp < 0)
            tmp.izq = remover(dato, tmp.izq);
        else if (cmp > 0)
            tmp.der = remover(dato, tmp.der);
        else {
            if (tmp.izq == null)
                return tmp.der;
            else if (tmp.der == null)
                return tmp.izq;
            else {
                if (tmp.izq.altura > tmp.der.altura)
                    tmp.izq = remover(sucesor(tmp).dato, tmp.izq);
                else
                    tmp.der = remover(sucesor(tmp).dato, tmp.der);
            }
        }

        actualizar(tmp);
        return balance(tmp);
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

    private Nodo<E> balance(Nodo<E> nodo) {
        if (nodo.fb == -2) {
            if (nodo.izq.fb <= 0) {
                return casoIzqIzq(nodo);
            } else {
                return casoIzqDer(nodo);
            }
        } else if (nodo.fb == 2) {
            if (nodo.der.fb >= 0) {
                return casoDerDer(nodo);
            } else {
                return casoDerIzq(nodo);
            }
        }
        // No se necesita balancear el arbol
        return nodo;
    }

    private Nodo<E> casoIzqIzq(Nodo<E> nodo) {
        return rotacionDer(nodo);
    }

    private Nodo<E> casoIzqDer(Nodo<E> nodo) {
        nodo.izq = rotacionIzq(nodo.izq);
        return casoIzqIzq(nodo);
    }

    private Nodo<E> casoDerDer(Nodo<E> nodo) {
        return rotacionIzq(nodo);
    }

    private Nodo casoDerIzq(Nodo nodo) {
        nodo.der = rotacionDer(nodo.der);
        return casoDerDer(nodo);
    }

    private Nodo<E> rotacionIzq(Nodo<E> nodo) {
        Nodo<E> padre = nodo.der;
        nodo.der = padre.izq;
        padre.izq = nodo;
        actualizar(nodo);
        actualizar(padre);
        return padre;
    }

    private Nodo<E> rotacionDer(Nodo<E> nodo) {
        Nodo<E> padre = nodo.izq;
        nodo.izq = padre.der;
        padre.der = nodo;
        actualizar(nodo);
        actualizar(padre);
        return padre;
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