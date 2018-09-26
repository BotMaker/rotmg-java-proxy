// GOOD DONT AUTOGEN Name: SHOOT, Original AS3 class: _-U5.as

package packets.server;

import java.io.IOException;

import packets.*;
import data.*;

public class SHOOT_Packet implements RWable {
	public int bulletId_; // uint
	public int ownerId_; // int
	public int containerType_; // int
	public Location startingPos_; // _-Tq
	public float angle_; // Number
	public int damage_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.bulletId_ = badi.readUnsignedByte(); // UnsignedByte
		this.ownerId_ = badi.readInt(); // Int
		this.containerType_ = badi.readShort(); // Short
		this.startingPos_ = new Location(badi);
		this.angle_ = badi.readFloat(); // Float
		this.damage_ = badi.readShort(); // Short
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeByte(bulletId_); // UnsignedByte
		bado.writeInt(ownerId_); // Int
		bado.writeShort(containerType_); // Short
		startingPos_.writeToOutput(bado);
		bado.writeFloat(angle_); // Float
		bado.writeShort(damage_); // Short
	}

	public int getId() {
		return 21;
	}

	public String toString() {
		return "SHOOT [" + bulletId_ + " , " + ownerId_ + " , " + containerType_ + " , " + startingPos_ + " , " + angle_ + " , " + damage_ + "]";
	}
}
