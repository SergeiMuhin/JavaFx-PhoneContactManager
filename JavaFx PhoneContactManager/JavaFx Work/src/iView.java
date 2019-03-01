import java.awt.event.ActionListener;

public interface iView {

	/** Initiate the view (Screen) **/
	public void init();

	/** Register the events to listener Array **/
	void registerListener(ActionListener listener);

	/** Take's Command Name and make's Them Event same Name **/
	void processEvent(String command);

	/** Set's Clear TextLine **/
	void setClearText();

	/** Upload's the Current Contact Display to the TextLine. **/
	void setContactText();

	/** Set's The Loade'd Text After Receiving The LoadFile Info **/
	void setLoadText(String[] tmpString);

	/** Update Button Disable enable as requested **/
	void allowButton(boolean Condition, boolean timerOrUpdate);

	/** Set's The Contact received from Controller **/
	void setContactInfo(String[] contact);

	/** Return Text From Texts line. **/
	String[] getText();

	/** Return what Type of Show Ascending or Descending **/
	int getShowIndex();

	/** Clears the FilePathText **/
	void filePathClear();

	/** Return What to Load (Name,Type) **/
	String[] loadFile();

	/** Return What type of Export (Txt,Obj,Byte). **/
	String getExportIndex();

	/** Return the Indexe's of Fields (Sort/Count/Reverse).. **/
	int[] getFieldIndex();

	/** Sets the Color (Depend on Control) **/
	void setMyColor(Colors myColor);

	/** Color Timer **/
	void startColorTimer();

	/** Method to Update the Colors of Contact Base on settled Color **/
	public void updateColor();

}
