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
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.db.model.*;
import com.main.testProtocol;
import com.protocol.Protocol;

public class Login {

    @FXML
    private TextField enter_id;

    @FXML
    private PasswordField enter_passwd;

    @FXML
    private Button login;

    @FXML
    private Button sign_up;

    @FXML
    private Text status;

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
    }

    void loginTry() throws IOException
    {
        try 
        {
        	Protocol protocol = new Protocol(Protocol.CS_REQ_LOGIN);
//			byte[] buf = protocol.getPacket();
			protocol.setId(enter_id.getText());
			protocol.setPassword(enter_passwd.getText());
    		testProtocol.getOs().write(protocol.getPacket());
    		
            //gui에서 값 가져옴
            String id = enter_id.getText();
            String passwd = enter_passwd.getText();
            
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
            
            Parent root = FXMLLoader.load(Login.class.getResource(path));
            Scene scene = new Scene(root, 1000, 666);
            Stage primaryStage = (Stage) login.getScene().getWindow();
            primaryStage.setTitle(title);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            status.setText("");
            status.setText("로그인 실패!");
        }
    }

    @FXML
    void signUp(ActionEvent event) 
    {

    }

}
