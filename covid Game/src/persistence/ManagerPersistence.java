package persistence;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import models.Game;

public class ManagerPersistence {

	private static final String EXTENDS_TO__FOLDER_IMGS = "db/ss/";
	private static final String SLASH = "/";
	private static final String PATH_COVID_GAME_DATA = "./db/CovidGameData.json";
	private static final String FORMAT_IMAGE = "jpg";
	private static final String DOT_FORMAT = ".jpg";
	private static final String EXTENSION_DESTINY_FOLDER = "./db/ss/";
	private static final String DESTINY_FOLDER = "./db/ss";

	public static int getTotalNumImage(File directorio) {
		int total = 0;
		String[] arrArchivos = directorio.list();
		total += arrArchivos.length;
		File tmpFile;
		for (int i = 0; i < arrArchivos.length; ++i) {
			tmpFile = new File(directorio.getPath() + SLASH + arrArchivos[i]);
			if (tmpFile.isDirectory()) {
				total += getTotalNumImage(tmpFile);
			}
		}
		return total;
	}

	public static void captureScreen(Rectangle window) throws AWTException, IOException {
		File folder = new File(DESTINY_FOLDER);
		BufferedImage captura = new Robot().createScreenCapture(window);
		File file = new File(EXTENSION_DESTINY_FOLDER + String.valueOf(getTotalNumImage(folder)) + DOT_FORMAT);
		ImageIO.write(captura, FORMAT_IMAGE, file);
	}

	public static ArrayList<String> getImages() {
		ArrayList<String> listImages = new ArrayList<>();
		File folderImages = new File(DESTINY_FOLDER);
		String[] listPathImages = folderImages.list();
		for (int i = 0; i < listPathImages.length; i++) {
			listImages.add(EXTENDS_TO__FOLDER_IMGS+ listPathImages[i]);
		}
		return listImages;
	}

	public static void writeGame(Game game) {
		String jGson = new Gson().toJson(game);

		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(PATH_COVID_GAME_DATA);
			fileWriter.write(jGson);
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Game loadGame() {
		File file = new File(PATH_COVID_GAME_DATA);
		if (file.exists()) {
			try {
				@SuppressWarnings("resource")
				Game game = new Gson().fromJson(new BufferedReader(new FileReader(file)).readLine(), Game.class);
				return game;
			} catch (JsonSyntaxException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Clip getSound(String file) {
		File audio = new File(file);
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audio);
			Clip sound = (Clip) AudioSystem.getClip();
			sound.open(audioInputStream);
			FloatControl gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f); //
			return sound;
		} catch (Exception e) {
			return null;
		}
	}

}
