import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import packets.client.MOVE_Packet;
import packets.client.PLAYERSHOOT_Packet;
import packets.server.GOTO_Packet;
import packets.server.MAPINFO_Packet;
import packets.server.NEW_TICK_Packet;
import packets.server.SHOOTMULTI_Packet;
import packets.server.SHOOT_Packet;
import packets.server.UPDATE_Packet;
import data.LocationWTime;
import data.ObjectStatus;
import data.ObjectStatusData;
import data.StatData;
import data.Tile;

public class World {
	public LocationWTime currentPlayerLocation;
	public int myid;
	public int width_; // int
	public int height_; // int
	public String name_; // String
	public int fp_; // uint
	public int background_; // int
	public Boolean allowPlayerTeleport_; // Boolean
	public Boolean showDisplays_; // Boolean
	public ArrayList<String> clientXML_; // Vector.<String>
	public ArrayList<String> extraXML_; // Vector.<String>

	public ArrayList<Tile> tiles_ = new ArrayList<Tile>(); // Vector.<_-MV>
	public ArrayList<ObjectStatus> objects_ = new ArrayList<ObjectStatus>(); // Vector.<_-Oe>
	public int currentTickId;
	public int currentTickTime;
	private long startTime;
	public ArrayList<Bullet> bullets_ = new ArrayList<Bullet>();
	public ObjectStatus us;
	private Connection con;
	public int charid;
	private int lastMove;
	private FileOutputStream fosmap;
	private FileOutputStream fosobj;

	public long currentTime() {
		return System.currentTimeMillis() - startTime;
	}

	public World(Connection con, MAPINFO_Packet p) {
		this.con = con;
		this.width_ = p.width_; // int
		this.height_ = p.height_; // int
		this.name_ = p.name_; // String
		this.fp_ = p.fp_; // uint
		this.background_ = p.background_; // int
		this.allowPlayerTeleport_ = p.allowPlayerTeleport_; // Boolean
		this.showDisplays_ = p.showDisplays_; // Boolean
		this.clientXML_ = p.clientXML_; // Vector.<String>
		this.extraXML_ = p.extraXML_; // Vector.<String>
		File f = new File("servers/" + con.getServerConnection().getInetAddress().getHostAddress() + " - " + name_);
		File map = new File("maps/" + con.getServerConnection().getInetAddress().getHostAddress() + " - tile - " + name_);
		File obj = new File("maps/" + con.getServerConnection().getInetAddress().getHostAddress() + " - objects - " + name_);
		f.getParentFile().mkdirs();
		map.getParentFile().mkdirs();
		try {
			fosmap = new FileOutputStream(map);
			fosobj = new FileOutputStream(obj);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (!f.exists()) {
			try {
				new FileOutputStream(f).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void parseUpdate(UPDATE_Packet p) {
		for (int i : p.drops_) {
			for (ObjectStatus os : objects_) {
				if (os.objStatData.objectId == i) {
					objects_.remove(os);
					break;
				}
			}
		}
		for (Tile t : p.tiles_) {
			tiles_.add(t);
			// TODO add handling
			
			try {
				fosmap.write((t.x + " " + t.y + " " + t.type +"\n").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (p.tiles_.size() > 0) {
			try {
				fosmap.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (ObjectStatus os : p.newObjs_) {
			objects_.add(os);
			if (os.objectType == 0x30e) {//49 52 56
				System.out.println(os.objStatData.getStatData(49) + " | "+ os.objStatData.getStatData(52) + " | " + os.objStatData.getStatData(56) + " | " + os.objStatData.getStatData(31));
			}
			if (os.objStatData.objectId == myid) {
				System.out.println("found us");
				us = os;
			}
			// TODO add handling
		}
	}

	public void parseTick(NEW_TICK_Packet p) {
		this.currentTickId = p.tickId_;
		// for (ObjectStatusData osd : p.statuses_) {
		// for (ObjectStatus os : objects_) {
		// if (os.objStatData.objectId == osd.objectId) {
		// os.update(osd);
		// // if (os.objStatData.position.distanceTo(getPlayerObjectStatus().objStatData.position) < 15) {
		// if (os.objStatData.getStatData(StatData.HEALTH) != null && os.objStatData.getStatData(StatData.MAXHEALTH) != null) {
		// // try {
		// //// if (os.objStatData.getStatData(StatData.HEALTH).numValue != os.objStatData.getStatData(StatData.MAXHEALTH).numValue)
		// //// con.sendNotification(os.objStatData.objectId, Color.ORANGE.getRGB(), os.objStatData.getStatData(StatData.HEALTH).numValue + " / " + os.objStatData.getStatData(StatData.MAXHEALTH).numValue);
		// // } catch (IOException e) {
		// // e.printStackTrace();
		// // }
		// }
		// // }
		// }
		// }
		// }
		this.currentTickTime = p.tickTime_;
	}

	public void parsePlayerShoot(PLAYERSHOOT_Packet p) {
		double timeSinceLastShot = p.time_ - lastShot;
		//System.out.println("tsls: " + timeSinceLastShot);
		lastShot = p.time_;
	}

	public void parsePlayerMove(MOVE_Packet p) {
		// System.out.println("Diff: " + ((System.currentTimeMillis() - p.time_) - startTime));

		double timeSinceLastMove = p.time_ - lastMove;
		if (currentPlayerLocation != null) {
			// System.out.println("200: " + ((currentPlayerLocation.distanceTo(p.newPosition_) / timeSinceLastMove * 200)-(1d/67d*10)));
		}

		startTime = System.currentTimeMillis() - p.time_;
		//if (currentPlayerLocation == null || !p.newPosition_.equals(currentPlayerLocation)) {
			currentPlayerLocation = new LocationWTime(p.time_, p.newPosition_);
		//}

		lastMove = p.time_;
	}

	public void parseGoto(GOTO_Packet p) {
		for (ObjectStatus os : objects_) {
			os.objStatData.position = p.pos_;
			break;
		}
	}

	long lastShot;

	public void parseShoot(SHOOT_Packet p) {
	}

	public void parseShootMulti(SHOOTMULTI_Packet p) {

	}

	public ObjectStatus getPlayerObjectStatus() {
		return us;
		// for (ObjectStatus os : objects_)
		// if (os.objStatData.objectId == myid) {
		// System.out.println(os);
		// return os;
		// }
		// return null;
	}

	public ObjectStatus objectById(int ownerId_) {
		for (ObjectStatus os : objects_) {
			if (os.objStatData.objectId == ownerId_)
				return os;
		}
		return null;
	}

}
