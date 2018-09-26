// GOOD Name: TRADEREQUESTED, Original AS3 class: _-Fd.as

package packets.server;

import java.io.IOException;

import packets.*;

public class TRADEREQUESTED_Packet implements RWable {
	public String name_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.name_ = badi.readUTF(); // UTF
		/*

        {
            var _loc_2:Boolean = true;
            ;
            _loc_2++;
            var _loc_3:* = null * (-null + 1 < false);
            if (_loc_2 || _loc_3)
            {
                this.name_ = param1.readUTF();
            }
            return;
        }		*/
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(name_); // UTF
		/*

        {
            var _loc_2:Boolean = true;
            ;
            _loc_2++;
            var _loc_3:* = null * (-null + 1 < false);
            if (_loc_2 || _loc_3)
            {
                this.name_ = param1.readUTF();
            }
            return;
        }		*/
	}

public int getId() {
return 36;
}
public String toString() {
		return "TRADEREQUESTED [" + name_ + "]";
	}
}
