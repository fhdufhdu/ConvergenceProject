package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.db.model.ScreenDAO;
import com.db.model.ScreenDTO;
import com.db.model.TheaterDAO;
import com.db.model.TheaterDTO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class ScreenManage
{
    private ObservableList<ScreenDTO> screen_list;
    private ScreenDTO table_row_data;
    private TheaterDTO theater;

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

    }

    @FXML
    void changeScreen(ActionEvent event) 
    {

    }

    @FXML
    void clearTextField(ActionEvent event) 
    {

    }

    @FXML
    void deleteScreen(ActionEvent event) 
    {

    }

    //리스트 초기화
    private void initList()
    {
        try 
        {
            ScreenDAO tDao = new ScreenDAO();
            ArrayList<ScreenDTO> tlist = tDao.getScreenList(theater.getId());
            Iterator<ScreenDTO> tIter = tlist.iterator();
            while(tIter.hasNext())
            {
                screen_list.add(tIter.next());
            }
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
        }
    }


    //이전 컨트롤러에서 값 받아오기, 상영관 관리에는 해당하는 영화관 객체가 필요
    void initData(TheaterDTO t) 
    {
        try
        {
            theater = t;
            theater_name.setText(theater.getName()+"의 상영관 리스트");
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
                    if(table_row_data != null)
                    {
                        tf_name.setText(table_row_data.getName());
                        tf_capacity.setText(Integer.toString(table_row_data.getTotalCapacity()));
                        tf_row.setText(Integer.toString(table_row_data.getMaxRow()));
                        tf_col.setText(Integer.toString(table_row_data.getMaxCol()));
                    }
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
