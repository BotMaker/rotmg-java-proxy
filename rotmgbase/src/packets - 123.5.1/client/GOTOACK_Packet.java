// GOOD Name: GOTOACK, Original AS3 class: _-rw.as

package packets.client;

import java.io.IOException;

import packets.*;

public class GOTOACK_Packet implements RWable {
	public int time_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_); // Int
	}

	public int getId() {
		return 51;
	}

	public String toString() {
		return "GOTOACK [" + time_ + "]";
	}
}
