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


public class MovieAdd {

    @FXML
    private Text result;

    @FXML
    private TextField tf_title;

    @FXML
    private TextArea ta_actor;

    @FXML
    private TextField tf_director;

    @FXML
    private DatePicker dp_release_date;

    @FXML
    private TextArea ta_plot;

    @FXML
    private TextField tf_min;

    @FXML
    private Button btn_poster;

    @FXML
    private Button btn_still_cut;

    @FXML
    private Hyperlink hl_trailer;

    @FXML
    private Button btn_add_movie;

    @FXML
    void addMovie(ActionEvent event) 
    {
        /*DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        MovieDAO mDao = new MovieDAO();
        MovieDTO mDto = new MovieDTO(DTO.EMPTY_ID, tf_title.getText(), dateFormat.format(dp_release_date.getValue()), String is_current, String plot, String poster_path,
        String still_cut_path, String trailer_path, String director, String actor, int min)
        mDao.addMovie(new_mov);*/
    }

}
