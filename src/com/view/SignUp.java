package com.view;

import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.lang.Exception;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.db.model.*;

public class SignUp implements Initializable  {

    @FXML
    private TextField enter_id;

    @FXML
    private PasswordField enter_passwd;

    @FXML
    private TextField enter_name;

    @FXML
    private TextField enter_account;

    @FXML
    private TextField enter_phone;

    @FXML
    private MenuButton gender_menu;

    @FXML
    private MenuItem male;

    @FXML
    private MenuItem female;

    @FXML
    private Button btn_sign_up;

    @FXML
    private DatePicker enter_birth;
    
    @FXML
    private Text success;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    void setFemale(ActionEvent event) 
    {
        gender_menu.setText(female.getText());
    }

    @FXML
    void setMale(ActionEvent event) 
    {
        gender_menu.setText(male.getText());
    }

    @FXML
    void trySignUp(ActionEvent event) 
    {
        try
        {
            String gender;
            if(gender_menu.getText().equals("남"))
            {
                gender = "1";
            }
            else
            {
                gender = "0";
            }
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            MemberDAO mDAO = new MemberDAO();
            mDAO.addMember(new MemberDTO(enter_id.getText(), "2", enter_passwd.getText(), enter_account.getText(), enter_name.getText(), enter_phone.getText(), dateFormat.format(enter_birth.getValue()), gender));

            success.setText("회원가입 성공! 2초 후 로그인 화면으로 돌아갑니다!");

            new Thread(() -> {
                Platform.runLater(() -> { 
                    try  
                    {
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
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }

}