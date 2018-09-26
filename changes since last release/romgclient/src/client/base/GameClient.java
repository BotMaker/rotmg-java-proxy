
// NOTE: This is only the content that was changed in this class

public HELLO_Packet constructHelloPacket(int gm) {
	HELLO_Packet hp = new HELLO_Packet();
	hp.buildVersion_ = "123.0.0";
	hp.gameId_ = gm;
	hp.keyTime_ = -1;
	hp.secret_ = "";
	hp.key_ = new byte[0];
	if (_accGUID == null) {
		_accGUID = SCry.encrypt(SCry.createGuestGUID());
		_accPass = "";
	}
	hp.guid_ = getGuid();
	hp.password_ = getPassword();
	hp.jD = "";
	hp.pk = "";
	hp.Tq = "rotmg";
	hp.H = "";
	hp.playPlatform = "rotmg";
	return hp;
}

public HELLO_Packet constructHelloPacket(int gm, int keyTime, byte[] key) {
	HELLO_Packet hp = new HELLO_Packet();
	hp.buildVersion_ = "123.0.0";
	hp.gameId_ = gm;
	hp.keyTime_ = keyTime;
	hp.secret_ = "";
	hp.key_ = key;
	if (_accGUID == null) {
		_accGUID = SCry.encrypt(SCry.createGuestGUID());
		_accPass = "";
	}
	hp.guid_ = getGuid();
	hp.password_ = getPassword();
	hp.jD = "";
	hp.pk = "";
	hp.Tq = "rotmg";
	hp.H = "";
	hp.playPlatform = "rotmg";
	return hp;
}