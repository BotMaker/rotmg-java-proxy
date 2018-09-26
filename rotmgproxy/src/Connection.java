import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import packets.ByteArrayDataOutput;
import packets.Packets;
import packets.RWable;
import packets.client.*;
import packets.server.*;
import data.Location;
import data.LocationWTime;
import data.ObjectStatus;
import data.StatData;
// import extra.MoveSendThreadPacket;

public class Connection {
	//private static final String realServerAddress_REALM = "50.19.47.160";//"50.16.139.250";
	private static final String realServerAddress_NEXUS = "54.226.214.216";//"50.19.47.160"; // 50.19.47.160"; 46.137.247.5
	private static String nextAddress = realServerAddress_NEXUS;
	private Socket serverConnection;
	private Socket clientConnection;
	private Forwarder c2s;
	private Forwarder s2c;
	private boolean pendingWelcome;
	private long timeWelcome;

	private int gotoAckBlock;
	private ArrayList<Location> pendingGoto = new ArrayList<Location>();
	private long timeGoto;
	private ArrayList<PLAYERSHOOT_Packet> pendingPlayerShoot = new ArrayList<PLAYERSHOOT_Packet>();
	private long pendingPlayerShoot_lastShot;
	private int pendingPlayerShoot_interval;
	private int pendingPlayerShoot_shotsPerInterval;

	public static final int SEND = 0;
	public static final int NO_SEND = 1;
	public static final int NO_PRINT = 2;
	public World world;
	private boolean blockAllyShoot;
	long start;
	private boolean almostDead;
	private final static ArrayList<Integer> classes = new ArrayList<Integer>();
	Plotter p = new Plotter();
	private FileOutputStream log;
	
	private ArrayList<spam> spamlist = new ArrayList<spam>();
	
	int donedone=0;
	
	public int spam_findinlist(String name)
	{
	  
	  for(int i=0;i<spamlist.size();i+=1)
	  {
		  if (spamlist.get(i).name.equals(name)==true)
		  {
		    return i;	  
		  }
	  }
	  return -1;
	}
	
	public void spam_countdown(int t)
	{
	  
	  for(int i=0;i<spamlist.size();i+=1)
	  {
		  spamlist.get(i).countdown-=t;
		  
	  }
	  
	}
	
	public void spam_countdown()
	{
	  
	  int doo=1;
	  while (doo==1)
	  {
	  doo=0;
	  for(int i=0;i<spamlist.size();i+=1)
	  {
		  if (spamlist.get(i).countdown<=0)
		  {
			  doo=1;
			  spamlist.remove(i);
			  break;
		  }
	  }
	  }
	}
	

	static {
		classes.add(0x325);
		classes.add(0x324);
		classes.add(0x323);
		classes.add(0x322);
		classes.add(0x321);
		classes.add(0x320);
		classes.add(0x31F);
		classes.add(0x31E);
		classes.add(0x31D);
		classes.add(0x310);
		classes.add(0x30E);
		classes.add(0x307);
		classes.add(0x300);
	}

	public Connection(Socket s) throws UnknownHostException, IOException {
		System.out.println("next: " + nextAddress);
		try {
			this.serverConnection = new Socket(nextAddress, 2050);
		} catch (IOException ioe) {
			nextAddress = realServerAddress_NEXUS;
			throw ioe;
		}
		//nextAddress = realServerAddress_NEXUS;
		this.clientConnection = s;
		c2s = new Forwarder("c2s", this, clientConnection.getInputStream(), serverConnection.getOutputStream(), prox.SERVERKEY, true);
		s2c = new Forwarder("s2c", this, serverConnection.getInputStream(), clientConnection.getOutputStream(), prox.CLIENTKEY, false);
		c2s.start();
		s2c.start();
		start = System.currentTimeMillis();
		log = new FileOutputStream(System.currentTimeMillis() + " - log.txt");
	}
	
	public void log(String s, boolean toServer) {
		synchronized(log) {
			try {
				if (toServer) {
					log.write(("<<< " + currentTime() + " | " + s + "\n").getBytes());
				} else {
					log.write((">>> " + currentTime() + " | " + s + "\n").getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Socket getServerConnection() {
		return serverConnection;
	}

	public Socket getClientConnection() {
		return clientConnection;
	}

	public void close() {
		p.finish();
		try {
			clientConnection.close();
		} catch (Exception e) {
		}
		try {
			serverConnection.close();
		} catch (Exception e) {
		}
	}

	static Random rand = new Random();

	public static float getFuzz() {
		return rand.nextFloat();
	}

	int count = 0;
	long resetAt = 0;
	long lastKnownTime;
	long lastDone;

	// int prevMoveTick;
	public int update(int packetId, RWable rw, boolean isClient) throws IOException {
		if (rw instanceof HELLO_Packet) {
			HELLO_Packet hp = (HELLO_Packet) rw;
//			hp.gameId_ = 520;
//			hp.keyTime_ = 1338968337;
//			hp.key_ = DatatypeConverter.parseHexBinary("04ABCCFCA277D0F313B62F0006147065BF8E97412328ABC1EA3BCEA3C65E7E95");
//			hp.
			//return SEND;
		}
		// BEGIN changing timestamps
		// if (packetId == Packets.MOVE) {
		// MOVE_Packet p = (MOVE_Packet) rw;
		// // if (p.tickId_ == prevMoveTick) {
		// // return NO_SEND; // dont send more than one move packet per tick
		// // }
		// // prevMoveTick = p.tickId_;
		// for (LocationWTime lw : p.records_) {
		// lw.time = currentTime() + lw.time - p.time_;
		// }
		// }
		// try {
		// rw.getClass().getDeclaredField("time_").set(rw, currentTime());
		// } catch (Exception e) {
		// // e.printStackTrace();
		// }
		// END changing timestamps

		if (packetId == Packets.RECONNECT) {
			RECONNECT_Packet p = (RECONNECT_Packet) rw;
			if (p.host_.length() != 0) {
				System.out.println(p.host_);
				nextAddress = p.host_.split("-", 2)[1].split("\\.")[0].replaceAll("-", ".");
				p.host_ = "127.0.0.1"; // send them to us :)
			} else {
				System.out.println(serverConnection.getInetAddress().getHostAddress());
				nextAddress = serverConnection.getInetAddress().getHostAddress();
				p.host_ = "127.0.0.1"; // send them to us :)
			}
			almostDead = false;
			return SEND;
		}

		// if (packetId == Packets.MOVE && world.currentPlayerLocation != null) {
		// System.out.println(world.currentPlayerLocation.distanceTo(((MOVE_Packet)rw).newPosition_));
		// }

		// BEGIN updating world data
		if (packetId == Packets.MAPINFO) {
			MAPINFO_Packet p = (MAPINFO_Packet) rw;
			this.world = new World(this, p);
		}
		if (packetId == Packets.UPDATE) {
			UPDATE_Packet p = (UPDATE_Packet) rw;
			//this.world.parseUpdate(p);
		}
		if (packetId == Packets.NEW_TICK) {
			NEW_TICK_Packet p = (NEW_TICK_Packet) rw;
			
			//new spam filter
			//
			//System.out.println(p.tickTime_);
			spam_countdown(p.tickTime_);
			spam_countdown();
			
			this.world.parseTick(p);
		}
		if (packetId == Packets.PLAYERSHOOT) {
			PLAYERSHOOT_Packet p = (PLAYERSHOOT_Packet) rw;

			//System.out.println(this.world.currentPlayerLocation);
			//float startx=(float)115.50421;
			//float endx=(float)115.8042;
			float startx=(float)147.99503;
			float endx=(float)147.99763;
			float offsetx=0;
			float result;
			int count=0;
			
			Location pos1 = new Location((float)115.50421, (float)147.99503);
			//Location pos2 = new Location((float)115.8042, (float)147.99763);
			
		
			Location pos2 = new Location((float)115.5962, (float)147.70947);
			
			
			if (donedone==0)
			{
			
			//System.out.println(147.99503 + (float)(0.29999968*Math.cos(0.008722128) ));
			//System.out.println((float)115.50421 + (float)(34.395725*Math.sin(0.008722128) ));
			//System.out.println(115.50421-115.8042);
			//System.out.println(147.99503-147.99763);
				
			
			//System.out.println(115.50421-115.5962);
			//System.out.println(pos1.distanceToSq(pos2));
			//System.out.println((float)115.50421 + (float)(0.09000222384929657*Math.sin(0.008722128) ));
			//System.out.println(0.09000222384929657);
			while(1==1)
			{
				result= startx + (float)(offsetx*Math.sin(0.008722128));
				offsetx+=0.001;
				if (result==(float)endx)
				{
					//System.out.println(offsetx);
				 break;
				}
				/*
				 try {
					    Thread.sleep(1);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
					*/
				count+=1;
				//System.out.println(result);
				//System.out.println(offsetx);
				//System.out.println(count);
			}
			donedone=1;
			}
			//System.out.println(115.50421 - 0.5*Math.sin(0.008722128));
			//System.out.println("done");
			this.world.parsePlayerShoot(p);
		}
		if (packetId == Packets.SHOOT) {
			SHOOT_Packet p = (SHOOT_Packet) rw;
			this.world.parseShoot(p);
		}
		/*
		if (packetId == Packets.SHOOTMULTI) {
			SHOOTMULTI_Packet p = (SHOOTMULTI_Packet) rw;
			System.out.println(p + " | " + world.objectById(p.ownerId_));
			this.world.parseShootMulti(p);
		}
		*/
		if (packetId == Packets.MOVE) {
			MOVE_Packet p = (MOVE_Packet) rw;
//			MoveSendThreadPacket mstp = new MoveSendThreadPacket();
//			mstp.name = world.us.getName();
//			mstp.newPosition_ = p.newPosition_.clone();
//			prox.mst.sendQueue.add(mstp);
			// this.p.plotR((int) (p.newPosition_.x*10), (int) (p.newPosition_.y*10));
			this.world.parsePlayerMove(p);
		}
		if (packetId == Packets.GOTO) {
			GOTO_Packet p = (GOTO_Packet) rw;
			this.world.parseGoto(p);
		}
		// END updating world data

		// BEGIN Death prevention
		if (!almostDead && world != null && world.getPlayerObjectStatus() != null && world.getPlayerObjectStatus().objStatData.getHealthPercent() < 40 && !world.name_.equalsIgnoreCase("nexus")) {
			for (ObjectStatus os : world.objects_) {
				if (os.objStatData.getHealthPercent() > 60) {
					if (classes.contains(os.objectType)) {
						sendTeleport(os.objStatData.objectId);
						sendEscape();
						almostDead = true;
						break;
					} else {
						sendEscape();
						almostDead = true;
						break;
					}
				}
			}
		}
		if (packetId == Packets.PLAYERHIT && almostDead && !world.name_.equalsIgnoreCase("nexus")) {
			// sendEscape();
			return NO_SEND; // lets try not die.
		}
		if (packetId == Packets.PLAYERHIT && System.currentTimeMillis() - resetAt > 0) {
			count++;
			resetAt = System.currentTimeMillis() + 7000;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return NO_SEND; // lets try not die.
			// sendEscape();
		}
		// END Death prevention

		// BEGIN check pendings
		if (!isClient && s2c.prevPacketId == Packets.NEW_TICK && this.pendingGoto.size() > 0 && System.currentTimeMillis() > this.timeGoto) {
			this.timeGoto = System.currentTimeMillis() + 25;
			Location l = this.pendingGoto.remove(0);
			// if (pendingGoto.size() == 0)
			sendGoto(l);
			// sendMove(l);
			// if (packetId == Packets.MOVE)
			// return NO_SEND;
		}
		if (pendingPlayerShoot.size() > 0 && System.currentTimeMillis() - pendingPlayerShoot_lastShot > pendingPlayerShoot_interval) {
			pendingPlayerShoot_lastShot = System.currentTimeMillis();

			for (int i = 0; i < pendingPlayerShoot_shotsPerInterval; i++) {
				c2s.queue.add(pendingPlayerShoot.remove(0));
			}
		}
		
		
		if (rw instanceof TEXT_Packet) {
			int index;
			TEXT_Packet tp = (TEXT_Packet) rw;
			index=spam_findinlist(tp.name_);
			if (index==-1)
			{
				spam sp = new spam();
				sp.name=tp.name_;
				sp.messages.add(tp.text_);
				sp.countdown=15*1000;
				spamlist.add(sp);
			}
			else
			{
				//System.out.println(1234);
				spam sp =spamlist.get(index);
				int found=0;
				for(int i=0;i<sp.messages.size();i++)
				{
				  if (sp.messages.get(i).equals(tp.text_)==true)
				  {
				    //sp.messages.add(tp.text_);
					found=1;
					sp.spamcount++;
				  }
				  //System.out.println(sp.messages.get(i));
				}
				if (found==0)
				{
					sp.messages.add(tp.text_);
				}
				sp.countdown=15*1000;
				if (sp.spamcount>=4)
				{
					EDITACCOUNTLIST_Packet ealp = new EDITACCOUNTLIST_Packet();
					ealp.objectId_=tp.objectId_;
					ealp.add_=true;
					ealp.accountListId_=1;
					c2s.writePacket( ealp );
					c2s.out.flush();
					System.out.println(tp.name_);
					System.out.println(tp.text_);
				}
				
			}
			
			///System.out.println(spamlist.size());
			//System.out.println(index);
			//System.out.println(tp.name_);
			//System.out.println(tp.text_);
		}

		// END check pendings

		if (isClient) {
			// NEW_TICK_Packet p = (NEW_TICK_Packet) rw;
			// if (world.currentPlayerLocation != null && System.currentTimeMillis() - lastDone > 2200 && world.charid == 1) {
			// lastDone = System.currentTimeMillis();
			// int time = (int) world.currentTime();
			// int inter = 300;
			// // pendingPlayerShoot_interval = 100;
			// // pendingPlayerShoot_shotsPerInterval = 2;
			// // for (int i = 0 ; i < 10 ; i++) {
			// // PLAYERSHOOT_Packet ps = new PLAYERSHOOT_Packet();
			// // ps.time_ = time+(inter*((int)i/2));
			// // ps.bulletId_ = nextClientBulletId();
			// // ps.containerType_ = 2714;
			// // ps.startingPos_ = world.currentPlayerLocation.asLocation();
			// // ps.angle_ = -2.0420353f;
			// // pendingPlayerShoot.add(ps);
			// // }
			// sendPlayerShoot(time+(inter*1), -2.0420353f);
			// sendPlayerShoot(time+(inter*1), -1.4137167f);
			// sendPlayerShoot(time+(inter*2), -0.7853982f);
			// sendPlayerShoot(time+(inter*2), -0.15707964f);
			// sendPlayerShoot(time+(inter*3), 0.4712389f);
			// sendPlayerShoot(time+(inter*3), 1.0995574f);
			// sendPlayerShoot(time+(inter*4), 1.727876f);
			// sendPlayerShoot(time+(inter*4), 2.3561945f);
			// sendPlayerShoot(time+(inter*5), 2.984513f);
			// sendPlayerShoot(time+(inter*5), -2.6703537f);
			// }
		}

		if (this.pendingWelcome && System.currentTimeMillis() > this.timeWelcome) {
			this.pendingWelcome = false;
			sendNotification(this.world.myid, Color.yellow.getRGB(), "Test!!");
		}

		if (packetId == Packets.ALLYSHOOT && blockAllyShoot) {
			return NO_SEND;
		}
		if (packetId == Packets.CREATE) {
			CREATE_Packet p = (CREATE_Packet) rw;
			System.out.println("Created a " + CREATE_Packet.getName(p.objectType_));
			return NO_PRINT;
		}
		if (packetId == Packets.SETCONDITION) {
			SETCONDITION_Packet p = (SETCONDITION_Packet) rw;
			p.conditionDuration_ = 0;
			// System.out.println("Created a " + CREATE_Packet.getName(p.objectType_));
			return SEND;
			// return NO_SEND;
		}
		if (packetId == Packets.CREATE_SUCCESS) {
			pendingGoto.clear();
			this.world.myid = ((CREATE_SUCCESS_Packet) rw).objectId_;
			this.world.charid = ((CREATE_SUCCESS_Packet) rw).charId_;
			System.out.println("Player ID: " + this.world.myid + ", Character ID: " + ((CREATE_SUCCESS_Packet) rw).charId_);
			pendingWelcome = true;
			timeWelcome = System.currentTimeMillis() + 2000;
			return NO_PRINT;
		}
		if (packetId == Packets.GOTOACK && gotoAckBlock > 0) {
			gotoAckBlock--;
			return NO_SEND;
		}
		if (packetId == Packets.PLAYERTEXT) {
			
			PLAYERTEXT_Packet p = (PLAYERTEXT_Packet) rw;
			System.out.println(p.text_);
			
			if (p.text_.equalsIgnoreCase(".tph")) {
				for (float i = 120f; i < 144f; i += 3.5)
					pendingGoto.add(new Location(134f, i));
				return NO_SEND;
			}
			if (p.text_.equalsIgnoreCase(".dc")) {
				close();
				return NO_SEND;
			}
			if (p.text_.equalsIgnoreCase(".ald")) {
				blockAllyShoot = true;
				return NO_SEND;
			}
			if (p.text_.equalsIgnoreCase(".b")) {
				BUY_Packet bp = new BUY_Packet();
				bp.objectId_ = 2547;
				c2s.writePacket(bp);
				c2s.out.flush();
				return NO_SEND;
			}
			if (p.text_.startsWith(".con")) {
				RECONNECT_Packet rc = new RECONNECT_Packet();
				rc.gameId_ = -2;
				rc.host_ = "127.0.0.1"; //p.text_.substring(5); //"127.0.0.1";
				rc.port_ = 2050;
				rc.key_ = new byte[0];
				rc.keyTime_ = -1;
				rc.name_ = "Direct Connection";
				nextAddress = p.text_.substring(5);
				s2c.writePacket(rc);
				s2c.out.flush();
				return NO_SEND;
			}
			if (p.text_.equalsIgnoreCase(".ale")) {
				blockAllyShoot = false;
				return NO_SEND;
			}
			if (p.text_.equalsIgnoreCase(".se")) {
				ByteArrayDataOutput bado = new ByteArrayDataOutput();
				SHOW_EFFECT_Packet se = new SHOW_EFFECT_Packet();

				p.writeToOutput(bado);
				s2c.writePacket(Packets.SHOW_EFFECT, bado.getArray());
				s2c.out.flush();
				return NO_SEND;
			}
			if (p.text_.equalsIgnoreCase(".test")) {
				int time = (int) world.currentTime();
				int inter = 300;
				sendPlayerShoot(time + (inter * 1), -2.0420353f);
				sendPlayerShoot(time + (inter * 1), -1.4137167f);
				sendPlayerShoot(time + (inter * 2), -0.7853982f);
				sendPlayerShoot(time + (inter * 2), -0.15707964f);
				sendPlayerShoot(time + (inter * 3), 0.4712389f);
				sendPlayerShoot(time + (inter * 3), 1.0995574f);
				sendPlayerShoot(time + (inter * 4), 1.727876f);
				sendPlayerShoot(time + (inter * 4), 2.3561945f);
				sendPlayerShoot(time + (inter * 5), 2.984513f);
				sendPlayerShoot(time + (inter * 6), -2.6703537f);
			}
			if (p.text_.startsWith("."))
				return NO_SEND;
		}

		return SEND;
	}

	public void sendPlayerShoot(int time, float angle) throws IOException {
		ByteArrayDataOutput bado = new ByteArrayDataOutput();
		PLAYERSHOOT_Packet ps = new PLAYERSHOOT_Packet();
		ps.time_ = time;
		ps.bulletId_ = nextClientBulletId();
		ps.containerType_ = 2714;
		ps.startingPos_ = world.currentPlayerLocation.asLocation();
		ps.angle_ = angle;
		System.out.println("FAKE: " + ps);
		ps.writeToOutput(bado);
		c2s.writePacket(Packets.PLAYERSHOOT, bado.getArray());
		c2s.out.flush();
	}

	byte clientBulletId = 0;

	private int nextClientBulletId() {
		if (clientBulletId++ > 80)
			clientBulletId = 0;
		return clientBulletId;
	}

	public void sendEscape() throws IOException {
		ByteArrayDataOutput bado = new ByteArrayDataOutput();
		ESCAPE_Packet esc = new ESCAPE_Packet();
		System.out.println("FAKE: " + esc);
		esc.writeToOutput(bado);
		c2s.writePacket(Packets.ESCAPE, bado.getArray());
		c2s.out.flush();
	}

	public int currentTime() {
		return (int) (System.currentTimeMillis() - start);
	}

	public void sendMove(Location l) throws IOException {
		sendMove(l.x, l.y);
	}

	private void sendMove(float x, float y) throws IOException {
		ByteArrayDataOutput bado = new ByteArrayDataOutput();
		MOVE_Packet mv = new MOVE_Packet();
		mv.newPosition_ = new Location(x, y);
		mv.tickId_ = world.currentTickId;
		mv.time_ = (int) (this.world.currentTime() + 10);
		mv.records_ = new LocationWTime[] { world.currentPlayerLocation };
		world.currentPlayerLocation = new LocationWTime(mv.time_, mv.newPosition_);
		System.out.println("FAKE: " + mv);
		mv.writeToOutput(bado);
		c2s.writePacket(Packets.MOVE, bado.getArray());
		c2s.out.flush();
	}

	public void sendTeleport(int objectId) throws IOException {
		ByteArrayDataOutput bado = new ByteArrayDataOutput();
		TELEPORT_Packet tp = new TELEPORT_Packet();
		tp.objectId_ = objectId;
		System.out.println("FAKE: " + tp);
		tp.writeToOutput(bado);
		c2s.writePacket(Packets.TELEPORT, bado.getArray());
		c2s.out.flush();
	}

	public void sendNotification(int id, int rgb, String text) throws IOException {
		ByteArrayDataOutput bado = new ByteArrayDataOutput();
		NOTIFICATION_Packet p = new NOTIFICATION_Packet();
		p.text_ = text;
		p.objectId_ = id;
		p.color_ = rgb;
		p.writeToOutput(bado);
		s2c.writePacket(Packets.NOTIFICATION, bado.getArray());
		s2c.out.flush();
	}

	public void sendGoto(Location l) throws IOException {
		sendGoto(l.x, l.y);
	}

	public void sendGoto(float x, float y) throws IOException {
		gotoAckBlock++;
		ByteArrayDataOutput bado = new ByteArrayDataOutput();
		GOTO_Packet gotoP = new GOTO_Packet();
		gotoP.pos_ = new Location(x + getFuzz(), y + getFuzz());
		gotoP.objectId_ = this.world.myid;
		gotoP.writeToOutput(bado);
		System.out.println("FAKE: " + gotoP);
		s2c.writePacket(Packets.GOTO, bado.getArray());
		s2c.out.flush();
	}

}
