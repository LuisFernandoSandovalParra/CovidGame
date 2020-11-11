package models;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.io.IOException;

import persistence.ManagerPersistence;

public class ScreenShot implements Runnable {

	private static final int TIME_AFTER_SCREEN_SHOOT = 1500;
	private static final int TIME_BEFORE_SCREEN_SHOOT = 13500;
	private Rectangle windowRectangle;
	private boolean isCapturing;
	private boolean isCameraImg;
	
	public ScreenShot(Rectangle windowRectangle) {
		initScreenShootConditions();
		this.windowRectangle = windowRectangle;
		initScreenShootThread();
	}

	private void initScreenShootThread() {
		Thread threadScreenCap = new Thread(this);
		threadScreenCap.start();
	}

	private void initScreenShootConditions() {
		isCameraImg = false;
		isCapturing = true;
	}

	@Override
	public void run() {
		while (isCapturing) {
			isCameraImg = false;
			try {
				Thread.sleep(TIME_BEFORE_SCREEN_SHOOT);
				isCameraImg = true;
				Thread.sleep(TIME_AFTER_SCREEN_SHOOT);
				ManagerPersistence.captureScreen(windowRectangle);
			} catch (AWTException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopSSThread() {
		isCapturing = false;
	}

	public Rectangle getWindowRectangle() {
		return windowRectangle;
	}

	public void setWindowRectangle(Rectangle windowRectangle) {
		this.windowRectangle = windowRectangle;
	}
	
	public boolean getIsCameraImg() {
		return isCameraImg;
	}
}