package models;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.ConcurrentHashMap;

public class Game implements Runnable {

	private static final int TIME_TO_SLEEP_GAME_THREAD = 33;
	private static final int HEIGHT_LIMIT_DOWN_WINDOW = 50;
	private static final int Y_LIMIT_DOWN_WINDOW = 810;
	private static final int HEIGHT_LAB_LIMIT = 350;
	private static final int WIDTH_LIMIT = 650;
	private static final int X_Y_POSITION_LAB_LIMIT = 0;
	private Doctor doctor;
	private ConcurrentHashMap<Integer, Enemy> enemyList;
	private Point collitionPoint;
	private boolean isExecuteGame;
	private boolean isShoot;
	private int numEnemies;
	private int xEnemyKill;
	private int yEnemyKill;
	private boolean isScreenShot;
	private boolean isSavingGame;
	private boolean isPaused;
	private boolean isWin;
	private boolean isMute;
	private boolean confirmCollitionEnemyAndPotion;

	public Game() {
		initGameConditions();
		doctor = new Doctor();
		enemyList = new ConcurrentHashMap<>();
		initThreadGame();
	}

	private void initThreadGame() {
		Thread threadGame = new Thread(this);
		threadGame.start();
	}

	private void initGameConditions() {
		initEnemyConditions();
		isPaused = false;
		isExecuteGame = true;
		isShoot = false;
		isScreenShot = false;
		isSavingGame = false;
		isWin = false;
		setIsMute(false);
	}

	private void initEnemyConditions() {
		numEnemies = 20;
		xEnemyKill = 50;
		yEnemyKill = 50;
	}

	public synchronized void pause() {
		isPaused = true;
		notify();
	}

	public synchronized void resume() {
		isPaused = false;
		notify();
	}

	public void pauseOrResumeGame() {
		if (!isPaused) {
			isPaused = true;
			this.pause();
		} else {
			isPaused = false;
			this.resume();
		}
	}

	@Override
	public void run() {
		while (!isExecuteGame || !isWin) {
			if (isExecuteGame) {
				actionOfThreadGame();
				synchronized (this) {
					while (isPaused) {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			try {
				Thread.sleep(TIME_TO_SLEEP_GAME_THREAD);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (validateWithOutPotions()) {
				winGame();
				return;
			}
			if (validateDoctorDie()) {
				doctor.setIsDieStats(false);
				return;
			}
		}
	}

	private boolean validateWithOutPotions() {
		return getNumEnemies(PotionsEnum.GREEN_POTION) == 0 && getNumEnemies(PotionsEnum.YELLOW_POTION) == 0;
	}

	private boolean winGame() {
		return isWin = true;
	}

	private boolean validateDoctorDie() {
		return doctor.getIsDieStats() == true;
	}

	private void actionOfThreadGame() {
		verifyIsReloadable();
		addEnemiesToThread();
		moveEnemies();
		movePotionsRemoved();
		validateAllCollitions();
		confirmShoot();
	}

	private void verifyIsReloadable() {
		doctor.verifyIsReloadable();
	}

	private void validateAllCollitions() {
		validateCollitionEnemyAndPotion();
		validateCollitionEnemyAndLimit();
		validateCollitionPotionAndLimit();
	}

	private void confirmShoot() {
		if (isShoot == true) {
			removePotion();
			isShoot = false;
		}
	}

	public void setIsPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean getIsPaused() {
		return isPaused;
	}

	public void addPotions() {
		doctor.addInitialPotions();
	}

	private void removePotion() {
		try {
			doctor.removePotion();
		} catch (Exception e) {
		}
	}

	private void addEnemies() {
		for (int i = 0; i < numEnemies; i++) {
			if (i <= 9) {
				addEnemyToList(i, PotionsEnum.YELLOW_POTION);
			} else {
				addEnemyToList(i, PotionsEnum.GREEN_POTION);
			}
		}
	}

	private void addEnemyToList(int i, PotionsEnum potionsEnum) {
		Enemy e = new Enemy(potionsEnum);
		if (enemyList.size() > 0) {
			validateColitionEnemies(e);
		}
		enemyList.put(i, e);
	}

	public int getNumEnemies(PotionsEnum potionsEnum) {
		int numEnemies = 0;
		for (Integer id : enemyList.keySet()) {
			if (enemyList.get(id).getPotionsType().equals(potionsEnum)) {
				numEnemies++;
			}
		}
		return numEnemies;
	}

	private void addEnemiesToThread() {
		if (enemyList.size() == 0) {
			addEnemies();
		}
	}

	private void moveEnemies() {
		for (Integer id : enemyList.keySet()) {
			enemyList.get(id).moveUp();
		}
	}

	private void movePotionsRemoved() {
		for (Integer id : doctor.getPotionsRemovedList().keySet()) {
			doctor.getPotionsRemovedList().get(id).moveDown();
		}
	}

	private void validateColitionEnemies(Enemy enemy) {
		for (Integer id : enemyList.keySet()) {
			while (enemyList.get(id).intersects(enemy)) {
				enemy.setPointX();
				validateColitionEnemies(enemy);
			}
		}
	}

	private void validateCollitionEnemyAndPotion() {
		confirmCollitionEnemyAndPotion = false;
		for (Integer idEnemy : enemyList.keySet()) {
			for (Integer idPotion : getPotionsRemovedList().keySet()) {
				if (enemyList.get(idEnemy).intersects(getPotionsRemovedList().get(idPotion))) {
					if (enemyList.get(idEnemy).getPotionsType()
							.equals(getPotionsRemovedList().get(idPotion).getPotionsEnum())) {
						confirmCollitionEnemyAndPotion = true;
						confirmCollitionEnemyPotion(idPotion, idEnemy);
						return;
					} else {
						deletePotionWithId(idPotion);
						return;
					}
				}
			}
		}
	}

	public boolean getConfirmCollitionEnemyAndPotion() {
		return confirmCollitionEnemyAndPotion;
	}

	public Point getCollitionPoint() {
		return collitionPoint;
	}

	public void setCollitionPoint(Point collition) {
		this.collitionPoint = collition;
	}

	private void confirmCollitionEnemyPotion(int idPotion, int idEnemy) {
		getLastEnemyPosition(idEnemy);
		deletePotionWithId(idPotion);
		deleteEnemyWithId(idEnemy);
		numEnemies--;
		doctor.setWinPoints();
	}

	private void getLastEnemyPosition(int idEnemy) {
		xEnemyKill = enemyList.get(idEnemy).x;
		yEnemyKill = enemyList.get(idEnemy).y;
		collitionPoint = new Point(xEnemyKill, yEnemyKill);
	}

	private void validateCollitionEnemyAndLimit() {
		Rectangle limitLab = new Rectangle(X_Y_POSITION_LAB_LIMIT, X_Y_POSITION_LAB_LIMIT, WIDTH_LIMIT,
				HEIGHT_LAB_LIMIT);
		for (Integer idEnemy : enemyList.keySet()) {
			if (enemyList.get(idEnemy).intersects(limitLab)) {
				removeHealtWithCollition(idEnemy);
			}
		}
	}

	private void validateCollitionPotionAndLimit() {
		Rectangle limitWindow = new Rectangle(X_Y_POSITION_LAB_LIMIT, Y_LIMIT_DOWN_WINDOW, WIDTH_LIMIT,
				HEIGHT_LIMIT_DOWN_WINDOW);
		for (Integer id : getPotionsRemovedList().keySet()) {
			if (getPotionsRemovedList().get(id).intersects(limitWindow)) {
				confirmCollitionPotionAndLimit(id);
			}
		}
	}

	private void confirmCollitionPotionAndLimit(int id) {
		deletePotionWithId(id);
		doctor.setLosePoints();
	}

	private void removeHealtWithCollition(int idEnemy) {
		doctor.setHealth(doctor.getHealth() - 1);
		doctor.setImageDoctor(ConstantsModels.IMG_DOC_HURT);
		enemyList.get(idEnemy).setPointY();
	}

	private void deletePotionWithId(int id) {
		getPotionsRemovedList().remove(id);
	}

	private void deleteEnemyWithId(int id) {
		enemyList.remove(id);
	}

	public void shootPotion() {
		isShoot = true;
	}

	public void setImageDoctor(String path) {
		doctor.setImageDoctor(path);
	}

	public ConcurrentHashMap<Integer, Enemy> getEnemyList() {
		return enemyList;
	}

	public ConcurrentHashMap<Integer, Potion> getPotionsRemovedList() {
		return doctor.getPotionsRemovedList();
	}

	public boolean getIsWin() {
		return isWin;
	}

	public void setIsWin(boolean isWin) {
		this.isWin = isWin;
	}

	public String getNumPotionToString(String nameEnum) {
		return String.valueOf(doctor.getNumPotions(nameEnum));
	}

	public int getNumPotions(String nameEnum) {
		return doctor.getNumPotions(nameEnum);
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void getDirectionDoc(DoctorMovingEnum directionEnum) {
		doctor.getDirectionDoctor(directionEnum);
	}

	public void getPotions(PotionsEnum potionsEnum) {
		doctor.getPotions(potionsEnum);
	}

	public boolean getIsReloadable() {
		return doctor.getIsReloadable();
	}

	public void addReloadPotions() {
		doctor.addReloadPotions();
	}

	public void setIsReloadable(boolean isReloadable) {
		doctor.setIsReloadable(isReloadable);
	}

	public int getNumPotionsReloaded() {
		return doctor.getNumPotionsReloaded();
	}

	public ConcurrentHashMap<Integer, Potion> getPotionsList() {
		return doctor.getPotionsList();
	}

	public boolean getIsScreenShotImg() {
		return isScreenShot;
	}

	public void setScreenShotImg(boolean isScreenShot) {
		this.isScreenShot = isScreenShot;
	}

	public boolean getIsMute() {
		return isMute;
	}

	public void setIsMute(boolean isMute) {
		this.isMute = isMute;
	}

	public void setSavingGame(boolean isSavingGame) {
		this.isSavingGame = isSavingGame;
	}

	public boolean getSavingGame() {
		return isSavingGame;
	}

	public int getxEnemyKill() {
		return xEnemyKill;
	}

	public int getyEnemyKill() {
		return yEnemyKill;
	}
}