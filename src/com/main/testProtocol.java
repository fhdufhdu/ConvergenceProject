package com.main;

import com.db.model.DAO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.protocol.movieClient.*;

public class testProtocol extends Application{
    public static void main(String args[]) throws Exception
    {
    	MovieClient.main(args);
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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}