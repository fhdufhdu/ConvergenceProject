package com.view;
//
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Payment {

	 @FXML
	    private TableView<?> tv_reservation;

	    @FXML
	    private TableColumn<?, ?> tc_theater;

	    @FXML
	    private TableColumn<?, ?> tc_movie;

	    @FXML
	    private TableColumn<?, ?> tc_screen;

	    @FXML
	    private TableColumn<?, ?> tc_date;

	    @FXML
	    private TableColumn<?, ?> tc_movie_time;

	    @FXML
	    private TableColumn<?, ?> tc_seat;

	    @FXML
	    private TableColumn<?, ?> tc_price;

	    @FXML
	    private TextField tf_card_number;

	    @FXML
	    private TextField tf_card_number2;

	    @FXML
	    private TextField tf_card_number3;

	    @FXML
	    private TextField tf_card_number4;

	    @FXML
	    private Button btn_payment;

	    @FXML
	    void getPayment(ActionEvent event) {

	    }
	    
}
