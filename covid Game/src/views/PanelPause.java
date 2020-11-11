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

public class PanelPause extends JPanel {

	private static final String CURSOR_NAME = "img";
	private static final Border BORDER_PANEL_START = BorderFactory.createEmptyBorder(100, 100, 100, 100);
	private static final String GIF_PC_BACKGROUND_PAUSE = "/img/doctorBackgroundPause.gif";
	private static final String IMG_CONTINUE_MATCH_PIXEL = "/img/continueGame.png";
	private static final String IMG_RESTART_MATCH_PIXEL = "/img/restartGame.png";
	private static final String IMG_EXIT_TO_MENU_PIXEL = "/img/exitToMenuGame.png";
	private static final String IMG_EXIT_MATCH_PIXEL = "/img/exitMatchPixel.png";
	private static final String IMG_LOGO_TWO_PIXEL = "/img/pause.png";
	private static final long serialVersionUID = 1L;
	private JTextField userField;

	public PanelPause(ActionListener actionListener) {
		setLayout(new GridBagLayout());
		setBorder(BORDER_PANEL_START);
		initComponents(actionListener);
		changeCursor();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(new ImageIcon(getClass().getResource(GIF_PC_BACKGROUND_PAUSE)).getImage(), 0, 0, getWidth(),
				getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}

	private void initComponents(ActionListener actionListener) {
		GridBagConstraints gbc = new GridBagConstraints();
		addImagePause(gbc);
		addBtnContinue(actionListener, gbc);
		addBtnRestart(actionListener, gbc);
		addBtnExitToMenu(actionListener, gbc);
		addBtnExit(actionListener, gbc);
	}

	private void addImagePause(GridBagConstraints gbc) {
		Image imageDoc = new ImageIcon(getClass().getResource(IMG_LOGO_TWO_PIXEL)).getImage().getScaledInstance(400,
				350, Image.SCALE_SMOOTH);
		JLabel logoImage = new JLabel(new ImageIcon(imageDoc));
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 0, 2, 1, 1, 1, GridBagConstraints.BOTH);
		add(logoImage, gbc);
	}

	private void addBtnContinue(ActionListener actionListener, GridBagConstraints gbc) {
		JButton continueBtn = new JButtonFormatGame(actionListener, IMG_CONTINUE_MATCH_PIXEL,
				Event.PRESS_CONTINUE_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 2, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(continueBtn, gbc);
	}

	private void addBtnRestart(ActionListener actionListener, GridBagConstraints gbc) {
		JButton restartBtn = new JButtonFormatGame(actionListener, IMG_RESTART_MATCH_PIXEL,
				Event.PRESS_RESTART_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 1, 2, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(restartBtn, gbc);
	}

	private void addBtnExitToMenu(ActionListener actionListener, GridBagConstraints gbc) {
		JButton exitToMenuBtn = new JButtonFormatGame(actionListener, IMG_EXIT_TO_MENU_PIXEL,
				Event.PRESS_EXIT_TO_MENU.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 3, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(exitToMenuBtn, gbc);
	}
	
	private void addBtnExit(ActionListener actionListener, GridBagConstraints gbc) {
		JButton exitGameBtn = new JButtonFormatGame(actionListener, IMG_EXIT_MATCH_PIXEL, Event.PRESS_EXIT_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 4, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
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