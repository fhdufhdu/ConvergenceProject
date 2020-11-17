package com.view;

import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.format.*;
import java.sql.*;

import com.db.model.*;
import com.main.mainGUI;
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

import com.db.model.*;

public class SignUp {

	@FXML
	private TextField tf_id;

	@FXML
	private PasswordField pf_passwd;

	@FXML
	private TextField tf_name;

	@FXML
	private TextField tf_account;

	@FXML
	private TextField tf_phone;

	@FXML
	private MenuButton mb_gender;

	@FXML
	private MenuItem mi_male;

	@FXML
	private MenuItem mi_female;

	@FXML
	private Button btn_sign_up;

	@FXML
	private DatePicker dp_birth;

	@FXML
	private Text t_result;

	// 메뉴 선택시 외부 메뉴 이름 변경
	@FXML
	void setFemale(ActionEvent event) {
		mb_gender.setText(mi_female.getText());
	}

	// 메뉴 선택시 외부 메뉴 이름 변경
	@FXML
	void setMale(ActionEvent event) {
		mb_gender.setText(mi_male.getText());
	}

	// 회원가입 시도
	@FXML
	void trySignUp(ActionEvent event) {
		try {
			t_result.setText("");
			String id = tf_id.getText();
			String passwd = pf_passwd.getText();
			String account = tf_account.getText();
			String name = tf_name.getText();
			String phone_number = tf_phone.getText();
			String birth = dp_birth.getValue().toString();
			String gender;
			// 성별에 따라 gender 값 세팅
			if (mb_gender.getText().equals("남")) {
				gender = "1";
			} else {
				gender = "0";
			}

			mainGUI.writePacket(Protocol.CS_REQ_SIGNUP + "/2/" + id + "/" + passwd + "/" + account + "/" + name + "/"
					+ phone_number + "/" + birth + "/" + gender);

			String packet = mainGUI.readLine();
			String packetArr[] = packet.split("/");
			String packetType = packetArr[0];

			if (packetType.equals(Protocol.SC_RES_SIGNUP)) {
				String result = packetArr[1];
				switch (result) {
					case "1":
						t_result.setText("회원가입 성공! 2초 후 로그인 화면으로 돌아갑니다!");

						// 스레드사용으로 1.5초 후 로그인 페이지로 전환
						new Thread(() -> {
							Platform.runLater(() -> {
								try {
									Thread.sleep(1500);
									Parent root = FXMLLoader.load(SignUp.class.getResource("../view/xml/login.fxml"));
									Scene scene = new Scene(root, 600, 400);
									Stage primaryStage = (Stage) btn_sign_up.getScene().getWindow();
									primaryStage.setTitle("로그인");
									primaryStage.setResizable(false);
									primaryStage.setScene(scene);
									primaryStage.show();
								} catch (Exception e) {
									e.printStackTrace();
								}
							});
						}).start();
						return;
					case "2":
						t_result.setText("회원가입 실패!");
						return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}