// GOOD Name: BUY, Original AS3 class: Buy.as

package packets.client;

import java.io.IOException;

import packets.*;

public class BUY_Packet implements RWable {
	public int objectId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.objectId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.objectId_); // Int
	}

	public int getId() {
		return 68;
	}

	public String toString() {
		return "BUY [" + objectId_ + "]";
	}
}
