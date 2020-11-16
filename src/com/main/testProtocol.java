package com.main;

import java.io.*;
import java.net.*;

import com.db.model.DAO;
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
	private static BufferedReader br;
	private static BufferedWriter bw;

	public testProtocol() throws Exception {
		try {
			localHostAddress = InetAddress.getLocalHost().getHostAddress();
			socket = new Socket(localHostAddress, 5000);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		DAO.connectDB();
		launch();
		DAO.closeDB();
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
	
	public static BufferedReader getBr() {
		return br;
	}

	public static BufferedWriter getBw() {
		return bw;
	}
}