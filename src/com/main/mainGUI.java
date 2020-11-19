package com.main;

import java.io.*;
import java.net.*;

import com.db.model.DAO;
import com.protocol.Protocol;
import com.protocol.movieServer.MovieServer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainGUI extends Application
{
	private static BufferedReader br;
	private static BufferedWriter bw;
	
	public static void main(String args[]) throws Exception
	{
		try
		{
			DAO.connectDB();
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			Socket socket = new Socket(localHostAddress, 5000);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			launch();
			bw.close();
			br.close();
			socket.close();
			DAO.connectDB();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			Parent root = FXMLLoader.load(mainGUI.class.getResource("../view/xml/login.fxml"));
			// Parent root = FXMLLoader.load(mainGUI.class.getResource("../view/xml/admin_sub_page/reservation_manage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("로그인");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static BufferedReader getBr()
	{
		return br;
	}
	
	public static BufferedWriter getBw()
	{
		return bw;
	}
	
	public static void writePacket(String source) throws Exception
	{
		try
		{
			bw.write(source + "\n");
			bw.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String readLine()
	{
		try
		{
			return br.readLine();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}