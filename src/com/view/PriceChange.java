package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.db.model.ChargeDAO;
import com.db.model.ChargeDTO;
import com.main.mainGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class PriceChange implements Initializable
{
    
    @FXML
    private TextField tf_morning;
    
    @FXML
    private TextField tf_afternoon;
    
    @FXML
    private TextField tf_night;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        try
        {
            ChargeDAO cDao = new ChargeDAO();
            ArrayList<ChargeDTO> cList = cDao.getChargeList();
            Iterator<ChargeDTO> cIter = cList.iterator();
            
            while (cIter.hasNext())
            {
                ChargeDTO temp = cIter.next();
                switch (temp.getType())
                {
                    case "1":
                    {
                        tf_morning.setText(Integer.toString(temp.getPrice()));
                        break;
                    }
                    case "2":
                    {
                        tf_afternoon.setText(Integer.toString(temp.getPrice()));
                        break;
                    }
                    case "3":
                    {
                        tf_night.setText(Integer.toString(temp.getPrice()));
                        break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    void changePrice(ActionEvent event)
    {
        try
        {
            if (tf_morning.getText().equals("") || tf_afternoon.getText().equals("") || tf_night.getText().equals(""))
            {
                mainGUI.alert("경고", "모든 데이터를 입력해주세요");
                return;
            }
            ChargeDAO cDao = new ChargeDAO();
            cDao.changeCharge(new ChargeDTO("1", Integer.valueOf(tf_morning.getText())));
            cDao.changeCharge(new ChargeDTO("2", Integer.valueOf(tf_afternoon.getText())));
            cDao.changeCharge(new ChargeDTO("3", Integer.valueOf(tf_night.getText())));
            
            mainGUI.alert("수정 완료", "데이터 수정 완료");
        }
        catch (Exception e)
        {
            mainGUI.alert("오류", "DB 접속 오류");
            e.printStackTrace();
        }
    }
    
}
