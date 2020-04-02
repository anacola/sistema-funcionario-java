package conectividade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static void main(String[] args){
		String driver = "com.mysql.jdbc.Driver"; 
		String servidor = "jdbc:mysql://localhost:3306/sistema_de_funcionarios?useTimezone=true&serverTimezone=UTC&useSSL=false";
		String usuario = "root";
		String senha = "q1w2e3r4";
		
		Connection conexao;
		
		Statement instrucaoSQL;
		
		ResultSet resultados;
		
		try{
			conexao = DriverManager.getConnection(servidor, usuario, senha);
			
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultados = instrucaoSQL.executeQuery("SELECT * FROM funcionarios");
			System.out.println("deu certo");
		} catch(SQLException erro){
			System.out.println(erro.getMessage());
		}		
	}
}

