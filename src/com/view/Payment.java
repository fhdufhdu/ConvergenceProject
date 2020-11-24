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
    private TextField tf_bank;

    @FXML
    private TextField tf_account_num;

    @FXML
    private Button btn_payment;

    @FXML
    private TextField tf_secret_num;

    @FXML
    void getPayment(ActionEvent event) {

    }

}
