package api.methods;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class HighScores {

	private static final String HISCORES = "http://services.runescape.com/m=hiscore/g=runescape/compare.ws?user1=";

	/**
	 * Gets hiscores of the given username
	 * 
	 * @param name
	 *            Username to fetch info
	 * @return UserInfo containing all 26 skills informations, including
	 *         level,xp,rank
	 * @throws Exception
	 *             if an error occurs while connecting,parsing.
	 */
	public static UserInfo fetchInfo(String name) throws Exception {
		UserInfo user = new UserInfo();
		URL url = new URL(HISCORES + name);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		int count = 0;
		int skills = 0;
		String line = "";
		while ((line = in.readLine()) != null) {
			count++;
			if (count < 363) {
				continue; // no checking required.
			} else if (count > 955 || skills >= 26) {
				break;
			}
			String skillname = null;
			int level = 1;
			while (null != line && !line.contains("columnLevel")) {
				line = in.readLine();
				count++;
			}
			line = in.readLine();
			if (line != null && !line.equals("--")) {
				level = Integer.parseInt(line.replaceAll(",", ""));
			}
			int xp = -1;
			while (null != line && !line.contains("columnTotal")) {
				line = in.readLine();
				count++;
			}
			line = in.readLine();
			if (line != null && !line.equals("--")) {
				xp = Integer.parseInt(line.replaceAll(",", ""));
			}
			int rank = -1;
			while (null != line && !line.contains("columnRank")) {
				line = in.readLine();
				count++;
			}
			line = in.readLine();
			if (line != null && !line.equals("--")) {
				rank = Integer.parseInt(line.replaceAll(",", ""));
			}
			while (null != line && !line.contains("statName")) {
				line = in.readLine();
				count++;
			}
			if (line != null) {
				skillname = line.substring(41, line.indexOf("<", 41));
			}
			user.insertSkill(new SkillInfo(skillname, level, xp, rank));
			skills++;
			count += 3;
		}
		in.close();
		return user;
	}

	public static class UserInfo {

		public int start = 0;
		SkillInfo[] data = new SkillInfo[26];
		int place = 0;

		public void insertSkill(SkillInfo info) {
			if (place < 26) {
				data[place] = info;
				place++;
			}
		}

		public SkillInfo getSkill(String skillname) {
			for (SkillInfo skill : data) {
				if (skill != null && skill.name.equalsIgnoreCase(skillname)) {
					return skill;
				}
			}
			return null;
		}

		public SkillInfo[] getSkills() {
			return data;
		}

		public int combatLevel() {
			int hp = getSkill("constitution").level;
			if (hp == 1) {
				hp = 10;
			}
			int at = getSkill("attack").level;
			int def = getSkill("defence").level;
			int pra = getSkill("prayer").level;
			int summoning = getSkill("summoning").level;
			int ranged = getSkill("ranged").level;
			int str = getSkill("strength").level;
			int ma = getSkill("magic").level;
			return Math.max(
					(int) (0.25 * (1.3 * (at + str) + def + hp
							+ Math.floor(0.5 * pra) + Math
							.floor(0.5 * summoning))), Math.max(
							(int) (0.25 * (1.3 * (1.5 * ma) + def + hp
									+ Math.floor(0.5 * pra) + Math
									.floor(0.5 * summoning))),
							(int) (0.25 * (1.3 * (1.5 * ranged) + def + hp
									+ Math.floor(0.5 * pra) + Math
									.floor(0.5 * summoning))))); // runescape
																	// formula
		}
	}

	public static class SkillInfo {

		public String name;
		public int level, xp, rank;

		public SkillInfo(String skill, int level, int xp, int rank) {
			name = skill;
			this.level = level;
			this.xp = xp;
			this.rank = rank;
		}

		@Override
		public String toString() {
			return "Skill: " + name + " Level: " + level + " xp: " + xp
					+ " rank: " + rank;
		}
	}
}
