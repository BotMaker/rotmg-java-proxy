// GOOD Name: TRADEACCEPTED, Original AS3 class: _-Ou.as

package packets.server;

import java.io.IOException;

import packets.*;

public class TRADEACCEPTED_Packet implements RWable {
	public boolean[] myOffer_; // Vector.<Boolean>
	public boolean[] yourOffer_; // Vector.<Boolean>

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		short length = badi.readShort();
		myOffer_ = new boolean[length];
		for (int i = 0; i < length; i++) {
			myOffer_[i] = badi.readBoolean();
		}
		length = badi.readShort();
		yourOffer_ = new boolean[length];
		for (int i = 0; i < length; i++) {
			yourOffer_[i] = badi.readBoolean();
		}
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeShort(myOffer_.length);
		for (int i = 0; i < myOffer_.length; i++) {
			bado.writeBoolean(myOffer_[i]);
		}
		bado.writeShort(yourOffer_.length);
		for (int i = 0; i < yourOffer_.length; i++) {
			bado.writeBoolean(yourOffer_[i]);
		}
	}

public int getId() {
return 1;
}
public String toString() {
		return "TRADEACCEPTED [" + myOffer_ + " , " + yourOffer_ + "]";
	}
}
