
package Telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TelaPrincipal extends JFrame{
       
     JButton botao1;
     JButton botao2;
    JButton botao3;
     JButton botao4;
     JLabel tit;
     JButton voltarbtn;

    public TelaPrincipal(){
       
       
        
        setTitle("SISTEMA DE GESTÃO DE BIBLIOTECA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(null); // Usando layout absoluto (sem gerenciador de layout)
        
        tit = new JLabel("PAINEL PRINCIPAL");
        tit.setBounds(110, 50, 200, 30);
        tit.setFont(new java.awt.Font("Times New Roman", 0, 18));
        add(tit);
        
        
        // Configuração dos botões
        botao1 = new JButton("LIVROS");
        botao1.setBounds(50, 110, 110, 30); // Define posição e tamanho do botão
        add(botao1);

        botao2 = new JButton("EMPRESTIMO");
        botao2.setBounds(220, 110, 120, 30);
        add(botao2);

        botao3 = new JButton("ESTUDANTES");
        botao3.setBounds(50, 230, 110, 30);
        add(botao3);

        botao4 = new JButton("DEVOLUCAO");
        botao4.setBounds(220, 230, 120, 30);
        add(botao4);
        
        voltarbtn= new JButton();
        voltarbtn.setBounds(0, 0, 30, 30);
        voltarbtn.setIcon(new ImageIcon(getClass().getResource("/Icons/voltar.png")));
        add(voltarbtn);

        getContentPane().setBackground(Color.decode("#D3D3D3")); // Define a cor de fundo da janela
        setVisible(true);
        
        voltarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               LoginUsuario User = new LoginUsuario();
                new LoginUsuario().setVisible(true);
                dispose();
                
            }
        });
      
        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Livros livros = new Livros();
                new Livros().setVisible(true);
                dispose();
                
            }
        });
        
        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Emprestimo emprestimo = new Emprestimo();
                new Emprestimo().setVisible(true);
                dispose();
                
            }
        });
        
        botao3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Estudantes livros = new Estudantes();
                new Estudantes().setVisible(true);
                dispose();
                
            }
        });
        botao4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Devolucao devolucao = new Devolucao();
                } catch (ParseException ex) {
                    Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    new Devolucao().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
                
            }
        });
        
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}

