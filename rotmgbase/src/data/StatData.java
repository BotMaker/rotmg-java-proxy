// 123.0.0 StatData.as

package data;

import java.io.IOException;

import packets.*;

public class StatData {
	public static final int MAX_HEALTH = 0;
	public static final int HEALTH = 1;
	public static final int SIZE = 2;
	public static final int MAX_MANA = 3;
	public static final int MANA = 4;
	public static final int UNKNOWN_5 = 5;
	public static final int UNKNOWN_6 = 6;
	public static final int CURRENT_LEVEL = 7;
	public static final int SLOT_1 = 8;
	public static final int SLOT_2 = 9;
	public static final int SLOT_3 = 10;
	public static final int SLOT_4 = 11;
	public static final int SLOT_5 = 12;
	public static final int SLOT_6 = 13;
	public static final int SLOT_7 = 14;
	public static final int SLOT_8 = 15;
	public static final int SLOT_9 = 16;
	public static final int SLOT_10 = 17;
	public static final int SLOT_11 = 18;
	public static final int SLOT_12 = 19;
	public static final int TOTAL_ATTACK = 20;
	public static final int TOTAL_DEFENCE = 21;
	public static final int TOTAL_SPEED = 22;
	public static final int UNKNOWN_23 = 23;
	public static final int UNKNOWN_24 = 24;
	public static final int UNKNOWN_25 = 25;
	public static final int TOTAL_VITALITY = 26;
	public static final int TOTAL_WISDOM = 27;
	public static final int TOTAL_DEXTERITY = 28;
	public static final int UNKNOWN_29 = 29; // something to do with pause
	public static final int UNKNOWN_30 = 30;
	public static final int NAME = 31;
	public static final int UNKNOWN_32 = 32;
	public static final int UNKNOWN_33 = 33;
	public static final int UNKNOWN_34 = 34;
	public static final int REALM_GOLD = 35;
	public static final int UNKNOWN_36 = 36;
	public static final int UNKNOWN_37 = 37;
	public static final int UNKNOWN_38 = 38;
	public static final int TOTAL_FAME = 39;
	public static final int UNKNOWN_40 = 40;
	public static final int UNKNOWN_41 = 41;
	public static final int UNKNOWN_42 = 42;
	public static final int UNKNOWN_43 = 43;
	public static final int UNKNOWN_44 = 44;
	public static final int UNKNOWN_45 = 45;
	public static final int BONUS_HEALTH = 46;
	public static final int BONUS_MANA = 47;
	public static final int BONUS_ATTACK = 48;
	public static final int BONUS_DEFENCE = 49;
	public static final int UNKNOWN_50 = 50;
	public static final int UNKNOWN_51 = 51;
	public static final int BONUS_WISDOM = 52;
	public static final int BONUS_DEXTERITY = 53;
	public static final int UNKNOWN_54 = 54;
	public static final int UNKNOWN_55 = 55;
	public static final int UNKNOWN_56 = 56;
	public static final int FAME = 57;
	public static final int FAME_GOAL = 58;
	public static final int UNKNOWN_59 = 59;
	public static final int UNKNOWN_60 = 60;
	public static final int UNKNOWN_61 = 61;
	public static final int GUILD = 62;
	public static final int GUILD_RANK = 63;
	public static final int UNKNOWN_64 = 64;

    public int type = 0; // ubyte
	public int numValue;
    public String stringValue;
    
	public StatData(int type, int numValue) {
		this.type = type;
		this.numValue = numValue;
	}

	public StatData(ByteArrayDataInput badi) throws IOException {
		parseFromInput(badi);
	}
	
	public StatData(int type, String stringValue) {
		this.type = type;
		this.stringValue = stringValue;
	}

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.type = badi.readUnsignedByte();
		//if (type == NAME || type == GUILD) { // theese have a string value
		if (type == 31 || type == 62 || type ==82 || type ==38 || type ==54) {//new 14.2
			this.stringValue = badi.readUTF();
			return;
		}
		this.numValue = badi.readInt();
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.write(type);
		if (type == NAME || type == GUILD) { // theese have a string value
			bado.writeUTF(stringValue);
			return;
		}
		bado.writeInt(numValue);
	}

	public String toString() {
		return "statdata [" + type + ", " + (stringValue == null ? numValue : stringValue) + "]";
	}
}
