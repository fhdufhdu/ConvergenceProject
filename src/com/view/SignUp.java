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

public class SignUp 
{

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

    //메뉴 선택시 외부 메뉴 이름 변경
    @FXML
    void setFemale(ActionEvent event) 
    {
        mb_gender.setText(mi_female.getText());
    }

    //메뉴 선택시 외부 메뉴 이름 변경
    @FXML
    void setMale(ActionEvent event) 
    {
        mb_gender.setText(mi_male.getText());
    }

    //회원가입 시도
    @FXML
    void trySignUp(ActionEvent event) 
    {
        try
        {
            String gender;
            //성별에 따라 gender 값 세팅
            if(mb_gender.getText().equals("남"))
            {
                gender = "1";
            }
            else
            {
                gender = "0";
            }
            //DatePicker의 값을 원하는 포맷으로 변경
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            MemberDAO mDAO = new MemberDAO();
            mDAO.addMember(new MemberDTO(tf_id.getText(), "2", pf_passwd.getText(), tf_account.getText(), tf_name.getText(), tf_phone.getText(), dateFormat.format(dp_birth.getValue()), gender));

            t_result.setText("회원가입 성공! 2초 후 로그인 화면으로 돌아갑니다!");

            //스레드사용으로 1.5초 후 로그인 페이지로 전환
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