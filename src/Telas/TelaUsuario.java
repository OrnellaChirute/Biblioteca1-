
package Telas;


import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.biblioteca1.conexaoBD.ConexaoBD1;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.proteanit.sql.DbUtils;


public class TelaUsuario extends JFrame{
   JFrame frame = new JFrame();
  
   JLabel titleLabel, nameLabel, senhaLabel, emailLabel, idLabel;
   JTextField titleField, nameField, senhaField, emailField, idField;
  
   //JTable useTable;
   JButton addButton, searchButton, refreshButton, deleteButton, backButton,showButton;
   JTable usertable;
   
   Connection conexao = null;
    PreparedStatement pstm = null;
    Statement stm= null;
    ResultSet rs = null;
  
public TelaUsuario(){
     
        conexao = ConexaoBD1.conector();
        
        JFrame frame = new JFrame("Sistema de Gestão de Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
getContentPane().setBackground(Color.decode("#D3D3D3")); 

        titleLabel = new JLabel("REGISTRAR USUARIOS");
        titleLabel.setBounds(200, 10, 200, 30);
        frame.add(titleLabel);

        
        nameLabel = new JLabel("NOME");
        nameLabel.setBounds(120, 60, 80, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(170, 60, 150, 25);
        frame.add(nameField);

        senhaLabel = new JLabel("SENHA");
        senhaLabel.setBounds(120, 150, 80, 25);
        frame.add(senhaLabel);

        senhaField = new JTextField();
        senhaField.setBounds(170, 150, 150, 25);
        frame.add(senhaField);

        emailLabel = new JLabel("EMAIL");
        emailLabel.setBounds(110, 100, 80, 25);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(170, 100, 150, 25);
        frame.add(emailField);
        
        idLabel = new JLabel("ID");
        idLabel.setBounds(30,50, 50, 50);
 
        frame.add(idLabel);
        
       idField = new JTextField();
        idField.setBounds(60, 60, 30, 25);
        idField.setEnabled(false);
        frame.add(idField);
        


        addButton = new JButton(new ImageIcon());
        addButton.setIcon(new ImageIcon(getClass().getResource("/Icons/ADICIONAR.png")));
        addButton.setBounds(350, 70, 30, 30);
        frame.add(addButton);

        searchButton = new JButton(new ImageIcon("search_icon.png"));
        searchButton.setIcon(new ImageIcon(getClass().getResource("/Icons/PESQUISAR.png")));
        searchButton.setBounds(400, 70, 30, 30);
        frame.add(searchButton);

        refreshButton = new JButton(new ImageIcon());
        refreshButton.setIcon(new ImageIcon(getClass().getResource("/Icons/AC.png")));
        refreshButton.setBounds(350, 130, 30, 30);
        frame.add(refreshButton);

        deleteButton = new JButton();
        deleteButton.setBounds(400, 130, 30, 30);
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/Icons/remover.png")));
        frame.add(deleteButton);
        
        backButton = new JButton();
        backButton.setBounds(0, 0, 30, 30);
        backButton.setIcon(new ImageIcon(getClass().getResource("/Icons/voltar.png")));
        frame.add(backButton);
        
        
        showButton = new JButton("mostrar");
        showButton.setBounds(350, 210, 90, 20);
        //showButton.setIcon(new ImageIcon(getClass().getResource()));
        frame.add(showButton);
        
        

        String[] columnNames = {"id_usuario", "Nome", "senha", "Email"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        usertable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(usertable);
        scrollPane.setBounds(10, 250, 570, 200);
        frame.add(scrollPane);

      
        frame.setVisible(true);
        
        
        
        
       addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent cadastro) {  
                String sql = "Insert usuario (nome,senha, email) values(?,?,?)";
                try{
                    
                   PreparedStatement pstm = conexao.prepareStatement(sql);
                    pstm.setString(1, nameField.getText()); //O NUMERO QUE APARECE DEFINE A POSICAO 
                    pstm.setString(2, senhaField.getText());
                    pstm.setString(3, emailField.getText());
                            
                if( 
                    nameField.getText().isEmpty()|| 
                    senhaField.getText().isEmpty()||
                    emailField.getText().isEmpty()
                   
                ){
                    JOptionPane.showMessageDialog(null,"Prencha todos Campos","ERRO", JOptionPane.ERROR_MESSAGE);
                }
                else{   
               
                   
                    int inserido = pstm.executeUpdate();
                    if (inserido > 0){
                        JOptionPane.showMessageDialog(null,"Cadastrado com sucesso");
                            nameField.setText(null);
                            senhaField.setText(null);
                            emailField.setText(null);
                           DisplayUsuarios();
                    }
                }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e);
                }   
              }         
          
                
            });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  int confirma = JOptionPane.showConfirmDialog(null, "deseja remover esse usuario?", "Atencao", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from usuario where id_usario=?";
            try {
                pstm = conexao.prepareStatement(sql);
                pstm.setString(1, idField.getText());
                int apagado = pstm.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Removido com sucesso");
                 limpar();
                 DisplayUsuarios();
                 addButton.setEnabled(true);
                }
            }catch(Exception a) {
            JOptionPane.showMessageDialog(null,a);
            }
            
        } else {
        }
    }
      
            
        });
       backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               LoginAdmin admin = new  LoginAdmin();
                admin.setVisible(true);
                dispose();
                
            }
        });
      
       
       searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "select * from usuario where nome like ?";
               try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, nameField.getText() + "%");
            rs = pstm.executeQuery();
             usertable.setModel(DbUtils.resultSetToTableModel(rs));
           
        }       catch (SQLException ex) { 
                    JOptionPane.showMessageDialog(null, e);
                } 
                
            }
        });
       
       usertable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usertableMouseClicked(evt);
            }
         });
       refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "update usuario set nome=?, senha=?, email=? where id_usario=?";
               try {
           pstm = conexao.prepareStatement(sql);
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1,nameField.getText());
            pstm.setString(2,senhaField.getText());
            pstm.setString(3,emailField.getText());
            pstm.setString(4, idField.getText());
            
                   if (nameField.getText().isEmpty() || senhaField.getText().isEmpty() || emailField.getText().isEmpty()) {
                       JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatorios");
                   } else {
                        int adicionado = pstm.executeUpdate();
                        if (adicionado>0) {
                            JOptionPane.showMessageDialog(null, "Dados dos Usuarios foram actualizados com sucesso");
                            limpar();
                   DisplayUsuarios();
                    addButton.setEnabled(true);
                           
                       } else {
                       }
                       
                   }
           
        }       catch (SQLException ex) { 
                    JOptionPane.showMessageDialog(null, ex);
                } 
                
            }
        });

       showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DisplayUsuarios();
                } catch (SQLException ex) {
                    Logger.getLogger(TelaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
       
}
private void usertableMouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
       try{
          addButton.setEnabled(false);
        /*Obtém o modelo da tabela a partir de uma tabela chamada TabelaLivros.
        Está fazendo um tipo de conversão, chamada "casting", para DefaultTableModel*/
       DefaultTableModel model = (DefaultTableModel)usertable.getModel();
       /*Obtém o índice da linha selecionada na tabela TabelaLivros*/
       int Myindex=usertable.getSelectedRow();
       /*Obtém o valor na primeira coluna (índice 0) da linha selecionada e converte para um número inteiro.
       Este valor parece ser armazenado na variável chave*/
        idField.setText(model.getValueAt(Myindex, 0).toString());
       /*Define o texto de um campo chamado CampoTitulo com o valor da segunda coluna (índice 1) da 
       linha selecionada na tabela.*/
        nameField.setText(model.getValueAt(Myindex, 1).toString());
       /*Define o texto de um campo chamado CampoAno com o valor da terceira coluna (índice 2) 
       da linha selecionada na tabela.*/
       senhaField.setText(model.getValueAt(Myindex, 2).toString());
       emailField.setText(model.getValueAt(Myindex, 3).toString()); 
       }catch(Exception e){
         JOptionPane.showMessageDialog(null, e);  
       }
    }

       
       private void limpar(){
          idField.setText(null);
          nameField.setText(null);
          senhaField.setText(null);
          emailField.setText(null);
      }
       
       

       private void DisplayUsuarios() throws SQLException{
     try{
         stm = conexao.createStatement();
         rs = stm.executeQuery("select * from usuario");
         usertable.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
         }
     catch(Exception e)
     {
        JOptionPane.showMessageDialog(null,e);
     
 } 
    }  
    

       
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
              TelaUsuario window = new TelaUsuario();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
      
    }
       
          }
