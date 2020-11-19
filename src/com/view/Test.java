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

public class Test implements Initializable
{
    
    @FXML
    private Text current_time;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        /*
         * Date date = new Date(); SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         * 
         * //SimpleDateFormat 을 이용해 String 타입으로 가져오기
         * 
         * current_time.setText(dateFormat.format(date));
         */
    }
    
    void initData(String a)
    {
        current_time.setText(a);
    }
    
}
