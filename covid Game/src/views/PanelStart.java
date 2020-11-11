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

public class PanelStart extends JPanel {

	private static final Border BORDER_PANEL_START = BorderFactory.createEmptyBorder(200, 100, 200, 100);
	private static final String GIF_PC_BACKGROUND_START = "/img/pcBackgroundStart.gif";
	private static final String IMG_SCREEN_MATCH_PIXEL = "/img/titleScreenShootLite.png";
	private static final String IMG_STAR_MATCH_PIXEL = "/img/starMatchPixel.png";
	private static final String IMG_LOAD_MATCH_PIXEL = "/img/loadMatchPixel.png";
	private static final String IMG_EXIT_MATCH_PIXEL = "/img/exitMatchPixel.png";
	private static final String IMG_LOGO_TWO_PIXEL = "/img/LogoTwoPixel.png";
	private static final long serialVersionUID = 1L;
	private JTextField userField;

	public PanelStart(ActionListener actionListener) {
		setLayout(new GridBagLayout());
		setBorder(BORDER_PANEL_START);
		initComponents(actionListener);
		changeCursor();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(new ImageIcon(getClass().getResource(GIF_PC_BACKGROUND_START)).getImage(), 0, 0, getWidth(),
				getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}

	private void initComponents(ActionListener actionListener) {
		GridBagConstraints gbc = new GridBagConstraints();
		addLogoImage(gbc);
		addBtnStart(actionListener, gbc);
		addBtnLoad(actionListener, gbc);
		addBtnExit(actionListener, gbc);
		addBtnShowScreens(actionListener, gbc);
	}

	private void addBtnShowScreens(ActionListener actionListener, GridBagConstraints gbc) {
		JButton startGameBtn = new JButtonFormatGame(actionListener, IMG_SCREEN_MATCH_PIXEL,
				Event.PRESS_SHOW_SCREENS_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 3, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(startGameBtn, gbc);
	}

	private void addBtnExit(ActionListener actionListener, GridBagConstraints gbc) {
		JButton exitGameBtn = new JButtonFormatGame(actionListener, IMG_EXIT_MATCH_PIXEL, Event.PRESS_EXIT_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 4, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(exitGameBtn, gbc);
	}

	private void addBtnLoad(ActionListener actionListener, GridBagConstraints gbc) {
		JButton loadGameBtn = new JButtonFormatGame(actionListener, IMG_LOAD_MATCH_PIXEL, Event.PRESS_LOAD_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 1, 2, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(loadGameBtn, gbc);
	}

	private void addBtnStart(ActionListener actionListener, GridBagConstraints gbc) {
		JButton startGameBtn = new JButtonFormatGame(actionListener, IMG_STAR_MATCH_PIXEL,
				Event.PRESS_START_GAME.name());
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 2, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(startGameBtn, gbc);
	}

	private void addLogoImage(GridBagConstraints gbc) {
		Image imageDoc = new ImageIcon(getClass().getResource(IMG_LOGO_TWO_PIXEL)).getImage().getScaledInstance(300,
				250, Image.SCALE_SMOOTH);
		JLabel logoImage = new JLabel(new ImageIcon(imageDoc));
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 0, 2, 1, 1, 1, GridBagConstraints.BOTH);
		add(logoImage, gbc);
	}

	public String getTextUsernameField() {
		return userField.getText();
	}

	private void changeCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(ConstantsGUI.CURSOR_IMAGE);
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "img");
		setCursor(c);
	}
}