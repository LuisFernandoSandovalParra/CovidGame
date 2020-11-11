package views;

import java.awt.GridBagConstraints;

public class GridBagConstrainsForm{
	
	public static void gridBagConstrainsForm(GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight,
			double weightx, double weighty, int fill) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.fill = fill;
	}
}
