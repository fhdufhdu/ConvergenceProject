package com.main;

import java.io.*;
import java.net.*;
import com.protocol.Protocol;
//import com.db.model.DAO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testProtocol extends Application {
	private Socket socket;
	private static OutputStream os;
	private InputStream is;
	private Protocol protocol;
	private String currID;
	private String localHostAddress;
	byte[] buf;

	public testProtocol() {
		currID = null;
		try {
			localHostAddress = InetAddress.getLocalHost().getHostAddress();
			socket = new Socket(localHostAddress, 5000);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			protocol = new Protocol();
			buf = protocol.getPacket();
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
	
	public static void main(String args[]) throws Exception {
		launch();
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			testProtocol test = new testProtocol();
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