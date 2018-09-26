// GOOD Name: AOE, Original AS3 class: _-84.as

package packets.server;

import java.io.IOException;

import packets.*;
import data.*;

public class AOE_Packet implements RWable {
	public Location pos_; // _-Tq
	public float radius_; // Number
	public int damage_; // int
	public int effect_; // int
	public float duration_; // Number
	public int origType_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.pos_ = new Location(badi);
		this.radius_ = badi.readFloat(); // Float
		this.damage_ = badi.readUnsignedShort(); // UnsignedShort
		this.effect_ = badi.readUnsignedByte(); // UnsignedByte
		this.duration_ = badi.readFloat(); // Float
		this.origType_ = badi.readUnsignedShort(); // UnsignedShort
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		pos_.writeToOutput(bado);
		bado.writeFloat(radius_); // Float
		bado.writeShort(damage_); // UnsignedShort
		bado.writeByte(effect_); // UnsignedByte
		bado.writeFloat(duration_); // Float
		bado.writeShort(origType_); // UnsignedShort
	}

public int getId() {
return 62;
}
public String toString() {
		return "AOE [" + pos_ + " , " + radius_ + " , " + damage_ + " , " + origType_ + "]";
	}
}
