package com.company;

/**
 * Created by caichangzhou on 3/22/17.
 */
import java.util.Random;

public class hex {

    private String GF []={"rocks","jungle","lake","grassland"};
    private int hexCode;
    private String hexType;
    boolean isMeeple=false;
    boolean isTotora=false;
    boolean isTiger =false;
    int x, y, z;

    public hex(String inti)
    {
        hexType=inti;
    }

    public hex()
    {

    }


    void hexGenerate()
    {
        Random rand =new Random();
        int k=rand.nextInt(4);
        hexCode=k+1;
       // System.out.println((k+1));
        hexType= GF[k];

    }

    int getHexCode()
    {
        return hexCode;
    }

    String getHexType()
    {
        return hexType;
    }
    void setX(int x)
    {
        this.x=x;
    }
    void setY(int y)
    {
        this.y=y;
    }
    void setZ(int z)
    {
        this.z=z;
    }

    int getX()
    {
        return x;
    }
    int getY()
    {
        return y;
    }
    int getZ()
    {
        return z;
    }

    void setIsMeeple(boolean temp)
    {
        isMeeple=temp;
    }

    void setTotora(boolean temp)
    {
        isTotora=temp;
    }

    void setTiger(boolean temp)
    {
        isTiger=temp;
    }

    boolean getIsMeeple()
    {
        return isMeeple;
    }

    boolean getIsTotora()
    {
        return isTotora;
    }

    boolean getIsTiger()
    {
        return isTiger;
    }

}

