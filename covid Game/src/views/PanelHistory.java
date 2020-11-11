package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelHistory extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String CURSOR_NAME = "img";
	private static final String IMG_INSTRUCCION_THREE = "/img/instruccion3Pix.jpeg";
	private static final String IMG_INSTRUCCION_TWO = "/img/instruccion2Pix.jpeg";
	private static final String IMG_INSTRUCCION_ONE = "/img/instruccion1Pix.jpeg";
	private static final String IMG_HISTORY_GAME_ONE = "/img/texto1.jpeg";
	private static final Color COLOR_BLUE_BASE = Color.decode("#005671");
	private ArrayList<String> imagesList;
	private int counterList;
	private String path;

	public PanelHistory(MouseListener mouseListener) {
		counterList = 0;
		addMouseListener(mouseListener);
		addImagesHistory();
		path = imagesList.get(0);
		setBackground(COLOR_BLUE_BASE);
		changeCursor();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Image imageDoc = new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(620, 780,
				Image.SCALE_SMOOTH);
		g2.drawImage(new ImageIcon(imageDoc).getImage(), 10, 1, this);
	}

	public void drawHistory(int next) throws Exception {
		if (counterList >= 0 || counterList <= 3) {
			selectButtonMouse(next);
			path = imagesList.get(counterList);
			repaint();
		} else {
			throw new Exception();
		}
	}

	private void selectButtonMouse(int next) {
		switch (next) {
		case 1:
			backHistoryImage();
			break;
		case 3:
			nextHistoryImage();
			break;
		}
	}

	private void backHistoryImage() {
		if (counterList >= 0) {
			counterList--;
		}
	}

	private void nextHistoryImage() {
		if (counterList <= imagesList.size()) {
			counterList++;
		}
	}

	private void addImagesHistory() {
		imagesList = new ArrayList<>();
		imagesList.add(IMG_HISTORY_GAME_ONE);
		imagesList.add(IMG_INSTRUCCION_ONE);
		imagesList.add(IMG_INSTRUCCION_TWO);
		imagesList.add(IMG_INSTRUCCION_THREE);
	}

	private void changeCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(ConstantsGUI.CURSOR_IMAGE);
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), CURSOR_NAME);
		setCursor(c);
	}
}