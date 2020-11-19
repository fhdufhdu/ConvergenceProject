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

public class RsvAdd implements Initializable
{
    private ObservableList<TheaterDTO> theater_list;
    private ObservableList<ScreenDTO> screen_list;
    private ObservableList<MovieDTO> movie_list;
    
    private TheaterDTO selectedThea;
    private ScreenDTO selectedScreen;
    private MovieDTO selectedMovie;
    
    @FXML
    private Button btn_search_rsv;
    
    @FXML
    private TextField tf_theater;
    
    @FXML
    private ListView<TheaterDTO> lv_theater;
    
    @FXML
    private TextField tf_screen;
    
    @FXML
    private ListView<ScreenDTO> lv_screen;
    
    @FXML
    private TextField tf_movie;
    
    @FXML
    private ListView<MovieDTO> lv_movie;
    
    @FXML
    private DatePicker dp_start_date;
    
    @FXML
    private MenuButton mb_meridiem;
    
    @FXML
    private MenuButton mb_hours;
    
    @FXML
    private MenuButton mb_minutes;
    
    @FXML
    private DatePicker dp_movie;
    
    @FXML
    private TextField tf_end_time;
    
    @FXML
    private TextField tf_price;
    
    @FXML
    private Text result;
    
    @FXML
    private TextField tf_start_time;
    
    @FXML
    private TableView<?> rsv_table;
    
    @FXML
    private TextField tf_seat1;
    
    @FXML
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        try
        {
            Thread t_thea = new Thread(() ->
            {
                Platform.runLater(() ->
                {
                    try
                    {
                        theater_list = FXCollections.observableArrayList();
                        TheaterDAO tDao = new TheaterDAO();
                        Iterator<TheaterDTO> t_iter = tDao.getTheaterList().iterator();
                        while (t_iter.hasNext())
                        {
                            theater_list.add(t_iter.next());
                        }
                        lv_theater.setItems(FXCollections.observableArrayList());
                        lv_theater.getItems().addAll(theater_list);
                        lv_theater.setOnMouseClicked((MouseEvent) ->
                        {
                            selectedThea = lv_theater.getSelectionModel().getSelectedItem();
                            lv_theater.setMaxHeight(0);
                            lv_theater.getItems().clear();
                            tf_theater.setText(selectedThea.getName());
                        });
                        lv_theater.setCellFactory(lv -> new ListCell<TheaterDTO>()
                        {
                            @Override
                            protected void updateItem(TheaterDTO item, boolean empty)
                            {
                                super.updateItem(item, empty);
                                setText(item == null ? null : item.getName());
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                });
            });
            Thread t_mov = new Thread(() ->
            {
                Platform.runLater(() ->
                {
                    try
                    {
                        movie_list = FXCollections.observableArrayList();
                        MovieDAO mDao = new MovieDAO();
                        Iterator<MovieDTO> m_iter = mDao.getAllMovieList().iterator();
                        while (m_iter.hasNext())
                        {
                            movie_list.add(m_iter.next());
                        }
                        lv_movie.setItems(FXCollections.observableArrayList());
                        lv_movie.getItems().addAll(movie_list);
                        lv_movie.setOnMouseClicked((MouseEvent) ->
                        {
                            selectedMovie = lv_movie.getSelectionModel().getSelectedItem();
                            lv_movie.setMaxHeight(0);
                            lv_movie.getItems().clear();
                            tf_movie.setText(selectedMovie.getTitle());
                        });
                        lv_movie.setCellFactory(lv -> new ListCell<MovieDTO>()
                        {
                            @Override
                            protected void updateItem(MovieDTO item, boolean empty)
                            {
                                super.updateItem(item, empty);
                                setText(item == null ? null : item.getTitle());
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                });
            });
            
            t_thea.start();
            t_mov.start();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    void addMovie(ActionEvent event)
    {
        
    }
    
    @FXML
    void clickedLvMovie(MouseEvent event)
    {
        
    }
    
    @FXML
    void clickedLvScreen(MouseEvent event)
    {
        
    }
    
    @FXML
    void clickedLvTheater(MouseEvent event)
    {
        
    }
    
    @FXML
    void clickedTfMovie(MouseEvent event)
    {
        lv_movie.getItems().clear();
        lv_movie.getItems().addAll(movie_list);
        lv_movie.setMaxHeight(130);
    }
    
    @FXML
    void clickedTfScreen(MouseEvent event)
    {
        try
        {
            screen_list = FXCollections.observableArrayList();
            ScreenDAO sDao = new ScreenDAO();
            Iterator<ScreenDTO> m_iter = sDao.getScreenList(selectedThea.getId()).iterator();
            while (m_iter.hasNext())
            {
                screen_list.add(m_iter.next());
            }
            lv_screen.setItems(FXCollections.observableArrayList());
            lv_screen.getItems().addAll(screen_list);
            lv_screen.setOnMouseClicked((MouseEvent) ->
            {
                selectedScreen = lv_screen.getSelectionModel().getSelectedItem();
                lv_screen.setMaxHeight(0);
                lv_screen.getItems().clear();
                tf_screen.setText(selectedScreen.getName());
            });
            lv_screen.setCellFactory(lv -> new ListCell<ScreenDTO>()
            {
                @Override
                protected void updateItem(ScreenDTO item, boolean empty)
                {
                    super.updateItem(item, empty);
                    setText(item == null ? null : item.getName());
                }
            });
            
            // 필드에 값이 없다면 리스트뷰 감추기
            if (screen_list.size() == 0)
            {
                lv_screen.getItems().clear();
                lv_screen.setMaxHeight(0);
                return;
            }
            lv_screen.setMaxHeight(130);
            // 값이 있으면 리스트뷰 활성화
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    void clickedTfTheater(MouseEvent event)
    {
        lv_theater.getItems().clear();
        lv_theater.getItems().addAll(theater_list);
        lv_theater.setMaxHeight(130);
    }
    
    @FXML
    void searchRsv(ActionEvent event)
    {
        
    }
}
