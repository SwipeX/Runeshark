package ui;

import api.methods.MethodProvider;
import bot.InputManager;
import bot.debug.*;
import bot.interfaces.CanvasRenewal;
import bot.log.TextAreaLogHandler;
import bot.script.ScriptManager;
import loader.RSLoader;
import util.Configuration;
import util.io.HttpClient;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class BotFrame extends javax.swing.JFrame implements CanvasRenewal,
		WindowListener {

	MainDebug mainDebug;
	MouseDebug drawMouse;
	InventoryDebug inventorydebug;
	TabDebug tabdebug;

	public MenuItem stascript;
	private JMenuItem jMenuItem8;
	private JMenuItem jMenuItem9;
	private JMenuItem jMenuItem10;
	private JMenuItem jMenuItem11;
	private JMenuItem jMenuItem12;
	public static BotFrame botframe;

	public BotFrame(Applet a) {
		super("RuneShark - " + Configuration.version);
		botframe = this;
		createTray();
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
		this.setDefaultCloseOperation(3);
		try {
			setIconImage(ImageIO.read(new URL(
					"http://www.runeshark.net/images/icon.png")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainDebug = new MainDebug();
		drawMouse = new MouseDebug();
		inventorydebug = new InventoryDebug();
		tabdebug = new TabDebug();
		Configuration.doImages();
		new DebugHandler();
		while (RSLoader.getCurrent().getCanvas() == null) {

		}
		RSLoader.getCurrent().getCanvas().paint = DebugHandler.getCurrent();
		RSLoader.getCurrent().getCanvas().canvasRenew = this;
		initComponents(a);
		Configuration.registerLogging();
		setLocationRelativeTo(null);
	}

	public static BotFrame getCurrent() {
		return botframe;
	}

	private void initComponents(Applet a) {
		jTextArea1 = new LogTextArea();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem7 = new javax.swing.JCheckBoxMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		jMenuItem5 = new javax.swing.JCheckBoxMenuItem();
		jMenuItem6 = new javax.swing.JCheckBoxMenuItem();
		jMenuItem8 = new javax.swing.JMenuItem();
		jMenuItem9 = new javax.swing.JCheckBoxMenuItem();
		jMenuItem10 = new javax.swing.JCheckBoxMenuItem();
		jMenuItem11 = new javax.swing.JMenuItem();
		jMenuItem12 = new javax.swing.JMenuItem();
		stascript = new MenuItem("Start Script");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jTextArea1.setPreferredSize(new java.awt.Dimension(756, 80));
		textScroll = new JScrollPane(TextAreaLogHandler.TEXT_AREA,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textScroll.setBorder(null);
		textScroll.setPreferredSize(new Dimension(756, 100));
		textScroll.setVisible(true);

		jMenu1.setText("File");

		jMenuItem2.setText("Start Script");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenuItem12.setText("Pause Script");
		jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem12ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);
		jMenu1.add(jMenuItem12);

		jMenuItem3.setText("Stop Script");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem3);

		jMenuItem1.setText("Exit");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("Bot");

		jMenuItem7.setText("Send to Tray");
		jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem7ActionPerformed(evt);
			}
		});

		jMenu2.add(jMenuItem7);
		jMenuItem4.setText("Block Input");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem4ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem4);

		jMenuBar1.add(jMenu2);

		jMenu3.setText("Tools");
		jMenuItem5.setText("Color Debug");

		jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem5ActionPerformed(evt);
			}
		});

		jMenu3.add(jMenuItem5);

		jMenuItem6.setText("Draw Mouse");
		jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem6ActionPerformed(evt);
			}
		});

		jMenu3.add(jMenuItem6);

		jMenuItem8.setText("Sync Script Network");
		jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem8ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem8);

		jMenuItem9.setText("Inventory Debug");
		jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem9ActionPerformed(evt);
			}
		});

		jMenu3.add(jMenuItem9);

		jMenuItem10.setText("Tab Debug");
		jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem10ActionPerformed(evt);
			}
		});
		jMenu3.add(jMenuItem10);

		jMenuItem11.setText("DTM Creator");
		jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem11ActionPerformed(evt);
			}
		});

		jMenuBar1.add(jMenu3);
		getContentPane().add(textScroll, BorderLayout.SOUTH);
		TabbedBotPanel tbp = new TabbedBotPanel(a);
		getContentPane().add(a, BorderLayout.CENTER);
		setJMenuBar(jMenuBar1);
		MenuStrip m = new MenuStrip();
		add(m.getBar(), BorderLayout.NORTH);
		setSize(776, 693);
		setVisible(true);
	}

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ScriptSelector().setVisible(true);
			}
		});
	}

	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
		ScriptManager.getCurrent().stopScript();
		try {
			MenuStrip.jButton1.setIcon(new javax.swing.ImageIcon(Configuration
					.getIcon("control_play.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
		InputManager.getCurrent().setInput(
				!InputManager.getCurrent().getInput());
		if (InputManager.getCurrent().getInput()) {
			try {
				MenuStrip.jButton2.setIcon(new javax.swing.ImageIcon(
						Configuration.getIcon("tick.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				MenuStrip.jButton2.setIcon(new javax.swing.ImageIcon(
						Configuration.getIcon("keyboard.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (InputManager.getCurrent().getInput()) {
			jMenuItem4.setText("Block Input");
		} else {
			jMenuItem4.setText("Enable Input");
		}
	}

	private void jMenuItem5ActionPerformed(ActionEvent evt) {

		if (jMenuItem5.isSelected()) {
			DebugHandler.getCurrent().enableDebug(mainDebug);
		} else {
			DebugHandler.getCurrent().disableDebug(mainDebug);
		}
	}

	private void jMenuItem6ActionPerformed(ActionEvent evt) {
		if (jMenuItem6.isSelected()) {
			DebugHandler.getCurrent().enableDebug(drawMouse);
		} else {
			DebugHandler.getCurrent().disableDebug(drawMouse);
		}

	}

	private void jMenuItem7ActionPerformed(ActionEvent evt) {
		if (jMenuItem7.isSelected()) {
			hideTrayActionPerformed();
		}
	}

	private void jMenuItem10ActionPerformed(ActionEvent evt) {
		if (jMenuItem10.isSelected()) {
			DebugHandler.getCurrent().enableDebug(tabdebug);
		} else {
			DebugHandler.getCurrent().disableDebug(tabdebug);
		}
	}

	private void jMenuItem9ActionPerformed(ActionEvent evt) {
		if (jMenuItem9.isSelected()) {
			DebugHandler.getCurrent().enableDebug(inventorydebug);
		} else {
			DebugHandler.getCurrent().disableDebug(inventorydebug);
		}
	}

	private void jMenuItem11ActionPerformed(ActionEvent evt) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				// whatever
			}
		});
	}

	private void jMenuItem12ActionPerformed(ActionEvent evt) {
		if (ScriptManager.getCurrent().getCurrentScript().isPaused) {
			ScriptManager.getCurrent().unPauseScript();
		} else {
			ScriptManager.getCurrent().pauseScript();
		}
	}

	private void jMenuItem8ActionPerformed(ActionEvent evt) {
		ArrayList<String> URLS = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					new URL("http://www.runeshark.net/network/NetworkInfo.txt")
							.openStream()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String input;
		try {
			while ((input = br.readLine()) != null) {
				URLS.add(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < URLS.size(); i++) {
			try {
				HttpClient.download(
						new URL(URLS.get(i)),
						new File(Configuration.Paths.getScriptsDirectory()
								+ File.separator
								+ URLS.get(i).substring(
										URLS.get(i).lastIndexOf("/"))));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	Image i = null;

	public void createTray() {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						setIconImage((i = ImageIO.read(new URL(
								"http://www.runeshark.net/images/icon.png"))));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					TrayIcon trayIcon = new TrayIcon(i, "RuneShark",
							createAWTPopupMenu());
					trayIcon.setImageAutoSize(true);

					trayIcon.setToolTip("RuneShark");

					trayIcon.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							openTrayActionPerformed();
						}
					});
					SystemTray.getSystemTray().add(trayIcon);

				} catch (Exception e) {
					jMenuItem7.setEnabled(false);
				}
			}
		});
	}

	public void openTrayActionPerformed() {
		setVisible(true);
		jMenuItem7.setSelected(false);
		setState(0);
	}

	public void hideTrayActionPerformed() {
		this.setVisible(false);
	}

	private PopupMenu createAWTPopupMenu() {
		PopupMenu menu = new PopupMenu();

		MenuItem open = new MenuItem("Open");
		open.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				openTrayActionPerformed();
			}
		});
		menu.add(open);

		stascript.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						new ScriptSelector().setVisible(true);
					}
				});
			}
		});
		menu.add(stascript);
		MenuItem pascript = new MenuItem("Pause Script");
		pascript.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (ScriptManager.getCurrent().getCurrentScript().isPaused) {
					ScriptManager.getCurrent().unPauseScript();
				} else {
					ScriptManager.getCurrent().pauseScript();
				}
			}
		});
		menu.add(pascript);
		MenuItem stoscript = new MenuItem("Stop Script");
		stoscript.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ScriptManager.getCurrent().stopScript();
			}
		});
		menu.add(stoscript);

		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(exit);

		return menu;
	}

	@Deprecated
	public static void println(final Object s) {
		println(s);
	}

	private static final Logger log = Logger.getLogger("Bot");

	public static void log(String s) {
		log.info(s);
	}

	public static void error(String s) {
		log.severe(s);
	}

	public static void clearLog() {
		jTextArea1.clearSelection();
	}

	public static void hideLog() {
		jTextArea1.setVisible(false);
	}

	public static void showLog() {
		jTextArea1.setVisible(true);
	}

	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JCheckBoxMenuItem jMenuItem5;
	private javax.swing.JCheckBoxMenuItem jMenuItem6;
	private javax.swing.JCheckBoxMenuItem jMenuItem7;
	private JScrollPane textScroll;
	private static LogTextArea jTextArea1;

	public void setupListeners() {
		for (MouseListener listener : RSLoader.getCurrent().getCanvas()
				.getMouseListeners()) {

			if (listener.equals(RSLoader.getCurrentMouse())) {
				RSLoader.getCurrent().getCanvas().removeMouseListener(listener);
			}
		}

		for (MouseMotionListener listener : RSLoader.getCurrent().getCanvas()
				.getMouseMotionListeners()) {
			if (listener.equals(RSLoader.getCurrentMouse())) {
				RSLoader.getCurrent().getCanvas()
						.removeMouseMotionListener(listener);
			}
		}

		for (KeyListener listener : RSLoader.getCurrent().getCanvas()
				.getKeyListeners()) {
			if (listener.equals(RSLoader.getCurrentMouse())) {
				RSLoader.getCurrent().getCanvas().removeKeyListener(listener);
			}
		}
		RSLoader.getCurrent().getCanvas()
				.addMouseListener(RSLoader.getCurrentMouse());
		RSLoader.getCurrent().getCanvas()
				.addMouseMotionListener(RSLoader.getCurrentMouse());
		RSLoader.getCurrent().getCanvas()
				.addKeyListener(RSLoader.getKeyBoard());
	}

	public void applyRenew() {
		while (true) {
			MethodProvider.sleep(100);
			java.awt.Canvas canvas = RSLoader.getCurrent().getCanvas();

			if ((canvas != null) && (canvas.paint == null)) {
				break;
			}
			canvas = null;
		}
		RSLoader.getCurrent().getCanvas().paint = DebugHandler.getCurrent();
		RSLoader.getCurrent().getCanvas().canvasRenew = this;
		setupListeners();
	}

	@Override
	public void onRenew() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				applyRenew();
			}
		});
		t.setName("Runeshark Canvas Renewal Thread");
		t.start();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// visit log out

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
