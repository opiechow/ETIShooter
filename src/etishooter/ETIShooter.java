package etishooter;

import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

class Cel
{
    private Rectangle kwadraturaKola = new Rectangle();
    private boolean czyDobry = true;
    private boolean czyTrafiony = false;
    
    Cel(Rectangle granice)
    {
        kwadraturaKola.height = 50;
        kwadraturaKola.width = 50;
        kwadraturaKola.x = (int)( granice.x + granice.width * Math.random() );
        kwadraturaKola.y = (int)( granice.y + granice.height * Math.random() );
        
        if (Math.random() < 0.5)
            czyDobry = false;
    }
    
    public boolean GetDobry()
    {
        return czyDobry;
    }
    public boolean GetTrafiony()
    {
        return czyTrafiony;
    }
    public Rectangle GetKwadratura()
    {
        return kwadraturaKola;
    }
    public void trafiony()
    {
        czyTrafiony = true;
    }
}


public class ETIShooter extends JFrame 
{
    private JButton przyciski[];
    private JTextArea oknoTekstowe;
    private int punktacja = 0;
    private int tryb = 0;
    private Cel cele[];
    private Image tlo1;
    private Image celDobry;
    private Image celZly;
    private Timer zegar;
    private Rectangle granice = new Rectangle(50,50,700,500);
    private boolean CzyGenerowac = true;
    
    private class ObslugaPrzycisku implements ActionListener
    {
        private JFrame ref_okno;

        ObslugaPrzycisku(JFrame okno)
        {
         ref_okno = okno;
        }

        public void actionPerformed(ActionEvent e) 
        {
            JButton bt = (JButton)e.getSource();
            if(bt==przyciski[0])
            {
                oknoTekstowe.append("Najlepsze wyniki:\n");
            }                
            else if(bt==przyciski[1])
            {
                tryb = 1; 
            }         
        }
    }
    
    class Zadanie extends TimerTask
    {

        public void run()
        {
            for (int i = 0; i < 10; i++)
            {

                cele[i] = new Cel(granice);
                for (int j = 0; j < i; j++)
                {
                    if (cele[i].GetKwadratura().intersects(cele[j].GetKwadratura()))
                    {
                        cele[i] = new Cel(granice);
                        j = 0;
                    }
                }   
            }
            repaint();
        }
    }
    
    ETIShooter()
    {
        super("ETI Shooter");
        cele = new Cel[10];
        setBounds(50,50,800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
       
        przyciski = new JButton[2];
        
        przyciski[0] = new JButton("NAJLEPSZE WYNIKI");
        przyciski[0].addActionListener(new ObslugaPrzycisku(this));

        przyciski[1] = new JButton("NOWA GRA");
        przyciski[1].addActionListener(new ObslugaPrzycisku(this));

        JPanel panelPrzyciski   = new JPanel(new FlowLayout());
        panelPrzyciski.add(przyciski[0]);
        panelPrzyciski.add(przyciski[1]);

        oknoTekstowe = new JTextArea("ETIShooter\n");

        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        content.add(panelPrzyciski,BorderLayout.NORTH);
        content.add(new JScrollPane(oknoTekstowe),BorderLayout.CENTER);
        setVisible(true);
        
        
        zegar = new java.util.Timer();
        zegar.scheduleAtFixedRate(new Zadanie(),0,2000);
        
        this.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e) 
            {
                
            }
            
            @Override
            public void mousePressed(MouseEvent e) 
            {
                Point trafione = e.getPoint();
                for (int i = 0; i < 10; i++)
                {
                    if (cele[i].GetKwadratura().contains(trafione) &&
                        !cele[i].GetTrafiony())
                    {
                        cele[i].trafiony();
                        if (cele[i].GetDobry())
                            punktacja += 100;
                        else
                            punktacja -= 100;
                    }
                }
                repaint();               
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) 
            {
                
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                
            }
        });
        
        tlo1 = new ImageIcon("res/tlo1.jpeg").getImage();
        celDobry = new ImageIcon("res/celDobry.jpg").getImage();
        celZly = new ImageIcon("res/celZly.jpg").getImage();
    }    
    
    public static void main(String[] args) 
    {
        ETIShooter okno = new ETIShooter();
        okno.repaint();
    }
    
 /*   public void paint(Graphics g)
    {
        try 
        {
            Thread.sleep(100);    // zeby uniknac NullPointerException przy inicjalizacji
        } 
        catch(InterruptedException ex) 
        {
            Thread.currentThread().interrupt();
        }
        
        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics2D g2d = (Graphics2D)bstrategy.getDrawGraphics();
        
        
        g2d.drawImage(tlo1, 0, 0, 800 , 600, null);
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial",Font.BOLD,20));
        g2d.drawString("PUNKTACJA: " + punktacja, 5, 55);
        
        for (int i=0; i < 10; i++)
        {
            if (!cele[i].GetTrafiony())
           {
                try
                {
                    if (cele[i].GetDobry())
                        g2d.drawImage(celDobry, 
                                      cele[i].GetKwadratura().x,
                                      cele[i].GetKwadratura().y,
                                      cele[i].GetKwadratura().width,
                                      cele[i].GetKwadratura().height,
                                      null);
                    else
                       g2d.drawImage(celZly, 
                                     cele[i].GetKwadratura().x,
                                     cele[i].GetKwadratura().y,
                                     cele[i].GetKwadratura().width,
                                     cele[i].GetKwadratura().height,
                                     null);
                }
                catch (NullPointerException e)
                {
                    continue;
                }
           }
        }        
        g2d.dispose();
        bstrategy.show();        
    }   */ 
}
