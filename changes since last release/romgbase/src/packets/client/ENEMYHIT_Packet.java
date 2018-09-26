// GOOD Name: ENEMYHIT, Original AS3 class: _-eI.as

package packets.client;

import java.io.IOException;

import packets.*;

public class ENEMYHIT_Packet implements RWable {
	public int time_; // int
	public int bulletId_; // uint
	public int targetId_; // int
	public Boolean kill_; // Boolean

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt();
		this.bulletId_ = badi.readByte();
		this.targetId_ = badi.readInt(); // Int
		this.kill_ = badi.readBoolean(); // Boolean
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_); // Int
		bado.writeByte(this.bulletId_);
		bado.writeInt(this.targetId_); // Int
		bado.writeBoolean(this.kill_); // Boolean
	}

	public int getId() {
		return 24;
	}

	public String toString() {
		return "ENEMYHIT [" + time_ + " , " + bulletId_ + " , " + targetId_ + " , " + kill_ + "]";
	}
}
