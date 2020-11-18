package com.view;
//
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MoviePresentDetail {

	@FXML
    private TabPane tabpane_detail;

    @FXML
    private Tab tab_plot;

    @FXML
    private AnchorPane tab_plot1;

    @FXML
    private TextArea ta_plot;

    @FXML
    private Tab tab_review;

    @FXML
    private AnchorPane tab_review1;

    @FXML
    private TextField tf_review_score;

    @FXML
    private TextArea ta_review;

    @FXML
    private Button btn_add;

    @FXML
    private TableView<?> tv_review;

    @FXML
    private TableColumn<?, ?> tc_client;

    @FXML
    private TableColumn<?, ?> tc_review_score;

    @FXML
    private TableColumn<?, ?> tc_review_date;

    @FXML
    private TableColumn<?, ?> tc_review;

    @FXML
    private Button btn_image;

    @FXML
    private TableView<?> tv_movie;

    @FXML
    private TableColumn<?, ?> tc_title;

    @FXML
    private TableColumn<?, ?> tc_actor;

    @FXML
    private TableColumn<?, ?> tc_director;

    @FXML
    private TableColumn<?, ?> tc_start_date;

    @FXML
    private TableColumn<?, ?> tc_plot;

    @FXML
    private Hyperlink link_trailer;

    @FXML
    private Button btn_image2;

    @FXML
    private Button btn_image3;

    @FXML
    private Button btn_image4;

    @FXML
    void addReview(ActionEvent event) {

    }

    @FXML
    void getPosterPath(ActionEvent event) {

    }

    @FXML
    void getPosterPath2(ActionEvent event) {

    }

    @FXML
    void getPosterPath3(ActionEvent event) {

    }

    @FXML
    void getPosterPath4(ActionEvent event) {

    }

    @FXML
    void getTrailerPath(ActionEvent event) {

    }
    
}
