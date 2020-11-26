package com.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.db.model.AccountDAO;
import com.db.model.AccountDTO;
import com.db.model.DAOException;
import com.main.mainGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class AccountController implements Initializable
{
    
    @FXML
    private TextField tf_account;
    
    @FXML
    private TextField tf_bank;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        try
        {
            AccountDAO aDao = new AccountDAO();
            AccountDTO aDto = aDao.getAdminAccount("admin");
            
            tf_account.setPromptText(aDto.getAccount());
            tf_bank.setPromptText(aDto.getBank());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    void change(ActionEvent event)
    {
        try
        {
            if (tf_bank.getText().equals("") || tf_account.getText().equals(""))
            {
                mainGUI.alert("경고", "모든 데이터를 입력해주세요");
                return;
            }
            AccountDAO aDao = new AccountDAO();
            aDao.changeAccountInfo("admin", tf_bank.getText(), tf_account.getText());
            mainGUI.alert("수정 완료", "수정이 완료 되었습니다");
        }
        catch (DAOException e)
        {
            mainGUI.alert("오류", "해당 계좌번호가 존재하지 않습니다");
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            mainGUI.alert("오류", "DB 접속 오류");
            e.printStackTrace();
        }
    }
    
}
