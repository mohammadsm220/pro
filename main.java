package pro;

import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class main extends Application{
	public static void main(String[] args) {
		Application.launch(args);
	}

	//this is the method that will link the fxmil code to MainScreenController so it will set the ActionEvents
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("mein.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	//this is the method that will link the fxmil code to MaillMergeprocess so it will set the ActionEvents
	public void start2 (Stage sacendStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Maill_Merge_process.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		sacendStage.setScene(scene);
		sacendStage.show();
	}

}
