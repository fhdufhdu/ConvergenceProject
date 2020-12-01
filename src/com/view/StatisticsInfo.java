package com.view;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.db.model.ReservationDAO;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class StatisticsInfo implements Initializable
{
    private ObservableList<String> benefit_list;
    private ObservableList<String> rsv_list;
    private ObservableList<String> cancel_list;
    
    private String start_date;
    private String end_date;
    
    @FXML
    private AnchorPane ap_statistics_main;
    
    @FXML
    private TableView<String> tv_benefit_rate;
    
    @FXML
    private TableColumn<String, String> tc_theater;
    
    @FXML
    private TableColumn<String, String> tc_benefit;
    
    @FXML
    private TableView<String> tv_rsv_rate;
    
    @FXML
    private TableColumn<String, String> tc_movie_rsv;
    
    @FXML
    private TableColumn<String, String> tc_rsv;
    
    @FXML
    private TableView<String> tv_cancel_rate;
    
    @FXML
    private TableColumn<String, String> tc_movie_cancel;
    
    @FXML
    private TableColumn<String, String> tc_cancel;
    
    @FXML
    private DatePicker dp_start;
    
    @FXML
    private DatePicker dp_end;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        try
        {
            dp_start.setValue(LocalDate.now());
            dp_end.setValue(LocalDate.now());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            start_date = dp_start.getValue().format(formatter) + " 00:00:00.0";
            end_date = dp_end.getValue().format(formatter) + " 23:59:00.0";
            
            benefit_list = FXCollections.observableArrayList();
            rsv_list = FXCollections.observableArrayList();
            cancel_list = FXCollections.observableArrayList();
            
            initList();
            
            tc_theater.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split("/")[0]));
            tc_benefit.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split("/")[1]));
            tv_benefit_rate.setItems(benefit_list);
            
            tc_movie_rsv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split("/")[0]));
            tc_rsv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split("/")[1]));
            tv_rsv_rate.setItems(rsv_list);
            
            tc_movie_cancel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split("/")[0]));
            tc_cancel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split("/")[1]));
            tv_cancel_rate.setItems(cancel_list);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    void selectEndDate(ActionEvent event)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        end_date = dp_end.getValue().format(formatter) + " 23:59:00.0";
    }
    
    @FXML
    void selectStartDate(ActionEvent event)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        start_date = dp_start.getValue().format(formatter) + " 00:00:00.0";
    }
    
    @FXML
    void search(ActionEvent event)
    {
        initList();
    }
    
    private void initList()
    {
        try
        {
            benefit_list.clear();
            rsv_list.clear();
            cancel_list.clear();
            ReservationDAO rDao = new ReservationDAO();
            benefit_list.addAll(rDao.getBenefitSatistics(start_date, end_date));
            rsv_list.addAll(rDao.getRsvSatistics(start_date, end_date));
            cancel_list.addAll(rDao.getCancelSatistics(start_date, end_date));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
}
