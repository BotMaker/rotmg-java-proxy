// GOOD Name: ALLYSHOOT, Original AS3 class: _-lC.as

package packets.server;

import java.io.IOException;

import packets.*;

public class ALLYSHOOT_Packet implements RWable {
	public int bulletId_; // uint
	public int ownerId_; // int
	public int containerType_; // int
	public float angle_; // Number

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.bulletId_ = badi.readUnsignedByte(); // UnsignedByte
		this.ownerId_ = badi.readInt();
		this.containerType_ = badi.readShort(); // Short
		this.angle_ = badi.readFloat(); // Float
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeByte(bulletId_); // UnsignedByte
		bado.writeInt(ownerId_);
		bado.writeShort(containerType_); // Short
		bado.writeFloat(angle_); // Float
	}

	public boolean query() {
		return true;
	}

	public int getId() {
		return 74;
	}

	public String toString() {
		return "ALLYSHOOT [" + bulletId_ + " , " + ownerId_ + " , " + containerType_ + " , " + angle_ + "]";
	}
}
