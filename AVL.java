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

    // * ---------------- Métodos ----------------

    /***
     * Método de preparación para imprimir el árbol con nuestro método recursivo
     * Siempre toma el nodo raiz
     */
    public boolean imprimirArb() {
        if (this.root == null) {
            System.out.println("El arbol está vacio");
            return false;
        }
        Nodo<E> current = this.root;
        imprimirArb(current, 0, 5);
        return true;
    }

    private void imprimirArb(Nodo<E> actual, int espacio, int cuenta) {
        if (actual == null)
            return;
        espacio += cuenta;
        imprimirArb(actual.der, espacio, cuenta);
        System.out.print("\n");
        for (int i = cuenta; i < espacio; i++)
            System.out.print(" ");
        System.out.print(actual.dato + "\n");
        // Ahora llamamos al nodo izquierdo
        imprimirArb(actual.izq, espacio, cuenta);
    }

    /***
     * @param valor datos a insertat en el árbol Método insertar con el nodo de root
     *              para comparar
     */
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
                if (tmp.izq.altura > tmp.der.altura) {
                    Nodo<E> valS = predecesor(tmp);
                    tmp.izq = remover(valS.dato, tmp.izq);
                    tmp.dato = valS.dato;
                } else {
                    Nodo<E> valP = sucesor(tmp);
                    tmp.der = remover(valP.dato, tmp.der);
                    tmp.dato = valP.dato;
                }
            }
        }
        actualizar(tmp);
        return balance(tmp);
    }

    /***
     * Método que encuentra el sucesor de un nodo
     * 
     * @param tmp el nodo a encontrar su sucesor
     * @return el nodo
     */
    public Nodo<E> sucesor(Nodo<E> actual) {
        Nodo<E> sucesor = actual.der;
        while (sucesor.izq != null) {
            sucesor = sucesor.izq;
        }
        return sucesor;
    }

    private Nodo<E> predecesor(Nodo<E> actual) {
        Nodo<E> predecesor = actual.izq;
        while (predecesor.der != null) {
            predecesor = predecesor.der;
        }
        return predecesor;
    }

    /**
     * Metodo que nos identifica si el valor esta dentro de nuestro arbol
     * 
     * @param dato a buscar
     * @return true si se encuentra, false si no
     */
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

    /***
     * Método similar al de contiene, pero este nos regresa el dato que buscabamos
     * 
     * @param valor dato a buscar
     * @return el dato si se encuentra
     */
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

    /**
     * Método para buscar el nodo del valor que ingresamos dentro de nuestro arbol
     * 
     * @param valor el valor a buscar
     * @return nos retorna su nodo del valor
     */
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
     * Método para obtener el root
     * 
     * @return el nodo
     */
    public Nodo<E> getRoot() {
        return this.root;
    }

    /**
     * Metodo de nodos completos
     * 
     * @param n un nodo
     * @return entero
     */
    public int nodosCompletos(Nodo<E> nodo) {
        if (nodo == null)
            return 0;
        else {
            if (nodo.izq != null && nodo.der != null)
                return nodosCompletos(nodo.der) + nodosCompletos(nodo.der) + 1;
            return nodosCompletos(nodo.izq) + nodosCompletos(nodo.der);
        }
    }

    /**
     * Esté método manda a llamar un método que es recursivo que ordena nuestro
     * arbol en el recorrido preorden, raiz, izquierda y derecha
     */
    public String preorden() {
        String nodos = "";
        return preorden(this.root, nodos);
    }

    /**
     * 
     * @param valor a buscar para iniciar desde ese a imprimir el subarbol
     */
    public String preorden(E valor) {
        Nodo<E> raiz = buscarNodo(valor);
        String nodos = "";
        return preorden(raiz, nodos);
    }

    private String preorden(Nodo<E> current, String nodos) {
        if (current != null) {
            nodos = nodos + current.dato + ",";
            nodos = preorden(current.izq, nodos);
            nodos = preorden(current.der, nodos);
        }
        return nodos;
    }

    /**
     * Esté método que manda a llamar un metodo que es recursivo que ordena nuestro
     * arbol en el recorrido preorden, izquierda,raiz y derecha
     * 
     * @param current nuestor nodo ya que usamos recursivida
     */
    public String inorden() {
        String nodos = "";
        return inorden(this.root, nodos);
    }

    /**
     * 
     * @param valor a buscar para iniciar desde ese a imprimir el subarbol
     */
    public String inorden(E valor) {
        Nodo<E> raiz = buscarNodo(valor);
        String nodos = "";
        return inorden(raiz, nodos);
    }

    /**
     * 
     * @param valor a buscar para iniciar desde ese a imprimir el subarbol
     */
    private String inorden(Nodo<E> current, String nodos) {
        if (current != null) {
            nodos = inorden(current.izq, nodos);
            nodos = nodos + current.dato + ", ";
            nodos = inorden(current.der, nodos);
        }
        return nodos;
    }

    /**
     * Esté método que manda a llamar un método es recursivo que ordena nuestro
     * arbol en el recorrido preorden, izquierda, derecha y raiz
     * 
     * @param current nuestor nodo ya que usamos recursivida
     */
    public String postorden() {
        String nodos = "";
        return postorden(this.root, nodos);
    }

    /**
     * 
     * @param valor a buscar para iniciar desde ese a imprimir el subarbol
     */
    public String postorden(E valor) {
        Nodo<E> raiz = buscarNodo(valor);
        String nodos = "";
        return postorden(raiz, nodos);
    }

    private String postorden(Nodo<E> current, String nodos) {
        if (current != null) {
            nodos = postorden(current.izq, nodos);
            nodos = postorden(current.der, nodos);
            nodos = nodos + current.dato + ", ";
        }
        return nodos;
    }

    /**
     * Metodo que trabaja con el arbol binario, toma el raiz nodo y llama a una
     * funcion sobrecargada que imprimiar el arbol de arriba hacia abajo
     */
    public String nivel() {
        String nodos = "";
        return nivel(this.root, nodos);
    }

    /**
     * 
     * @param valor a buscar para iniciar desde ese a imprimir el subarbol
     */
    public String nivel(E valor) {
        Nodo<E> raiz = buscarNodo(valor);
        String nodos = "";
        return nivel(raiz, nodos);
    }

    private String nivel(Nodo<E> current, String nodos) {
        Queue<Nodo<E>> cola = new LinkedList<Nodo<E>>();
        cola.add(current); // agrega el nodo raiz
        while (cola.size() > 0) {
            if (cola.peek().izq != null) // checa si hay un elemento izquierdo primer objeto de la pila
                cola.add(cola.peek().izq); // agrega el valor del hijo izquierdo
            if (cola.peek().der != null) // ve si hay un elemento a su derecha
                cola.add(cola.peek().der); // Agrega el valor del hijo de la derecha
            nodos = nodos + cola.poll().dato + ",";
        }
        return nodos;
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

    public Nodo<E> getIzq() {
        return izq;
    }

    public void setIzq(Nodo<E> izq) {
        this.izq = izq;
    }

    public Nodo<E> getDer() {
        return der;
    }

    public void setDer(Nodo<E> der) {
        this.der = der;
    }

    public E getDato() {
        return dato;
    }

    public void setDato(E dato) {
        this.dato = dato;
    }

}