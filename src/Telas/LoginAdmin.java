
package Telas;

import com.biblioteca1.conexaoBD.ConexaoBD1;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

 
public class LoginAdmin extends JFrame implements ActionListener {
    
    JFrame jr = new JFrame("Login");
    JLabel nome, senha, titulo ;
    JTextField cUser;
    JPasswordField   cPass;
    JButton ok, voltaAdmin;
    JCheckBox cbx;
    
    Connection conexao = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    public LoginAdmin(){
        
        conexao = ConexaoBD1.conector();
         getContentPane().setBackground(Color.decode("#D3D3D3"));
        titulo = new JLabel("Tela Admin");
        titulo.setBounds(100, 20, 100, 50);
        titulo.setForeground(new Color(92,32,5));
        add(titulo);
        
        
        nome = new JLabel("Usuario");
        nome.setBounds(35, 60, 50, 50);
        nome.setForeground(new Color(92,32,5));
        add(nome);
        
        cUser = new JTextField();
        cUser.setBounds(35, 100, 220, 20);
        
       add(cUser);
        
        senha = new JLabel("Senha");
        senha.setBounds(35, 115, 50, 50);
        senha.setForeground(new Color(92,32,5));
        add(senha);
        
        cPass = new JPasswordField();
        cPass.setBounds(35, 160, 220, 20);
        add(cPass);
        ok = new JButton("Entrar");
        ok.setIcon(new ImageIcon(getClass().getResource("/Icons/accept.png")));
        ok.setBounds(100, 210, 100, 20);
        ok.setBackground(Color.lightGray);
        add(ok);
        
        
        
       voltaAdmin= new JButton("");
       voltaAdmin.setBounds(0, 0, 30, 30);
       voltaAdmin.setIcon(new ImageIcon(getClass().getResource("/Icons/voltar.png")));
        voltaAdmin.setBackground(Color.lightGray);
        add(voltaAdmin);
       
        
       
        setLayout(null);
        setSize(300,330);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(220, 220, 0));
        //IMPLEMENTANDO A CLAASE DE CONEXAO CRIADA EM BAIXO
        
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
            }
        });
        
        
        
        
voltaAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               LoginUsuario User = new LoginUsuario();
                new LoginUsuario().setVisible(true);
                dispose();
                
            }
        });

           ok.addActionListener(new ActionListener(){
               
            public void actionPerformed(ActionEvent cadastro) {  
                 String sql = "select * from admin where nome =? and senha =?";    
                try {
            /*as linhas abaixo prepara a consulta ao banco em funcao do
            que foi digitado nas caixas de texto. o ponto de interrogacao
             e substituido pelo conteudo das variaveis*/
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1,  cUser.getText());
            String captura = new String(cPass.getPassword());
            pstm.setString(2, captura);
            // a linha abaixo executa a consulta
            rs = pstm.executeQuery();
            // se existir usuario e senha correspondente
            if (rs.next()) {
                TelaUsuario usuario = new TelaUsuario();
                usuario.setVisible(true);
                dispose();
                //conexao.close();
                
            } else {
                JOptionPane.showMessageDialog(null, "Usuario ou senha invalido");
                cUser.setText(null);
                cPass.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
                
            });
 
            }  
  
 public static void main(String[] args) {
        new LoginAdmin(); 
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
