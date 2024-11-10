
package Telas;

import com.biblioteca1.conexaoBD.ConexaoBD1;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;



public class Estudantes extends JFrame{
   JFrame frame = new JFrame();
   
   private JLabel titleLabel, idLabel, nameLabel, courseLabel, numberLabel, levelLabel;
   private JTextField  idField,nameField,courseField ,numberField, levelField;
   private JButton voltarButton, addButton, searchButton,refreshButton,deleteButton, mostrar;
   private JComboBox<String> levelComboBox;
   private DefaultTableModel model;
   private JTable table ;
   private JScrollPane scrollPane ;
   
   
     Connection conexao = null;
    PreparedStatement pstm = null;
   ResultSet rs = null,rs1=null;
   Statement stm = null,stm1 = null;
    
   
public Estudantes(){
      conexao = ConexaoBD1.conector();
    
    
         getContentPane().setBackground(Color.decode("#D3D3D3"));
        frame = new JFrame("Sistema de Gestão de Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        titleLabel = new JLabel("REGISTRAR ESTUDANTE");
        titleLabel.setBounds(200, 10, 200, 30);
        frame.add(titleLabel);
        
        idLabel = new JLabel("ID");
        idLabel.setBounds(50, 60, 80, 25);
        frame.add(idLabel);
        
         idField = new JTextField();
        idField.setBounds(70, 60, 30, 25);
        idField.setEditable(false);
        frame.add(idField);

         nameLabel = new JLabel("NOME");
        nameLabel.setBounds(120, 60, 80, 25);
        frame.add(nameLabel);

         nameField = new JTextField();
        nameField.setBounds(170, 60, 150, 25);
        frame.add(nameField);

        courseLabel = new JLabel("CURSO");
        courseLabel.setBounds(350, 60, 80, 25);
        frame.add(courseLabel);

        courseField = new JTextField();
        courseField.setBounds(400, 60, 150, 25);
        frame.add(courseField);

         numberLabel = new JLabel("NUMERO");
        numberLabel.setBounds(110, 100, 80, 25);
        frame.add(numberLabel);

        numberField = new JTextField();
        numberField.setBounds(170, 100, 150, 25);
        frame.add(numberField);

        levelLabel = new JLabel("NIVEL");
        levelLabel.setBounds(350, 100, 80, 25);
        frame.add(levelLabel);

        String[] levels = {"1º Ano", "2º Ano", "3º Ano", "4º Ano"};
        levelComboBox = new JComboBox<>(levels);
        levelComboBox.setBounds(400, 100, 150, 25);
        frame.add(levelComboBox);

        addButton = new JButton(new ImageIcon());
        addButton.setIcon(new ImageIcon(getClass().getResource("/Icons/ADICIONAR.png")));
        addButton.setBounds(170, 150, 50, 50);
        frame.add(addButton);

        searchButton = new JButton(new ImageIcon("search_icon.png"));
        searchButton.setIcon(new ImageIcon(getClass().getResource("/Icons/PESQUISAR.png")));
        searchButton.setBounds(270, 150, 50, 50);
        frame.add(searchButton);

        refreshButton = new JButton(new ImageIcon());
        refreshButton.setIcon(new ImageIcon(getClass().getResource("/Icons/AC.png")));
        refreshButton.setBounds(400, 150, 50, 50);
        frame.add(refreshButton);

         deleteButton = new JButton(new ImageIcon());
        deleteButton.setBounds(500, 150, 50, 50);
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/Icons/remover.png")));
        frame.add(deleteButton);
        
         voltarButton = new JButton();
        voltarButton.setBounds(0, 0, 30, 30);
        voltarButton.setIcon(new ImageIcon(getClass().getResource("/Icons/voltar.png")));
        frame.add(voltarButton);
        
        mostrar = new JButton();
        mostrar.setBounds(20, 215, 30, 30);
        mostrar.setIcon(new ImageIcon(getClass().getResource("/Icons/eye.png")));
        mostrar.setToolTipText("MOSTRAR");
        frame.add(mostrar);

        String[] columnNames = {"id_estudante", "nome", "curso", "numero", "nivel"};
         model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 250, 570, 200);
        frame.add(scrollPane);

        /** Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = model.getRowCount() + 1;
                String name = nameField.getText();
                String course = courseField.getText();
                String number = numberField.getText();
                String level = (String) levelComboBox.getSelectedItem();

                model.addRow(new Object[]{id, name, course, number, level});
                idField.setText(String.valueOf(id + 1));
            }
        });*/
        
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               TelaPrincipal principal = new TelaPrincipal();
                new TelaPrincipal().setVisible(true);
                Estudantes.this.dispose();
                
            }
        });
 addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent cadastro) {  
                String sql = "Insert estudante (nome, curso, numero,nivel) values(?,?,?,?)";
                try{
                    
                   PreparedStatement pstm = conexao.prepareStatement(sql);
                    pstm.setString(1, nameField.getText()); //O NUMERO QUE APARECE DEFINE A POSICAO 
                    pstm.setString(2, courseField.getText());
                    pstm.setString(3, numberField .getText());
                    pstm.setString(4, levelComboBox.getSelectedItem().toString());
                            
                if( 
                    nameField.getText().isEmpty()|| 
                    courseField.getText().isEmpty()||
                    numberField.getText().isEmpty()
                   
                ){
                    JOptionPane.showMessageDialog(null,"Prencha todos Campos","ERRO", JOptionPane.ERROR_MESSAGE);
                }
                else{   
               
                   
                    int inserido = pstm.executeUpdate();
                    if (inserido > 0){
                        JOptionPane.showMessageDialog(null,"Cadastrado com sucesso");
                            nameField.setText(null);
                            courseField.setText(null);
                             numberField.setText(null);
                             levelComboBox.setSelectedItem(null);
                           //DisplayUsuarios();
                    }
                }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e);
                }   
              }         
          
                
            });
        frame.setVisible(true);
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  int confirma = JOptionPane.showConfirmDialog(null, "deseja remover esse usuario?", "Atencao", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from estudante where id_estudante=?";
            try {
                pstm = conexao.prepareStatement(sql);
                pstm.setString(1, idField.getText());
                int apagado = pstm.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Removido com sucesso");
                 limpar();
                 DisplayEstudantes();
                 addButton.setEnabled(true);
                }
            }catch(Exception a) {
            JOptionPane.showMessageDialog(null,a);
            }
            
        } else {
        }
    }
      
            
        });
        
        searchButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e){
                String sql = "select * from estudante where nome like ?";
        try {
            pstm = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa no ponto de interrogacao
            //atencao ao porcentagem que e a continuacao da string sql
            pstm.setString(1, nameField.getText() + "%");
            rs = pstm.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception s) {
          JOptionPane.showMessageDialog(null,s);    
        }
            }
        }); 
        
        
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               String sql = "update estudante set nome=?, curso=?, numero=?, nivel=? where id_estudante=? ";
        try {
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, nameField.getText());
            pstm.setString(2, courseField.getText());
            pstm.setString(3, numberField.getText());
            pstm.setString(4, levelComboBox.getSelectedItem().toString());
             pstm.setString(5, idField.getText());
            if (nameField.getText().isEmpty() || courseField.getText().isEmpty() || numberField.getText().isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatorios");
                
            } else {

                // a linha abaixo atualiza a tabela funcionario com os dados do formulario
                // a estrutura abaixo e usada pra a confirmacao da insercao da tabela
                int adicionado = pstm.executeUpdate();
                
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Foi alterado com sucesso");
                    // a linha abaixo limpa os campos
                   limpar();
                   DisplayEstudantes();
                  addButton.setEnabled(true); 
                 
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);            
        }
    }
            
        });
        mostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DisplayEstudantes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                
            }
        });
        
        //Evento para setar campos 
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                 try{
          addButton.setEnabled(false);
        /*Obtém o modelo da tabela a partir de uma tabela chamada TabelaLivros.
        Está fazendo um tipo de conversão, chamada "casting", para DefaultTableModel*/
       DefaultTableModel model = (DefaultTableModel)table.getModel();
       /*Obtém o índice da linha selecionada na tabela TabelaLivros*/
       int Myindex=table.getSelectedRow();
       /*Obtém o valor na primeira coluna (índice 0) da linha selecionada e converte para um número inteiro.
       Este valor parece ser armazenado na variável chave*/
        idField.setText(model.getValueAt(Myindex, 0).toString());
       /*Define o texto de um campo chamado CampoTitulo com o valor da segunda coluna (índice 1) da 
       linha selecionada na tabela.*/
        nameField.setText(model.getValueAt(Myindex, 1).toString());
       /*Define o texto de um campo chamado CampoAno com o valor da terceira coluna (índice 2) 
       da linha selecionada na tabela.*/
       courseField.setText(model.getValueAt(Myindex, 2).toString());
       numberField.setText(model.getValueAt(Myindex, 3).toString()); 
      levelComboBox.setSelectedItem(model.getValueAt(Myindex, 4).toString()); 
       }catch(Exception e){
         JOptionPane.showMessageDialog(null, e);  
       }
            }
         });
    }

       
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
               new Estudantes().setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
      
       
       
    }
      private void limpar(){
          idField.setText(null);
          nameField.setText(null);
          courseField.setText(null);
          numberField.setText(null);
          levelComboBox.setSelectedItem(null);
      }
      
        private void DisplayEstudantes() throws SQLException{
     try{
         stm = conexao.createStatement();
         rs = stm.executeQuery("select * from estudante");
         table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
         }
     catch(Exception e)
     {
        JOptionPane.showMessageDialog(null,e);
     
 } 
    }
}
