import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements ActionListener, MyFinals {
	private ArrayList<iView> views = new ArrayList<iView>();
	private ContactsManager contactM;
	Timer timer;

	/** Creating Connection view-> Controller <- Model **/

	// The Controller add the Model here (ContactsManager) + registers Model to
	// Listener (So you can listen to Model Events)

	public Controller(ContactsManager cm) {
		this.contactM = cm;
		this.contactM.registerListener(this);
	}

	// Add the view Here + registers View to listener (So you can listen to
	// Views Events) Start Initiate in view ( All the Frame go's Up)
	public void addView(iView view) {
		this.views.add(view);
		view.registerListener(this);
		view.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Convert Events to String so i can Check Null = Timer or More
		// Exception's if Existing
		String event = e.getActionCommand();
		boolean colorUpdate = false;
		iView currentView;
		Colors myColor = null;
		switch (event) {
		// Next Contact Event
		case NEXT_BUTTON:
			contactM.nextContact();
			colorUpdate = true;
			myColor = Colors.NEXT;
			break;
		// Previous Contact Event
		case PREVIOUS_BUTTON:
			contactM.previousContact();
			colorUpdate = true;
			myColor = Colors.PREVIOUS;
			break;
		// Last Contact Event
		case LAST_BUTTON:
			contactM.lastContact();
			colorUpdate = true;
			myColor = Colors.NEXT;
			break;
		// First Contact Event
		case FIRST_BUTTON:
			contactM.firstContact();
			colorUpdate = true;
			myColor = Colors.PREVIOUS;
			break;
		// Creating new Contact here
		case CREATE_BUTTON:
			currentView = (iView) e.getSource();
			if (currentView.getText()[0].isEmpty() || currentView.getText()[1].isEmpty()
					|| currentView.getText()[2].isEmpty()) {
				System.out.println(NOT_ENOUGHT_INFORMATION);
			} else {
				contactM.creatContact(currentView.getText()[0], currentView.getText()[1], currentView.getText()[2]);
				currentView.setClearText();
			}
			for (iView view : views) {
				view.setClearText();
			}
			myColor = Colors.CREATE;
			colorUpdate = true;
			break;
		// Edit Contact here (Upload's the CurrentContact on Views Text
		// line)
		case EDIT_CONTACT:
			if (contactM.checkContactExist()) {
				for (iView consoleView : views) {
					consoleView.setContactText();
					consoleView.allowButton(false, false);
				}
			}
			break;
		// Update's the CurrentContact from View
		case UPDATE_BUTTON:
			currentView = (iView) e.getSource();
			contactM.updateContact(currentView.getText()[0], currentView.getText()[1], currentView.getText()[2]);
			for (iView view : views) {
				view.setMyColor(Colors.CREATE);
				view.setClearText();
				view.allowButton(true, false);
			}

			myColor = Colors.CREATE;
			colorUpdate = true;
			break;
		// Sorting by Request (Receives request info from View changes
		// Contacts
		// accordingly with Model)
		case SORT_BUTTON:
			int[] tmpIndex = new int[3];
			currentView = (iView) e.getSource();
			tmpIndex = currentView.getFieldIndex();
			switch (tmpIndex[0]) {
			case (0):
				contactM.sort(tmpIndex[1], tmpIndex[2], SORT_FIELD);
				break;
			case (1):
				contactM.sort(tmpIndex[1], tmpIndex[2], SORT_COUNT);
				break;
			case (2):
				contactM.reverse();
				break;
			default:
				break;
			}
			if (tmpIndex[2] == 0) {

				colorUpdate = true;
				myColor = Colors.NEXT;
			} else if (tmpIndex[2] == 1) {
				colorUpdate = true;
				myColor = Colors.PREVIOUS;
			}
			System.out.println(tmpIndex[2]);
			break;
		// Get Type Of Export and Exports CurrentContact
		case EXPORT_BUTTON:
			currentView = (iView) e.getSource();
			contactM.eXport(currentView.getExportIndex());
			break;
		// Gets Type and Name To search,if exists Load's View textLine from
		// the Existing File
		case LOAD_BUTTON:
			currentView = (iView) e.getSource();
			String[] textLoadString = contactM.loadFile(currentView.loadFile()[0], currentView.loadFile()[1]);
			for (iView consoleView : views) {
				if (textLoadString != null) {
					consoleView.setLoadText(textLoadString);
				}
				consoleView.filePathClear();
			}
			break;
		// Check if Show Allow'd (not if Contacts don't Exist)
		// Start"s the Timer and Show Begins
		// Checks How to Start (Ascending/Descending)
		case SHOW_BUTTON:
			currentView = (iView) e.getSource();
			int showIndex = currentView.getShowIndex();
			if (showIndex == 0) {
				contactM.firstContact();
				colorUpdate = true;
				myColor = Colors.NEXT;
			} else if (showIndex == 1) {
				contactM.lastContact();
				colorUpdate = true;
				myColor = Colors.PREVIOUS;
			}
			for (iView view : views) {
				view.allowButton(false, true);
			}
			switch (showIndex) {
			case 0:
				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						contactM.show(showIndex);
					}
				}, TIMER_DELAY_ONE_SECOND, TIMER_DELAY_ONE_SECOND);
				break;
			case 1:
				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						contactM.show(showIndex);
					}
				}, TIMER_DELAY_ONE_SECOND, TIMER_DELAY_ONE_SECOND);
				break;
			}
			break;
		case SHOW_END:
			timer.cancel();
			System.out.println(SHOW_END);
			for (iView view : views) {
				view.allowButton(true, true);
			}
			break;
		// Set's the CurrentContact in the Model to the view
		case SET_CONTACT_EVENT:
			IContact tmpContact = contactM.getCurrentContact();
			for (iView consoleView : views) {
				consoleView.setContactInfo(tmpContact.getUiData());
			}
			break;
		default:
			break;
		}
		if (colorUpdate == true) {
			for (iView view : views) {
				view.setMyColor(myColor);
			}
		}
	}
}
