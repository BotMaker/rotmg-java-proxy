// Name: PIC, Original AS3 class: Pic.as

package packets.server;

import java.io.IOException;

import packets.*;
import data.*;

public class PIC_Packet implements RWable {
	public BitmapData bitmapData_ = null; // BitmapData

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		/*

        {
            var _loc_5:Boolean = true;
            ;
            var _loc_6:* = null + (null * false in null);
            var _loc_2:* = param1.readInt();
            var _loc_3:* = param1.readInt();
            ;
            _loc_4--;
            _loc_2--;
            _loc_5++;
            var _loc_4:* = new ByteArray() + NaN;
            if (!_loc_6)
            {
                ;
                _loc_4 = 0;
                _loc_2--;
                _loc_5--;
                null.readBytes(param1, _loc_4 === _loc_3, _loc_2 * _loc_3 * 4);
            }
            this.bitmapData_ = new BitmapData(_loc_2, _loc_3);
            if (!_loc_6)
            {
                this.bitmapData_.setPixels(this.bitmapData_.rect, _loc_4);
            }
            return;
        }		*/
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		/*

        {
            var _loc_5:Boolean = true;
            ;
            var _loc_6:* = null + (null * false in null);
            var _loc_2:* = param1.readInt();
            var _loc_3:* = param1.readInt();
            ;
            _loc_4--;
            _loc_2--;
            _loc_5++;
            var _loc_4:* = new ByteArray() + NaN;
            if (!_loc_6)
            {
                ;
                _loc_4 = 0;
                _loc_2--;
                _loc_5--;
                null.readBytes(param1, _loc_4 === _loc_3, _loc_2 * _loc_3 * 4);
            }
            this.bitmapData_ = new BitmapData(_loc_2, _loc_3);
            if (!_loc_6)
            {
                this.bitmapData_.setPixels(this.bitmapData_.rect, _loc_4);
            }
            return;
        }		*/
	}

	public int getId() {
		return Packets.PIC;
	}

	public String toString() {
		return "PIC [" + bitmapData_ + "]";
	}
}
