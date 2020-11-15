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

import com.db.model.*;

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

    void loginTry()
    {
        try 
        {
            String id = enter_id.getText();
            String passwd = enter_passwd.getText();
            DAO.connectDB();
            MemberDAO mDao = new MemberDAO();
            MemberDTO mem = mDao.getMember(id, passwd);

            String path;
            if(mem.getRole().equals("1"))
                path = "./xml/admin_main.fxml";
            else
                path = "./xml/user_main.fxml";
            Parent root = FXMLLoader.load(Login.class.getResource(path));
            Scene scene = new Scene(root, 1000, 666);
            Stage primaryStage = (Stage) login.getScene().getWindow();
            primaryStage.setTitle("관리자 모드");
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
        finally
        {
            DAO.closeDB();
        }
    }

    @FXML
    void signUp(ActionEvent event) 
    {

    }

}
