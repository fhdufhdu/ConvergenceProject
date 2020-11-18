package com.view;

import java.net.*;
import java.nio.file.Files;
import java.util.*;
import java.io.File;
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
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;

public class MovieAdd {
    private String is_current;

    @FXML
    private CheckBox cb_current;

    @FXML
    private CheckBox cb_close;

    @FXML
    private CheckBox cb_soon;

    @FXML
    private TextField tf_title;

    @FXML
    private DatePicker dp_release_date;

    @FXML
    private TextField tf_director;

    @FXML
    private TextArea ta_actor;

    @FXML
    private TextField tf_min;

    @FXML
    private TextField tf_poster;

    @FXML
    private TextField tf_stillcut;

    @FXML
    private TextField tf_trailer;

    @FXML
    private TextArea ta_plot;

    @FXML
    private Text result;

    @FXML
    void addMovie(ActionEvent event) {
        try {
            if (is_current == null || tf_title.getText().equals("") || dp_release_date.getValue() == null
                    || tf_director.getText().equals("") || ta_actor.getText().equals("") || tf_min.getText().equals("")
                    || tf_poster.getText().equals("") || tf_stillcut.getText().equals("")
                    || tf_trailer.getText().equals("") || ta_plot.getText().equals("")) {
                alert("입력오류", "모든 필드를 채워주세요!");
            }
            
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            MovieDAO mDao = new MovieDAO(); 
            MovieDTO mDto = new MovieDTO(DTO.EMPTY_ID,
            tf_title.getText(), dateFormat.format(dp_release_date.getValue()),
            is_current, ta_plot.getText(), tf_poster.getText(), tf_stillcut.getText(),
            tf_trailer.getText(), tf_director.getText(), ta_actor.getText(),
            Integer.valueOf(tf_min.getText())); 
            mDao.addMovie(mDto);
             
        } catch (NumberFormatException e) {
            alert("상영시간", "상영시간에는 숫자를 입력해주세요!");
        }catch (DAOException e) {
            alert("영화관 중복", "이미 존재하는 영화가 있습니다!");
        }catch (SQLException e) {
            alert("DB서버 연결오류", "잠시 후 다시 시도해주세요!");
        }
    }

    @FXML
    void selectClose(ActionEvent event) {
        cb_current.setSelected(false);
        cb_soon.setSelected(false);
        is_current = "0";
    }

    @FXML
    void selectCurrent(ActionEvent event) {
        cb_soon.setSelected(false);
        cb_close.setSelected(false);
        is_current = "1";
    }

    @FXML
    void selectSoon(ActionEvent event) {
        cb_current.setSelected(false);
        cb_close.setSelected(false);
        is_current = "2";
    }

    @FXML
    void getPosterPath(ActionEvent event) {
        tf_poster.setText(getFile().getPath());
    }

    @FXML
    void getStillCutPath(ActionEvent event) {
        tf_stillcut.setText(getFile().getPath());
    }

    private File getFile() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog((Stage) tf_poster.getScene().getWindow());

        return selectedFile;
    }

    private byte[] getFileByteArray(File selectedFile) {
        try {
            byte arr[] = Files.readAllBytes(selectedFile.toPath());
            return arr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void alert(String head, String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("경고");
        alert.setHeaderText(head);
        alert.setContentText(msg);

        alert.showAndWait(); // Alert창 보여주기
    }

}
