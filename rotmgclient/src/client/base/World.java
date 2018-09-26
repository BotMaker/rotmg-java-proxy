package client.base;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import data.Location;
import data.ObjectStatus;
import data.ObjectStatusData;
import data.StatData;
import data.Tile;

import packets.client.CHOOSENAME_Packet;
import packets.client.TELEPORT_Packet;
import packets.server.CREATE_SUCCESS_Packet;
import packets.server.GOTO_Packet;
import packets.server.MAPINFO_Packet;
import packets.server.NEW_TICK_Packet;
import packets.server.UPDATE_Packet;
// import util.MassRegister;

public class World {

	private int width_;
	private int height_;
	private String name_;
	private int fp_;
	private int background_;
	private Boolean allowPlayerTeleport_;
	private Boolean showDisplays_;
	private ArrayList<String> clientXML_;
	private ArrayList<String> extraXML_;
	public ArrayList<ObjectStatus> objects = new ArrayList<ObjectStatus>();
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	private int currentTick;
	private int prevTickTime;
	private GameConnection gCon;
	private GameClient gCli;
	public int myid;
	public int charid;
	private ObjectStatus us;
	private Location position;
	private long lastTeleport;
	private String nameToChoose;
	
	public AtomicInteger bulletIdCounter = new AtomicInteger();

	public World(MAPINFO_Packet mip, GameConnection gCon, GameClient gCli) {
		this.gCon = gCon;
		this.gCli = gCli;
		
		this.width_ = mip.width_; // int
		this.height_ = mip.height_; // int
		this.name_ = mip.name_; // String
		this.fp_ = mip.fp_; // uint
		this.background_ = mip.background_; // int
		this.allowPlayerTeleport_ = mip.allowPlayerTeleport_; // Boolean
		this.showDisplays_ = mip.showDisplays_; // Boolean
		this.clientXML_ = mip.clientXML_; // Vector.<String>
		this.extraXML_ = mip.extraXML_; // Vector.<String>
		bulletIdCounter.set(0);
	}

	public void parseNewTick(NEW_TICK_Packet ntp) {
		this.currentTick = ntp.tickId_;
		this.prevTickTime = ntp.tickTime_;

		//System.out.println(ntp.statuses_);
		ArrayList<ObjectStatus> objs = getObjectsClone();
		
		for (ObjectStatusData osd : ntp.statuses_) {
			for (ObjectStatus os : objs) {
				if (os.objStatData.objectId == osd.objectId) {
					
					os.update(osd);
				}
			}
			//System.out.println(osd.objectId);
		}
		this.objects = objs;
	}

	public void parseUpdate(UPDATE_Packet up) {
		ArrayList<ObjectStatus> objs = getObjectsClone();
		for (int i : up.drops_) {
			for (ObjectStatus os : objs) {
				if (os.objStatData.objectId == i) {
					objs.remove(os);
					for (GameListener gl : gCon.listeners)
						gl.objectRemoved(os);
					break;
				}
			}
		}
		for (ObjectStatus os : up.newObjs_) {
			objs.add(os);
			if (os.objStatData.objectId == myid) {
				us = os;
				System.out.println(os.objStatData.objectId);
				this.position = us.objStatData.position.clone();
				if (nameToChoose != null && os.objStatData.getStatData(56).numValue == 0) {// if dont have name
					CHOOSENAME_Packet cn = new CHOOSENAME_Packet();
					cn.name_ = nameToChoose;
					
					gCon.sendQueue.add(cn);
				}
			}
			for (GameListener gl : gCon.listeners)
				gl.objectAdded(os);
		}
		this.objects = objs;
		
		ArrayList<Tile> ti = getTilesClone();
		ti.addAll(up.tiles_);
		this.tiles = ti;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ObjectStatus> getObjectsClone() {
		return (ArrayList<ObjectStatus>) objects.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Tile> getTilesClone() {
		return (ArrayList<Tile>) tiles.clone();
	}
	
	public int getCurrentTick() {
		return currentTick;
	}

	public int getPrevTickTime() {
		return prevTickTime;
	}

	public void parseCreateSuccess(CREATE_SUCCESS_Packet csp) {
		this.myid = csp.objectId_;
		this.charid = csp.charId_;
	}

	public Location getPosition() {
		return position != null ? position.clone() : null;
	}
	
	public ObjectStatus getObjectById(int objectId_) {
		for (ObjectStatus os : gCon.world.objects) {
			if (os.objStatData.objectId == objectId_) {
				return os;
			}
		}
		return null;
	}
	
	public ObjectStatus getObjectByName(String name) {
		for (ObjectStatus os : gCon.world.objects) {
			if (os.objStatData.getStatData(StatData.NAME) != null && os.objStatData.getStatData(StatData.NAME).stringValue.equals(name)) {
				return os;
			}
		}
		return null;
	}
	
	public int getNextBulletId() {
		if (bulletIdCounter.getAndIncrement() >= 127)
			bulletIdCounter.set(0);
		return bulletIdCounter.get();
	}

	public ObjectStatus getUs() {
		return us;
	}

	public void setPosition(Location newPosition_) {
		position = newPosition_.clone();
	}
	
	public void parseGoto(GOTO_Packet gp) {
		gCon.world.getObjectById(gp.objectId_).objStatData.position = gp.pos_;
		if (gp.objectId_ == myid) {
			this.position = gp.pos_.clone();
		}
	}

	public boolean isTeleportAvailable() {
		return System.currentTimeMillis() - lastTeleport > 10000;
	}

	public void teleport(int objectId) {
		lastTeleport = System.currentTimeMillis();
		TELEPORT_Packet tp = new TELEPORT_Packet();
		tp.objectId_ = objectId;
		gCon.sendQueue.add(tp);
	}

	public void chooseName(String nameToChoose) {
		this.nameToChoose = nameToChoose;
	}
}
