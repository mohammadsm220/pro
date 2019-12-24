package pro;

/**
 * @(#)MailMerge.java
 *
 * MailMerge application
 *
 * @author
 * @version 1.00 2010/5/15
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import jcifs.util.transport.Transport;

public class MailMerge {
	static String userName=null;
	static String password=null;
	static String subject= null;

	public static void main(String[] args) {

		//it will show Dialog so the user will enter he's userName and password and the subject of the email
		PasswordDialog pd = new PasswordDialog();
		Stage window = new Stage();
		pd.start(window);

		userName = PasswordDialog.login.getUserName();
		password = PasswordDialog.login.getPassword();
		subject= PasswordDialog.login.getSubject();

	}
	public static void sendsemil(String messageToBeSent,String toEmail) throws Exception
	{
		if(userName.contains("gmail")){
			SendFromGMail mailer = new SendFromGMail(userName, password , toEmail,subject, messageToBeSent);
		}
		if(userName.contains("yahoo")){
			SendFromYahoo mailer = new SendFromYahoo(userName, password , toEmail,subject, messageToBeSent);
		}
		else{
			SendEmailOffice365 mailer =  new SendEmailOffice365(userName, password , toEmail,subject, messageToBeSent);

			if (mailer.sendEmail())
			{
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Emile is send");
				alert.showAndWait();
			}
			else
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("email wasn't sent to "+toEmail);
				alert.showAndWait();
			}
		}

	}
	//it will call the mine method
	public static void mainCaller()
	{
		main(null);
	}
}
