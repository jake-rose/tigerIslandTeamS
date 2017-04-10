
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
public class GUI_test extends Canvas implements Runnable{

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
    private Polygon Game [] =new Polygon[200]; //     0         1  2  3      4            5                     6       7      8                   9
    private int IsPlaced [][]=new int [999][10];// [occupition][x][y][z][landscape][player 1&2 piece type][tileIndex][level][settlement size][settlement index]
    private int PlacedCounter =0;              //    [piece]  ==> 1= meeple  2= totora  3= tiger else 0=nothing for player 1
    private int TileIndex=0;                   //    [piece]  ==> 4= meeple  5= totora  6= tiger else 0=nothing for player 2
    private boolean firstTile =true;
    private boolean adj= false;
    private int meeple = 20;
    private int totora = 3;
    private int tiger =2;
    private int meeple1 = 20;
    private int totora1 = 3;
    private int tiger1 =2;
    private boolean tilePlaced=false;
    private boolean piecePlaced=false;
    private boolean player1roundsEnd=false;
    private boolean player2roundsEnd=true;
    private int player1score=0;
    private int player2score=0;
    private int settlementIndex;
    private int counter=0;

    private boolean EndGame=false;
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
        this.setTitle("TigerLand Team-S,1.2");
        this.setSize(width, height);
        GameBoardInin();
        addMenu();

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        button1();
        button2();

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
                    IsPlaced[i][0]=0;
                PlacedCounter=0;
                displayBufferedImage(image1);


                meeple = 20;
                totora = 3;
                tiger =2;
                meeple1 = 20;
                totora1 = 3;
                tiger1 =2;
                tilePlaced=false;
                piecePlaced=false;
                player1roundsEnd=false;
                player2roundsEnd=true;
                player1score=0;
                player2score=0;
                JOptionPane.showMessageDialog(null, "New game started", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);


            }
        });

        fileMenu.add(openItem1);

        JMenuItem openItem2 = new JMenuItem("Preload the board");
        openItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                //GameBoardInin();
                Color fill=Color.white;
                int rgb=fill.getRGB();
                for(int i=0;i<image1.getHeight();i++)
                {
                    for(int j=0;j<image1.getHeight();j++)
                        image1.setRGB(i, j, rgb);
                }
                g2d.setColor(Color.red);
                IsPlaced[PlacedCounter][0] = 1;
                IsPlaced[PlacedCounter][1] = 0;
                IsPlaced[PlacedCounter][2] = 0;
                IsPlaced[PlacedCounter][3] = 0;
                IsPlaced[PlacedCounter][4] = 0;  //voclano
                IsPlaced[PlacedCounter][5] = 0;
                IsPlaced[PlacedCounter][6] = TileIndex;
                IsPlaced[PlacedCounter++][7] = 1;

                Polygon hex = FindHex(0,0,0);
                g2d.drawPolygon(hex);
                g2d.setColor(Color.black);

                IsPlaced[PlacedCounter][0] = 1;
                IsPlaced[PlacedCounter][1] = 0;
                IsPlaced[PlacedCounter][2] = 1;
                IsPlaced[PlacedCounter][3] = -1;
                IsPlaced[PlacedCounter][4] = 2;  //jungle
                IsPlaced[PlacedCounter][5] = 0;
                IsPlaced[PlacedCounter][6] = TileIndex;
                IsPlaced[PlacedCounter++][7] = 1;

                hex = FindHex(0,1,-1);
                g2d.drawPolygon(hex);

                IsPlaced[PlacedCounter][0] = 1;
                IsPlaced[PlacedCounter][1] = 1;
                IsPlaced[PlacedCounter][2] = 0;
                IsPlaced[PlacedCounter][3] = -1;
                IsPlaced[PlacedCounter][4] = 3;  //lake
                IsPlaced[PlacedCounter][5] = 0;
                IsPlaced[PlacedCounter][6] = TileIndex;
                IsPlaced[PlacedCounter++][7] = 1;

                hex = FindHex(1,0,-1);
                g2d.drawPolygon(hex);

                IsPlaced[PlacedCounter][0] = 1;
                IsPlaced[PlacedCounter][1] = -1;
                IsPlaced[PlacedCounter][2] = 0;
                IsPlaced[PlacedCounter][3] = 1;
                IsPlaced[PlacedCounter][4] = 1;  //rock
                IsPlaced[PlacedCounter][5] = 0;
                IsPlaced[PlacedCounter][6] = TileIndex;
                IsPlaced[PlacedCounter++][7] = 1;

                hex = FindHex(-1,0,1);
                g2d.drawPolygon(hex);

                IsPlaced[PlacedCounter][0] = 1;
                IsPlaced[PlacedCounter][1] = 0;
                IsPlaced[PlacedCounter][2] = -1;
                IsPlaced[PlacedCounter][3] = 1;
                IsPlaced[PlacedCounter][4] = 4;  //rock
                IsPlaced[PlacedCounter][5] = 0;
                IsPlaced[PlacedCounter][6] = TileIndex++;
                IsPlaced[PlacedCounter++][7] = 1;

                hex = FindHex(0,-1,1);
                g2d.drawPolygon(hex);



                displayBufferedImage(image1);


                JOptionPane.showMessageDialog(null, "New game started", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);


            }
        });

        fileMenu.add(openItem2);

        JMenuItem openItem3 = new JMenuItem("End turn");
        openItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                if(tilePlaced==false)
                {
                    if(player1roundsEnd==false && player2roundsEnd==true)
                    {
                        player1roundsEnd=true;
                        player2roundsEnd=false;
                    }
                    else if(player1roundsEnd==true && player2roundsEnd==false)
                    {
                        player1roundsEnd=false;
                        player2roundsEnd=true;
                    }
                }
                tilePlaced=false;
                piecePlaced=false;




                if(player1roundsEnd==false)
                {
                    JOptionPane.showMessageDialog(null, "Player 1's turn", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                }
                else if(player2roundsEnd==false)
                {
                    JOptionPane.showMessageDialog(null, "Player 2's turn", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                }

                System.out.println("Score for player 1: "+player1score);
                System.out.println("Score for player 2: "+player2score);

            }
        });

        fileMenu.add(openItem3);



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

    private int ValidMove(int x, int y, int z)
    {
        for(int i=0;i<PlacedCounter;i++)
        {
            if(IsPlaced[i][1]==x)
            {
                if(IsPlaced[i][2]==y)
                {
                    if(IsPlaced[i][3]==z)
                        return IsPlaced[i][0];
                }
            }
        }

        return 0;
    }

    void button1(){


        final JButton button = new JButton( "Place a new tile" );
        button.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent event ) {

                int x, y, z, o;
                int orgin;
                String input1 = JOptionPane.showInputDialog("Please give a value of x :");
                try {
                    x = Integer.parseInt(input1);
                    String input2 = JOptionPane.showInputDialog("Please give a value of y :");
                    try {
                        y = Integer.parseInt(input2);
                        String input3 = JOptionPane.showInputDialog("Please give a value of z :");
                        try {
                            z = Integer.parseInt(input3);
                            //g2d.drawPolygon(FindHex(x,y,z));
                            String input4 = JOptionPane.showInputDialog("Please give a orientation number :");
                            try {

                                tile Tile = new tile();
                                String test1 = Tile.getHex1Type();
                                String test2 = Tile.getHex2Type();
                                String test3 = Tile.getHex3Type();

                                System.out.println(test1);
                                System.out.println(test2);
                                System.out.println(test3);

                                Polygon hex1 = null;
                                Polygon hex2 = null;
                                Polygon hex3 = null;
                                o = Integer.parseInt(input4);
                               // System.out.println("made here");
                                boolean tempHex = CheckAdj(x, y, z);
                                if (EndGame == false && tilePlaced==false) {
                                    if (ValidMove(x, y, z) == 1 && CheckVolcano(x, y, z) == 0) {
                                        if (o == 1) {
                                            if (ValidMove(x, y + 1, z - 1) == 1 && ValidMove(x + 1, y, z - 1) == 1) {
                                                int temp1 = CheckTileIndex(x, y, z);
                                                int temp2 = CheckTileIndex(x, y + 1, z - 1);
                                                int temp3 = CheckTileIndex(x + 1, y, z - 1);
                                                boolean nope=false;
                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x) {
                                                        if (IsPlaced[i][2] == y + 1) {
                                                            if (IsPlaced[i][3] == z - 1) {
                                                                        if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5|| IsPlaced[i][5]==6)
                                                                                        nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x+1) {
                                                        if (IsPlaced[i][2] == y) {
                                                            if (IsPlaced[i][3] == z - 1) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                if ((temp1 != temp2 || temp2 != temp3) &&nope==false) {
                                                        tilePlaced=true;
                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test1 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test1 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test1 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test1 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test1 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y + 1) {
                                                                if (IsPlaced[i][3] == z - 1) {
                                                                    if (test2 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test2 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test2 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test2 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test2 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x + 1) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z - 1) {
                                                                    if (test3 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test3 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test3 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test3 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test3 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex++;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    g2d.setColor(Color.green);
                                                    hex1 = FindHex(x, y, z);

                                                    hex2 = FindHex(x, y + 1, z - 1);
                                                    hex3 = FindHex(x + 1, y, z - 1);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);


                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }


                                            } else {
                                                JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }

                                        } else if (o == 2 && CheckVolcano(x, y, z) == 0) {
                                            if (ValidMove(x + 1, y, z - 1) == 1 && ValidMove(x + 1, y - 1, z) == 1) {
                                                tilePlaced=true;
                                                int temp1 = CheckTileIndex(x, y, z);
                                                int temp2 = CheckTileIndex(x + 1, y, z - 1);
                                                int temp3 = CheckTileIndex(x + 1, y - 1, z);
                                                boolean nope=false;

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x+1) {
                                                        if (IsPlaced[i][2] == y) {
                                                            if (IsPlaced[i][3] == z - 1) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x+1) {
                                                        if (IsPlaced[i][2] == y-1) {
                                                            if (IsPlaced[i][3] == z) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3|| IsPlaced[i][5]==5|| IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                if ((temp1 != temp2 || temp2 != temp3)&&nope==false) {
                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test1 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test1 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test1 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test1 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test1 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x + 1) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z - 1) {
                                                                    if (test2 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test2 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test2 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test2 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test2 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x + 1) {
                                                            if (IsPlaced[i][2] == y - 1) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test3 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test3 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test3 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test3 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test3 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex++;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    g2d.setColor(Color.green);
                                                    hex1 = FindHex(x, y, z);

                                                    hex2 = FindHex(x + 1, y, z - 1);
                                                    hex3 = FindHex(x + 1, y - 1, z);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex:", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }
                                        } else if (o == 3 && CheckVolcano(x, y, z) == 0) {
                                            if (ValidMove(x + 1, y - 1, z) == 1 && ValidMove(x, y - 1, z + 1) == 1) {
                                                tilePlaced=true;
                                                int temp1 = CheckTileIndex(x, y, z);
                                                int temp2 = CheckTileIndex(x + 1, y - 1, z);
                                                int temp3 = CheckTileIndex(x, y - 1, z + 1);
                                                boolean nope=false;

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x+1) {
                                                        if (IsPlaced[i][2] == y-1) {
                                                            if (IsPlaced[i][3] == z) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x) {
                                                        if (IsPlaced[i][2] == y-1) {
                                                            if (IsPlaced[i][3] == z+1) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                if ((temp1 != temp2 || temp2 != temp3)&&nope==false) {
                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test1 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test1 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test1 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test1 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test1 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x + 1) {
                                                            if (IsPlaced[i][2] == y - 1) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test2 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test2 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test2 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test2 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test2 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y - 1) {
                                                                if (IsPlaced[i][3] == z + 1) {
                                                                    if (test3 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test3 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test3 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test3 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test3 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex++;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    g2d.setColor(Color.green);
                                                    hex1 = FindHex(x, y, z);

                                                    hex2 = FindHex(x + 1, y - 1, z);
                                                    hex3 = FindHex(x, y - 1, z + 1);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }
                                        } else if (o == 4 && CheckVolcano(x, y, z) == 0) {
                                            if (ValidMove(x, y - 1, z + 1) == 1 && ValidMove(x - 1, y, z + 1) == 1) {
                                                tilePlaced=true;
                                                int temp1 = CheckTileIndex(x, y, z);
                                                int temp2 = CheckTileIndex(x, y - 1, z + 1);
                                                int temp3 = CheckTileIndex(x - 1, y, z + 1);
                                                boolean nope=false;

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x) {
                                                        if (IsPlaced[i][2] == y-1) {
                                                            if (IsPlaced[i][3] == z+1) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x-1) {
                                                        if (IsPlaced[i][2] == y) {
                                                            if (IsPlaced[i][3] == z+1) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                if ((temp1 != temp2 || temp2 != temp3)&& nope==false) {
                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test1 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test1 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test1 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test1 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test1 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y - 1) {
                                                                if (IsPlaced[i][3] == z + 1) {
                                                                    if (test2 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test2 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test2 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test2 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test2 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x - 1) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z + 1) {
                                                                    if (test3 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test3 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test3 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test3 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test3 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex++;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    g2d.setColor(Color.green);
                                                    hex1 = FindHex(x, y, z);

                                                    hex2 = FindHex(x, y - 1, z + 1);
                                                    hex3 = FindHex(x - 1, y, z + 1);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }
                                        } else if (o == 5 && CheckVolcano(x, y, z) == 0) {
                                            if (ValidMove(x - 1, y, z + 1) == 1 && ValidMove(x - 1, y + 1, z) == 1) {
                                                tilePlaced=true;
                                                int temp1 = CheckTileIndex(x, y, z);
                                                int temp2 = CheckTileIndex(x - 1, y, z + 1);
                                                int temp3 = CheckTileIndex(x - 1, y + 1, z);
                                                boolean nope=false;

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x-1) {
                                                        if (IsPlaced[i][2] == y) {
                                                            if (IsPlaced[i][3] == z+1) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x-1) {
                                                        if (IsPlaced[i][2] == y+1) {
                                                            if (IsPlaced[i][3] == z) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                if ((temp1 != temp2 || temp2 != temp3)&&nope==false) {
                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test1 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test1 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test1 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test1 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test1 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x - 1) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z + 1) {
                                                                    if (test2 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test2 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test2 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test2 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test2 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x - 1) {
                                                            if (IsPlaced[i][2] == y + 1) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test3 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test3 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test3 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test3 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test3 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex++;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    g2d.setColor(Color.green);
                                                    hex1 = FindHex(x, y, z);

                                                    hex2 = FindHex(x - 1, y, z + 1);
                                                    hex3 = FindHex(x - 1, y + 1, z);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }
                                        } else if (o == 6 && CheckVolcano(x, y, z) == 0) {
                                            if (ValidMove(x - 1, y + 1, z) == 1 && ValidMove(x, y + 1, z - 1) == 1) {
                                                tilePlaced=true;
                                                int temp1 = CheckTileIndex(x, y, z);
                                                int temp2 = CheckTileIndex(x - 1, y + 1, z);
                                                int temp3 = CheckTileIndex(x, y + 1, z - 1);
                                                boolean nope=false;

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x-1) {
                                                        if (IsPlaced[i][2] == y+1) {
                                                            if (IsPlaced[i][3] == z) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                for(int i=0;i<PlacedCounter;i++)
                                                {
                                                    if (IsPlaced[i][1] == x) {
                                                        if (IsPlaced[i][2] == y+1) {
                                                            if (IsPlaced[i][3] == z-1) {
                                                                if(IsPlaced[i][5]==2 || IsPlaced[i][5]==3 || IsPlaced[i][5]==5 || IsPlaced[i][5]==6)
                                                                    nope=true;
                                                            }
                                                        }
                                                    }
                                                }

                                                if ((temp1 != temp2 || temp2 != temp3) &&nope ==false) {
                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test1 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test1 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test1 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test1 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test1 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x - 1) {
                                                            if (IsPlaced[i][2] == y + 1) {
                                                                if (IsPlaced[i][3] == z) {
                                                                    if (test2 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test2 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test2 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test2 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test2 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    for (int i = 0; i < PlacedCounter; i++) {
                                                        if (IsPlaced[i][1] == x) {
                                                            if (IsPlaced[i][2] == y + 1) {
                                                                if (IsPlaced[i][3] == z - 1) {
                                                                    if (test3 == "volcano")
                                                                        IsPlaced[i][4] = 0;
                                                                    else if (test3 == "rocks")
                                                                        IsPlaced[i][4] = 1;
                                                                    else if (test3 == "jungle")
                                                                        IsPlaced[i][4] = 2;
                                                                    else if (test3 == "lake")
                                                                        IsPlaced[i][4] = 3;
                                                                    else if (test3 == "grassland")
                                                                        IsPlaced[i][4] = 4;
                                                                    IsPlaced[i][5]=0;
                                                                    IsPlaced[i][7] = IsPlaced[i][7] + 1;
                                                                    IsPlaced[i][6] = TileIndex++;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    g2d.setColor(Color.green);
                                                    hex1 = FindHex(x, y, z);

                                                    hex2 = FindHex(x - 1, y + 1, z);
                                                    hex3 = FindHex(x, y + 1, z - 1);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }
                                        }


                                    }
                                    else
                                        {
                                        if (tempHex == true || firstTile == true) {
                                            if (ValidMove(x, y, z) == 0) {
                                                IsPlaced[PlacedCounter][0] = 1;
                                                IsPlaced[PlacedCounter][1] = x;
                                                IsPlaced[PlacedCounter][2] = y;
                                                IsPlaced[PlacedCounter][3] = z;
                                                if (test1 == "volcano")
                                                    IsPlaced[PlacedCounter][4] = 0;
                                                else if (test1 == "rocks")
                                                    IsPlaced[PlacedCounter][4] = 1;
                                                else if (test1 == "jungle")
                                                    IsPlaced[PlacedCounter][4] = 2;
                                                else if (test1 == "lake")
                                                    IsPlaced[PlacedCounter][4] = 3;
                                                else if (test1 == "grassland")
                                                    IsPlaced[PlacedCounter][4] = 4;
                                                IsPlaced[PlacedCounter][7] = 1;
                                                IsPlaced[PlacedCounter][5] = 0;
                                                IsPlaced[PlacedCounter++][6] = TileIndex;
                                                System.out.println( IsPlaced[PlacedCounter-1][4]);


                                                hex1 = FindHex(x, y, z);
                                                adj = true;
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                            button1();
                                        }


                                        if (o == 1) {
                                            if (CheckAdj(x, y + 1, z - 1) == true || CheckAdj(x + 1, y, z - 1) == true || firstTile == true || adj == true) {
                                                if (ValidMove(x, y + 1, z - 1) == 0 && ValidMove(x + 1, y, z - 1) == 0) {
                                                    tilePlaced=true;
                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x;
                                                    IsPlaced[PlacedCounter][2] = y + 1;
                                                    IsPlaced[PlacedCounter][3] = z - 1;
                                                    if (test2 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test2 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test2 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test2 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test2 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);


                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x + 1;
                                                    IsPlaced[PlacedCounter][2] = y;
                                                    IsPlaced[PlacedCounter][3] = z - 1;
                                                    if (test3 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test3 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test3 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test3 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test3 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex++;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);
                                                    g2d.setColor(Color.red);
                                                    hex2 = FindHex(x, y + 1, z - 1);
                                                    hex3 = FindHex(x + 1, y, z - 1);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                    firstTile = false;
                                                    adj = false;
                                                } else {

                                                    SetIsplaced(x, y, z);
                                                    JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }
                                            } else {

                                                JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }

                                        } else if (o == 2) {
                                            if (CheckAdj(x + 1, y, z - 1) == true || CheckAdj(x + 1, y - 1, z) == true || firstTile == true || adj == true) {
                                                if (ValidMove(x + 1, y, z - 1) == 0 && ValidMove(x + 1, y - 1, z) == 0) {
                                                    tilePlaced=true;
                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x + 1;
                                                    IsPlaced[PlacedCounter][2] = y;
                                                    IsPlaced[PlacedCounter][3] = z - 1;
                                                    if (test2 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test2 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test2 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test2 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test2 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);

                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x + 1;
                                                    IsPlaced[PlacedCounter][2] = y - 1;
                                                    IsPlaced[PlacedCounter][3] = z;
                                                    if (test3 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test3 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test3 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test3 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test3 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex++;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);
                                                    g2d.setColor(Color.red);
                                                    hex2 = FindHex(x + 1, y, z - 1);
                                                    hex3 = FindHex(x + 1, y - 1, z);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                    firstTile = false;
                                                    adj = false;
                                                } else {
                                                    SetIsplaced(x, y, z);
                                                    JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }
                                            } else {

                                                JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }

                                        } else if (o == 3) {
                                            if (CheckAdj(x + 1, y - 1, z) == true || CheckAdj(x, y - 1, z + 1) == true || firstTile == true || adj == true) {
                                                if (ValidMove(x + 1, y - 1, z) == 0 && ValidMove(x, y - 1, z + 1) == 0) {
                                                    tilePlaced=true;
                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x;
                                                    IsPlaced[PlacedCounter][2] = y + 1;
                                                    IsPlaced[PlacedCounter][3] = z - 1;
                                                    if (test2 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test2 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test2 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test2 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test2 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);

                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x + 1;
                                                    IsPlaced[PlacedCounter][2] = y;
                                                    IsPlaced[PlacedCounter][3] = z - 1;
                                                    if (test3 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test3 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test3 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test3 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test3 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex++;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);
                                                    g2d.setColor(Color.red);
                                                    hex2 = FindHex(x + 1, y - 1, z);
                                                    hex3 = FindHex(x, y - 1, z + 1);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                    firstTile = false;
                                                    adj = false;
                                                } else {
                                                    SetIsplaced(x, y, z);
                                                    JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }
                                            } else {

                                                JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }
                                        } else if (o == 4) {
                                            if (CheckAdj(x, y - 1, z + 1) == true || CheckAdj(x - 1, y, z + 1) == true || firstTile == true || adj == true) {
                                                if (ValidMove(x, y - 1, z + 1) == 0 && ValidMove(x - 1, y, z + 1) == 0) {
                                                    tilePlaced=true;
                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x;
                                                    IsPlaced[PlacedCounter][2] = y - 1;
                                                    IsPlaced[PlacedCounter][3] = z + 1;
                                                    if (test2 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test2 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test2 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test2 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test2 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);

                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x - 1;
                                                    IsPlaced[PlacedCounter][2] = y;
                                                    IsPlaced[PlacedCounter][3] = z + 1;
                                                    if (test3 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test3 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test3 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test3 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test3 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex++;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);
                                                    g2d.setColor(Color.red);
                                                    hex2 = FindHex(x, y - 1, z + 1);
                                                    hex3 = FindHex(x - 1, y, z + 1);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                    firstTile = false;
                                                    adj = false;
                                                } else {
                                                    SetIsplaced(x, y, z);
                                                    JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }
                                            } else {

                                                JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }

                                        } else if (o == 5) {
                                            if (CheckAdj(x - 1, y, z + 1) == true || CheckAdj(x - 1, y + 1, z) == true || firstTile == true || adj == true) {
                                                if (ValidMove(x - 1, y, z + 1) == 0 && ValidMove(x - 1, y + 1, z) == 0) {
                                                    tilePlaced=true;
                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x - 1;
                                                    IsPlaced[PlacedCounter][2] = y;
                                                    IsPlaced[PlacedCounter][3] = z + 1;
                                                    if (test2 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test2 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test2 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test2 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test2 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);

                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x - 1;
                                                    IsPlaced[PlacedCounter][2] = y + 1;
                                                    IsPlaced[PlacedCounter][3] = z;
                                                    hex2 = FindHex(x - 1, y, z + 1);
                                                    hex3 = FindHex(x - 1, y + 1, z);
                                                    if (test3 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test3 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test3 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test3 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test3 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex++;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);
                                                    g2d.setColor(Color.red);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                    firstTile = false;
                                                    adj = false;
                                                } else {
                                                    SetIsplaced(x, y, z);
                                                    JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }
                                            } else {

                                                JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }

                                        } else if (o == 6) {
                                            if (CheckAdj(x - 1, y + 1, z) == true || CheckAdj(x, y + 1, z - 1) == true || firstTile == true || adj == true) {
                                                if (ValidMove(x - 1, y + 1, z) == 0 && ValidMove(x, y + 1, z - 1) == 0) {
                                                    tilePlaced=true;
                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x - 1;
                                                    IsPlaced[PlacedCounter][2] = y + 1;
                                                    IsPlaced[PlacedCounter][3] = z;
                                                    if (test2 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test2 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test2 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test2 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test2 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex;
                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);


                                                    IsPlaced[PlacedCounter][0] = 1;
                                                    IsPlaced[PlacedCounter][1] = x;
                                                    IsPlaced[PlacedCounter][2] = y + 1;
                                                    IsPlaced[PlacedCounter][3] = z - 1;
                                                    hex2 = FindHex(x - 1, y + 1, z);
                                                    hex3 = FindHex(x, y + 1, z - 1);
                                                    if (test3 == "volcano")
                                                        IsPlaced[PlacedCounter][4] = 0;
                                                    else if (test3 == "rocks")
                                                        IsPlaced[PlacedCounter][4] = 1;
                                                    else if (test3 == "jungle")
                                                        IsPlaced[PlacedCounter][4] = 2;
                                                    else if (test3 == "lake")
                                                        IsPlaced[PlacedCounter][4] = 3;
                                                    else if (test3 == "grassland")
                                                        IsPlaced[PlacedCounter][4] = 4;
                                                    IsPlaced[PlacedCounter][7] = 1;
                                                    IsPlaced[PlacedCounter][5] = 0;
                                                    IsPlaced[PlacedCounter++][6] = TileIndex++;

                                                    System.out.println( IsPlaced[PlacedCounter-1][4]);
                                                    g2d.setColor(Color.red);
                                                    g2d.drawPolygon(hex1);
                                                    g2d.setColor(Color.black);
                                                    g2d.drawPolygon(hex2);
                                                    g2d.drawPolygon(hex3);
                                                    firstTile = false;
                                                    adj = false;
                                                } else {
                                                    SetIsplaced(x, y, z);
                                                    JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                    button1();
                                                }
                                            } else {

                                                JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button1();
                                            }
                                        }

                                    }
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Player turn is completed or &Game is finished", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                                }


                                }
                            catch(NullPointerException e)
                                {
                                    JOptionPane.showMessageDialog(fileMenu, e);
                                }

                            } catch (NullPointerException e) {
                                JOptionPane.showMessageDialog(fileMenu, e);
                            }
                        } catch (NullPointerException e) {
                            JOptionPane.showMessageDialog(fileMenu, e);
                        }
                    } catch (NullPointerException e) {
                        JOptionPane.showMessageDialog(fileMenu, e);
                    }
                    displayBufferedImage(image1);
                }




        } );
        this.getContentPane().add( button, BorderLayout.SOUTH );

    }


    void button2()
    {

        final JButton button1 = new JButton( "Place a new piece" );
        button1.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent event )
            {

                int x,y,z,o;

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
                            String input4 = JOptionPane.showInputDialog("Please give a type of piece (1 for meeple, 2 for totora, 3 for tiger):");
                            try {
                                o = Integer.parseInt(input4);
                                    if(EndGame==false && piecePlaced==false && player1roundsEnd==false && tilePlaced==true)
                                    {
                                        if (o == 1) {
                                            for (int i = 0; i < PlacedCounter; i++) {
                                                if (IsPlaced[i][1] == x) {
                                                    if (IsPlaced[i][2] == y) {
                                                        if (IsPlaced[i][3] == z) {
                                                            if (IsPlaced[i][0] == 1 && IsPlaced[i][5] == 0 && IsPlaced[i][4] != 0 ) {
                                                                IsPlaced[i][5] = 1;
                                                                meeple--;
                                                                player1score=1*IsPlaced[i][7]+player1score;
                                                                piecePlaced=true;
                                                                player1roundsEnd=true;
                                                                player2roundsEnd=false;
                                                                if (meeple == 0) {
                                                                    EndGame = true;
                                                                }
                                                                System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                System.out.println(IsPlaced[i][5]);
                                                                break;
                                                            }
                                                            else
                                                            {
                                                                JOptionPane.showMessageDialog(null, "Something already on the Hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                                button2();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if (o==2) {
                                            SetSettlementSize();
                                            for (int i = 0; i < PlacedCounter; i++) {
                                                if (IsPlaced[i][1] == x) {
                                                    if (IsPlaced[i][2] == y) {
                                                        if (IsPlaced[i][3] == z) {
                                                            if (IsPlaced[i][0] == 1 && IsPlaced[i][5] == 0 &&IsPlaced[i][4] != 0) {

                                                                int check1,check2,check3,check4,check5,check6;
                                                                check1=CheckTotora(x,y+1,z-1);
                                                                check2=CheckTotora(x+1,y,z-1);
                                                                check3=CheckTotora(x+1,y-1,z);
                                                                check4=CheckTotora(x,y-1,z+1);
                                                                check5=CheckTotora(x-1,y,z+1);
                                                                check6=CheckTotora(x-1,y+1,z);
                                                                if(check1>=5)
                                                                {
                                                                    player1score+=200;
                                                                    piecePlaced=true;
                                                                    player1roundsEnd=true;
                                                                    player2roundsEnd=false;
                                                                    IsPlaced[i][5] = 2;
                                                                    totora--;
                                                                    if (totora == 0) {
                                                                        EndGame = true;
                                                                    }
                                                                    System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                    System.out.println(IsPlaced[i][5]);
                                                                    break;
                                                                }
                                                                else if(check2>=5)
                                                                {
                                                                    player1score+=200;
                                                                    piecePlaced=true;
                                                                    player1roundsEnd=true;
                                                                    player2roundsEnd=false;
                                                                    IsPlaced[i][5] = 2;
                                                                    totora--;
                                                                    if (totora == 0) {
                                                                        EndGame = true;
                                                                    }
                                                                    System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                    System.out.println(IsPlaced[i][5]);
                                                                    break;
                                                                }
                                                                else if(check3>=5)
                                                                {
                                                                    player1score+=200;
                                                                    piecePlaced=true;
                                                                    player1roundsEnd=true;
                                                                    player2roundsEnd=false;
                                                                    IsPlaced[i][5] = 2;
                                                                    totora--;
                                                                    if (totora == 0) {
                                                                        EndGame = true;
                                                                    }
                                                                    System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                    System.out.println(IsPlaced[i][5]);
                                                                    break;
                                                                }
                                                                else if(check4 >=5)
                                                                {
                                                                    player1score+=200;
                                                                    piecePlaced=true;
                                                                    player1roundsEnd=true;
                                                                    player2roundsEnd=false;
                                                                    IsPlaced[i][5] = 2;
                                                                    totora--;
                                                                    if (totora == 0) {
                                                                        EndGame = true;
                                                                    }
                                                                    System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                    System.out.println(IsPlaced[i][5]);
                                                                    break;
                                                                }
                                                                else if(check5 >=5)
                                                                {
                                                                    player1score+=200;
                                                                    piecePlaced=true;
                                                                    player1roundsEnd=true;
                                                                    player2roundsEnd=false;
                                                                    IsPlaced[i][5] = 2;
                                                                    totora--;
                                                                    if (totora == 0) {
                                                                        EndGame = true;
                                                                    }
                                                                    System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                    System.out.println(IsPlaced[i][5]);
                                                                    break;
                                                                }
                                                                else if(check6>=5)
                                                                {
                                                                    player1score+=200;
                                                                    piecePlaced=true;
                                                                    player1roundsEnd=true;
                                                                    player2roundsEnd=false;
                                                                    IsPlaced[i][5] = 2;
                                                                    totora--;
                                                                    if (totora == 0) {
                                                                        EndGame = true;
                                                                    }
                                                                    System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                    System.out.println(IsPlaced[i][5]);
                                                                    break;
                                                                }



                                                            }
                                                            else
                                                            {
                                                                JOptionPane.showMessageDialog(null, "Something already on the Hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                                button2();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if (o == 3) {
                                            for (int i = 0; i < PlacedCounter; i++) {
                                                if (IsPlaced[i][1] == x) {
                                                    if (IsPlaced[i][2] == y) {
                                                        if (IsPlaced[i][3] == z) {
                                                            if (IsPlaced[i][0] == 1 && IsPlaced[i][5] == 0 && IsPlaced[i][7] >= 3 &&IsPlaced[i][4] != 0) {
                                                                player1score+=75;
                                                                piecePlaced=true;

                                                                player1roundsEnd=true;
                                                                player2roundsEnd=false;
                                                                IsPlaced[i][5] = 3;
                                                                tiger--;
                                                                if (tiger == 0) {
                                                                    EndGame = true;
                                                                }
                                                                System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                System.out.println(IsPlaced[i][5]);
                                                                break;
                                                            }
                                                            else
                                                            {
                                                                JOptionPane.showMessageDialog(null, "Something already on the Hex or lower than level 3", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                                button2();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if(o==4)
                                        {
                                            String input5 = JOptionPane.showInputDialog("Please given the terrein type if you are trying to expend the settlement (1 for rocks, 2 for jungle, 3 for lake, 4 for grassland:");
                                            try
                                            {

                                                int t=Integer.parseInt(input5);
                                                setSettlementForP1(x,y,z,t);
                                                counter++;
                                                player1roundsEnd=true;
                                                player2roundsEnd=false;


                                            }
                                            catch(NullPointerException e)
                                            {
                                                JOptionPane.showMessageDialog(fileMenu,e);
                                            }


                                        }
                                        else
                                        {
                                            JOptionPane.showMessageDialog(null, "Invalid move", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                            button2();
                                        }
                                    }
                                    else if(EndGame==false && piecePlaced==false && player2roundsEnd==false && tilePlaced==true)
                                    {
                                        {
                                            if (o == 1) {
                                                for (int i = 0; i < PlacedCounter; i++) {
                                                    if (IsPlaced[i][1] == x) {
                                                        if (IsPlaced[i][2] == y) {
                                                            if (IsPlaced[i][3] == z) {
                                                                if (IsPlaced[i][0] == 1 && IsPlaced[i][5] == 0 && IsPlaced[i][4] != 0 ) {
                                                                    IsPlaced[i][5] = 4;
                                                                    player2score=1*IsPlaced[i][7]+player2score;
                                                                    meeple1--;
                                                                    piecePlaced=true;
                                                                    player1roundsEnd=false;
                                                                    player2roundsEnd=true;
                                                                    if (meeple == 0) {
                                                                        EndGame = true;
                                                                    }
                                                                    System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                    System.out.println(IsPlaced[i][5]);
                                                                    break;
                                                                }
                                                                else
                                                                {
                                                                    JOptionPane.showMessageDialog(null, "Something already on the Hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                                    button2();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else if (o==2) {
                                                SetSettlementSize();
                                                for (int i = 0; i < PlacedCounter; i++) {
                                                    if (IsPlaced[i][1] == x) {
                                                        if (IsPlaced[i][2] == y) {
                                                            if (IsPlaced[i][3] == z) {
                                                                if (IsPlaced[i][0] == 1 && IsPlaced[i][5] == 0 &&IsPlaced[i][4] != 0) {
                                                                    int check1,check2,check3,check4,check5,check6;
                                                                    check1=CheckTotora(x,y+1,z-1);
                                                                    check2=CheckTotora(x+1,y,z-1);
                                                                    check3=CheckTotora(x+1,y-1,z);
                                                                    check4=CheckTotora(x,y-1,z+1);
                                                                    check5=CheckTotora(x-1,y,z+1);
                                                                    check6=CheckTotora(x-1,y+1,z);
                                                                    System.out.println(check1);
                                                                    System.out.println(check2);
                                                                    System.out.println(check3);
                                                                    System.out.println(check4);
                                                                    System.out.println(check5);
                                                                    System.out.println(check6);

                                                                    if(check1>=5)
                                                                    {
                                                                        player2score+=200;
                                                                        piecePlaced=true;
                                                                        player1roundsEnd=true;
                                                                        player2roundsEnd=false;
                                                                        IsPlaced[i][5] = 5;
                                                                        totora1--;
                                                                        if (totora == 0) {
                                                                            EndGame = true;
                                                                        }
                                                                        System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                        System.out.println(IsPlaced[i][5]);
                                                                        break;
                                                                    }
                                                                    else if(check2>=5)
                                                                    {
                                                                        player2score+=200;
                                                                        piecePlaced=true;
                                                                        player1roundsEnd=true;
                                                                        player2roundsEnd=false;
                                                                        IsPlaced[i][5] = 5;
                                                                        totora1--;
                                                                        if (totora == 0) {
                                                                            EndGame = true;
                                                                        }
                                                                        System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                        System.out.println(IsPlaced[i][5]);
                                                                        break;
                                                                    }
                                                                    else if(check3>=5)
                                                                    {
                                                                        player2score+=200;
                                                                        piecePlaced=true;
                                                                        player1roundsEnd=true;
                                                                        player2roundsEnd=false;
                                                                        IsPlaced[i][5] = 5;
                                                                        totora1--;
                                                                        if (totora == 0) {
                                                                            EndGame = true;
                                                                        }
                                                                        System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                        System.out.println(IsPlaced[i][5]);
                                                                        break;
                                                                    }
                                                                    else if(check4 >=5)
                                                                    {
                                                                        player2score+=200;
                                                                        piecePlaced=true;
                                                                        player1roundsEnd=true;
                                                                        player2roundsEnd=false;
                                                                        IsPlaced[i][5] = 5;
                                                                        totora1--;
                                                                        if (totora == 0) {
                                                                            EndGame = true;
                                                                        }
                                                                        System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                        System.out.println(IsPlaced[i][5]);
                                                                        break;
                                                                    }
                                                                    else if(check5 >=5)
                                                                    {
                                                                        player2score+=200;
                                                                        piecePlaced=true;
                                                                        player1roundsEnd=true;
                                                                        player2roundsEnd=false;
                                                                        IsPlaced[i][5] = 5;
                                                                        totora1--;
                                                                        if (totora == 0) {
                                                                            EndGame = true;
                                                                        }
                                                                        System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                        System.out.println(IsPlaced[i][5]);
                                                                        break;
                                                                    }
                                                                    else if(check6>=5)
                                                                    {
                                                                        player2score+=200;
                                                                        piecePlaced=true;
                                                                        player1roundsEnd=true;
                                                                        player2roundsEnd=false;
                                                                        IsPlaced[i][5] = 2;
                                                                        totora1--;
                                                                        if (totora == 0) {
                                                                            EndGame = true;
                                                                        }
                                                                        System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                        System.out.println(IsPlaced[i][5]);
                                                                        break;
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    JOptionPane.showMessageDialog(null, "Something already on the Hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                                    button2();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else if (o == 3) {
                                                for (int i = 0; i < PlacedCounter; i++) {
                                                    if (IsPlaced[i][1] == x) {
                                                        if (IsPlaced[i][2] == y) {
                                                            if (IsPlaced[i][3] == z) {
                                                                if (IsPlaced[i][0] == 1 && IsPlaced[i][5] == 0 && IsPlaced[i][7] >= 3 &&IsPlaced[i][4] != 0) {
                                                                            player2score+=75;
                                                                            piecePlaced=true;

                                                                            player1roundsEnd=false;
                                                                            player2roundsEnd=true;
                                                                            IsPlaced[i][5] = 6;
                                                                            tiger1--;
                                                                            if (tiger == 0) {
                                                                                EndGame = true;
                                                                            }
                                                                            System.out.println("1= meeple  2= totora  3= tiger || 0=nothing");
                                                                            System.out.println(IsPlaced[i][5]);
                                                                            break;
                                                                }
                                                                else
                                                                {
                                                                    JOptionPane.showMessageDialog(null, "Something already on the Hex or lower than level 3", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                                    button2();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else if(o==4)
                                            {
                                                String input5 = JOptionPane.showInputDialog("Please given the terrein type if you are trying to expend the settlement (1 for rocks, 2 for jungle, 3 for lake, 4 for grassland:");
                                                try
                                                {

                                                    int t=Integer.parseInt(input5);
                                                    setSettlementForP2(x,y,z,t);
                                                    counter++;
                                                    player1roundsEnd=false;
                                                    player2roundsEnd=true;
                                                }
                                                catch(NullPointerException e)
                                                {
                                                    JOptionPane.showMessageDialog(fileMenu,e);
                                                }


                                            }
                                            else
                                            {
                                                JOptionPane.showMessageDialog(null, "Invalid move", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
                                                button2();
                                            }
                                        }
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null, "player's turn is completed &&Game is finished", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
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
        this.getContentPane().add( button1, BorderLayout.NORTH );
    }

    public void SetIsplaced(int x, int y, int z)
    {
       for(int i=0;i<PlacedCounter;i++)
       {
           if(IsPlaced[i][1]==x)
           {
               if(IsPlaced[i][2]==y)
               {
                   if(IsPlaced[i][3]==z)
                        IsPlaced[i][0]=0;
               }
           }
       }

    }

    public int CheckVolcano(int x,int y, int z)
    {
        for(int i=0;i<PlacedCounter;i++)
        {
            if(IsPlaced[i][1]==x)
            {
                if(IsPlaced[i][2]==y)
                {
                    if(IsPlaced[i][3]==z)
                        return IsPlaced[i][4];
                }
            }
        }
        return 1;
    }

    public int CheckTileIndex(int x, int y, int z)
    {
        for(int i=0;i<PlacedCounter;i++)
        {
            if(IsPlaced[i][1]==x)
            {
                if(IsPlaced[i][2]==y)
                {
                    if(IsPlaced[i][3]==z)
                        return IsPlaced[i][6];
                }
            }
        }
        return 0;
    }

    public int CheckTotora(int x, int y, int z)
    {
        for(int i=0;i<PlacedCounter;i++)
        {
            if(IsPlaced[i][1]==x)
            {
                if(IsPlaced[i][2]==y)
                {
                    if(IsPlaced[i][3]==z)
                    {
                        return IsPlaced[i][8];
                    }
                }
            }
        }
        return 0;
    }

    public void setSettlementForP1(int x, int y, int z, int t)
    {
        boolean check1,check2,check3,check4,check5,check6;
        check1=CheckSettlement(x,y+1,z-1,t);

        for (int i =0 ;i<IsPlaced.length;i++)
        {
            if(IsPlaced[i][1]==x)
            {
                if(IsPlaced[i][2]==y)
                {
                    if(IsPlaced[i][3]==z)
                    {
                        settlementIndex=IsPlaced[i][9];


                    }
                    else settlementIndex=counter;
                }
            }
        }

        if(check1 && ValidMove(x,y+1,z-1)==0)
        {
            for(int i=0;i<PlacedCounter;i++)
            {
                if(IsPlaced[i][1]==x)
                {
                    if(IsPlaced[i][2]==y+1)
                    {
                        if(IsPlaced[i][3]==z-1)
                        {
                            meeple--;
                            player1score=1*IsPlaced[i][7]+player1score;
                            IsPlaced[i][5]=1;
                            IsPlaced[i][9]=settlementIndex;
                            setSettlementForP1(x,y+1,z-1,t);
                        }
                    }
                }
            }
        }
        check2=CheckSettlement(x+1,y,z-1,t);
        if(check2 && ValidMove(x+1,y,z-1)==0)
        {
            for(int i=0;i<PlacedCounter;i++)
            {
                if(IsPlaced[i][1]==x+1)
                {
                    if(IsPlaced[i][2]==y)
                    {
                        if(IsPlaced[i][3]==z-1)
                        {
                            meeple--;
                            player1score=1*IsPlaced[i][7]+player1score;
                            IsPlaced[i][5]=1;
                            IsPlaced[i][9]=settlementIndex;
                            setSettlementForP1(x+1,y,z-1,t);
                        }
                    }
                }
            }
        }
        check3=CheckSettlement(x+1,y-1,z,t);
        if(check3 && ValidMove(x+1,y-1,z)==0)
        {
            for(int i=0;i<PlacedCounter;i++)
            {
                if(IsPlaced[i][1]==x+1)
                {
                    if(IsPlaced[i][2]==y-1)
                    {
                        if(IsPlaced[i][3]==z)
                        {
                            meeple--;
                            player1score=1*IsPlaced[i][7]+player1score;
                            IsPlaced[i][5]=1;
                            IsPlaced[i][9]=settlementIndex;
                            setSettlementForP1(x+1,y,z-1,t);
                        }
                    }
                }
            }
        }
        check4=CheckSettlement(x,y-1,z+1,t);
        if(check4 && ValidMove(x,y-1,z+1)==0)
        {
            for(int i=0;i<PlacedCounter;i++)
            {
                if(IsPlaced[i][1]==x)
                {
                    if(IsPlaced[i][2]==y-1)
                    {
                        if(IsPlaced[i][3]==z+1)
                        {
                            meeple--;
                            player1score=1*IsPlaced[i][7]+player1score;
                            IsPlaced[i][5]=1;
                            IsPlaced[i][9]=settlementIndex;
                            setSettlementForP1(x,y-1,z+1,t);
                        }
                    }
                }
            }
        }
        check5=CheckSettlement(x-1,y,z+1,t);
        if(check5 && ValidMove(x-1,y,z+1)==0)
        {
            for(int i=0;i<PlacedCounter;i++)
            {
                if(IsPlaced[i][1]==x-1)
                {
                    if(IsPlaced[i][2]==y)
                    {
                        if(IsPlaced[i][3]==z+1)
                        {
                            meeple--;
                            player1score=1*IsPlaced[i][7]+player1score;
                            IsPlaced[i][5]=1;
                            IsPlaced[i][9]=settlementIndex;
                            setSettlementForP1(x-1,y,z+1,t);
                        }
                    }
                }
            }
        }
        check6=CheckSettlement(x-1,y+1,z,t);
        if(check6 && ValidMove(x-1,y+1,z)==0)
        {
            for(int i=0;i<PlacedCounter;i++)
            {
                if(IsPlaced[i][1]==x-1)
                {
                    if(IsPlaced[i][2]==y+1)
                    {
                        if(IsPlaced[i][3]==z)
                        {
                            meeple--;
                            player1score=1*IsPlaced[i][7]+player1score;
                            IsPlaced[i][5]=1;
                            IsPlaced[i][9]=settlementIndex;
                            setSettlementForP1(x-1,y+1,z,t);
                        }
                    }
                }
            }
        }
    }

    public void setSettlementForP2(int x,int y,int z,int t)
    {
        {
            boolean check1,check2,check3,check4,check5,check6;
            check1=CheckSettlement(x,y+1,z-1,t);

            for (int i =0 ;i<IsPlaced.length;i++)
            {
                if(IsPlaced[i][1]==x)
                {
                    if(IsPlaced[i][2]==y)
                    {
                        if(IsPlaced[i][3]==z)
                        {
                            settlementIndex=IsPlaced[i][9];


                        }
                        else settlementIndex=counter;
                    }
                }
            }

            if(check1 && ValidMove(x,y+1,z-1)==0)
            {
                for(int i=0;i<PlacedCounter;i++)
                {
                    if(IsPlaced[i][1]==x)
                    {
                        if(IsPlaced[i][2]==y+1)
                        {
                            if(IsPlaced[i][3]==z-1)
                            {
                                meeple1--;
                                player2score=1*IsPlaced[i][7]+player2score;
                                IsPlaced[i][5]=5;
                                IsPlaced[i][9]=settlementIndex;
                                setSettlementForP1(x,y+1,z-1,t);
                            }
                        }
                    }
                }
            }
            check2=CheckSettlement(x+1,y,z-1,t);
            if(check2 && ValidMove(x+1,y,z-1)==0)
            {
                for(int i=0;i<PlacedCounter;i++)
                {
                    if(IsPlaced[i][1]==x+1)
                    {
                        if(IsPlaced[i][2]==y)
                        {
                            if(IsPlaced[i][3]==z-1)
                            {
                                meeple1--;
                                player2score=1*IsPlaced[i][7]+player2score;
                                IsPlaced[i][5]=5;
                                IsPlaced[i][9]=settlementIndex;
                                setSettlementForP1(x+1,y,z-1,t);
                            }
                        }
                    }
                }
            }
            check3=CheckSettlement(x+1,y-1,z,t);
            if(check3 && ValidMove(x+1,y-1,z)==0)
            {
                for(int i=0;i<PlacedCounter;i++)
                {
                    if(IsPlaced[i][1]==x+1)
                    {
                        if(IsPlaced[i][2]==y-1)
                        {
                            if(IsPlaced[i][3]==z)
                            {
                                meeple1--;
                                player2score=1*IsPlaced[i][7]+player2score;
                                IsPlaced[i][5]=5;
                                IsPlaced[i][9]=settlementIndex;
                                setSettlementForP1(x+1,y,z-1,t);
                            }
                        }
                    }
                }
            }
            check4=CheckSettlement(x,y-1,z+1,t);
            if(check4 && ValidMove(x,y-1,z+1)==0)
            {
                for(int i=0;i<PlacedCounter;i++)
                {
                    if(IsPlaced[i][1]==x)
                    {
                        if(IsPlaced[i][2]==y-1)
                        {
                            if(IsPlaced[i][3]==z+1)
                            {
                                meeple1--;
                                player2score=1*IsPlaced[i][7]+player2score;
                                IsPlaced[i][5]=5;
                                IsPlaced[i][9]=settlementIndex;
                                setSettlementForP1(x,y-1,z+1,t);
                            }
                        }
                    }
                }
            }
            check5=CheckSettlement(x-1,y,z+1,t);
            if(check5 && ValidMove(x-1,y,z+1)==0)
            {
                for(int i=0;i<PlacedCounter;i++)
                {
                    if(IsPlaced[i][1]==x-1)
                    {
                        if(IsPlaced[i][2]==y)
                        {
                            if(IsPlaced[i][3]==z+1)
                            {
                                meeple1--;
                                player2score=1*IsPlaced[i][7]+player2score;
                                IsPlaced[i][5]=5;
                                IsPlaced[i][9]=settlementIndex;
                                setSettlementForP1(x-1,y,z+1,t);
                            }
                        }
                    }
                }
            }
            check6=CheckSettlement(x-1,y+1,z,t);
            if(check6 && ValidMove(x-1,y+1,z)==0)
            {
                for(int i=0;i<PlacedCounter;i++)
                {
                    if(IsPlaced[i][1]==x-1)
                    {
                        if(IsPlaced[i][2]==y+1)
                        {
                            if(IsPlaced[i][3]==z)
                            {
                                meeple1--;
                                player2score=1*IsPlaced[i][7]+player2score;
                                IsPlaced[i][5]=5;
                                IsPlaced[i][9]=settlementIndex;
                                setSettlementForP1(x-1,y+1,z,t);
                            }
                        }
                    }
                }
            }
        }
    }

    public void SetSettlementSize()
    {
        int array [] = new int [50];

            for(int j=0;j<IsPlaced.length;j++)
            {
                for(int i=0;i<array.length;i++)
                {
                    if(IsPlaced[j][9]==i && i!=0)
                        array[i]+=1;
                }
            }

        for(int j=0;j<IsPlaced.length;j++)
        {
            for(int i=0;i<array.length;i++)
            {
                if(IsPlaced[j][9]==i)
                    IsPlaced[j][8]=array[i];
            }
        }


    }

    public boolean CheckSettlement (int x, int y, int z, int t )
    {
        int array[]=new int[3];

        for(int i=0;i<PlacedCounter;i++)
        {
            if(IsPlaced[i][1]==x)
            {
                if(IsPlaced[i][2]==y)
                {
                    if(IsPlaced[i][3]==z)
                    {
                        if(IsPlaced[i][4]==t)
                            return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean CheckAdj(int x, int y, int z)
    {



        for(int i=0;i<PlacedCounter;i++)
        {
            if(IsPlaced[i][0]==1) {
                            if(IsPlaced[i][1]==x && IsPlaced[i][2]==y+1 && IsPlaced[i][3]==z-1)
                                return true;
                            else if (IsPlaced[i][1]==x+1 && IsPlaced[i][2]==y && IsPlaced[i][3]==z-1)
                                return true;
                            else if (IsPlaced[i][1]==x+1 && IsPlaced[i][2]==y-1 && IsPlaced[i][3]==z)
                                return true;
                            else if (IsPlaced[i][1]==x && IsPlaced[i][2]==y-1 && IsPlaced[i][3]==z+1)
                                return true;
                            else if (IsPlaced[i][1]==x-1 && IsPlaced[i][2]==y && IsPlaced[i][3]==z+1)
                                return true;
                            else if (IsPlaced[i][1]==x-1 && IsPlaced[i][2]==y+1 && IsPlaced[i][3]==z)
                                return true;
            }
        }

        return false;

    }



    public int [] FindCord(Polygon game)
    {
       //System.out.println("FindCordsa");
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
           // System.out.println("made here #3");
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

            return Game[0];
        }
        else if(x==1&& y==2 && z==-3) //hex #2
        {

            return Game[1];
        }
        else if(x==2&& y==1 && z==-3) //hex #3
        {

            return Game[2];
        }
        else if(x==3&& y==0 && z==-3) //hex #4
        {

            return Game[3];
        }

        else if(x==-1&& y==3 && z==-2) //hex #5
        {

            return Game[4];
        }
        else if(x==0&& y==2 && z==-2) //hex #6
        {

            return Game[5];
        }
        else if(x==1&& y==1 && z==-2) //hex #7
        {

            return Game[6];
        }
        else if(x==2&& y==0 && z==-2) //hex #8
        {

            return Game[7];
        }
        else if(x==3&& y==-1 && z==-2) //hex #9
        {

            return Game[8];
        }

        else if(x==-2&& y==3 && z==-1) //hex #10
        {

            return Game[9];
        }
        else if(x==-1&& y==2 && z==-1) //hex #11
        {

            return Game[10];
        }
        else if(x==0&& y==1 && z==-1) //hex #12
        {

            return Game[11];
        }
        else if(x==1&& y==0 && z==-1) //hex #13
        {

            return Game[12];
        }
        else if(x==2&& y==-1 && z==-1) //hex #14
        {

            return Game[13];
        }
        else if(x==3&& y==-2 && z==-1) //hex #15
        {

            return Game[14];
        }

        else if(x==-3&& y==3 && z==0) //hex #16
        {
            return Game[15];
        }
        else if(x==-2&& y==2 && z==0) //hex #17
        {

            return Game[16];
        }
        else if(x==-1&& y==1 && z==0) //hex #18
        {

            return Game[17];
        }
        else if(x==0&& y==0 && z==0) //hex #19
        {

            return Game[18];

        }
        else if(x==1&& y==-1 && z==0) //hex #20
        {

            return Game[19];
        }
        else if(x==2&& y==-2 && z==0) //hex #21
        {

            return Game[20];
        }
        else if(x==3&& y==-3 && z==0) //hex #22
        {

            return Game[21];
        }

        else if(x==-3&& y==2 && z==1) //hex #23
        {

            return Game[22];
        }
        else if(x==-2&& y==1 && z==1) //hex #24
        {

            return Game[23];
        }
        else if(x==-1&& y==0 && z==1) //hex #25
        {

            return Game[24];
        }
        else if(x==0&& y==-1 && z==1) //hex #26
        {

            return Game[25];
        }
        else if(x==1&& y==-2 && z==1) //hex #27
        {

            return Game[26];
        }
        else if(x==2&& y==-3 && z==1) //hex #28
        {

            return Game[27];
        }

        else if(x==-3&& y==1 && z==2) //hex #29
        {

            return Game[28];
        }
        else if(x==-2&& y==0 && z==2) //hex #30
        {

            return Game[29];
        }
        else if(x==-1&& y==-1 && z==2) //hex #31
        {

            return Game[30];
        }
        else if(x==0&& y==-2 && z==2) //hex #32
        {

            return Game[31];
        }
        else if(x==1&& y==-3 && z==2) //hex #33
        {

            return Game[32];
        }

        else if(x==-3&& y==0 && z==3) //hex #34
        {

            return Game[33];
        }
        else if(x==-2&& y==-1 && z==3) //hex #35
        {

            return Game[34];
        }
        else if(x==-1&& y==-2 && z==3) //hex #36
        {

            return Game[35];
        }
        else if(x==0&& y==-3 && z==3) //hex #37
        {

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

//        for(int i =0; i<IsPlaced.length;i++)
//            IsPlaced[i]=false;

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


    public  void displayBufferedImage(BufferedImage image)
    {

        this.getContentPane().add(new JScrollPane(new JLabel(new ImageIcon(image))),BorderLayout.CENTER);
        this.validate();
    }

}
