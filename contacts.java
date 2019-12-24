package pro;
import java.io.FileInputStream;
import java.util.ArrayList;
public class contacts {

	static String name;



	ArrayList<String> contactsArray = new ArrayList<String>();
	static ArrayList<String> placeholdersArray = new ArrayList<String>();

	public contacts(ArrayList<String> contact) {
		contactsArray=contact;
	}


	public static void placeholdersArray(ArrayList<String> placeholders) {
		placeholdersArray=placeholders;
	}
	public  ArrayList<String> getContactsArray() {
		return contactsArray;
	}

	public  void setContactsArray(ArrayList<String> contactsArray) {
		contactsArray = contactsArray;
	}
	public  String getitem(String item) {
		return contactsArray.get(placeholdersArray.indexOf(item));
	}
}
