package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import bot.InputManager;

import api.methods.Game;

import loader.RSLoader;

public class Mouse implements MouseListener, MouseWheelListener,
		MouseMotionListener {
	public static int X = 0;
	public static int Y = 0;
	public static boolean isClicking = false;
    public static boolean isPresent = false;
	public static int LastClickX = 0;
	public static int LastClickY = 0;
    public static long LastClickTime;

	static Mouse m;

	static boolean Pressed = false;

	public Mouse() {
		m = this;
	}

	public static Mouse getMouse() {
		return m;
	}

	public static void HopMouse(int x, int y) {
		X = x;
		Y = y;
		boolean c = false;
		if(!InputManager.getCurrent().getInput()){
			InputManager.getCurrent().setInput(true);
			c=true;
		}
		RSLoader.getCurrent()
				.getCanvas()
				.dispatchEvent(
						new MouseEvent(RSLoader.getCurrent().getCanvas(),
								MouseEvent.MOUSE_MOVED, System
										.currentTimeMillis(), 0, x, y, 0, false));
		if(c)
		InputManager.getCurrent().setInput(false);
	}

	/**
	 * 
	 * @param x
	 *            Mouse X
	 * @param y
	 *            Mouse Y
	 * @param left
	 *            if <tt>true</tt> will click left, if <tt>false</tt> will click
	 *            right.
	 */
	public static void click(int x, int y, boolean left) {
		try {
			api.methods.Mouse.move(x, y);
			System.out.println(Mouse.getLocation());
			pressMouse(x, y, left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
			releaseMouse(x, y, left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void pressMouse(int x, int y, int button) {
		X = x;
		Y = y;
		boolean c = false;
		if(!InputManager.getCurrent().getInput()){
			InputManager.getCurrent().setInput(true);
			c=true;
		}
		RSLoader.getCurrent()
				.getCanvas()
				.dispatchEvent(
						new MouseEvent(RSLoader.getCurrent().getCanvas(),
								MouseEvent.MOUSE_PRESSED, System
										.currentTimeMillis(), 0, x, y, 1,
								false, button));
		if(c)
			InputManager.getCurrent().setInput(false);
	}

	public static void releaseMouse(int x, int y, int button) {
		X = x;
		Y = y;
		boolean c = false;
		if(!InputManager.getCurrent().getInput()){
			InputManager.getCurrent().setInput(true);
			c=true;
		}
		RSLoader.getCurrent()
				.getCanvas()
				.dispatchEvent(
						new MouseEvent(RSLoader.getCurrent().getCanvas(),
								MouseEvent.MOUSE_RELEASED, System
										.currentTimeMillis(), 0, x, y, 1,
								false, button));
		if(c)
			InputManager.getCurrent().setInput(false);

	}

	/**
	 * Gets the current mouse Location
	 * 
	 * @return Mouse Location as Point
	 */

	public static Point getLocation() {
		return new Point(X, Y);
	}

	public static Point getAppletLocation() {
		Point p = RSLoader.getCurrent().getCanvas().getMousePosition();
		if (p != null && !p.equals(new Point(-1, -1))) {
			return RSLoader.getCurrent().getCanvas().getMousePosition();
		}
		return new Point(0, 0);
	}

	public static boolean isPressed() {
		return Pressed;
	}

    public static boolean isPresent(){
        return isPresent;
    }
    
	@Override
	public void mouseClicked(MouseEvent arg0) {
        LastClickX = arg0.getX();
        LastClickY = arg0.getY();
		if (!isClicking)
			refreshMouse(arg0);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
        isPresent = true;
		refreshMouse(arg0);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
        isPresent = false;
		refreshMouse(arg0);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Pressed = true;
		if (!isClicking)
			refreshMouse(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Pressed = false;
		if (!isClicking)
			refreshMouse(arg0);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		refreshMouse(arg0);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		refreshMouse(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		refreshMouse(arg0);
	}

	public void refreshMouse(MouseEvent arg0) {
		if (Game.isPointValid(arg0.getX(), arg0.getY())) {
			if (bot.InputManager.getCurrent().input) {
				X = arg0.getX();
				Y = arg0.getY();
				if (arg0.getClickCount() > 0) {
                    LastClickX = X;
                    LastClickY = Y;
                    LastClickTime = arg0.getWhen();
					isClicking = true;
				} else {
					isClicking = false;
				}
			}

		}
	}
}