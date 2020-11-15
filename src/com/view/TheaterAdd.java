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
import java.sql.SQLException;
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

    @FXML
    private TextField enter_t_seat;

    @FXML
    private TextField enter_t_screen;

    @FXML
    private TextField enter_t_name;

    @FXML
    private TextField enter_t_address;

    @FXML
    private Button insert_theater_btn;

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

    @FXML
    void insertTheater(ActionEvent event) 
    {
        try
        {
            TheaterDAO tDao = new TheaterDAO();
            TheaterDTO tDto = new TheaterDTO(enter_t_name.getText(), enter_t_address.getText(), Integer.valueOf(enter_t_screen.getText()), Integer.valueOf(enter_t_seat.getText()));
                                    
            tDao.addTheater(tDto);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
        } 
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
