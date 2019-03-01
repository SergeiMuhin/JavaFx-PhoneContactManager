import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Sergei Muhin
public class ContactsManagerFrame extends JFrame implements iView, MyFinals {
	private static final long serialVersionUID = 1L;
	private final int FRAME_LENGHT = 500;
	private final int FRAME_HIGHT = 650;
	// GridLayout Set
	private final GridLayout Grid_Four_Two = new GridLayout(4, 2);
	private final GridLayout Grid_Four_Zero = new GridLayout(4, 0);
	private final GridLayout Grid_Zero_Four = new GridLayout(0, 4);
	private final GridLayout Grid_Two_Zero = new GridLayout(2, 0);
	private final GridLayout Grid_Zero_Two = new GridLayout(0, 2);
	private final GridLayout Grid_Tree_Zero = new GridLayout(3, 0);
	// Main Panels
	private JPanel PanelUp, PanelMiddle, PanelLow, PanelFourList, PanelMain;
	// Mid panels
	private JPanel PanelMiddleOne, PanelMiddleTwo, PanelMiddleTree, PanelMiddleFour, PanelFourListOne, PanelFourListTwo;
	// Low panels
	private JPanel PanelLowRight, PanelLowLeft, PanelLowLeftOne;
	// All TextField/Label/Buttons
	private JTextField FirstName, LastName, PhoneNumber, FilePathText;
	@SuppressWarnings("unused")
	private JLabel FilePath, TextFName, TextFNameOne, TextLName, TextLNameOne, TextPNumber, TextPNumberOne;
	private JLabel FName, LName, PNumber;
	private JButton ButtonCreate, Previous, First, Next, Last, EditContact, ButtonUpdate, LoadFile, Export, ShowButton,
			SortButton;
	// ComboBox here
	private JComboBox<String> ComboBox, SortBox, SortByBox, SortAscDescBox, SortAscDescForShowBox;
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

	// Register the events to listener Array
	@Override
	public void registerListener(ActionListener listener) {
		this.listeners.add(listener);
	}

	// Take's Command Name and make's Them Event same Name
	@Override
	public void processEvent(String command) {
		for (ActionListener actionListener : listeners) {
			actionListener.actionPerformed(new ActionEvent(this, -1, command));
		}
	}

	// Initiate the view (Screen)
	@Override
	public void init() {
		// Set the Frame here
		this.add(PanelMain = new JPanel());
		PanelMain.setLayout(Grid_Tree_Zero);
		PanelMain.add(PanelUp = new JPanel());
		PanelMain.add(PanelMiddle = new JPanel());
		PanelMain.add(PanelLow = new JPanel());
		new BorderLayout();
		this.add(PanelFourList = new JPanel(), BorderLayout.SOUTH);

		// Add PANEL UP
		PanelUp.add(TextFName = new JLabel(FIRST_NAME));
		PanelUp.add(FirstName = new JTextField());
		PanelUp.add(TextLName = new JLabel(LAST_NAME));
		PanelUp.add(LastName = new JTextField());
		PanelUp.add(TextPNumber = new JLabel(PHONE_NUMBER));
		PanelUp.add(PhoneNumber = new JTextField());
		PanelUp.add(ButtonCreate = new JButton(CREATE_BUTTON));
		PanelUp.add(ButtonUpdate = new JButton(UPDATE_BUTTON));
		ButtonUpdate.setEnabled(false);

		// Add PANEL MIDDLE
		PanelMiddle.add(PanelMiddleOne = new JPanel());
		PanelMiddle.add(PanelMiddleTwo = new JPanel());
		PanelMiddle.add(PanelMiddleTree = new JPanel());
		PanelMiddle.add(PanelMiddleFour = new JPanel());
		PanelMiddleOne.add(Previous = new JButton(PREVIOUS_BUTTON));
		PanelMiddleOne.add(First = new JButton(FIRST_BUTTON));
		PanelMiddleTwo.add(TextFNameOne = new JLabel(FIRST_NAME));
		PanelMiddleTwo.add(TextLNameOne = new JLabel(LAST_NAME));
		PanelMiddleTwo.add(TextPNumberOne = new JLabel(PHONE_NUMBER));
		PanelMiddleTwo.add(EditContact = new JButton(EDIT_CONTACT));
		PanelMiddleTree.add(FName = new JLabel());
		PanelMiddleTree.add(LName = new JLabel());
		PanelMiddleTree.add(PNumber = new JLabel());
		PanelMiddleFour.add(Next = new JButton(NEXT_BUTTON));
		PanelMiddleFour.add(Last = new JButton(LAST_BUTTON));

		// Add PANEL LOW
		PanelLow.add(PanelLowLeft = new JPanel());
		PanelLow.add(PanelLowRight = new JPanel());
		PanelLowRight.add(FilePath = new JLabel(FILE_PATH));
		PanelLowRight.add(FilePathText = new JTextField());
		PanelLowRight.add(LoadFile = new JButton(LOAD_BUTTON));
		PanelLowLeft.add(PanelLowLeftOne = new JPanel());
		PanelLowLeftOne.setLayout(new GridBagLayout());
		PanelLowLeftOne.add(ComboBox = new JComboBox<String>(COMBO_BOX_STRING));
		PanelLowLeft.add(Export = new JButton(EXPORT_BUTTON));

		// Add PANEL LOW EDITION (HW2)
		PanelFourList.add(PanelFourListOne = new JPanel());
		PanelFourList.add(PanelFourListTwo = new JPanel());
		PanelFourListOne.add(SortBox = new JComboBox<String>(SORT_STRING));
		PanelFourListOne.add(SortByBox = new JComboBox<String>(SORT_BY_STRING));
		PanelFourListOne.add(SortAscDescBox = new JComboBox<String>(ASC_DESC_STRING));
		PanelFourListOne.add(SortButton = new JButton(SORT_BUTTON));
		PanelFourListTwo.add(SortAscDescForShowBox = new JComboBox<String>(ASC_DESC_STRING));
		PanelFourListTwo.add(ShowButton = new JButton(SHOW_BUTTON));

		// SetPanel location Grid

		PanelUp.setLayout(Grid_Four_Two);
		PanelMiddle.setLayout(Grid_Zero_Four);
		PanelMiddleOne.setLayout(Grid_Two_Zero);
		PanelMiddleTwo.setLayout(Grid_Four_Zero);
		PanelMiddleTree.setLayout(Grid_Four_Zero);
		PanelMiddleFour.setLayout(Grid_Two_Zero);
		PanelLow.setLayout(Grid_Zero_Two);
		PanelLowRight.setLayout(Grid_Tree_Zero);
		PanelLowLeft.setLayout(Grid_Zero_Two);
		PanelFourList.setLayout(Grid_Two_Zero);
		PanelFourListOne.setLayout(Grid_Zero_Four);
		PanelFourListTwo.setLayout(Grid_Zero_Two);

		// Add Buttons listener!
		ButtonCreate.addActionListener(new ButtonListener());
		Previous.addActionListener(new ButtonListener());
		First.addActionListener(new ButtonListener());
		Next.addActionListener(new ButtonListener());
		Last.addActionListener(new ButtonListener());
		ButtonUpdate.addActionListener(new ButtonListener());
		EditContact.addActionListener(new ButtonListener());
		SortButton.addActionListener(new ButtonListener());
		LoadFile.addActionListener(new ButtonListener());
		Export.addActionListener(new ButtonListener());
		ShowButton.addActionListener(new ButtonListener());

		// No need we do this Here , FX Come's Second and it Update's them Both
		// processEvent(FIRST_BUTTON);

		// Set frame here
		this.setSize(FRAME_LENGHT, FRAME_HIGHT);
		this.setTitle(CONTACT_MANAGER);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Send's all the Events from Button here to The ProcessEvent.
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			processEvent(e.getActionCommand());
		}
	}

	// Return the Indexe's of Fields (Sort/Count/Reverse)..
	@Override
	public int[] getFieldIndex() {
		int[] tmpIndex = new int[3];
		tmpIndex[0] = SortBox.getSelectedIndex();
		tmpIndex[1] = SortByBox.getSelectedIndex();
		tmpIndex[2] = SortAscDescBox.getSelectedIndex();
		return tmpIndex;

	}

	// Return What type of Export (Txt,Obj,Byte).
	@Override
	public String getExportIndex() {
		return ComboBox.getItemAt(ComboBox.getSelectedIndex());

	}

	// Return What to Load (Name,Type)
	@Override
	public String[] loadFile() {
		String[] tmpString = new String[2];
		tmpString[0] = ComboBox.getItemAt(ComboBox.getSelectedIndex());
		tmpString[1] = FilePathText.getText().trim();
		return tmpString;

	}

	// Clears the FilePathText
	@Override
	public void filePathClear() {
		FilePathText.setText(EMPTY_STRING);
	}

	// Return what Type of Show Ascending or Descending
	@Override
	public int getShowIndex() {
		return SortAscDescForShowBox.getSelectedIndex();

	}

	// Set's The Contact received from Controller
	@Override
	public void setContactInfo(String[] contact) {
		String[] tmpString = contact;
		FName.setText(tmpString[1].trim());
		LName.setText(tmpString[2].trim());
		PNumber.setText(tmpString[3].trim());
	}

	// Return Text From Texts line.
	@Override
	public String[] getText() {
		String[] tmpString = new String[3];
		tmpString[0] = FirstName.getText();
		tmpString[1] = LastName.getText();
		tmpString[2] = PhoneNumber.getText();
		return tmpString;
	}

	// Set's The Loade'd Text After Receiving The LoadFile Info
	@Override
	public void setLoadText(String[] tmpString) {
		FirstName.setText(tmpString[1]);
		LastName.setText(tmpString[2]);
		PhoneNumber.setText(tmpString[3]);
	}

	// Upload's the Current Contact Display to the TextLine.
	@Override
	public void setContactText() {
		FirstName.setText(FName.getText());
		LastName.setText(LName.getText());
		PhoneNumber.setText(PNumber.getText());
	}

	// Set's Clear TextLine
	@Override
	public void setClearText() {
		FirstName.setText(EMPTY_STRING);
		LastName.setText(EMPTY_STRING);
		PhoneNumber.setText(EMPTY_STRING);
	}

	// Update Button Disable enable as requested
	@Override
	public void allowButton(boolean Condition, boolean timerOrUpdate) {
		ButtonCreate.setEnabled(Condition);
		Previous.setEnabled(Condition);
		First.setEnabled(Condition);
		Next.setEnabled(Condition);
		Last.setEnabled(Condition);
		ButtonUpdate.setEnabled(!Condition);
		SortByBox.setEnabled(Condition);
		SortAscDescBox.setEnabled(Condition);
		SortAscDescForShowBox.setEnabled(Condition);
		SortBox.setEnabled(Condition);
		ShowButton.setEnabled(Condition);
		SortButton.setEnabled(Condition);
		LoadFile.setEnabled(Condition);
		Export.setEnabled(Condition);
		ComboBox.setEnabled(Condition);
		FilePathText.setEnabled(Condition);
		if (timerOrUpdate) {
			ButtonUpdate.setEnabled(Condition);
			if (timerOrUpdate && Condition) {
				ButtonUpdate.setEnabled(!Condition);
			}
			EditContact.setEnabled(Condition);
			FirstName.setEnabled(Condition);
			LastName.setEnabled(Condition);
			PhoneNumber.setEnabled(Condition);
		}
	}

	/** All of those Use'd Only in the JavaFx By request! **/
	@Override
	public void setMyColor(Colors myColor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startColorTimer() {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateColor() {
		// TODO Auto-generated method stub
	}

}
