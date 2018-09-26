// GOOD Name: INVITEDTOGUILD, Original AS3 class: _-Gu.as

package packets.server;

import java.io.IOException;

import packets.*;

public class INVITEDTOGUILD_Packet implements RWable {
	public String name_; // String
	public String guildName_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.name_ = badi.readUTF(); // UTF
		this.guildName_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(name_); // UTF
		bado.writeUTF(guildName_); // UTF
	}

public int getId() {
return 35;
}
public String toString() {
		return "INVITEDTOGUILD [" + name_ + " , " + guildName_ + "]";
	}
}
