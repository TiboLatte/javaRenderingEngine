package renderer.point;

import java.awt.Point;

import renderer.Display;

public class PointConverter {

	private static double scale = 1;

	public static Point convertPoint(ObjectPoint point3D) {
		double x3d = point3D.y * scale;
		double y3d = point3D.z * scale;
		double depth = point3D.x * scale;
		double[] newVal = scale(x3d, y3d, depth);
		int x2d = (int) (Display.WIDTH / 2 + newVal[0]);
		int y2d = (int) (Display.HEIGHT / 2 - newVal[1]);
		Point point2D = new Point(x2d, y2d);
		return point2D;
	}

	private static double[] scale(double x3d, double y3d, double depth) {
		double dist = Math.sqrt(x3d * x3d + y3d * y3d);
		double theta = Math.atan2(y3d, x3d);
		double relativeDepth = 15 - depth;
		double localScale = Math.abs(1400 / (relativeDepth + 1400));
		dist *= localScale;
		double[] newVal = new double[2];
		newVal[0] = dist * Math.cos(theta); //x component
		newVal[1] = dist * Math.sin(theta); //y component
		return newVal;

	}
}
