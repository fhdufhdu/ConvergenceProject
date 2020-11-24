package com.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
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
	    private TableColumn<?, ?> tc_start_time;

	    @FXML
	    private TableColumn<?, ?> tc_end_time;

	    @FXML
	    private Text t_result;

	    @FXML
	    private Button btn_add_movie_table;

	    @FXML
	    private Button btn_change_movie_table;

	    @FXML
	    private Button btn_delete_movie_table;

	    @FXML
	    private Button btn_clear;

	    @FXML
	    private MenuButton mb_theater;

	    @FXML
	    private MenuButton mb_screen;

	    @FXML
	    private TextField tf_movie;

	    @FXML
	    private TextField tf_start_time;

	    @FXML
	    private TextField tf_end_time;

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
