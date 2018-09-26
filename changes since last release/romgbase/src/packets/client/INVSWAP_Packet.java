// GOOD Name: INVSWAP, Original AS3 class: _-U0.as

package packets.client;

import java.io.IOException;

import packets.*;
import data.*;

public class INVSWAP_Packet implements RWable {
	public int time_; // int
	public Location position_; // _-Tq
	public Item slotObject1_; // _-Qd
	public Item slotObject2_; // _-Qd

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt();
		this.position_ = new Location(badi);
		this.slotObject1_ = new Item(badi);
		this.slotObject2_ = new Item(badi);
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(time_);
		position_.writeToOutput(bado);
		slotObject1_.writeToOutput(bado);
		slotObject2_.writeToOutput(bado);
	}

	public int getId() {
		return 65;
	}

	public String toString() {
		return "INVSWAP [" + time_ + " , " + position_ + " , " + slotObject1_ + " , " + slotObject2_ + "]";
	}
}
