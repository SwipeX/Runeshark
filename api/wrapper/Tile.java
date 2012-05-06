package api.wrapper;

public class Tile {

	private int x;
	private int y;

	Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Tile) {
			final Tile tile = (Tile) obj;
			return tile.x == x && tile.y == y;
		}
		return false;
	}

	/**
	 * Randomizes this tile.
	 * 
	 * @param maxXDeviation
	 *            Max X distance from tile x.
	 * @param maxYDeviation
	 *            Max Y distance from tile y.
	 * @return The randomized tile
	 */
	public Tile randomize(final int maxXDeviation, final int maxYDeviation) {
		int x = getX();
		int y = getY();
		if (maxXDeviation > 0) {
			double d = Math.random() * 2 - 1.0;
			d *= maxXDeviation;
			x += (int) d;
		}
		if (maxYDeviation > 0) {
			double d = Math.random() * 2 - 1.0;
			d *= maxYDeviation;
			y += (int) d;
		}
		return new Tile(x, y);
	}

	@Override
	public int hashCode() {
		return x * 31 + y;
	}

	@Override
	public String toString() {
		return "(X: " + x + ", Y:" + y + ")";
	}
}
