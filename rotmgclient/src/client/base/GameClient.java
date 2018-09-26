package client.base;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import client.GameConsts;
import client.multibox.MultiBoxListener;
import data.Location;

import packets.client.CREATE_Packet;
import packets.client.HELLO_Packet;
import packets.client.LOAD_Packet;
import packets.server.DEATH_Packet;
import packets.server.FAILURE_Packet;
import packets.server.RECONNECT_Packet;
import util.SCry;

public class GameClient implements GameConsts {

	private FileOutputStream log;
	public GameConnection gCon;
	private long startTime;
	private String _accGUID;
	private String _accPass;
	public FAILURE_Packet failed;
	public DEATH_Packet death;
	public static final Random rand = new Random();
	
	public int index;
	public Location tpos;
	

	public GameClient() {
		
		System.out.println("loading client");
		//System.out.println(GameConsts.SPEED_BASE);
		this.gCon = new GameConnection();
		//gCon.
		startTime = System.currentTimeMillis();
		//System.out.println("testing 00");
		//try {
			//log = new FileOutputStream(System.currentTimeMillis() + " - log.txt");
		//} catch (FileNotFoundException e) {
		//	e.printStackTrace();
		//}
		gCon.moveClazz = new StationaryMover(gCon);
		
		
		MoveFollower mf = new MoveFollower(gCon,this);
		mf.setTeleport(true);
		mf.setFollowDist(2);
		mf.setFollowTarget("Vectorhyp");
		//mf.setFollowTarget("Bowsieb");
		gCon.moveClazz = mf;
		
		gCon.addListener(new BasicListener(gCon, this));
		//gCon.connect("50.19.47.160", constructHelloPacket(-2), p);
		//
		//connectNewChar();
		
		//useCredentials("spacetime@gmail.com","azsxdc");
		//connectLoadChar(1);
        //gCon.addListener(new MultiBoxListener(gCon, this));
   		//gCon.addListener();
	}
	
	public void useCredentials(String email, String pass) {
		_accGUID = SCry.encrypt(email);
		_accPass = SCry.encrypt(pass);
	}
	
	public void connectNewChar() {
		gCon.connect(constructHelloPacket(Modes.NORMAL), constructCreatePacket(Characters.WIZARD));
	}
	
	public void connectLoadChar(int charr) {
		gCon.connect(constructHelloPacket(Modes.NORMAL), constructLoadPacket(charr));
	}

	public CREATE_Packet constructCreatePacket(int charClass) {
		CREATE_Packet cp = new CREATE_Packet();
		cp.objectType_ = charClass;
		return cp;
	}

	public LOAD_Packet constructLoadPacket(int charId) {
		LOAD_Packet lp = new LOAD_Packet();
		lp.charId_ = charId;
		return lp;
	}

	public HELLO_Packet constructHelloPacket(int gm) {
		HELLO_Packet hp = new HELLO_Packet();
		hp.buildVersion_ = "16.2";
		hp.gameId_ = gm;
		hp.keyTime_ = -1;
		hp.secret_ = "";
		hp.key_ = new byte[0];
		if (_accGUID == null) {
			//System.out.println("asd");
			//"NOzRFlg8XCIkqD+3wVMgzeKxYqVo2FoI3pwZZCW1cIkqyW++cAB4YvrhqKywRm+1tTqdcWrLL5NH3Z06CU1FVVWujgbbsF1bf/D7ltimgtxUAf4hjdUQ2a9NDR3OFyFm0y4LpaQjB7myhoOBJZm4GTwUZsZqFLpMYqtZ3Pz1Dfo=";// SCry.encrypt("72C9060DB5AA892841EF552EFC3277D8753DB96F")
			_accGUID =SCry.encrypt(SCry.createGuestGUID());
			//_accGUID ="Z+TWxbDpdKsNAKn358XZHOTI4nZEI4/oCrOB7CI8uDYdgWmXN4DKwfCcONgurW+xrGEWoLzviOyg5yRYxaeV55N7OB75OSGtslcs4Tjc8oNRILQG5wofw2YH+MG/jWshUfxC8yQBWJ/Akn+j1ROuS4+CEgjklZjLGC/Oc0EVgDQ=";
			//_accGUID=SCry.encrypt("Q72C9060DB5AA892841EF552EFC3277D8753DB96F");
			//_accGUID="NOzRFlg8XCIkqD+3wVMgzeKxYqVo2FoI3pwZZCW1cIkqyW++cAB4YvrhqKywRm+1tTqdcWrLL5NH3Z06CU1FVVWujgbbsF1bf/D7ltimgtxUAf4hjdUQ2a9NDR3OFyFm0y4LpaQjB7myhoOBJZm4GTwUZsZqFLpMYqtZ3Pz1Dfo=";
			_accPass = "";
		}
		hp.guid_ = getGuid();
		hp.password_ = getPassword();
		hp.jD = "";
		hp.pk = "";
		hp.Tq = "rotmg";
		hp.H = "";
		hp.playPlatform = "rotmg";
		return hp;
	}

	public HELLO_Packet constructHelloPacket(int gm, int keyTime, byte[] key) {
		HELLO_Packet hp = new HELLO_Packet();
		hp.buildVersion_ = "16.2";
		hp.gameId_ = gm;
		hp.keyTime_ = keyTime;
		hp.secret_ = "";
		hp.key_ = key;
		if (_accGUID == null) {
			_accGUID = SCry.encrypt(SCry.createGuestGUID());
			_accPass = "";
		}
		hp.guid_ = getGuid();
		hp.password_ = getPassword();
		hp.jD = "";
		hp.pk = "";
		hp.Tq = "rotmg";
		hp.H = "";
		hp.playPlatform = "rotmg";
		return hp;
	}

	public String getPassword() {
		return _accPass;
	}

	public String getGuid() {
		return _accGUID;
	}

	public static void main(String[] args) {
		GameClient gCli = new GameClient();
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

	public int currentTime() {
		return (int) (System.currentTimeMillis() - startTime);
	}

	
	
	public ArrayList<GameListener> getNewListeners(GameConnection ngCon) {
	
		ArrayList<GameListener> li = new ArrayList<GameListener>();
		li.add(new BasicListener(ngCon, this));
		li.add(new MultiBoxListener(ngCon, this));
		// FIXME
		return li;
		
	}

}
