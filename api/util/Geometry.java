package api.util;
import java.awt.Point;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tim
 */
public class Geometry{

    public boolean isSquare(Point square[]) {
        Point past = square[0];
        if (square.length != 4) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            if (past.distance(square[i]) == 0) {
                past = square[i];
            } else {
                return false;
            }

        }
        return true;
    }

    public boolean isTriangle(Point triangle[]) {
        return !(triangle.length != 3 || isLine(triangle));
    }

    public boolean isLine(Point line[]) {
        if (line.length == 1) {
            return false;
        } else if (line.length == 2) {
            return true;
        } else {
            Point slope = new Point(line[1].x - line[0].x, line[1].y - line[0].y);
            for (Point aLine : line) {
                if (aLine.x % slope.x == 0 && aLine.y % slope.y == 0) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
