// GOOD Name: ACCOUNTLIST, Original AS3 class: _-l6.as

package packets.server;

import java.io.IOException;
import java.util.ArrayList;

import packets.*;

public class ACCOUNTLIST_Packet implements RWable {
	public int accountListId_; // int
	public ArrayList<Integer> accountIds_; // Vector.<int>

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.accountListId_ = badi.readInt(); // Int
		short size = badi.readShort();
		this.accountIds_ = new ArrayList<Integer>(size);
		for (int i = 0 ; i < size; i++) {
			accountIds_.add(badi.readInt());
		}
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(accountListId_); // Int
		bado.writeShort(accountIds_.size());
		for (int i = 0 ; i < accountIds_.size(); i++) {
			bado.writeInt(accountIds_.get(i));
		}
	}

public int getId() {
return 66;
}
public String toString() {
		return "ACCOUNTLIST [" + accountListId_ + " , " + accountIds_ + "]";
	}
}
