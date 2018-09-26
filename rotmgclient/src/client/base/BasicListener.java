package client.base;

import java.util.Random;

import client.GameConsts.Characters;

import data.Item;
import data.Location;
import data.LocationWTime;
import data.ObjectStatus;
import packets.RWable;
import packets.client.AOEACK_Packet;
import packets.client.CREATE_Packet;
import packets.client.GOTOACK_Packet;
import packets.client.HELLO_Packet;
import packets.client.LOAD_Packet;
import packets.client.MOVE_Packet;
import packets.client.PLAYERSHOOT_Packet;
import packets.client.PLAYERTEXT_Packet;
import packets.client.PONG_Packet;
import packets.client.SHOOTACK_Packet;
import packets.client.UPDATEACK_Packet;
import packets.client.USEITEM_Packet;
import packets.server.ALLYSHOOT_Packet;
import packets.server.AOE_Packet;
import packets.server.CREATE_SUCCESS_Packet;
import packets.server.DEATH_Packet;
import packets.server.FAILURE_Packet;
import packets.server.GOTO_Packet;
import packets.server.MAPINFO_Packet;
import packets.server.NEW_TICK_Packet;
import packets.server.PING_Packet;
import packets.server.RECONNECT_Packet;
import packets.server.SHOOTMULTI_Packet;
import packets.server.SHOOT_Packet;
import packets.server.UPDATE_Packet;

public class BasicListener implements GameListener {
	private GameClient gCli;
	private GameConnection gCon;
	private int lastMoveTime;
	private String nameToChoose;
	private final static Random rand = new Random();
	
	RECONNECT_Packet rcpdetails = new RECONNECT_Packet();
	
	int count=0;
	int bullet_count=0;
	
	int lastshotTick=0;
	int shotTick=0;
	

	public BasicListener(GameConnection gCon, GameClient gameClient) {
		this.gCon = gCon;
		this.gCli = gameClient;
	}

	@Override
	public void loop() {
	   //System.out.println("test");
	}

	@Override
	public void packetRecieved(RWable rw) {
		if (rw instanceof MAPINFO_Packet) {
			MAPINFO_Packet mip = (MAPINFO_Packet) rw;
			gCon.world = new World(mip, gCon, gCli);
			gCon.world.chooseName(nameToChoose);
			
			//todo load or create 
			//CREATE_Packet cp = new CREATE_Packet();
			//cp.objectType_ = Characters.WIZARD;
			//gCon.sendQueue.add(cp);
			
			// TODO create world object
		} else if (rw instanceof CREATE_SUCCESS_Packet) {
			CREATE_SUCCESS_Packet csp = (CREATE_SUCCESS_Packet) rw;
			gCon.world.parseCreateSuccess(csp);
			
		} else if (rw instanceof PING_Packet) {
			
			PING_Packet pip = (PING_Packet) rw;
			PONG_Packet pop = new PONG_Packet();
			pop.serial_ = pip.serial_;
			pop.time_ = gCli.currentTime();//207063;//(int) (System.currentTimeMillis()-gCon.start);//gCli.currentTime();
			
			count++;
			if (count==10)
			{
				PLAYERTEXT_Packet pt = new PLAYERTEXT_Packet();
				pt.text_="LOL,"+rand.nextInt(5);
				count=0;
				//gCon.sendQueue.add(pt);
			}
			
			gCon.sendQueue.add(pop);
			 
		} else if (rw instanceof UPDATE_Packet) {
			UPDATE_Packet up = (UPDATE_Packet) rw;
			gCon.world.parseUpdate(up);
			UPDATEACK_Packet ua = new UPDATEACK_Packet();
			gCon.sendQueue.add(ua);
			//System.out.println("11");
		} else if (rw instanceof NEW_TICK_Packet) {
			
			NEW_TICK_Packet ntp = (NEW_TICK_Packet) rw;
			if (ntp.statuses_!=null)
			{
			  gCon.world.parseNewTick(ntp);
			}
			MOVE_Packet mp = new MOVE_Packet();
			mp.time_ = gCli.currentTime();
			mp.tickId_ =gCon.world.getCurrentTick();
			//mp.newPosition_ = new Location(139,140) ;//gCon.moveClazz.move(mp.time_ - lastMoveTime);
			mp.newPosition_ = gCon.moveClazz.move(mp.time_ - lastMoveTime);
			gCon.world.setPosition(mp.newPosition_);
			lastMoveTime = mp.time_;
			mp.records_ = new LocationWTime[0];
			
			gCon.sendQueue.add(mp);
			
		} else if (rw instanceof GOTO_Packet) {
			
			//System.out.println("goto packet");
			GOTO_Packet gp = (GOTO_Packet) rw;
			gCon.world.parseGoto(gp);
			GOTOACK_Packet gap = new GOTOACK_Packet();
			gap.time_ = gCli.currentTime();
			gCon.sendQueue.add(gap);
			
		} else if (rw instanceof RECONNECT_Packet) {
			
			RECONNECT_Packet rcp = (RECONNECT_Packet) rw;
			rcpdetails=(RECONNECT_Packet)rcp;
			System.out.println(rcpdetails.host_);
			rcpdetails.host_=rcp.host_;
			gCon.disconnect();
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			HELLO_Packet hp = gCli.constructHelloPacket(rcp.gameId_, rcp.keyTime_, rcp.key_);
			System.out.println(gCon.world.charid);
			LOAD_Packet lp = gCli.constructLoadPacket(gCon.world.charid);
			GameConnection ngCon = new GameConnection();
			ngCon.listeners.addAll(gCli.getNewListeners(ngCon));
			if (rcp.host_.length() == 0) {
				ngCon.connect(gCon.getCurrentAddress(), hp, lp);
			} else {
				ngCon.connect(rcp.host_, hp, lp);
			}
			
		} else if (rw instanceof FAILURE_Packet) {
			FAILURE_Packet fp = (FAILURE_Packet) rw;
			if (fp.errorDescription_.contains("Account in use")==true)
			{
				System.out.println("error found ");
				System.err.println(fp);

				gCon.disconnect();
				HELLO_Packet hp = gCli.constructHelloPacket(rcpdetails.gameId_, rcpdetails.keyTime_, rcpdetails.key_);
				LOAD_Packet lp = gCli.constructLoadPacket(gCon.world.charid);
				GameConnection ngCon = new GameConnection();
				ngCon.listeners.addAll(gCli.getNewListeners(ngCon));

				ngCon.connect(rcpdetails.host_, hp, lp);

			}
			else
			{
				System.err.println(fp);
				gCli.failed = fp;
			}
			
			//
			// TODO handle failure
		} else if (rw instanceof DEATH_Packet) {
			DEATH_Packet dp = (DEATH_Packet) rw;
			System.err.println(dp);
			gCli.death = dp;
			// TODO handle death
		} else if (rw instanceof SHOOTMULTI_Packet) {
			/*
			SHOOTMULTI_Packet smp = (SHOOTMULTI_Packet) rw;
			SHOOTACK_Packet sap = new SHOOTACK_Packet();
			sap.time_ = gCli.currentTime();
			gCon.sendQueue.add(sap);
			*/
		} else if (rw instanceof AOE_Packet) {
			/*
			AOE_Packet ap = (AOE_Packet) rw;
			AOEACK_Packet aap = new AOEACK_Packet();
			aap.time_ = gCli.currentTime();
			aap.position_ = gCon.world.getPosition();
			gCon.sendQueue.add(aap);
			*/
		} else if (rw instanceof ALLYSHOOT_Packet) {
			
ALLYSHOOT_Packet asp = (ALLYSHOOT_Packet) rw;
			
			ObjectStatus os;
			//os=gCon.world.getObjectByName("Spacehyp");
			os=gCon.world.getObjectByName("Dbsdbajdbj");
			//os=gCon.world.getObjectByName("SomeFrog");
			
			//System.out.println(os.objStatData.objectId );
			if (os!=null)
			{
			if (asp.ownerId_==os.objStatData.objectId)
			{
			  
			  
			  //System.out.println(asp.bulletId_);
			  /*
			  try {
				    Thread.sleep(500);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			  */
			  bullet_count+=1;
			  //System.out.println(lastshotTick);
			  //System.out.println(gCon.world.getCurrentTick());
			  if (bullet_count==2 )
			  {
				  if (lastshotTick<gCon.world.getCurrentTick())
				  {
				  lastshotTick=gCon.world.getCurrentTick()+3;
				  //System.out.println(lastshotTick);
				  PLAYERSHOOT_Packet psp = new PLAYERSHOOT_Packet();
				  psp.angle_=asp.angle_;
				  psp.bulletId_=gCon.world.getNextBulletId();//asp.bulletId_;
				  psp.containerType_=asp.containerType_;
				  psp.startingPos_=gCon.world.getPosition();
				  psp.time_=gCli.currentTime();	  
			  gCon.sendQueue.add(psp);
			  
			  PLAYERSHOOT_Packet psp2 = new PLAYERSHOOT_Packet();
			  psp2.angle_=asp.angle_;
			  psp2.bulletId_=gCon.world.getNextBulletId();//asp.bulletId_;
			  psp2.containerType_=asp.containerType_;
			  psp2.startingPos_=gCon.world.getPosition();
			  psp2.time_=psp.time_;
			  //System.out.println(os.objStatData.position.subtract(asp.));	
			  
			  
			  //psp2.bulletId_=gCon.world.getNextBulletId();
			  gCon.sendQueue.add(psp2);
				  }
			  bullet_count=0;
			  }
			  
			}
			}
			
		} else if (rw instanceof SHOOT_Packet) {
			SHOOT_Packet sp = (SHOOT_Packet) rw;
			ObjectStatus os;
			//os=gCon.world.getObjectByName("Spacehyp");
			//os=gCon.world.getObjectByName("Dbsdbajdbj");
			os=gCon.world.getObjectByName("SomeFrog");
			
			
			//System.out.println(os.objStatData.objectId );
			if (os!=null)
			{
			  if (sp.ownerId_==os.objStatData.objectId)
			  {
				if (shotTick==0)
				{
					Item it = new Item(gCon.world.myid,1,2606);
					shotTick=gCon.world.getCurrentTick();
					System.out.println("shot"); 
					USEITEM_Packet uip = new USEITEM_Packet();
					uip.itemUsePos_=gCon.world.getPosition();
					uip.slotObject_=it;
					uip.useType=1;
					uip.time_=gCli.currentTime();
					gCon.sendQueue.add(uip);
				}
				else
				{
				  if (shotTick!=gCon.world.getCurrentTick())
				  {
					System.out.println("shot"); 
					//shot
					Item it = new Item(gCon.world.myid,1,2606);
					shotTick=gCon.world.getCurrentTick();
					System.out.println("shot"); 
					USEITEM_Packet uip = new USEITEM_Packet();
					uip.itemUsePos_=gCon.world.getPosition();
					uip.slotObject_=it;
					uip.useType=1;
					uip.time_=gCli.currentTime();
					gCon.sendQueue.add(uip);
					shotTick=gCon.world.getCurrentTick();
				  }
				  else
				  {
					  //System.out.println("same");    
				  }
				}
			  }
			}
			
			
			if (sp.ownerId_==gCon.world.myid)
			{
				SHOOTACK_Packet sap = new SHOOTACK_Packet();
				sap.time_ = gCli.currentTime();
				//gCon.sendQueue.add(sap);	
			}
             
		}
	}

	@Override
	public void objectRemoved(ObjectStatus os) {
		
	}

	@Override
	public void objectAdded(ObjectStatus os) {
		
	}

	public void chooseName(String name) {
		this.nameToChoose = name;
	}

}
