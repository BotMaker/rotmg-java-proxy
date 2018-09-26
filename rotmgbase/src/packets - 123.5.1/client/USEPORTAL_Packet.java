// GOOD Name: USEPORTAL, Original AS3 class: _-mx.as

package packets.client;

import java.io.IOException;

import packets.*;

public class USEPORTAL_Packet implements RWable {
	public int objectId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.objectId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.objectId_); // Int
	}

	public int getId() {
		return 3;
	}

	public String toString() {
		return "USEPORTAL [" + objectId_ + "]";
	}
}
