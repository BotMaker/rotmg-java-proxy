// GOOD Name: PLAYERTEXT, Original AS3 class: _-hm.as

package packets.client;

import java.io.IOException;

import packets.*;

public class PLAYERTEXT_Packet implements RWable {
	public String text_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.text_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(this.text_); // UTF
	}

	public int getId() {
		return 39;
	}

	public String toString() {
		return "PLAYERTEXT [" + text_ + "]";
	}
}
