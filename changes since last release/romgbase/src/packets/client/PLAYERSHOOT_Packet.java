// GOOD Name: PLAYERSHOOT, Original AS3 class: _-bQ.as

package packets.client;

import java.io.IOException;

import packets.*;
import data.*;

public class PLAYERSHOOT_Packet implements RWable {
	public int time_; // int
	public int bulletId_; // uint
	public int containerType_; // int
	public Location startingPos_; // _-Tq
	public float angle_; // Number

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt();
		this.bulletId_ = badi.readByte(); // Byte
		this.containerType_ = badi.readShort(); // Short
		this.startingPos_ = new Location(badi);
		this.angle_ = badi.readFloat(); // Float
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_);
		bado.writeByte(this.bulletId_); // Byte
		bado.writeShort(this.containerType_); // Short
		startingPos_.writeToOutput(bado);
		bado.writeFloat(this.angle_); // Float
	}

	public int getId() {
		return 20;
	}

	public String toString() {
		return "PLAYERSHOOT [" + time_ + " , " + bulletId_ + " , " + containerType_ + " , " + startingPos_ + " , " + angle_ + "(" + (180 + angle_ * (360d / Math.PI / 2)) + ")" + "]";
	}
}
