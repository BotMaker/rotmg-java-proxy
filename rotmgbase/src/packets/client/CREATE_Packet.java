// GOOD Name: CREATE, Original AS3 class: Create.as

package packets.client;

import java.io.IOException;

import packets.*;

public class CREATE_Packet implements RWable {
	public int objectType_; // int
	public final static int SORCERER = 0x325;
	public final static int TRICKSTER = 0x324;
	public final static int MYSTIC = 0x323;
	public final static int HUNTRESS = 0x322;
	public final static int NECROMANCER = 0x321;
	public final static int ASSASSIN = 0x320;
	public final static int PALADIN = 0x31F;
	public final static int KNIGHT = 0x31E;
	public final static int WARRIOR = 0x31D;
	public final static int PRIEST = 0x310;
	public final static int WIZARD = 0x30E;
	public final static int ARCHER = 0x307;
	public final static int ROGUE = 0x300;

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		//System.out.println("remaining bytes");
		//System.out.println(badi.size());
		//System.out.println(badi.readByte());
		this.objectType_ = badi.readShort(); // Short
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeShort(this.objectType_); // Short
		bado.writeShort(0);
		
	}

	public static String getName(int i) {
		if (i == 0x325) {
			return "SORCERER";
		} else if (i == 0x324) {
			return "TRICKSTER";
		} else if (i == 0x323) {
			return "MYSTIC";
		} else if (i == 0x322) {
			return "HUNTRESS";
		} else if (i == 0x321) {
			return "NECROMANCER";
		} else if (i == 0x320) {
			return "ASSASSIN";
		} else if (i == 0x31F) {
			return "PALADIN";
		} else if (i == 0x31E) {
			return "KNIGHT";
		} else if (i == 0x31D) {
			return "WARRIOR";
		} else if (i == 0x310) {
			return "PRIEST";
		} else if (i == 0x30E) {
			return "WIZARD";
		} else if (i == 0x307) {
			return "ARCHER";
		} else if (i == 0x300) {
			return "ROGUE";
		}
		return null;
	}

	public static int getId(String s) {
		if (s.equalsIgnoreCase("SORCERER")) {
			return 0x325;
		} else if (s.equalsIgnoreCase("TRICKSTER")) {
			return 0x324;
		} else if (s.equalsIgnoreCase("MYSTIC")) {
			return 0x323;
		} else if (s.equalsIgnoreCase("HUNTRESS")) {
			return 0x322;
		} else if (s.equalsIgnoreCase("NECROMANCER")) {
			return 0x321;
		} else if (s.equalsIgnoreCase("ASSASSIN")) {
			return 0x320;
		} else if (s.equalsIgnoreCase("PALADIN")) {
			return 0x31F;
		} else if (s.equalsIgnoreCase("KNIGHT")) {
			return 0x31E;
		} else if (s.equalsIgnoreCase("WARRIOR")) {
			return 0x31D;
		} else if (s.equalsIgnoreCase("PRIEST")) {
			return 0x310;
		} else if (s.equalsIgnoreCase("WIZARD")) {
			return 0x30E;
		} else if (s.equalsIgnoreCase("ARCHER")) {
			return 0x307;
		} else if (s.equalsIgnoreCase("ROGUE")) {
			return 0x300;
		}
		return -1;
	}

	public int getId() {
		return 36;
	}

	public String toString() {
		return "CREATE [" + getName(objectType_) + " (" + objectType_ + ")" + "]";
	}
}
