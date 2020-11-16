package com.view;

import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.format.*;
import java.sql.*;

import com.db.model.*;

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
        if(event.getCode().equals(KeyCode.ENTER))
        {
            loginTry();
        }
    }

    @FXML
    void login(ActionEvent event) throws Exception
    {
        loginTry();
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("./xml/test.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 666);
        Stage stage = (Stage) btn_login.getScene().getWindow();
        stage.setScene(scene);

        Test controller = loader.<Test>getController();
        controller.initData("1234");

        stage.show();*/
    }

    //로그인 시도
    void loginTry()
    {
        try 
        {
            //gui에서 값 가져옴
            String id = tf_id.getText();
            String passwd = pf_passwd.getText();
            
            MemberDAO mDao = new MemberDAO();
            MemberDTO mem = mDao.getMember(id, passwd);

            //사용자, 관리자 구분해서 실행할 xml파일 선택
            String path;
            String title;
            if(mem.getRole().equals("1"))
            {
                path = "./xml/admin_main.fxml";
                title = "관리자 모드";
            }
            else
            {
                path = "./xml/user_main.fxml";
                title = "시네마";
            }
            
            //로그인 성공시 새로운 window 표시
            Parent root = FXMLLoader.load(Login.class.getResource(path));
            Scene scene = new Scene(root, 1000, 666);
            Stage primaryStage = (Stage) btn_login.getScene().getWindow();
            primaryStage.setTitle(title);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e)     //에러 발생시
        {
            e.printStackTrace();
            t_result.setText("로그인 실패!");
        }
    }

    //회원 가입
    @FXML
    void signUp(ActionEvent event) 
    {
        try 
        {
            //회원 가입 버튼 누를 시 새로운 윈도우 출력
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
