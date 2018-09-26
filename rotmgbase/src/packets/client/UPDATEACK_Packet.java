package packets.client;

import java.io.IOException;

import packets.ByteArrayDataInput;
import packets.ByteArrayDataOutput;
import packets.RWable;

public class UPDATEACK_Packet implements RWable {

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {

	}

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {

	}

	public int getId() {
		return 11;
	}

	public String toString() {
		return "UPDATEACK []";
	}

}
