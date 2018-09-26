package server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import javax.xml.bind.DatatypeConverter;

import data.Location;
import data.ObjectStatus;
import data.ObjectStatusData;
import data.SpawnList;
import data.StatData;
import data.MyBase64;
import data.WorldData;
import data.WorldDataObject;

import util.Angles;


import packets.ByteArrayDataInput;
import packets.ByteArrayDataOutput;
import packets.Packets;
import packets.RWable;
import packets.client.CREATE_Packet;
import packets.client.ENEMYHIT_Packet;
import packets.client.ESCAPE_Packet;
import packets.client.HELLO_Packet;
import packets.client.INVDROP_Packet;
import packets.client.INVSWAP_Packet;
import packets.client.LOAD_Packet;
import packets.client.MOVE_Packet;
import packets.client.PLAYERSHOOT_Packet;
import packets.client.PLAYERTEXT_Packet;
import packets.client.PONG_Packet;
import packets.client.TELEPORT_Packet;
import packets.client.USEITEM_Packet;
import packets.server.ACCOUNTLIST_Packet;
import packets.server.ALLYSHOOT_Packet;
import packets.server.CREATE_SUCCESS_Packet;
import packets.server.GOTO_Packet;
import packets.server.INVRESULT_Packet;
import packets.server.NEW_TICK_Packet;
import packets.server.PING_Packet;
import packets.server.SHOOTMULTI_Packet;
import packets.server.SHOOT_Packet;
import packets.server.UPDATE_Packet;

import net.clarenceho.crypto.RC4;
import com.google.gson.Gson;

public class ClientHandler extends Thread {
	private Socket s;
	public RC4 cipher_fromClient;
	public RC4 cipher_toClient;
	private DataInputStream in;
	private DataOutputStream out;
	private ROTMGServer server;
	public ArrayList<RWable> sendQueue = new ArrayList<RWable>();
	public boolean[][] revealed;
	public ArrayList<Integer> revealedObjects = new ArrayList<Integer>();
	public ArrayList<Integer> deletedObjects = new ArrayList<Integer>();
	//public ArrayList<Integer> lootbagObjects = new ArrayList<Integer>();
	private int currentTick = 0;
	int lastpongtime=0;
	int pongtimenow=0;
	int[] pingpongtime;
	//int pingpongid;
	private final static Random rand = new Random();
	
	public ObjectStatus status;
	public ObjectStatus enemystatus = new ObjectStatus();
	
	public ObjectStatus status2;
	
	
	public ObjectStatus pet=null;
	public int pet_count=0;
	
	public int inv_updated=0;
	public ObjectStatus inv_status;

	// begin new tick status info
	public int _changedSinceLastTick;
	public int _movedSinceLastTick;
	// end new tick status info
	
	public int ineditor=0;
	public int editorx=0,editory=0;
	
	enum StringCompressor {
	    ;
	    public static byte[] compress(String text) {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try {
	            OutputStream out = new DeflaterOutputStream(baos);
	            out.write(text.getBytes("UTF-8"));
	            out.close();
	        } catch (IOException e) {
	            throw new AssertionError(e);
	        }
	        return baos.toByteArray();
	    }

	    public static String decompress(byte[] bytes) {
	        InputStream in = new InflaterInputStream(new ByteArrayInputStream(bytes));
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try {
	            byte[] buffer = new byte[8192];
	            int len;
	            while((len = in.read(buffer))>0)
	                baos.write(buffer, 0, len);
	            return new String(baos.toByteArray(), "UTF-8");
	        } catch (IOException e) {
	            throw new AssertionError(e);
	        }
	    }
	}

	private List<Integer> dontPrint = Arrays.asList(new Integer[] { 
		
			//Packets.MOVE, Packets.UPDATEACK, Packets.PLAYERSHOOT,Packets.ALLYSHOOT
			//,Packets.UPDATE
			//,Packets.NEW_TICK
			//,Packets.ENEMYHIT
			//,Packets.INVSWAP
			//,Packets.INVDROP
			//,Packets.INVRESULT
			//,Packets.PLAYERTEXT
			//,Packets.MAPINFO
			//,Packets.HELLO
			//,Packets.TEXT
			//,Packets.GOTO
			//,Packets.GOTOACK
			//,Packets.USEITEM
			//,Packets.PING
			//,Packets.PONG 
	});
	/*
	0xa1f attack
	0xa20 defence 
	0xa21 spd
	0xa34 vit
	0xa35 wis
	0xa4c dex
	0xae9 life
	0xaea mana
	0xa22 hp
	0xa23 mp 
	*/
	private List <Integer> potions = Arrays.asList(new Integer[] { 
		0xa1f,0xa20,0xa21,0xa34,0xa35,0xa4c,0xae9,0xaea,0xa22,0xa23
	});
	
	private int pingCounter;
	private int unansweredPings;
	private Boolean disconnected = false;
	
	 Random generator = new Random();

	public ClientHandler(ROTMGServer rotmgServer, Socket s) {
		try {
			this.server = rotmgServer;
			this.s = s;
			this.in = new DataInputStream(s.getInputStream());
			this.out = new DataOutputStream(s.getOutputStream());
			initCiphers();

			startThreads();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startThreads() {
		new Thread() {
			public void run() {
				try {
					while (s.isConnected()) {
						RWable rw = readPacket();
						if (rw!=null)
						{
						handlePacket(rw);
						}
					}
				} catch (IOException ioe) {
					 ioe.printStackTrace();
				}
				disconnect();
			}
		}.start();
		new Thread() {
			public void run() {
				try {
					
					while (s.isConnected()) {
						long start = System.currentTimeMillis();

						while (sendQueue.size() != 0) {
							RWable rw = sendQueue.remove(0);
							writePacket(rw);
						}
						handleLoop();
						long end = System.currentTimeMillis();
						// System.out.println("Done in " + ((int) (end-start)) + " | " + (50 - (int) (end-start)));
						int time = (int) (end - start);
						sleep(50 - (time > 50 ? 0 : time));
						//sleep(1);
					}
				} catch (IOException ioe) {
					// ioe.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				disconnect();
			}
		}.start();
	}
	
	public void clearlist()
	{
		revealedObjects.clear();	
		deletedObjects.clear();
	}

	protected void handleLoop() {
		if (status != null && status.objStatData.position != null) {
			UPDATE_Packet up = server.map.update(this);
			
			if (up != null)
				sendQueue.add(up);
		}

	}

	protected void handlePacket(RWable rw) {
		if (!dontPrint.contains(rw.getId()))
		{
			System.out.println(rw);
			
		}
		if (rw instanceof HELLO_Packet) {
			HELLO_Packet hp = (HELLO_Packet) rw;
			// System.out.println(hp);
			// TODO actually check auth

			ineditor=0;
			if (hp.gameId_ == -2) { //nexus
				
				//sendQueue.add(this.server.map.getMapInfo());
				
			}
			else if (hp.gameId_ == -60) { //editor
				ineditor=1;
				String data="";
				byte [] data2;
			    Gson gson = new Gson();
			    WorldData obj = gson.fromJson(hp.jD, WorldData.class);
			    System.out.println(obj);
			    if (obj!=null)
			    {
			    for (WorldDataObject wdo : obj.dict ) {
			    	//System.out.println(wdo.ground);
			    }
			    
			    
			    
			    data2=MyBase64.decode(obj.data);
			    data=StringCompressor.decompress(data2);
			    try {
					server.map.loadEditor(obj,data,this);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    }
			    sendQueue.add(this.server.map.getMapInfo());
			   
			    
			} else {
			  //defalut nexus
			  sendQueue.add(this.server.map.getMapInfo());
			}
			

		} else if (rw instanceof LOAD_Packet) {
			LOAD_Packet lp = (LOAD_Packet) rw;
			
			CREATE_SUCCESS_Packet csp = new CREATE_SUCCESS_Packet();
			csp.charId_ = lp.charId_;
			// TODO actually load a char
			csp.objectId_ = getNextObjectId();
			status = new ObjectStatus();
			status.objectType = CREATE_Packet.WIZARD;
			status.objStatData.objectId = csp.objectId_;////server.map.objectIdCounter.getAndIncrement();// ;
			if (ineditor==1)
			{		
			  
			  status.objStatData.position = new Location(editorx, editory);
			}
			else
			{
				status.objStatData.position = new Location(134, 140);
			}
			System.out.println(status.objStatData.position);
			// status.objStatData.statData
			status2=status;
			loadStatData();

			server.map.addPlayer(this);

			sendQueue.add(csp);
			
			/*
			ObjectStatus os = new ObjectStatus();
			os.objectType = 0x0500;
			os.objStatData.objectId = server.map.objectIdCounter.getAndIncrement();
			os.objStatData.position = new Location(130, 140);
			//os.objStatData.statData.add(sd);
			server.map.objects.add(os);
			*/
			
			UPDATE_Packet up = server.map.getInitialUpdate();
			//System.out.println(up);
			sendQueue.add(up);
			
			ACCOUNTLIST_Packet alp = new ACCOUNTLIST_Packet();
			alp.accountIds_ = new ArrayList<Integer>();
			alp.accountListId_ = 0;
			sendQueue.add(alp);
			
			
		} else if (rw instanceof MOVE_Packet) {
			MOVE_Packet mp = (MOVE_Packet) rw;
			if (status.objStatData.position.x!=mp.newPosition_.x && status.objStatData.position.y!=mp.newPosition_.y)
			{
			status.objStatData.position=mp.newPosition_;
			_changedSinceLastTick = 1;
			_movedSinceLastTick = 1;
			
			}
			//System.out.println(status.objStatData.position); 
			// TODO check for bs
			
			
		} else if (rw instanceof PLAYERSHOOT_Packet) {
			PLAYERSHOOT_Packet ps = (PLAYERSHOOT_Packet) rw;
			
			//System.out.println(ps.bulletId_);
			
			server.map.sendShot(ps, this);
		    
			// TODO parse shot
			
			
		} else if (rw instanceof PONG_Packet) {
			PONG_Packet pp = (PONG_Packet) rw;
			// TODO check serial
			unansweredPings--;
			if (lastpongtime!=pp.time_)
			{
			  pongtimenow=pp.time_-lastpongtime;
			  lastpongtime=pp.time_;
			  System.out.println(pongtimenow);
			}
		
		}else if (rw instanceof ENEMYHIT_Packet) { 
			ENEMYHIT_Packet ehp = (ENEMYHIT_Packet) rw;
			
			synchronized(server.map.enemys)
			{
			for (ObjectStatus os : server.map.enemys)
			{
			  if (os.objStatData.objectId==ehp.targetId_)
			  {
				  for (StatData sd :os.objStatData.statData ) {
					 if (sd.type==StatData.HEALTH)
					 {
				      sd.numValue-=100;
				        if (ehp.kill_==true)
						{
				        
				          server.map.enemys.remove(os);
				          
				          synchronized( server.map.objects)
				          { 
				        	  server.map.objects.remove(os);
				          }
				          synchronized (server.map.drops) { 
								server.map.drops.add(os);
							}
						}
					   break;
					 }
				  }
				  break;
			  }
			}
			}
			
			
			
		}else if (rw instanceof USEITEM_Packet) { 
			//INVRESULT_Packet ir = new INVRESULT_Packet();
			//ir.result_=1;
			//sendQueue.add(ir);
			USEITEM_Packet ui = (USEITEM_Packet) rw;
			
			/*
			0xa1f attack
			0xa20 defence 
			0xa21 spd
			0xa34 vit
			0xa35 wis
			0xa4c dex
			0xae9 life
			0xaea mana
			0xa22 hp
			0xa23 mp 
			*/
			int found=0;
			
			if (ui.slotObject_.objectId==status.objStatData.objectId) //player
			{
				if (potions.contains(ui.slotObject_.itemType)==true )
				{
				  _changedSinceLastTick = 1;
			      for (StatData sd :status.objStatData.statData ) 
			      {
			    	//System.out.println(sd.type);
			        if (sd.type==ui.slotObject_.slotId+8)
			        {
				      sd.numValue=-1;
			        }
			        if (sd.type==StatData.TOTAL_ATTACK && ui.slotObject_.itemType==0xa1f) 
			        {
			          sd.numValue+=1;
			        }
			        else if (sd.type==StatData.TOTAL_DEFENCE && ui.slotObject_.itemType==0xa20) 
			        {
				      sd.numValue+=1;
				    }
			        else if (sd.type==StatData.TOTAL_SPEED && ui.slotObject_.itemType==0xa21) 
			        {
					   sd.numValue+=1;
			        }
			        else if ( (sd.type==StatData.TOTAL_VITALITY) && (ui.slotObject_.itemType==0xa34)) 
			        {
					   sd.numValue+=1;
			        }
			        else if ((sd.type==StatData.TOTAL_WISDOM) && (ui.slotObject_.itemType==0xa35)) 
			        {
					   sd.numValue+=1;
			        }
			        else if (sd.type==StatData.TOTAL_DEXTERITY && ui.slotObject_.itemType==0xa4c) 
			        {
					   sd.numValue+=1;
			        }
			        else if (sd.type==StatData.MAX_HEALTH && ui.slotObject_.itemType==0xae9) 
			        {
					   sd.numValue+=1;
			        }
			        else if (sd.type==StatData.MAX_MANA && ui.slotObject_.itemType==0xaea) 
			        {
					   sd.numValue+=1;
			        }
			        else if (sd.type==StatData.HEALTH && ui.slotObject_.itemType==0xa22) 
			        {
					   sd.numValue+=50;
			        }
			        else if (sd.type==StatData.MANA && ui.slotObject_.itemType==0xa23) 
			        {
					   sd.numValue+=50;
			        }
			        
			      }
			   }
			   else
			   {
				 //ablity
				   // version 02
				   SHOOT_Packet sp = new SHOOT_Packet();
				   //(angle*Math.PI/180)
				   //sp.angle_= (float)(server.map.point_direction(status.objStatData.position.x,status.objStatData.position.y,ui.itemUsePos_.x,ui.itemUsePos_.y)*Math.PI/180);
				    sp.angle_= (float)(server.map.point_direction(ui.itemUsePos_.x,ui.itemUsePos_.y,status.objStatData.position.x,status.objStatData.position.y)*Math.PI/180);
					sp.bulletId_=server.map.getNextBulletId();
					sp.containerType_ = ui.slotObject_.itemType;
					sp.ownerId_=status.objStatData.objectId;
					sp.startingPos_=status.objStatData.position;
					sp.damage_=50;
					sendQueue.add(sp);
					
				   //version 03 not working yet need xml data
				   /*
				   SHOOTMULTI_Packet smp = new SHOOTMULTI_Packet();
				   smp.angle_= (float)(server.map.point_direction(ui.itemUsePos_.x,ui.itemUsePos_.y,status.objStatData.position.x,status.objStatData.position.y)*Math.PI/180);
					smp.bulletId_=server.map.getNextBulletId();
					smp.bulletType_ = ui.slotObject_.itemType;
					smp.ownerId_=status.objStatData.objectId;
					smp.startingPos_=status.objStatData.position;
					smp.angleInc_=(float)0.45;
					smp.multiProjectiles=true;
					smp.numShots_=8;
					smp.damage_=0;
					sendQueue.add(smp);
					*/
			   }
					
			}
			
			/*
			SHOOT_Packet sp = new SHOOT_Packet();
			
			
			sp.angle_= angleFrom(status.objStatData.position.x,status.objStatData.position.y,ui.itemUsePos_.x,ui.itemUsePos_.y);
			sp.bulletId_=server.map.getNextBulletId();
			sp.containerType_ = ui.slotObject_.itemType;
			sp.ownerId_=status.objStatData.objectId;
			sp.startingPos_=status.objStatData.position;
			sp.damage_=0;
			sendQueue.add(sp);
			
			
			sp.angle_= (float) (0 *(Math.PI / 180));
			sp.bulletId_=server.map.getNextBulletId();
			sp.containerType_ = ui.slotObject_.itemType;
			sp.ownerId_=status.objStatData.objectId;
			sp.startingPos_=status.objStatData.position;
			sp.damage_=0;
			//sendQueue.add(sp);
			sp = new SHOOT_Packet();
			sp.angle_= angleFrom(status.objStatData.position.x,status.objStatData.position.y,ui.itemUsePos_.x,ui.itemUsePos_.y);//(float) (90 *(Math.PI / 180));
			sp.bulletId_=server.map.getNextBulletId();
			sp.containerType_ = ui.slotObject_.itemType;
			sp.ownerId_=status.objStatData.objectId;
			sp.startingPos_=status.objStatData.position;
			sp.damage_=0;
			//sendQueue.add(sp);
			sp = new SHOOT_Packet();
			sp.angle_= (float) (180 *(Math.PI / 180));
			sp.bulletId_=server.map.getNextBulletId();
			sp.containerType_ = ui.slotObject_.itemType;
			sp.ownerId_=status.objStatData.objectId;
			sp.startingPos_=status.objStatData.position;
			sp.damage_=0;
			//sendQueue.add(sp);
			sp = new SHOOT_Packet();
			sp.angle_= (float) (270 *(Math.PI / 180));
			sp.bulletId_=server.map.getNextBulletId();
			sp.containerType_ = ui.slotObject_.itemType;
			sp.ownerId_=status.objStatData.objectId;
			sp.startingPos_=status.objStatData.position;
			sp.damage_=0;
			*/
			//sendQueue.add(sp);
			
			/*
			ALLYSHOOT_Packet asp = new ALLYSHOOT_Packet();
			asp.angle_ = 0;
			asp.containerType_ = 0;
			asp.ownerId_ = status.objStatData.objectId;

			asp.bulletId_ = server.map.getNextBulletId();

			for (ClientHandler ch : server.map.players) {
				if (ch != this)
					ch.sendQueue.add(asp);
			}
			*/
			
		}else if (rw instanceof INVSWAP_Packet) {
			INVRESULT_Packet ir = new INVRESULT_Packet();
			INVSWAP_Packet is = (INVSWAP_Packet) rw;
			int found=0;
			
			inv_updated=1;
			_changedSinceLastTick = 1;
		
			if (is.slotObject1_.objectId==is.slotObject2_.objectId && is.slotObject1_.objectId==status.objStatData.objectId) //player is swaping items around in his inventory
			{
				inv_status=status;
			 for (StatData sd :status.objStatData.statData ) 
			 {
			   if (sd.type==is.slotObject1_.slotId+8)
			   {
				 sd.numValue=is.slotObject2_.itemType;
			   }
			   
			   if (sd.type==is.slotObject2_.slotId+8)
			   {
				 sd.numValue=is.slotObject1_.itemType;
			   }
			   
			 }
			}
			else //player is moving items around from source to destnation object
			{
			  
			  if (status.objStatData.objectId==is.slotObject1_.objectId)  //player sends a item to lootbag 
			  {
				  for (StatData sd :status.objStatData.statData ) {
					   if (sd.type==is.slotObject1_.slotId+8)
					   {
						 
						 sd.numValue=is.slotObject2_.itemType;
						 break;
					   }
					   					   
					}
				  
				  //synchronized(server.map.lootbags){
				  for (ObjectStatus os : server.map.lootbags)
				  {
	                if (os.objStatData.objectId==is.slotObject2_.objectId)
	                {
	                	//System.out.println("player sends a item to lootbag  1");
	                	inv_status=os;
	                	for (StatData sd : os.objStatData.statData)
	                	{
	                		if (sd.type==is.slotObject2_.slotId+8)
	 					    {
	                		  //System.out.println("player sends a item to lootbag ");
	                		  sd.numValue=is.slotObject1_.itemType;
	                		  break;
	 					    }

	                		//System.out.println(sd.numValue);
	                	}

	                	//server.map.objects.add(os);
	                	//ir.result_=-1;
	      			    //sendQueue.add(ir);
	                	break;
	                	
	                }
				  }
			      //}
			  }
			  else if (status.objStatData.objectId==is.slotObject2_.objectId) //player recives a item from lootbag
			  {
				  for (StatData sd :status.objStatData.statData ) {
					   if (sd.type==is.slotObject2_.slotId+8)
					   {
						 
						 sd.numValue=is.slotObject1_.itemType;
						 break;
					   }
					   					   
					}
				  synchronized(server.map.lootbags){
				  for (ObjectStatus os : server.map.lootbags)
				  {
	                if (os.objStatData.objectId==is.slotObject1_.objectId)
	                {
	                	//System.out.println("player recives a item from lootbag 1");
	                	inv_status=os;
	                	for (StatData sd : os.objStatData.statData)
	                	{
	                		if (sd.type==is.slotObject1_.slotId+8)
	 					    {
	                		  //System.out.println("player recives a item from lootbag 2");
	                		  sd.numValue=is.slotObject2_.itemType;
	                		  //System.out.println(sd.numValue);
	                		  //break;
	 					    }
	                		
	                		//System.out.println(sd.numValue);
	                		//System.out.println(is.slotObject2_.itemType);
	                		if ((sd.numValue!=-1) && sd.type>=8 && sd.type<=8+7)
	                		{
	                			found=1;
	                		}
	                		
	                	}
	                	if (found==0)
	                	{
	                	    //System.out.println("loot bag empty");
	                		synchronized( server.map.objects)
					        { 
					        	  server.map.lootbags.remove(os);
					        }
	                		synchronized( server.map.objects)
					        { 
					        	  server.map.objects.remove(os);
					        }
					        synchronized (server.map.drops) 
					        { 
							     server.map.drops.add(os);
						   }	
	                	}
	                	//server.map.objects.add(os);
	                	//ir.result_=-1;
	      			    //sendQueue.add(ir);
	                	break;
	                	
	                }
				  }
				  }
				  
			  }
			  else
			  {
				if (is.slotObject1_.objectId==is.slotObject2_.objectId && is.slotObject1_.objectId!=status.objStatData.objectId)//items swaping in loot bag
				{
			      synchronized(server.map.lootbags){
				  for (ObjectStatus os : server.map.lootbags)
				  {
		             if (os.objStatData.objectId==is.slotObject1_.objectId || os.objStatData.objectId==is.slotObject2_.objectId )
		             {
		            	inv_status=os;
					    for (StatData sd : os.objStatData.statData ) 
					    {
						   if (sd.type==is.slotObject1_.slotId+8)
						   {
							 sd.numValue=is.slotObject2_.itemType;
						   }
						   
						   if (sd.type==is.slotObject2_.slotId+8)
						   {
							 sd.numValue=is.slotObject1_.itemType;
						   }
						   
						 }
				       }
				    }
			        }
				  }
				else
				{
				  ir.result_=-1;
    			  sendQueue.add(ir);
				}
			  }
			  
			  
			}

			
		}
        else if (rw instanceof INVDROP_Packet) {
			INVRESULT_Packet ir = new INVRESULT_Packet();
			INVDROP_Packet idp = (INVDROP_Packet) rw;

			if (status.objStatData.objectId==idp.slotObject_.objectId)//player droping item from inventory
			{
			  _changedSinceLastTick = 1;
			  ObjectStatus os = new ObjectStatus();
			  os.objectType = 0x0500;
			  os.objStatData.objectId = server.map.objectIdCounter.getAndIncrement();
			  os.objStatData.position = status.objStatData.position;
			
			  os.objStatData.statData.add(new StatData(StatData.SIZE, 80 ));
		  	  os.objStatData.statData.add(new StatData(StatData.SLOT_1, idp.slotObject_.itemType ));
			  os.objStatData.statData.add(new StatData(StatData.SLOT_2, -1 ));
			  os.objStatData.statData.add(new StatData(StatData.SLOT_3, -1 ));
			  os.objStatData.statData.add(new StatData(StatData.SLOT_4, -1 ));
			  os.objStatData.statData.add(new StatData(StatData.SLOT_5, -1 ));
			  os.objStatData.statData.add(new StatData(StatData.SLOT_6, -1 ));
			  os.objStatData.statData.add(new StatData(StatData.SLOT_7, -1 ));
			  os.objStatData.statData.add(new StatData(StatData.SLOT_8, -1 ));
			  synchronized (server.map.lootbags) {
			  server.map.lootbags.add(os);
			  }
			  synchronized (server.map.objects) {
			  server.map.objects.add(os);
			  }

			  //System.out.println(idp.slotObject_.slotId);
			  for (StatData sd : status.objStatData.statData ) {
				  if (sd.type==idp.slotObject_.slotId+8) 
				  {
					 sd.numValue=-1;
				  }
			  }
			  //ir.result_=1;
			  //sendQueue.add(ir);	
			}
			else
			{
				ir.result_=-1;
				sendQueue.add(ir);	
			}
			
			
		}
		else if (rw instanceof TELEPORT_Packet) {
			TELEPORT_Packet tp = (TELEPORT_Packet) rw;
			//System.out.println(tp.objectId_);
			//System.out.println(status2.objStatData.objectId);
			server.map.teleport(this, tp.objectId_);
			_changedSinceLastTick = 1;
			
		} else if (rw instanceof ESCAPE_Packet) {
			disconnect();
			
		} else if (rw instanceof PLAYERTEXT_Packet) {
			PLAYERTEXT_Packet ptp = (PLAYERTEXT_Packet) rw;
			
			
			if (ptp.text_.equalsIgnoreCase("pet"))
			{
				ObjectStatus os = new ObjectStatus();
				os.objectType = 0x1635+pet_count;
				pet_count++;
				os.objStatData.objectId = server.map.objectIdCounter.getAndIncrement();
				
				os.objStatData.position = status.objStatData.position;
				
				os.objStatData.owner_id= status.objStatData.objectId;
				if (pet!=null)
				{
					synchronized (server.map.drops) { 
					server.map.drops.add(pet);
					}
				}
				pet=os;
				synchronized (server.map.objects) { 
				server.map.objects.add(os); 
				}
				//System.out.println(os.objStatData.objectId);
				
			}
			else
			{
			  String command[];
			  command = ptp.text_.split(" ");
			  //System.out.println(command[0]);
			  if (command[0].length()==5)
			  {
				  //System.out.println(ptp.text_.substring(5));
				  //System.out.println(ptp.text_);
			      if (command[0].equalsIgnoreCase("spawn") && ptp.text_.length()>=6 )
			      {
			    	  //server.map.sendNoPermission(this);
			    	  
			    	  //System.out.println(server.map.spawnlist.size());
			         for (SpawnList spl : server.map.spawnlist) {
			        	 
			           //System.out.println(spl.name);
				       if (spl.name.equalsIgnoreCase(ptp.text_.substring(6)))
				       {
					     ObjectStatus os = new ObjectStatus();
					     os.objectType = spl.objectid;
					     os.objStatData.objectId = server.map.objectIdCounter.getAndIncrement();
					     os.objStatData.position = status.objStatData.position;
					     os.objStatData.owner_id= status.objStatData.objectId;
					     EnemyloadStatData();
					     os.objStatData.statData=enemystatus.objStatData.statData;
					     synchronized (server.map.enemys) { 
					     server.map.enemys.add(os);
					     }
					     synchronized (server.map.objects) {  
					     server.map.objects.add(os); 
					     }
				         break; 
				       }
				       
			         }
			         
			      }
			      else
			      {
				    server.map.sendText(this, ptp);
			      };
			  }
			  
			  else if (command[0].length()==6)
			  {
				  //System.out.println(ptp.text_.substring(5));
				  System.out.println(ptp.text_);
			      if (command[0].equalsIgnoreCase("dododo") && ptp.text_.length()>=7 )
			      {
			    	  System.out.println(server.map.spawnlist.size());
			         for (SpawnList spl : server.map.spawnlist) {
			        	 
			           //System.out.println(spl.name);
				       if (spl.name.equalsIgnoreCase(ptp.text_.substring(7)))
				       {
				    	 Location pos1 = new  Location(1,1);
				    	 int i=0;
				    	 for(i=0;i<10;i++)
				    	 {
					     ObjectStatus os = new ObjectStatus();
					     os.objectType = spl.objectid;
					     os.objStatData.objectId = server.map.objectIdCounter.getAndIncrement();

					     EnemyloadStatData();
					     os.objStatData.position = status.objStatData.position;
					     os.objStatData.owner_id= status.objStatData.objectId;
					     os.objStatData.statData=enemystatus.objStatData.statData;
					     synchronized (server.map.enemys) { 
					     server.map.enemys.add(os);
					     }
					     synchronized (server.map.objects) {  
					     server.map.objects.add(os); 
					     }
				    	 }
				         break; 
				       }
				       
			         }
			      }
			      else
			      {
				    server.map.sendText(this, ptp);
			      };
			  }
			  else if (command[0].length()==4)
			  {
				  if (command[0].equalsIgnoreCase("give") && ptp.text_.length()>=4 )
			      {
			    	 //System.out.println(server.map.givelist.size());
			         for (SpawnList spl : server.map.givelist) {
			        	 
			           //System.out.println(spl.name);
				       if (spl.name.equalsIgnoreCase(ptp.text_.substring(5)))
				       {
				    	   
				    	   for (StatData sd :status.objStatData.statData ) {
							   if (sd.type>=12 && sd.type<=12+7 )
							   {
								 if (sd.numValue==-1)
								 {
								   sd.numValue=spl.objectid;
								   inv_updated=1;
								   inv_status=status;
								   break;
								 }
							   }
							}
				         break; 
				       }
				       
			         }
			      }
			      else
			      {
				    server.map.sendText(this, ptp);
			      };
			  }
			  else
			  {
				server.map.sendText(this, ptp);
			  };
			  
			}
			
		} else if (rw instanceof CREATE_Packet) {
			CREATE_Packet cp = (CREATE_Packet) rw;
			CREATE_SUCCESS_Packet csp = new CREATE_SUCCESS_Packet();
			_changedSinceLastTick = 1;
			
			csp.charId_ = 8;
			// TODO actually make new char
			csp.objectId_ = getNextObjectId();
			status = new ObjectStatus();
			status.objectType = CREATE_Packet.WIZARD;
			status.objStatData.objectId = server.map.objectIdCounter.getAndIncrement();
			status.objStatData.position = new Location(219.5f, 177.5f);
			// status.objStatData.statData
			loadStatData();
			server.map.addPlayer(this);
			sendQueue.add(csp);
			UPDATE_Packet up = server.map.getInitialUpdate();
			//System.out.println(up);
			sendQueue.add(up);
			ACCOUNTLIST_Packet alp = new ACCOUNTLIST_Packet();
			alp.accountIds_ = new ArrayList<Integer>();
			alp.accountListId_ = 0;
			sendQueue.add(alp);
		}
	}

	private void loadStatData() {
		status.objStatData.statData.clear();
		status.objStatData.statData.add(new StatData(StatData.MAX_HEALTH, 100));
		status.objStatData.statData.add(new StatData(StatData.HEALTH, 100));
		status.objStatData.statData.add(new StatData(StatData.SIZE, 100));
		status.objStatData.statData.add(new StatData(StatData.MAX_MANA, 50));
		status.objStatData.statData.add(new StatData(StatData.MANA, 50));
		status.objStatData.statData.add(new StatData(StatData.CURRENT_LEVEL, 0));
		status.objStatData.statData.add(new StatData(StatData.TOTAL_ATTACK, 10));
		status.objStatData.statData.add(new StatData(StatData.TOTAL_DEFENCE, 0));
		status.objStatData.statData.add(new StatData(StatData.TOTAL_SPEED, 10));
		status.objStatData.statData.add(new StatData(StatData.TOTAL_DEXTERITY, 10));
		status.objStatData.statData.add(new StatData(StatData.TOTAL_VITALITY, 10));
		status.objStatData.statData.add(new StatData(StatData.TOTAL_WISDOM, 10));
		status.objStatData.statData.add(new StatData(StatData.NAME, "pl" + rand.nextInt(99999999)));
		status.objStatData.statData.add(new StatData(StatData.REALM_GOLD, 100));
		status.objStatData.statData.add(new StatData(StatData.TOTAL_FAME, 0));
		status.objStatData.statData.add(new StatData(StatData.FAME, 0));
		status.objStatData.statData.add(new StatData(StatData.FAME_GOAL, 0));
		status.objStatData.statData.add(new StatData(StatData.GUILD, "Trololol"));
		status.objStatData.statData.add(new StatData(StatData.GUILD_RANK, 4));

		status.objStatData.statData.add(new StatData(StatData.SLOT_1, 0xC03)); 
		status.objStatData.statData.add(new StatData(StatData.SLOT_2, 2606));
		status.objStatData.statData.add(new StatData(StatData.SLOT_3, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_4, -1));
		
		status.objStatData.statData.add(new StatData(StatData.SLOT_5, 2594));
		status.objStatData.statData.add(new StatData(StatData.SLOT_6, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_7, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_8, -1));
		
		status.objStatData.statData.add(new StatData(StatData.SLOT_9, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_10, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_11, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_12, -1));
	}
	
	private void EnemyloadStatData() {
		enemystatus.objStatData.statData.clear();
		//enemystatus.objStatData.statData.add(new StatData(StatData.MAX_HEALTH, 1000));
		enemystatus.objStatData.statData.add(new StatData(StatData.HEALTH, 500));
		//enemystatus.objStatData.statData.add(new StatData(StatData.SIZE, 100));
		/*
		enemystatus.objStatData.statData.add(new StatData(StatData.MAX_MANA, 400));
		enemystatus.objStatData.statData.add(new StatData(StatData.MANA, 200));
		enemystatus.objStatData.statData.add(new StatData(StatData.CURRENT_LEVEL, 20));
		enemystatus.objStatData.statData.add(new StatData(StatData.TOTAL_ATTACK, 50));
		enemystatus.objStatData.statData.add(new StatData(StatData.TOTAL_DEFENCE, 50));
		enemystatus.objStatData.statData.add(new StatData(StatData.TOTAL_SPEED, 70));
		enemystatus.objStatData.statData.add(new StatData(StatData.TOTAL_DEXTERITY, 50));
		enemystatus.objStatData.statData.add(new StatData(StatData.TOTAL_VITALITY, 50));
		enemystatus.objStatData.statData.add(new StatData(StatData.TOTAL_WISDOM, 100));
		enemystatus.objStatData.statData.add(new StatData(StatData.NAME, "pl" + rand.nextInt(99999999)));
		enemystatus.objStatData.statData.add(new StatData(StatData.REALM_GOLD, 100));
		enemystatus.objStatData.statData.add(new StatData(StatData.TOTAL_FAME, 0));
		enemystatus.objStatData.statData.add(new StatData(StatData.FAME, 10000));
		enemystatus.objStatData.statData.add(new StatData(StatData.FAME_GOAL, 10000));
		enemystatus.objStatData.statData.add(new StatData(StatData.GUILD, "Trololol"));
		enemystatus.objStatData.statData.add(new StatData(StatData.GUILD_RANK, 4));
		*/

		/*
		status.objStatData.statData.add(new StatData(StatData.SLOT_1, 0xC03)); 
		status.objStatData.statData.add(new StatData(StatData.SLOT_2, 2606));
		status.objStatData.statData.add(new StatData(StatData.SLOT_3, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_4, 0xBa2));
		
		status.objStatData.statData.add(new StatData(StatData.SLOT_5, 2594));
		status.objStatData.statData.add(new StatData(StatData.SLOT_6, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_7, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_8, -1));
		
		status.objStatData.statData.add(new StatData(StatData.SLOT_9, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_10, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_11, -1));
		status.objStatData.statData.add(new StatData(StatData.SLOT_12, -1));
		*/
	}

	private int getNextObjectId() {
		return server.map.objectIdCounter.getAndIncrement();
	}

	private void initCiphers() {
		cipher_fromClient = new RC4(DatatypeConverter.parseHexBinary("311F80691451C71B09A13A2A6E"));
		cipher_toClient = new RC4(DatatypeConverter.parseHexBinary("72C5583CAFB6818995CBD74B80"));
	}
	
	
	public void map_ediotr_create_enemy(ObjectStatus os2)
	{
		
		ObjectStatus os = new ObjectStatus();
	     os.objectType = os2.objectType;
	     os.objStatData.objectId = server.map.objectIdCounter.getAndIncrement();
	     os.objStatData.position = os2.getPosition();
	     os.objStatData.owner_id= 0;
	     EnemyloadStatData();
	     os.objStatData.statData=enemystatus.objStatData.statData;
	     synchronized (server.map.enemys) { 
	     server.map.enemys.add(os);
	     }

	}
	
	int checked=0;
	int check_packetLength = 0;
	
	int check_packetId = 0;

	protected RWable readPacket() throws IOException {
		
		if (s.isClosed()==true)
		{
			System.out.println("socket is closed why closed the connection why??");
		}
		
		

		if (in.available()>=5)
		{
			int packetLength;
			int packetId;
			
		    if  (checked==0)
		    {
			packetLength = in.readInt();
			
			packetId = in.readByte();
			check_packetLength=packetLength;
			check_packetId=packetId;
			checked=1;
		    }
		    else// checked==1
		    {
		    	 packetLength = check_packetLength;
				
				 packetId = check_packetId;
		    }
			
			if (in.available()>=packetLength-5)
			{
				checked=0;
		  //while (in.available()<packetLength - 5)
		   //{
			 
				//System.out.println(in.available());
			 
			//try {
			//	sleep(75);
			//} catch (InterruptedException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			//} 
		  // }
		 
		 
		 byte[] buf = new byte[packetLength - 5]; // read the rest of the packet
			in.readFully(buf);
			if (packetId==36)
			{
			  System.out.println("create 36");
			  //String s = new String(buf);
			  System.out.println((int)(buf[0] &0xFF));
			  System.out.println((int)(buf[1] &0xFF));
			  //buf[0]=(byte)188;
			  //buf[1]=(byte)130;
			}
			byte[] decr = cipher_fromClient.rc4(buf);
			
			RWable rw = Packets.findPacket(packetId);
			
			if (rw==null) 
			{
				//System.out.println(packetId);
				
				return null;
		    } 
			
			rw.parseFromInput(new ByteArrayDataInput(decr));
			return rw;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;	
		}
		
		
		
	}

	public void writePacket(int i, byte[] buf) throws IOException {
		byte[] encr = cipher_toClient.rc4(buf);
		out.writeInt(encr.length + 5);
		out.writeByte(i);
		out.write(encr);
		out.flush();
	}

	public void writePacket(RWable rw) throws IOException {
		if (rw!=null) {
		if (!dontPrint.contains(rw.getId()))
		{
			System.out.println(rw);
		}
		
		ByteArrayDataOutput bado = new ByteArrayDataOutput(100000);
		rw.writeToOutput(bado);
		writePacket(rw.getId(), bado.getArray());
		}
	}

	public int getNextTickNum() {
		return currentTick++;
	}

	public ObjectStatus getStatus() {
		return status;
	}

	public double distanceTo(Location loc) {
		return status.objStatData.position.distanceTo(loc.x, loc.y);
	}

	public double distanceToSq(Location loc) {
		return status.objStatData.position.distanceToSq(loc.x, loc.y);
	}

	public double distanceTo(ClientHandler ch) {
		return status.objStatData.position.distanceTo(ch.status.objStatData.position.x, ch.status.objStatData.position.y);
	}

	public double distanceToSq(ClientHandler ch) {
		return status.objStatData.position.distanceToSq(ch.status.objStatData.position.x, ch.status.objStatData.position.y);
	}
	
	float angleFrom(float APositionX, float APositionY, float BPositionX, float BPositionY)
	{
	    float dx = APositionY -  BPositionX;
	    float dy = APositionY -  BPositionY ;
        float angle=0;
        /*
        if (y>=1)
        {
        angle=(float)(Math.atan(y/x) * 180 / Math.PI)-90;
        }
        else
        {
        angle=90;
        }
        if (angle>0)
        {
        	angle+=285;
        }
        if (angle > 360)
        {
          angle-=360;
        }
        else if (angle<=0)
        {
          angle+=360;
        } 
        System.out.println(angle);
	    return angle;
	    */
        //System.out.println(BPositionX);
        //System.out.println(BPositionY);
        //System.out.println((int) (Math.atan2(dy, dx) * 180 / Math.PI )  );
        //System.out.println((float)   ((Math.atan2(dy, dx) * 180 / Math.PI ) *(Math.PI / 180) ));
        
        angle=(float)((Math.atan(dx /dy) * 180 / Math.PI ) *(Math.PI / 180) );
        //if (angle<0)
        //{
        	System.out.println((float)   ((Math.atan(dx /dy) * 180 / Math.PI ) *(Math.PI / 180) ) +3.14115927 );
        //}
        //else
       // {
          System.out.println((float)   ((Math.atan(dx /dy) * 180 / Math.PI ) *(Math.PI / 180) ));
        //}
        
        System.out.println((float) (0 *(Math.PI / 180)));
        System.out.println((float) (90 *(Math.PI / 180)));
        System.out.println((float) (180 *(Math.PI / 180)));
        System.out.println((float) (270 *(Math.PI / 180)));
	    return (float) Math.atan2(dy, dx);
	}
	
	public  float toRotmgAngle(float angle) {
		return (float) ((angle - 90) / (360 / Math.PI / 2));
	}

	// returns the current tick this client is on
	public int getCurrentTick() {
		return currentTick;
	}

	// sends a ping and if required, kick client for missed pings
	public void sendPing() { // sent every 5sec
		PING_Packet pp = new PING_Packet();
		sendQueue.add(pp);
		pp.serial_ = pingCounter++;
		if (unansweredPings++ > 20) { // if missed 5 pings (not including this)
			//disconnect();
		}
	}

	public void disconnect() {
		//Thread.dumpStack();
           
			if (disconnected) // if we're already disconnected
				return; // don't do anything
			
			try {
				s.close();
			} catch (IOException e) {
			}
			disconnected = true;
			server.map.disconnect(this);
			
			// TODO maybe other handling, such as saving?
		}

}
