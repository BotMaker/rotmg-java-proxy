// GOOD Name: SHOW_EFFECT, Original AS3 class: _-Pl.as

package packets.server;

import java.io.IOException;

import packets.*;
import data.*;

public class SHOW_EFFECT_Packet implements RWable {
	public int effectType_; // uint
	public int targetObjectId_; // int
	public Location pos1_; // _-Tq
	public Location pos2_; // _-Tq
	public int color_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.effectType_ = badi.readUnsignedByte(); // UnsignedByte
		this.targetObjectId_ = badi.readInt(); // Int
		this.pos1_ = new Location(badi);
		this.pos2_ = new Location(badi);
		this.color_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeByte(effectType_); // UnsignedByte
		bado.writeInt(targetObjectId_); // Int
		pos1_.writeToOutput(bado);
		pos2_.writeToOutput(bado);
		bado.writeInt(color_); // Int
	}

	public int getId() {
		return 77;
	}

	public String toString() {
		return "SHOW_EFFECT [" + effectType_ + " , " + targetObjectId_ + " , " + pos1_ + " , " + pos2_ + " , " + color_ + "]";
	}
}
