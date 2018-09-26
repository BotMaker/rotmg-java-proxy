// GOOD Name: PING, Original AS3 class: _-MJ.as

package packets.server;

import java.io.IOException;

import packets.*;

public class PING_Packet implements RWable {
	public int serial_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.serial_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(serial_); // Int
	}

public int getId() {
return 6;
}
public String toString() {
		return "PING [" + serial_ + "]";
	}
}
