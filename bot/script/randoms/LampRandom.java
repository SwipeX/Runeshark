package bot.script.randoms;

import java.awt.Color;

import api.methods.ColorUtil;
import api.methods.Inventory;
import api.methods.Mouse;
import bot.script.ScriptManifest;
import bot.script.randoms.Random;

import java.awt.*;
import javax.swing.*;

@ScriptManifest(authors = { "Vertigo" }, category = "Randoms", name = "lamp") 
public class LampRandom extends Random{
	
	Color lamp = new Color (154, 152, 22);
	Color attack = new Color (186, 184, 180);
	Color strength = new Color (168, 105, 70);
	Color ranged = new Color (66, 42, 15);
	Color magic = new Color (23, 30, 49);
	Color defence = new Color (70, 72, 71);
	Color crafting = new Color (44, 31, 29);
	Color hitpoints = new Color (165, 47, 45);
	Color prayer = new Color (99, 152, 176);
	Color agility = new Color (180, 110, 19);
	Color herblore = new Color (62, 114, 49);
	Color thieving = new Color (28, 25, 23);
	Color fishing = new Color (67, 84, 92);
	Color runecrafting = new Color (241, 88, 45);
	Color slayer = new Color (170, 146, 126);
	Color farming = new Color (100, 98, 98);
	Color minning = new Color (177, 136, 69);
	Color smithing = new Color (56, 56, 55);
	Color hunter = new Color (67, 56, 60);
	Color cooking = new Color (107, 86,59);
	Color firemaking = new Color (238, 69, 31);
	Color woodcutting = new Color (17, 27, 17);
	Color fletching = new Color (203, 162, 130);
	Color construction = new Color (125, 98, 66);
	Color summoning = new Color (81, 100, 111);
	Color dungeoneering = new Color (142, 56, 12);
	Color confirm_text = new Color (70, 50,10);
	Color skill = new Color (0, 0, 0);
	Color X = new Color (165, 125, 63);

	@Override
	public int loop() {
Inventory.getSlotAt(0);//(lamp);
if (lamp != null) {
	Mouse.click();
	};
	Point[] dwPoints;
	dwPoints = ColorUtil.findAllColor(X);
	for(Point p : dwPoints) {
		if (p != null){
			chooseSkill();
		}
	}
	return 1000;
	
}
	
	/*
	 * Created by JFormDesigner on Sun Feb 26 22:29:35 BRT 2012
	 */



	public void chooseSkill() {
		
	}

	/**
	 * @author Sam Med
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public class anti_random  {


		public void initComponents() {
			// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
			// Generated using JFormDesigner Evaluation license - Sam Med
			frame1 = new JFrame();
			label1 = new JLabel();
			comboBox1 = new JComboBox();
			button1 = new JButton();

			//======== frame1 ========
			{
				frame1.setTitle("Lamp anti-random");
				Container frame1ContentPane = frame1.getContentPane();
				frame1ContentPane.setLayout(null);

				//---- label1 ----
				label1.setText("Choose the skill:");
				frame1ContentPane.add(label1);
				label1.setBounds(new Rectangle(new Point(15, 30), label1.getPreferredSize()));

				//---- comboBox1 ----
				comboBox1.setModel(new DefaultComboBoxModel(new String[] {
					"Attack",
					"strength ",
					"ranged ",
					"magic ",
					"defence ",
					"crafting",
					"hitpoints",
					"prayer",
					"agility ",
					"herblore",
					"thieving ",
					"fishing ",
					"runecrafting ",
					"slayer ",
					"farming ",
					"minning",
					"smithing",
					"hunter",
					"cooking ",
					"firemaking",
					"woodcutting",
					"fletching ",
					"construction ",
					"summoning ",
					"dungeoneering"
				}));
				frame1ContentPane.add(comboBox1);
				comboBox1.setBounds(15, 55, 125, comboBox1.getPreferredSize().height);

				//---- button1 ----
				button1.setText("Ok");
				frame1ContentPane.add(button1);
				button1.setBounds(new Rectangle(new Point(15, 100), button1.getPreferredSize()));

				{ // compute preferred size
					Dimension preferredSize = new Dimension();
					for(int i = 0; i < frame1ContentPane.getComponentCount(); i++) {
						Rectangle bounds = frame1ContentPane.getComponent(i).getBounds();
						preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
						preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
					}
					Insets insets = frame1ContentPane.getInsets();
					preferredSize.width += insets.right;
					preferredSize.height += insets.bottom;
					frame1ContentPane.setMinimumSize(preferredSize);
					frame1ContentPane.setPreferredSize(preferredSize);
				}
				frame1.pack();
				frame1.setLocationRelativeTo(frame1.getOwner());
			}
			// JFormDesigner - End of component initialization  //GEN-END:initComponents
		}

		// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
		// Generated using JFormDesigner Evaluation license - Sam Med
		private JFrame frame1;
		private JLabel label1;
		private JComboBox comboBox1;
		private JButton button1;
		// JFormDesigner - End of variables declaration  //GEN-END:variables
	}

	@Override
	public boolean onStart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startCondition() {
		// TODO Auto-generated method stub
		return false;
	}
}
