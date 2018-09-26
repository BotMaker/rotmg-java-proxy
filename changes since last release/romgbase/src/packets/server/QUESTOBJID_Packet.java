// GOOD Name: QUESTOBJID, Original AS3 class: _20case.as

package packets.server;

import java.io.IOException;

import packets.*;

public class QUESTOBJID_Packet implements RWable {
	public int objectId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.objectId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(objectId_); // Int
	}

public int getId() {
return 74;
}
public String toString() {
		return "QUESTOBJID [" + objectId_ + "]";
	}
}
