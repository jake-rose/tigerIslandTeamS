package tigerland;
import java.util.Random;

public class hex {
	
private String GF []={"rocks","jungle","lake","grassland"};
private int hexCode;
private String hexType;
boolean isMeeple=false;
boolean isTotora=false;
 
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
	System.out.println((k+1));
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

void setIsMeeple()
{
	isMeeple=true;
}

void setTotora()
{
	isTotora=true;
}

boolean getIsMeeple()
{
	return isMeeple;
}

boolean getIsTotora()
{
	return isTotora;
}

}
