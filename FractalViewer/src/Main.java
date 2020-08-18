import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		int width = 960;
		int height = 540;
		
		JFrame mainFrame = new JFrame("Fractal Viewer");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FractalPanel fp = new FractalPanel(width, height,1);
		
		mainFrame.getContentPane().add(fp);
		
		mainFrame.setSize(width, height);
		mainFrame.setLocation(new Point(400,400));
		mainFrame.setVisible(true);
		
		mainFrame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("keytyped"+e);
				if(e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
					fp.moveScreen(10,0);
				} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					fp.moveScreen(-10,0);
				} else if(e.getKeyCode() == KeyEvent.VK_UP) {
					fp.moveScreen(0,10);
				} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					fp.moveScreen(0,-10);
				} else if(e.getKeyCode() == KeyEvent.VK_J) {
					fp.changeZoom(1.1);
				} else if(e.getKeyCode() == KeyEvent.VK_K) {
					fp.changeZoom(0.9);
				} else if(e.getKeyCode() == KeyEvent.VK_P) {
					BufferedImage img = fp.computeImage(1920, 1080, 4, true);
				    File outputfile = new File("./fractal_screenshot.png");
				    try {
						ImageIO.write(img, "png", outputfile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				fp.repaint();
				mainFrame.repaint();
			}
		});
		
	}

}
