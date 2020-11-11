package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelCardScreenShoot extends JPanel{

	private static final Font FONT_TITLE_SCREENSHOOT = new Font("Circular", Font.PLAIN, 20);
	private static final String SCREENSHOOT_NUMBER = "Captura Nro ";
	private static final long serialVersionUID = 1L;
	public PanelCardScreenShoot(String pathImage, int numImage){
		setLayout(new BorderLayout());
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0));
		initComponents(pathImage, numImage);
	}
	
	private void initComponents(String pathImage, int numImage) {
		createNameScreenShoot(numImage);
		createScreenShoot(pathImage);
	}
	
	private void createNameScreenShoot(int numImage) {
		JLabel imageSpace = new JLabel(SCREENSHOOT_NUMBER+ numImage);
		imageSpace.setOpaque(false);
		imageSpace.setBackground(Color.GREEN);
		imageSpace.setForeground(Color.WHITE);
		imageSpace.setFont(FONT_TITLE_SCREENSHOOT);
		imageSpace.setHorizontalAlignment(SwingConstants.CENTER);
		add(imageSpace, BorderLayout.NORTH);
	}

	private void createScreenShoot(String pathImage) {
		Image screenShoot = new ImageIcon(pathImage).getImage().getScaledInstance(400,
				500, Image.SCALE_SMOOTH);
		JLabel imageSpace = new JLabel(new ImageIcon(screenShoot));
		imageSpace.setOpaque(false);
		add(imageSpace, BorderLayout.CENTER);
	}
}
