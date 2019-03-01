import java.util.Comparator;

public class MyComparator implements Comparator<ContactCount> {
	private int firstLastPhone;
	private int ascDsc;
	private String fieldCount;

	public MyComparator(int firstLastPhone, int ascDsc, String fieldCount) {
		this.firstLastPhone = firstLastPhone;
		this.ascDsc = ascDsc;
		this.fieldCount = fieldCount;
	}

	@Override
	public int compare(ContactCount objectOne, ContactCount objectTwo) {
		String stringOne = objectOne.getContact().getUiData()[firstLastPhone];
		String stringTwo = objectTwo.getContact().getUiData()[firstLastPhone];
		int countOne = objectOne.getContactCount();
		int countTwo = objectTwo.getContactCount();
		int tmpOne;
		switch (ascDsc) {
		case (0):
			tmpOne = stringOne.compareTo(stringTwo);
			switch (fieldCount) {
			case ("Sort-field"):
				return tmpOne;
			case ("Sort-count"):
				if (countOne < countTwo && !stringOne.equals(stringTwo))
					return -1;
				else if (countOne == countTwo && stringOne.equals(stringTwo))
					return 0;
				else
					return 1;
			}
		case (1):
			// * - 1 reverse 1 is -1 -1 is 1 0 is 0.
			tmpOne = (-1) * stringOne.compareTo(stringTwo);
			switch (fieldCount) {
			case ("Sort-field"):
				return tmpOne;
			case ("Sort-count"):
				if (countOne < countTwo && !stringOne.equals(stringTwo))
					return 1;
				else if (countOne == countTwo && stringOne.equals(stringTwo))
					return 0;
				else
					return -1;
			}

		}
		return 0;
	}

}
