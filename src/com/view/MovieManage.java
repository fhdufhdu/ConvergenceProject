package com.view;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class MovieManage {

	  @FXML
	    private BorderPane bp_parent;

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
	    private TableColumn<?, ?> tc_poster_path;

	    @FXML
	    private TableColumn<?, ?> tc_stillcut_path;

	    @FXML
	    private TableColumn<?, ?> tc_trailer_path;

	    @FXML
	    private Text t_result;

	    @FXML
	    private Button btn_next;

	    @FXML
	    void getNextAction(ActionEvent event) {

	    }
	    
}
