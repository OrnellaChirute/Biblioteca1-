
package Telas;

import com.biblioteca1.conexaoBD.ConexaoBD1;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.proteanit.sql.DbUtils;

public class Livros extends JFrame {
 
    
    private JTextField  Idtxt, txtTitulo, txtData, txtISBN, txtAutor, txtQuant; 
    private JTable tableLivros, tableEmprestimo;
    private DefaultTableModel modelLivros, modelEmprestimo;
    private JButton vltrButton, btnActualizar, btnPesquisar, btncadastrar, btnEliminar,  btnMostrar;
    private JLabel IdLabel, lblTiL, lblTitulo, lblData, lblISBN, lblAutor, lblListaLivros, lblQuant;  

    
    Connection conexao = null;
    PreparedStatement pstm = null;
   ResultSet rs = null,rs1=null;
   Statement stm = null,stm1 = null;
    
    public Livros() {
        
         
        conexao = ConexaoBD1.conector();
        setTitle("Sistema de Gestão de Biblioteca");
        setLayout(null);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        
        
         lblTiL = new JLabel("CADASTRO DE LIVROS ");
        lblTiL.setBounds(220, 10, 150, 20);
        add(lblTiL);
        
        IdLabel = new JLabel("ID");
        IdLabel.setBounds(30,50, 30, 20);
        add(IdLabel);
        
       Idtxt= new JTextField();
        Idtxt.setBounds(80, 50, 30, 20);
        Idtxt.setEnabled(false);
        add(Idtxt);
        
        lblTitulo = new JLabel("TITULO");
        lblTitulo.setBounds(30, 90, 80, 20);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(80, 90, 150, 20);
        add(txtTitulo);

        lblData = new JLabel("ANO DE PUBLICACAO");
        lblData.setBounds(280, 50, 130, 20);
        add(lblData);

        txtData = new JTextField();
        txtData.setBounds(420, 50, 150, 20); 
        add(txtData);
        
        lblAutor= new JLabel("AUTOR");
        lblAutor.setBounds(30, 130, 50, 20);
        add(lblAutor);
        
       txtAutor = new JTextField();
       txtAutor.setBounds(80, 130, 150, 20);
       add(txtAutor);
       
       lblISBN = new JLabel("ISBN");
       lblISBN.setBounds(360, 90, 80, 20);
       add( lblISBN);
       
       txtISBN = new JTextField();
       txtISBN.setBounds(420, 90, 150, 20);
       add(txtISBN);
       
       lblQuant= new JLabel("QUANTIDADE");
       lblQuant.setBounds(330, 130, 80, 20);
       add( lblQuant);
       
       txtQuant = new JTextField();
       txtQuant.setBounds(420, 130, 150, 20);
       add(txtQuant);
       
        
       
        btncadastrar = new JButton("Adicionar");
        btncadastrar.setBounds(80, 190, 90, 25);
        add( btncadastrar);

        btnEliminar = new JButton("Apagar");
        btnEliminar.setBounds(190, 190, 80, 25);
        add(btnEliminar);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(290, 190, 100, 25);
        add(btnActualizar);
        
        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(400, 190, 100, 25);
        add(btnPesquisar);
        
        
        btnMostrar = new JButton();
        btnMostrar.setBounds(5, 220, 30, 20);
        btnMostrar.setIcon(new ImageIcon(getClass().getResource("/Icons/eye.png")));
        add(btnMostrar);
        
         vltrButton = new JButton();
        vltrButton.setBounds(0, 0, 30, 30);
        vltrButton.setIcon(new ImageIcon(getClass().getResource("/Icons/voltar.png")));
        add(vltrButton);
        
        
    
        modelLivros = new DefaultTableModel();
        modelLivros.addColumn("ID");
        modelLivros.addColumn("Titulo");
        modelLivros.addColumn("Ano");
         modelLivros.addColumn("ISBN");
        modelLivros.addColumn("Autor");
        modelLivros.addColumn("Quantidade");

        tableLivros = new JTable(modelLivros);
        JScrollPane scrollLivros = new JScrollPane(tableLivros);
        scrollLivros.setBounds(5, 240, 570, 200);
        add(scrollLivros);
        
        //Evento para preencher campos
 tableLivros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableLivrosMouseClicked(evt);
            }
         });

      

        // Mock data
        //adicionarDadosLivros();
         vltrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               TelaPrincipal principal = new TelaPrincipal();
                new TelaPrincipal().setVisible(true);
                dispose();
                
            }
        });
         
          btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               String sql = "update livro set  titulo=?, ano_publicacao=?,  ISBN=?, autor=?, quantidade=? where id_livro=? ";
        try {
            pstm = conexao.prepareStatement(sql);        
            pstm.setString(1, txtTitulo.getText());
            pstm.setString(2, txtData.getText());
            pstm.setString(3, txtISBN.getText());
             pstm.setString(4, txtAutor.getText());
             pstm.setString(5, txtQuant.getText());
             pstm.setString(6, Idtxt.getText());
            if (txtTitulo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatorios");
                
            } else {

                // a linha abaixo atualiza a tabela funcionario com os dados do formulario
                // a estrutura abaixo e usada pra a confirmacao da insercao da tabela
                int adicionado = pstm.executeUpdate();
                
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Foi alterado com sucesso");
                    // a linha abaixo limpa os campos
                   Limpar();
                   DisplayLivros();
                  btncadastrar.setEnabled(true); 
                 
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);            
        }
    }
            
        });
         int chave=0;
          btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  int confirma = JOptionPane.showConfirmDialog(null, "deseja remover esse livro?", "Atencao", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from livro where id_livro=?";
            try {
                pstm = conexao.prepareStatement(sql);
                pstm.setString(1, Idtxt.getText());
                int apagado = pstm.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Removido com sucesso");
                 Limpar();
                 DisplayLivros();
                 btncadastrar.setEnabled(true);
                }
            }catch(Exception a) {
            JOptionPane.showMessageDialog(null,"IMPOSSIVEL APAGAR PORQUE O LIVRO FOI EMPRESTADO");
            }
            
        } else {
        }
    }
      
            
        });
          
       
        btnPesquisar.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e){
                String sql = "select * from livro where titulo like ?";
        try {
            pstm = conexao.prepareStatement(sql);
            // passando o conteudo da caixa de pesquisa no ponto de interrogacao
            //atencao ao porcentagem que e a continuacao da string sql
            pstm.setString(1, txtTitulo.getText() + "%");
            rs = pstm.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tableLivros.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception s) {
          JOptionPane.showMessageDialog(null,s);    
        }
            }
        });  
         
         
         btncadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                 if (txtTitulo.getText().isEmpty() || txtData.getText().isEmpty() || txtISBN.getText().isEmpty() || txtQuant.getText().isEmpty()) {
             JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatorios");   
             }
                 else{
                 try {
                  CountLivros();
                 String sql = "insert livro  values (?,?,?,?,?,?)";
                 pstm = conexao.prepareStatement(sql);
                 pstm.setInt(1,id_livro);
                 pstm.setString(2, txtTitulo.getText());
                  pstm.setString(3, txtData.getText());
                  pstm.setString(4, txtISBN.getText());
                   pstm.setString(5, txtAutor.getText());
                    pstm.setString(6, txtQuant.getText());
                  int row = pstm.executeUpdate();
                  if( row > 0){
                  JOptionPane.showMessageDialog(null, "Livro Adicionado com sucesso");
                   Limpar();
                  DisplayLivros();
                  
                  //conexao.close();
                  
                  }else{
                      
                  }
                 
                 }catch( Exception s){
                       JOptionPane.showMessageDialog(null,"Erro");   
                          }
                     
                 
                         
                 }}              
               
            
        });
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayLivros();
            }
        });
      
                
         
    }
   
     
    private void Limpar(){
          Idtxt.setText(null);
         txtTitulo.setText(null);
          txtData.setText(null);
          txtAutor.setText(null);
           txtISBN.setText(null);
           txtQuant.setText(null);
      }
       
private void DisplayLivros(){
     try{
         stm1 = conexao.createStatement();
         rs1 = stm1.executeQuery("select * from livro");
         tableLivros.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs1));
         }
      catch(Exception e)
     {
        JOptionPane.showMessageDialog(null,"Erro1");
        
     } 
         }

//para preencher os campos 
  private void tableLivrosMouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
       try{
          btncadastrar.setEnabled(false);
        /*Obtém o modelo da tabela a partir de uma tabela chamada TabelaLivros.
        Está fazendo um tipo de conversão, chamada "casting", para DefaultTableModel*/
       DefaultTableModel model = (DefaultTableModel)tableLivros.getModel();
       /*Obtém o índice da linha selecionada na tabela TabelaLivros*/
       int Myindex=tableLivros.getSelectedRow();
       /*Obtém o valor na primeira coluna (índice 0) da linha selecionada e converte para um número inteiro.
       Este valor parece ser armazenado na variável chave*/
        Idtxt.setText(model.getValueAt(Myindex, 0).toString());
       /*Define o texto de um campo chamado CampoTitulo com o valor da segunda coluna (índice 1) da 
       linha selecionada na tabela.*/
       txtTitulo.setText(model.getValueAt(Myindex, 1).toString());
       /*Define o texto de um campo chamado CampoAno com o valor da terceira coluna (índice 2) 
       da linha selecionada na tabela.*/
       txtData.setText(model.getValueAt(Myindex, 2).toString());
      txtISBN.setText(model.getValueAt(Myindex, 3).toString());
       txtAutor.setText(model.getValueAt(Myindex, 4).toString());
       txtQuant.setText(model.getValueAt(Myindex,5).toString());  
       }catch(Exception e){
         JOptionPane.showMessageDialog(null, e);  
       }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Livros().setVisible(true);
               
            }
        });
    }
       int id_livro;
private void CountLivros()
{
    try {
        stm1 = conexao.createStatement();
        rs1 = stm1.executeQuery("select Max(Id_livros) from livros");
        rs1.next();
        id_livro = rs1.getInt(1)+1;
    } catch (Exception e) {
    }
    }

}
