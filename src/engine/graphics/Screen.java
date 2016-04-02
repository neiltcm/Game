package engine.graphics;

public class Screen {
	private int width;
	private int height;
	private int[] pixels;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderSprite(int xPixelPosition, int yPixelPosition, Sprite sprite) {
		int[] p = sprite.getPixels();
		for (int y = 0; y < sprite.getHeightInPixels(); y++) {
			int yy = yPixelPosition + y;
			if (yy >= 0 && yy <= height)
			for (int x = 0; x < sprite.getWidthInPixels(); x++) {
				int xx = xPixelPosition + x;
				if (xx >= 0 && xx <= width) {
					pixels[xx + (yy * width)] = p[x +(y * sprite.getWidthInPixels())];
				}
			}
		}
	}
	
	public int[] getPixels() {
		return pixels;
	}
}
