package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import models.Game;
import models.PotionsEnum;

public class PanelGame extends JPanel {

	private static final String ONE_HUNDRED_POINTS = "100";
	private static final String NAME_CURSOR = "img";
	private static final String IMG_DISK_SAVE_GAME = "/img/diskSaveGame.png";
	private static final String IMG_MUTE = "/img/muteIcon.png";
	private static final String IMG_CAMERA_SCREENS = "/img/cameraScreens.png";
	private static final int Y_POSITION_DRAW_HEALTH = 10;
	private static final int X_POSITION_DRAW_HEALTH = 420;
	private static final String IMG_VIRUS_GREEN = "/img/virusGreen.png";
	private static final String IMG_VIRUS_RED = "/img/virusRed.png";
	private static final String IMG_REMEDY_GREEN = "/img/remedyGreen.png";
	private static final String IMG_REMEDY_YELLOW = "/img/remedyRed.png";
	private static final String IMG_HEALTH = "/img/health.png";
	private static final Font GENERAL_FONT = new Font("Roboto", Font.PLAIN, 25);
	private static final Color BASE_COLOR = Color.decode("#008081");
	private static final String IMG_LABORATORY = "/img/laboratorio.jpg";
	private static final long serialVersionUID = 1L;
	private Game game;

	public PanelGame(Game game) {
		this.game = game;
		setBackground(BASE_COLOR);
		setFocusable(true);
		changeCursor();
	}

	private void changeCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(ConstantsGUI.CURSOR_IMAGE);
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), NAME_CURSOR);
		setCursor(c);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paint(g2);
		drawStadisticsTable(g2);
		drawScenary(g2);
		drawMuteIcon(g2);
		drawCamScreen(g2);
		drawDiskSave(g2);
		drawDoctor(g2);
		drawEnemies(g2);
		drawExplotionAndPoints(g2);
		drawReloadZone(g2);
		drawNumEnemies(g2);
	}

	private void drawStadisticsTable(Graphics2D g2) {
		g2.setStroke(new BasicStroke(3.0f));
		g2.drawRect(5, 5, getWidth() - 10, 59);
		drawPoints(g2);
		drawPotions(g2);
		drawHealthIndex(g2);
	}

	private void drawPoints(Graphics2D g2) {
		g2.setFont(GENERAL_FONT);
		g2.setColor(Color.WHITE);
		g2.drawString(String.valueOf(game.getDoctor().getPoints()), 15, 40);
	}

	private void drawPotions(Graphics2D g2) {
		for (int i = 0; i < 4; i++) {
			g2.drawImage(new ImageIcon(getClass().getResource(IMG_REMEDY_YELLOW)).getImage(), 250, 10, this);
			g2.setColor(Color.white);
			g2.drawString(game.getNumPotionToString(PotionsEnum.YELLOW_POTION.name()), 242, 32);
			g2.drawImage(new ImageIcon(getClass().getResource(IMG_REMEDY_GREEN)).getImage(), 300, 10, this);
			g2.setColor(Color.white);
			g2.drawString(game.getNumPotionToString(PotionsEnum.GREEN_POTION.name()), 298, 32);
		}
	}

	private void drawHealthIndex(Graphics2D g2) {
		int xHealth = X_POSITION_DRAW_HEALTH;
		int yHealth = Y_POSITION_DRAW_HEALTH;
		for (int i = 0; i < game.getDoctor().getHealth(); i++) {
			g2.drawImage(new ImageIcon(getClass().getResource(IMG_HEALTH)).getImage(), xHealth, yHealth, this);
			xHealth += 50;
		}
	}

	private void drawScenary(Graphics2D g2) {
		g2.drawImage(new ImageIcon(getClass().getResource(IMG_LABORATORY)).getImage(), 0, 65, this);
	}

	private void drawMuteIcon(Graphics2D g2) {
		if (game.getIsMute() == true) {
			Image imageMute = new ImageIcon(getClass().getResource(IMG_MUTE)).getImage().getScaledInstance(50, 40,
					Image.SCALE_SMOOTH);
			g2.drawImage(new ImageIcon(imageMute).getImage(), 10, 70, this);
		}
	}

	private void drawCamScreen(Graphics2D g2) {
		if (game.getIsScreenShotImg() == true) {
			Image imageCamera = new ImageIcon(getClass().getResource(IMG_CAMERA_SCREENS)).getImage()
					.getScaledInstance(50, 40, Image.SCALE_SMOOTH);
			g2.drawImage(new ImageIcon(imageCamera).getImage(), 70, 70, this);
		}
	}

	private void drawDiskSave(Graphics2D g2) {
		if (game.getSavingGame() == true) {
			Image imageSaving = new ImageIcon(getClass().getResource(IMG_DISK_SAVE_GAME)).getImage()
					.getScaledInstance(50, 40, Image.SCALE_SMOOTH);
			g2.drawImage(new ImageIcon(imageSaving).getImage(), 120, 70, this);
		}

	}

	private void drawDoctor(Graphics2D g2) {
		Image imageDoc = new ImageIcon(getClass().getResource(game.getDoctor().getImageDoctor())).getImage()
				.getScaledInstance(75, 115, Image.SCALE_SMOOTH);
		g2.drawImage(new ImageIcon(imageDoc).getImage(), game.getDoctor().x, game.getDoctor().y, this);
		drawPotionInHand(g2);
		drawPotionRemoved(g2);
	}

	private void drawPotionInHand(Graphics2D g2) {
		if (game.getDoctor().getPotionsList().size() > 0) {
			Image imagePotion = new ImageIcon(getClass().getResource(game.getDoctor().getImagePotion())).getImage()
					.getScaledInstance(30, 40, Image.SCALE_SMOOTH);
			g2.drawImage(new ImageIcon(imagePotion).getImage(), game.getDoctor().getActualPotion().x,
					game.getDoctor().getActualPotion().y, this);
		}
	}

	private void drawPotionRemoved(Graphics2D g2) {
		for (Integer id : game.getPotionsRemovedList().keySet()) {
			try {
				if (game.getPotionsRemovedList().size() > 0) {
					Image imagePotion = new ImageIcon(
							getClass().getResource(game.getPotionsRemovedList().get(id).getPotionImage())).getImage()
									.getScaledInstance(30, 40, Image.SCALE_SMOOTH);
					g2.drawImage(new ImageIcon(imagePotion).getImage(), game.getPotionsRemovedList().get(id).x,
							game.getPotionsRemovedList().get(id).y, this);
				}
			} catch (Exception e) {
			}
		}
	}

	private void drawExplotionAndPoints(Graphics2D g2) {
		if (game.getConfirmCollitionEnemyAndPotion() == true) {
			g2.drawString(ONE_HUNDRED_POINTS, game.getCollitionPoint().x, game.getCollitionPoint().y);
		}
	}

	private void drawEnemies(Graphics2D g2) {
		Image imageRedEnemies = new ImageIcon(getClass().getResource(IMG_VIRUS_RED)).getImage().getScaledInstance(80,
				80, Image.SCALE_SMOOTH);
		Image imageGreenEnemies = new ImageIcon(getClass().getResource(IMG_VIRUS_GREEN)).getImage()
				.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		for (Integer id : game.getEnemyList().keySet()) {
			try {
				String s1 = game.getEnemyList().get(id).getPotionsType().toString();
				String s2 = PotionsEnum.YELLOW_POTION.toString();
				if (s1.equals(s2)) {
					g2.drawImage(new ImageIcon(imageRedEnemies).getImage(), game.getEnemyList().get(id).x,
							game.getEnemyList().get(id).y, this);
				} else {
					g2.drawImage(new ImageIcon(imageGreenEnemies).getImage(), game.getEnemyList().get(id).x,
							game.getEnemyList().get(id).y, this);
				}
			} catch (Exception e) {
			}
		}
	}

	private void drawNumEnemies(Graphics2D g2) {
		drawNumRedEnemies(g2);
		drawNumGreenEnemies(g2);
	}

	private void drawNumRedEnemies(Graphics2D g2) {
		Image imageRedEnemies = new ImageIcon(getClass().getResource(IMG_VIRUS_RED)).getImage().getScaledInstance(30,
				30, Image.SCALE_SMOOTH);
		g2.drawImage(new ImageIcon(imageRedEnemies).getImage(), 490, 150, this);
		g2.setColor(Color.WHITE);
		g2.drawString(String.valueOf(game.getNumEnemies(PotionsEnum.YELLOW_POTION)), 525, 170);
	}

	private void drawNumGreenEnemies(Graphics2D g2) {
		Image imageGreenEnemies = new ImageIcon(getClass().getResource(IMG_VIRUS_GREEN)).getImage()
				.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		g2.setColor(Color.WHITE);
		g2.drawImage(new ImageIcon(imageGreenEnemies).getImage(), 490, 190, this);
		g2.drawString(String.valueOf(game.getNumEnemies(PotionsEnum.GREEN_POTION)), 525, 210);
	}

	private void drawReloadZone(Graphics2D g2) {
		drawAvailableLoadZone(g2);
		drawRandomNumsPotions(g2);
	}

	private void drawAvailableLoadZone(Graphics2D g2) {
		drawBorderAvaLoadZone(g2);
		if (game.getNumPotions(PotionsEnum.YELLOW_POTION.name()) == 0
				|| game.getNumPotions(PotionsEnum.GREEN_POTION.name()) == 0) {
			g2.setColor(Color.GREEN);
			g2.fillRect(380, 130, 30, 30);
		} else {
			g2.setColor(Color.RED);
			g2.fillRect(410, 130, 30, 30);
		}
	}

	private void drawBorderAvaLoadZone(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.drawRect(380, 130, 60, 30);
		g2.setColor(Color.WHITE);
		g2.fillRect(380, 130, 60, 30);
	}

	private void drawRandomNumsPotions(Graphics2D g2) {
		if (game.getIsReloadable() == true) {
			g2.drawString(String.valueOf(game.getNumPotionsReloaded()), 390, 200);
		}
	}

	public void updateGame(Game game) {
		this.game = game;
		repaint();
		Toolkit.getDefaultToolkit().sync();
	}
}