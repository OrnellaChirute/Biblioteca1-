package Telas;


import com.biblioteca1.conexaoBD.ConexaoBD1;
import java.awt.Color;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;



public class Devolucao extends JFrame {
    private JTextField txtTitulo, txtEstudante, txtDataEmprestimo;
    private JButton btnDevolucao, vltarButton;
    private JTable tableEmprestados, tableDevolvidos;
    private DefaultTableModel modelEmprestados, modelDevolvidos;
     private JTextField DataDevolucao;
    
  Connection conexao = null;
   PreparedStatement pstm = null;
   ResultSet rs = null,rs1=null;
   Statement stm = null,stm1 = null;
   
   
    public Devolucao() throws ParseException {
       
     conexao = ConexaoBD1.conector();
        setTitle("Sistema de Gestão de Biblioteca");
        setLayout(null);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(new Color(220, 220, 0));
       
        JLabel lblTitulo = new JLabel("TITULO");
        lblTitulo.setBounds(20, 60, 100, 30);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(150, 60, 200, 30);
        txtTitulo.setEnabled(false);
        txtTitulo.setFont(new java.awt.Font("Arial Black", 0, 14));
        txtTitulo.setForeground(new java.awt.Color(0, 0, 0));
        add(txtTitulo);

        JLabel lblDataDevolucao = new JLabel("DATA DE DEVOLUÇÃO");
        lblDataDevolucao.setBounds(370, 60, 150, 30);
        add(lblDataDevolucao);
        
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            DataDevolucao = new JFormattedTextField(dateMask);
            DataDevolucao.setBounds(520, 60, 200, 30);
            add(DataDevolucao);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        JLabel lblEstudante = new JLabel("ESTUDANTE");
        lblEstudante.setBounds(20, 100, 100, 30);
        add(lblEstudante);

        txtEstudante = new JTextField();
        txtEstudante.setBounds(150, 100, 200, 30);
        txtEstudante.setEnabled(false);
         txtEstudante.setForeground(new java.awt.Color(0, 0, 0));
        add(txtEstudante);

        JLabel lblDataEmprestimo = new JLabel("DATA DE EMPRESTIMO");
        lblDataEmprestimo.setBounds(370, 100, 150, 30);
        add(lblDataEmprestimo);

        txtDataEmprestimo = new JTextField();
        txtDataEmprestimo.setBounds(520, 100, 200, 30);
        txtDataEmprestimo.setEnabled(false);
        txtDataEmprestimo.setFont(new java.awt.Font("Arial Black", 0, 14));
        txtDataEmprestimo.setForeground(new java.awt.Color(0, 0, 0));
        add(txtDataEmprestimo);

        btnDevolucao = new JButton("DEVOLUCAO");
        btnDevolucao.setBounds(150, 180, 200, 30);
        add(btnDevolucao);
        
        
        vltarButton = new JButton();
        vltarButton.setBounds(0, 0, 30, 30);
       vltarButton.setIcon(new ImageIcon(getClass().getResource("/Icons/voltar.png")));
        add(vltarButton);

        JLabel lblLivrosEmprestados = new JLabel("LIVROS EMPRESTADOS");
        lblLivrosEmprestados.setBounds(20, 220, 200, 30);
        add(lblLivrosEmprestados);

        modelEmprestados = new DefaultTableModel();
        modelEmprestados.addColumn("id_emprestimo");
        modelEmprestados.addColumn("id_livro");
        modelEmprestados.addColumn("titulo");
        modelEmprestados.addColumn("data_emprestimo");
        modelEmprestados.addColumn("id_estudante");

        tableEmprestados = new JTable(modelEmprestados);
        JScrollPane scrollEmprestados = new JScrollPane(tableEmprestados);
        scrollEmprestados.setBounds(5, 250, 450, 300);
        add(scrollEmprestados);

        JLabel lblListaDevolvidos = new JLabel("LISTA DEVOLVIDOS");
        lblListaDevolvidos.setBounds(460, 220, 200, 30);
        add(lblListaDevolvidos);

        modelDevolvidos = new DefaultTableModel();
        modelDevolvidos.addColumn("Id");
        modelDevolvidos.addColumn("ID_estudante");
        modelDevolvidos.addColumn("Livros_devolvidos");
        modelDevolvidos.addColumn("Data_retorno");
        modelDevolvidos.addColumn("data_emprestimo");

        tableDevolvidos = new JTable(modelDevolvidos);
        JScrollPane scrollDevolvidos = new JScrollPane(tableDevolvidos);
        scrollDevolvidos.setBounds(460, 250, 425, 300);
        add(scrollDevolvidos);

        
        
       
       
        btnDevolucao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                 String sql = "insert devolucao values (?,?,?,?,?)";
        try {
            contaretorno(); 
            pstm = conexao.prepareStatement(sql);
         // pstm.setInt(1, id_emprestimo);
           pstm.setInt(1, id_devolucao);
           pstm.setInt(2,id_estudante);
            pstm.setInt(3,id_livro);
             
           String DataRetorno = DataDevolucao.getText();
           pstm.setString(4, DataRetorno);
           String DataEmprestimo = txtDataEmprestimo.getText();
           pstm.setString(5, DataEmprestimo);
            if (id_emprestimo == -1) {
             JOptionPane.showMessageDialog(null,"Selecione um livro");   
                
           } else{
               int adicionado = pstm.executeUpdate();
               if(adicionado>0){
               JOptionPane.showMessageDialog(null,"livro devolvido"); 
               atualizarLivro();
               DisplayDev();
               apagaremp();
               }else{
                   
               }
            }
            
            
        }
        catch(Exception o){
            JOptionPane.showMessageDialog(null, o+"error");
            
        }
                
            }
        });
        //Evento para setar campos 
        tableEmprestados.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DefaultTableModel model = (DefaultTableModel)tableEmprestados.getModel();
        int Myindex=tableEmprestados.getSelectedRow();
        id_emprestimo = Integer.valueOf(model.getValueAt(Myindex, 0).toString());
        id_livro = Integer.valueOf(model.getValueAt(Myindex, 1).toString());
        id_estudante = Integer.valueOf(model.getValueAt(Myindex, 2).toString());
        txtEstudante.setText(model.getValueAt(Myindex, 2).toString());
         txtTitulo.setText(model.getValueAt(Myindex, 3).toString());
         String DataEmprestimo = model.getValueAt(Myindex, 4).toString();
        txtDataEmprestimo.setText(DataEmprestimo);       
        
            }
        });
        
        
         vltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               TelaPrincipal principal = new TelaPrincipal();
                new TelaPrincipal().setVisible(true);
                dispose();
                
            }
        });
      DisplayEmp();
           DisplayDev();
    }   
    
      private void apagaremp(){
try {
            String sql = "delete from emprestimo where id_emprestimo="+id_emprestimo;
            Statement Del = conexao.createStatement();       
                Del.executeUpdate(sql);
                //DisplayLivros();
          
            //JOptionPane.showMessageDialog(this, "Livro apagado");
            //BtnAddLivros.setEnabled(true);
}catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e);
            }   
    }    
      
int id_estudante;
int id_livro;
int id_emprestimo=-1;
 int livrodisp;
     private void atualizarLivro(){
        PegarQuant();
               int novo = Integer.valueOf(livrodisp)+1;
               String sql = "update livro set quantidade=? where id_livro=?";
        try{
            pstm = conexao.prepareStatement(sql);   
               pstm.setInt(1,novo);
               pstm.setInt(2,id_livro);
           
            
                if (pstm.executeUpdate() > 0){
                   
                   
                   //DisplayLivros();
                 DisplayEmp();
                }else{
                
                }
              

        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
    }
       
           }
     
     int id_devolucao;
    private void contaretorno(){
       try {
           stm1=conexao.createStatement();
           rs1=stm1.executeQuery("select Max(id_emprestimo) from emprestimo");
           rs1.next();
           id_emprestimo=rs1.getInt(1)+1; 
       } catch (SQLException ex) {
           Logger.getLogger(Emprestimo.class.getName()).log(Level.SEVERE, null, ex);
       }

}
     private void PegarQuant(){
      try {
           stm1=conexao.createStatement();
           rs1=stm1.executeQuery("select quantidade from livro where"+id_livro);
           rs1.next();
           livrodisp =rs1.getInt(1);
       } catch (SQLException ex) {
           Logger.getLogger(Emprestimo.class.getName()).log(Level.SEVERE, null, ex);
       }  
    }
     
      private void DisplayEmp(){
     try{
         stm = conexao.createStatement();
         rs = stm.executeQuery("select id_emprestimo,id_livro,id_estudante,titulo,data_emprestimo from emprestimo");
         tableEmprestados.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
         }
      catch(SQLException e )
     {
        
         JOptionPane.showMessageDialog(this, e);
     } 
         }
      
     // Metodo para mostrar livros na tabela 
      private void DisplayDev(){
       try{
         stm = conexao.createStatement();
         rs = stm.executeQuery("select id_devolucao as Id,id_estudante as ID_estudante,livro_devolvido as Livros_devolvidos,data_retorno as Data_retorno,data_emprestimo from devolucao");
         tableDevolvidos.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
         }
      catch(SQLException e )
     {
        
         JOptionPane.showMessageDialog(this, e);
     }  
    }
      
 
     

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Devolucao().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(Devolucao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}