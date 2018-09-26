package client.multibox;

import client.GameValues;
import client.base.GameClient;
import client.base.GameConnection;
import client.base.GameListener;
import client.base.MoveClass;
import client.base.MoveFollower;
import packets.RWable;
import packets.client.USEPORTAL_Packet;
import data.Location;
import data.ObjectStatus;
import data.StatData;

public class MultiBoxListener implements GameListener, MoveClass {
	private GameConnection gCon;
	private GameClient gCli;
	private MoveFollower mf;
	private String name = "SomeFrog";
	//private String name = "Dbsdbajdbj";
	//private String name = "PiPEsMoKeR";
	//private String name = "Vectorhyp";
	//private String name = "Spacehyp";
	//private String name = "KingSensei";
	//private String name = "LoveMYwig";
	//private String name = "OSX";
	//private String name = "TonyDee";
	//private String name = "McFalco";
	//private String name = "ManNYC";//"TheWhom";
	//private String name = "WheatGerm";
	//private String name = "FreezeLife";
	//private String name = "Brystin";
	//private String name = "Brydon";
	//private String name = "Garsh";
	//private String name = "LRMSS";
	//private String name = "Godsey";
	//private String name = "ScareOwner";
	//private String name = "Maaaaa";
	//private String name = "TheoryD";
	//private String name = "HighPixel";
	//private String name = "JarJarDink";
	//private String name = "EpicWinnn";
	//private String name = "Jim";
	//private String name = "GodXZY";
	//private String name = "RockTheory";
	//private String name = "DangerC";
	//private String name = "Kalle";
	//private String name = "Bluenoser";
	private ObjectStatus walkToPortal;
	private long usePortalTime;
	

	public MultiBoxListener(GameConnection gCon, GameClient gameClient) {
		this.gCon = gCon;
		this.gCli = gameClient;
		
		this.mf = new MoveFollower(gCon, gCli);
		mf.setTeleport(true);
		mf.setFollowDist(0);
		mf.setFollowTarget(name);
		gCon.moveClazz = this;
	}

	@Override
	public void loop() {
		if (gCon.world == null || gCon.world.getPosition() == null) {
			return;
		}
		
		
		if (walkToPortal != null && gCon.world.getPosition().distanceToSq(walkToPortal.objStatData.position) < 2.25 && System.currentTimeMillis() - usePortalTime > 5000) {
			USEPORTAL_Packet upp = new USEPORTAL_Packet();
			upp.objectId_ = walkToPortal.objStatData.objectId;
			gCon.sendQueue.add(upp);
			usePortalTime = System.currentTimeMillis();
		}
		

		
	}

	@Override
	public void packetRecieved(RWable rw) {

	}

	@Override
	public void objectRemoved(ObjectStatus os) {
		if (os.objStatData.getStatData(StatData.NAME) != null && os.objStatData.getStatData(StatData.NAME).stringValue.equals(name)) {
			System.out.println(name + " dissapered.");
			for (ObjectStatus osx : gCon.world.objects) {
				if (os.objStatData.position.distanceToSq(osx.objStatData.position) < 2.25) { // less than 1.5 tiles away
//					System.out.println("A: " + osx);
					if (GameValues.portals.contains(osx.objectType)) {
						System.out.println("They left at " + osx);
						walkToPortal = osx;
					}
				}
			}
		}
	}

	@Override
	public void objectAdded(ObjectStatus os) {

	}

	@Override
	public Location move(int time) {
		if (walkToPortal != null) {
			return mf.getFollowPosition(time, walkToPortal.objStatData.position);
		}
		return mf.move(time);
	}

}
