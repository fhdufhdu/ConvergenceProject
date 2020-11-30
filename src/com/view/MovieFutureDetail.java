package com.view;

//
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;


public class MovieFutureDetail
{
	@FXML
    private Button btn_reservation;

    @FXML
    private ImageView image_movie;

    @FXML
    private Text text_title;

    @FXML
    private Text text_open_date;

    @FXML
    private Text text_director;

    @FXML
    private Text text_actor;

    @FXML
    private TableView<?> tv_review;

    @FXML
    private TableColumn<?, ?> tc_reviewer;

    @FXML
    private TableColumn<?, ?> tc_review_score;

    @FXML
    private TableColumn<?, ?> tc_review_date;

    @FXML
    private TableColumn<?, ?> tc_review;

    @FXML
    private MenuButton mb_review;

    @FXML
    private TextField tf_review;

    @FXML
    private Button btn_review;

    @FXML
    private Text text_plot;

    @FXML
    private MediaView media_movie;

    @FXML
    private ImageView image_stillcut;

    @FXML
    private ImageView image_stillcut2;

    @FXML
    private ImageView image_stillcut3;

    @FXML
    void getReservation(ActionEvent event) {

    }

    @FXML
    void getReview(ActionEvent event) {

    }

}
