package api.wrapper;

import java.awt.Point;

import api.methods.Location;

/**
 * 
 * @author Tim
 * 
 */
public class RSArea {
	Tile min;
	Tile max;

	public RSArea(Tile a, Tile b) {
		min = a;
		max = b;
	}

	public boolean Contains() {
		Point p = Location.getMiniMapLocOnFull();
		return min.getX() < p.x && min.getY() < p.y && p.x < max.getX()
				&& p.y < max.getY();
	}

	public int distFromSide() {
		Point p = Location.getMiniMapLocOnFull();
		if (Contains()) {
			if (p.x - min.getX() > max.getX() - p.x) {
				return p.x - min.getX();
			} else {
				return max.getX() - p.x;
			}
		}
		return -1;
	}
}
