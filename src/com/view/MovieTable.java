package com.view;
//
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class MovieTable {

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
	    private Button btn_reservation;

	    @FXML
	    private TableView<?> tv_theater1;

	    @FXML
	    private TableColumn<?, ?> tc_theater1;

	    @FXML
	    private TableView<?> tv_screen1;

	    @FXML
	    private TableColumn<?, ?> tc_screen1;

	    @FXML
	    private TableView<?> tv_movie1;

	    @FXML
	    private TableColumn<?, ?> tc_movie1;

	    @FXML
	    private TableView<?> tv_date1;

	    @FXML
	    private TableColumn<?, ?> tc_date1;

	    @FXML
	    private TableView<?> tv_screen_time1;

	    @FXML
	    private TableColumn<?, ?> tc_screen_time1;

	    @FXML
	    void getReservation(ActionEvent event) {

	    }
	    
}
