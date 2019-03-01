import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

//Sergei Muhin
public class ContactsManagerJavaFX implements iView, MyFinals {
	Stage PrimaryStage;
	private TextField FirstName, LastName, PhoneNumber, FilePathText;
	private Button ButtonCreate, Previous, First, Next, Last, EditContact, ButtonUpdate, LoadFile, Export, ShowButton,
			SortButton;
	private Label TextFName, TextLName, TextPNumber;
	private ComboBox<String> ComboBox, SortBox, SortByBox, SortAscDescBox, SortAscDescForShowBox;
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private ObservableList<String> Extentions;
	private ObservableList<String> Sorts;
	private ObservableList<String> Fields;
	private ObservableList<String> OrderOne;
	private ObservableList<String> OrderTwo;
	private Colors myColor;
	private Timeline repaintTimer;

	// Add Pane's on Main Stage
	public ContactsManagerJavaFX(Stage stage) {
		PrimaryStage = stage;
		VBox mainLay = new VBox(40);
		mainLay.getChildren().add(setFirst());
		mainLay.getChildren().add(setSecond());
		mainLay.getChildren().add(setThird());
		mainLay.getChildren().add(setForth());

		Pane mainPane = new Pane();
		mainPane.getChildren().add(mainLay);
		Scene mainScene = new Scene(mainPane);
		PrimaryStage.setScene(mainScene);
	}

	// Pane first
	private Pane setFirst() {
		GridPane firstPane = new GridPane();
		firstPane.setAlignment(Pos.CENTER);
		firstPane.setHgap(6);
		firstPane.setVgap(6);
		firstPane.add(new Label(FIRST_NAME), 0, 0);
		firstPane.add(FirstName = new TextField(), 1, 0);
		firstPane.add(new Label(LAST_NAME), 0, 1);
		firstPane.add(LastName = new TextField(), 1, 1);
		firstPane.add(new Label(PHONE_NUMBER), 0, 2);
		firstPane.add(PhoneNumber = new TextField(), 1, 2);
		firstPane.add(ButtonCreate = new Button(CREATE_BUTTON), 0, 3);
		firstPane.add(ButtonUpdate = new Button(UPDATE_BUTTON), 1, 3);
		ButtonCreate.setOnAction(e -> processEvent(CREATE_BUTTON));
		ButtonUpdate.setOnAction(e -> processEvent(UPDATE_BUTTON));
		return firstPane;
	}

	// Pane Second
	private Pane setSecond() {
		GridPane innerpane = new GridPane();
		innerpane.setAlignment(Pos.CENTER);
		innerpane.setHgap(2);
		innerpane.setVgap(2);
		innerpane.add(new Label(FIRST_NAME), 0, 0);
		innerpane.add(TextFName = new Label(), 1, 0);
		innerpane.add(new Label(LAST_NAME), 0, 1);
		innerpane.add(TextLName = new Label(), 1, 1);
		innerpane.add(new Label(PHONE_NUMBER), 0, 2);
		innerpane.add(TextPNumber = new Label(), 1, 2);

		BorderPane upperpane = new BorderPane();
		upperpane.setLeft(Previous = new Button(PREVIOUS_BUTTON));
		upperpane.setRight(Next = new Button(NEXT_BUTTON));
		upperpane.setCenter(innerpane);
		Previous.setOnAction(e -> processEvent(PREVIOUS_BUTTON));
		Next.setOnAction(e -> processEvent(NEXT_BUTTON));

		BorderPane lowerpane = new BorderPane();
		lowerpane.setLeft(First = new Button(FIRST_BUTTON));
		lowerpane.setRight(Last = new Button(LAST_BUTTON));
		lowerpane.setCenter(EditContact = new Button(EDIT_CONTACT));
		First.setOnAction(e -> processEvent(FIRST_BUTTON));
		Last.setOnAction(e -> processEvent(LAST_BUTTON));
		EditContact.setOnAction(e -> processEvent(EDIT_CONTACT));

		VBox secondPanel = new VBox(5);
		secondPanel.getChildren().add(upperpane);
		secondPanel.getChildren().add(lowerpane);
		return secondPanel;
	}

	// Pane Third
	private Pane setThird() {
		ComboBox = new ComboBox<>();
		Extentions = FXCollections.observableArrayList(COMBO_BOX_STRING);
		ComboBox.getItems().addAll(Extentions);
		ComboBox.getSelectionModel().selectFirst();

		VBox leftpane = new VBox(5);
		leftpane.getChildren().add(ComboBox);
		leftpane.getChildren().add(Export = new Button(EXPORT_BUTTON));
		Export.setOnAction(e -> processEvent(EXPORT_BUTTON));

		VBox rightpane = new VBox(5);
		rightpane.getChildren().add(new Label(FILE_PATH));
		rightpane.getChildren().add(FilePathText = new TextField());
		rightpane.getChildren().add(LoadFile = new Button(LOAD_BUTTON));
		LoadFile.setOnAction(e -> processEvent(LOAD_BUTTON));

		BorderPane thirdPanel = new BorderPane();
		thirdPanel.setLeft(leftpane);
		thirdPanel.setRight(rightpane);

		return thirdPanel;
	}

	// Pane Forth
	private Pane setForth() {
		SortBox = new ComboBox<>();
		Sorts = FXCollections.observableArrayList(SORT_STRING);
		SortBox.getItems().addAll(Sorts);
		SortBox.getSelectionModel().selectFirst();

		SortByBox = new ComboBox<>();
		Fields = FXCollections.observableArrayList(SORT_BY_STRING);
		SortByBox.getItems().addAll(Fields);
		SortByBox.getSelectionModel().selectFirst();

		SortAscDescBox = new ComboBox<>();
		OrderOne = FXCollections.observableArrayList(ASC_DESC_STRING);
		SortAscDescBox.getItems().addAll(OrderOne);
		SortAscDescBox.getSelectionModel().selectFirst();

		SortAscDescForShowBox = new ComboBox<>();
		OrderTwo = FXCollections.observableArrayList(ASC_DESC_STRING);
		SortAscDescForShowBox.getItems().addAll(OrderTwo);
		SortAscDescForShowBox.getSelectionModel().selectFirst();

		VBox upperpane = new VBox(2);
		upperpane.getChildren().add(SortBox);
		upperpane.getChildren().add(SortByBox);
		upperpane.getChildren().add(SortAscDescBox);

		HBox lowerpane = new HBox(2);
		lowerpane.getChildren().add(SortAscDescForShowBox);
		lowerpane.getChildren().add(ShowButton = new Button(SHOW_BUTTON));
		ShowButton.setOnAction(e -> processEvent(SHOW_BUTTON));

		GridPane forthPanel = new GridPane();
		forthPanel.add(upperpane, 0, 0);
		forthPanel.add(SortButton = new Button(SORT_BUTTON), 0, 1);
		forthPanel.add(lowerpane, 1, 1);
		SortButton.setOnAction(e -> processEvent(SORT_BUTTON));
		return forthPanel;
	}

	// Initiate the view (Screen)
	@Override
	public void init() {
		PrimaryStage.show();
		PrimaryStage.setAlwaysOnTop(true);
		PrimaryStage.setResizable(false);
		First.fire();
		setMyColor(Colors.START);
		startColorTimer();
	}

	// Sets the Color (Depend on Control)
	@Override
	public void setMyColor(Colors myColor) {
		this.myColor = myColor;
	}

	// Color Timer
	@Override
	public void startColorTimer() {
		repaintTimer = new Timeline(new KeyFrame(Duration.millis(TIMER_DELAY_ONE_SECOND), e -> updateColor()));
		repaintTimer.setCycleCount(Timeline.INDEFINITE);
		repaintTimer.play();
	}

	// Method to Update the Colors of Contact Base on settled Color
	@Override
	public void updateColor() {
		TextFName.setTextFill(myColor.getColor());
		TextFName.setFont(Font.font(null, FontWeight.BOLD, 12));
		TextLName.setTextFill(myColor.getColor());
		TextLName.setFont(Font.font(null, FontWeight.BOLD, 12));
		TextPNumber.setTextFill(myColor.getColor());
		TextPNumber.setFont(Font.font(null, FontWeight.BOLD, 12));
	}

	// Register the events to listener Array
	@Override
	public void registerListener(ActionListener listener) {
		this.listeners.add(listener);

	}

	// Take's Command Name and make's Them Event same Name
	@Override
	public void processEvent(String command) {
		for (ActionListener listener : listeners)
			listener.actionPerformed(new ActionEvent(this, -1, command));

	}

	// Set's Clear TextLine
	@Override
	public void setClearText() {
		FirstName.setText(EMPTY_STRING);
		LastName.setText(EMPTY_STRING);
		PhoneNumber.setText(EMPTY_STRING);
	}

	// Upload's the Current Contact Display to the TextLine.
	@Override
	public void setContactText() {
		FirstName.setText(TextFName.getText());
		LastName.setText(TextLName.getText());
		PhoneNumber.setText(TextPNumber.getText());

	}

	// Set's The Loade'd Text After Receiving The LoadFile Info
	@Override
	public void setLoadText(String[] tmpString) {
		FirstName.setText(tmpString[1]);
		LastName.setText(tmpString[2]);
		PhoneNumber.setText(tmpString[3]);

	}

	// Update Button Disable enable as requested
	@Override
	public void allowButton(boolean Condition, boolean timerOrUpdate) {
		ButtonCreate.setDisable(!Condition);
		Previous.setDisable(!Condition);
		First.setDisable(!Condition);
		Next.setDisable(!Condition);
		Last.setDisable(!Condition);
		ButtonUpdate.setDisable(Condition);
		SortByBox.setDisable(!Condition);
		SortAscDescBox.setDisable(!Condition);
		SortAscDescForShowBox.setDisable(!Condition);
		SortBox.setDisable(!Condition);
		ShowButton.setDisable(!Condition);
		SortButton.setDisable(!Condition);
		LoadFile.setDisable(!Condition);
		Export.setDisable(!Condition);
		ComboBox.setDisable(!Condition);
		FilePathText.setDisable(!Condition);
		if (timerOrUpdate) {
			ButtonUpdate.setDisable(!Condition);
			if (timerOrUpdate && Condition) {
				ButtonUpdate.setDisable(Condition);
			}
			EditContact.setDisable(!Condition);
			FirstName.setDisable(!Condition);
			LastName.setDisable(!Condition);
			PhoneNumber.setDisable(!Condition);
		}

	}

	// Set's The Contact received from Controller
	@Override
	public void setContactInfo(String[] contact) {
		if (contact != null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TextFName.setText(contact[1].trim());
					TextLName.setText(contact[2].trim());
					TextPNumber.setText(contact[3].trim());
				}
			});
		}
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

	// Return what Type of Show Ascending or Descending
	@Override
	public int getShowIndex() {
		return SortAscDescForShowBox.getSelectionModel().getSelectedIndex();
	}

	// Clears the FilePathText
	@Override
	public void filePathClear() {
		FilePathText.setText(EMPTY_STRING);

	}

	// Return What to Load (Name,Type)
	@Override
	public String[] loadFile() {
		String[] tmpString = new String[2];
		tmpString[0] = ComboBox.getValue();
		tmpString[1] = FilePathText.getText().trim();
		return tmpString;
	}

	// Return What type of Export (Txt,Obj,Byte).
	@Override
	public String getExportIndex() {
		return ComboBox.getValue();
	}

	// Return the Indexe's of Fields (Sort/Count/Reverse)..
	@Override
	public int[] getFieldIndex() {
		int[] tmpIndex = new int[3];
		tmpIndex[0] = SortBox.getSelectionModel().getSelectedIndex();
		tmpIndex[1] = SortByBox.getSelectionModel().getSelectedIndex();
		tmpIndex[2] = SortAscDescBox.getSelectionModel().getSelectedIndex();
		return tmpIndex;
	}

}
