package models;

import persistence.ManagerPersistence;

public class AutoSave implements Runnable {

	private static final int TIME_AFTER_SAVE = 1500;
	private static final int TIME_BEFORE_SAVE = 14500;
	private boolean isSaving;
	private boolean isSaveImg;
	private Game game;

	public AutoSave(Game game) {
		initAutoSaveConditions();
		this.game = game;
		Thread threadAutoSave = new Thread(this);
		threadAutoSave.start();
	}

	private void initAutoSaveConditions() {
		isSaveImg = false;
		isSaving = true;
	}

	@Override
	public void run() {
		while (isSaving) {
			isSaveImg = false;
			try {
				Thread.sleep(TIME_BEFORE_SAVE);
				isSaveImg = true;
				Thread.sleep(TIME_AFTER_SAVE);
				ManagerPersistence.writeGame(game);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopAutoSaveThread() {
		isSaving = false;
	}

	public boolean getIsSaveImg() {
		return isSaveImg;
	}
}
