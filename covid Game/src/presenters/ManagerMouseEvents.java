package presenters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagerMouseEvents extends MouseAdapter{
	private Presenter presenter;
	public ManagerMouseEvents(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if(e.getButton() == 2) {
			presenter.initMainTimer();
		}else {
			presenter.drawHistory(e.getButton());
		}
	}
}
