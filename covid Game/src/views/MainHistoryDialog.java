package views;

import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class MainHistoryDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	private PanelHistory panelHistory; 
	
	public MainHistoryDialog(MouseListener mouseListener) {
		setTitle(ConstantsGUI.TITLE_WINDOW);
		setIconImage(new ImageIcon(getClass().getResource(ConstantsGUI.IMG_MAIN_ICON)).getImage());
		setSize(ConstantsGUI.WIDTH_WINDOW, ConstantsGUI.HEIGHT_WINDOW);
		setLocationRelativeTo(null);
		setResizable(false);
		initComponents(mouseListener);
		addWindowListener(actionToClose());
		setVisible(false);
	}
	
	private void initComponents(MouseListener mouseListener) {
		panelHistory = new PanelHistory(mouseListener);
		add(panelHistory);
	}
	
	public void drawHistory(int next) throws Exception {
		System.out.println("este es el next"+ next);
		panelHistory.drawHistory(next);
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
}