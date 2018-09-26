// GOOD Name: SETCONDITION, Original AS3 class: _-g7.as

package packets.client;

import java.io.IOException;

import packets.*;

public class SETCONDITION_Packet implements RWable {
	public int conditionEffect_; // uint
	public float conditionDuration_; // Number

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.conditionEffect_ = badi.readByte(); // Byte
		this.conditionDuration_ = badi.readFloat(); // Float
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeByte(this.conditionEffect_); // Byte
		bado.writeFloat(this.conditionDuration_); // Float
	}

	public int getId() {
		return 28;
	}

	public String toString() {
		return "SETCONDITION [" + conditionEffect_ + " , " + conditionDuration_ + "]";
	}
}
