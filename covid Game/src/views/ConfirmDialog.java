package views;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import presenters.Event;

public class ConfirmDialog extends JDialog {

	private static final int PROPORTION = 50;
	private static final String SEPARATOR = "-";
	private static final long serialVersionUID = 1L;
	private PanelConfirm panelConfirm;

	public ConfirmDialog(ActionListener actionListener, String title, String message) {
		setTitle(ConstantsGUI.TITLE_WINDOW + SEPARATOR + title);
		setIconImage(new ImageIcon(getClass().getResource(ConstantsGUI.IMG_MAIN_ICON)).getImage());
		setSize(ConstantsGUI.WIDTH_WINDOW - PROPORTION, ConstantsGUI.HEIGHT_WINDOW/2 - 100);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		initComponents(actionListener, message);
		setVisible(true);
	}
	
	private void initComponents(ActionListener actionListener, String message) {
		panelConfirm = new PanelConfirm(actionListener, Event.PRESS_CONFIRM_EXIT_GAME.name(), Event.PRESS_CANCEL_CONFIRM_EXIT_GAME.name(), message);
		add(panelConfirm);
	}
}