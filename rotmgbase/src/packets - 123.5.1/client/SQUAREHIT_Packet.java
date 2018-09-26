// GOOD Name: SQUAREHIT, Original AS3 class: _-Ss.as

package packets.client;

import java.io.IOException;

import packets.*;

public class SQUAREHIT_Packet implements RWable {
	public int time_; // int
	public int bulletId_; // uint
	public int objectId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt(); // Int
		this.bulletId_ = badi.readByte(); // Byte
		this.objectId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_); // Int
		bado.writeByte(this.bulletId_); // Byte
		bado.writeInt(this.objectId_); // Int
	}

	public int getId() {
		return 16;
	}

	public String toString() {
		return "SQUAREHIT [" + time_ + " , " + bulletId_ + " , " + objectId_ + "]";
	}
}
