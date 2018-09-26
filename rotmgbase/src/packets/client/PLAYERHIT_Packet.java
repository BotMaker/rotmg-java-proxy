// GOOD Name: PLAYERHIT, Original AS3 class: _-57.as

package packets.client;

import java.io.IOException;

import packets.*;

public class PLAYERHIT_Packet implements RWable {
	public int bulletId_; // uint
	public int objectId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.bulletId_ = badi.readByte(); // Byte
		this.objectId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeByte(this.bulletId_); // Byte
		bado.writeInt(this.objectId_); // Int
	}

	public int getId() {
		return 18;
	}

	public String toString() {
		return "PLAYERHIT [" + bulletId_ + " , " + objectId_ + "]";
	}
}
