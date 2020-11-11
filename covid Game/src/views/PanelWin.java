package views;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import presenters.Event;

public class PanelWin extends JPanel {

	private static final String CURSOR_NAME = "img";
	private static final Border BORDER_PANEL_START = BorderFactory.createEmptyBorder(200, 100, 200, 100);
	private static final String GIF_PC_BACKGROUND_WIN = "/img/winBackground.gif";
	private static final String IMG_RESTART_MATCH_PIXEL = "/img/restartGame.png";
	private static final String IMG_EXIT_TO_MENU_PIXEL = "/img/exitToMenuGame.png";
	private static final String IMG_EXIT_MATCH_PIXEL = "/img/exitMatchPixel.png";
	private static final String IMG_LOGO_TWO_PIXEL = "/img/winGif.gif";
	private static final long serialVersionUID = 1L;
	private JTextField userField;

	public PanelWin(ActionListener actionListener) {
		setLayout(new GridBagLayout());
		setBorder(BORDER_PANEL_START);
		initComponents(actionListener);
		changeCursor();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(new ImageIcon(getClass().getResource(GIF_PC_BACKGROUND_WIN)).getImage(), 0, 0, getWidth(),
				getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}

	private void initComponents(ActionListener actionListener) {
		GridBagConstraints gbc = new GridBagConstraints();
		addImageWin(gbc);
		addBtnRestart(actionListener, gbc);
		addBtnExitToMenu(actionListener, gbc);
		addBtnExit(actionListener, gbc);
	}

	private void addImageWin(GridBagConstraints gbc) {
		JLabel logoImage = new JLabel(new ImageIcon(getClass().getResource(IMG_LOGO_TWO_PIXEL)));
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 0, 2, 1, 1, 1, GridBagConstraints.BOTH);
		add(logoImage, gbc);
	}

	private void addBtnRestart(ActionListener actionListener, GridBagConstraints gbc) {
		JButton restartBtn = new JButtonFormatGame(actionListener, IMG_RESTART_MATCH_PIXEL,
				Event.PRESS_RESTART_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 2, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(restartBtn, gbc);
	}

	private void addBtnExitToMenu(ActionListener actionListener, GridBagConstraints gbc) {
		JButton exitToMenuBtn = new JButtonFormatGame(actionListener, IMG_EXIT_TO_MENU_PIXEL,
				Event.PRESS_EXIT_TO_MENU.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 3, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(exitToMenuBtn, gbc);
	}
	
	private void addBtnExit(ActionListener actionListener, GridBagConstraints gbc) {
		JButton exitGameBtn = new JButtonFormatGame(actionListener, IMG_EXIT_MATCH_PIXEL, Event.PRESS_EXIT_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 1, 3, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(exitGameBtn, gbc);
	}

	public String getTextUsernameField() {
		return userField.getText();
	}

	private void changeCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(ConstantsGUI.CURSOR_IMAGE);
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), CURSOR_NAME);
		setCursor(c);
	}
}