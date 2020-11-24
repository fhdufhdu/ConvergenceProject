package com.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReservationCancel {

	   @FXML
	    private TableView<?> tv_reservation;

	    @FXML
	    private TableColumn<?, ?> tc_movie;

	    @FXML
	    private TableColumn<?, ?> tc_theater;

	    @FXML
	    private TableColumn<?, ?> tc_screen;

	    @FXML
	    private TableColumn<?, ?> tc_date;

	    @FXML
	    private TableColumn<?, ?> tc_movie_time;

	    @FXML
	    private TableColumn<?, ?> tc_seat;

	    @FXML
	    private Button btn_cancel;

	    @FXML
	    void getCancel(ActionEvent event) {

	    }
    
}
