// GOOD Name: BUYRESULT, Original AS3 class: _-qY.as

package packets.server;

import java.io.IOException;

import packets.*;

public class BUYRESULT_Packet implements RWable {
	public int result_; // int
	public String resultString_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.result_ = badi.readInt(); // Int
		this.resultString_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(result_); // Int
		bado.writeUTF(resultString_); // UTF
	}

public int getId() {
return 10;
}
public String toString() {
		return "BUYRESULT [" + result_ + " , " + resultString_ + "]";
	}
}
