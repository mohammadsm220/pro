package pro;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

//this is the MainScreenController which will show the first interface,
//set the ActionEvents to the Buttons and will launch the second interface
public class MainScreenController {

	static ArrayList<contacts> contactsArray = new ArrayList<>();
	static ArrayList<String> placeholdersArray = new ArrayList<String>();
	static String textAreaValue = "";
	static String template;
	static ArrayList<contacts> getContactsArray() {
		return contactsArray;
	}
	static ArrayList<String> getPlaceholdersArray() {
		return placeholdersArray;
	}
	@FXML
	private Button loadTemplate;

	@FXML
	private Button starMailMergeProcess;

	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private MenuItem loadContact;

	@FXML
	private MenuItem clear;

	@FXML
	private MenuItem save;

	@FXML
	private  TextArea textArea;

	//this method will check a text is written from the KeyBord in the textArea and will save the text in textAreaValue
	@FXML
	private void textArea(KeyEvent event){
		textAreaValue = textArea.getText();
	}

	//this method will add the place Holders to the string pressing on the chose of the hader
	@FXML
	private void comboBox(ActionEvent event){
		String newTimplet=textArea.getText();
		String placeholder= comboBox.getValue();
		//to get the caret position
		int CaretPosition = textArea.getCaretPosition();
		//it will add the hader in the cart position
		newTimplet= newTimplet.substring(0,CaretPosition)+placeholder+newTimplet.substring(CaretPosition);
		textArea.setText(newTimplet);
		textAreaValue = textArea.getText();
	}

	//it will activate the save Button
	@FXML
	private void save(ActionEvent event){
		//it will show the fileChooser and get the path of the wanted file
		FileChooser fileChooser = new FileChooser();
		//Set extension filter
		FileChooser extFilter = new FileChooser();
		extFilter.getExtensionFilters().addAll(
				new ExtensionFilter("TXT Files","*.txt"));
		File seletedFile = extFilter.showSaveDialog(null);
		if(seletedFile != null){
			//it will send the text to the seletedFile method which will save the text in the path chosen
			saveFile(textArea.getText(), seletedFile);
		}
		else{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("file not found");
			alert.showAndWait();
		}
	}

	//it will activate the clear Button
	@FXML
	private void clear(ActionEvent event){
		textArea.clear();
	}

	//it will save File in the file chose
	private void saveFile(String content, File file){
		try {
			FileWriter fileWriter;
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException ex) {

		}

	}

	//it will show FileChooser and after it will print the chose template in to the textArea
	@FXML
	private void loadTemplateAction(ActionEvent event){
		BufferedReader br = null;
		String line;
		FileChooser fc = new FileChooser();
		//so that the FileChooser will not show then then TXT files
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("TXT Files","*.txt"));
		File seletedFile = fc.showOpenDialog(null);
		if(seletedFile != null){
			try {
				br = new BufferedReader(new FileReader(seletedFile.getAbsolutePath()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				while ((line = br.readLine()) != null) {
					textArea.appendText(line+"\n");
					textAreaValue = textArea.getText();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			template=textArea.getText();
		}
		else{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("file not found");
			alert.showAndWait();
		}
	}

	//it will load the contact from the CSV file
	@FXML
	private void loadContact(ActionEvent event){
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("CSV Files","*.csv"));
		File seletedFile = fc.showOpenDialog(null);

		if(seletedFile != null){
			FileInputStream filereed = null;
			Scanner filescan = null;
			try {
				filereed = new FileInputStream(seletedFile.getPath());
				filescan = new Scanner(filereed);
			} catch (FileNotFoundException e) {
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			}
			//the delimiter is used to split the text
			filescan.useDelimiter(",|\r\n");
			String row, placeHolders;
			row = filescan.nextLine();
			boolean x =true;
			int lengthofplaceholders=0;

			//this loop will add the place holders and will count how mine are their to use letter in the contact array
			while (x){
				//split
				placeHolders= row.substring(0,row.indexOf(","));
				row=row.substring(row.indexOf(",")+1);
				placeholdersArray.add(placeHolders);
				comboBox.getItems().add(placeholdersArray.get(lengthofplaceholders));

				if(row.indexOf(",")==-1){
					x=false;
				}
				lengthofplaceholders++;
			}
			contacts.placeholdersArray(placeholdersArray);

			//this loop will add the contacts and will send it to the contacts class
			while (filescan.hasNextLine()) {
				ArrayList<String> contact = new ArrayList<String>();

				for(int i=0;i<=lengthofplaceholders;i++){
					contact.add(filescan.next());
				}
				contacts c = new contacts(contact);

				contactsArray.add(c);
				filescan.nextLine();
			}
		}
		else{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("file not found");
			alert.showAndWait();
		}
	}

	//this method it will launch the second interface
	@FXML
	private void mailMergeProcessAction(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Mail_Merge_Process.fxml"));
		//		a.setController(mailController);
		Parent lv = loader.load();
		Scene listView = new Scene(lv);

		Stage window = new Stage();
		window.setScene(listView);
		window.initModality(Modality.APPLICATION_MODAL);
		window.show();
	}

	//it will change the  place holders in the text area and will but the contact info
	public static String formatTimplet (contacts contact){
		String newTimplet = null;
		newTimplet = textAreaValue;

		for(int i=0;i<placeholdersArray.size();i++)
			if(newTimplet.contains(placeholdersArray.get(i)))
				newTimplet = newTimplet.replace(placeholdersArray.get(i), contact.getContactsArray().get(i));

		return newTimplet;
	}

	@FXML
	void initialize() {
		comboBox.getItems().clear();
	}


}



