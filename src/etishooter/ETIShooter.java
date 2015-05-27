/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etishooter;

import javax.swing.JFrame;

/**
 *
 * @author Oskar
 */
public class ETIShooter extends JFrame {
    ETIShooter(){
        super("ETIShooter");
        setBounds(50,50,800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ETIShooter okno = new ETIShooter();
        okno.repaint();
        // TODO code application logic here
    }
    
}
