// GOOD Name: SHOOTACK, Original AS3 class: _-q3.as

package packets.client;

import java.io.IOException;

import packets.*;

public class SHOOTACK_Packet implements RWable {
	public int time_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_); // Int
	}

	public int getId() {
		return 22;
	}

	public String toString() {
		return "SHOOTACK [" + time_ + "]";
	}
}
