package com.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.lang.Exception;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import com.db.model.*;

public class TheaterAdd implements Initializable {

    private ObservableList<TheaterDTO> theater_list = FXCollections.observableArrayList();
    
    @FXML
    private ScrollPane scroll;

    @FXML
    private TableView<TheaterDTO> theater_table;

    @FXML
    private TableColumn<TheaterDTO, String> theater_name;

    @FXML
    private TableColumn<TheaterDTO, String> theater_address;

    @FXML
    private TableColumn<TheaterDTO, String> theater_total_screen;

    @FXML
    private TableColumn<TheaterDTO, String> theater_total_seat;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            scroll.setVvalue(0.0);

            TheaterDAO tDao = new TheaterDAO();
            ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
            Iterator<TheaterDTO> tIter = tlist.iterator();
            while(tIter.hasNext())
            {
                theater_list.add(tIter.next());
            }
            
            theater_name.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
            theater_address.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
            theater_total_screen.setCellValueFactory(cellData -> cellData.getValue().getTotalScreenProperty());
            theater_total_seat.setCellValueFactory(cellData -> cellData.getValue().getTotalSeatsProperty());

            theater_table.setItems(theater_list);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
