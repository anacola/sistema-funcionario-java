package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sistema.BancoDeDados;
import sistema.Navegador;
import entidade.Funcionario;

public class FuncionariosConsultar extends JPanel {
    
    Funcionario funcionarioAtual;
    JLabel labelTitulo, labelFuncionario;
    JTextField campoFuncionario;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Funcionario> listasFuncionariosModelo = new DefaultListModel();
    JList<Funcionario> listaFuncionarios;
            
    public FuncionariosConsultar(){
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }

    private void criarComponentes() {
        setLayout(null);
        
        labelTitulo = new JLabel("Consulta de Funcionarios", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
        labelFuncionario = new JLabel("Nome do funcionario", JLabel.LEFT);
        campoFuncionario = new JTextField();
        botaoPesquisar = new JButton("Pesquisar Funcionario");
        botaoEditar = new JButton("Editar Funcionario");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir Funcionario");
        botaoExcluir.setEnabled(false);
        listasFuncionariosModelo = new DefaultListModel();
        listaFuncionarios = new JList();
        listaFuncionarios.setModel(listasFuncionariosModelo);
        listaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelFuncionario.setBounds(150, 120, 400, 20);
        campoFuncionario.setBounds(150, 140, 400, 40);
        botaoPesquisar.setBounds(560, 140, 130, 40); 
        listaFuncionarios.setBounds(150, 200, 400, 240);
        botaoEditar.setBounds(560, 360, 130, 40); 
        botaoExcluir.setBounds(560, 400, 130, 40);
        
        add(labelTitulo);
        add(labelFuncionario);
        add(campoFuncionario);
        add(listaFuncionarios);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        setVisible(true);
    }

    private void criarEventos() {
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarFuncionarios(campoFuncionario.getText());
            }
        });
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navegador.funcionariosEditar(funcionarioAtual);
            }
        });
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletarFuncionario();
            }
        });
        listaFuncionarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                funcionarioAtual = listaFuncionarios.getSelectedValue();
                if(funcionarioAtual == null){
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }else{
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });
    }

    private void sqlPesquisarFuncionarios(String nome) {        
        // conexão
        Connection conexao;
        // instrucao SQL
        Statement instrucaoSQL;
        // resultados
        ResultSet resultados;
        
        try {
            // conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            // criando a instrução SQL 
           instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM funcionarios WHERE nome like '%"+nome+"%' order by nome ASC");
            
            listasFuncionariosModelo.clear();

            while (resultados.next()) {                
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultados.getInt("id"));
                funcionario.setNome(resultados.getString("nome"));
                funcionario.setSobrenome(resultados.getString("sobrenome"));
                funcionario.setDataNascimento(resultados.getString("data_nascimento"));
                funcionario.setEmail(resultados.getString("email"));
                if(resultados.getString("cargo") != null) funcionario.setCargo(Integer.parseInt(resultados.getString("cargo")));
                funcionario.setSalario(Double.parseDouble(resultados.getString("salario")));
                
                listasFuncionariosModelo.addElement(funcionario);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar funcionários.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sqlDeletarFuncionario() {
        String pergunta = "Deseja realmente excluir o Funcionario "+funcionarioAtual.getNome()+"?";
        int confirmacao = JOptionPane.showConfirmDialog(null, pergunta, "Excluir", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            // conexão
            Connection conexao;
            // instrucao SQL
            Statement instrucaoSQL;
            // resultados
            ResultSet resultados;

            try {
                // conectando ao banco de dados
                conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);

                // criando a instrução SQL
                instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                instrucaoSQL.executeUpdate("DELETE FROM funcionarios WHERE id="+funcionarioAtual.getId()+"");            

                JOptionPane.showMessageDialog(null, "Funcionario deletado com sucesso!");
                Navegador.inicio();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir Funcionario.");
                Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}