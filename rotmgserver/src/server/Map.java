package server;


import java.math.BigDecimal;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;

import packets.client.PLAYERSHOOT_Packet;
import packets.client.PLAYERTEXT_Packet;
import packets.server.ALLYSHOOT_Packet;
import packets.server.GOTO_Packet;
import packets.server.MAPINFO_Packet;
import packets.server.NEW_TICK_Packet;
import packets.server.TEXT_Packet;
import packets.server.UPDATE_Packet;
import data.Location;
import data.ObjectStatus;
import data.ObjectStatusData;
import data.SpawnList;
import data.StatData;
import data.Tile;
import data.WorldData;
import data.WorldDataObject;

public class Map extends Thread {
	public int width;
	public int height;
	private String name;
	private int fp_;
	private int background;
	private boolean allowPlayerTeleport;
	private boolean showDisplays;
	int[][] mapData;
	public ArrayList<SpawnList> spawnlist = new ArrayList<SpawnList>();
	public ArrayList<SpawnList> givelist = new ArrayList<SpawnList>();
	public ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
	public ArrayList<ObjectStatus> objects = new ArrayList<ObjectStatus>();
	public ArrayList<ObjectStatus> drops = new ArrayList<ObjectStatus>();
	public ArrayList<ObjectStatus> enemys = new ArrayList<ObjectStatus>();

	public AtomicInteger objectIdCounter = new AtomicInteger();
	public AtomicInteger bulletIdCounter = new AtomicInteger();
	public ArrayList<ObjectStatus> lootbags = new ArrayList<ObjectStatus>();
	public int notdone=0;
	public float oldx=0,oldy=0;
	
	
	//xml files
	NodeList groundList;
	NodeList objectList;
	NodeList regionList;
	
	

//	 public static void main(String[] a) throws FileNotFoundException {
//		 System.out.println(new Map("Nexus"));
//	 }

	public Map(String string) throws SAXException, IOException {
		File fXmlFile = new File("maps/ground.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    Document doc = dBuilder.parse(fXmlFile);
		
		
		doc.getDocumentElement().normalize();
		 
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	 
		groundList = doc.getElementsByTagName("Ground");
		
		fXmlFile = new File("maps/objects.xml");
	    doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
	    
	    //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    objectList = doc.getElementsByTagName("Object");
	    
	    
	    fXmlFile = new File("maps/regions.xml");
	    doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
	    
	    //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    regionList = doc.getElementsByTagName("Region");
		
		/*
		for (int temp = 0; temp < objectList.getLength(); temp++) {
			 
			Node nNode = objectList.item(temp);
	 
			//System.out.println("\nCurrent Element :" + nNode.getNodeName());
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element eElement = (Element) nNode;
	 
				System.out.println("type id : " + eElement.getAttribute("type"));
				System.out.println("name : " + eElement.getAttribute("id"));
	 
			}
		}
		*/
		
		
		loadTiles("maps/tile - " + string);
		loadObjects("maps/objects - " + string);
		loadSpawnList("maps/spawnlist.txt");
		loadGiveList("maps/givelist.txt");
		
//		FileOutputStream fos = new FileOutputStream("maps/Nexusobjfix");
//		ArrayList<ObjectStatus> done = new ArrayList<ObjectStatus>();
//		for (ObjectStatus os1 : objects) {
//			boolean brea = false;
//			for (ObjectStatus os2 : done) {
//				if (os1 == os2) {
//					brea = true;
//					continue;
//				}
//				if (os1.getPosition().equals(os2.getPosition())) {
//					System.out.println(os1 + " == " + os2);
//					if (os1.objectType == os2.objectType)
//						brea = true;
//					continue;
//				}
//			}
//			if (brea)
//				continue;
//			done.add(os1);
//			try {
//				fos.write((os1.getPosition().x + " " + os1.getPosition().y + " " + os1.objectType + " " + os1.objStatData.statData + "\n").getBytes());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	
	private void loadSpawnList(String string) throws FileNotFoundException {
		Scanner s = new Scanner(new FileInputStream(string));
		String l = "";

		while (s.hasNextLine()) {
			l = s.nextLine();
			String[] temp = l.split(":");
			SpawnList spl = new SpawnList("",0);
			spl.name=temp[1];
			spl.objectid=Integer.parseInt(temp[0]);
			spawnlist.add(spl);
			//System.out.println(temp[1]);
		}
	}
	
	private void loadGiveList(String string) throws FileNotFoundException {
		Scanner s = new Scanner(new FileInputStream(string));
		String l = "";
		System.out.println("loading");
		while (s.hasNextLine()) {
			l = s.nextLine();
			String[] temp = l.split(":");
			SpawnList spl = new SpawnList("",0);
			spl.name=temp[1];
			spl.objectid=Integer.parseInt(temp[0]);
		    givelist.add(spl);
			//System.out.println(temp[1]);
		}
	}
	
	public void loadEditor(WorldData obj,String data, ClientHandler ch) throws FileNotFoundException 
	{
      
      this.width=obj.width;
      this.height=obj.height;
      this.mapData = new int[width][height];
      
      objects.clear();
      drops.clear();
      enemys.clear();
      ch.clearlist();
		
      byte [] data2;
	  data2=data.getBytes();
      int i,xx=0,yy=0,index;
      String objid;
      WorldDataObject wdo;
        
        for(i=0;i<data2.length;i+=2)
        {
          
           index=(short) ((data2[i] << 8) + (data2[i+1] << 0));
           //System.out.println(index);
           //System.out.println(obj.dict.get(index));
           wdo=obj.dict.get(index);
           
           if (wdo.ground!=null)
           {
        	  for (int temp = 0; temp < groundList.getLength(); temp++)
   		      {
   			    Node nNode = groundList.item(temp);
   			    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
   			    {
   	 
   				  Element eElement = (Element) nNode;
   	              if  ((eElement.getAttribute("id").equals(wdo.ground)) )
   	              {
   	            	mapData[xx][yy]=Integer.parseInt( eElement.getAttribute("type").substring(2) ,16);
   	            	break;
   	              }
   	 
   			    }
   		      }
           }
           
           if (wdo.objs!=null)
 		   {
             for (int temp = 0; temp < objectList.getLength(); temp++)
 			 {
 			   Node nNode = objectList.item(temp);
 			   if (nNode.getNodeType() == Node.ELEMENT_NODE) 
 			   {
 		         Element eElement = (Element) nNode;
 			     //System.out.println(wdo.objs.get(0).toString().substring(4,wdo.objs.get(0).toString().length()-1 ));
 		         //System.out.println("1" + eElement.getAttribute("id"));
 			     objid=wdo.objs.get(0).toString().substring(4,wdo.objs.get(0).toString().length()-1 );
 			     //System.out.println("2" + objid);
 		         if  (eElement.getAttribute("id").equals(objid) )
 		         {
 		        	//System.out.println("found");
 		            ObjectStatus os = new ObjectStatus();
 					os.objectType = Integer.parseInt( eElement.getAttribute("type").substring(2) ,16);;
 					os.objStatData.objectId = objectIdCounter.getAndIncrement();
 					//os.objStatData.position = new Location(xx, yy);
 					os.objStatData.position = new Location((float)(xx+0.5), (float)(yy+0.5));//Due to centering of the tile!
 					this.objects.add(os);
 					
 					ch.map_ediotr_create_enemy(os);
 					
 					//enemys.add(os);
 					//break;
 		         }
 		        }
 			  } 
 		    }
           
           
           if (wdo.regions!=null)
 		   {
             for (int temp = 0; temp < regionList.getLength(); temp++)
 			 {
 			   Node nNode = regionList.item(temp);
 			   if (nNode.getNodeType() == Node.ELEMENT_NODE) 
 			   {
 		         Element eElement = (Element) nNode;
 			     System.out.println(wdo.regions.get(0).toString().substring(4,wdo.regions.get(0).toString().length()-1 ));
 			     objid=wdo.regions.get(0).toString().substring(4,wdo.regions.get(0).toString().length()-1 );
 		         if  (eElement.getAttribute("id").equals(objid) )
 		         {
 		           if (objid.equals("Spawn")==true)
 		           {
 		        	 ch.editorx=xx;
 		        	 ch.editory=yy;
 		        	 break;
 		           }
 		         }
 		        }
 			  } 
 		    }
           
           xx+=1;
   	       if (xx>=width)
   	       {
   		     yy+=1;
   		     xx=0;
   	       }
        }
		
      
	}

	private void loadTiles(String string) throws FileNotFoundException {
		Scanner s = new Scanner(new FileInputStream(string));
		String l = s.nextLine();
		String[] mapInfo = l.split(",");
		this.width = Integer.parseInt(mapInfo[0]);
		this.height = Integer.parseInt(mapInfo[1]);
		this.name = mapInfo[2];
		this.fp_ = Integer.parseInt(mapInfo[3]);
		this.background = Integer.parseInt(mapInfo[4]);
		this.allowPlayerTeleport = Boolean.parseBoolean(mapInfo[5]);
		this.showDisplays = Boolean.parseBoolean(mapInfo[6]);

		this.mapData = new int[width][height];

		while (s.hasNextLine()) {
			l = s.nextLine();
			String[] tileInfo = l.split(" ");
			int x = Integer.parseInt(tileInfo[0]), y = Integer.parseInt(tileInfo[1]), type = Integer.parseInt(tileInfo[2]);
			if (this.mapData[x][y] != 0) {
				System.err.println("this.mapData[" + x + "][" + y + "] already set to " + this.mapData[x][y] + " setting to " + type + ".");
			}
			this.mapData[x][y] = type;
			//System.out.println("this.mapData[" + x + "][" + y + "] = " + type);
		}
	}

	private void loadObjects(String string) throws FileNotFoundException {
		
		Scanner s = new Scanner(new FileInputStream(string));
		String l;
        
		while (s.hasNextLine()) {
			l = s.nextLine();
			String[] objectInfo = l.split(" ", 4);
			float x = Float.parseFloat(objectInfo[0]), y = Float.parseFloat(objectInfo[1]); 
			int type = Integer.parseInt(objectInfo[2]);
//			if (type == 456)
//				continue;
			ObjectStatus os = new ObjectStatus();
			os.objectType = type;
			os.objStatData.objectId = objectIdCounter.getAndIncrement();
			os.objStatData.position = new Location(x, y);
			String[] stats = objectInfo[3].substring(1).split("statdata \\[.*?, .*?\\], ");
			for (String stat : stats) {
				if (stat.length() == 0)
					continue;
				//System.out.println(stat);
				String[] sst = stat.substring(10).split(", ");
				int num = Integer.parseInt(sst[0]);
				StatData sd = new StatData(num, -1);
				if (num != StatData.NAME && num != StatData.GUILD) {
					sd.numValue = Integer.parseInt(sst[1].split("\\]")[0]);
				} else {
					sd.stringValue = sst[1].split("\\]")[0];
				}
				os.objStatData.statData.add(sd);
			}
			//System.out.println(os.objectType);
			if ( os.objectType==456)
			{}
			else if ( os.objectType==256){}
			else if ( os.objectType==478){}
			else if ( os.objectType==273){}
			//else if ( os.objectType==453){} //wall
			//else if ( os.objectType==474){}
			//else if ( os.objectType==476){}
			//else if ( os.objectType==274){}
			//else if ( os.objectType==479){}
			else
			{
			  this.objects.add(os);
			  //System.out.println(os.objectType);
			}
			//System.out.println("this.mapData[" + x + "][" + y + "] = " + type);
		}
		
	}

	@Override
	public String toString() {
		return "Map [width=" + width + ", height=" + height + ", name=" + name + ", fp_=" + fp_ + ", background=" + background + ", allowPlayerTeleport=" + allowPlayerTeleport + ", showDisplays=" + showDisplays + "]";
	}

	public MAPINFO_Packet getMapInfo() {
		MAPINFO_Packet mp = new MAPINFO_Packet();
		mp.width_ = this.width;
		mp.height_ = this.height;
		mp.name_ = this.name;
		mp.fp_ = this.fp_;
		mp.background_ = this.background;
		mp.allowPlayerTeleport_ = this.allowPlayerTeleport;
		mp.showDisplays_ = this.showDisplays;
		mp.extraXML_ = new ArrayList<String>();
		mp.clientXML_ = new ArrayList<String>();
		
		return mp;
	}

	public UPDATE_Packet getInitialUpdate() {
		UPDATE_Packet up = new UPDATE_Packet();
		for (ClientHandler ch : players) {
			up.newObjs_.add(ch.getStatus());
		}
		return up;
	}

	public UPDATE_Packet update(ClientHandler ch) {
		//if (ch.revealed == null) {
		//	return null;
		//}

	    
		UPDATE_Packet up = new UPDATE_Packet();
		final int revealRadius = 20;
		Location pos = ch.getStatus().objStatData.position;
		
		if (ch == null || mapData == null || ch.revealed == null) {
			System.out.println(ch + " " + mapData);
			System.out.println(ch.revealed);
		}
		
		/*
		if (enemys.size()==1)
		{
			
			enemys.get(0).objStatData.position=ch.status.objStatData.position;
			up.newObjs_.add(enemys.get(0));
			System.out.println(enemys.size());
		}
		*/
		
		synchronized(objects)
		{
		
		for (ObjectStatus os : objects) {
			if (ch.distanceToSq(os.getPosition()) < 80) {
				if (!ch.revealedObjects.contains(os.objStatData.objectId)) {
					up.newObjs_.add(os);
					ch.revealedObjects.add(os.objStatData.objectId);
				}
			} else if (ch.revealedObjects.contains(os.objStatData.objectId)) {
				up.drops_.add(os.objStatData.objectId);
				ch.revealedObjects.remove((Integer) os.objStatData.objectId);
			}
		}
		//notdone=1;
		//}
		}
		
		//glitched forever exspanding drops list
		
		for (ObjectStatus os : drops) { //deletedObjects never removes the objectid
			if (!ch.deletedObjects.contains(os.objStatData.objectId)) {
              up.drops_.add(os.objStatData.objectId);
              ch.deletedObjects.add(os.objStatData.objectId);
			}
           //need a new method 
		 // to check when all clients have sent the delete object update
		 // then remove from drops
		}
		
		
		/*
		for (ObjectStatus os : lootbags) { //deletedObjects never removes the objectid
			if (!ch.lootbagObjects.contains(os.objStatData.objectId)) {
              //up.newObjs_.add(os);
              ch.deletedObjects.add(os.objStatData.objectId);
			}
           //need a new method 
		 // to check when all clients have sent the delete object update
		 // then remove from drops
		}
		*/
		
		//System.out.println(pos);
		
		for (int x = (int) (pos.x - revealRadius < 0 ? 0 : pos.x - revealRadius); x < (pos.x + revealRadius > width - 1 ? width - 1 : pos.x + revealRadius); x++) {
			for (int y = (int) (pos.y - revealRadius < 0 ? 0 : pos.y - revealRadius); y < (pos.y + revealRadius > height - 1 ? height - 1 : pos.y + revealRadius); y++) {
				if (mapData[x][y] != 0 && !ch.revealed[x][y]) {
					ch.revealed[x][y] = true;
					// System.out.println("this.mapData[" + x + "][" + y + "] = " + mapData[x][y]);
					up.tiles_.add(new Tile(x, y, mapData[x][y]));
				}
			}
		}
		
		//System.out.println(drops.size());
		if (up.drops_.size() == 0 && up.newObjs_.size() == 0 && up.tiles_.size() == 0)
			return null;
		return up;
		
	}

	public void addPlayer(ClientHandler clientHandler) {
		clientHandler.revealed = new boolean[width][height];
		UPDATE_Packet up = new UPDATE_Packet();
		up.newObjs_.add(clientHandler.getStatus());
		for (ClientHandler p : players) {
			p.sendQueue.add(up);
		}
		ArrayList<ClientHandler> pl = (ArrayList<ClientHandler>) players.clone();
		pl.add(clientHandler);
		players = pl;
		// TODO add notification ---
	}

	private long lastTick = 0;

	public void run() {
		while (true) {
			long start = System.currentTimeMillis();
			int timeSinceLastTick = (int) (start - lastTick);

			for (ClientHandler ch : players) {
				
				NEW_TICK_Packet ntp = new NEW_TICK_Packet();
				ntp.tickId_ = ch.getNextTickNum();
				ntp.tickTime_ = timeSinceLastTick;
				ntp.statuses_ = new ArrayList<ObjectStatusData>();
				
				for (ClientHandler p : players) {
					// TODO send select updates about current player
					//if (p != ch) {
						// TODO add statues ---
					    //p.getStatus().objStatData.SLOT_1= 0xC03;
					    //p.status.objStatData.
					    //System.out.println(p.status.objStatData.statData.size());
					    if (p._changedSinceLastTick==1)
					    {
						ntp.statuses_.add(p.getStatus().objStatData);
					    }
						
						if (p.inv_updated==1)
						{
						  ntp.statuses_.add(p.inv_status.objStatData);
						}
						
						if (p.pet!=null && p._movedSinceLastTick==1)
						{
						  ntp.statuses_.add(p.pet.objStatData);
						  p.pet.objStatData.position =p.getStatus().objStatData.position;
						}
					//}
				}
				
				
				ClientHandler np;
				
				synchronized(enemys)
				{
				  for (ObjectStatus os : enemys) {
					Location pos = new Location(0,0);
					Location pos2 = new Location(0,0);
				    int angle=0;
					pos=os.objStatData.position; //sheep
					//os.objStatData.position = new Location(os.objStatData.position+1)
					np=player_nearest(pos.x,pos.y);
					pos2=np.status.objStatData.position;
					angle = point_direction(pos2.x,pos2.y,pos.x,pos.y);
					angle = point_direction(pos.x,pos.y,pos2.x,pos2.y);
					//angle2=angleFrom(pos.x,pos.y,pos2.x,pos2.y);
					//angle=toRotmgAngle((float)(angle*Math.PI/180));
				    //angle=180;
					//System.out.println(angle);
					//System.out.println(angle2);
					//angle2=toRotmgAngle(angle);
					//pos.x+=(0.0002*Math.cos(angle2));
					//pos.y+=(0.0002*Math.sin(angle2));
					
					//float number = 123.123456F;
					 //BigDecimal numberBigDecimal = new BigDecimal(number);
					 //System.out.println(numberBigDecimal);
					 //numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
					 //System.out.println(numberBigDecimal);
					/*
					float tempx=(float)(0.2*Math.cos((angle*Math.PI/180)));
					float tempy=(float)(0.2*Math.sin((angle*Math.PI/180)));
					pos.x+=tempx;
					pos.y+=tempy;
					BigDecimal numberBigDecimal = new BigDecimal(pos.x);
					numberBigDecimal  = numberBigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal numberBigDecimal2 = new BigDecimal(pos.y);
					numberBigDecimal2  = numberBigDecimal2 .setScale(2, BigDecimal.ROUND_HALF_UP);
					*/
					
					//pos.x=Float.parseFloat(numberBigDecimal.toString());
					//pos.y=Float.parseFloat(numberBigDecimal2.toString());
					pos.x+= -(1.20*Math.cos((angle*Math.PI/180))) ;
					pos.y+=-(1.20*Math.sin((angle*Math.PI/180)));
					
					//pos=ch.status.objStatData.position;
					//ch.status.objStatData.position.y=pos.y;
					
					//if (pos.x!=oldx || pos.y!=oldy)
					//{
					//pos.x=(float) 133.94;
					//pos.y=(float) 139.06;
					
					//System.out.println(pos.x +" "+pos.y);
					//System.out.println(pos2.x +" "+pos2.y);
					//oldx=pos.x;
					//oldy=pos.y;
					os.objStatData.position=pos;
					ntp.statuses_.add(os.objStatData);
					//}
					//float fx = Float.intBitsToFloat(tempx&0x7FFFFFFF);
					//float fy = Float.intBitsToFloat(tempy&0x7FFFFFFF);
					//pos.x+= (0.2*Math.cos((angle*Math.PI/180))) ;
					//pos.y+=(0.2*Math.sin((angle*Math.PI/180)));
					
					//System.out.println(numberBigDecimal);
					
					
					
					
				    //os.objStatData.position = new Location(os.objStatData.position.x+1,os.objStatData.position.y+1);
					
			        
				  }
			
				}
				
		        
				
				
				if (ch.getCurrentTick() % 5 == 0) {
					ch.sendPing(); // also checks for missed pings
				}
				ch.sendQueue.add(ntp);
				//ch.newTick((int) (start-lastTick));
				
			}
			
			for (ClientHandler p : players) {
				p.inv_updated=0;
				p._movedSinceLastTick=0;
				p._changedSinceLastTick =0;
			}
			
			
			
			lastTick = System.currentTimeMillis();
			long end = System.currentTimeMillis();
			//System.out.println("Done in " + ((int) (end-start)) + " | " + (50 - (int) (end-start)));
			int time = (int) (end - start);
			//System.out.println(timeSinceLastTick);
			
			
			try {
				sleep(100 - (time > 100 ? 0 : time));
				//sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	float angleFrom(float APositionX, float APositionY, float BPositionX, float BPositionY)
	{
	    float dx = BPositionX - APositionY;
	    float dy = BPositionY - APositionY;

	    return (float)Math.atan2(dy, dx);
	}
	
	public  float toRotmgAngle(float angle) {
		return (float) ((angle - 90) / (360d / Math.PI / 2));
	}
	
	int point_direction(float x1,float y1 , float x2, float y2)
	{
		float angle, x, y;
	    x = x1-x2;
	    y = y1-y2;
	    if (y!=0) 
	    { 
	      angle = 90-(float) -(Math.atan(x/y) * 180 / Math.PI);
	    }
	    else 
	    {
	      if (x1>x2)
	      {
	    	 angle=180;
	      }
	      else if (x1<x2)
	      {
            angle=0;
	      }
	      else
	      {
	         angle=0;
	      }
	    }
	    
	    if (y>0)
	    {
	      angle-=180;
	    }
	    
	    if (angle>0)
	    {
	    	angle-=360;
	    }
	    return (int)-angle;
	}
	
	public float point_distance(float x1,float y1, float x2, float y2)
	{
	    float x, y;
	    x = (float) Math.pow(Math.floor(x1) - Math.floor(x2), 2);
	    y = (float) Math.pow(Math.floor(y1) - Math.floor(y2), 2);
	    return (float) Math.sqrt(x + y);
	}
	
	public ClientHandler player_nearest(float x, float y)
	{
		float dist=1000000; //no map can be bigger then 65536
		ClientHandler p=null;
		for (ClientHandler ch : players) 
		{
			if (point_distance(ch.status.objStatData.position.x,ch.status.objStatData.position.x, x,y)<dist )
			{
			  dist=	point_distance(ch.status.objStatData.position.x,ch.status.objStatData.position.x, x,y);
			  p=ch;
			}
			
		}
		return p;
	}

	public void sendShot(PLAYERSHOOT_Packet ps, ClientHandler clientHandler) {
		ALLYSHOOT_Packet asp = new ALLYSHOOT_Packet();
		asp.angle_ = ps.angle_;
		asp.containerType_ = ps.containerType_;
		asp.ownerId_ = clientHandler.getStatus().objStatData.objectId;
		//System.out.println("Ownder: " + asp.ownerId_);
		asp.bulletId_ = getNextBulletId();

		for (ClientHandler ch : players) {
			if (ch != clientHandler)
				ch.sendQueue.add(asp);
		}
	}

	public int getNextBulletId() {
		if (bulletIdCounter.getAndIncrement() > 253)
			bulletIdCounter.set(0);
		return bulletIdCounter.get();
	}

	public void disconnect(ClientHandler ch) {
		ArrayList<ClientHandler> pl = (ArrayList<ClientHandler>) players.clone();
		pl.remove(ch);
		players = pl;
		UPDATE_Packet up = new UPDATE_Packet();
		up.drops_.add(ch.getStatus().objStatData.objectId);
		for (ClientHandler p : pl) {
			p.sendQueue.add(up);
		}
	}

	public void teleport(ClientHandler ch, int objectId_) {
		GOTO_Packet gp = new GOTO_Packet();
		gp.objectId_ = ch.getStatus().objStatData.objectId;
		for (ClientHandler c : players) {
			if (c.getStatus().objStatData.objectId == objectId_) {
				gp.pos_ = c.getStatus().getPosition().clone();
				break;
			}
		}
		if (gp.pos_ == null) {
			return;
		}
		for (ClientHandler c : players) {
			c.sendQueue.add(gp);
		}
	}

	public void sendText(ClientHandler ch, PLAYERTEXT_Packet ptp) {
		TEXT_Packet tp = new TEXT_Packet();
		tp.bubbleTime_ = 4;
		tp.name_ =  "BotMaker";//ch.getStatus().objStatData.statData.get(StatData.NAME).stringValue; //edited "NAMEph";
		tp.numStars_ = 1;
		tp.recipient_ = "";
		tp.text_ = ptp.text_;
		tp.objectId_ = ch.getStatus().objStatData.objectId;
		tp.cleanText_ = "";
		for (ClientHandler c : players) {
			c.sendQueue.add(tp);
		}
	}
	
	public void sendNoPermission(ClientHandler ch) {
		TEXT_Packet tp = new TEXT_Packet();
		tp.bubbleTime_ = 0;
		tp.name_ =  "";//ch.getStatus().objStatData.statData.get(StatData.NAME).stringValue; //edited "NAMEph";
		tp.numStars_ = 0;
		tp.recipient_ = "";
		tp.text_ = "No Permission";
		tp.objectId_ = ch.getStatus().objStatData.objectId;
		tp.cleanText_ = "";
		ch.sendQueue.add(tp);
	}
}
