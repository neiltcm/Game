package game.test;

import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;
import game.framework.Game;

public class Test extends Game {
	private static final long serialVersionUID = 1L;

	SpriteSheet sheet;
	Sprite sprite;

	public Test() {
		super();
	}

	public Test(String title, int width, int height) {
		super(title, width, height, null);
		sheet = new SpriteSheet("/spritesheet.png", 32, 512, 512);
		sprite = sheet.getSprite(0, 0, 32, 32);
	}

	@Override
	public void render() {		
		screen.renderSprite(0, 0, sprite);
	}

	public static void main(String[] args) {
		System.out.println("Starting game..");
		Test test = new Test("Game title", 800, 600);
		test.run();
	}
}
