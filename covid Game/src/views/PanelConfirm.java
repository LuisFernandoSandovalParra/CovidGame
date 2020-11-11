package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PanelConfirm extends JPanel {
	private static final Border CREATE_BORDER_PANEL = BorderFactory.createEmptyBorder(20, 10, 20, 10);
	private static final Font FONT_MESSAGE_CONFIRM_EXIT = new Font("Circular", Font.ITALIC, 30);
	private static final String IMG_DOCTOR_GIF = "/img/doctorGif.gif";
	private static final String IMG_ACCEPT_BTN = "/img/AcceptBtn.png";
	private static final String IMG_CANCEL_BTN = "/img/cancelBtn.png";
	private static final String GIF_PC_BACKGROUND = "/img/gameOverBackground.jpg";
	private static final long serialVersionUID = 1L;

	public PanelConfirm(ActionListener actionListener, String actionCommandAccept, String actionCommandCancel,
			String message) {
		setLayout(new GridBagLayout());
		setOpaque(false);
		setBorder(CREATE_BORDER_PANEL);
		initComponents(actionListener, actionCommandAccept, actionCommandCancel, message);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(new ImageIcon(getClass().getResource(GIF_PC_BACKGROUND)).getImage(), 0, 0, getWidth(), getHeight(),
				this);
		setOpaque(false);
		super.paint(g);
	}

	public void initComponents(ActionListener actionListener, String actionCommandAccept, String actionCommandCancel,
			String message) {
		GridBagConstraints gbc = new GridBagConstraints();
		createImageDoctor(gbc);
		createSpaceMessage(message, gbc);
		createBtnAccept(actionListener, actionCommandAccept, gbc);
		createBtnCancel(actionListener, actionCommandCancel, gbc);
	}

	private void createBtnCancel(ActionListener actionListener, String actionCommandCancel, GridBagConstraints gbc) {
		JButtonFormatGame btnCancel = new JButtonFormatGame(actionListener, IMG_CANCEL_BTN, actionCommandCancel);
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 1, 1, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(btnCancel, gbc);
	}

	private void createBtnAccept(ActionListener actionListener, String actionCommandAccept, GridBagConstraints gbc) {
		JButtonFormatGame btnAccept = new JButtonFormatGame(actionListener, IMG_ACCEPT_BTN, actionCommandAccept);
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(btnAccept, gbc);
	}

	private void createSpaceMessage(String message, GridBagConstraints gbc) {
		JLabel spaceMessage = new JLabel(message);
		spaceMessage.setFont(FONT_MESSAGE_CONFIRM_EXIT);
		spaceMessage.setForeground(Color.WHITE);
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 1, 0, 1, 1, 0.7, 1, GridBagConstraints.BOTH);
		add(spaceMessage, gbc);
	}

	private void createImageDoctor(GridBagConstraints gbc) {
		JLabel imageDoctor = new JLabel(new ImageIcon(getClass().getResource(IMG_DOCTOR_GIF)));
		GridBagConstrainsForm.gridBagConstrainsForm(gbc, 0, 0, 1, 1, 0.3, 1, GridBagConstraints.BOTH);
		add(imageDoctor, gbc);
	}
}