// GOOD Name: ESCAPE, Original AS3 class: _-UH.as

package packets.client;

import java.io.IOException;

import packets.*;

public class ESCAPE_Packet implements RWable {

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		// empty
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		// empty
	}

	public int getId() {
		return 33;
	}

	public String toString() {
		return "ESCAPE [" + "]";
	}
}
