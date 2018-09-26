// GOOD Name: CHOOSENAME, Original AS3 class: _-5D.as

package packets.client;

import java.io.IOException;

import packets.*;

public class CHOOSENAME_Packet implements RWable {
	public String name_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.name_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(this.name_); // UTF
	}

	public int getId() {
		return 40;
	}

	public String toString() {
		return "CHOOSENAME [" + name_ + "]";
	}
}
