// GOOD Name: LOAD, Original AS3 class: _-PT.as

package packets.client;

import java.io.IOException;

import packets.*;

public class LOAD_Packet implements RWable {
	public int charId_; // int
	public boolean ina;

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.charId_ = badi.readInt(); // Int
		this.ina = badi.readBoolean();
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.charId_); // Int
		bado.writeBoolean(this.ina); // Int
	}

	public int getId() {
		return 45;
	}

	public String toString() {
		return "LOAD [" + charId_ + "]";
	}
}
