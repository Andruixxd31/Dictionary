import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Controles extends JPanel {
	private JTextField tfInsertar;
	private JButton agregar, eliminar, preorden, inorden, posorden, nivel;
	private AVL<Integer> avl;
	private PanelDibujo pt;

	public Controles(PanelDibujo pt, AVL<Integer> avl) {
		super();
		this.setPreferredSize(new Dimension(200, 55));
		this.setBackground(new Color(224, 125, 124));

		this.avl = avl;
		this.pt = pt;

		this.agregar = new JButton("Agregar");
		this.agregar.setFont(new Font("Arial", Font.BOLD, 13));
		this.agregar.setBackground(new Color(180, 230, 30));
		this.agregar.setPreferredSize(new Dimension(120, 25));
		this.agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonAgregar();
			}
		});
		this.add(this.agregar);

		// Boton eliminars
		this.eliminar = new JButton("Eliminar");
		this.eliminar.setFont(new Font("Arial", Font.BOLD, 13));
		this.eliminar.setBackground(new Color(180, 230, 30));
		this.eliminar.setPreferredSize(new Dimension(120, 25));
		this.eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonEliminar();
			}
		});
		this.add(this.eliminar);

		// Boton preorden
		this.preorden = new JButton("PreOrden");
		this.preorden.setFont(new Font("Arial", Font.BOLD, 13));
		this.preorden.setBackground(new Color(180, 230, 30));
		this.preorden.setPreferredSize(new Dimension(120, 25));
		this.preorden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonPreorden();
			}
		});
		this.add(this.preorden);

		// Boton inorden
		this.inorden = new JButton("Inorden");
		this.inorden.setFont(new Font("Arial", Font.BOLD, 13));
		this.inorden.setBackground(new Color(180, 230, 30));
		this.inorden.setPreferredSize(new Dimension(120, 25));
		this.inorden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonInorden();
			}
		});
		this.add(this.inorden);

		// Boton postorden
		this.posorden = new JButton("Posorden");
		this.posorden.setFont(new Font("Arial", Font.BOLD, 13));
		this.posorden.setBackground(new Color(180, 230, 30));
		this.posorden.setPreferredSize(new Dimension(120, 25));
		this.posorden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonPostorden();
			}
		});
		this.add(this.posorden);

		// Boton postorden
		this.nivel = new JButton("Nivel");
		this.nivel.setFont(new Font("Arial", Font.BOLD, 13));
		this.nivel.setBackground(new Color(180, 230, 30));
		this.nivel.setPreferredSize(new Dimension(120, 25));
		this.nivel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botonNivel();
			}
		});
		this.add(this.nivel);
	}

	// ********************** M�todos *************************

	public void botonAgregar() {
		try {
			Integer valor = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el valor:"));
			if (avl.contiene(valor) == false) {
				avl.insertar(valor);
				pt.repaint();
			} else {
				JOptionPane.showMessageDialog(null, "No puedes ingresar datos repetidos");
			}
		} catch (NullPointerException ev) {
			JOptionPane.showMessageDialog(null, "No ingresaste ningun dato");
		} catch (NumberFormatException evt) {
			JOptionPane.showMessageDialog(null, "No ingresaste un Número");
		}
	}

	public void botonEliminar() {
		try {
			Integer numero = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el número"));
			if (avl.contiene(numero)) {
				avl.remover(numero);
				pt.repaint();
			} else {
				JOptionPane.showMessageDialog(null, "No existe ese dato");
			}
		} catch (NullPointerException ev) {
			JOptionPane.showMessageDialog(null, "No ingresaste ningun dato");
		} catch (NumberFormatException evt) {
			JOptionPane.showMessageDialog(null, "No ingresaste un Número");
		}
	}

	public void botonPreorden() {
		try {
			String nodos = avl.preorden();
			JOptionPane.showMessageDialog(null, nodos);
		} catch (NullPointerException ev) {
			JOptionPane.showMessageDialog(null, "No ingresaste ningun dato");
		} catch (NumberFormatException evt) {
			JOptionPane.showMessageDialog(null, "No ingresaste un Número");
		}
	}

	public void botonInorden() {
		try {
			String nodos = avl.inorden();
			JOptionPane.showMessageDialog(null, nodos);
		} catch (NullPointerException ev) {
			JOptionPane.showMessageDialog(null, "No ingresaste ningun dato");
		} catch (NumberFormatException evt) {
			JOptionPane.showMessageDialog(null, "No ingresaste un Número");
		}
	}

	public void botonPostorden() {
		try {
			String nodos = avl.postorden();
			JOptionPane.showMessageDialog(null, nodos);
		} catch (NullPointerException ev) {
			JOptionPane.showMessageDialog(null, "No ingresaste ningun dato");
		} catch (NumberFormatException evt) {
			JOptionPane.showMessageDialog(null, "No ingresaste un Número");
		}
	}

	public void botonNivel() {
		try {
			String nodos = avl.nivel();
			JOptionPane.showMessageDialog(null, nodos);
		} catch (NullPointerException ev) {
			JOptionPane.showMessageDialog(null, "No ingresaste ningun dato");
		} catch (NumberFormatException evt) {
			JOptionPane.showMessageDialog(null, "No ingresaste un Número");
		}
	}

}
