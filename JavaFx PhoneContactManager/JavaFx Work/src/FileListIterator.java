import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ListIterator;

public class FileListIterator<T extends IContact> implements ListIterator<T> {
	private RandomAccessFile raf;
	private final int ZERO = 0;
	private final int BYTE = 2;
	private int OBJECT_SIZE;
	private int SIZE_OBJECT;

	// simple approach
	@SuppressWarnings("unchecked")
	private T initContactWorkaround(int id, String firstName, String lastName, String phoneNumber) {
		return (T) new Contact(id, firstName, lastName, phoneNumber);
	}

	public FileListIterator(String fileName, int objectSize, int sizeObject) {
		File file = new File(fileName);
		this.OBJECT_SIZE = objectSize;
		this.SIZE_OBJECT = sizeObject;
		try {
			raf = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void add(T e) {
		try {
			long currentLocation = raf.getFilePointer();
			raf.seek(raf.length());
			e.writeObject(raf);
			raf.seek(currentLocation);
		} catch (IOException eXp) {
			eXp.printStackTrace();
		}
	}

	@Override
	public boolean hasNext() {
		try {
			if (raf.length() <= raf.getFilePointer() || raf.length() == ZERO) {
				return false;
			} else
				return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public boolean hasPrevious() {
		try {
			if (raf.getFilePointer() - OBJECT_SIZE <= ZERO || raf.length() == ZERO) {
				return false;
			} else
				return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public T next() {
		try {
			if (hasNext())
				return mainRead(raf.getFilePointer());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mainRead(previousLocation());
	}

	@Override
	public T previous() {
		try {
			long setLocation = raf.getFilePointer() - BYTE * OBJECT_SIZE;
			if (hasPrevious()) {
				raf.seek(setLocation);
				return mainRead(setLocation);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mainRead(previousLocation());
	}

	@Override
	public int nextIndex() {
		return ZERO;
	}

	@Override
	public int previousIndex() {
		return ZERO;

	}

	private int previousLocation() {
		try {
			long tmpLocation = raf.getFilePointer();
			if (tmpLocation - OBJECT_SIZE <= 0) {
				return 0;
			} else {
				return (int) tmpLocation - OBJECT_SIZE;
			}
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public void remove() {
		try {
			raf.setLength(raf.getFilePointer());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void set(T e) {
		try {
			raf.seek(previousLocation());
			e.writeObject(raf);
		} catch (IOException eXc) {
			eXc.printStackTrace();
		}
	}

	private T mainRead(long location) {
		try {
			raf.seek(location);
			return initContactWorkaround(
					Integer.valueOf(FixedLengthStringIO.readFixedLengthString(SIZE_OBJECT, raf).trim()),
					FixedLengthStringIO.readFixedLengthString(SIZE_OBJECT, raf),
					FixedLengthStringIO.readFixedLengthString(SIZE_OBJECT, raf),
					FixedLengthStringIO.readFixedLengthString(SIZE_OBJECT, raf));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
