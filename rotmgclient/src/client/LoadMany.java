package client;

import client.base.GameClient;
import client.base.GameListener;
import client.multibox.MultiBoxListener;
import java.io.IOException;
import java.util.ArrayList;

import data.Location;
import data.ObjectStatusData;

public class LoadMany {
	
	
	//public static ArrayList<GameClient> clients = new ArrayList<GameClient>();
	
	public static void main(String[] a) {
		//Threader thr = new Threader(6);
		System.out.println(System.currentTimeMillis());
		ArrayList<GameClient> clients = new ArrayList<GameClient>();

		/*
		final String name=
		 "100100100111100010011011"
		+"101001010100010101010101"
		+"110001110101100111010101"
		+"101001010100010101010001"
		+"100101010111100101010001";
		*/
		
		String name;
		ArrayList<String> names = new ArrayList<String>();
		int name_index;
		name_index=0;
		
		final String name1=
				 "10111010"
				+"11111110"
				+"01111100"
				+"00011100"
				+"01111110"
				+"10111001"
				+"00111000"
				+"00101000";
		
		final String name2=
				 "11111001"
				+"01111101"
				+"01111101"
				+"01111101"
				+"11111111"
				+"11111101"
				+"01111101"
				+"11111111";
		
		//
		final String name3=
				 "00000000"
				+"00011000"
				+"00110100"
				+"01101010"
				+"01110110"
				+"11111111"
				+"11111111"
				+"00000000";
		final String name4=
		"00111110"
		+"00000101"
		+"00011110"
		+"00101100"
		+"01000100"
		+"11101000"
		+"01110000"
		+"00100000";
		
		final String name5=
		"11111111"
		+"11100111"
		+"11110111"
		+"11000011"
		+"11110111"
		+"11100111"
		+"11111111"
		+"00000000";
		
		//names[3]="";
		names.add(name1);
		names.add(name2);
		names.add(name3);
		names.add(name4);
		names.add(name5);
		
		/*
		final String name=
				 "11110011110011101101100100101011101111"
				+"10001010010001001010101010110010001001"
				+"10110010010001001010101110100011101111"
				+"10001010010001001000101010110010001010"
				+"11110011110001001000101010101011101001";
		*/
		
		
		
		 /*
		 final String name=
				 "1111001111001110"
				+"1000101001000100"
				+"1011001001000100"
				+"1000101001000100"
				+"1111001111000100";
		*/
		 /*
		 final String name=
				  "110110010010101110111" 
				 +"101010101011001000100"
				 +"101010111010001110100"
				 +"100010101011001000100"
				 +"100010101010101110100";
		 */
		 
		 int x,y,count;
		 x=0;
		 y=0;
		 count=0;
		 
		 int inc=0,mode=0,inx=0;
		 
		 name=name1;
		
		//for (int i = 0 ; i < 8 ; i++) {
		 for (int i = 0 ; i < name.length() ; i++) {
			//thr.add(new Runnable() {
				//public void run() {
			//gdasgdshgdsh@gmail.com
			//shdasnksnmsbnnsm@gmail.com
			//sndxhsjjcbbs@gmail.com
			
			//shdjshdjshdhsjd@gmail.com
			//sdshjfhdjoweowe@gmail.com
			//sdikhhvffvsdda@gmail.com
			//System.out.println(i);
			 
			//if (name.charAt(i)=='1')
			{
			  GameClient gCli = new GameClient();
			  clients.add(gCli);
			  gCli.gCon.addListener(new MultiBoxListener(gCli.gCon, gCli));
			  gCli.connectNewChar();	
			  System.out.println("load many");
			  gCli.index=i;
			 // gCli.tpos= new Location((130-32)+x,(140-20)+y); ..out side nexus top
			  
			  //graphics
			  gCli.tpos= new Location((130-15)+x,(140)+y); //middle ish
			  
			//botmker
			  //gCli.tpos= new Location((130-15)+x,(140)+y); //middle ish
			  
			  //gCli.tpos= new Location((130-15)+x,(140+20)+y); //bottom of the nexus nice
			  //gCli.tpos= new Location((130-15)+x,(140+4)+y);
			  //new moving 
			  if (name.charAt(i)=='1')
			  {
				  //gCli.tpos= new Location((130-15)+x,(140+20)+y);   
				  //gCli.tpos= new Location((130)+x,(140-38)+y);   
			  }
			  else
			  {
				  //gCli.tpos= new Location((130-13),(140+18)); 
				  //gCli.tpos= new Location((130),(140-39));   
			  }
			  count+=1;
			}
			
			x+=1;
			//if (x>=22+16) //botmaker
			//if (x>=16)
			//if (x>=24)//kabam
			if (x>=8)//graphic 8*8
			{
			  y+=1;
			  x=0;
			}
			
			/*
			if (i==0)
			{
					GameClient gCli = new GameClient();
					gCli.gCon.addListener(new MultiBoxListener(gCli.gCon, gCli));
					gCli.useCredentials("gdasgdshgdsh@gmail.com","123456");
					gCli.connectLoadChar(1);
					System.out.println("load many");
					gCli.index=i;
			}
			else if (i==1)
			{
				GameClient gCli = new GameClient();
				gCli.gCon.addListener(new MultiBoxListener(gCli.gCon, gCli));
				gCli.useCredentials("shdasnksnmsbnnsm@gmail.com","123456");
				gCli.connectLoadChar(1);
				System.out.println("load many");
				gCli.index=i;
		    }
			else if (i==2)
			{
				GameClient gCli = new GameClient();
				gCli.gCon.addListener(new MultiBoxListener(gCli.gCon, gCli));
				gCli.useCredentials("sndxhsjjcbbs@gmail.com","123456");
				gCli.connectLoadChar(1);
				System.out.println("load many");
				gCli.index=i;
		    }
			else if (i==3)
			{
				GameClient gCli = new GameClient();
				gCli.gCon.addListener(new MultiBoxListener(gCli.gCon, gCli));
				gCli.useCredentials("shdjshdjshdhsjd@gmail.com","123456");
				gCli.connectLoadChar(1);
				System.out.println("load many");
				gCli.index=i;
		    }
			else if (i==4)
			{
				GameClient gCli = new GameClient();
				gCli.gCon.addListener(new MultiBoxListener(gCli.gCon, gCli));
				gCli.useCredentials("sdshjfhdjoweowe@gmail.com","123456");
				gCli.connectLoadChar(1);
				System.out.println("load many");
				gCli.index=i;
		    }
			else if (i==5)
			{
				GameClient gCli = new GameClient();
				gCli.gCon.addListener(new MultiBoxListener(gCli.gCon, gCli));
				gCli.useCredentials("sdikhhvffvsdda@gmail.com","123456");
				gCli.connectLoadChar(1);
				System.out.println("load many");
				gCli.index=i;
		    }
			else
			{
				GameClient gCli = new GameClient();
				gCli.gCon.addListener(new MultiBoxListener(gCli.gCon, gCli));
				gCli.connectNewChar();	
				System.out.println("load many");
				gCli.index=i;
			}
			*/
			//sleep(1000);
				//}
			//});
		}
		
		while (1==1)
		{
		   try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		   
		   
		    //movement code for graphics
		   inc+=1;
		   
		   
		   if (mode==0)
		   {
			 if (inc>=10)
			 {
			   mode=1;
			   inc=0;
			 }
		   }
		   if (mode==1)
		   {
             if (inc>=4)
             {
            	 x=0;
        		 y=0;
        		 count=0;
        		 inc=0;
        		 
        		 mode=2;
        		 name_index+=1;
        		 if (name_index>=5)
        		 {
        			 name_index=0; 
        		 }
        		 name=names.get(name_index);
        		 
            	 for(int i=0;i<64;i+=1)
            	 {
            	  if (name.charAt(i)=='1')
       			  {
            		  //clients.get(i).tpos = new Location((130-15)+x,(140+20)+y);   
            		 // clients.get(i).tpos= new Location((130)+x,(140-38)+y); 
            		  clients.get(i).tpos= new Location((130-15)+x,(140-2)+y); 
            		  
       			  }
       			  else
       			  {
       				 ///clients.get(i).tpos = new Location((130-13),(140+18)); 
       				clients.get(i).tpos = new Location((130-15),(140-2)); 
       			  }
            	  x+=1;
      			  if (x>=8)
      			  {
      			    y+=1;
      			    x=0;
      			  }
            		
            	 } 
             }
		   }
		   if (mode==2)
		   {
             if (inc>=4)
             {
            	mode=1;
            	inc=0;
             }
		   }
		   
		   //end movement code graphics
		   
		   
		   
		   //move ment code for botmaker 
		   /*
		   if (mode==0)
		   {
			 if (inc>=10)
			 {
			   mode=1;
			   inc=0;
			 }
		   }
		   if (mode==1)
		   {
             if (inc>=3)
             {
            	 inc=0;
            	 inx+=1;
            	 for (GameClient gcl : clients) {
            		 gcl.tpos.y-=1;
            		 //gcl.tpos.x+=1;
            	 }
            	 if (inx==8)
            	 {
                   mode=2;
                   inx=0;
            	 }
             }
		   }
		   
		   if (mode==2)
		   {
             if (inc>=3)
             {
            	 inc=0;
            	 inx+=1;
            	 for (GameClient gcl : clients) {
            		 gcl.tpos.y+=1;
            		 //gcl.tpos.x-=1;
            	 }
            	 if (inx==8)
            	 {
                   mode=1;
                   inx=0;
            	 }
             }
		   }
		   
		   inc+=1;
		   //System.out.println(clients.size());
           */
		   //end movement code botmaker 
		    
		}
		
	}
}
