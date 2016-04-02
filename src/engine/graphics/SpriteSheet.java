package engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	String path;
	int tileSize;
	int width;
	int height;
	int[] pixels;
	
	public SpriteSheet(String path, int tileSize, int spriteSheetWidthInPixels, int spriteSheetHeightInPixels) {
		this.path = path;
		this.tileSize = tileSize;
		this.width = spriteSheetWidthInPixels;
		this.height = spriteSheetHeightInPixels;
		this.pixels = new int[spriteSheetWidthInPixels * spriteSheetHeightInPixels];
		load();
		System.out.println("spritesheet loaded");
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			System.out.println("w:" + w + " | h:" + h);
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) { 
			System.out.println("ERROR: Could not load spritesheet [" + e + "]");
		}
	}
	
	public Sprite getSprite(int xPosition, int yPosition, int widthInPixel, int heightInPixel) {
		System.out.println("getting sprite");
		Sprite sprite = new Sprite(widthInPixel, heightInPixel);
		int[] pixels = new int[widthInPixel * heightInPixel];
		
		for (int y = 0; y < heightInPixel; y++) {
			int yy = (yPosition * tileSize) + y;
			for (int x = 0; x < widthInPixel; x++) {
				int xx = (xPosition * tileSize) + x;
				pixels[x + (y * widthInPixel)] = this.pixels[xx + (yy * width)];
			}
		}
		
		sprite.setPixels(pixels);
		
		return sprite;
	}
}
