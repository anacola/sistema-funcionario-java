package sistema.telas;

import javax.swing.JLabel;
import javax.swing.JPanel;
import sistema.Navegador;

public class Inicio extends JPanel{
	JLabel labelTitulo;

	public Inicio() {
		criarComponentes();
		criarEventos();
		Navegador.habilitarMenu();
	}
	
	private void criarComponentes() {
		setLayout(null);
		
		labelTitulo = new JLabel("Escolha uma opção no menu superior", JLabel.CENTER);
		labelTitulo.setBounds(20, 100, 660, 40);
		
		add(labelTitulo);
		
		setVisible(true);
	}
	
	private void criarEventos() {
	
	}

}
