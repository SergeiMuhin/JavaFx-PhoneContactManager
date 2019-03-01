
public class ContactCount {
	private IContact contact;
	private int contactCount;

	public ContactCount(IContact contact, int contactCount) {
		this.contact = contact;
		this.contactCount = contactCount;
	}

	public IContact getContact() {
		return contact;
	}

	public int getContactCount() {
		return contactCount;
	}

}
