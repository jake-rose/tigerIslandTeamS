
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
    private Polygon Game [] =new Polygon[200];
    private boolean IsPlaced []=new boolean [38];
    private boolean firstTile =true;
    private boolean adj= false;
    Graphics2D g2d=(Graphics2D)image1.createGraphics();

    private int HexCord [][]  = {
                                {0,3,-3},{1,2,-3},{2,1,-3},{3,0,-3},
                          {-1,3,-2},{0,2,-2},{1,1,-2},{2,0,-2},{3,-1,-2},
                    {-2,3,-1},{-1,2,-1},{0,1,-1},{1,0,-1},{2,-1,-1},{3,-2,-1},
               {-3,3,0},{-2,2,0},{-1,1,0},{0,0,0},{1,-1,0},{2,-2,0},{3,-3,0},
                    {-3,2,1},{-2,1,1},{-1,0,1},{0,-1,1},{1,-2,1},{2,-3,1},
                        {-3,1,2},{-2,0,-2},{-1,-1,2},{0,-2,2},{1,-3,2},
                                 {-3,0,3},{-2,-1,3},{-1,-2,3},{0,-3,3},
                                };
    private static boolean running =true;
    public static final int UPS_CAP=60;
    private static BufferedImage image1 = new BufferedImage(610,610, BufferedImage.TYPE_INT_ARGB);


    public ImageFrame7(int width, int height) {
        this.setTitle("TigerLand Team-S");
        this.setSize(width, height);
        GameBoardInin();
        addMenu();

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        button();

    }

    JMenu fileMenu = new JMenu("File");

    private void addMenu() {

        JMenuItem openItem1 = new JMenuItem("Clear Game Board");
        openItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                //GameBoardInin();
                Color fill=Color.white;
                int rgb=fill.getRGB();
                for(int i=0;i<image1.getHeight();i++)
                {
                    for(int j=0;j<image1.getHeight();j++)
                        image1.setRGB(i, j, rgb);
                }
                firstTile=true;
                adj=false;
                for(int i =0;i<IsPlaced.length;i++)
                    IsPlaced[i]=false;
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

    private boolean ValidMove(int x, int y, int z)
    {
        if(x==0&& y==3 && z==-3)     //hex #1
            return IsPlaced[0];
        else if(x==1&& y==2 && z==-3) //hex #2
            return IsPlaced[1];
        else if(x==2&& y==1 && z==-3) //hex #3
            return IsPlaced[2];
        else if(x==3&& y==0 && z==-3) //hex #4
            return IsPlaced[3];

        else if(x==-1&& y==3 && z==-2) //hex #5
            return IsPlaced[4];
        else if(x==0&& y==2 && z==-2) //hex #6
            return IsPlaced[5];
        else if(x==1&& y==1 && z==-2) //hex #7
            return IsPlaced[6];
        else if(x==2&& y==0 && z==-2) //hex #8
            return IsPlaced[7];
        else if(x==3&& y==-1 && z==-2) //hex #9
            return IsPlaced[8];

        else if(x==-2&& y==3 && z==-1) //hex #10
            return IsPlaced[9];
        else if(x==-1&& y==2 && z==-1) //hex #11
            return IsPlaced[10];
        else if(x==0&& y==1 && z==-1) //hex #12
            return IsPlaced[11];
        else if(x==1&& y==0 && z==-1) //hex #13
            return IsPlaced[12];
        else if(x==2&& y==-1 && z==-1) //hex #14
            return IsPlaced[13];
        else if(x==3&& y==-2 && z==-1) //hex #15
            return IsPlaced[14];

        else if(x==-3&& y==3 && z==0) //hex #16
            return IsPlaced[15];
        else if(x==-2&& y==2 && z==0) //hex #17
            return IsPlaced[16];
        else if(x==-1&& y==1 && z==0) //hex #18
            return IsPlaced[17];
        else if(x==0&& y==0 && z==0) //hex #19
            return IsPlaced[18];
        else if(x==1&& y==-1 && z==0) //hex #20
            return IsPlaced[19];
        else if(x==2&& y==-2 && z==0) //hex #21
            return IsPlaced[20];
        else if(x==3&& y==-3 && z==0) //hex #22
            return IsPlaced[21];

        else if(x==-3&& y==2 && z==1) //hex #23
            return IsPlaced[22];
        else if(x==-2&& y==1 && z==1) //hex #24
            return IsPlaced[23];
        else if(x==-1&& y==0 && z==1) //hex #25
            return IsPlaced[24];
        else if(x==0&& y==-1 && z==1) //hex #26
            return IsPlaced[25];
        else if(x==1&& y==-2 && z==1) //hex #27
            return IsPlaced[26];
        else if(x==2&& y==-3 && z==1) //hex #28
            return IsPlaced[27];

        else if(x==-3&& y==1 && z==2) //hex #29
            return IsPlaced[28];
        else if(x==-2&& y==0 && z==2) //hex #30
            return IsPlaced[29];
        else if(x==-1&& y==-1 && z==2) //hex #31
            return IsPlaced[30];
        else if(x==0&& y==-2 && z==2) //hex #32
            return IsPlaced[31];
        else if(x==1&& y==-3 && z==2) //hex #33
            return IsPlaced[32];

        else if(x==-3&& y==0 && z==3) //hex #34
            return IsPlaced[33];
        else if(x==-2&& y==-1 && z==3) //hex #35
            return IsPlaced[34];
        else if(x==-1&& y==-2 && z==3) //hex #36
            return IsPlaced[35];
        else if(x==0&& y==-3 && z==3) //hex #37
            return IsPlaced[36];

        return false;
    }

    void button(){


        final JButton button = new JButton( "Place a new tile" );
        button.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent event )
            {

                int x,y,z,o;
                int orgin;
                String input1 = JOptionPane.showInputDialog("Please give a value of x :");
                try {
                    x=Integer.parseInt(input1);
                    String input2 = JOptionPane.showInputDialog("Please give a value of y :");
                    try {
                        y=Integer.parseInt(input2);
                        String input3 = JOptionPane.showInputDialog("Please give a value of z :");
                        try {
                            z=Integer.parseInt(input3);
                            //g2d.drawPolygon(FindHex(x,y,z));
                            String input4 = JOptionPane.showInputDialog("Please give a orientation number :");
                            try {
                                Polygon hex1 =null;
                                Polygon hex2 =null;
                                Polygon hex3 =null;
                                o=Integer.parseInt(input4);
                                System.out.println("made here");
                                boolean tempHex =CheckAdj(x,y,z);
                                if(tempHex==true || firstTile==true) {
                                    if (ValidMove(x, y, z) == false) {
                                        hex1 = FindHex(x, y, z);
                                        adj=true;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                        button();
                                    }
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                    button();
                                }



                                if(o==1)
                                {
                                    if(CheckAdj(x,y+1,z-1)==true || CheckAdj(x+1,y,z-1)==true || firstTile==true || adj ==true) {
                                        if (ValidMove(x, y + 1, z - 1) == false && ValidMove(x + 1, y, z - 1) == false) {
                                            hex2 = FindHex(x, y + 1, z - 1);
                                            hex3 = FindHex(x + 1, y, z - 1);
                                            g2d.drawPolygon(hex1);
                                            g2d.drawPolygon(hex2);
                                            g2d.drawPolygon(hex3);
                                            firstTile=false;
                                            adj=false;
                                        } else {

                                            SetIsplaced(hex1);
                                            JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                            button();
                                        }
                                    }
                                    else
                                    {

                                        JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                        button();
                                    }

                                }
                                else if(o==2)
                                {
                                    if( CheckAdj(x + 1, y, z - 1)==true || CheckAdj(x + 1, y - 1, z)==true ||firstTile==true || adj ==true) {
                                        if (ValidMove(x + 1, y, z - 1) == false && ValidMove(x + 1, y - 1, z) == false) {
                                            hex2 = FindHex(x + 1, y, z - 1);
                                            hex3 = FindHex(x + 1, y - 1, z);
                                            g2d.drawPolygon(hex1);
                                            g2d.drawPolygon(hex2);
                                            g2d.drawPolygon(hex3);
                                            firstTile=false;
                                            adj=false;
                                        } else {
                                            SetIsplaced(hex1);
                                            JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                            button();
                                        }
                                    }
                                    else
                                    {

                                        JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                        button();
                                    }

                                }
                                else if(o==3)
                                {
                                    if(CheckAdj(x + 1, y - 1, z)==true || CheckAdj(x, y - 1, z + 1)==true || firstTile ==true || adj ==true) {
                                        if (ValidMove(x + 1, y - 1, z) == false && ValidMove(x, y - 1, z + 1) == false) {
                                            hex2 = FindHex(x + 1, y - 1, z);
                                            hex3 = FindHex(x, y - 1, z + 1);
                                            g2d.drawPolygon(hex1);
                                            g2d.drawPolygon(hex2);
                                            g2d.drawPolygon(hex3);
                                            firstTile=false;
                                            adj=false;
                                        } else {
                                            SetIsplaced(hex1);
                                            JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                            button();
                                        }
                                    }
                                    else
                                    {

                                        JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                        button();
                                    }
                                }
                                else if(o==4)
                                {
                                    if(CheckAdj(x, y - 1, z + 1)==true || CheckAdj(x - 1, y, z + 1)==true || firstTile==true || adj ==true) {
                                        if (ValidMove(x, y - 1, z + 1) == false && ValidMove(x - 1, y, z + 1) == false) {
                                            hex2 = FindHex(x, y - 1, z + 1);
                                            hex3 = FindHex(x - 1, y, z + 1);
                                            g2d.drawPolygon(hex1);
                                            g2d.drawPolygon(hex2);
                                            g2d.drawPolygon(hex3);
                                            firstTile=false;
                                            adj=false;
                                        } else {
                                            SetIsplaced(hex1);
                                            JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                            button();
                                        }
                                    }
                                    else
                                    {

                                        JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                        button();
                                    }

                                }
                                else if(o==5)
                                {
                                    if(CheckAdj(x - 1, y, z + 1)==true || CheckAdj(x - 1, y + 1, z)==true ||firstTile==true || adj ==true) {
                                        if (ValidMove(x - 1, y, z + 1) == false && ValidMove(x - 1, y + 1, z) == false) {
                                            hex2 = FindHex(x - 1, y, z + 1);
                                            hex3 = FindHex(x - 1, y + 1, z);
                                            g2d.drawPolygon(hex1);
                                            g2d.drawPolygon(hex2);
                                            g2d.drawPolygon(hex3);
                                            firstTile=false;
                                            adj=false;
                                        } else {
                                            SetIsplaced(hex1);
                                            JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                            button();
                                        }
                                    }
                                    else
                                    {

                                        JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                        button();
                                    }

                                }
                                else if(o==6)
                                {
                                    if(CheckAdj(x - 1, y + 1, z)==true || CheckAdj(x, y + 1, z - 1)==true||firstTile==true || adj ==true) {
                                        if (ValidMove(x - 1, y + 1, z) == false && ValidMove(x, y + 1, z - 1) == false) {
                                            hex2 = FindHex(x - 1, y + 1, z);
                                            hex3 = FindHex(x, y + 1, z - 1);
                                            g2d.drawPolygon(hex1);
                                            g2d.drawPolygon(hex2);
                                            g2d.drawPolygon(hex3);
                                            firstTile=false;
                                            adj=false;
                                        } else {
                                            SetIsplaced(hex1);
                                            JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                            button();
                                        }
                                    }
                                    else
                                    {

                                        JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                        button();
                                    }

                                }






                            }
                            catch(NullPointerException e)
                            {
                                JOptionPane.showMessageDialog(fileMenu,e);
                            }

                        }
                        catch(NullPointerException e)
                        {
                            JOptionPane.showMessageDialog(fileMenu,e);
                        }
                    }
                    catch(NullPointerException e)
                    {
                        JOptionPane.showMessageDialog(fileMenu,e);
                    }
                }
                catch(NullPointerException e)
                {
                    JOptionPane.showMessageDialog(fileMenu,e);
                }
                displayBufferedImage(image1);
            }



        } );
        this.getContentPane().add( button, BorderLayout.SOUTH );

    }

    public void SetIsplaced(Polygon game)
    {
        if(Game[0].equals(game))     //hex #1
        {
            //temp= {0,3,-3};
            IsPlaced[0]=false;
        }
        else if(Game[1].equals(game)) //hex #2
        {
            IsPlaced[1]=false;
        }
        else if(Game[2].equals(game)) //hex #3
        {
            IsPlaced[2]=false;
        }
        else if(Game[3].equals(game)) //hex #4
        {
            IsPlaced[3]=false;
        }

        else if(Game[4].equals(game)) //hex #5
        {
            IsPlaced[4]=false;
        }
        else if(Game[5].equals(game)) //hex #6
        {
            IsPlaced[5]=false;
        }
        else if(Game[6].equals(game)) //hex #7
        {
            IsPlaced[6]=false;
        }
        else if(Game[7].equals(game)) //hex #8
        {
            IsPlaced[7]=false;
        }
        else if(Game[8].equals(game)) //hex #9
        {
            IsPlaced[8]=false;
        }

        else if(Game[9].equals(game)) //hex #10
        {
            IsPlaced[9]=false;
        }
        else if(Game[10].equals(game)) //hex #11
        {
            IsPlaced[10]=false;
        }
        else if(Game[11].equals(game)) //hex #12
        {
            IsPlaced[11]=false;
        }
        else if(Game[12].equals(game)) //hex #13
        {
            IsPlaced[12]=false;
        }
        else if(Game[13].equals(game)) //hex #14
        {
            IsPlaced[13]=false;
        }
        else if(Game[14].equals(game)) //hex #15
        {
            IsPlaced[14]=false;
        }

        else if(Game[15].equals(game)) //hex #16
        {
            IsPlaced[15]=false;
        }
        else if(Game[16].equals(game)) //hex #17
        {
            IsPlaced[16]=false;
        }
        else if(Game[17].equals(game)) //hex #18
        {
            IsPlaced[17]=false;
        }
        else if(Game[18].equals(game)) //hex #19
        {
            IsPlaced[18]=false;

        }
        else if(Game[19].equals(game)) //hex #20
        {
            IsPlaced[19]=false;
        }
        else if(Game[20].equals(game)) //hex #21
        {
            IsPlaced[20]=false;
        }
        else if(Game[21].equals(game)) //hex #22
        {
            IsPlaced[21]=false;
        }

        else if(Game[22].equals(game)) //hex #23
        {
            IsPlaced[22]=false;
        }
        else if(Game[23].equals(game)) //hex #24
        {
            IsPlaced[23]=false;
        }
        else if(Game[24].equals(game)) //hex #25
        {
            IsPlaced[24]=false;
        }
        else if(Game[25].equals(game)) //hex #26
        {
            IsPlaced[25]=false;
        }
        else if(Game[26].equals(game)) //hex #27
        {
            IsPlaced[26]=false;
        }
        else if(Game[27].equals(game)) //hex #28
        {
            IsPlaced[27]=false;
        }

        else if(Game[28].equals(game)) //hex #29
        {
            IsPlaced[28]=false;
        }
        else if(Game[29].equals(game)) //hex #30
        {
            IsPlaced[29]=false;
        }
        else if(Game[30].equals(game)) //hex #31
        {
            IsPlaced[30]=false;
        }
        else if(Game[31].equals(game)) //hex #32
        {
            IsPlaced[31]=false;
        }
        else if(Game[32].equals(game)) //hex #33
        {
            IsPlaced[32]=false;
        }

        else if(Game[33].equals(game)) //hex #34
        {
            IsPlaced[33]=false;
        }
        else if(Game[34].equals(game)) //hex #35
        {
            IsPlaced[34]=false;
        }
        else if(Game[35].equals(game)) //hex #36
        {
            IsPlaced[35]=false;
        }
        else if(Game[36].equals(game)) //hex #37
        {
            IsPlaced[36]=false;
        }

    }

    public boolean CheckAdj(int x, int y, int z)
    {
        int array[];

        for(int i=0;i<37;i++)
        {
            System.out.println("Index " + i+ ": " + IsPlaced[i]);
            if(IsPlaced[i]==true)
            {
                array=FindCord(Game[i]);
                System.out.println("mdae here# 2");
                System.out.println(array[0] + array[1] + array[2]);
                if(array[0]==x && array[1]==y+1 && array[2]==z-1)
                    return true;
                else if (array[0]==x+1 && array[1]==y && array[2]==z-1)
                    return true;
                else if (array[0]==x+1 && array[1]==y-1 && array[2]==z)
                    return true;
                else if (array[0]==x && array[1]==y-1 && array[2]==z+1)
                    return true;
                else if (array[0]==x-1 && array[1]==y && array[2]==z+1)
                    return true;
                else if (array[0]==x-1 && array[1]==y+1 && array[2]==z)
                    return true;


            }

        }

        return false;
    }

    public int [] FindCord(Polygon game)
    {
        System.out.println("FindCordsa");
        int [] array =new int[3];

        if(Game[0].equals(game))     //hex #1
        {
            //temp= {0,3,-3};
            array[0]=0;
            array[1]=3;
            array[2]=-3;
            return array;
        }
        else if(Game[1].equals(game)) //hex #2
        {
            array[0]=1;
            array[1]=2;
            array[2]=-3;
            return array;
        }
        else if(Game[2].equals(game)) //hex #3
        {
            array[0]=2;
            array[1]=1;
            array[2]=-3;
            return array;
        }
        else if(Game[3].equals(game)) //hex #4
        {
            array[0]=3;
            array[1]=0;
            array[2]=-3;
            return array;
        }

        else if(Game[4].equals(game)) //hex #5
        {
            array[0]=-1;
            array[1]=3;
            array[2]=-2;
            return array;
        }
        else if(Game[5].equals(game)) //hex #6
        {
            array[0]=0;
            array[1]=2;
            array[2]=-2;
            return array;
        }
        else if(Game[6].equals(game)) //hex #7
        {
            array[0]=1;
            array[1]=1;
            array[2]=-2;
            return array;
        }
        else if(Game[7].equals(game)) //hex #8
        {
            array[0]=2;
            array[1]=0;
            array[2]=-2;
            return array;
        }
        else if(Game[8].equals(game)) //hex #9
        {
            array[0]=3;
            array[1]=-1;
            array[2]=-2;
            return array;
        }

        else if(Game[9].equals(game)) //hex #10
        {
            array[0]=-2;
            array[1]=3;
            array[2]=-1;
            return array;
        }
        else if(Game[10].equals(game)) //hex #11
        {
            array[0]=-1;
            array[1]=2;
            array[2]=-1;
            return array;
        }
        else if(Game[11].equals(game)) //hex #12
        {
            array[0]=0;
            array[1]=1;
            array[2]=-1;
            return array;
        }
        else if(Game[12].equals(game)) //hex #13
        {
            array[0]=1;
            array[1]=0;
            array[2]=-1;
            return array;
        }
        else if(Game[13].equals(game)) //hex #14
        {
            array[0]=2;
            array[1]=-1;
            array[2]=-1;
            return array;
        }
        else if(Game[14].equals(game)) //hex #15
        {
            array[0]=3;
            array[1]=-2;
            array[2]=-1;
            return array;
        }

        else if(Game[15].equals(game)) //hex #16
        {
            array[0]=-3;
            array[1]=3;
            array[2]=0;
            return array;
        }
        else if(Game[16].equals(game)) //hex #17
        {
            array[0]=-2;
            array[1]=2;
            array[2]=0;
            return array;
        }
        else if(Game[17].equals(game)) //hex #18
        {
            array[0]=-1;
            array[1]=1;
            array[2]=0;
            return array;
        }
        else if(Game[18].equals(game)) //hex #19
        {
            array[0]=0;
            array[1]=0;
            array[2]=0;
            System.out.println("made here #3");
            return array;

        }
        else if(Game[19].equals(game)) //hex #20
        {
            array[0]=1;
            array[1]=-1;
            array[2]=0;
            return array;
        }
        else if(Game[20].equals(game)) //hex #21
        {
            array[0]=2;
            array[1]=-2;
            array[2]=0;
            return array;
        }
        else if(Game[21].equals(game)) //hex #22
        {
            array[0]=3;
            array[1]=-3;
            array[2]=0;
            return array;
        }

        else if(Game[22].equals(game)) //hex #23
        {
            array[0]=3;
            array[1]=2;
            array[2]=1;
            return array;
        }
        else if(Game[23].equals(game)) //hex #24
        {
            array[0]=-2;
            array[1]=1;
            array[2]=1;
            return array;
        }
        else if(Game[24].equals(game)) //hex #25
        {
            array[0]=-1;
            array[1]=0;
            array[2]=1;
            return array;
        }
        else if(Game[25].equals(game)) //hex #26
        {
            array[0]=0;
            array[1]=-1;
            array[2]=1;
            return array;
        }
        else if(Game[26].equals(game)) //hex #27
        {
            array[0]=1;
            array[1]=-2;
            array[2]=1;
            return array;
        }
        else if(Game[27].equals(game)) //hex #28
        {
            array[0]=2;
            array[1]=-3;
            array[2]=1;
            return array;
        }

        else if(Game[28].equals(game)) //hex #29
        {
            array[0]=-3;
            array[1]=1;
            array[2]=2;
            return array;
        }
        else if(Game[29].equals(game)) //hex #30
        {
            array[0]=-2;
            array[1]=0;
            array[2]=2;
            return array;
        }
        else if(Game[30].equals(game)) //hex #31
        {
            array[0]=-1;
            array[1]=-1;
            array[2]=2;
            return array;
        }
        else if(Game[31].equals(game)) //hex #32
        {
            array[0]=0;
            array[1]=-2;
            array[2]=2;
            return array;
        }
        else if(Game[32].equals(game)) //hex #33
        {
            array[0]=1;
            array[1]=-3;
            array[2]=2;
            return array;
        }

        else if(Game[33].equals(game)) //hex #34
        {
            array[0]=-3;
            array[1]=0;
            array[2]=3;
            return array;
        }
        else if(Game[34].equals(game)) //hex #35
        {
            array[0]=-2;
            array[1]=-1;
            array[2]=3;
            return array;
        }
        else if(Game[35].equals(game)) //hex #36
        {
            array[0]=-1;
            array[1]=-2;
            array[2]=3;
            return array;
        }
        else if(Game[36].equals(game)) //hex #37
        {
            array[0]=0;
            array[1]=-3;
            array[2]=3;
            return array;
        }



        return null;

    }


    public Polygon FindHex(int x, int y, int z)
    {
        if(x==0&& y==3 && z==-3)     //hex #1
        {
            IsPlaced[0]=true;
            return Game[0];
        }
        else if(x==1&& y==2 && z==-3) //hex #2
        {
            IsPlaced[1]=true;
            return Game[1];
        }
        else if(x==2&& y==1 && z==-3) //hex #3
        {
            IsPlaced[2]=true;
            return Game[2];
        }
        else if(x==3&& y==0 && z==-3) //hex #4
        {
            IsPlaced[3]=true;
            return Game[3];
        }

        else if(x==-1&& y==3 && z==-2) //hex #5
        {
            IsPlaced[4]=true;
            return Game[4];
        }
        else if(x==0&& y==2 && z==-2) //hex #6
        {
            IsPlaced[5]=true;
            return Game[5];
        }
        else if(x==1&& y==1 && z==-2) //hex #7
        {
            IsPlaced[6]=true;
            return Game[6];
        }
        else if(x==2&& y==0 && z==-2) //hex #8
        {
            IsPlaced[7]=true;
            return Game[7];
        }
        else if(x==3&& y==-1 && z==-2) //hex #9
        {
            IsPlaced[8]=true;
            return Game[8];
        }

        else if(x==-2&& y==3 && z==-1) //hex #10
        {
            IsPlaced[9]=true;
            return Game[9];
        }
        else if(x==-1&& y==2 && z==-1) //hex #11
        {
            IsPlaced[10]=true;
            return Game[10];
        }
        else if(x==0&& y==1 && z==-1) //hex #12
        {
            System.out.println("Hex 2 here");
            IsPlaced[11]=true;
            return Game[11];
        }
        else if(x==1&& y==0 && z==-1) //hex #13
        {
            System.out.println("Hex 3 here");
            IsPlaced[12]=true;
            return Game[12];
        }
        else if(x==2&& y==-1 && z==-1) //hex #14
        {
            IsPlaced[13]=true;
            return Game[13];
        }
        else if(x==3&& y==-2 && z==-1) //hex #15
        {
            IsPlaced[14]=true;
            return Game[14];
        }

        else if(x==-3&& y==3 && z==0) //hex #16
        {
            IsPlaced[15]=true;
            return Game[15];
        }
        else if(x==-2&& y==2 && z==0) //hex #17
        {
            IsPlaced[16]=true;
            return Game[16];
        }
        else if(x==-1&& y==1 && z==0) //hex #18
        {
            IsPlaced[17]=true;
            return Game[17];
        }
        else if(x==0&& y==0 && z==0) //hex #19
        {
            System.out.println("Find hex made here");
            IsPlaced[18]=true;
            return Game[18];

        }
        else if(x==1&& y==-1 && z==0) //hex #20
        {
            IsPlaced[19]=true;
            return Game[19];
        }
        else if(x==2&& y==-2 && z==0) //hex #21
        {
            IsPlaced[20]=true;
            return Game[20];
        }
        else if(x==3&& y==-3 && z==0) //hex #22
        {
            IsPlaced[21]=true;
            return Game[21];
        }

        else if(x==-3&& y==2 && z==1) //hex #23
        {
            IsPlaced[22]=true;
            return Game[22];
        }
        else if(x==-2&& y==1 && z==1) //hex #24
        {
            IsPlaced[23]=true;
            return Game[23];
        }
        else if(x==-1&& y==0 && z==1) //hex #25
        {
            IsPlaced[24]=true;
            return Game[24];
        }
        else if(x==0&& y==-1 && z==1) //hex #26
        {
            IsPlaced[25]=true;
            return Game[25];
        }
        else if(x==1&& y==-2 && z==1) //hex #27
        {
            IsPlaced[26]=true;
            return Game[26];
        }
        else if(x==2&& y==-3 && z==1) //hex #28
        {
            IsPlaced[27]=true;
            return Game[27];
        }

        else if(x==-3&& y==1 && z==2) //hex #29
        {
            IsPlaced[28]=true;
            return Game[28];
        }
        else if(x==-2&& y==0 && z==2) //hex #30
        {
            IsPlaced[29]=true;
            return Game[29];
        }
        else if(x==-1&& y==-1 && z==2) //hex #31
        {
            IsPlaced[30]=true;
            return Game[30];
        }
        else if(x==0&& y==-2 && z==2) //hex #32
        {
            IsPlaced[31]=true;
            return Game[31];
        }
        else if(x==1&& y==-3 && z==2) //hex #33
        {
            IsPlaced[32]=true;
            return Game[32];
        }

        else if(x==-3&& y==0 && z==3) //hex #34
        {
            IsPlaced[33]=true;
            return Game[33];
        }
        else if(x==-2&& y==-1 && z==3) //hex #35
        {
            IsPlaced[34]=true;
            return Game[34];
        }
        else if(x==-1&& y==-2 && z==3) //hex #36
        {
            IsPlaced[35]=true;
            return Game[35];
        }
        else if(x==0&& y==-3 && z==3) //hex #37
        {
            IsPlaced[36]=true;
            return Game[36];
        }



        return null;


    }

    public Polygon getHexagon(int x, int y, int h)
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

        for(int i =0; i<IsPlaced.length;i++)
            IsPlaced[i]=false;

        g2d.setColor(Color.black);
        Color fill=Color.white;
        int rgb=fill.getRGB();
        for(int i=0;i<image1.getHeight();i++)
        {
            for(int j=0;j<image1.getHeight();j++)
                image1.setRGB(i, j, rgb);
        }



        int temp1 =0;
        int temp=60;    //line four
        for (int i=0; i<10;i++){

            Boards[i] =getHexagon(temp,165,30);
            temp+=52;

        }

        for(int i=3; i<7;i++) {

            Game[temp1++]=Boards[i];

        }

        temp=35;  //line five
        for (int i=10; i<21;i++){

            Boards[i] =getHexagon(temp,210,30);
            temp+=52;

        }

        for(int i=13; i<18;i++) {
            Game[temp1++]=Boards[i];

        }

        temp=60;    //line seven
        for (int i=21; i<32;i++){

            Boards[i] =getHexagon(temp,255,30);
            temp+=52;

        }

        for(int i=23; i<29;i++) {
            Game[temp1++]=Boards[i];

        }

        temp=35;  //line eight
        for (int i=32; i<43;i++){

            Boards[i] =getHexagon(temp,300,30);
            temp+=52;

        }

        for(int i=34; i<41;i++){
            Game[temp1++]=Boards[i];

        }



        temp=60;    //line nine
        for (int i=43; i<54;i++){

            Boards[i] =getHexagon(temp,345,30);
            temp+=52;

        }

        for(int i=45; i<51;i++){
            Game[temp1++]=Boards[i];

        }



        temp=35;  //line ten
        for (int i=54; i<63;i++){

            Boards[i] =getHexagon(temp,390,30);
            temp+=52;

        }

        for(int i=57; i<62;i++){
            Game[temp1++]=Boards[i];

        }

        temp=60;    //line 11
        for (int i=63; i<73;i++){

            Boards[i] =getHexagon(temp,435,30);
            temp+=52;

        }

        for(int i=66; i<70;i++){
            Game[temp1++]=Boards[i];
            //g2d.drawPolygon(Boards[i]);
        }

//        for (int i=0; i<37; i++)
//            g2d.drawPolygon(Game[i]);




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

        this.getContentPane().add(new JScrollPane(new JLabel(new ImageIcon(image))),BorderLayout.CENTER);
        this.validate();
    }

}
