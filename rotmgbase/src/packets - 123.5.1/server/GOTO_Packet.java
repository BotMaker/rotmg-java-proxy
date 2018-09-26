// GOOD Name: GOTO, Original AS3 class: _-qM.as

package packets.server;

import java.io.IOException;

import packets.*;
import data.*;

public class GOTO_Packet implements RWable {
	public int objectId_; // int
	public Location pos_; // _-Tq

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.objectId_ = badi.readInt(); // Int
		this.pos_ = new Location(badi);
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(objectId_); // Int
		pos_.writeToOutput(bado);
	}

public int getId() {
return 52;
}
public String toString() {
		return "GOTO [" + objectId_ + " , " + pos_ + "]";
	}
}
