package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import presenters.Event;

public class PanelShowScreenShoot extends JPanel {

	private static final Border BORDER_PANEL_START = BorderFactory.createEmptyBorder(50, 100, 50, 100);
	private static final String GIF_PC_BACKGROUND_START = "/img/pcBackgroundStart.gif";
	private static final String IMG_LOGO_TWO_PIXEL = "/img/titleScreenShoot.png";
	private static final Color COLOR_JSCROLLPANE = Color.decode("#86BDC4");
	private static final long serialVersionUID = 1L;
	private JTextField userField;

	public PanelShowScreenShoot(ActionListener actionListener, ArrayList<String> listImg) {
		setLayout(new BorderLayout(0, 20));
		setBorder(BORDER_PANEL_START);
		initComponents(actionListener, listImg);
		changeCursor();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(new ImageIcon(getClass().getResource(GIF_PC_BACKGROUND_START)).getImage(), 0, 0, getWidth(),
				getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}

	private void initComponents(ActionListener actionListener, ArrayList<String> listImg) {
		GridBagConstraints gbc = new GridBagConstraints();
		addLogoImage(gbc);
		addPanelScreens(actionListener, listImg, gbc);
		addBtnBackToMenu(actionListener);
	}


	private void addLogoImage(GridBagConstraints gbc) {
		Image imageDoc = new ImageIcon(getClass().getResource(IMG_LOGO_TWO_PIXEL)).getImage().getScaledInstance(380, 80,
				Image.SCALE_SMOOTH);
		JLabel logoImage = new JLabel(new ImageIcon(imageDoc));
		add(logoImage, BorderLayout.NORTH);
	}
	
	private void addPanelScreens(ActionListener actionListener, ArrayList<String> listImg, GridBagConstraints gbc) {
		JPanel panelContainerScreens = new JPanel();
		panelContainerScreens.setLayout(new GridLayout(listImg.size(), 1));
		panelContainerScreens.setOpaque(false);
		for (int i = 0; i < listImg.size(); i++) {
			PanelCardScreenShoot panelCardImg = new PanelCardScreenShoot(listImg.get(i), i + 1);
			panelContainerScreens.add(panelCardImg);
		}
		JScrollPane jsp = new JScrollPane(panelContainerScreens);
		jsp.getViewport().setBackground(COLOR_JSCROLLPANE);
		jsp.getVerticalScrollBar().setBackground(COLOR_JSCROLLPANE);
		add(jsp, BorderLayout.CENTER);
	}
	
	private void addBtnBackToMenu(ActionListener actionListener) {
		JButtonFormatGame btnBackToMenu = new JButtonFormatGame(actionListener, "/img/backToMenu.png", Event.PRESS_BACK_TO_MENU_GAME.name());
		add(btnBackToMenu, BorderLayout.SOUTH);
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