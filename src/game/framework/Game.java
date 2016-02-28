package game.framework;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public abstract class Game extends Canvas {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private Thread thread;
	private FPSCounter counter;
	private boolean running;
	
	private int width;
	private int height;
	private String title;

	public Game(String title, int width, int height, Double fps) {
		this.title = title;
		this.width = width;
		this.height = height;
		if (fps == null) {
			counter = new FPSCounter();
		} else {
			counter = new FPSCounter(fps);
		}
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
        		render();
        		
        		counter.refreshStatus();
        	}
        }
    }
	
	private void render() {
		// creating buffer strategy
		BufferStrategy buffer = getBufferStrategy();
		if (buffer == null) {
			createBufferStrategy(3);
			return;
		}
		
		// creating rendering objects
		Graphics graphics = buffer.getDrawGraphics();
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.dispose();
		buffer.show();
	}
	
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
