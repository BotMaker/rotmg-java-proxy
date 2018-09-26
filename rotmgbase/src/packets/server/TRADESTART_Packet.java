// GOOD Name: TRADESTART, Original AS3 class: _-jE.as

package packets.server;

import java.io.IOException;
import java.util.Arrays;

import packets.*;
import data.*;

public class TRADESTART_Packet implements RWable {
	public TradeItem[] myItems_; // Vector.<_-ZS>
	public String yourName_; // String
	public TradeItem[] yourItems_; // Vector.<_-ZS>

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
//		((Integer)null).toString();
		int len = badi.readUnsignedShort();
		System.out.println(len);
		myItems_ = new TradeItem[len];
		for (int i = 0 ; i < len ; i++) {
			myItems_[i] = new TradeItem(badi);
		}
		this.yourName_ = badi.readUTF(); // UTF
		len = badi.readUnsignedShort();
		System.out.println(len);
		yourItems_ = new TradeItem[len];
		for (int i = 0 ; i < len ; i++) {
			yourItems_[i] = new TradeItem(badi);
		}
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeShort(myItems_.length);
		for (int i = 0 ; i < myItems_.length ; i++)
			myItems_[i].writeToOutput(bado);
		bado.writeUTF(yourName_); // UTF
		bado.writeShort(yourItems_.length);
		for (int i = 0 ; i < yourItems_.length ; i++)
			yourItems_[i].writeToOutput(bado);
		/*

        {
            ;
            _loc_4--;
            _loc_2++;
            var _loc_4:Boolean = false;
            var _loc_5:Boolean = false;
            var _loc_2:int = 0;
            var _loc_3:* = param1.readShort();
            if (!_loc_4)
            {
                if (_loc_5 || this)
                {
                }
                _loc_2 = _loc_3;
            }
            while (_loc_2 < this.myItems_.length)
            {
                
                _-o._-Yh(this.myItems_[_loc_2]);
                _loc_2++;
                if (_loc_5 || this)
                {
                }
            }
            this.myItems_.length = Math.min(_loc_3, this.myItems_.length);
            if (_loc_5 || this)
            {
                while (this.myItems_.length < _loc_3)
                {
                    
                    this.myItems_.push(_-o._-nG(_-ZS) as _-ZS);
                }
                _loc_2 = 0;
                if (!_loc_4)
                {
                    do
                    {
                        
                        ;
                        _loc_3++;
                        (null % (null + (true + false - this.myItems_) >= null))[_loc_2].parseFromInput(param1);
                        _loc_2++;
                    }while (_loc_2 < _loc_3)
                }
                this.yourName_ = param1.readUTF();
            }
            if (!(_loc_4 && this))
            {
                _loc_3 = param1.readShort();
                if (_loc_5 || _loc_2)
                {
                    _loc_2 = _loc_3;
                    while (_loc_2 < this.yourItems_.length)
                    {
                        
                        _-o._-Yh(this.yourItems_[_loc_2]);
                        _loc_2++;
                    }
                }
            }
            this.yourItems_.length = Math.min(_loc_3, this.yourItems_.length);
            while (this.yourItems_.length < _loc_3)
            {
                
                this.yourItems_.push(_-o._-nG(_-ZS) as _-ZS);
            }
            ;
            _loc_4--;
            _loc_2 = null;
            _loc_2 = 0;
            if (!(_loc_4 && _loc_3))
            {
                while (_loc_2 < _loc_3)
                {
                    
                    this.yourItems_[_loc_2].parseFromInput(param1);
                }
                _loc_2++;
            }
            return;
        }		*/
	}

	public int getId() {
		return 9;
	}
	
	public String toString() {
		return "TRADESTART [" + Arrays.asList(myItems_) + " , " + yourName_ + " , " + Arrays.asList(yourItems_) + "]";
	}
}
