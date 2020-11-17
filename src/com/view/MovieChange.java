package com.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MovieChange {

	@FXML
    private CheckBox cb_current;

    @FXML
    private CheckBox cb_close;

    @FXML
    private CheckBox cb_soon;

    @FXML
    private TextField tf_title;

    @FXML
    private DatePicker dp_release_date;

    @FXML
    private TextField tf_director;

    @FXML
    private TextArea ta_actor;

    @FXML
    private TextField tf_min;

    @FXML
    private TextField tf_poster;

    @FXML
    private TextField tf_stillcut;

    @FXML
    private TextField tf_trailer;

    @FXML
    private TextArea ta_plot;

    @FXML
    private Text result;

    @FXML
    private Button btn_movie_change;

    @FXML
    void changeMovie(ActionEvent event) {

    }

    @FXML
    void getPosterPath(ActionEvent event) {

    }

    @FXML
    void getStillCutPath(ActionEvent event) {

    }

    @FXML
    void selectClose(ActionEvent event) {

    }

    @FXML
    void selectCurrent(ActionEvent event) {

    }

    @FXML
    void selectSoon(ActionEvent event) {

    }

    
}
