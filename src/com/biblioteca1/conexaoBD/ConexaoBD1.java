
package com.biblioteca1.conexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBD1 {
     // metodo responsavel para estabelecer conexao de dados  
    public static Connection conector() {
        java.sql.Connection conexao = null;
        //linha abaixo chama o driver
        String driver = "com.mysql.jdbc.Driver";
        //armazenar informacoes referente ao banco de dados
        String url = "jdbc:mysql://localhost:3306/biblioteca1";
        String user = "root";
        String password = "Maria";
        // estabelecendo a conexao de base de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null,e);
            return null;

        }
    }
    
}


