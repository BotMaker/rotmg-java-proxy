// GOOD Name: GUILDINVITE, Original AS3 class: _-Wf.as

package packets.client;

import java.io.IOException;

import packets.*;

public class GUILDINVITE_Packet implements RWable {
	public String name_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.name_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(this.name_); // UTF
	}

	public int getId() {
		return 22;
	}

	public String toString() {
		return "GUILDINVITE [" + name_ + "]";
	}
}
