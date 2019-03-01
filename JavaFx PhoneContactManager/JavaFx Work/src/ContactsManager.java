import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

//Sergei Muhin
public class ContactsManager implements MyFinals {
	private static int ID = 0;
	private IContact currentContact;
	private FileListIterator<IContact> fileList;
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

	// Main Open
	public ContactsManager(String fileName) throws IOException {
		fileList = new FileListIterator<IContact>(fileName, Contact.TOTAL_SIZE, Contact.SIZE);
		lastID();
	}

	public void registerListener(ActionListener listener) {
		this.listeners.add(listener);
	}

	private void proccesEvent(String command) {
		for (ActionListener actionListener : listeners) {
			actionListener.actionPerformed(new ActionEvent(this, -1, command));
		}
	}

	// LastID not need'd but Working fine ^^
	private void lastID() {
		if (!fileList.hasNext() && !fileList.hasPrevious()) {
		} else {
			while (fileList.hasNext()) {
				fileList.next();
			}
			ID = Integer.valueOf(fileList.next().getUiData()[0].trim());
		}
	}

	// Check if There is Any Contact
	public boolean checkContactExist() {
		if (!fileList.hasNext() && !fileList.hasPrevious() && ID == 0) {
			System.out.println(NO_CONTACT);
			return false;
		} else
			return true;
	}

	public IContact getCurrentContact() {
		return currentContact;
	}

	// Create Contact
	public void creatContact(String firstName, String lastName, String phoneNumber) {
		ID++;
		IContact tmpContact = new Contact(ID, firstName, lastName, phoneNumber);
		fileList.add(tmpContact);
		System.out.println(CONTACT_CREATED);
		if (ID == 1) {
			currentContact = tmpContact;
			proccesEvent(SET_CONTACT_EVENT);
		}
	}

	// First Contact
	public void firstContact() {
		if (checkContactExist()) {
			while (fileList.hasPrevious()) {
				fileList.previous();
			}
			currentContact = fileList.previous();
			proccesEvent(SET_CONTACT_EVENT);
			System.out.println(FIRST_CONTACT);
		}
	}

	// LastContact
	public void lastContact() {
		if (checkContactExist()) {
			while (fileList.hasNext()) {
				fileList.next();
			}
			currentContact = fileList.next();
			proccesEvent(SET_CONTACT_EVENT);
			System.out.println(LAST_CONTACT);
		}
	}

	// Previous Contact
	public void previousContact() {
		if (checkContactExist()) {
			currentContact = fileList.previous();
			proccesEvent(SET_CONTACT_EVENT);
			if (!fileList.hasPrevious())
				System.out.println(FIRST_CONTACT);
		}
	}

	// Next Contact
	public void nextContact() {
		if (checkContactExist()) {
			currentContact = fileList.next();
			proccesEvent(SET_CONTACT_EVENT);
			if (!fileList.hasNext())
				System.out.println(LAST_CONTACT);
		}
	}

	// Update Contact
	public void updateContact(String firstName, String lastName, String phoneNumber) {
		IContact tmpContact = new Contact(Integer.valueOf(currentContact.getUiData()[0].trim()), firstName, lastName,
				phoneNumber);
		fileList.set(tmpContact);
		currentContact = tmpContact;
		proccesEvent(SET_CONTACT_EVENT);
	}

	public void reverse() {
		if (checkContactExist()) {
			ArrayList<IContact> tmpListReverse = new ArrayList<IContact>();
			while (fileList.hasPrevious()) {
				fileList.previous();
			}
			currentContact = fileList.previous();
			tmpListReverse.add(currentContact);
			while (fileList.hasNext()) {
				tmpListReverse.add(fileList.next());
			}
			Collections.reverse(tmpListReverse);
			while (fileList.hasPrevious()) {
				fileList.previous();
			}
			currentContact = fileList.previous();
			for (IContact list : tmpListReverse) {
				fileList.set(list);
				fileList.next();
			}
			firstContact();
		}
	}

	// Show Model
	public void show(int ascDsc) {
		if (checkContactExist()) {
			switch (ascDsc) {
			case (0):
				currentContact = fileList.next();
				proccesEvent(SET_CONTACT_EVENT);
				if (!fileList.hasNext())
					proccesEvent(SHOW_END);
				break;
			case (1):
				currentContact = fileList.previous();
				proccesEvent(SET_CONTACT_EVENT);
				if (!fileList.hasPrevious())
					proccesEvent(SHOW_END);
				break;
			}
		}
	}

	// Sort // Count // Field (Depends on index)
	public void sort(int word, int ascDesc, String fieldCount) {
		if (checkContactExist()) {
			Set<ContactCount> setList = new TreeSet<ContactCount>(new MyComparator(word + 1, ascDesc, fieldCount));
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			while (fileList.hasPrevious()) {
				fileList.previous();
			}
			currentContact = fileList.previous();
			hashMap.put(currentContact.getUiData()[word + 1].trim(), new Integer(1));
			while (fileList.hasNext()) {
				String key = fileList.next().getUiData()[word + 1].trim();
				if (hashMap.get(key) != null) {
					Integer value = ((Integer) hashMap.get(key)).intValue();
					value++;
					hashMap.put(key, new Integer(value));
				} else {
					hashMap.put(key, new Integer(1));
				}
			}
			while (fileList.hasPrevious()) {
				fileList.previous();
			}
			currentContact = fileList.previous();
			int initalCount = 0;
			String key = currentContact.getUiData()[word + 1].trim();
			int CountKey = hashMap.get(key);
			ContactCount tmpContactCount = new ContactCount(currentContact, CountKey);
			setList.add(tmpContactCount);
			initalCount++;
			while (fileList.hasNext()) {
				currentContact = fileList.next();
				key = currentContact.getUiData()[word + 1].trim();
				CountKey = hashMap.get(key);
				tmpContactCount = new ContactCount(currentContact, CountKey);
				setList.add(tmpContactCount);
				initalCount++;
			}
			int newID = 1;
			while (fileList.hasPrevious()) {
				fileList.previous();
			}
			currentContact = fileList.previous();
			int maxCount = setList.size();
			for (ContactCount list : setList) {
				IContact tmpContact = new Contact(newID, list.getContact().getUiData()[1],
						list.getContact().getUiData()[2], list.getContact().getUiData()[3]);
				fileList.set(tmpContact);
				if (!fileList.hasNext())
					;
				else {
					newID++;
					fileList.next();
				}
			}
			ID = newID;
			if (initalCount != maxCount) {
				fileList.previous();
			}
			fileList.remove();
			firstContact();
		}
	}

	// Export Mode
	public void eXport(String format) {
		try {
			if (currentContact == null)
				throw new Exception();
			else {
				File f = new File(currentContact.getUiData()[0].trim() + DOT_STRING + format);
				currentContact.export(format, f);
				System.out.println(currentContact.getUiData()[0].trim() + DOT_STRING + format + EXPORT_STRING);
			}
		} catch (Exception e) {
			System.out.println(NO_EXPORT_CONTACT);
		}
	}

	// LoadFile Mode
	public String[] loadFile(String format, String path) {
		String[] loadFileString = new String[4];
		IContact tmpLoad;
		try {
			File f = new File(path + DOT_STRING + format);
			String firstName, lastName, phoneNumber;
			switch (format) {
			case TXT_STRING:
				Scanner s = new Scanner(f);
				// reading just to read ID Useless here
				s.nextInt();
				firstName = s.next();
				lastName = s.next();
				phoneNumber = s.next();
				tmpLoad = new Contact(ID, firstName, lastName, phoneNumber);
				loadFileString = tmpLoad.getUiData();
				System.out.println(FILE + path + DOT_STRING + format + WAS_LOADED);
				s.close();
				return loadFileString;
			case OBJ_STRING:
				FileInputStream fileObjectInput = new FileInputStream(f);
				ObjectInputStream objectInput = new ObjectInputStream(fileObjectInput);
				try {
					tmpLoad = (Contact) objectInput.readObject();
					loadFileString = tmpLoad.getUiData();
					System.out.println(FILE + path + DOT_STRING + format + WAS_LOADED);
					objectInput.close();
				} catch (ClassNotFoundException e) {
					objectInput.close();
					throw new IOException();
				}
				return loadFileString;
			case BYTE_STRING:
				FileInputStream fileDataInput = new FileInputStream(f);
				DataInputStream dataInput = new DataInputStream(fileDataInput);
				// reading just to read ID Useless Here
				@SuppressWarnings("unused")
				int id = dataInput.readInt();
				tmpLoad = new Contact(ID, dataInput.readUTF(), dataInput.readUTF(), dataInput.readUTF());
				System.out.println(FILE + path + DOT_STRING + format + WAS_LOADED);
				loadFileString = tmpLoad.getUiData();
				dataInput.close();
				return loadFileString;
			default:
				break;
			}
		} catch (IOException e) {
			System.out.println(NO_FILE_EXIST);
			return loadFileString = null;
		}
		return loadFileString = null;
	}

}
