package client.base;

import data.ObjectStatus;
import packets.RWable;

public interface GameListener {

	public void loop();
	public void packetRecieved(RWable rw);
	public void objectRemoved(ObjectStatus os);
	public void objectAdded(ObjectStatus os);

}
