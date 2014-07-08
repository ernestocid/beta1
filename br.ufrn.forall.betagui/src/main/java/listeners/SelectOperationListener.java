package listeners;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import views.Application;


public class SelectOperationListener implements ListSelectionListener {

	private Application application;
	
	public SelectOperationListener(Application application) {
		this.application = application;
	}

	@Override
	public void valueChanged(ListSelectionEvent evt) {
		if (!evt.getValueIsAdjusting()) {
			JList list = (JList) evt.getSource();
			int operationIndex = list.getSelectedIndex();
			application.setSelectedOperation(operationIndex);
		}
	}
}
