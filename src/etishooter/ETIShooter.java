package etishooter;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;


public class ETIShooter extends JFrame {
    ETIShooter(){
        super("ETI Shooter");
        setBounds(50,50,800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.setVisible(true);
        JButton Przycisk1 = new JButton();
        Przycisk1.setText("OPCJA 1");
        Przycisk1.setVisible(true);
        menu.add(Przycisk1);
        
        setResizable(false);
        setVisible(true);
        
              
    
    }    
    public static void main(String[] args) {
        ETIShooter okno = new ETIShooter();
        okno.repaint();
        // TODO code application logic here
    }
    
}
