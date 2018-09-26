// GOOD Name: AOEACK, Original AS3 class: _-Lr.as

package packets.client;

import java.io.IOException;

import packets.*;
import data.*;

public class AOEACK_Packet implements RWable {
	public int time_; // int
	public Location position_; // _-Tq

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time_ = badi.readInt(); // Int
		this.position_ = new Location(badi);
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time_); // Int
		this.position_.writeToOutput(bado);
	}

	public int getId() {
		return 59;
	}

	public String toString() {
		return "AOEACK [" + time_ + " , " + position_ + "]";
	}
}
