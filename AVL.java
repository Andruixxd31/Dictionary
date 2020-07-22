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
        //System.out.println(avl.buscar(6).dato);
        System.out.println("-------Busqueda-------");
		System.out.println("El dato: "+ avl.buscarDato(1)+ " fue encontrado en el árbol");
		System.out.println("-------Preorden-------");
		avl.preorden();
		System.out.println("-------InOrden-------");
		avl.inorden();
		System.out.println("-------PostOrden-------");
		avl.posorden();
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

    public E buscarDato(E valor) {
		if(this.root==null) {
			throw new NullPointerException("El árbol está vacio");
		}else {
			Nodo<E> temporal = this.root;
			while(temporal.dato!=valor) {
				if(valor.compareTo(temporal.dato)<0) {
					temporal = temporal.izq;
				}else {
					temporal = temporal.der;
				}
				if(temporal == null) {
					throw new NoSuchElementException("No se encontrarón el dato en el árbol");
				}				
			}
			return temporal.dato;
		}
	}
	
    /*
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
	*/
    
    private void preorden(Nodo<E> current) {
		if(current!= null) {
			System.out.print(current.dato+",");
			preorden(current.izq);
			preorden(current.der);
		}
		
	}
    
	/**
	 * Esté método manda a llamar un método que es recursivo que ordena nuestro arbol en el recorrido preorden, raiz, izquierda y derecha
	 * 
	 */
    
	public void preorden() {
		preorden(this.root);
		System.out.println();
	}
	

	private void inorden(Nodo<E> current){
        if (current!=null) {
        	inorden(current.izq);
        	System.out.print( current.dato + ", "  );
        	inorden(current.der);
        }
    }
	
	/**
	 * Esté método que manda a llamar un metodo que es recursivo que ordena nuestro arbol en el recorrido preorden,  izquierda,raiz y derecha
	 * @param current nuestor nodo ya que usamos recursivida
	 */
	public void inorden() {
		inorden(this.root);
		System.out.println();
	}
	
	private void posorden(Nodo<E> current) {
		if(current!=null) {
			posorden(current.izq);
			posorden(current.der);
			System.out.print(current.dato + ", "  );
		}
	}
	
	/**
	 * Esté método que manda a llamar un método es recursivo que ordena nuestro arbol en el recorrido preorden,  izquierda, derecha y raiz
	 * @param current nuestor nodo ya que usamos recursivida
	 */
	
	public void posorden() {
		posorden(this.root);
		System.out.println();
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