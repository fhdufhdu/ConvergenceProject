package com.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReservationListChange {

	 @FXML
	    private TableView<?> tv_reservation;

	    @FXML
	    private TableColumn<?, ?> tc_user;

	    @FXML
	    private TableColumn<?, ?> tc_movie;

	    @FXML
	    private TableColumn<?, ?> tv_theater;

	    @FXML
	    private TableColumn<?, ?> tc_screen;

	    @FXML
	    private TableColumn<?, ?> tc_date;

	    @FXML
	    private TableColumn<?, ?> tc_start_time;

	    @FXML
	    private TableColumn<?, ?> tc_end_time;

	    @FXML
	    private TableColumn<?, ?> tc_seat;

	    @FXML
	    private TableColumn<?, ?> tc_price;

	    @FXML
	    private Button btn_change;

	    @FXML
	    void getChange(ActionEvent event) {

	    }
	    
}
