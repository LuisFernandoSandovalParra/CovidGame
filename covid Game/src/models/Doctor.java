package models;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.ConcurrentHashMap;

public class Doctor extends Point {

	private static final int NUM_PIXELS_MOVING_DOCTOR = 6;
	private static final int MIDLE_QUANTITY_POTIONS = 4;
	private static final int INITIAL_QUANTITY_POTIONS = 10;
	private static final int NUM_MAX_POTIONS = 7;
	private static final int HEIGHT_DOCTOR = 110;
	private static final int WIDTH_DOCTOR = 75;
	private static final int FIRST_POSITION = 1;
	private static final int INITIAL_HEALTH_DOCTOR = 4;
	private static final int GENERAL_INITIAL_VALUE = 0;
	private static final int INITIAL_Y_POSITION_DOCTOR = 230;
	private static final int INITIAL_X_POSITION_DOCTOR = 180;
	private static final int POINTS_FOR_KILLS = 100;
	private static final int POINTS_FOR_LOSE_POTION = 50;
	private static final int HEIGHT_RELOAD_ZONE = 200;
	private static final int WIDTH_RELOAD_ZONE = 105;
	private static final int Y_RELOAD_ZONE = 150;
	private static final int X_RELOAD_ZONE = 360;
	private static final long serialVersionUID = 1L;

	private int health;
	private int points;
	private int validMove;
	private String imageDoctor;
	private Potion actualPotion;
	private ConcurrentHashMap<Integer, Potion> potionsList;
	private ConcurrentHashMap<Integer, Potion> potionsRemovedList;
	private boolean isReloadable;
	private int numPotionsReloaded;
	private boolean isDieStats;

	public Doctor() {
		initGameDefaultValues();
		imageDoctor = ConstantsModels.IMG_DOC_STOP_FRONT;
		potionsList = new ConcurrentHashMap<>();
		potionsRemovedList = new ConcurrentHashMap<>();
		addInitialPotions();
		actualPotion = potionsList.get(FIRST_POSITION);
	}

	private void initGameDefaultValues() {
		x = INITIAL_X_POSITION_DOCTOR;
		y = INITIAL_Y_POSITION_DOCTOR;
		validMove = GENERAL_INITIAL_VALUE;
		health = INITIAL_HEALTH_DOCTOR;
		points = GENERAL_INITIAL_VALUE;
		numPotionsReloaded = GENERAL_INITIAL_VALUE;
		isReloadable = false;
		isDieStats = false;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getPoints() {
		return points;
	}

	public void setWinPoints() {
		this.points += POINTS_FOR_KILLS;
	}

	public void setLosePoints() {
		this.points -= POINTS_FOR_LOSE_POTION;
	}

	public boolean isDie() {
		if (health == 0) {
			return isDieStats = true;
		} else {
			return isDieStats = false;
		}
	}

	public void getDirectionDoctor(DoctorMovingEnum directionEnum) {
		switch (directionEnum) {
		case LEFT:
			moveDoctorLeft();
			break;
		case RIGHT:
			moveDoctorRight();
			break;
		}
	}

	private void moveDoctorLeft() {
		if (x - 5 < -1) {
			x = 650;
		}
		x -= NUM_PIXELS_MOVING_DOCTOR;
		validateMoveDoctor(ConstantsModels.IMG_DOC_STOP_LEFT, ConstantsModels.IMG_DOC_RUN_TO_LEFT);
	}

	private void moveDoctorRight() {
		if (x + 5 > 650) {
			x = -5;
		}
		x += NUM_PIXELS_MOVING_DOCTOR;
		validateMoveDoctor(ConstantsModels.IMG_DOC_STOP_RIGHT, ConstantsModels.IMG_DOC_RUN_TO_RIGHT);
	}

	private void validateMoveDoctor(String imageDocStop, String imageDocRun) {
		if (validMove == GENERAL_INITIAL_VALUE) {
			changeValidMoveDoctor(1, imageDocStop);
		} else {
			changeValidMoveDoctor(0, imageDocRun);
		}
	}

	private void changeValidMoveDoctor(int numValid, String pathImage) {
		imageDoctor = pathImage;
		validMove = numValid;
	}

	public String getImageDoctor() {
		return imageDoctor;
	}

	public void setImageDoctor(String path) {
		this.imageDoctor = path;
	}

	public void addInitialPotions() {
		for (int i = 0; i < INITIAL_QUANTITY_POTIONS; i++) {
			if (i <= MIDLE_QUANTITY_POTIONS) {
				addInitialPotion(i, PotionsEnum.YELLOW_POTION);
			} else {
				addInitialPotion(i, PotionsEnum.GREEN_POTION);
			}
		}
	}

	private void addInitialPotion(int i, PotionsEnum potionsEnum) {
		Potion potionTwo = new Potion(i + 1, x, potionsEnum);
		potionsList.put(i + 1, potionTwo);
	}

	private int generateRandomNumPotions() {
		return (int) (Math.random() * NUM_MAX_POTIONS) + 1;
	}

	public void addReloadPotions() {
		numPotionsReloaded = generateRandomNumPotions();
		for (int i = 0; i < numPotionsReloaded; i++) {
			potionsList.put(potionsList.size() + 1, new Potion(potionsList.size() + 1, x, PotionsEnum.YELLOW_POTION));
			potionsList.put(potionsList.size() + 1, new Potion(potionsList.size() + 1, x, PotionsEnum.GREEN_POTION));
		}
	}

	public int getNumPotionsReloaded() {
		return numPotionsReloaded;
	}

	public int getNumPotions(String nameEnum) {
		int num = 0;
		for (Integer id : potionsList.keySet()) {
			if (potionsList.get(id).getPotionEnumName().equals(nameEnum)) {
				num += 1;
			}
		}
		return num;
	}

	public Potion getActualPotion() {
		return actualPotion;
	}

	public void getPotions(PotionsEnum potionsEnum) {
		for (Integer id : potionsList.keySet()) {
			if (potionsList.get(id).getPotionEnumName().equals(potionsEnum.name())) {
				potionsList.get(id).setX(x);
				actualPotion = potionsList.get(id);
				break;
			}
		}
	}

	public void removePotion() throws Exception {
		if (actualPotion != null) {
			potionsRemovedList.put(actualPotion.getId(), potionsList.get(actualPotion.getId()));
			potionsList.remove(actualPotion.getId());
			updateActualPotion();
		}
	}

	public void verifyIsReloadable() {
		if (getNumPotions(PotionsEnum.YELLOW_POTION.name()) == GENERAL_INITIAL_VALUE
				|| getNumPotions(PotionsEnum.GREEN_POTION.name()) == GENERAL_INITIAL_VALUE) {
			reloadPotions();
		}
	}

	public void reloadPotions() {
		Rectangle reloadZone = new Rectangle(X_RELOAD_ZONE, Y_RELOAD_ZONE, WIDTH_RELOAD_ZONE, HEIGHT_RELOAD_ZONE);
		Rectangle doctorZone = new Rectangle(x, y, WIDTH_DOCTOR, HEIGHT_DOCTOR);
		if (reloadZone.intersects(doctorZone)) {
			isReloadable = true;
		} else {
			isReloadable = false;
		}
	}

	public boolean getIsReloadable() {
		return isReloadable;
	}

	public void setIsReloadable(boolean isReloadable) {
		this.isReloadable = false;
	}

	private void updateActualPotion() throws Exception {
		Potion potionAux = new Potion();
		if (potionsList.size() > 0) {
			for (Integer id : potionsList.keySet()) {
				validateUpdateActualPotion(potionAux, id);
			}
		}
	}

	private void validateUpdateActualPotion(Potion potionAux, Integer id) {
		if (actualPotion.getPotionEnumName().equals(potionsList.get(id).getPotionEnumName())) {
			changeActualPotion(id, potionAux);
			return;
		} else {
			changeActualPotion(id, potionAux);
		}
	}

	private void changeActualPotion(int id, Potion potionAux) {
		potionAux = potionsList.get(id);
		potionAux.setX(x);
		actualPotion = potionAux;
	}

	public String getImagePotion() {
		if (actualPotion != null) {
			return actualPotion.getPotionImage();
		}
		return null;
	}

	public ConcurrentHashMap<Integer, Potion> getPotionsList() {
		return potionsList;
	}

	public ConcurrentHashMap<Integer, Potion> getPotionsRemovedList() {
		return potionsRemovedList;
	}

	public boolean getIsDieStats() {
		return isDieStats;
	}

	public void setIsDieStats(boolean isDie) {
		this.isDieStats = false;
	}
}