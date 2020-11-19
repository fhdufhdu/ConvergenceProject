package com.view;

import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.format.*;
import java.sql.*;

import com.db.model.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

public class RsvManage implements Initializable
{
    private ObservableList<MemberDTO> member_id_list;
    private ObservableList<MovieDTO> movie_title_list;
    private ObservableList<TheaterDTO> theater_list;
    
    private ObservableList<CustomDTO> cus_list;
    
    private MemberDTO selectedMem;
    private MovieDTO selectedMov;
    private TheaterDTO selectedThea;
    private CustomDTO table_row_data;
    
    @FXML
    private TableView<CustomDTO> tv_reservation;
    
    @FXML
    private TableColumn<CustomDTO, String> tc_user;
    
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
    private TextField tf_member_id;
    
    @FXML
    private ListView<MemberDTO> lv_member;
    
    @FXML
    private TextField tf_movie_name;
    
    @FXML
    private TextField tf_theater_name;
    
    @FXML
    private ListView<MovieDTO> lv_movie;
    
    @FXML
    private ListView<TheaterDTO> lv_theater;
    
    @FXML
    private DatePicker dp_start_date;
    
    @FXML
    private Button btn_search;
    
    @FXML
    private DatePicker dp_end_date;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // 3개의 스레드로 리스트 받아오기 동시진행
        Thread t_mem = new Thread(() ->
        {
            Platform.runLater(() ->
            {
                try
                {
                    member_id_list = FXCollections.observableArrayList();
                    MemberDAO mDao = new MemberDAO();
                    Iterator<MemberDTO> m_iter = mDao.getAllMember().iterator();
                    while (m_iter.hasNext())
                    {
                        member_id_list.add(m_iter.next());
                    }
                    lv_member.setItems(FXCollections.observableArrayList());
                    lv_member.getItems().addAll(member_id_list);
                    lv_member.setOnMouseClicked((MouseEvent) ->
                    {
                        selectedMem = lv_member.getSelectionModel().getSelectedItem();
                        lv_member.setMaxHeight(0);
                        lv_member.getItems().clear();
                        tf_member_id.setText(selectedMem.getId());
                    });
                    lv_member.setCellFactory(lv -> new ListCell<MemberDTO>()
                    {
                        @Override
                        protected void updateItem(MemberDTO item, boolean empty)
                        {
                            super.updateItem(item, empty);
                            setText(item == null ? null : item.getId());
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
                    movie_title_list = FXCollections.observableArrayList();
                    MovieDAO mDao = new MovieDAO();
                    Iterator<MovieDTO> m_iter = mDao.getAllMovieList().iterator();
                    while (m_iter.hasNext())
                    {
                        movie_title_list.add(m_iter.next());
                    }
                    lv_movie.setItems(FXCollections.observableArrayList());
                    lv_movie.getItems().addAll(movie_title_list);
                    lv_movie.setOnMouseClicked((MouseEvent) ->
                    {
                        selectedMov = lv_movie.getSelectionModel().getSelectedItem();
                        lv_movie.setMaxHeight(0);
                        tf_movie_name.setText(selectedMov.getTitle());
                        lv_movie.getItems().clear();
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
                        tf_theater_name.setText(selectedThea.getName());
                        lv_theater.getItems().clear();
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
        t_mem.start();
        t_mov.start();
        t_thea.start();
        try
        {
            t_mem.join();
            t_mov.join();
            t_thea.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML // 사용자 리스트뷰 요소 선택시
    void clickedLvMember(MouseEvent event)
    {
        lv_member.setMaxHeight(0);
    }
    
    @FXML // 사용자 필드에 값 입력시
    void typedTfMember(KeyEvent event)
    {
        // 필드에 값이 없다면 리스트뷰 감추기
        if (tf_member_id.getText().equals(""))
        {
            lv_member.getItems().clear();
            lv_member.setMaxHeight(0);
            return;
        }
        
        // 값이 있으면 리스트뷰 활성화
        lv_member.setMaxHeight(130);
        lv_member.setItems(FXCollections.observableArrayList());
        ObservableList<MemberDTO> temp_list = FXCollections.observableArrayList();
        for (int i = 0; i < member_id_list.size(); i++)
        {
            // 모든 사용자 리스트에서 현재 입력한 값을 포함하는 요소가 있는지 확인
            if (member_id_list.get(i).getId().contains(tf_member_id.getText()))
            {
                temp_list.add(member_id_list.get(i));
            }
        }
        lv_member.getItems().addAll(temp_list);
    }
    
    @FXML // 영화 리스트뷰 요소 선택시
    void clickedLvMovie(MouseEvent event)
    {
        lv_movie.setMaxHeight(0);
    }
    
    @FXML // 영화 필드에 값 입력시
    void typedTfMovie(KeyEvent event)
    {
        // 필드에 값이 없다면 리스트뷰 감추기
        if (tf_movie_name.getText().equals(""))
        {
            lv_movie.getItems().clear();
            lv_movie.setMaxHeight(0);
            return;
        }
        
        // 값이 있으면 리스트뷰 활성화
        lv_movie.setMaxHeight(130);
        lv_movie.setItems(FXCollections.observableArrayList());
        ObservableList<MovieDTO> temp_list = FXCollections.observableArrayList();
        for (int i = 0; i < movie_title_list.size(); i++)
        {
            // 모든 사용자 리스트에서 현재 입력한 값을 포함하는 요소가 있는지 확인
            if (movie_title_list.get(i).getTitle().contains(tf_movie_name.getText()))
            {
                temp_list.add(movie_title_list.get(i));
            }
        }
        lv_movie.getItems().addAll(temp_list);
    }
    
    @FXML // 영화관 리스트뷰 요소 선택시
    void clickedLvTheater(MouseEvent event)
    {
        lv_theater.setMaxHeight(0);
    }
    
    @FXML // 영화관 필드에 값 입력시
    void typedTfTheater(KeyEvent event)
    {
        // 필드에 값이 없다면 리스트뷰 감추기
        if (tf_theater_name.getText().equals(""))
        {
            lv_theater.getItems().clear();
            lv_theater.setMaxHeight(0);
            return;
        }
        
        // 값이 있으면 리스트뷰 활성화
        lv_theater.setMaxHeight(130);
        lv_theater.setItems(FXCollections.observableArrayList());
        ObservableList<TheaterDTO> temp_list = FXCollections.observableArrayList();
        for (int i = 0; i < theater_list.size(); i++)
        {
            // 모든 사용자 리스트에서 현재 입력한 값을 포함하는 요소가 있는지 확인
            if (theater_list.get(i).getName().contains(tf_theater_name.getText()))
            {
                temp_list.add(theater_list.get(i));
            }
        }
        lv_theater.getItems().addAll(temp_list);
    }
    
    @FXML
    void getRsvSearch(ActionEvent event)
    {
        try
        {
            cus_list = FXCollections.observableArrayList();
            
            // 리스트 초기화
            initList();
            
            // 테이블 뷰 초기화
            tv_reservation.getItems().clear();
            
            // 각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
            tc_user.setCellValueFactory(cellData -> cellData.getValue().getMember());
            tc_movie.setCellValueFactory(cellData -> cellData.getValue().getMovie());
            tc_theater.setCellValueFactory(cellData -> cellData.getValue().getTheater());
            tc_screen.setCellValueFactory(cellData -> cellData.getValue().getScreen());
            tc_start_time.setCellValueFactory(cellData -> cellData.getValue().getStartTime());
            tc_end_time.setCellValueFactory(cellData -> cellData.getValue().getEndTime());
            tc_seat.setCellValueFactory(cellData -> cellData.getValue().getSeat());
            tc_price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
            
            // 테이블 뷰와 리스트를 연결
            tv_reservation.setItems(cus_list);
            
            // 테이블 뷰 row 선택 시 발생하는 이벤트 지정
            tv_reservation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomDTO>()
            {
                @Override
                public void changed(ObservableValue<? extends CustomDTO> observable, CustomDTO oldValue, CustomDTO newValue)
                {
                    table_row_data = tv_reservation.getSelectionModel().getSelectedItem();
                    System.out.println(table_row_data.getRsv().getId());
                }
            });
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
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            ReservationDAO rDao = new ReservationDAO();
            ArrayList<ReservationDTO> r_list = rDao.getRsvList(selectedMem.getId(), selectedMov.getId(), selectedThea.getId(), dateFormat.format(dp_start_date.getValue()) + " 00:00:00.0", dateFormat.format(dp_end_date.getValue()) + " 23:59:00.0");
            Iterator<ReservationDTO> r_iter = r_list.iterator();
            ArrayList<CustomDTO> c_list = new ArrayList<CustomDTO>();
            while (r_iter.hasNext())
            {
                c_list.add(new CustomDTO(r_iter.next()));
            }
            cus_list.addAll(c_list);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
