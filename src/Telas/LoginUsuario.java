package Telas;

import com.biblioteca1.conexaoBD.ConexaoBD1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginUsuario extends JFrame {

    private JLabel nome, senha;
    private JTextField CampoNome;
    private JPasswordField CampoPasse;
    private JButton ok, canc, admin;
    JCheckBox cbx;

    Connection conexao = null;
    PreparedStatement pstm = null;
    Statement stm = null;
    ResultSet rs = null;

    public LoginUsuario() {
        conexao = ConexaoBD1.conector();

        ImageIcon imagem = new ImageIcon(getClass().getResource("/Icons/admin.png"));
        Image img = imagem.getImage();
        Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        imagem = new ImageIcon(newImg);
        JLabel imgLabel = new JLabel(imagem);
        imgLabel.setBounds(150, 30, 100, 50); // Ajustando posição e tamanho do JLabel imgLabel
        add(imgLabel);

        getContentPane().setBackground(Color.decode("#D3D3D3"));

        nome = new JLabel("Usuário");
        nome.setBounds(40, 120, 60, 20); // Ajustando posição e tamanho do JLabel nome
        nome.setForeground(new Color(92, 32, 5));
        nome.setFont(new Font("Arial", Font.PLAIN, 14)); // Mudando o tipo de fonte e tamanho
        add(nome);

        CampoNome = new JTextField();
        CampoNome.setBounds(100, 120, 260, 30); // Ajustando posição e tamanho do JTextField cUser
        add(CampoNome);

        senha = new JLabel("Senha");
        senha.setBounds(40, 180, 50, 20); // Ajustando posição e tamanho do JLabel senha
        senha.setForeground(new Color(92, 32, 5));
        senha.setFont(new Font("Arial", Font.PLAIN, 14)); // Mudando o tipo de fonte e tamanho
        add(senha);

        CampoPasse = new JPasswordField();
        CampoPasse.setBounds(100, 180, 260, 30); // Ajustando posição e tamanho do JPasswordField cPass
        add(CampoPasse);

        ok = new JButton("Entrar");
        ok.setIcon(new ImageIcon(getClass().getResource("/Icons/accept.png")));
        ok.setBounds(150, 230, 100, 30); // Ajustando posição e tamanho do JButton ok
        ok.setBackground(Color.lightGray);
        add(ok);
        
        admin = new JButton("Admin");
        //admin.setIcon(new ImageIcon(getClass().getResource("/Icons/accept.png")));
        admin.setBounds(150, 280, 100, 30); // Ajustando posição e tamanho do JButton ok
        admin.setBackground(Color.lightGray);
        add(admin);

        canc = new JButton("");
        canc.setIcon(new ImageIcon(getClass().getResource("/Icons/cancel.png")));
        canc.setBounds(365, 0, 30, 30);
        canc.setBackground(Color.lightGray);
        add(canc);
        //canc.addActionListener(this);

        setLayout(null);
        setSize(400, 400);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logar();

            }
        });
        
        admin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent v) {
                  LoginAdmin login = new LoginAdmin();
                new LoginAdmin().setVisible(true);
                dispose();
                
            }    
        });
     
        
  canc.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent v) {
                System.exit(0); //VAI SAIR DO FORMULARIO
            }    
        });
    }

        
    public static void main(String[] args) {
        new LoginUsuario();
    }

    public void logar() {
        //a linha abaixo faz a consulta sql onde criou-se uma variavel do tipo string
        String sql = "select * from usuario where nome=? and senha =?";
        try {
            /*as linhas abaixo prepara a consulta ao banco em funcao do
            que foi digitado nas caixas de texto. o ponto de interrogacao
             e substituido pelo conteudo das variaveis*/
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, CampoNome.getText());
            String captura = new String(CampoPasse.getPassword());
            pstm.setString(2, captura);
            // a linha abaixo executa a consulta
            rs = pstm.executeQuery();

            // se existir usuario e senha correspondente
            if (rs.next()) {
                //chama a tela principal
                TelaPrincipal principal = new TelaPrincipal();
                principal.setVisible(true);
                //fecha a tela login
                this.dispose();
                
                //se usuario digitar o login e a senha invalida
            } else {
                JOptionPane.showMessageDialog(null, "Usuario ou senha invalido");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
