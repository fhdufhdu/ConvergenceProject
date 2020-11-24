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
	    private Text t_result;

	    @FXML
	    private TableView<?> tv_theater;

	    @FXML
	    private TableColumn<?, ?> tc_theater;

	    @FXML
	    private TableView<?> tv_screen;

	    @FXML
	    private TableColumn<?, ?> tc_screen;

	    @FXML
	    private TableView<?> tv_movie_table;

	    @FXML
	    private TableColumn<?, ?> tc_date;

	    @FXML
	    private TableColumn<?, ?> tc_screen_time;

	    @FXML
	    private Button btn_reservation;

	    @FXML
	    void getReservation(ActionEvent event) {

	    }
	    
}
