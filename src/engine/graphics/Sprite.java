package engine.graphics;

public class Sprite {
	int widthInPixel;
	int heightInPixel;
	int[] pixels;
	
	public Sprite(int width, int height) {
		this.widthInPixel = width;
		this.heightInPixel = height;
	}
	
	public int getWidthInPixels() {
		return widthInPixel;
	}
	
	public int getHeightInPixels() {
		return heightInPixel;
	}
	
	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	
	public int[] getPixels() {
		return pixels;
	}
}
