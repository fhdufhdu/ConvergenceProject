package com.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class MovieTableManage {

	  	@FXML
	    private BorderPane bp_parent;

	    @FXML
	    private TableView<?> tv_movie_table;

	    @FXML
	    private TableColumn<?, ?> tc_theater;

	    @FXML
	    private TableColumn<?, ?> tc_screen;

	    @FXML
	    private TableColumn<?, ?> tc_movie;

	    @FXML
	    private TableColumn<?, ?> tc_date;

	    @FXML
	    private TableColumn<?, ?> tc_screen_time;

	    @FXML
	    private Text t_result;

	    @FXML
	    private TextField tf_theater;

	    @FXML
	    private TextField tf_screen;

	    @FXML
	    private TextField tf_movie;

	    @FXML
	    private Button btn_add_movie_table;

	    @FXML
	    private Button btn_change_movie_table;

	    @FXML
	    private Button btn_delete_movie_table;

	    @FXML
	    private Button btn_clear;

	    @FXML
	    private TextField tf_scrren_time;

	    @FXML
	    private DatePicker dp_movie;

	    @FXML
	    void addMovieTable(ActionEvent event) {

	    }

	    @FXML
	    void changeMovieTable(ActionEvent event) {

	    }

	    @FXML
	    void clearTextField(ActionEvent event) {

	    }

	    @FXML
	    void deleteMovieTable(ActionEvent event) {

	    }
	    
}
