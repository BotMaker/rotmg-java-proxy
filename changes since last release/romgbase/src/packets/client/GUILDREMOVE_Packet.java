// GOOD Name: GUILDREMOVE, Original AS3 class: _-er.as

package packets.client;

import java.io.IOException;

import packets.*;

public class GUILDREMOVE_Packet implements RWable {
	public String name_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.name_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(this.name_); // UTF
	}

	public int getId() {
		return 8;
	}

	public String toString() {
		return "GUILDREMOVE [" + name_ + "]";
	}
}
