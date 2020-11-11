package presenters;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.Timer;

import models.AutoSave;
import models.ConstantsModels;
import models.DoctorMovingEnum;
import models.Game;
import models.PotionsEnum;
import models.ScreenShot;
import persistence.ManagerPersistence;
import views.ConfirmDialog;
import views.ConstantsGUI;
import views.MainFrame;
import views.MainHistoryDialog;
import views.MainPauseDialog;
import views.MainStartDialog;

public class Presenter extends KeyAdapter implements ActionListener {
	private MainStartDialog startDialog;
	private MainHistoryDialog historyDialog;
	private ManagerMouseEvents managerMouseEvents;
	private MainPauseDialog mainPauseDialog;
	private MainFrame mainFrame;
	private Game game;
	private ScreenShot screenShot;
	private AutoSave autoSave;
	private Clip sound;
	private Timer timer;
	private ConfirmDialog confirmDialog;
	private boolean isMute;
	private ArrayList<String> imgs;

	public Presenter() {
		managerMouseEvents = new ManagerMouseEvents(this);
		startDialog = new MainStartDialog(this);
		historyDialog = new MainHistoryDialog(managerMouseEvents);
		playMusic(ConstantsModels.MUSIC_START, true);
		imgs = ManagerPersistence.getImages();
		timer = new Timer(33, listenerMainTimer());
	}

	public ActionListener listenerMainTimer() {
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				screenShot.setWindowRectangle(getRectangleWindow());
				getGame();
				getIsWinGame();
				game.setScreenShotImg(screenShot.getIsCameraImg());
				imgs = ManagerPersistence.getImages();
				game.setSavingGame(autoSave.getIsSaveImg());
				actionToWinGame();
				actionToLose();
				mainFrame.updateGame(game);
			}
		};
		return actionListener;
	}

	public void initMainTimer() {
		stopMusic();
		game = new Game();
		changeHistoryToGame();
		runThreadsIntoGame();
		timer.start();
	}

	private void changeHistoryToGame() {
		mainFrame = new MainFrame(game);
		mainFrame.focusPanel(this);
		historyDialog.dispose();
		mainFrame.setVisible(true);
	}

	private void runThreadsIntoGame() {
		playMusic(ConstantsModels.MUSIC_INTO_GAME, true);
		screenShot = new ScreenShot(getRectangleWindow());
		autoSave = new AutoSave(getGame());
	}

	public Game getGame() {
		return game;
	}

	private Rectangle getRectangleWindow() {
		return new Rectangle(mainFrame.getX(), mainFrame.getY(), mainFrame.getWidth(), mainFrame.getHeight());
	}

	public void drawHistory(int next) {
		try {
			historyDialog.drawHistory(next);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Event.valueOf(e.getActionCommand())) {
		case PRESS_START_GAME:
			actionToPressStart();
			break;
		case PRESS_LOAD_GAME:
			actionToPressLoad();
			break;
		case PRESS_EXIT_GAME:
			actionToPressExit();
			break;
		case PRESS_CONTINUE_GAME:
			actionToPressContinue();
			break;
		case PRESS_RESTART_GAME:
			actionToPressRestart();
			break;
		case PRESS_EXIT_TO_MENU:
			actionToPressExitToMenu();
			break;
		case PRESS_BACK_TO_MENU_GAME:
			actionToPressBackToMenu();
			break;
		case PRESS_SHOW_SCREENS_GAME:
			actionToPressShowScreens();
			break;
		case PRESS_CANCEL_CONFIRM_EXIT_GAME:
			actionToPressCancelExit();
			break;
		case PRESS_CONFIRM_EXIT_GAME:
			System.exit(0);
			break;
		}
	}

	private void actionToPressStart() {
		startDialog.dispose();
		historyDialog.setVisible(true);
	}

	private void actionToPressLoad() {
		stopMusic();
		changeStartToLoadGame();
		runThreadsIntoGame();
		timer.start();
	}

	private void changeStartToLoadGame() {
		startDialog.dispose();
		game = ManagerPersistence.loadGame();
		mainFrame = new MainFrame(game);
		mainFrame.focusPanel(this);
		mainFrame.setVisible(true);
	}

	private void actionToPressExit() {
		confirmDialog = new ConfirmDialog(this, ConstantsGUI.TITLE_CONFIRM_DIALOG, ConstantsGUI.MESSAGE_CONFIRM_DIALOG);
	}

	private void actionToPressCancelExit() {
		confirmDialog.dispose();
	}

	private void actionToPressContinue() {
		stopMusic();
		mainPauseDialog.dispose();
		playMusic(ConstantsModels.MUSIC_INTO_GAME, true);
		mainFrame.setVisible(true);
		game.pauseOrResumeGame();
	}

	private void actionToPressRestart() {
		mainFrame.dispose();
		stopMusic();
		game = new Game();
		mainFrame = new MainFrame(game);
		mainFrame.focusPanel(this);
		mainPauseDialog.dispose();
		mainFrame.setVisible(true);
		runThreadsIntoGame();
	}

	private void actionToPressExitToMenu() {
		screenShot.stopSSThread();
		mainFrame.dispose();
		mainPauseDialog.dispose();
		stopMusic();
		startDialog = new MainStartDialog(this);
		playMusic(ConstantsModels.MUSIC_START, true);
	}

	private void actionToPressBackToMenu() {
		startDialog.createPanelStart(this);
	}

	private void actionToWinGame() {
		if (game.getIsWin() == true) {
			mainFrame.setVisible(false);
			screenShot.stopSSThread();
			autoSave.stopAutoSaveThread();
			stopMusic();
			mainPauseDialog = new MainPauseDialog(this);
			mainPauseDialog.createPanelWin(this);
			playMusic(ConstantsModels.MUSIC_WIN, true);
			game.setIsWin(false);
		}
	}

	private void actionToLose() {
		if (game.getDoctor().isDie() == true) {
			mainFrame.setVisible(false);
			autoSave.stopAutoSaveThread();
			screenShot.stopSSThread();
			stopMusic();
			mainPauseDialog = new MainPauseDialog(this);
			mainPauseDialog.createPanelLose(this);
			playMusic(ConstantsModels.MUSIC_LOSE, true);
			game.getDoctor().setIsDieStats(true);
			game.getDoctor().setHealth(4);
		}
	}

	public boolean getIsWinGame() {
		return game.getIsWin();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (getCodeDirEnum(e) != null) {
			game.getDirectionDoc(getCodeDirEnum(e));
			if (game.getPotionsList().size() > 0) {
				game.getDoctor().getActualPotion().setX(game.getDoctor().x);
			}
			mainFrame.updateGame(game);
		}
		if (getCodePotionsEnum(e) != null) {
			game.getPotions(getCodePotionsEnum(e));
			mainFrame.updateGame(game);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			game.shootPotion();
		}
		if (game.getIsReloadable() == true) {
			if (e.getKeyCode() == KeyEvent.VK_R) {
				game.addReloadPotions();
				game.getPotions(PotionsEnum.YELLOW_POTION);
				game.setIsReloadable(false);
				mainFrame.updateGame(game);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			actionToPressEscape();
		}
		if (e.getKeyCode() == KeyEvent.VK_M) {
			setMuteMusic();
		}
	}

	private void actionToPressEscape() {
		game.pauseOrResumeGame();
		stopMusic();
		mainPauseDialog = new MainPauseDialog(this);
		playMusic(ConstantsModels.MUSIC_PAUSE, true);
		mainPauseDialog.setVisible(true);
		mainFrame.setVisible(false);
	}

	private void actionToPressShowScreens() {
		startDialog.createPanelListImg(this, imgs);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		game.setImageDoctor(ConstantsModels.IMG_DOC_STOP_FRONT);
		mainFrame.updateGame(game);
	}

	public DoctorMovingEnum getCodeDirEnum(KeyEvent keyEvent) {

		switch (keyEvent.getKeyCode()) {
		case 39:
			return DoctorMovingEnum.RIGHT;
		case 37:
			return DoctorMovingEnum.LEFT;
		}
		return null;
	}

	public PotionsEnum getCodePotionsEnum(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
		case 49:
			return PotionsEnum.YELLOW_POTION;
		case 50:
			return PotionsEnum.GREEN_POTION;
		}
		return null;
	}

	private void playMusic(String pathMusic, boolean isLoop) {
		sound = ManagerPersistence.getSound(pathMusic);
		if (isLoop == true) {
			sound.loop(Clip.LOOP_CONTINUOUSLY);
		}
		sound.start();
	}

	private void stopMusic() {
		sound.stop();
	}

	private void startMusic() {
		sound.start();
	}

	private void setMuteMusic() {
		if (isMute == true) {
			stopMusic();
			game.setIsMute(true);
			isMute = false;
		} else {
			startMusic();
			game.setIsMute(false);
			isMute = true;
		}
	}

	public static void main(String[] args) {
		new Presenter();
	}
}