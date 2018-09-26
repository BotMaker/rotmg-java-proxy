

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Plotter {

	private BufferedImage bi;
	int width = 2000, height = 2000, sx = 0, sy = 0;
	private Point lastPointR = new Point(-10, -10);
	private Point lastPointT = new Point(-10, -10);

	public Plotter() {
		bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.dispose();
	}

	public void plotR(int x, int y) {
		Graphics2D g = (Graphics2D) bi.getGraphics();
//		g.setColor(Color.yellow);
//		g.drawLine(sx + (lastPointR.x), sy + (lastPointR.x), sx + (x), sy + (y));
//		g.setColor(Color.GREEN);
//		g.drawLine(sx + (lastPointT.x * 100), sy + (lastPointT.x * 100), sx + (x * 100), sy + (y * 100));
		g.setColor(Color.RED);
		g.drawRoundRect(sx + (x) - 1, sy + (y) - 1, 3, 3, 3, 3);
		g.dispose();
		lastPointT = new Point(x, y);
		lastPointR = new Point(x, y);
	}

	public void plotT(int x, int y) {
		Graphics2D g = (Graphics2D) bi.getGraphics();
//		g.setColor(Color.blue);
//		g.drawLine(sx + (lastPointT.x), sy + (lastPointT.x), sx + (x), sy + (y));
		g.setColor(Color.CYAN);
		g.drawRoundRect(sx + (x) - 1, sy + (y) - 1, 3, 3, 3, 3);
		g.dispose();
		lastPointT = new Point(x, y);
	}

	public void finish() {
//		try {
//			ImageIO.write(bi, "PNG", new FileOutputStream("time" + System.currentTimeMillis() + ".png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
