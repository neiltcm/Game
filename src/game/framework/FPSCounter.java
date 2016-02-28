package game.framework;

public class FPSCounter {
	
	private static double DEFAULT_FPS = 60.0;
	private static double SECOND_IN_NANOSECONDS = 1000000000.0;
	private static int SECOND_IN_MILLISECONDS = 1000;
	
	private double fps;
	private long timer;
	private double delta = 0;
	private double lastTime;
	private boolean canUpdate = false;
	private int updates = 0;
	
	public FPSCounter(double fps) {
		this.fps = fps;
		timer = System.currentTimeMillis();
		lastTime = System.nanoTime();
	}
	
	public FPSCounter() {
		this(DEFAULT_FPS);
	}
	
	public void tick() {
		long now = System.nanoTime();
		delta += (now - lastTime) / SECOND_IN_NANOSECONDS;
		lastTime = now;
		if (delta >= (1/fps)) {
			canUpdate = true;
			updates++;
			delta -= (1/fps);
		}
		
		if (System.currentTimeMillis() - timer > SECOND_IN_MILLISECONDS) {
			timer += SECOND_IN_MILLISECONDS;
			updates = 0;
		}
	}
	
	public boolean updateAllowed() {
		return canUpdate;
	}
	
	public void refreshStatus() {
		canUpdate = false;
	}
	
	public int getUpdates() {
		return updates;
	}
}
