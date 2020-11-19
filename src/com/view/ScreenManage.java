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
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;

public class ScreenManage
{
    private ObservableList<ScreenDTO> screen_list;
    private ScreenDTO table_row_data;
    private TheaterDTO theater;
    
    private String err_dao, err_type;
    
    @FXML
    private BorderPane bp_parent;
    
    @FXML
    private Text theater_name;
    
    @FXML
    private TableView<ScreenDTO> tv_screen;
    
    @FXML
    private TableColumn<ScreenDTO, String> tc_name;
    
    @FXML
    private TableColumn<ScreenDTO, String> tc_capacity;
    
    @FXML
    private TableColumn<ScreenDTO, String> tc_row;
    
    @FXML
    private TableColumn<ScreenDTO, String> tc_col;
    
    @FXML
    private Text t_result;
    
    @FXML
    private TextField tf_name;
    
    @FXML
    private TextField tf_capacity;
    
    @FXML
    private TextField tf_row;
    
    @FXML
    private TextField tf_col;
    
    @FXML
    private Button btn_add_screen;
    
    @FXML
    private Button btn_change_screen;
    
    @FXML
    private Button btn_delete_screen;
    
    @FXML
    private Button btn_clear;
    
    @FXML
    void addScreen(ActionEvent event)
    {
        try
        {
            ScreenDAO sDao = new ScreenDAO();
            ScreenDTO sDto = new ScreenDTO(DTO.EMPTY_ID, theater.getId(), tf_name.getText(), Integer.valueOf(tf_capacity.getText()), Integer.valueOf(tf_row.getText()), Integer.valueOf(tf_col.getText()));
            
            sDao.addScreen(sDto);
            
            // 값 추가 후 각 테이블 및 리스트 초기화
            screen_list.clear();
            tv_screen.getItems().clear();
            initList();
            tv_screen.setItems(screen_list);
            
            // text field 초기화
            clearText();
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText(err_dao);
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // DB관련 문제 발생시
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            // 입력값 타입이 맞지 않을때
            t_result.setText(err_type);
            e.printStackTrace();
        }
    }
    
    @FXML
    void changeScreen(ActionEvent event)
    {
        try
        {
            // 테이블 값이 선택되어 있는지 확인
            if (tv_screen.getSelectionModel().isEmpty())
            {
                alert("수정오류", "수정할 데이터를 선택해주세요");
                return;
            }
            ScreenDAO sDao = new ScreenDAO();
            ScreenDTO sDto = new ScreenDTO(table_row_data.getId(), theater.getId(), tf_name.getText(), Integer.valueOf(tf_capacity.getText()), Integer.valueOf(tf_row.getText()), Integer.valueOf(tf_col.getText()));
            
            sDao.changeScreen(sDto);
            
            screen_list.clear();
            tv_screen.getItems().clear();
            initList();
            tv_screen.setItems(screen_list);
            
            clearText();
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText(err_dao);
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // DB관련 문제 발생시
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            // 입력값 타입이 맞지 않을때
            t_result.setText(err_type);
            e.printStackTrace();
        }
    }
    
    @FXML
    void clearTextField(ActionEvent event)
    {
        clearText();
    }
    
    @FXML
    void deleteScreen(ActionEvent event)
    {
        try
        {
            if (tv_screen.getSelectionModel().isEmpty())
            {
                alert("삭제오류", "삭제할 데이터를 선택해주세요");
                return;
            }
            
            // 삭제할 것인지 재 확인
            ButtonType btnType = confirm("삭제확인", "정말로 삭제하시겠습니까?");
            if (btnType != ButtonType.OK)
            {
                return;
            }
            
            ScreenDAO sDao = new ScreenDAO();
            sDao.removeScreen(table_row_data.getId());
            t_result.setText("삭제되었습니다");
            
            screen_list.clear();
            tv_screen.getItems().clear();
            initList();
            tv_screen.setItems(screen_list);
            
            clearText();
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText(err_dao);
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // DB관련 문제 발생시
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            // 입력값 타입이 맞지 않을때
            t_result.setText(err_type);
            e.printStackTrace();
        }
    }
    
    // 리스트 초기화
    private void initList()
    {
        try
        {
            ScreenDAO tDao = new ScreenDAO();
            ArrayList<ScreenDTO> tlist = tDao.getScreenList(theater.getId());
            Iterator<ScreenDTO> tIter = tlist.iterator();
            while (tIter.hasNext())
            {
                screen_list.add(tIter.next());
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
    }
    
    // 이전 컨트롤러에서 값 받아오기, 상영관 관리에는 해당하는 영화관 객체가 필요
    void initData(TheaterDTO t)
    {
        try
        {
            err_dao = "상영관 이름이 중복됩니다!";
            err_type = "총 좌석, 최대 행, 최대 열에는 숫자만 입력해주세요!";
            
            theater = t;
            theater_name.setText(theater.getName() + "의 상영관 리스트");
            screen_list = FXCollections.observableArrayList();
            
            initList();
            
            tv_screen.getItems().clear();
            
            tc_name.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
            tc_capacity.setCellValueFactory(cellData -> cellData.getValue().getTotalCapacityProperty());
            tc_row.setCellValueFactory(cellData -> cellData.getValue().getMaxRowProperty());
            tc_col.setCellValueFactory(cellData -> cellData.getValue().getMaxColProperty());
            
            tv_screen.setItems(screen_list);
            
            tv_screen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ScreenDTO>()
            {
                @Override
                public void changed(ObservableValue<? extends ScreenDTO> observable, ScreenDTO oldValue, ScreenDTO newValue)
                {
                    table_row_data = tv_screen.getSelectionModel().getSelectedItem();
                    if (table_row_data != null)
                    {
                        tf_name.setText(table_row_data.getName());
                        tf_capacity.setText(Integer.toString(table_row_data.getTotalCapacity()));
                        tf_row.setText(Integer.toString(table_row_data.getMaxRow()));
                        tf_col.setText(Integer.toString(table_row_data.getMaxCol()));
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void clearText()
    {
        tf_name.clear();
        tf_capacity.clear();
        tf_row.clear();
        tf_col.clear();
        t_result.setText("");
    }
    
    private void alert(String head, String msg)
    {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("경고");
        alert.setHeaderText(head);
        alert.setContentText(msg);
        
        alert.showAndWait(); // Alert창 보여주기
    }
    
    private ButtonType confirm(String head, String msg)
    {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("확인");
        confirm.setHeaderText(head);
        confirm.setContentText(msg);
        return confirm.showAndWait().get();
        
    }
    
}
