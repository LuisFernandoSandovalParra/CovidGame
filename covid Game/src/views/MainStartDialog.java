package views;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MainStartDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private PanelStart panelStart;
	private PanelShowScreenShoot panelShowScreenShoot;

	public MainStartDialog(ActionListener actionListener) {
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
		panelStart = new PanelStart(actionListener);
		add(panelStart);
	}

	public void createPanelListImg(ActionListener actionListeners, ArrayList<String> listImg) {
		remove(panelStart);
		panelShowScreenShoot = new PanelShowScreenShoot(actionListeners, listImg);
		add(panelShowScreenShoot);
		revalidate();
		repaint();
	}

	public void createPanelStart(ActionListener actionListener) {
		remove(panelShowScreenShoot);
		panelStart = new PanelStart(actionListener);
		add(panelStart);
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
