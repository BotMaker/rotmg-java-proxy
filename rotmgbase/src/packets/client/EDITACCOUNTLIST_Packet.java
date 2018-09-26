// GOOD Name: EDITACCOUNTLIST, Original AS3 class: _-tD.as

package packets.client;

import java.io.IOException;

import packets.*;

public class EDITACCOUNTLIST_Packet implements RWable {
	public int accountListId_; // int
	public Boolean add_; // Boolean
	public int objectId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.accountListId_ = badi.readInt(); // Int
		this.add_ = badi.readBoolean(); // Boolean
		this.objectId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.accountListId_); // Int
		bado.writeBoolean(this.add_); // Boolean
		bado.writeInt(this.objectId_); // Int
	}

	public int getId() {
		return 53;
	}

	public String toString() {
		return "EDITACCOUNTLIST [" + accountListId_ + " , " + add_ + " , " + objectId_ + "]";
	}
}
