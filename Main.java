import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
	private AVL<Integer> avl;

	public Main() {
		super("Arbol AVL");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.avl = new AVL();
		PanelDibujo pt = new PanelDibujo(avl);
		this.add(pt, BorderLayout.NORTH);

		Controles p = new Controles(pt, avl);
		this.add(p, BorderLayout.SOUTH);
		this.pack();// Hace que la ventana se ajuste a sus componentes
		this.setVisible(true);

	}

	public static void main(String[] args) {
		Main v1 = new Main();
	}

}
