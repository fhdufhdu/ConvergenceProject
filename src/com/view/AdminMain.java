package com.view;

import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.format.*;
import java.sql.*;

import com.db.model.*;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;

public class AdminMain implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
         * WebEngine webEngine = test_webview.getEngine();
         * 
         * webEngine.load(
         * "https://m.map.naver.com/search2/search.nhn?query="+"경북 구미시 인동 메가박스"
         * +"&sm=hty&style=v5#/map");
         */
        final double SPEED = 0.005;
        sp_admin_main.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * SPEED;
            sp_admin_main.setVvalue(sp_admin_main.getVvalue() - deltaY);
        });
    }

    @FXML
    private Menu m_theater;

    @FXML
    private MenuItem mi_movie_add;

    @FXML
    private MenuItem mi_movie_change;

    @FXML
    private MenuItem mi_movie_list;

    @FXML
    private MenuItem mi_timetable_add;

    @FXML
    private MenuItem mi_timetable_change;

    @FXML
    private MenuItem mi_timetable_list;

    @FXML
    private Menu m_admin_account;

    @FXML
    private BorderPane bp_admin_sub;

    @FXML
    private WebView test_webview;

    @FXML
    private ScrollPane sp_admin_main;

    @FXML
    void menuAdminAccount(ActionEvent event) {

    }

    @FXML
    void menuMovieAdd(ActionEvent event) {
        loadPage("movie_add");
    }

    @FXML
    void menuMovieChange(ActionEvent event) {
        loadPage("movie_manage");
    }

    @FXML
    void menuMovieList(ActionEvent event) {

    }

    @FXML
    void menuTheaterManage(Event event) {
        loadPage("theater_manage");
    }

    @FXML
    void menuTheaterManage(ActionEvent event) {
        loadPage("theater_manage");
    }

    @FXML
    void menuTimeTableAdd(ActionEvent event) {

    }

    @FXML
    void menuTimeTableChange(ActionEvent event) {

    }

    @FXML
    void menuTimeTableList(ActionEvent event) {

    }

    // 각 파일이름에 해당하는 뷰 불러오기
    private void loadPage(String file_name) {
        try {
            Parent root = FXMLLoader.load(AdminMain.class.getResource("./xml/admin_sub_page/" + file_name + ".fxml"));
            bp_admin_sub.setCenter(root);

            /*GridPane a = new GridPane();
            a.add(root, 1, 0);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
