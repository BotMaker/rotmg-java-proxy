// GOOD Name: CREATE_SUCCESS, Original AS3 class: _-Gb.as

package packets.server;

import java.io.IOException;

import packets.*;

public class CREATE_SUCCESS_Packet implements RWable {
	public int objectId_; // int
	public int charId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.objectId_ = badi.readInt(); // Int
		this.charId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(objectId_); // Int
		bado.writeInt(charId_); // Int
	}

	public int getId() {
		return 59;
	}

	public String toString() {
		return "CREATE_SUCCESS [" + objectId_ + " , " + charId_ + "]";
	}
}
