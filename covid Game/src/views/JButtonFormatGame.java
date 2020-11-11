package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import persistence.ManagerPersistence;

public class JButtonFormatGame extends JButton {
	private static final String SOUND_BLOP_BTN = "src/sounds/blop.wav";
	private static final Color COLOR_ENTERED = Color.decode("#DFDFDF");
	private static final Color COLOR_CLICKED = Color.decode("#CFE4DF");
	private static final Color COLOR_BTN = Color.decode("#86BDC4");
	private static final Font FONT_BUTTONS = new Font("Arial", Font.ITALIC, 20);
	private Clip sound;

	private static final long serialVersionUID = 1L;

	public JButtonFormatGame(ActionListener actionListener, String pathImg, String actionCommand) {
		Image imageStart = new ImageIcon(getClass().getResource(pathImg)).getImage().getScaledInstance(160, 60,
				Image.SCALE_SMOOTH);
		setIcon(new ImageIcon(imageStart));
		setBackground(COLOR_BTN);
		addActionListener(actionListener);
		setActionCommand(actionCommand);
		setFocusable(false);
		setFont(FONT_BUTTONS);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));
		addMouseListener(setMouseAdapter());
		changeCursor();
	}

	private void changeCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(ConstantsGUI.CURSOR_IMAGE);
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "img");
		setCursor(c);
	}

	private MouseAdapter setMouseAdapter() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				setBackground(COLOR_ENTERED);
				playSoundEfect(SOUND_BLOP_BTN);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				setBackground(COLOR_BTN);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				setBackground(COLOR_CLICKED);
			}
		};
		return mouseAdapter;
	}

	private void playSoundEfect(String pathSound) {
		sound = ManagerPersistence.getSound(pathSound);
		sound.start();
	}
}