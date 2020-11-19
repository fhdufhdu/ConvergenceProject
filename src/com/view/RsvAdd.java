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

public class RsvAdd implements Initializable
{
    @FXML
    private DatePicker dp_movie;
    
    @FXML
    private TextField tf_end_time;
    
    @FXML
    private TextField tf_price;
    
    @FXML
    private Text result;
    
    @FXML
    private TextField tf_start_time;
    
    @FXML
    private TextField tf_seat1;
    
    @FXML
    private MenuButton mb_select_theater;
    
    @FXML
    private MenuButton mb_select_screen;
    
    @FXML
    private Button btn_search_rsv;
    
    @FXML
    private MenuButton mb_select_movie;
    
    @FXML
    private MenuButton mb_start_time;
    
    @FXML
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        // mb_select_movie.
    }
    
    @FXML
    void addMovie(ActionEvent event)
    {
        
    }
    
}
