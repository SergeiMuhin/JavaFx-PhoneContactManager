import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;

//Sergei
@SuppressWarnings("serial")
public class Contact implements IContact, Serializable {
	private int ID;
	private String FirstName, LastName, PhoneNumber;
	final static int SIZE = 10;
	final static int NUMBER_OF_OBJ = 4;
	final static int BYTE = 2;
	final static int TOTAL_SIZE = SIZE * NUMBER_OF_OBJ * BYTE;

	public Contact(int id, String firstName, String lastName, String phoneNumber) {
		this.ID = id;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.PhoneNumber = phoneNumber;
	}

	// Write Objects here.
	@Override
	public void writeObject(RandomAccessFile randomAccessFile) throws IOException {
		FixedLengthStringIO.writeFixedLengthString(String.valueOf(ID), SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(FirstName, SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(LastName, SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(PhoneNumber, SIZE, randomAccessFile);

	}

	@Override
	public void export(String format, File file) throws IOException {
		switch (format) {
		case "txt":
			PrintWriter pw = new PrintWriter(file);
			pw.println(ID);
			pw.println(FirstName);
			pw.println(LastName);
			pw.println(PhoneNumber);
			pw.close();
			break;
		case "obj.dat":
			FileOutputStream fileObjectOut = new FileOutputStream(file);
			ObjectOutputStream ObjectExport = new ObjectOutputStream(fileObjectOut);
			ObjectExport.writeObject(this);
			ObjectExport.close();
			break;
		case "byte.dat":
			FileOutputStream fileDataOut = new FileOutputStream(file);
			DataOutputStream DataExport = new DataOutputStream(fileDataOut);
			DataExport.writeInt(ID);
			DataExport.writeUTF(FirstName);
			DataExport.writeUTF(LastName);
			DataExport.writeUTF(PhoneNumber);
			DataExport.close();
			break;
		default:
			break;
		}

	}

	@Override
	public String[] getUiData() {
		String[] list = { String.valueOf(ID), FirstName, LastName, PhoneNumber };
		return list;
	}

	@Override
	public int getObjectSize() {
		return TOTAL_SIZE;
	}

}
