package models;

import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends Rectangle {

	private static final int NUM_PIXELS_MOVING_ENEMY = 1;
	private static final int POSITION_BEGIN_APPEAR_ENEMIES = 750;
	private static final int WIDTH_LIMIT = 650;
	private static final long serialVersionUID = 1L;
	private static final int size = 50;
	private Random createRandom;
	private PotionsEnum potionsType;

	public Enemy(PotionsEnum potionsType) {
		this.potionsType = potionsType;
		setSize(size, size);
		initRandomEnemiesPosition();
	}

	private void initRandomEnemiesPosition() {
		createRandom = new Random();
		this.x = createRandom.nextInt(0 + (WIDTH_LIMIT - size));
		this.y = createRandom.nextInt(POSITION_BEGIN_APPEAR_ENEMIES) + POSITION_BEGIN_APPEAR_ENEMIES;
	}

	public void moveUp() {
		if (y - 1 > 0) {
			y -= NUM_PIXELS_MOVING_ENEMY;
		}
	}

	public void setPointX() {
		this.x = createRandom.nextInt(0 + (WIDTH_LIMIT - size));
	}

	public void setPointY() {
		this.y = createRandom.nextInt(POSITION_BEGIN_APPEAR_ENEMIES) + POSITION_BEGIN_APPEAR_ENEMIES;
	}

	public PotionsEnum getPotionsType() {
		return potionsType;
	}
}