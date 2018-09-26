// GOOD Name: ACCEPTTRADE, Original AS3 class: _-uT.as

package packets.client;

import java.io.IOException;

import packets.*;

public class ACCEPTTRADE_Packet implements RWable {
	public boolean[] myOffer_; // Vector.<Boolean>
	public boolean[] yourOffer_; // Vector.<Boolean>

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		int size = badi.readShort();
		myOffer_ = new boolean[size];
		for (int i = 0; i < size; i++) {
			myOffer_[i] = badi.readBoolean();
		}

		int size2 = badi.readShort();
		yourOffer_ = new boolean[size2];
		for (int i = 0; i < size2; i++) {
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
		return 15;
	}

	public String toString() {
		return "ACCEPTTRADE [" + myOffer_ + " , " + yourOffer_ + "]";
	}
}
