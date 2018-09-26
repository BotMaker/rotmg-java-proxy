// GOOD Name: GROUNDDAMAGE, Original AS3 class: _-bu.as

package packets.client;

import java.io.IOException;

import packets.*;
import data.*;

public class GROUNDDAMAGE_Packet implements RWable {
	public int time_; // int
	public Location position_; // _-Tq

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt(); // Int
		position_ = new Location(badi);
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_); // Int
		position_.writeToOutput(bado);
	}

	public int getId() {
		return 19;
	}

	public String toString() {
		return "GROUNDDAMAGE [" + time_ + " , " + position_ + "]";
	}
}
