package game.test;

import game.framework.Game;

public class Test extends Game {
	private static final long serialVersionUID = 1L;
	
	public Test() {
		super();
	}

	public static void main(String[] args) {
		System.out.println("Starting game..");
		Test test = new Test();
		test.run();
	}
}
