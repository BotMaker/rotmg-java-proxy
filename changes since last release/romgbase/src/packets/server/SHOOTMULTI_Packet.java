// GOOD DONT AUTOGEN Name: SHOOT, Original AS3 class: _-st.as

package packets.server;

import java.io.EOFException;
import java.io.IOException;

import packets.*;
import data.*;

public class SHOOTMULTI_Packet implements RWable {
	public int bulletId_; // uint
	public int ownerId_; // int
	public int bulletType_; // int
	public Location startingPos_; // _-Tq
	public float angle_; // Number
	public int damage_; // int
	public int numShots_; // int
	public float angleInc_; // Number
	public boolean multiProjectiles;

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.bulletId_ = badi.readUnsignedByte(); // UnsignedByte
		this.ownerId_ = badi.readInt(); // Int
		this.bulletType_ = badi.readUnsignedByte(); // Short
		this.startingPos_ = new Location(badi);
		this.angle_ = badi.readFloat(); // Float
		this.damage_ = badi.readShort(); // Short
		try {
			this.numShots_ = badi.readUnsignedByte(); // UnsignedByte
			this.angleInc_ = badi.readFloat(); // Float
			multiProjectiles = true;
		} catch (EOFException e) {
			multiProjectiles = false;
		}
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeByte(this.bulletId_); // UnsignedByte
		bado.writeInt(this.ownerId_); // Int
		bado.writeByte(this.bulletType_); // Short
		this.startingPos_.writeToOutput(bado);
		bado.writeFloat(this.angle_); // Float
		bado.writeShort(this.damage_); // Short
		if (multiProjectiles) {
			bado.writeByte(this.numShots_); // UnsignedByte
			bado.writeFloat(this.angleInc_); // Float
		}
	}

	public int getId() {
		return 31;
	}

	public String toString() {
		return "SHOOTMULTI [" + bulletId_ + " , " + ownerId_ + " , " + bulletType_ + " , " + startingPos_ + " , " + angle_ + " , " + damage_ + " , " + numShots_ + " , " + angleInc_ + "]";
	}
}
