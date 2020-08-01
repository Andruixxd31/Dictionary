import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PanelDibujo extends JPanel  {
		private AVL avl;
		public static final int diametro = 30;
	    public static final int radio = diametro / 2;
	    public static final int ancho = 50;
		
	    public PanelDibujo(AVL avl) {
			super();
			
			this.setPreferredSize(new Dimension(900,500));
			this.setBackground(new Color(224,225,224));
			this.avl = avl;
		}	
		
		public void paint(Graphics g) {
	        super.paint(g); 
	        pintar(g, getWidth() / 2, 20, avl.getRoot());
	    }
		
		private void pintar(Graphics g, int x, int y, Nodo<Integer> n) {
	        if (n == null)
	        {}
	        else {
	            int EXTRA =avl.nodosCompletos(n) * (ancho / 2);
	            
	            g.setColor(Color.cyan);
	            g.fillOval(x, y, diametro, diametro);
	            g.setColor(Color.black);
	            g.drawString(n.getDato().toString(), x + 12, y + 18);
	            
	            if (n.getIzq() != null)
	                g.drawLine(x+radio, y+radio, x-ancho-EXTRA+radio, y+ancho+radio);
	            if(n.getDer()!=null) {
	            	g.drawLine(x+radio,y+radio,x+ancho+EXTRA+radio,y+ancho+radio);
	            pintar(g,x-ancho-EXTRA,y+ancho,n.getIzq());
	            pintar(g,x+ancho+EXTRA,y+ancho,n.getDer());
	        }
	    }
		
		
		
		
		}
}
