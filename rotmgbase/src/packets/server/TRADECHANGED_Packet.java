// GOOD Name: TRADECHANGED, Original AS3 class: _-uS.as

package packets.server;

import java.io.IOException;

import packets.*;

public class TRADECHANGED_Packet implements RWable {
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
return 44;
}
public String toString() {
		return "TRADECHANGED [" + offer_ + "]";
	}
}
