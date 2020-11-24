package com.view;
//
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MovieSearch {

	   @FXML
	    private BorderPane bp_parent;

	    @FXML
	    private Button btn_search;

	    @FXML
	    private TableView<?> tv_movie;

	    @FXML
	    private TableColumn<?, ?> tc_screening;

	    @FXML
	    private TableColumn<?, ?> tc_movie_title;

	    @FXML
	    private TableColumn<?, ?> tc_start_date;

	    @FXML
	    private TableColumn<?, ?> tc_director;

	    @FXML
	    private TableColumn<?, ?> tc_actor;

	    @FXML
	    private TableColumn<?, ?> tc_screening_time;

	    @FXML
	    private TableColumn<?, ?> tc_plot;

	    @FXML
	    private TextField tf_title;

	    @FXML
	    private TextField tf_director;

	    @FXML
	    private TextField tf_actor;

	    @FXML
	    private DatePicker dp_start_date;

	    @FXML
	    private DatePicker dp_end_date;

	    @FXML
	    private CheckBox cb_close;

	    @FXML
	    private CheckBox cb_current;

	    @FXML
	    private CheckBox cb_soon;

	    @FXML
	    void getMovieSearch(ActionEvent event) {

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
