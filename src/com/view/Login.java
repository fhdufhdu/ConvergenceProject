package com.view;

import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.format.*;
import java.sql.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.db.model.*;
import com.main.testProtocol;
import com.protocol.Protocol;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;

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
	void enter(KeyEvent event) throws Exception {
		if (event.getCode().equals(KeyCode.ENTER)) {
			loginTry();
		}
	}

	@FXML
	void login(ActionEvent event) throws Exception {
		loginTry();
	}

	// 로그인 시도
	void loginTry() throws IOException {
		try {
			Protocol protocol = new Protocol();
			byte[] buf = protocol.getPacket();

			while (true) {
				testProtocol.getIs().read(buf);
				int packetType = buf[0];

				// gui에서 값 가져옴
				if(packetType != Protocol.SC_RES_LOGIN) {
					String id = tf_id.getText();
					String passwd = pf_passwd.getText();

					protocol = new Protocol(Protocol.CS_REQ_LOGIN);
					protocol.setId(id);
					protocol.setPassword(passwd);
					testProtocol.getOs().write(protocol.getPacket());
				}
				
				if (packetType == Protocol.SC_RES_LOGIN) {
					String result = protocol.getResult();
					if (result.equals("1")) {
						MemberDAO mDao = new MemberDAO();
						MemberDTO mem = mDao.getMember(protocol.getId(), protocol.getPassword());
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
						break;
					} else if (result.equals("2")) {
						t_result.setText("로그인 실패! 암호 틀림!");
					} else if (result.equals("3")) {
						t_result.setText("로그인 실패! 아이디 존재하지 않음!");
					}
				}
			}
		} catch (Exception e) // 에러 발생시
		{
			e.printStackTrace();
			t_result.setText("로그인 실패!");
		}
	}

	// 회원 가입
	@FXML
	void signUp(ActionEvent event) {
		try {
			// 회원 가입 버튼 누를 시 새로운 윈도우 출력
			Parent root = FXMLLoader.load(Login.class.getResource("./xml/btn_sign_up.fxml"));
			Scene scene = new Scene(root, 600, 400);
			Stage primaryStage = (Stage) btn_login.getScene().getWindow();
			primaryStage.setTitle("회원가입");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
