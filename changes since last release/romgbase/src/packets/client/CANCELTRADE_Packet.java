// GOOD Name: CANCELTRADE, Original AS3 class: _-Ge.as

package packets.client;

import java.io.IOException;

import packets.*;

public class CANCELTRADE_Packet implements RWable {
	public int objectId_; // int - why is this in _-Ge? it's not used.

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		// nothing
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		// nothing
	}

	public int getId() {
		return 25;
	}

	public String toString() {
		return "CANCELTRADE [" + objectId_ + "]";
	}
}
