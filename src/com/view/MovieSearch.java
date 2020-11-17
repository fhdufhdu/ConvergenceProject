package com.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MovieSearch {

	 @FXML
	    private TextField tf_title;

	    @FXML
	    private TextField tf_actor;

	    @FXML
	    private DatePicker datepick_start;

	    @FXML
	    private DatePicker datepick_end;

	    @FXML
	    private MenuButton menubtn_watch;

	    @FXML
	    private TextField tf_director;

	    @FXML
	    private Button btn_search;

	    @FXML
	    private TableView<?> tv_movie;

	    @FXML
	    private TableColumn<?, ?> tc_title;

	    @FXML
	    private TableColumn<?, ?> tc_start_date;

	    @FXML
	    private TableColumn<?, ?> tc_end_date;

	    @FXML
	    private TableColumn<?, ?> tc_watch;

	    @FXML
	    private TableColumn<?, ?> tc_director;

	    @FXML
	    private TableColumn<?, ?> tc_actor;

	    @FXML
	    void getMovieSearch(ActionEvent event) {

	    }
	    
}
