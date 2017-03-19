package tigerland;

public class game {

	static int check [][]=new int [100][3];
	static int counter=0;
	static int counter1=0;
	
	public static void main (String [] args)   
	{
		
		
		for (int k=0;k<30;k++)
		{
			inti();
		}
		System.out.println("1 is rocks, 2 is jungle, 3 is lake, 4 is grassland");
		
		 for(int i=0;i<100;i++)
			   System.out.println("index "+ i+" is "+ check[i][0]+"  " +check[i][1]+"   "+check[i][2]);
		 
	}
	
static void inti()
{
	tile test=new tile();
	boolean r=false;
	
	String test1=test.getHex1Type();
	String test2=test.getHex2Type();
	String test3=test.getHex3Type();
	
	if(test1=="volcano")
	{
//		System.out.println("tile 1 is hit");
//		System.out.println("hex2 code " + (test.getHex2Code()));
//		System.out.println("hex3 code " + (test.getHex3Code()));
		
		for (int i=0;i<100;i++)
		{
			if(test.getHex2Code()==check[i][0])
			{
					if(test.getHex3Code()==check[i][1])
					{
						check[i][2]++;
						r=true;
						
						if(check[i][2]>2)
						{
							System.out.println("same tile has been made more than three times " + check[i][2]);
							inti();
							
						}
						System.out.println("repeat: " + check[i][2]);
						break;
					}
			}
		}
		

		if(r==false)
		{
			check[counter][0]=test.getHex2Code();
			check[counter][1]=test.getHex3Code();
			counter++;
		}
		r=false;
	}



	System.out.println("the hex1 is "+ test1);
	System.out.println("the hex2 is "+ test2);
	System.out.println("the hex3 is "+ test3);
	System.out.println("*********************************");
}

  

}
