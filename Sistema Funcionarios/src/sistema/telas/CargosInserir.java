package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.BancoDeDados;
import entidade.Cargo;

public class CargosInserir extends JPanel {
	
	JLabel labelTitulo, labelCargo;
	JTextField campoCargo;
	JButton botaoGravar;
	
	public CargosInserir() {
		criarComponentes();
		criarEventos();
	}
	
	private  void criarComponentes() {
		setLayout(null);
		
		labelTitulo = new JLabel ("Cadastro de Cargo", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelCargo = new JLabel ("Nome do cargo", JLabel.LEFT);
		campoCargo = new JTextField();
		botaoGravar = new JButton("Adicionar Cargo");
		
		labelTitulo.setBounds(20, 20, 660, 40);
		labelCargo.setBounds(150, 120, 400, 20);
		campoCargo.setBounds(150, 140, 400, 40);
		botaoGravar.setBounds(250, 380, 200, 40);
		
		add(labelTitulo);
		add(labelCargo);
		add(campoCargo);
		add(botaoGravar);
		
		setVisible(true);
	}
	
	private void criarEventos() {
		botaoGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cargo novoCargo = 	new Cargo();
				novoCargo.setNome(campoCargo.getText());
				sqlInserirCargo(novoCargo);
			}
		});
		
	}
	
private void sqlInserirCargo(Cargo novoCargo) {
		
		if(campoCargo.getText().length() <= 3) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente.");
			return;
		}
		
		Connection conexao;
		Statement instrucaoSQL;
		ResultSet resultados;
		
		try {
			
			conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			instrucaoSQL.executeUpdate("INSERT INTO cargos (nome_cargo) VALUES ('"+novoCargo.getNome()+"')");
			
			JOptionPane.showMessageDialog(null, "Cargo adicionado com sucesso!");			
		
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o cargo.");	
			Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
			
		}
	}

}