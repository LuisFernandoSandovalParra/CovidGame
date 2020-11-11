package views;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MainPauseDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private PanelPause panelPause;
	private PanelWin panelWin;
	private PanelLose panelLose;

	public MainPauseDialog(ActionListener actionListener) {
		setTitle(ConstantsGUI.TITLE_WINDOW);
		setIconImage(new ImageIcon(getClass().getResource(ConstantsGUI.IMG_MAIN_ICON)).getImage());
		setSize(ConstantsGUI.WIDTH_WINDOW, ConstantsGUI.HEIGHT_WINDOW);
		setLocationRelativeTo(null);
		setResizable(false);
		initComponents(actionListener);
		addWindowListener(actionToClose());
		setVisible(true);
	}

	private void initComponents(ActionListener actionListener) {
		panelPause = new PanelPause(actionListener);
		add(panelPause);
	}

	public void createPanelWin(ActionListener actionListener) {
		remove(panelPause);
		panelWin = new PanelWin(actionListener);
		add(panelWin);
		revalidate();
		repaint();
	}

	public void createPanelLose(ActionListener actionListener) {
		remove(panelPause);
		panelLose = new PanelLose(actionListener);
		add(panelLose);
		revalidate();
		repaint();
	}

	private WindowAdapter actionToClose() {
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		return wa;
	}

	public void confirmExit() {
		int option = JOptionPane.showConfirmDialog(null, "¿Desea salir?", ConstantsGUI.TITLE_WINDOW,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(getClass().getResource(ConstantsGUI.IMG_MAIN_ICON)));
		if (option == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}
