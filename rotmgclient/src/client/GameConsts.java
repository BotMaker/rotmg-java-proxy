package client;

public interface GameConsts {
	public final static String NEXUS_USEAST3 = "50.18.113.133"; //"50.19.47.160"; // "50.19.47.160"; 46.137.247.5 67.202.12.226
	public final static double SPEED_MULTIPLIER = 1d / 67d / 200d; // 0.014925373134328358 | 7.462686567164179e-5
	public final static double SPEED_BASE = 0.7d / 200d; // 0.004 0.8
	public final static int ATTACK_RATE_BASE = 485;
	public final static double ATTACK_RATE_DEX_MULTIPLIER = 8 + 1 / 3;
	
	public interface Modes {
		// Positive values are instanced dungeons
		public final static int MAP_TESTING = -6;
		public final static int VAULT = -5;
		public final static int TUTORIAL_PT2 = -4;
		public final static int REALM = -3; // seems to auto join a realm?
		public final static int NORMAL = -2;
		public final static int TUTORIAL_PT1 = -1;
	}
	
	public interface Characters {
		public final static int SORCERER = 0x325;
		public final static int TRICKSTER = 0x324;
		public final static int MYSTIC = 0x323;
		public final static int HUNTRESS = 0x322;
		public final static int NECROMANCER = 0x321;
		public final static int ASSASSIN = 0x320;
		public final static int PALADIN = 0x31F;
		public final static int KNIGHT = 0x31E;
		public final static int WARRIOR = 0x31D;
		public final static int PRIEST = 0x310;
		public final static int WIZARD = 0x30E;
		public final static int ARCHER = 0x307;
		public final static int ROGUE = 0x300;
	}

}
