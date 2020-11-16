package com.main;

import java.io.*;
import java.net.*;
import com.protocol.Protocol;
//import com.db.model.DAO;
import com.protocol.movieServer.MovieDB;
import com.protocol.movieServer.MovieServer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testProtocol extends Application {
	private Socket socket;
	private String localHostAddress;
	private static OutputStream os;
	private static InputStream is;

	public testProtocol() throws Exception {
		try {
			localHostAddress = InetAddress.getLocalHost().getHostAddress();
			socket = new Socket(localHostAddress, 5000);
			os = socket.getOutputStream();
			is = socket.getInputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static OutputStream getOs() {
		return os;
	}

	public static InputStream getIs() {
		return is;
	}

	public static void main(String args[]) throws Exception {
		launch();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(testProtocol.class.getResource("../view/xml/login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("로그인ㅎㅎ");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}