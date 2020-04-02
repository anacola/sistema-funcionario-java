package sistema;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sistema {

	public static JPanel tela;
	public static JFrame frame;
	
	public static void main(String[] args) {
		criarComponentes();
	}
	
	private static void criarComponentes() {
		frame = new JFrame("Sistema");
		frame.setSize(700,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		Navegador.login();
	}
	
}
