package views;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import models.Game;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private PanelGame panelGame;

	public MainFrame(Game game) {
		setTitle(ConstantsGUI.TITLE_WINDOW);
		setIconImage(new ImageIcon(getClass().getResource(ConstantsGUI.IMG_MAIN_ICON)).getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(ConstantsGUI.WIDTH_WINDOW, ConstantsGUI.HEIGHT_WINDOW));
		setResizable(false);
		setLocationRelativeTo(null);
		initComponents(game);
		setVisible(false);
	}

	private void initComponents(Game game) {
		panelGame = new PanelGame(game);
		add(panelGame);
	}

	public void updateGame(Game game) {
		panelGame.updateGame(game);
	}

	public void focusPanel(KeyAdapter presenter) {
		addKeyListener(presenter);
		setFocusable(true);
		requestFocus();
	}
}