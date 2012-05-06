package api.methods;

import java.awt.Color;
import java.awt.Rectangle;

public class Skills {

	public static Color data = new Color(255, 140, 0);

	public static final int[] XP_TABLE = { 0, 0, 83, 174, 276, 388, 512, 650,
			801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523,
			3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031,
			13363, 14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408,
			33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127,
			83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636,
			184040, 203254, 224466, 247886, 273742, 302288, 333804, 368599,
			407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445,
			899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200,
			1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
			3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253,
			7195629, 7944614, 8771558, 9684577, 10692629, 11805606, 13034431,
			14391160, 15889109, 17542976, 19368992, 21385073, 23611006,
			26068632, 28782069, 31777943, 35085654, 38737661, 42769801,
			47221641, 52136869, 57563718, 63555443, 70170840, 77474828,
			85539082, 94442737, 104273167 };

	public enum Skill {
        ATTACK(0,"Attack",null),
        DEFENSE(1,"Defense",null),
        STRENGTH(2,"Strength",null),
        CONSTITUTION(3,"Constitution",null),
        RANGE(4,"Ranfe",null),
        PRAYER(5,"Prayer",null),
        MAGIC(6,"Magic",null),
        COOKING(7,"Cooking",null),
        WOODCUTTING(8,"Woodcutting",null),
        FLETCHING(9,"Fletching",null),
        FISHING(10,"Fishing",null),
        FIREMAKING(11,"FireMaking",null),
        CRAFTING(12,"Crafting",null),
        SMITHING(13,"Smithing",null),
        MINING(14,"Mining",null),
        HERBLORE(15,"Herbolre",null),
        AGILITY(16,"Agility",null),
        THIEVING(17,"Thieving",null),
        SLAYER(18,"Slayer",null),
        FARMING(19,"Farming",null),
        RUNECRAFTING(2,"RuneCrafting",null),
        HUNTER(21,"Hunter",null),
        CONSTRUCTION(22,"Constructuin",null),
        SUMMONING(23,"Summoing",null),
        DUNGEONEERING(24,"Dungeonring",null); // TODO

		public String name;
		public int ID;
		public Rectangle rect;

		Skill(int ID, String name, Rectangle r) {
			this.rect = r;
			this.ID = ID;
			this.name = name;
		}

		public int getID() {
			return ID;
		}

		public String getName() {
			return name;
		}

		public Rectangle getRectangle() {
			return rect;
		}
	}

	public static int getSkillLevel(Skill skill) {
		Tabs.setTab("Stats");
		return Integer
				.parseInt(OCR.getTextFromColor(skill.getRectangle(), data));
	}

	/**
	 * Gets the level at the given experience.
	 * 
	 * @param exp
	 *            The experience.
	 * @return The level based on the experience given.
	 * @see #XP_TABLE
	 */
	public static int getLevelAt(final int exp) {
		for (int i = Skills.XP_TABLE.length - 1; i > 0; i--) {
			if (exp > Skills.XP_TABLE[i]) {
				return i;
			}
		}
		return 1;
	}

	/**
	 * Gets the experience at the given level.
	 * 
	 * @param lvl
	 *            The level.
	 * @return The level based on the experience given.
	 */
	public static int getExpAt(final int lvl) {
		if (lvl > 120) {
			return 1;
		}
		return Skills.XP_TABLE[lvl - 1];
	}

}
