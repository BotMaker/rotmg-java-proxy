// GOOD Name: USEITEM, Original AS3 class: _-i5.as

package packets.client;

import java.io.IOException;

import packets.*;
import data.*;

public class USEITEM_Packet implements RWable {
	public int time_; // int
	public Item slotObject_; // _-Qd
	public Location itemUsePos_; // _-Tq
	public int useType;

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt(); // Int
		this.slotObject_ = new Item(badi);
		this.itemUsePos_ = new Location(badi);
		this.useType = badi.readUnsignedByte();
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_); // Int
		slotObject_.writeToOutput(bado);
		itemUsePos_.writeToOutput(bado);
		bado.writeByte(useType);
		
	}

	public int getId() {
		return 30;
	}

	public String toString() {
		return "USEITEM [" + time_ + " , " + slotObject_ + " , " + itemUsePos_ + " usetype"+useType+"]";
	}
}
