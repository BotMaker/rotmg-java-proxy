// GOOD Name: PONG, Original AS3 class: _-kj.as

package packets.client;

import java.io.IOException;

import packets.*;

public class PONG_Packet implements RWable {
	public int serial_; // int
	public int time_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.serial_ = badi.readInt(); // Int
		this.time_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.serial_); // Int
		bado.writeInt(this.time_); // Int
	}

	public int getId() {
		return 16;
	}

	public String toString() {
		return "PONG [" + serial_ + " , " + time_ + "]";
	}
}
