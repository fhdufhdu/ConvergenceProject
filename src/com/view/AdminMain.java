package com.view;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.lang.Exception;

public class AdminMain 
{
    @FXML
    private MenuItem m_theater_add;

    @FXML
    private MenuItem m_theater_chanage;

    @FXML
    private MenuItem m_theater_list;

    @FXML
    private MenuItem m_screen_add;

    @FXML
    private MenuItem m_screen_change;

    @FXML
    private MenuItem m_screen_list;

    @FXML
    private MenuItem m_movie_add;

    @FXML
    private MenuItem m_movie_change;

    @FXML
    private MenuItem m_movie_list;

    @FXML
    private MenuItem m_timetable_add;

    @FXML
    private MenuItem m_timetable_change;

    @FXML
    private MenuItem m_timetable_list;

    @FXML
    private Menu m_admin_account;

    @FXML
    private BorderPane admin_main_view;

    @FXML
    void menuAdminAccount(ActionEvent event) 
    {

    }

    @FXML
    void menuMovieAdd(ActionEvent event) 
    {

    }

    @FXML
    void menuMovieChange(ActionEvent event) 
    {

    }

    @FXML
    void menuMovieList(ActionEvent event) 
    {

    }

    @FXML
    void menuScreenAdd(ActionEvent event) 
    {

    }

    @FXML
    void menuScreenChange(ActionEvent event) 
    {

    }

    @FXML
    void menuScreenList(ActionEvent event) 
    {

    }

    @FXML
    void menuTheaterAdd(ActionEvent event) 
    {
        loadPage("theater_add");
    }

    @FXML
    void menuTheaterChange(ActionEvent event) 
    {

    }

    @FXML
    void menuTheaterList(ActionEvent event) 
    {

    }

    @FXML
    void menuTimeTableAdd(ActionEvent event) 
    {

    }

    @FXML
    void menuTimeTableChange(ActionEvent event) 
    {

    }

    @FXML
    void menuTimeTableList(ActionEvent event) 
    {

    }


    private void loadPage(String file_name) 
    {
        try 
        {
            Parent root = FXMLLoader.load(AdminMain.class.getResource("./xml/admin_sub_page/"+file_name+".fxml"));
            admin_main_view.setCenter(root);
        } 
        catch (Exception e) 
        {
           
        }
    }
}

