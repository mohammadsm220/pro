package pro;
import java.awt.Dialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.stage.FileChooser.ExtensionFilter;
//this is the MailController class which will show the second interface,
//set the ActionEvents to the Buttons and send the user info to MailMerge class to be send as an email.
public class MailController  {

	ArrayList<contacts> contactsArrayDisplay = new ArrayList<contacts>();
	ArrayList<String> placeholdersArrayDisplay = new ArrayList<String>();

	String template = "";

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<contacts> contactsTable;

	@FXML
	private Button createPDF;

	@FXML
	private Button sendEmail;

	@FXML
	private Button closeStage;

	public void initialize () {

		contactsArrayDisplay=MainScreenController.getContactsArray();
		placeholdersArrayDisplay=MainScreenController.getPlaceholdersArray();

		//this loop will add the Columns to the table view
		for(int i=0;i<placeholdersArrayDisplay.size();i++){
			String placeholder = placeholdersArrayDisplay.get(i);
			//to print the haders with out the [] and () and replace the _ to a space
			placeholder= placeholder.replace("]", "");
			placeholder=placeholder.replace("[", "");
			placeholder=placeholder.replace("(", "");
			placeholder=placeholder.replace(")", "");
			placeholder=placeholder.replace("_", " ");
			TableColumn  col = new TableColumn(placeholder);

			contactsTable.getColumns().addAll(col);

		}
		//this loop will add the rows to the table view
		for(int j=0;j<contactsArrayDisplay.size();j++){
			contactsTable.getItems().addAll(contactsArrayDisplay.get(j));
		}

	}

	//this will activate the createPDF Button
	@FXML
	private void createPDFAction(ActionEvent event){
		//it will show the fileChooser and get the path of the wanted file
		String fileLocation =null;
		String masseg=null;
		FileChooser fileChooser = new FileChooser();

		FileChooser extFilter = new FileChooser();
		extFilter.getExtensionFilters().addAll(
				new ExtensionFilter("PDF Files","*.pdf"));

		File seletedFile = extFilter.showSaveDialog(null);

		fileLocation= seletedFile.getAbsolutePath();

		try {
			OutputStream file = new FileOutputStream(new File(fileLocation));
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			document.open();

			//it will add the masseg to the document and add a new page to the pdf
			for(int i=0;i<MainScreenController.getContactsArray().size();i++){
				masseg = MainScreenController.formatTimplet(MainScreenController.getContactsArray().get(i));
				System.out.println(masseg);
				document.add(new Paragraph(masseg));
				document.newPage();
			}
			document.close();
			file.close();
		}catch (Exception e) {
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}

	}


	@FXML
	private void closeStageAction(ActionEvent event){
		Stage stage = (Stage) closeStage.getScene().getWindow();
		stage.close();

	}

	//it will send the masseg to the MailMerge.sendsemil which will do the rest
	@FXML
	private void sendEmailAction(ActionEvent event){

		String masseg;
		//it will activate the MailMerge mine method
		MailMerge.mainCaller();
		for(int i=0;i<MainScreenController.getContactsArray().size();i++){
			masseg = MainScreenController.formatTimplet(MainScreenController.getContactsArray().get(i));
			//it will send the masseg and the EMAIL_ADDRESS and it will get the EMAIL_ADDRESS from the method  getitem() that is in the contacts class
			try {
				MailMerge.sendsemil(masseg,contactsArrayDisplay.get(i).getitem("[[EMAIL_ADDRESS]]"));
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}

		}
	}


}
