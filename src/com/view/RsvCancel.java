package com.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.db.model.DAO;
import com.db.model.DAOException;
import com.db.model.MemberDAO;
import com.db.model.MemberDTO;
import com.db.model.MovieDAO;
import com.db.model.MovieDTO;
import com.db.model.ReservationDAO;
import com.db.model.ReservationDTO;
import com.db.model.ScreenDAO;
import com.db.model.ScreenDTO;
import com.db.model.TheaterDAO;
import com.db.model.TheaterDTO;
import com.db.model.TimeTableDAO;
import com.db.model.TimeTableDTO;
import com.main.mainGUI;


import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.input.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class RsvCancel implements Initializable
{
    private ObservableList<MemberDTO> member_id_list;
    private ObservableList<MovieDTO> movie_title_list;
    private ObservableList<TheaterDTO> theater_list;
    private ObservableList<ReservationDTO> reservation_list;
    private ObservableList<CustomDTO> custom_list;
    
    private MemberDTO selectedMem;
    private MovieDTO selectedMov;
    private TheaterDTO selectedThea;
    private ReservationDTO table_row_data;
    private CustomDTO selectedCustom;
    
    @FXML
    private TableView<ReservationDTO> tv_reservation;
    
    @FXML
    private TableColumn<ReservationDTO, String> tc_movie;
    
    @FXML
    private TableColumn<ReservationDTO, String> tc_theater;
    
    @FXML
    private TableColumn<ReservationDTO, String> tc_screen;
    
    @FXML
    private TableColumn<ReservationDTO, String> tc_date;
    
    @FXML
    private TableColumn<ReservationDTO, String> tc_start_time;
    
    @FXML
    private TableColumn<ReservationDTO, String> tc_seat;
    
    @FXML
    private Button btn_cancel;
    @FXML
    private Text t_result;

  
  
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	try
    	{
    		
    	  reservation_list = FXCollections.observableArrayList();
    		
          //리스트 초기화
          initList();
    	  
          //테이블 뷰에 넣을 리스트 세팅
          reservation_list = FXCollections.observableArrayList();


          //테이블 뷰 초기화
          tv_reservation.getItems().clear();
          
          //각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
          tc_movie.setCellValueFactory(cellData -> cellData.getValue().getMovie());
          tc_theater.setCellValueFactory(cellData -> cellData.getValue().getTheater());
          tc_screen.setCellValueFactory(cellData -> cellData.getValue().getScreen());
          tc_start_time.setCellValueFactory(cellData -> cellData.getValue().getStartTime());
          tc_seat.setCellValueFactory(cellData -> cellData.getValue().getSeat());
          
          //테이블 뷰와 리스트를 연결
          tv_reservation.setItems(reservation_list);

          //테이블 뷰 row 선택 시 발생하는 이벤트 지정
          tv_reservation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ReservationDTO>() 
          {
              @Override
              public void changed(ObservableValue<? extends ReservationDTO> observable, ReservationDTO oldValue, ReservationDTO newValue) 
              {
                  table_row_data = tv_reservation.getSelectionModel().getSelectedItem();
                  
              }
          });
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
           
            

           
    @FXML  //예매정보 취소
  void cancelReservation(ActionEvent event) 
  {
      try
      {
          if (tv_reservation.getSelectionModel().isEmpty()) 
          {
              mainGUI.alert("삭제오류", "삭제할 데이터를 선택해주세요");
              return;
          }

          //취소할 것인지 재 확인
          ButtonType btnType = confirm("취소확인","정말로 취소하시겠습니까?");
          if(btnType != ButtonType.OK) 
          {
              return;
          }

          ReservationDAO tDao = new ReservationDAO();                  
          tDao.cancelReservation(table_row_data.getMovie());
          t_result.setText("취소되었습니다");

          reservation_list.clear();
          tv_reservation.getItems().clear();
          initList();
          tv_reservation.setItems(reservation_list);
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
  }
    
    private void initList()
  {
      try 
      {
          TheaterDAO tDao = new TheaterDAO();
          ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
          Iterator<TheaterDTO> tIter = tlist.iterator();
          while(tIter.hasNext())
          {
              theater_list.add(tIter.next());
          }
      } 
      catch (Exception e) 
      {
          //TODO: handle exception
      }	
  }

    private class CustomDTO
    {
        TheaterDTO theater;
        ScreenDTO screen;
        MovieDTO movie;
        TimeTableDTO timetable;
        
        public CustomDTO(TimeTableDTO timetable) throws DAOException, SQLException
        {
            this.timetable = timetable;
            
            MovieDAO movDao = new MovieDAO();
            movie = movDao.getMovie(timetable.getMovieId());
            
            ScreenDAO sDao = new ScreenDAO();
            screen = sDao.getScreenElem(timetable.getScreenId());
            
            TheaterDAO tDao = new TheaterDAO();
            theater = tDao.getTheaterElem(screen.getTheaterId());
            
            
        }
        
        
            
    public StringProperty getMovie()
    {
        return new SimpleStringProperty(movie.getTitle());
    }
    
    public StringProperty getTheater()
    {
        return new SimpleStringProperty(theater.getName());
    }
    
    public StringProperty getScreen()
    {
        return new SimpleStringProperty(screen.getName());
    }
    
    public StringProperty getStartTime()
    {
        return new SimpleStringProperty(timetable.getStartTime().toString());
    }
    
    public StringProperty getSeat()
    {
        return new SimpleStringProperty(Character.toString((char) (rDto.getScreenRow() + 65)) + Integer.toString(rDto.getScreenCol() + 1));
    }
    
    }
        
    }
}