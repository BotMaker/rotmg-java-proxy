package packets;

import java.util.HashMap;
import java.util.Map;
import packets.server.*;
import packets.client.*;

public class Packets {
//	public static final Map<Integer, String> map = new HashMap<Integer, String>();
	public static final Map<Integer, String> allmap = new HashMap<Integer, String>();
	
	public static final int FAILURE = 0; // slotid = 1
	public static final int CREATE_SUCCESS = 81; // slotid = 2
	public static final int CREATE = 38; // slotid = 3
	public static final int PLAYERSHOOT = 41; // slotid = 4
	public static final int MOVE = 58; // slotid = 5
	public static final int PLAYERTEXT = 87; // slotid = 6
	public static final int TEXT = 93; // slotid = 7
	public static final int SHOOT2 = 31; // slotid = 8
	public static final int DAMAGE = 24; // slotid = 9
	public static final int UPDATE = 21; // slotid = 10
	public static final int UPDATEACK = 62; // slotid = 11
	public static final int NOTIFICATION = 37; // slotid = 12
	public static final int NEW_TICK = 83; // slotid = 13
	public static final int INVSWAP = 17; // slotid = 14
	public static final int USEITEM = 40; // slotid = 15
	public static final int SHOW_EFFECT = 51; // slotid = 16
	public static final int HELLO = 90; // slotid = 17
	public static final int GOTO = 7; // slotid = 18
	public static final int INVDROP = 55; // slotid = 19
	public static final int INVRESULT = 60; // slotid = 20
	public static final int RECONNECT = 33; // slotid = 21
	public static final int PING = 14; // slotid = 22
	public static final int PONG = 5; // slotid = 23
	public static final int MAPINFO = 65; // slotid = 24
	public static final int LOAD = 64; // slotid = 25
	public static final int PIC = 49; // slotid = 26
	public static final int SETCONDITION = 89; // slotid = 27
	public static final int TELEPORT = 9; // slotid = 28
	public static final int USEPORTAL = 36; // slotid = 29
	public static final int DEATH = 4; // slotid = 30
	public static final int BUY = 28; // slotid = 31
	public static final int BUYRESULT = 23; // slotid = 32
	public static final int AOE = 92; // slotid = 33
	public static final int GROUNDDAMAGE = 48; // slotid = 34
	public static final int PLAYERHIT = 13; // slotid = 35
	public static final int ENEMYHIT = 67; // slotid = 36
	public static final int AOEACK = 80; // slotid = 37
	public static final int SHOOTACK = 1; // slotid = 38
	public static final int OTHERHIT = 15; // slotid = 39
	public static final int SQUAREHIT = 57; // slotid = 40
	public static final int GOTOACK = 79; // slotid = 41
	public static final int EDITACCOUNTLIST = 46; // slotid = 42
	public static final int ACCOUNTLIST = 59; // slotid = 43
	public static final int QUESTOBJID = 88; // slotid = 44
	public static final int CHOOSENAME = 86; // slotid = 45
	public static final int NAMERESULT = 10; // slotid = 46
	public static final int CREATEGUILD = 69; // slotid = 47
	public static final int CREATEGUILDRESULT = 77; // slotid = 48
	public static final int GUILDREMOVE = 18; // slotid = 49
	public static final int GUILDINVITE = 12; // slotid = 50
	public static final int ALLYSHOOT = 34; // slotid = 51
	public static final int SHOOT = 75; // slotid = 52
	public static final int REQUESTTRADE = 30; // slotid = 53
	public static final int TRADEREQUESTED = 76; // slotid = 54
	public static final int TRADESTART = 78; // slotid = 55
	public static final int CHANGETRADE = 82; // slotid = 56
	public static final int TRADECHANGED = 53; // slotid = 57
	public static final int ACCEPTTRADE = 66; // slotid = 58
	public static final int CANCELTRADE = 6; // slotid = 59
	public static final int TRADEDONE = 20; // slotid = 60
	public static final int TRADEACCEPTED = 50; // slotid = 61
	public static final int CLIENTSTAT = 35; // slotid = 62
	public static final int CHECKCREDITS = 91; // slotid = 63
	public static final int ESCAPE = 74; // slotid = 64
	public static final int FILE = 25; // slotid = 65
	public static final int INVITEDTOGUILD = 8; // slotid = 66
	public static final int JOINGUILD = 11; // slotid = 67
	public static final int CHANGEGUILDRANK = 52; // slotid = 68
	public static final int PLAYSOUND = 42; // slotid = 69
	public static final int GLOBAL_NOTIFICATION = 63; // slotid = 70
	public static final int RESKIN = 45; // slotid = 71
//	public static final int _-10B = 39; // slotid = 72
//	public static final int _-0eH = 47; // slotid = 73
//	public static final int _-10e = 84; // slotid = 74
//	public static final int _-1jw = 26; // slotid = 75
//	public static final int _-1Vn = 44; // slotid = 76
//	public static final int _-1iG = 85; // slotid = 77
//	public static final int _-Mb = 68; // slotid = 78
	public static final int  var = 22; // slotid = 79
	public static final int ENTER_ARENA = 27; // slotid = 80
//	public static final int _-Ip = 19; // slotid = 81
//	public static final int _-Wy = 94; // slotid = 82
//	public static final int _-RB = 61; // slotid = 83
//	public static final int _-7r = 3; // slotid = 84
//	public static final int _-0Lw = 56; // slotid = 85
//	public static final int _-0dS = 16; // slotid = 86
	
	
	
	/* old
	public final static int FAILURE = 0;
	public final static int CANCELTRADE = 1;
	public final static int USEPORTAL = 3;
	public final static int INVRESULT = 4;
	public final static int JOINGUILD = 5;
	public final static int PING = 6;
	public final static int MOVE = 7;
	public final static int GUILDINVITE = 8;
	public final static int GLOBAL_NOTIFICATION = 9;
	public final static int SETCONDITION = 10;
	public final static int UPDATEACK = 11;
	public final static int TRADEDONE = 12;
	public final static int SHOOT = 13;
	public final static int GOTOACK = 14;
	public final static int CREATEGUILD = 15;
	public final static int PONG = 16;
	public final static int HELLO = 17;
	public final static int TRADEACCEPTED = 18;
	public final static int SHOOTMULTI = 19;
	public final static int NAMERESULT = 20;
	public final static int REQUESTTRADE = 21;
	public final static int SHOOTACK = 22;
	public final static int TRADECHANGED = 23;
	public final static int PLAYERHIT = 24;
	public final static int TEXT = 25;
	public final static int UPDATE = 26;
	public final static int BUYRESULT = 27;
	public final static int PIC = 28;
	public final static int USEITEM = 30; 
	public final static int CREATE_SUCCESS = 31;
	public final static int CHOOSENAME = 33;
	public final static int QUESTOBJID = 34;
	public final static int INVDROP = 35;
	public final static int CREATE = 36;
	public final static int CHANGETRADE = 37;
	public final static int PLAYERSHOOT = 38;
	public final static int RECONNECT = 39;
	public final static int CHANGEGUILDRANK = 40;
	public final static int DEATH = 41;
	public final static int ESCAPE = 42;
	public final static int PLAYSOUND = 44;
	public final static int LOAD = 45;
	public final static int ACCOUNTLIST = 46;
	public final static int DAMAGE = 47;
	public final static int CHECKCREDITS = 48;
	public final static int TELEPORT = 49;
	public final static int BUY = 50;
	public final static int SQUAREHIT = 51;
	public final static int GOTO = 52;
	public final static int EDITACCOUNTLIST = 53;
	public final static int SHOW_EFFECT = 56;
	public final static int ACCEPTTRADE = 57;
	public final static int AOEACK = 59;
	public final static int MAPINFO = 60;
	public final static int TRADEREQUESTED = 61;
	public final static int NEW_TICK = 62;
	public final static int NOTIFICATION = 63;
	public final static int GROUNDDAMAGE = 64;
	public final static int INVSWAP = 65;
	public final static int OTHERHIT = 66;
	public final static int TRADESTART = 67;
	public final static int AOE = 68;
	public final static int PLAYERTEXT = 69;
	public final static int ALLYSHOOT = 74;
	public final static int CLIENTSTAT = 75;
	public final static int ENEMYHIT = 76;
	public final static int INVITEDTOGUILD = 77;
	public final static int GUILDREMOVE = 78;
	public final static int CREATEGUILDRESULT = 83;
	public final static int CLIENTSTAT_FILE = 86;
	*/
	static {
		initAllMap();
		System.out.println("Init");
	}

	private static void initAllMap() {
        allmap.put(0, "FAILURE");
allmap.put(1, "CANCELTRADE");
allmap.put(3, "USEPORTAL");
allmap.put(4, "INVRESULT");
allmap.put(5, "JOINGUILD");
allmap.put(6, "PING");
allmap.put(7, "MOVE");
allmap.put(8, "GUILDINVITE");
allmap.put(9, "GLOBAL_NOTIFICATION");
allmap.put(10, "SETCONDITION");
allmap.put(11, "UPDATEACK");
allmap.put(12, "TRADEDONE");
allmap.put(13, "SHOOT");
allmap.put(14, "GOTOACK");
allmap.put(15, "CREATEGUILD");
allmap.put(16, "PONG");
allmap.put(17, "HELLO");
allmap.put(18, "TRADEACCEPTED");
allmap.put(19, "SHOOTMULTI");
allmap.put(20, "NAMERESULT");
allmap.put(21, "REQUESTTRADE");
allmap.put(22, "SHOOTACK");
allmap.put(23, "TRADECHANGED");
allmap.put(24, "PLAYERHIT");
allmap.put(25, "TEXT");
allmap.put(26, "UPDATE");
allmap.put(27, "BUYRESULT");
allmap.put(28, "PIC");
allmap.put(30, "USEITEM");
allmap.put(31, "CREATE_SUCCESS");
allmap.put(33, "CHOOSENAME");
allmap.put(34, "QUESTOBJID");
allmap.put(35, "INVDROP");
allmap.put(36, "CREATE");
allmap.put(37, "CHANGETRADE");
allmap.put(38, "PLAYERSHOOT");
allmap.put(39, "RECONNECT");
allmap.put(40, "CHANGEGUILDRANK");
allmap.put(41, "DEATH");
allmap.put(42, "ESCAPE");
allmap.put(44, "PLAYSOUND");
allmap.put(45, "LOAD");
allmap.put(46, "ACCOUNTLIST");
allmap.put(47, "DAMAGE");
allmap.put(48, "CHECKCREDITS");
allmap.put(49, "TELEPORT");
allmap.put(50, "BUY");
allmap.put(51, "SQUAREHIT");
allmap.put(52, "GOTO");
allmap.put(53, "EDITACCOUNTLIST");
allmap.put(56, "SHOW_EFFECT");
allmap.put(57, "ACCEPTTRADE");
allmap.put(59, "AOEACK");
allmap.put(60, "MAPINFO");
allmap.put(61, "TRADEREQUESTED");
allmap.put(62, "NEW_TICK");
allmap.put(63, "NOTIFICATION");
allmap.put(64, "GROUNDDAMAGE");
allmap.put(65, "INVSWAP");
allmap.put(66, "OTHERHIT");
allmap.put(67, "TRADESTART");
allmap.put(68, "AOE");
allmap.put(69, "PLAYERTEXT");
allmap.put(74, "ALLYSHOOT");
allmap.put(75, "CLIENTSTAT");
allmap.put(76, "ENEMYHIT");
allmap.put(77, "INVITEDTOGUILD");
allmap.put(78, "GUILDREMOVE");
allmap.put(81, "SHOOTMULTI");
allmap.put(83, "CREATEGUILDRESULT");

allmap.put(86, "CLIENTSTAT_FILE");
		System.out.println("Init");
	}

	public static RWable findPacket(int i) {
		if (i == FAILURE) {
			return new FAILURE_Packet();
		} else if (i == TRADEACCEPTED) {
			return new TRADEACCEPTED_Packet();
		} else if (i == PIC) {
			//return new PIC_Packet();
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
		} 
		//else if (i == SHOOTMULTI) {
			//return new SHOOTMULTI_Packet();
		//} 
	   else if (i == ESCAPE) {
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
		}
		//else if (i == CLIENTSTAT_FILE) {
		//	return new CLIENTSTAT_FILE_Packet();
		//} 
		else if (i == NEW_TICK) {
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
			
		} else if (i == GLOBAL_NOTIFICATION) {
			return new GLOBAL_NOTIFICATION_packet();
		}
		//else if (i == SHOOTMULTI) {
	//		return new SHOOTMULTI_Packet();
		//}
		return null;
	}
}
