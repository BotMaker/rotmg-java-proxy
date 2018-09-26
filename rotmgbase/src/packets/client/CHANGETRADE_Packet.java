// GOOD Name: CHANGETRADE, Original AS3 class: _-73.as

package packets.client;

import java.io.IOException;
import java.util.Arrays;

import packets.*;

public class CHANGETRADE_Packet implements RWable {
	public boolean[] offer_; // Vector.<Boolean>

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		short length = badi.readShort();
		offer_ = new boolean[length];
		for (int i = 0; i < length; i++) {
			offer_[i] = badi.readBoolean();
		}
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeShort(offer_.length);
		for (int i = 0; i < offer_.length; i++) {
			bado.writeBoolean(offer_[i]);
		}
	}

	public int getId() {
		return 12;
	}

	public String toString() {
		return "CHANGETRADE [" + Arrays.asList(offer_) + "]";
	}
}
