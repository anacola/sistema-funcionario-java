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
import sistema.Navegador;
import sistema.BancoDeDados;
import entidade.Cargo;

public class CargosConsultar extends JPanel {
    
    Cargo cargoAtual;
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Cargo> listasCargosModelo = new DefaultListModel();
    JList<Cargo> listaCargos;
            
    public CargosConsultar(){
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }

    private void criarComponentes() {
        setLayout(null);
        
        labelTitulo = new JLabel("Consulta de Cargos", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
        labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
        campoCargo = new JTextField();
        botaoPesquisar = new JButton("Pesquisar Cargo");
        botaoEditar = new JButton("Editar Cargo");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir Cargo");
        botaoExcluir.setEnabled(false);
        listasCargosModelo = new DefaultListModel();
        listaCargos = new JList();
        listaCargos.setModel(listasCargosModelo);
        listaCargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelCargo.setBounds(100, 120, 400, 20);
        campoCargo.setBounds(100, 140, 400, 40);
        botaoPesquisar.setBounds(500, 140, 130, 40); 
        listaCargos.setBounds(100, 200, 400, 240);
        botaoEditar.setBounds(500, 360, 130, 40); 
        botaoExcluir.setBounds(500, 400, 130, 40);
        
        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(listaCargos);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        setVisible(true);
    }

    private void criarEventos() {
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarCargos(campoCargo.getText());
            }
        });
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Navegador.cargosEditar(cargoAtual);
            }
        });
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletarCargo();
            }
        });
        listaCargos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cargoAtual = listaCargos.getSelectedValue();
                if(cargoAtual == null){
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }else{
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });
    }

    private void sqlPesquisarCargos(String nome_cargo) {
        Connection conexao;
        Statement instrucaoSQL;
        ResultSet resultados;
        
        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);             
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM cargos WHERE nome_cargo like '%"+nome_cargo+"%'");
            
            listasCargosModelo.clear();

            while (resultados.next()) {                
                Cargo cargo = new Cargo();
                cargo.setId(resultados.getInt("id"));
                cargo.setNome(resultados.getString("nome_cargo"));
                
                listasCargosModelo.addElement(cargo);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar os Cargos.");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sqlDeletarCargo() {
        
        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o Cargo "+cargoAtual.getNome()+"?", "Excluir", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            Connection conexao;
            Statement instrucaoSQL;
            ResultSet resultados;
            
            try {
                conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
                instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                instrucaoSQL.executeUpdate("DELETE FROM cargos WHERE id="+cargoAtual.getId()+"");
                listaCargos.clearSelection();
                JOptionPane.showMessageDialog(null, "Cargo deletado com sucesso!");  
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o Cargo.");
                Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
        }
        
    }
}