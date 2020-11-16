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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.db.model.*;
import com.main.testProtocol;
import com.protocol.Protocol;

public class Login {

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
   void login(ActionEvent event) throws Exception {
      loginTry();
   }

<<<<<<< HEAD
	// 로그인 시도
	void loginTry() throws IOException {
		try {
			// gui에서 값 가져옴
			String id = tf_id.getText();
			String passwd = pf_passwd.getText();
			boolean loginResult = false;

			while (true) {
				String packet = testProtocol.getBr().readLine();
				String packetArr[] = packet.split("/");
				String packetType = packetArr[0];

				switch (packetType) {
				case Protocol.SC_REQ_LOGIN:
					writePacket(Protocol.CS_REQ_LOGIN + "/" + id + "/" + passwd);
					break;

				case Protocol.SC_RES_LOGIN:
					String result = packetArr[1];
					if (result.equals("1") || result.equals("2")) {
						loginResult = true;

						// 사용자, 관리자 구분해서 실행할 xml파일 선택
						String path;
						String title;
						if (result.equals("1")) {
							path = "./xml/admin_main.fxml";
							title = "관리자 모드";
						} else {
							path = "./xml/user_main.fxml";
							title = "시네마";
						}

						// 로그인 성공시 새로운 window 표시
						Parent root = FXMLLoader.load(Login.class.getResource(path));
						Scene scene = new Scene(root, 1000, 666);
						Stage primaryStage = (Stage) btn_login.getScene().getWindow();
						primaryStage.setTitle(title);
						primaryStage.setResizable(false);
						primaryStage.setScene(scene);
						primaryStage.show();
					} else if (result.equals("3")) {
						t_result.setText("로그인 실패! 암호 오류!");
					} else if (result.equals("4")) {
						t_result.setText("로그인 실패! 아디디 존재 오류!");
					}
					break;
				}
				if (loginResult == true)
					break;
			}
		} catch (Exception e) // 에러 발생시
=======
   // 로그인 시도
    void loginTry() throws IOException 
    {
      try {
         // gui에서 값 가져옴
            String id = tf_id.getText();
            String passwd = pf_passwd.getText();
        
            MemberDAO mDao = new MemberDAO();
            MemberDTO mem = mDao.getMember(id, passwd);
            // 사용자, 관리자 구분해서 실행할 xml파일 선택
            String path;
            String title;
            if (mem.getRole().equals("1")) {
                path = "./xml/admin_main.fxml";
                title = "관리자 모드";
            } else {
                path = "./xml/user_main.fxml";
                title = "시네마";
            }

            // 로그인 성공시 새로운 window 표시
            Parent root = FXMLLoader.load(Login.class.getResource(path));
            Scene scene = new Scene(root, 1000, 666);
            Stage primaryStage = (Stage) btn_login.getScene().getWindow();
            primaryStage.setTitle(title);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) // 에러 발생시
>>>>>>> branch 'master' of https://github.com/fhdufhdu/ConvergenceProject
		{
			e.printStackTrace();
			t_result.setText("로그인 실패!");
		}
	}

	public void writePacket(String source) throws Exception {
		try {
			testProtocol.getBw().write(source);
			testProtocol.getBw().newLine();
			testProtocol.getBw().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원 가입
	@FXML
    void signUp(ActionEvent event)
    {
        try 
        {
			// 회원 가입 버튼 누를 시 새로운 윈도우 출력
			Parent root = FXMLLoader.load(Login.class.getResource("./xml/btn_sign_up.fxml"));
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

}
