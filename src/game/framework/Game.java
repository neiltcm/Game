package game.framework;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;

public abstract class Game extends Canvas {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private Thread thread;
	private FPSCounter counter;
	protected Screen screen;
	private boolean running;
	
	private int width;
	private int height;
	private String title;
	private BufferStrategy buffer;
	
	private BufferedImage image;
	private int[] pixels;

	public Game(String title, int width, int height, Double fps) {
		this.title = title;
		this.width = width;
		this.height = height;
		if (fps == null) {
			counter = new FPSCounter();
		} else {
			counter = new FPSCounter(fps);
		}
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		this.screen = new Screen(width, height);
	}
	
	public Game() {
		this("Game", 640, 480, null);
	}
	
	private void createWindow() {
		frame = new JFrame();
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		
		// setting frame settings
		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}
	
	private void init() {
		running = true;
		thread = new Thread("Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public final void loop()
    {
        while(running) {
        	counter.tick();
        	if (counter.updateAllowed()) {
        		renderWrapper();
        		
        		counter.refreshStatus();
        	}
        }
    }
	
	private void renderWrapper() {
		// creating buffer strategy
		buffer = getBufferStrategy();
		if (buffer == null) {
			createBufferStrategy(3);
			return;
		}
		
		// creating rendering objects
		Graphics graphics = buffer.getDrawGraphics();
		
		// implement graphics behavior by manipulating pixels array
		render();
		
		int[] screenPixels = screen.getPixels();
		for (int i = 0; i < screenPixels.length; i++) {
			pixels[i] = screenPixels[i];	
		}
		
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		graphics.dispose();
		buffer.show();
	}
	
	public abstract void render();
	
	public final void run() {
		try {
			// creates window
			createWindow();
			
			// initialization
			init();
			
			// run the loop
			loop();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.exit(0);
		}
	}
}
