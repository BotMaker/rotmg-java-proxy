// GOOD Name: INVDROP, Original AS3 class: _-J2.as

package packets.client;

import java.io.IOException;

import packets.*;
import data.*;

public class INVDROP_Packet implements RWable {
	public Item slotObject_; // _-Qd

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.slotObject_ = new Item(badi);
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		slotObject_.writeToOutput(bado);
	}

	public int getId() {
		return 35;
	}

	public String toString() {
		return "INVDROP [" + slotObject_ + "]";
	}
}
