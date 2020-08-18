import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class FractalPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7368543300117893789L;

	double center_x = 0.0,
			center_y = 0.0,
			zoom = 200.0;
	
	int width,height,antialias;
	
	public FractalPanel(int w, int h, int aliasforce) {
		width = w;
		height = h;
		antialias = aliasforce;
	}
	
	@Override
	public void paint(Graphics g) {
		
		System.out.println("repaint");
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		
		BufferedImage bf = computeImage(width, height, antialias, false);
		
		g.drawImage(bf, 0, 0, null);
		
	}
	
	public BufferedImage computeImage(int w, int h, int antialiasforce, boolean screenshot) {
		
		BufferedImage bf = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		if(screenshot) changeZoom(2.0);
		for (int x=0; x<w;x++) {
			for (int y=0; y<h;y++) {
				double p_x = (x - center_x - w/2)/zoom;
				double p_y = (y - center_y - h/2)/zoom;
				Color c = getColorAntialias(antialiasforce,p_x,p_y);
				bf.setRGB(x, y, c.getRGB());
			}
		}
		if(screenshot) changeZoom(0.5);
		return bf;
		
	}
	
	public Color getColorAntialias(int force, double x, double y) {
		int new_r=0;
		int new_g=0;
		int new_b=0;
		for(int i = 0; i < force; i++) {
			for(int j = 0; j < force; j++) {
				Color c_temp = getColor(x+i/(zoom*force),y+j/(zoom*force));
				new_r += c_temp.getRed();
				new_g += c_temp.getGreen();
				new_b += c_temp.getBlue();
			}
		}
		int forcesq = (int) (force*force);
		Color good_color = new Color(new_r/(forcesq),new_g/(forcesq),new_b/(forcesq));
		return good_color;
		
	}
	
	public Color getColor(double x, double y) {
		
		double z = 0;
		double z_i = 0;
		int i=0;
		int scale = 20;
		for(i=0; i < 100 ; i++) {
			double z_tmp = z;
			z = z*z - z_i*z_i + x;
			z_i = 2*z_tmp*z_i + y;
			if(z*z+z_i*z_i > scale)
				return new Color(cap((int)z*scale/5,0,255),cap((int)z_i*scale/5,0,255),255-cap((int)i*scale/5,0,255));
		}
		return new Color(cap((int)(Math.sqrt(x*x)*255),0,255),cap((int)(Math.sqrt(y*y)*255),0,255),cap((int)(Math.sqrt(x*y)*255),0,255));
	}
	
	public static int cap(int i, int min, int max) {
		if(i<min) return min;
		if(i>max) return max;
		return i;
	}

	public void moveScreen(int x, int y) {
		this.center_x += x;
		this.center_y += y;
		System.out.println("movin");
	}

	public void changeZoom(double d) {
		this.zoom *= d;
		this.center_x *= d;
		this.center_y *= d;
		System.out.println("mofflin");
	}
	
}
