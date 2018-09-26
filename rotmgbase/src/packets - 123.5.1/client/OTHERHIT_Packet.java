// GOOD Name: OTHERHIT, Original AS3 class: false.as

package packets.client;

import java.io.IOException;

import packets.*;

public class OTHERHIT_Packet implements RWable {
	public int time_; // int
	public int bulletId_; // uint
	public int objectId_; // int
	public int targetId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt(); // Int
		this.bulletId_ = badi.readByte();
		this.objectId_ = badi.readInt(); // Int
		this.targetId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_); // Int
		bado.writeByte(this.bulletId_);
		bado.writeInt(this.objectId_); // Int
		bado.writeInt(this.targetId_); // Int
	}

	public int getId() {
		return 6;
	}

	public String toString() {
		return "OTHERHIT [" + time_ + " , " + bulletId_ + " , " + objectId_ + " , " + targetId_ + "]";
	}
}
