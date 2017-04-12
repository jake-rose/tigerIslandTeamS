package com.company;


/**
 * Created by caichangzhou on 3/22/17.
 */
import java.util.Random;

public class tile {

    String inti="volcano";
    private hex hex1=new hex();
    private hex hex2=new hex();
    private hex hex3=new hex();


    public tile()
    {
        Inti();



    }


    void Inti()
    {
        hex temp=new hex(inti);
        hex1=temp;
        hex2.hexGenerate();
        hex3.hexGenerate();

    }

    hex getHex1()
    {
        return hex1;
    }

    hex getHex2()
    {
        return hex2;
    }

    hex getHex3()
    {
        return hex3;
    }

    String getHex1Type()
    {
        String temp=hex1.getHexType();
        return temp;
    }

    String getHex2Type()
    {
        String temp=hex2.getHexType();
        return temp;
    }

    String getHex3Type()
    {
        String temp=hex3.getHexType();
        return temp;
    }

    int getHex1Code()
    {
        return hex1.getHexCode();
    }

    int getHex2Code()
    {
        return hex2.getHexCode();
    }

    int getHex3Code()
    {
        return hex3.getHexCode();
    }

}

