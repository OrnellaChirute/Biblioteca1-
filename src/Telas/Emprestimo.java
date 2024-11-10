
package Telas;

import com.biblioteca1.conexaoBD.ConexaoBD1;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprestimo extends JFrame {
    private JTextField txtTitulo, txtData;
    private JComboBox cbEstudante;
    private JButton btnEmprestar, btnLimpar, vltrButton;
    private JTable tableLivros, tableEmprestimo;
    private DefaultTableModel modelLivros, modelEmprestimo;
     
     //DESENVOLVIDO POR @ORNELLA CHIRUTE
    
   Connection conexao = null;
    PreparedStatement pstm = null;
   ResultSet rs = null,rs1=null;
   Statement stm = null,stm1 = null;
    
    public Emprestimo()  {
        
         conexao = ConexaoBD1.conector();
        setTitle("Sistema de Gestão de Biblioteca");
        setLayout(null);
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        

        JLabel lblEstudante = new JLabel("ESTUDANTE");
        lblEstudante.setBounds(20, 20, 100, 30);
        add(lblEstudante);

        cbEstudante = new JComboBox();
        cbEstudante.setBounds(150, 20, 50, 30);
        add(cbEstudante);
      
        JLabel lblTitulo = new JLabel("TITULO");
        lblTitulo.setBounds(20, 60, 100, 30);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(150, 60, 200, 30);
        txtTitulo.setEnabled(false);
        txtTitulo.setFont(new java.awt.Font("Arial Black", 0, 14));
        add(txtTitulo);

        JLabel lblData = new JLabel("DATA");
        lblData.setBounds(20, 100, 100, 30);
        add(lblData);
        
        
        txtData = new JTextField();
        txtData.setBounds(150, 100, 200, 30);
        add(txtData);
                
        //Pega a data do computador 
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataAtualFormatada = dataAtual.format(formato);
       
       
       txtData.setText(dataAtualFormatada);
       txtData.setEnabled(false);
       txtData.setFont(new java.awt.Font("Arial Black", 0, 14));
       txtData.setForeground(new java.awt.Color(0, 0, 0));

        btnEmprestar = new JButton("EMPRESTAR");
        btnEmprestar.setBounds(150, 140, 110, 30);
        add(btnEmprestar);
        
        
        btnLimpar = new JButton("LIMPAR");
        btnLimpar.setBounds(270, 140, 80, 30);
        add(btnLimpar);
        
       vltrButton = new JButton();
       vltrButton.setBounds(0, 0, 30, 30);
       vltrButton.setIcon(new ImageIcon(getClass().getResource("/Icons/voltar.png")));
       add(vltrButton);
       
        
        
        JLabel lblListaLivros = new JLabel("LISTA DE LIVROS");
        lblListaLivros.setBounds(20, 150, 200, 30);
        add(lblListaLivros);

        modelLivros = new DefaultTableModel();
        modelLivros.addColumn("ID");
        modelLivros.addColumn("Titulo");
        modelLivros.addColumn("Ano");
         modelLivros.addColumn("Ano");
        modelLivros.addColumn("Autor");
        modelLivros.addColumn("Quantidade");

        tableLivros = new JTable(modelLivros);
        JScrollPane scrollLivros = new JScrollPane(tableLivros);
        scrollLivros.setBounds(20, 220, 400, 200);
        add(scrollLivros);

        JLabel lblListaEmprestimo = new JLabel("LISTA DE EMPRESTIMO");
        lblListaEmprestimo.setBounds(450, 20, 200, 30);
        add(lblListaEmprestimo);

        modelEmprestimo = new DefaultTableModel();
        modelEmprestimo.addColumn("id_emprestimo");
        modelEmprestimo.addColumn("id_livro");
        modelEmprestimo.addColumn("titulo");
        modelEmprestimo.addColumn("data_emprestimo");
        modelEmprestimo.addColumn("id_estudante");

        tableEmprestimo = new JTable(modelEmprestimo);
        JScrollPane scrollEmprestimo = new JScrollPane(tableEmprestimo);
        scrollEmprestimo.setBounds(450, 100, 300, 270);
        add(scrollEmprestimo);

        btnEmprestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String sql = "insert into emprestimo values (?,?,?,?,?)";
        try {
            CounteEmprestimo(); 
            pstm = conexao.prepareStatement(sql);
         pstm.setInt(1, id_emprestimo);
           pstm.setInt(2, id_livro);
           pstm.setInt(3,Integer.valueOf( cbEstudante.getSelectedItem().toString()));
           pstm.setString(4,txtTitulo.getText());
           String Data = txtData.getText();
           pstm.setString(5, Data);         
            if (txtTitulo.getText().isEmpty()) {
             JOptionPane.showMessageDialog(null,"Selecione um livro");   
                
           } else if(livrodisp == 0){
            JOptionPane.showMessageDialog(null,"Livros nao disponiveis");              
        }  else if(emprestar>=3){
         JOptionPane.showMessageDialog(null,"Atingiu o limite de emprestimo ");    
        }
           
           else{
               int adicionado = pstm.executeUpdate();
               if(adicionado>0){
               JOptionPane.showMessageDialog(null,"livro emprestado"); 
               atualizarLivro();
               contaemprestimo(); 
               }else{
                   
               }
            }

            
            
        }       catch (SQLException ex) {
                 JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTitulo.setText("");
               id_livro=-1;
            }
        });
        
         vltrButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               TelaPrincipal principal = new TelaPrincipal();
                new TelaPrincipal().setVisible(true);
                dispose();
                
            }
        }); 
tableLivros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                 DefaultTableModel model = (DefaultTableModel)tableLivros.getModel();
        int Myindex=tableLivros.getSelectedRow();
        //Obtém o valor da coluna 0 (primeira coluna, geralmente o ID do livro) da linha selecionada na tabela e converte esse valor para um tipo inteiro (Integer). 
        //Este valor parece ser o ID do livro e é armazenado na variável id_livro.
        id_livro = Integer.valueOf(model.getValueAt(Myindex, 0).toString());
        txtTitulo.setText(model.getValueAt(Myindex, 1).toString());
        livrodisp=Integer.valueOf(model.getValueAt(Myindex, 4).toString());
            }
         });
      
       
       // o metodo abaixo exibe os dados do emprestimo na tabela emprestimo
       DisplayEmp();
       // o metodo abaixo exibe os dados da tabela estudante no combobox
       estudantes();
       // o metodo abaixo exibe os dados de livros na tabela livros
       DisplayLivros();
    }

    
 private void estudantes(){
         try {
              stm1 = conexao.createStatement();
         rs1 = stm1.executeQuery("select * from estudante");
         while(rs1.next()){
             String id = rs1.getString("id_estudante");
             cbEstudante.addItem(id);
             
         }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "");
         }
    
     }
 
    int id_emprestimo;
    private void CounteEmprestimo() 
{
    try {
        stm1 = conexao.createStatement();
        rs1 = stm1.executeQuery("select Max(id_emprestimo) from emprestimo");
        rs1.next();
        id_emprestimo = rs1.getInt(1)+1;
    } catch (Exception e) {
     JOptionPane.showMessageDialog(null, e);   
    }
    }   
    
   private void DisplayLivros(){
     try{
         stm = conexao.createStatement();
         rs = stm.executeQuery("select id_livro as ID, titulo as Titulo, ano_publicacao as Ano, autor as Autor, quantidade as Quantidade from livro");
         tableLivros.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
         }
      catch(Exception e )
     {
        JOptionPane.showMessageDialog(null,e);
        
     } 
         }
   
    int id_livro =-1 ;
int livrodisp; 
     private void DisplayEmp(){
     try{
         stm1 = conexao.createStatement();
         rs1 = stm1.executeQuery("select id_emprestimo,id_livro,id_estudante, titulo,data_emprestimo from emprestimo");
         tableEmprestimo.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs1));
         }
      catch(Exception e )
     {
        
         JOptionPane.showMessageDialog(null, e);
     } 
         }
   
     int emprestar;
    private void contaemprestimo(){
       try {
           stm1=conexao.createStatement();
           rs1=stm1.executeQuery("select count(*) from emprestimo where id_estudante="+cbEstudante.getSelectedItem());
           rs1.next();
           emprestar=rs1.getInt(1); 
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this, ex);
       }

}
    //Metodo que faz com que a quantidade de livros diminua 
     private void atualizarLivro(){
               int novo = livrodisp-1;
               String sql = "update livro set quantidade=? where id_livro=?";
        try{
            pstm = conexao.prepareStatement(sql);   
               pstm.setInt(1,novo);
               pstm.setInt(2,id_livro);
           
            
                if (pstm.executeUpdate() > 0){
                    //JOptionPane.showMessageDialog(null, "Dados dos Usuarios foram actualizados com sucesso");
                   
                   DisplayLivros();
                 DisplayEmp();
                }else{
                 //  JOptionPane.showMessageDialog(null, "Livro nao disponivel");  
                }
                //pstm.executeUpdate();

        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
    }
       
           }  
      
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
             new Emprestimo().setVisible(true);
               
            }
        });
    }

   
}
