package renderer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.point.ObjectPoint;
import renderer.shapes.ObjectPolygon;
import renderer.shapes.Tetrahedron;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private JFrame frame;
	private static String title = "Rendering Engine";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private static boolean isRunning = false;

	private Tetrahedron tetra;

	public Display() { // constructor
		this.frame = new JFrame();

		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
	}

	public synchronized void start() {
		isRunning = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60; // update it 60 times per seconds, nanoseconds between each update.
		double delta = 0;
		int frames = 0;

		init();

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns; // add however close we are to the next update,
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) { // every time a second passes we display the number of fps
				timer += 1000;
				this.frame.setTitle(title + " | " + frames + " fps");
				frames = 0;
			}

		}
		stop();

	}

	private void init() {
		int s = 100;
		ObjectPoint p1 = new ObjectPoint(s/2,-s/2,-s/2);
		ObjectPoint p2 = new ObjectPoint(s/2,s/2,-s/2);
		ObjectPoint p3 = new ObjectPoint(s/2,s/2,s/2);
		ObjectPoint p4 = new ObjectPoint(s/2,-s/2,s/2);
		ObjectPoint p5 = new ObjectPoint(-s/2,-s/2,-s/2);
		ObjectPoint p6 = new ObjectPoint(-s/2,s/2,-s/2);
		ObjectPoint p7 = new ObjectPoint(-s/2,s/2,s/2);
		ObjectPoint p8 = new ObjectPoint(-s/2,-s/2,s/2);
		
		
		
		this.tetra = new Tetrahedron(
				new ObjectPolygon(Color.BLUE, p1,p2,p3,p4),
				new ObjectPolygon(Color.WHITE, p5,p6,p7,p8),
				new ObjectPolygon(Color.RED, p1,p2,p6,p5),
				new ObjectPolygon(Color.YELLOW, p1,p5,p8,p4),
				new ObjectPolygon(Color.GREEN, p2,p6,p7,p3),
				new ObjectPolygon(Color.CYAN, p7,p3,p7,p8)
				);
	}

	private void render() {
		/*
		 * Render everything on the screen
		 */
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;

		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		tetra.render(g);
		g.dispose();
		bs.show();
	}

	private void update() {

	}

	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display); // adding the display to the frame
		display.frame.setLayout(new BorderLayout());
		display.frame.add(display, BorderLayout.CENTER);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setLocationRelativeTo(null);
		display.frame.setResizable(false);
		display.frame.setVisible(true);

		display.start();

	}

}
