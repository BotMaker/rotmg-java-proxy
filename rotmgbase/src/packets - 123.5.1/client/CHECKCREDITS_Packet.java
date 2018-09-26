// GOOD Name: CHECKCREDITS, Original AS3 class: _-NV.as

package packets.client;

import java.io.IOException;

import packets.*;

public class CHECKCREDITS_Packet implements RWable {

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		// empty
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		// empty
	}

	public int getId() {
		return 30;
	}

	public String toString() {
		return "CHECKCREDITS [" + "]";
	}
}
