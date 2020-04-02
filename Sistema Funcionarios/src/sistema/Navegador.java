package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import entidade.Cargo;
import sistema.telas.CargosEditar;
import sistema.telas.CargosConsultar;
import sistema.telas.CargosInserir;
import entidade.Funcionario;
import sistema.telas.FuncionariosConsultar;
import sistema.telas.FuncionariosInserir;
import sistema.telas.FuncionariosEditar;
import sistema.telas.RelatorioCargos;
import sistema.telas.RelatorioSalarios;
import sistema.telas.Inicio;
import sistema.telas.Login;

public class Navegador {
	private static boolean menuConstruido;
	private static boolean menuHabilitado;
	private static JMenuBar menuBar;
	private static JMenu menuArquivo, menuFuncionarios, menuCargos, menuRelatorios;
	private static JMenuItem miSair, miFuncionariosConsultar, miFuncionariosCadastrar, miCargosConsultar;
	private static JMenuItem miCargosCadastrar, miRelatoriosCargos, miRelatoriosSalarios;
	
	public static void login() {
		Sistema.tela = new Login();
		Sistema.frame.setTitle("Funcionários Company SA");
		Navegador.atualizarTela();	
	}
	
	public static void inicio() {
		Sistema.tela = new Inicio();
		Sistema.frame.setTitle("Funcionários Company SA");
		Navegador.atualizarTela();
	}
	
	public static void cargosCadastrar() {
		Sistema.tela = new CargosInserir();
		Sistema.frame.setTitle("Funcionários Company SA - Cadastrar Cargos");
		Navegador.atualizarTela();		
	}
	
	public static void cargosConsultar() {
		Sistema.tela = new CargosConsultar();
		Sistema.frame.setTitle("Funcionários Company SA - Consultar Cargos");
		Navegador.atualizarTela();	
	}
	
	public static void cargosEditar(Cargo cargo) {
		Sistema.tela = new CargosEditar(cargo);
		Sistema.frame.setTitle("Funcionários Company SA - Editar Cargos");
		Navegador.atualizarTela();
	}
	
	 public static void funcionariosCadastrar(){
		 Sistema.tela = new FuncionariosInserir();
	     Sistema.frame.setTitle("Funcionarios Company SA - Cadastrar funcionários");
	     Navegador.atualizarTela();
	 }
	    
	 public static void funcionariosConsultar(){
	     Sistema.tela = new FuncionariosConsultar();
	     Sistema.frame.setTitle("Funcionarios Company SA - Consultar funcionários");
	     Navegador.atualizarTela();
	 }

	 public static void funcionariosEditar(Funcionario funcionario){
	     Sistema.tela = new FuncionariosEditar(funcionario);  
	     Sistema.frame.setTitle("Funcionários Company SA - Editar funcionários");           
	     Navegador.atualizarTela();
	    }
	 
	 public static void relatoriosCargos(){   
	        Sistema.tela = new RelatorioCargos();
	        Sistema.frame.setTitle("Funcionários Company SA - Relatórios");    
	        Navegador.atualizarTela();
	    }
	    
	    public static void relatoriosSalarios(){
	        Sistema.tela = new RelatorioSalarios();
	        Sistema.frame.setTitle("Funcionários Company SA - Relatórios");    
	        Navegador.atualizarTela();
	    }
	     
	
	private static void atualizarTela() {
		Sistema.frame.getContentPane().removeAll();
		Sistema.tela.setVisible(true);
		Sistema.frame.add(Sistema.tela);
		Sistema.frame.setVisible(true);
	}
	
	private static void construirMenu() {
		if(!menuConstruido) {
			menuConstruido = true;
			
			menuBar = new JMenuBar();
			
			menuArquivo = new JMenu("Arquivo");
			menuBar.add(menuArquivo);
			miSair = new JMenuItem("Sair");
			menuArquivo.add(miSair);
			
			menuFuncionarios = new JMenu ("Funcionários");
			menuBar.add(menuFuncionarios);
			miFuncionariosCadastrar = new JMenuItem("Cadastrar");
			menuFuncionarios.add(miFuncionariosCadastrar);
			miFuncionariosConsultar = new JMenuItem("Consultar");
			menuFuncionarios.add(miFuncionariosConsultar);
			
			menuCargos = new JMenu("Cargos");
			menuBar.add(menuCargos);
			miCargosCadastrar = new JMenuItem("Cadastrar");
			menuCargos.add(miCargosCadastrar);
			miCargosConsultar = new JMenuItem("Consultar");
			menuCargos.add(miCargosConsultar);
			
			menuRelatorios = new JMenu("Relatórios");
			menuBar.add(menuRelatorios);
			miRelatoriosCargos = new JMenuItem("Funcionários por Cargos");
			menuRelatorios.add(miRelatoriosCargos);
			miRelatoriosSalarios = new JMenuItem("Salários dos Funcionários");
			menuRelatorios.add(miRelatoriosSalarios);
			
			criarEventosMenu();
		}
	}
	
	public static void habilitarMenu() {
		if(!menuConstruido) construirMenu();
		if(!menuHabilitado) {
			menuHabilitado = true;
			Sistema.frame.setJMenuBar(menuBar);
		}
	}
	
	public static void desabilitarMenu() {
		if(menuHabilitado) {
			menuHabilitado = false;
			Sistema.frame.setJMenuBar(null);
		}
	}
	
	private static void criarEventosMenu() {
		miSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		miFuncionariosCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				funcionariosCadastrar();
			}
		});
		miFuncionariosConsultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				funcionariosConsultar();
			
			}
		});
		
		miCargosCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargosCadastrar();
			}
		});
		
		miCargosConsultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargosConsultar();
			}
		});
		
		miRelatoriosCargos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				relatoriosCargos();
			}
		});
		
		miRelatoriosSalarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				relatoriosSalarios();
			}
		});
	}
	
}
