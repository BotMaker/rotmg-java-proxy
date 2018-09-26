// 123.0.0 com.company.assembleegameclient.net.messages.data._-ZS.as

package data;

import java.io.IOException;

import packets.*;

public class TradeItem {

	public int item; // int
	public int bT; // int
	public boolean eQ; // boolean
	public boolean included_; // boolean

	public TradeItem(ByteArrayDataInput badi) throws IOException {
		parseFromInput(badi);
	}

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		item = badi.readInt();
		bT = badi.readInt();
		eQ = badi.readBoolean();
		included_ = badi.readBoolean();
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(item);
		bado.writeInt(bT);
		bado.writeBoolean(eQ);
		bado.writeBoolean(included_);
	}

	public String toString() {
		return "tradeitem [" + item + ", " + bT + ", " + eQ + ", " + included_ + "]";
	}

}
