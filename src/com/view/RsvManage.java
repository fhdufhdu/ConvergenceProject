package com.view;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.format.*;
import java.sql.*;

import com.db.model.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;

public class RsvManage implements Initializable {
    private ObservableList<MemberDTO> member_id_list = FXCollections.observableArrayList();
    private ObservableList<MovieDTO> movie_title_list = FXCollections.observableArrayList();
    private ObservableList<TheaterDTO> theater_list = FXCollections.observableArrayList();

    private MemberDTO selectedMem;
    private MovieDTO selectedMov;
    private TheaterDTO selectedThea;


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
    private TextField tf_member_id;

    @FXML
    private ListView<MemberDTO> lv_member;

    @FXML
    private TextField tf_movie_name;

    @FXML
    private TextField tf_theater_name;

    @FXML
    private ListView<String> lv_movie;

    @FXML
    private ListView<String> lv_theater;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Thread t_mem = new Thread(() -> {
            Platform.runLater(() -> {
                try{
                    MemberDAO mDao = new MemberDAO();
                    Iterator<MemberDTO> m_iter = mDao.getAllMember().iterator();
                    while(m_iter.hasNext()){
                        member_id_list.add(m_iter.next());
                    }
                    lv_member.setItems(FXCollections.observableArrayList());
                    lv_member.getItems().addAll(member_id_list);
                    lv_member.setOnMouseClicked((MouseEvent)->{ 
                        selectedMem = lv_member.getSelectionModel().getSelectedItem();
                        lv_member.setMaxHeight(0);
                        lv_member.getItems().clear();
                        tf_member_id.setText(selectedMem.getId());
                    });
                    lv_member.setCellFactory(lv -> new ListCell<MemberDTO>() {
                        @Override
                        protected void updateItem(MemberDTO item, boolean empty) {
                            super.updateItem(item, empty);
                            setText(item == null ? null : item.getId());
                        }
                 });
                }catch(Exception e){
        
                }
            });
        });
        Thread t_mov = new Thread(() -> {
            Platform.runLater(() -> {
                try{
                    MovieDAO mDao = new MovieDAO();
                    Iterator<MovieDTO> m_iter = mDao.getAllMovieList().iterator();
                    while(m_iter.hasNext()){
                        movie_title_list.add(m_iter.next().getTitle());
                    }
                    lv_movie.setItems(FXCollections.observableArrayList());
                    lv_movie.getItems().addAll(movie_title_list);
                    lv_movie.setOnMouseClicked((MouseEvent)->{ 
                        lv_movie.setMaxHeight(0);
                        tf_movie_name.setText(lv_movie.getSelectionModel().getSelectedItem().toString());
                        lv_movie.getItems().clear();
                    });
                }catch(Exception e){
        
                }
            });
        });
        Thread t_thea = new Thread(() -> {
            Platform.runLater(() -> {
                try{
                    TheaterDAO tDao = new TheaterDAO();
                    Iterator<TheaterDTO> t_iter = tDao.getTheaterList().iterator();
                    while(t_iter.hasNext()){
                        theater_list.add(t_iter.next().getName());
                    }
                    lv_theater.setItems(FXCollections.observableArrayList());
                    lv_theater.getItems().addAll(theater_list);
                    lv_theater.setOnMouseClicked((MouseEvent)->{ 
                        lv_theater.setMaxHeight(0);
                        tf_theater_name.setText(lv_theater.getSelectionModel().getSelectedItem().toString());
                        lv_theater.getItems().clear();
                    });
                }catch(Exception e){
        
                }
            });
        });
        t_mem.start();
        t_mov.start();
        t_thea.start();
        try {
            t_mem.join();
            t_mov.join();
            t_thea.join();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    @FXML
    void clickedLvMember(MouseEvent event) {
        lv_member.setMaxHeight(0);
    }

    @FXML
    void typedTfMember(KeyEvent event) {
        if(tf_member_id.getText().equals("")){
            lv_member.getItems().clear();
            lv_member.setMaxHeight(0);
            return;
        }
        lv_member.setMaxHeight(130);  
        lv_member.setItems(FXCollections.observableArrayList());
        ObservableList<MemberDTO> temp_list = FXCollections.observableArrayList();
        for(int i = 0; i < member_id_list.size(); i++){
            if(member_id_list.get(i).getId().contains(tf_member_id.getText())){
                temp_list.add(member_id_list.get(i));
            }
        }
        lv_member.getItems().addAll(temp_list);
    }

    @FXML
    void clickedLvMovie(MouseEvent event) {
        lv_movie.setMaxHeight(0);
    }

    @FXML
    void typedTfMovie(KeyEvent event) {
        if(tf_movie_name.getText().equals("")){
            lv_movie.getItems().clear();
            lv_movie.setMaxHeight(0);
            return;
        }
        lv_movie.setMaxHeight(130);  
        lv_movie.setItems(FXCollections.observableArrayList());
        ObservableList<String> temp_list = FXCollections.observableArrayList();
        for(int i = 0; i < movie_title_list.size(); i++){
            if(movie_title_list.get(i).contains(tf_movie_name.getText())){
                temp_list.add(movie_title_list.get(i));
            }
        }
        lv_movie.getItems().addAll(temp_list);
    }

    @FXML
    void clickedLvTheater(MouseEvent event) {
        lv_theater.setMaxHeight(0);
    }

    @FXML
    void typedTfTheater(KeyEvent event) {
        if(tf_theater_name.getText().equals("")){
            lv_theater.getItems().clear();
            lv_theater.setMaxHeight(0);
            return;
        }
        lv_theater.setMaxHeight(130);
        lv_theater.setItems(FXCollections.observableArrayList());
        ObservableList<String> temp_list = FXCollections.observableArrayList();
        for(int i = 0; i < theater_list.size(); i++){
            if(theater_list.get(i).contains(tf_theater_name.getText())){
                temp_list.add(theater_list.get(i));
            }
        }
        lv_theater.getItems().addAll(temp_list);
    }

}
