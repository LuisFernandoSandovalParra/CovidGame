package models;

import java.awt.Rectangle;

public class Potion extends Rectangle {

	private static final int NUM_PIXELS_TO_FALL = 10;
	private static final int HEIGHT_LIMIT = 850;
	private static final int Y_POSITION_INITIAL_POTION = 290;
	private static final int HEIGHT_RECTANGLE_POTION = 40;
	private static final int WIDTH_RECTANGLE_POTION = 30;
	private static final long serialVersionUID = 1L;
	private PotionsEnum potionsEnum;
	private int id;

	public Potion(int id, int xDoctor, PotionsEnum potionsEnum) {
		setSize(WIDTH_RECTANGLE_POTION, HEIGHT_RECTANGLE_POTION);
		this.id = id;
		this.potionsEnum = potionsEnum;
		initialPotionPosition(xDoctor);
	}

	private void initialPotionPosition(int xDoctor) {
		x = xDoctor;
		y = Y_POSITION_INITIAL_POTION;
	}

	public Potion() {

	}

	public void moveDown() {
		if (y + 1 < HEIGHT_LIMIT) {
			y += NUM_PIXELS_TO_FALL;
		}
	}

	public int getId() {
		return id;
	}

	public String getPotionImage() {
		return potionsEnum.getPathImg();
	}

	public String getPotionEnumName() {
		return potionsEnum.name();
	}

	public void setPotionsEnum(PotionsEnum potionsEnum) {
		this.potionsEnum = potionsEnum;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public PotionsEnum getPotionsEnum() {
		return potionsEnum;
	}
}