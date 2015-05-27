package etishooter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

class Cel
{
    private int x = 0, y = 0;
    private boolean czyDobry = true;
    
    Cel(Rectangle granice)
    {
        x = (int)( granice.x + granice.width * Math.random() );
        y = (int)( granice.y + granice.height * Math.random() );
        if (Math.random() < 0.5)
            czyDobry = false;
    }
    
    public int GetX()
    {
        return x;
    }
    public int GetY()
    {
        return y;
    }
    public boolean GetDobry()
    {
        return czyDobry;
    }
}


public class ETIShooter extends JFrame {
    
    private int tryb = 0;
    private Cel cele[];
    private Image tlo;
    private Timer zegar;
    
    class Zadanie extends TimerTask
    {

        public void run()
        {
            for (int i = 0; i < 10; i++)
            {
                cele[i] = new Cel(new Rectangle(0,0,600,500));
            }
            repaint();
        }
    }
    
    ETIShooter(){
        super("ETI Shooter");
        cele = new Cel[10];
        setBounds(50,50,800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
        try 
        {
            Thread.sleep(10);
        } 
        catch(InterruptedException ex) 
        {
            Thread.currentThread().interrupt();
        }

        zegar = new java.util.Timer();
        zegar.scheduleAtFixedRate(new Zadanie(),0,1000);
        
        tlo = new ImageIcon("res/tlo.jpg").getImage();
              
    }    
    
    public static void main(String[] args) 
    {
        ETIShooter okno = new ETIShooter();
        okno.repaint();
    }
    
    public void paint(Graphics g)
    {
        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics2D g2d = (Graphics2D)bstrategy.getDrawGraphics();
        
        g2d.drawImage(tlo, 0, 0, 800 , 600, null);
        g2d.setColor(Color.DARK_GRAY);
        
        g2d.setColor(Color.RED);
        
        for (int i=0; i < 10; i++)
        {
            try
            {
                if (cele[i].GetDobry())
                    g2d.setColor(Color.GREEN);
                else
                    g2d.setColor(Color.RED);
            }
            catch (NullPointerException e)
            {
                continue;
            }
            g2d.draw(new Ellipse2D.Float(cele[i].GetX(), cele[i].GetY(), 100, 100));
            g2d.fill(new Ellipse2D.Float(cele[i].GetX(), cele[i].GetY(), 100, 100));
        }
        g2d.dispose();
        
        bstrategy.show();
        
    }
    
}
