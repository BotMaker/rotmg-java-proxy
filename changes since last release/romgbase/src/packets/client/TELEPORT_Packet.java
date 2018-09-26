// GOOD Name: TELEPORT, Original AS3 class: Teleport.as

package packets.client;

import java.io.IOException;

import packets.*;

public class TELEPORT_Packet implements RWable {
	public int objectId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.objectId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.objectId_); // Int
	}

	public int getId() {
		return 23;
	}

	public String toString() {
		return "TELEPORT [" + objectId_ + "]";
	}
}
