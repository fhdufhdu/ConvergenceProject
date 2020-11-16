package com.view;

import javafx.fxml.*;
import javafx.scene.text.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Test implements Initializable{

    @FXML
    private Text current_time;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //SimpleDateFormat 을 이용해 String 타입으로 가져오기 

        current_time.setText(dateFormat.format(date));
    }

    
}

