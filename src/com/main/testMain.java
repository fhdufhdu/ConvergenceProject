package com.main;

import com.db.model.DAO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testMain extends Application {
	public static void main(String args[]) throws Exception {
		DAO.connectDB();
		launch();
		DAO.closeDB();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(testMain.class.getResource("../view/xml/login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("로그인");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
