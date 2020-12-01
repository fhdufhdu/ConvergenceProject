package com.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RsvCancel implements Initializable
{
    private ObservableList<MemberDTO> member_id_list;
    private ObservableList<MovieDTO> movie_title_list;
    private ObservableList<TheaterDTO> theater_list;
    private ObservableList<CustomDTO> custom_list;
    
    private MemberDTO selectedMem;
    private MovieDTO selectedMov;
    private TheaterDTO selectedThea;
    private CustomDTO selectedCustom;
    
    @FXML
    private TableView<CustomDTO> tv_reservation;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_movie;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_theater;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_screen;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_date;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_start_time;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_end_time;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_seat;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_price;
    
    @FXML
    private Button btn_cancel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            custom_list = FXCollections.observableArrayList();
            
            // 리스트 초기화
            initList();
            
            // 각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
            tc_movie.setCellValueFactory(cellData -> cellData.getValue().getMovie());
            tc_theater.setCellValueFactory(cellData -> cellData.getValue().getTheater());
            tc_screen.setCellValueFactory(cellData -> cellData.getValue().getScreen());
            tc_start_time.setCellValueFactory(cellData -> cellData.getValue().getStartTime());
            tc_end_time.setCellValueFactory(cellData -> cellData.getValue().getEndTime());
            tc_seat.setCellValueFactory(cellData -> cellData.getValue().getSeat());
            tc_price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
            
            // 테이블 뷰와 리스트를 연결
            tv_reservation.setItems(custom_list);
            
            // 테이블 뷰 row 선택 시 발생하는 이벤트 지정
            tv_reservation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomDTO>()
            {
                @Override
                public void changed(ObservableValue<? extends CustomDTO> observable, CustomDTO oldValue, CustomDTO newValue)
                {
                    selectedCustom = tv_reservation.getSelectionModel().getSelectedItem();
                }
            });
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    void getCancel(ActionEvent event) throws Exception
    {
        Connection conn = DAO.getConn();
        conn.setAutoCommit(false);
        Savepoint sp = conn.setSavepoint();
        try
        {
            if (tv_reservation.getSelectionModel().isEmpty())
            {
                mainGUI.alert("취소 오류", "취소할 데이터를 선택해주세요");
                return;
            }
            
            ButtonType btnType = mainGUI.confirm("취소 확인", "정말로 취소하시겠습니까?");
            if (btnType != ButtonType.OK)
            {
                return;
            }
            
            ReservationDAO rDao = new ReservationDAO();
            rDao.cancelRsv(selectedCustom.getRsv().getId());
            rDao.refund(selectedCustom.getRsv().getId());
            initList();
            conn.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            conn.rollback(sp);
            conn.setAutoCommit(true);
        }
        
    }
    
    private void initList()
    {
        try
        {
            custom_list.clear();
            ReservationDAO rDao = new ReservationDAO();
            
            ArrayList<ReservationDTO> r_list = rDao.getRsvListFromMem(Login.USER_ID);
            Iterator<ReservationDTO> r_iter = r_list.iterator();
            ArrayList<CustomDTO> c_list = new ArrayList<CustomDTO>();
            while (r_iter.hasNext())
            {
                ReservationDTO temp = r_iter.next();
                if (temp.getType().equals("2"))
                {
                    continue;
                }
                c_list.add(new CustomDTO(temp));
            }
            if (c_list.size() == 0)
            {
                mainGUI.alert("경고", "해당 예매 리스트가 없습니다.");
            }
            custom_list.addAll(c_list);
        }
        catch (DAOException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            mainGUI.alert("에러", "DB서버 문제발생");
        }
    }
    
    private class CustomDTO
    {
        ReservationDTO rDto;
        MemberDTO memDto;
        TimeTableDTO ttDto;
        MovieDTO movDto;
        ScreenDTO sDto;
        TheaterDTO tDto;
        
        public CustomDTO(ReservationDTO rDto) throws DAOException, SQLException
        {
            this.rDto = rDto;
            
            MemberDAO memDao = new MemberDAO();
            memDto = memDao.getMemberInfo(rDto.getMemberId());
            
            TimeTableDAO ttDao = new TimeTableDAO();
            ttDto = ttDao.getTimeTable(rDto.getTimeTableId());
            
            MovieDAO movDao = new MovieDAO();
            movDto = movDao.getMovie(ttDto.getMovieId());
            
            ScreenDAO sDao = new ScreenDAO();
            sDto = sDao.getScreenElem(ttDto.getScreenId());
            
            TheaterDAO tDao = new TheaterDAO();
            tDto = tDao.getTheaterElem(sDto.getTheaterId());
        }
        
        public StringProperty getMember()
        {
            return new SimpleStringProperty(memDto.getName());
        }
        
        public StringProperty getMovie()
        {
            return new SimpleStringProperty(movDto.getTitle());
        }
        
        public StringProperty getTheater()
        {
            return new SimpleStringProperty(tDto.getName());
        }
        
        public StringProperty getScreen()
        {
            return new SimpleStringProperty(sDto.getName());
        }
        
        public StringProperty getStartTime()
        {
            return new SimpleStringProperty(ttDto.getStartTime().toString());
        }
        
        public StringProperty getEndTime()
        {
            return new SimpleStringProperty(ttDto.getEndTime().toString());
        }
        
        public StringProperty getSeat()
        {
            return new SimpleStringProperty(Character.toString((char) (rDto.getScreenRow() + 65)) + Integer.toString(rDto.getScreenCol() + 1));
        }
        
        public StringProperty getPrice()
        {
            return new SimpleStringProperty(Integer.toString(rDto.getPrice()));
        }
        
        public ReservationDTO getRsv()
        {
            return rDto;
        }
    }
    
}
