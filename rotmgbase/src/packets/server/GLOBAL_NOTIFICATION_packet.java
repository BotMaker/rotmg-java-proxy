package packets.server;

import java.io.IOException;

import packets.*;

public class GLOBAL_NOTIFICATION_packet implements RWable{
	public int Id_; // int
	public String text_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.Id_ = badi.readInt(); // Int
		this.text_ = badi.readUTF(); // UTF

	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(Id_); // Int
		bado.writeUTF(text_); // UTF
	}

public int getId() {
return 9;
}
public String toString() {
		return "GLOBAL_NOTIFICATION [" + Id_ + " , " + text_ + "]";
	}
}
