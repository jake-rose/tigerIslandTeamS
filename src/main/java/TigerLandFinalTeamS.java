import com.company.tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
/**
 * Created by caichangzhou on 4/10/17.
 */
public class TigerLandFinalTeamS {



                                                 //     0         1  2  3      4            5                     6       7      8                   9
    private static int IsPlaced [][]=new int [999][10];// [occupition][x][y][z][landscape][player 1&2 piece type][tileIndex][level][settlement size][settlement index]
    private static int PlacedCounter =0;              //    [piece]  ==> 1= meeple  2= totora  3= tiger else 0=nothing for player 1
    private static int TileIndex=0;                   //    [piece]  ==> 4= meeple  5= totora  6= tiger else 0=nothing for player 2
    private static boolean firstTile =true;
    private static boolean adj= false;
    private static int meeple = 20;
    private static int totora = 3;
    private static  int tiger =2;
    private static int meeple1 = 20;
    private static int totora1 = 3;
    private static int tiger1 =2;
    private static boolean tilePlaced=false;
    private static boolean piecePlaced=false;
    private static boolean player1roundsEnd=false;
    private static boolean player2roundsEnd=true;
    private static int player1score=0;
    private static int player2score=0;
    private static int settlementIndex;
    private static int counter=0;
    private static boolean EndGame=false;

    public static void main (String [] args)
    {
        Preload();
        int x, y, z, o;
        String a, b;
do {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Input x, y ,z, o, a, b: ");
    x = scanner.nextInt();
    y = scanner.nextInt();
    z = scanner.nextInt();
    o = scanner.nextInt();
    a = scanner.next();
    b = scanner.next();

    placeTile(x, y, z, o, a, b);
}while(x != 999);

    }

//    public static void AI()
//    {
//        int [] array = new int [3];
//        for(int i=PlacedCounter;;i++)
//        {
//            if(ValidMove(IsPlaced[i][1],IsPlaced[i][2]+1,IsPlaced[i][3]-1)==0)
//            {
//                if(ValidMove(IsPlaced[i][1],IsPlaced[i][2]+2,IsPlaced[i][3]-2)==0 && ValidMove(IsPlaced[i][1]+1,IsPlaced[i][2]+1,IsPlaced[i][3]-2)==0)
//            }
//        }
//
//    }

    private static void Preload()
    {
        IsPlaced[PlacedCounter][0] = 1;
        IsPlaced[PlacedCounter][1] = 0;
        IsPlaced[PlacedCounter][2] = 0;
        IsPlaced[PlacedCounter][3] = 0;
        IsPlaced[PlacedCounter][4] = 0;  //voclano
        IsPlaced[PlacedCounter][5] = 0;
        IsPlaced[PlacedCounter][6] = TileIndex;
        IsPlaced[PlacedCounter++][7] = 1;


        IsPlaced[PlacedCounter][0] = 1;
        IsPlaced[PlacedCounter][1] = 0;
        IsPlaced[PlacedCounter][2] = 1;
        IsPlaced[PlacedCounter][3] = -1;
        IsPlaced[PlacedCounter][4] = 2;  //jungle
        IsPlaced[PlacedCounter][5] = 0;
        IsPlaced[PlacedCounter][6] = TileIndex;
        IsPlaced[PlacedCounter++][7] = 1;



        IsPlaced[PlacedCounter][0] = 1;
        IsPlaced[PlacedCounter][1] = 1;
        IsPlaced[PlacedCounter][2] = 0;
        IsPlaced[PlacedCounter][3] = -1;
        IsPlaced[PlacedCounter][4] = 3;  //lake
        IsPlaced[PlacedCounter][5] = 0;
        IsPlaced[PlacedCounter][6] = TileIndex;
        IsPlaced[PlacedCounter++][7] = 1;



        IsPlaced[PlacedCounter][0] = 1;
        IsPlaced[PlacedCounter][1] = -1;
        IsPlaced[PlacedCounter][2] = 0;
        IsPlaced[PlacedCounter][3] = 1;
        IsPlaced[PlacedCounter][4] = 1;  //rock
        IsPlaced[PlacedCounter][5] = 0;
        IsPlaced[PlacedCounter][6] = TileIndex;
        IsPlaced[PlacedCounter++][7] = 1;



        IsPlaced[PlacedCounter][0] = 1;
        IsPlaced[PlacedCounter][1] = 0;
        IsPlaced[PlacedCounter][2] = -1;
        IsPlaced[PlacedCounter][3] = 1;
        IsPlaced[PlacedCounter][4] = 4;  //rock
        IsPlaced[PlacedCounter][5] = 0;
        IsPlaced[PlacedCounter][6] = TileIndex++;
        IsPlaced[PlacedCounter++][7] = 1;
    }

   private static void placeTile(int x, int y, int z, int o,String t1, String t2)
   {
       {


           String test1 = "volcano";
           String test2 = t1;
           String test3 = t2;

           System.out.println(test1);
           System.out.println(test2);
           System.out.println(test3);

           Polygon hex1 = null;
           Polygon hex2 = null;
           Polygon hex3 = null;

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



                           } else {
                               System.out.println("Please place higher level tile onto two different base tile");
                           }


                       } else {

                           System.out.println("Please place higher level on occupied hex");
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

                           } else {
                               System.out.println("Please place higher level tile onto two different base tile");
                           }

                       } else {

                           System.out.println("Please place higher level on occupied hex");
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


                           } else {
                               JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }

                       } else {
                           JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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


                           } else {
                               JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }

                       } else {
                           JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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


                           } else {
                               JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }

                       } else {
                           JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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


                           } else {
                               JOptionPane.showMessageDialog(null, "Please place higher level tile onto two different base tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }

                       } else {
                           JOptionPane.showMessageDialog(null, "Please place higher level on occupied hex", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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



                           adj = true;
                       } else {
                           JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                       }
                   } else {
                       JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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

                               firstTile = false;
                               adj = false;
                           } else {

                               SetIsplaced(x, y, z);
                               JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }
                       } else {

                           JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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

                               firstTile = false;
                               adj = false;
                           } else {
                               SetIsplaced(x, y, z);
                               JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }
                       } else {

                           JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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

                               firstTile = false;
                               adj = false;
                           } else {
                               SetIsplaced(x, y, z);
                               JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }
                       } else {

                           JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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

                               firstTile = false;
                               adj = false;
                           } else {
                               SetIsplaced(x, y, z);
                               JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }
                       } else {

                           JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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

                               firstTile = false;
                               adj = false;
                           } else {
                               SetIsplaced(x, y, z);
                               JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }
                       } else {

                           JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

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

                               firstTile = false;
                               adj = false;
                           } else {
                               SetIsplaced(x, y, z);
                               JOptionPane.showMessageDialog(null, "Please place hex on empty place", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                           }
                       } else {

                           JOptionPane.showMessageDialog(null, "New tile has to be adjacen of pervious tile", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

                       }
                   }

               }
           }
           else
           {
               JOptionPane.showMessageDialog(null, "Player turn is completed or &Game is finished", "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);

           }


       }
   }

   private static void Endtrun()
   {
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
           System.out.println("Player 1's trun! ");

       }
       else if(player2roundsEnd==false)
       {
           System.out.println("Player 2's trun! ");
       }

       System.out.println("Score for player 1: "+player1score);
       System.out.println("Score for player 2: "+player2score);
   }


    private static int ValidMove(int x, int y, int z)
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


    public static void SetIsplaced(int x, int y, int z)
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

    public static int CheckVolcano(int x,int y, int z)
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

    public static int CheckTileIndex(int x, int y, int z)
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

    public static int CheckTotora(int x, int y, int z)
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

    public static void setSettlementForP1(int x, int y, int z, int t)
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

    public static void setSettlementForP2(int x,int y,int z,int t)
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

    public static void SetSettlementSize()
    {
        int array [] = new int [50];

        for(int j=0;j<IsPlaced.length;j++)
        {
            for(int i=0;i<array.length;i++)
            {
                if(IsPlaced[j][9]==i&& i!=0)
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

    public static boolean CheckSettlement (int x, int y, int z, int t )
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

    public static boolean CheckAdj(int x, int y, int z)
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


    private void  GameBoardInin()
    {

//        for(int i =0; i<IsPlaced.length;i++)
//            IsPlaced[i]=fa




    }
}