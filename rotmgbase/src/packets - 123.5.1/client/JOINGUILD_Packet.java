// GOOD Name: JOINGUILD, Original AS3 class: _-j8.as

package packets.client;

import java.io.IOException;

import packets.*;

public class JOINGUILD_Packet implements RWable {
	public String guildName_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.guildName_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(this.guildName_); // UTF
	}

	public int getId() {
		return 52;
	}

	public String toString() {
		return "JOINGUILD [" + guildName_ + "]";
	}
}
