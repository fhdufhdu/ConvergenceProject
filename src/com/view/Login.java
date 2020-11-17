package com.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.sun.javafx.sg.prism.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.db.model.*;
import com.main.mainGUI;
import com.protocol.Protocol;

public class Login
{
	@FXML
	private TextField tf_id;

	@FXML
	private PasswordField pf_passwd;

	@FXML
	private Button btn_login;

	@FXML
	private Button btn_signup;

	@FXML
	private Text t_result;

	@FXML
    void enter(KeyEvent event) throws Exception 
    {
        if (event.getCode().equals(KeyCode.ENTER)) 
        {
            loginTry();
        }
    }

    @FXML
    void login(ActionEvent event) throws Exception 
    {
        loginTry();
    }

	// 로그인 시도
    void loginTry() throws IOException 
    {
        try 
        {
			// gui에서 값 가져옴
			String id = tf_id.getText();
			String passwd = pf_passwd.getText();
            
            mainGUI.writePacket(Protocol.CS_REQ_LOGIN + "/" + id + "/" + passwd); 

            String packet = mainGUI.readLine();
            String packetArr[] = packet.split("/");
            String packetType = packetArr[0];

            if(packetType.equals(Protocol.SC_RES_LOGIN))
            {
                String result = packetArr[1];
                switch(result)
                {
                    case "1":
                    {
                        startWindow("./xml/admin_main.fxml", "관리자 모드");
                        return;
                    }
                    case "2":
                    {
                        startWindow("./xml/user_main.fxml", "시네마");
                        return;
                    }
                    case "3":
                    {
                        t_result.setText("로그인 실패!");
                        return;
                    }
                }
            }
        } 
        catch (Exception e) // 에러 발생시
		{
			e.printStackTrace();
			t_result.setText("로그인 실패!");
		}
	}

	// 회원 가입
	@FXML
    void signUp(ActionEvent event)
    {
        try 
        {
			// 회원 가입 버튼 누를 시 새로운 윈도우 출력
			Parent root = FXMLLoader.load(Login.class.getResource("./xml/sign_up.fxml"));
			Scene scene = new Scene(root, 600, 400);
			Stage primaryStage = (Stage) btn_login.getScene().getWindow();
			primaryStage.setTitle("회원가입");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
		}
    }
    
    private void startWindow(String path, String title) throws Exception
    {
        Parent root = FXMLLoader.load(Login.class.getResource(path));
        Scene scene = new Scene(root, 1000, 666);
        Stage primaryStage = (Stage) btn_login.getScene().getWindow();
        primaryStage.setTitle(title);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
