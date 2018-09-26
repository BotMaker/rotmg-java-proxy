package packets;

import java.util.HashMap;
import java.util.Map;
import packets.server.*;
import packets.client.*;

public class Packets {
//	public static final Map<Integer, String> map = new HashMap<Integer, String>();
	public static final Map<Integer, String> allmap = new HashMap<Integer, String>();
	
	public final static int FAILURE = 0;
	public final static int TRADEACCEPTED = 1;
	public final static int PIC = 3;
	public final static int RECONNECT = 4;
	public final static int UPDATE = 5;
	public final static int OTHERHIT = 6;
	public final static int TEXT = 7;
	public final static int GUILDREMOVE = 8;
	public final static int TRADESTART = 9;
	public final static int BUYRESULT = 10;
	public final static int INVDROP = 11;
	public final static int CHANGETRADE = 12;
	public final static int PLAYSOUND = 13;
	public final static int UPDATEACK = 14;
	public final static int ACCEPTTRADE = 15;
	public final static int SQUAREHIT = 16;
	public final static int HELLO = 17;
	public final static int PLAYERHIT = 18;
	public final static int GROUNDDAMAGE = 19;
	public final static int PLAYERSHOOT = 20;
	public final static int SHOOT = 21;
	public final static int GUILDINVITE = 22;
	public final static int TELEPORT = 23;
	public final static int ENEMYHIT = 24;
	public final static int CANCELTRADE = 25;
	public final static int AOEACK = 26;
	public final static int PONG = 27;
	public final static int SETCONDITION = 28;
	public final static int CHECKCREDITS = 30;
	public final static int SHOOTMULTI = 31;
	public final static int ESCAPE = 33;
	public final static int LOAD = 34;
	public final static int INVITEDTOGUILD = 35;
	public final static int TRADEREQUESTED = 36;
	public final static int MAPINFO = 37;
	public final static int DAMAGE = 38;
	public final static int PLAYERTEXT = 39;
	public final static int CHOOSENAME = 40;
	public final static int PING = 41;
	public final static int TRADEDONE = 42;
	public final static int TRADECHANGED = 44;
	public final static int USEPORTAL = 45;
	public final static int CLIENTSTAT = 46;
	public final static int DEATH = 47;
	public final static int REQUESTTRADE = 48;
	public final static int CREATE = 49;
	public final static int GOTO = 50;
	public final static int GOTOACK = 51;
	public final static int JOINGUILD = 52;
	public final static int ALLYSHOOT = 53;
	public final static int EDITACCOUNTLIST = 55;
	public final static int CLIENTSTAT_FILE = 56;
	public final static int NEW_TICK = 57;
	public final static int USEITEM = 58;
	public final static int CREATE_SUCCESS = 59;
	public final static int CHANGEGUILDRANK = 60;
	public final static int MOVE = 61;
	public final static int AOE = 62;
	public final static int NOTIFICATION = 63;
	public final static int SHOOTACK = 64;
	public final static int INVSWAP = 65;
	public final static int ACCOUNTLIST = 66;
	public final static int NAMERESULT = 67;
	public final static int BUY = 68;
	public final static int CREATEGUILDRESULT = 69;
	public final static int QUESTOBJID = 74;
	public final static int INVRESULT = 75;
	public final static int CREATEGUILD = 76;
	public final static int SHOW_EFFECT = 77;
	
	static {
		initAllMap();
		System.out.println("Init");
	}

	private static void initAllMap() {
		allmap.put(0, "FAILURE");
		allmap.put(1, "TRADEACCEPTED");
		// no 2?
		allmap.put(3, "PIC");
		allmap.put(4, "RECONNECT");
		allmap.put(5, "UPDATE");
		allmap.put(6, "OTHERHIT");
		allmap.put(7, "TEXT");
		allmap.put(8, "GUILDREMOVE");
		allmap.put(9, "TRADESTART");
		allmap.put(10, "BUYRESULT");
		allmap.put(11, "INVDROP");
		allmap.put(12, "CHANGETRADE");
		allmap.put(13, "PLAYSOUND");
		allmap.put(14, "UPDATEACK");
		allmap.put(15, "ACCEPTTRADE");
		allmap.put(16, "SQUAREHIT");
		allmap.put(17, "HELLO");
		allmap.put(18, "PLAYERHIT");
		allmap.put(19, "GROUNDDAMAGE");
		allmap.put(20, "PLAYERSHOOT");
		allmap.put(21, "SHOOT");
		allmap.put(22, "GUILDINVITE");
		allmap.put(23, "TELEPORT");
		allmap.put(24, "ENEMYHIT");
		allmap.put(25, "CANCELTRADE");
		allmap.put(26, "AOEACK");
		allmap.put(27, "PONG");
		allmap.put(28, "SETCONDITION");
		// no 29?
		allmap.put(30, "CHECKCREDITS");
		allmap.put(31, "SHOOTMULTI");
		// no 32?
		allmap.put(33, "ESCAPE");
		allmap.put(34, "LOAD");
		allmap.put(35, "INVITEDTOGUILD");
		allmap.put(36, "TRADEREQUESTED");
		allmap.put(37, "MAPINFO");
		allmap.put(38, "DAMAGE");
		allmap.put(39, "PLAYERTEXT");
		allmap.put(40, "CHOOSENAME");
		allmap.put(41, "PING");
		allmap.put(42, "TRADEDONE");
		// no 43?
		allmap.put(44, "TRADECHANGED");
		allmap.put(45, "USEPORTAL");
		allmap.put(46, "CLIENTSTAT");
		allmap.put(47, "DEATH");
		allmap.put(48, "REQUESTTRADE");
		allmap.put(49, "CREATE");
		allmap.put(50, "GOTO");
		allmap.put(51, "GOTOACK");
		allmap.put(52, "JOINGUILD");
		allmap.put(53, "ALLYSHOOT");
		// no 54?
		allmap.put(55, "EDITACCOUNTLIST");
		allmap.put(56, "CLIENTSTAT_FILE");
		allmap.put(57, "NEW_TICK");
		allmap.put(58, "USEITEM");
		allmap.put(59, "CREATE_SUCCESS");
		allmap.put(60, "CHANGEGUILDRANK");
		allmap.put(61, "MOVE");
		allmap.put(62, "AOE");
		allmap.put(63, "NOTIFICATION");
		allmap.put(64, "SHOOTACK");
		allmap.put(65, "INVSWAP");
		allmap.put(66, "ACCOUNTLIST");
		allmap.put(67, "NAMERESULT");
		allmap.put(68, "BUY");
		allmap.put(69, "CREATEGUILDRESULT");
		// no 70?
		// no 71?
		// no 72?
		// no 73?
		allmap.put(74, "QUESTOBJID");
		allmap.put(75, "INVRESULT");
		allmap.put(76, "CREATEGUILD");
		allmap.put(77, "SHOW_EFFECT");
		System.out.println("Init");
	}

	public static RWable findPacket(int i) {
		if (i == FAILURE) {
			return new FAILURE_Packet();
		} else if (i == TRADEACCEPTED) {
			return new TRADEACCEPTED_Packet();
		} else if (i == PIC) {
			return new PIC_Packet();
		} else if (i == RECONNECT) {
			return new RECONNECT_Packet();
		} else if (i == UPDATE) {
			return new UPDATE_Packet();
		} else if (i == OTHERHIT) {
			return new OTHERHIT_Packet();
		} else if (i == TEXT) {
			return new TEXT_Packet();
		} else if (i == GUILDREMOVE) {
			return new GUILDREMOVE_Packet();
		} else if (i == TRADESTART) {
			return new TRADESTART_Packet();
		} else if (i == BUYRESULT) {
			return new BUYRESULT_Packet();
		} else if (i == INVDROP) {
			return new INVDROP_Packet();
		} else if (i == CHANGETRADE) {
			return new CHANGETRADE_Packet();
		} else if (i == PLAYSOUND) {
			return new PLAYSOUND_Packet();
		} else if (i == UPDATEACK) {
			return new UPDATEACK_Packet();
		} else if (i == ACCEPTTRADE) {
			return new ACCEPTTRADE_Packet();
		} else if (i == SQUAREHIT) {
			return new SQUAREHIT_Packet();
		} else if (i == HELLO) {
			return new HELLO_Packet();
		} else if (i == PLAYERHIT) {
			return new PLAYERHIT_Packet();
		} else if (i == GROUNDDAMAGE) {
			return new GROUNDDAMAGE_Packet();
		} else if (i == PLAYERSHOOT) {
			return new PLAYERSHOOT_Packet();
		} else if (i == SHOOT) {
			return new SHOOT_Packet();
		} else if (i == GUILDINVITE) {
			return new GUILDINVITE_Packet();
		} else if (i == TELEPORT) {
			return new TELEPORT_Packet();
		} else if (i == ENEMYHIT) {
			return new ENEMYHIT_Packet();
		} else if (i == CANCELTRADE) {
			return new CANCELTRADE_Packet();
		} else if (i == AOEACK) {
			return new AOEACK_Packet();
		} else if (i == PONG) {
			return new PONG_Packet();
		} else if (i == SETCONDITION) {
			return new SETCONDITION_Packet();
		} else if (i == CHECKCREDITS) {
			return new CHECKCREDITS_Packet();
		} else if (i == SHOOTMULTI) {
			return new SHOOTMULTI_Packet();
		} else if (i == ESCAPE) {
			return new ESCAPE_Packet();
		} else if (i == LOAD) {
			return new LOAD_Packet();
		} else if (i == INVITEDTOGUILD) {
			return new INVITEDTOGUILD_Packet();
		} else if (i == TRADEREQUESTED) {
			return new TRADEREQUESTED_Packet();
		} else if (i == MAPINFO) {
			return new MAPINFO_Packet();
		} else if (i == DAMAGE) {
			return new DAMAGE_Packet();
		} else if (i == PLAYERTEXT) {
			return new PLAYERTEXT_Packet();
		} else if (i == CHOOSENAME) {
			return new CHOOSENAME_Packet();
		} else if (i == PING) {
			return new PING_Packet();
		} else if (i == TRADEDONE) {
			return new TRADEDONE_Packet();
		} else if (i == TRADECHANGED) {
			return new TRADECHANGED_Packet();
		} else if (i == USEPORTAL) {
			return new USEPORTAL_Packet();
		} else if (i == CLIENTSTAT) {
			return new CLIENTSTAT_FILE_Packet();
		} else if (i == DEATH) {
			return new DEATH_Packet();
		} else if (i == REQUESTTRADE) {
			return new REQUESTTRADE_Packet();
		} else if (i == CREATE) {
			return new CREATE_Packet();
		} else if (i == GOTO) {
			return new GOTO_Packet();
		} else if (i == GOTOACK) {
			return new GOTOACK_Packet();
		} else if (i == JOINGUILD) {
			return new JOINGUILD_Packet();
		} else if (i == ALLYSHOOT) {
			return new ALLYSHOOT_Packet();
		} else if (i == EDITACCOUNTLIST) {
			return new EDITACCOUNTLIST_Packet();
		} else if (i == CLIENTSTAT_FILE) {
			return new CLIENTSTAT_FILE_Packet();
		} else if (i == NEW_TICK) {
			return new NEW_TICK_Packet();
		} else if (i == USEITEM) {
			return new USEITEM_Packet();
		} else if (i == CREATE_SUCCESS) {
			return new CREATE_SUCCESS_Packet();
		} else if (i == CHANGEGUILDRANK) {
			return new CHANGEGUILDRANK_Packet();
		} else if (i == MOVE) {
			return new MOVE_Packet();
		} else if (i == AOE) {
			return new AOE_Packet();
		} else if (i == NOTIFICATION) {
			return new NOTIFICATION_Packet();
		} else if (i == SHOOTACK) {
			return new SHOOTACK_Packet();
		} else if (i == INVSWAP) {
			return new INVSWAP_Packet();
		} else if (i == ACCOUNTLIST) {
			return new ACCOUNTLIST_Packet();
		} else if (i == NAMERESULT) {
			return new NAMERESULT_Packet();
		} else if (i == BUY) {
			return new BUY_Packet();
		} else if (i == CREATEGUILDRESULT) {
			return new CREATEGUILDRESULT_Packet();
		} else if (i == QUESTOBJID) {
			return new QUESTOBJID_Packet();
		} else if (i == INVRESULT) {
			return new INVRESULT_Packet();
		} else if (i == CREATEGUILD) {
			return new CREATEGUILD_Packet();
		} else if (i == SHOW_EFFECT) {
			return new SHOW_EFFECT_Packet();
		}
		return null;
	}
}
