package com.company;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;

/**
 * Created by caichangzhou on 3/29/17.
 */
public class test_GUI extends Canvas implements Runnable{

    private static final int WIDTH =700;
    private static final int HEIGHT=700;



    public static void main (String [] args)
    {

        JFrame frame = new ImageFrame7(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        frame.setVisible(true);

    }

    @Override
    public void run() {

    }
}

class ImageFrame7 extends JFrame {

    private final JFileChooser chooser;
    private BufferedImage image = null;
    private Polygon Boards [] = new Polygon[200];
    private static boolean running =true;
    public static final int UPS_CAP=60;
    private static BufferedImage image1 = new BufferedImage(610,610, BufferedImage.TYPE_INT_ARGB);


    public ImageFrame7(int width, int height) {
        this.setTitle("CAP 3027 2016 - HW04 - Changzhou Cai");
        this.setSize(width, height);
        addMenu();

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

    }

    JMenu fileMenu = new JMenu("File");

    private void addMenu() {

        JMenuItem openItem1 = new JMenuItem("Game Board");
        openItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                GameBoardInin();
                displayBufferedImage(image1);
                running=true;
                //run();

            }
        });

        fileMenu.add(openItem1);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        fileMenu.add(exitItem);


        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);


    }

    Polygon getHexagon(int x, int y, int h)
    {
        Polygon hexagon = new Polygon();

        for (int i=0; i < 7; i++)
        {
            double a = Math.PI / 3.0 * i;
            hexagon.addPoint((int)(Math.round(x + Math.sin(a) * h)), (int)(Math.round(y + Math.cos(a) * h)));
        }
        return hexagon;
    }

    private void  GameBoardInin()
    {

        Graphics2D g2d=(Graphics2D)image1.createGraphics();
        g2d.setColor(Color.black);

        Color fill=Color.white;
        int rgb=fill.getRGB();
        for(int i=0;i<image1.getHeight();i++)
        {
            for(int j=0;j<image1.getHeight();j++)
                image1.setRGB(i, j, rgb);
        }




        int temp=60;    //line four
        for (int i=0; i<10;i++){

            Boards[i] =getHexagon(temp,165,30);
            temp+=52;

        }

        for(int i=3; i<7;i++)
            g2d.drawPolygon(Boards[i]);

        temp=35;  //line five
        for (int i=10; i<21;i++){

            Boards[i] =getHexagon(temp,210,30);
            temp+=52;

        }

        for(int i=13; i<18;i++)
            g2d.drawPolygon(Boards[i]);

        temp=60;    //line seven
        for (int i=21; i<32;i++){

            Boards[i] =getHexagon(temp,255,30);
            temp+=52;

        }

        for(int i=23; i<29;i++)
            g2d.drawPolygon(Boards[i]);

        temp=35;  //line eight
        for (int i=32; i<43;i++){

            Boards[i] =getHexagon(temp,300,30);
            temp+=52;

        }

        for(int i=34; i<41;i++)
            g2d.drawPolygon(Boards[i]);


        temp=60;    //line nine
        for (int i=43; i<54;i++){

            Boards[i] =getHexagon(temp,345,30);
            temp+=52;

        }

        for(int i=45; i<51;i++)
            g2d.drawPolygon(Boards[i]);


        temp=35;  //line ten
        for (int i=54; i<63;i++){

            Boards[i] =getHexagon(temp,390,30);
            temp+=52;

        }

        for(int i=57; i<62;i++)
            g2d.drawPolygon(Boards[i]);

        temp=60;    //line 11
        for (int i=65; i<76;i++){

            Boards[i] =getHexagon(temp,435,30);
            temp+=52;

        }

        for(int i=68; i<72;i++)
            g2d.drawPolygon(Boards[i]);





    }


    private void run()
    {
        final double UNS = 1e9/UPS_CAP;
        long lastFrame = System.nanoTime();
        double delta =0 ;
        int frames =0;
        int updates =0;
        long timer = System.currentTimeMillis();

        while(running)
        {
            long thisFrame =System.nanoTime();
            delta+=(thisFrame-lastFrame)/UNS;
            lastFrame = thisFrame;
            while(delta>=1)
            {
                //update();
                updates++;
                delta--;
            }
            displayBufferedImage(image1);
            //render();
            frames++;
            if(System.currentTimeMillis()-timer>= 1e3)
            {
                timer+=1e3;
                frames =0;
                updates =0;
            }

        }
    }


    public  void render()
    {

    }

    public  void displayBufferedImage(BufferedImage image)
    {
        final JButton button = new JButton( "start/pause" );
        button.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent event )
            {
//
            }
        } );
        this.getContentPane().add( button, BorderLayout.SOUTH );
        this.getContentPane().add(new JScrollPane(new JLabel(new ImageIcon(image))),BorderLayout.CENTER);
        this.validate();
    }

}