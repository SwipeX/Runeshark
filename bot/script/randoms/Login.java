package bot.script.randoms;

import java.awt.Color;
import java.awt.Point;

import ui.BotFrame;

import api.methods.ColorUtil;

/**
 * 
 * This isn't done Need to get Points and make it click them :')
 */
public class Login extends Random {

	LoginStage ls;

	public class CP {

		Point point;
		Color color;

		CP(int x, int y, Color c) {
			this.point = new Point(x, y);
			this.color = c;
		}

		public Point getPoint() {
			return point;
		}

		public Color getColor() {
			return color;
		}

	}

	public class LoginStage {

		int ID = -1;

		CP[] Cps;

		LoginStage(int ID, CP[] CP) {
			this.Cps = CP;
			this.ID = ID;
		}

		public CP[] getCPs() {
			return Cps;
		}

		public int getID() {
			return ID;
		}

	}

	public CP[] LoginCP = { new CP(385, 142, new Color(235, 224, 188)),
			new CP(341, 321, new Color(200, 200, 200)),
			new CP(326, 213, new Color(235, 224, 188)),
			new CP(250, 329, new Color(169, 169, 168)) };

	public CP[] BLoginCP = { new CP(319, 311, new Color(29, 28, 28)),
			new CP(316, 241, new Color(235, 224, 188)),
			new CP(281, 274, new Color(251, 130, 28)),
			new CP(435, 201, new Color(235, 224, 188)) };

	public CP[] LobbyCP = { new CP(189, 30, new Color(100, 148, 79)),
			new CP(284, 29, new Color(197, 224, 186)),
			new CP(490, 30, new Color(215, 193, 150)),
			new CP(586, 29, new Color(117, 191, 195)) };

	Stage BLoginStage = new Stage(new LoginStage(0, BLoginCP));
	Stage LoginStage = new Stage(new LoginStage(1, LoginCP));
	Stage LobbyStage = new Stage(new LoginStage(2, LobbyCP));

	Stage[] Stages = { BLoginStage, LoginStage, LobbyStage };

	public class Stage {
		LoginStage Stage;

		Stage(LoginStage ls) {
			this.Stage = ls;
		}

		public LoginStage getStage() {
			return Stage;
		}
	}

	/**
	 * main method to login
	 */
	public int loop() {
		Stage stage = getStage();
		if (stage.equals(Stages[0])) {
			// Click leave alone
			return 50;
		} else if (stage.equals(Stages[1])) {
			// if username has any text delete
			// type in user and pass
			// click login
			return 70;
		} else if (stage.equals(Stages[2])) {
			// Click login button
			return 80;
		} else if (stage.equals(Stages[3])) {
			// done
			return -1;
		}
		return 100;
	}

	public Stage getStage() {
		for (Stage s : Stages) {
			for (CP cp : s.getStage().getCPs()) {
				int index = 0;
				if (getColor(cp.getPoint()).equals(cp.getColor())) {
					index++;
				}
				if (index == s.getStage().getCPs().length)
					return s;
			}
		}
		return null;
	}

	public Color getColor(Point p) {
		return ColorUtil.getColor(p);
	}

	@Override
	public boolean onStart() {
		BotFrame.log("Login Started!");
		return true;
	}

	@Override
	public boolean startCondition() {
		// TODO Auto-generated method stub
		return false;
	}
}
