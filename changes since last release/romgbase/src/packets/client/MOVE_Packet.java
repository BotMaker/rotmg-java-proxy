// GOOD Name: MOVE, Original AS3 class: _-gd.as

package packets.client;

import java.io.IOException;
import java.util.Arrays;

import packets.*;
import data.*;

public class MOVE_Packet implements RWable {
	public int tickId_; // int
	public int time_; // int
	public Location newPosition_; // _-Tq
	public LocationWTime[] records_; // Vector.<_-Tj>

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.tickId_ = badi.readInt(); // Int
		this.time_ = badi.readInt(); // Int
		this.newPosition_ = new Location(badi);
		short size = badi.readShort();
		if (size <= 0) {
			this.records_ = new LocationWTime[0];
			return;
		}
		this.records_ = new LocationWTime[size];
		for (int i = 0; i < size; i++) {
			records_[i] = new LocationWTime(badi);
		}
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.tickId_); // Int
		bado.writeInt(this.time_); // Int
		newPosition_.writeToOutput(bado);
		bado.writeShort(this.records_.length); // Short
		for (int i = 0; i < this.records_.length; i++) {
			records_[i].writeToOutput(bado);
		}
	}

	public int getId() {
		return 61;
	}

	public String toString() {
		return "MOVE [" + tickId_ + " , " + time_ + " , " + newPosition_ + " , " + Arrays.asList(records_) + "]";
	}
}
