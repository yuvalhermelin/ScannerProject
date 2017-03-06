import java.awt.Point;

public class Problem {
    private Point topRight, bottomRight, topLeft, bottomLeft;

    public Problem(int topRightX, int topRightY, int bottomRightX, int bottomRightY, int topLeftX, int topLeftY,
	    int bottomLeftX, int bottomLeftY) {
	topRight.setLocation(topRightX, topRightY);
	bottomRight.setLocation(bottomRightX, bottomRightY);
	topLeft.setLocation(topLeftX, topLeftY);
	bottomLeft.setLocation(bottomLeftX, bottomLeftY);
    }

    public Point getTopRight() {
	return topRight;
    }

    public Point getBottomRight() {
	return bottomRight;
    }

    public Point getTopLeft() {
	return topLeft;
    }

    public Point getBottomLeft() {
	return bottomLeft;
    }
}
