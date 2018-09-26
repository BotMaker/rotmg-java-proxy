// GOOD Name: NEW_TICK, Original AS3 class: _-Hk.as

package packets.server;

import java.io.IOException;
import java.util.ArrayList;

import packets.*;
import data.*;

public class NEW_TICK_Packet implements RWable {
	public int tickId_; // int
	public int tickTime_; // int
	public ArrayList<ObjectStatusData> statuses_; // Vector.<ObjectStatusData>

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.tickId_ = badi.readInt(); // Int
		this.tickTime_ = badi.readInt(); // Int
		short size = badi.readShort();
		statuses_ = new ArrayList<ObjectStatusData>(size);
		for (int i = 0 ; i < size ; i++) {
			statuses_.add(new ObjectStatusData(badi));
		}
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(tickId_); // Int
		bado.writeInt(tickTime_); // Int
		bado.writeShort(statuses_.size());
		for (int i = 0 ; i < statuses_.size() ; i++)
			statuses_.get(i).writeToOutput(bado);
	}
	
	public ObjectStatusData getObjectStatusData(int objid) {
		for (ObjectStatusData osd : statuses_) {
			if (osd.objectId == objid)
				return osd;
		}
		return null;
	}

public int getId() {
return 62;
}
public String toString() {
		return "NEW_TICK [" + tickId_ + " , " + tickTime_ + " , " + statuses_ + "]";
	}
}
