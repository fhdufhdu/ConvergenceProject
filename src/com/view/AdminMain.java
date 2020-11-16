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

public class AdminMain
{
    
    @FXML
    private Menu m_theater;

    @FXML
    private MenuItem mi_movie_add;

    @FXML
    private MenuItem mi_movie_change;

    @FXML
    private MenuItem mi_movie_list;

    @FXML
    private MenuItem mi_timetable_add;

    @FXML
    private MenuItem mi_timetable_change;

    @FXML
    private MenuItem mi_timetable_list;

    @FXML
    private Menu m_admin_account;

    @FXML
    private BorderPane bp_admin_sub;

    @FXML
    void menuAdminAccount(ActionEvent event) {

    }

    @FXML
    void menuMovieAdd(ActionEvent event) {

    }

    @FXML
    void menuMovieChange(ActionEvent event) {

    }

    @FXML
    void menuMovieList(ActionEvent event) {

    }

    @FXML
    void menuTheaterManage(Event event) {
        loadPage("theater_manage");
    }
    
    @FXML
    void menuTheaterManage(ActionEvent event) {
        loadPage("theater_manage");
    }

    @FXML
    void menuTimeTableAdd(ActionEvent event) {

    }

    @FXML
    void menuTimeTableChange(ActionEvent event) {

    }

    @FXML
    void menuTimeTableList(ActionEvent event) {

    }

    //각 파일이름에 해당하는 뷰 불러오기
    private void loadPage(String file_name) 
    {
        try 
        {
            Parent root = FXMLLoader.load(AdminMain.class.getResource("./xml/admin_sub_page/"+file_name+".fxml"));
            bp_admin_sub.setCenter(root);
        } 
        catch (Exception e) 
        {
           e.printStackTrace();
        }
    }
}

