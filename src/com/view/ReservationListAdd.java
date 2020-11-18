package com.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class ReservationListAdd {

	@FXML
    private TabPane tabpane_reservation;

    @FXML
    private Tab tab_theater;

    @FXML
    private AnchorPane tab_theater1;

    @FXML
    private TableView<?> tv_theater;

    @FXML
    private TableColumn<?, ?> tc_theater;

    @FXML
    private Tab tab_movie;

    @FXML
    private AnchorPane tab_movie1;

    @FXML
    private TableView<?> tv_movie;

    @FXML
    private TableColumn<?, ?> tc_movie;

    @FXML
    private Tab tab_screen;

    @FXML
    private AnchorPane tab_screen1;

    @FXML
    private TableView<?> tv_screen;

    @FXML
    private TableColumn<?, ?> tc_screen;

    @FXML
    private Tab tab_date_time;

    @FXML
    private AnchorPane tab_date_time1;

    @FXML
    private TableView<?> tv_screen_time;

    @FXML
    private TableColumn<?, ?> tc_start_time;

    @FXML
    private TableColumn<?, ?> tc_end_time;

    @FXML
    private DatePicker dt_movie;

    @FXML
    private Button btn_add;

    @FXML
    void getAddReservation(ActionEvent event) {

    }

}
