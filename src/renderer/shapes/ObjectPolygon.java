package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import renderer.point.ObjectPoint;
import renderer.point.PointConverter;

public class ObjectPolygon {

	private ObjectPoint[] points;
	private Color color;

	public ObjectPolygon(Color color, ObjectPoint... points) {
		this.color = color;
		this.points = new ObjectPoint[points.length];
		for (int i = 0; i < points.length; i++) {
			ObjectPoint p = points[i];
			this.points[i] = new ObjectPoint(p.x, p.y, p.z);
		}
	}

	public void render(Graphics g) {
		Polygon poly = new Polygon();
		for (int i = 0; i < this.points.length; i++) {
			Point p = PointConverter.convertPoint(points[i]);
			poly.addPoint(p.x, p.y);
			
		}
		
		g.setColor(this.color);
		g.fillPolygon(poly);
		
	}

	public void setColor(Color color) {
		this.color = color;
		
	}
}
