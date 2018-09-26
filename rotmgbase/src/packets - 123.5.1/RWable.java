package packets;

import java.io.IOException;

public interface RWable {
	public void writeToOutput(ByteArrayDataOutput bado) throws IOException;
	public void parseFromInput(ByteArrayDataInput badi) throws IOException;
	public int getId();
}
