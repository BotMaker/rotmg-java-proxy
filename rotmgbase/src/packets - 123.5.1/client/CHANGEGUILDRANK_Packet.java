// GOOD Name: CHANGEGUILDRANK, Original AS3 class: _-ta.as

package packets.client;

import java.io.IOException;

import packets.*;

public class CHANGEGUILDRANK_Packet implements RWable {
	public String name_; // String
	public int guildRank_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.name_ = badi.readUTF(); // UTF
		this.guildRank_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(this.name_); // UTF
		bado.writeInt(this.guildRank_); // Int
	}

	public int getId() {
		return 60;
	}

	public String toString() {
		return "CHANGEGUILDRANK [" + name_ + " , " + guildRank_ + "]";
	}
}
