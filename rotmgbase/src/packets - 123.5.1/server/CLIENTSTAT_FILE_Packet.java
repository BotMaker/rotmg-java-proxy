// GOOD Name: CLIENTSTAT, Original AS3 class: File.as

package packets.server;

import java.io.IOException;
import java.nio.charset.Charset;

import packets.*;

public class CLIENTSTAT_FILE_Packet implements RWable {
	public String filename_; // String
	public String file_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.filename_ = badi.readUTF(); // UTF
		byte[] buf = new byte[badi.readInt()];
		badi.readFully(buf);
		file_ = new String(buf, Charset.forName("UTF-8"));
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(filename_); // UTF
		bado.writeInt(file_.getBytes().length);
		bado.write(file_.getBytes());
	}

public int getId() {
return 56;
}
public String toString() {
		return "CLIENTSTAT [" + filename_ + " , " + file_ + "]";
	}
}
