package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Tetrahedron {
	private ObjectPolygon[] polygons;
	private Color color;
	
	public Tetrahedron(Color color, ObjectPolygon... polygons) {
		this.color = color;
		this.polygons = polygons; //be careful on pointer here
		
		
	}
	
	public void render(Graphics g) {
		for(ObjectPolygon poly : this.polygons) {
			poly.render(g);
		}
	}
	
	private void sortPolygons() {
		//TODO
	}
	
	private void setPolygonColor() {
		for(ObjectPolygon poly : this.polygons) {
			poly.setColor(this.color);
		}
	}
}
