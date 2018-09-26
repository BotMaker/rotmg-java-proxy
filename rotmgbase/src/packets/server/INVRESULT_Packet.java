// GOOD Name: INVRESULT, Original AS3 class: _-WK.as

package packets.server;

import java.io.IOException;

import packets.*;

public class INVRESULT_Packet implements RWable {
	public int result_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.result_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(result_); // Int
	}

public int getId() {
return 4;
}
public String toString() {
		return "INVRESULT [" + result_ + "]";
	}
}
