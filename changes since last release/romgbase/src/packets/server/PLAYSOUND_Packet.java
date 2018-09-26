// GOOD Name: PLAYSOUND, Original AS3 class: _-Qz.as

package packets.server;

import java.io.IOException;

import packets.*;

public class PLAYSOUND_Packet implements RWable {
	public int ownerId_; // int
	public int soundId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.ownerId_ = badi.readInt(); // Int
		this.soundId_ = badi.readUnsignedByte(); // UnsignedByte
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(ownerId_); // Int
		bado.writeByte(soundId_); // UnsignedByte
	}

public int getId() {
return 13;
}
public String toString() {
		return "PLAYSOUND [" + ownerId_ + " , " + soundId_ + "]";
	}
}
