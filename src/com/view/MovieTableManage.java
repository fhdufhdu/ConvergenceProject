package com.view;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.db.model.DAOException;
import com.db.model.DTO;
import com.db.model.MemberDTO;
import com.db.model.MovieDAO;
import com.db.model.MovieDTO;
import com.db.model.ScreenDAO;
import com.db.model.ScreenDTO;
import com.db.model.TheaterDAO;
import com.db.model.TheaterDTO;
import com.db.model.TimeTableDAO;
import com.db.model.TimeTableDTO;
import com.main.mainGUI;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MovieTableManage implements Initializable
{
	private ObservableList<TheaterDTO> theater_list;
	private ObservableList<ScreenDTO> screen_list;
	private ObservableList<MovieDTO> movie_list;
	private ObservableList<MemberDTO> member_list;
	
	private ObservableList<CustomDTO> custom_list;
	
	private ArrayList<Integer> row_list;
	private ArrayList<Integer> col_list;
	
	private TheaterDTO selectedThea;
	private ScreenDTO selectedScreen;
	private MovieDTO selectedMovie;
	private MemberDTO selectedMember;
	private CustomDTO selectedCustom;
	
	@FXML
	private ListView<TheaterDTO> lv_theater;
	
	@FXML
	private ListView<ScreenDTO> lv_screen;
	
	@FXML
	private ListView<MovieDTO> lv_movie;
	
	@FXML
	private ListView<MemberDTO> lv_member;
	
	@FXML
	private TableView<CustomDTO> tv_timetable;
	
	@FXML
	private TableColumn<CustomDTO, String> tc_theater;
	
	@FXML
	private TableColumn<CustomDTO, String> tc_screen;
	
	@FXML
	private TableColumn<CustomDTO, String> tc_movie;
	
	@FXML
	private TableColumn<CustomDTO, String> tc_start_time;
	
	@FXML
	private TableColumn<CustomDTO, String> tc_end_time;
	
	@FXML
	private TableColumn<CustomDTO, String> tc_current;
	
	@FXML
	private Text result;
	
	@FXML
	private TextField tf_theater;
	
	@FXML
	private TextField tf_screen;
	
	@FXML
	private TextField tf_movie;
	
	@FXML
	private DatePicker dp_start_date;
	
	@FXML
	private MenuButton mb_hours_start;
	
	@FXML
	private MenuButton mb_hours_end;
	
	@FXML
	private Button btn_search_timetable;
	
	@FXML
	private MenuButton mb_minute_start;
	
	@FXML
	private MenuButton mb_minute_end;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
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
			
			custom_list = FXCollections.observableArrayList();
			
			// 테이블 뷰 초기화
			tv_timetable.getItems().clear();
			
			// 각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
			tc_theater.setCellValueFactory(cellData -> cellData.getValue().getTheater());
			tc_screen.setCellValueFactory(cellData -> cellData.getValue().getScreen());
			tc_movie.setCellValueFactory(cellData -> cellData.getValue().getMovie());
			tc_start_time.setCellValueFactory(cellData -> cellData.getValue().getStartTime());
			tc_end_time.setCellValueFactory(cellData -> cellData.getValue().getEndTime());
			tc_current.setCellValueFactory(cellData -> cellData.getValue().getCurrent());
			
			// 테이블 뷰와 리스트를 연결
			tv_timetable.setItems(custom_list);
			
			// 테이블 뷰 row 선택 시 발생하는 이벤트 지정
			tv_timetable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomDTO>()
			{
				@Override
				public void changed(ObservableValue<? extends CustomDTO> observable, CustomDTO oldValue, CustomDTO newValue)
				{
					selectedCustom = tv_timetable.getSelectionModel().getSelectedItem();
					System.out.println(selectedCustom.getTimeTable().getId());
				}
			});
			
			for (int i = 0; i < 24; i++)
			{
				MenuItem hour_s = new MenuItem(Integer.toString(i + 1) + "시");
				hour_s.setOnAction(new EventHandler<ActionEvent>()
				{
					public void handle(ActionEvent event)
					{
						mb_hours_start.setText(hour_s.getText());
					}
				});
				mb_hours_start.getItems().add(hour_s);
				
				MenuItem hour_e = new MenuItem(Integer.toString(i + 1) + "시");
				hour_e.setOnAction(new EventHandler<ActionEvent>()
				{
					public void handle(ActionEvent event)
					{
						mb_hours_end.setText(hour_e.getText());
					}
				});
				mb_hours_end.getItems().add(hour_e);
			}
			
			for (int i = 0; i <= 12; i++)
			{
				MenuItem minute_s = new MenuItem(Integer.toString(i * 5) + "분");
				minute_s.setOnAction(new EventHandler<ActionEvent>()
				{
					public void handle(ActionEvent event)
					{
						mb_minute_start.setText(minute_s.getText());
					}
				});
				mb_minute_start.getItems().add(minute_s);
				
				MenuItem minute_e = new MenuItem(Integer.toString(i * 5) + "분");
				minute_e.setOnAction(new EventHandler<ActionEvent>()
				{
					public void handle(ActionEvent event)
					{
						mb_minute_end.setText(minute_e.getText());
					}
				});
				mb_minute_end.getItems().add(minute_e);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	void add(ActionEvent event)
	{
		try
		{
			if (selectedMovie == null || selectedScreen == null || dp_start_date.getValue() == null || mb_hours_start.getText().equals("시간") || mb_minute_start.getText().equals("분") || mb_hours_end.getText().equals("시간") || (mb_minute_end.getText().equals("분")))
			{
				mainGUI.alert("경고", "모든 데이터를 입력해주세요");
				return;
			}
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String date = dp_start_date.getValue() == null ? "1976-01-01 " : dateFormat.format(dp_start_date.getValue()) + " ";
			String start_time = mb_hours_start.getText().equals("시간") ? "00:00:00.0" : (mb_minute_start.getText().equals("분") ? mb_hours_start.getText().replace("시", "") + ":00:00.0" : mb_hours_start.getText().replace("시", "") + ":" + mb_minute_start.getText().replace("분", "") + ":00.0");
			String end_time = mb_hours_end.getText().equals("시간") ? "23:59:00.0" : (mb_minute_end.getText().equals("분") ? mb_hours_end.getText().replace("시", "") + ":00:00.0" : mb_hours_end.getText().replace("시", "") + ":" + mb_minute_end.getText().replace("분", "") + ":00.0");
			
			TimeTableDAO ttDao = new TimeTableDAO();
			ttDao.addTimeTable(new TimeTableDTO(DTO.EMPTY_ID, selectedMovie.getId(), selectedScreen.getId(), date + start_time, date + end_time, "0", 0));
			
			initList();
			mainGUI.alert("추가 성공", "상영시간표 추가 성공");
		}
		catch (DAOException e)
		{
			if (e.getMessage().equals("DUPLICATE_TIMETABLE"))
			{
				mainGUI.alert("오류", "상영시간표 중복 발생");
			}
		}
		catch (Exception e)
		{
			mainGUI.alert("오류", "DB접속 오류");
			e.printStackTrace();
		}
	}
	
	@FXML
	void change(ActionEvent event)
	{
		try
		{
			if (selectedCustom == null)
			{
				mainGUI.alert("경고", "데이터를 선택해주세요");
				return;
			}
			if (selectedMovie == null || selectedScreen == null || dp_start_date.getValue() == null || mb_hours_start.getText().equals("시간") || mb_minute_start.getText().equals("분") || mb_hours_end.getText().equals("시간") || (mb_minute_end.getText().equals("분")))
			{
				mainGUI.alert("경고", "모든 데이터를 입력해주세요");
				return;
			}
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String date = dp_start_date.getValue() == null ? "1976-01-01 " : dateFormat.format(dp_start_date.getValue()) + " ";
			String start_time = mb_hours_start.getText().equals("시간") ? "00:00:00.0" : (mb_minute_start.getText().equals("분") ? mb_hours_start.getText().replace("시", "") + ":00:00.0" : mb_hours_start.getText().replace("시", "") + ":" + mb_minute_start.getText().replace("분", "") + ":00.0");
			String end_time = mb_hours_end.getText().equals("시간") ? "23:59:00.0" : (mb_minute_end.getText().equals("분") ? mb_hours_end.getText().replace("시", "") + ":00:00.0" : mb_hours_end.getText().replace("시", "") + ":" + mb_minute_end.getText().replace("분", "") + ":00.0");
			
			TimeTableDAO ttDao = new TimeTableDAO();
			ttDao.changeTimeTable(new TimeTableDTO(selectedCustom.getTimeTable().getId(), selectedMovie.getId(), selectedScreen.getId(), date + start_time, date + end_time, "0", 0));
			
			initList();
			mainGUI.alert("수정 성공", "상영시간표 수정 성공");
		}
		catch (DAOException e)
		{
			if (e.getMessage().equals("DUPLICATE_TIMETABLE"))
			{
				mainGUI.alert("오류", "상영시간표 중복 발생");
			}
		}
		catch (Exception e)
		{
			mainGUI.alert("오류", "DB접속 오류");
			e.printStackTrace();
		}
	}
	
	@FXML
	void remove(ActionEvent event)
	{
		try
		{
			if (selectedCustom == null)
			{
				mainGUI.alert("경고", "데이터를 선택해주세요");
				return;
			}
			
			ButtonType type = mainGUI.confirm("확인", "정말로 삭제하시겠습니까?");
			if (type != ButtonType.OK)
			{
				return;
			}
			
			TimeTableDAO ttDao = new TimeTableDAO();
			ttDao.removeTimeTable(selectedCustom.getTimeTable().getId());
			
			initList();
			mainGUI.alert("삭제 성공", "상영시간표 삭제 성공");
		}
		catch (Exception e)
		{
			mainGUI.alert("오류", "DB접속 오류");
			e.printStackTrace();
		}
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
	void clickedLvMember(MouseEvent event)
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
	void clickedTfMember(MouseEvent event)
	{
		lv_member.getItems().clear();
		lv_member.getItems().addAll(member_list);
		lv_member.setMaxHeight(130);
	}
	
	@FXML
	void clickedParent(MouseEvent event)
	{
		lv_theater.setMaxHeight(0);
		lv_theater.getItems().clear();
		lv_screen.setMaxHeight(0);
		lv_screen.getItems().clear();
		lv_movie.setMaxHeight(0);
		lv_movie.getItems().clear();
		lv_member.setMaxHeight(0);
		lv_member.getItems().clear();
	}
	
	@FXML
	void typedTfMovie(KeyEvent event)
	{
		// 필드에 값이 없다면 리스트뷰 감추기
		if (tf_movie.getText().equals(""))
		{
			selectedMovie = null;
			lv_movie.getItems().clear();
			lv_movie.setMaxHeight(0);
			return;
		}
		
		// 값이 있으면 리스트뷰 활성화
		lv_movie.setMaxHeight(130);
		lv_movie.setItems(FXCollections.observableArrayList());
		ObservableList<MovieDTO> temp_list = FXCollections.observableArrayList();
		for (int i = 0; i < movie_list.size(); i++)
		{
			// 모든 사용자 리스트에서 현재 입력한 값을 포함하는 요소가 있는지 확인
			if (movie_list.get(i).getTitle().contains(tf_movie.getText()))
			{
				temp_list.add(movie_list.get(i));
			}
		}
		lv_movie.getItems().addAll(temp_list);
	}
	
	@FXML
	void searchTimeTable(ActionEvent event)
	{
		initList();
	}
	
	private void initList()
	{
		try
		{
			custom_list.clear();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			TimeTableDAO rDao = new TimeTableDAO();
			
			String mov_id = selectedMovie == null ? "%" : selectedMovie.getId();
			// String thea_id = selectedThea == null ? "%" : selectedThea.getId();
			String screen_id = selectedScreen == null ? "%" : selectedScreen.getId();
			String date = dp_start_date.getValue() == null ? "1976-01-01 " : dateFormat.format(dp_start_date.getValue()) + " ";
			String start_time = mb_hours_start.getText().equals("시간") ? "00:00:00.0" : (mb_minute_start.getText().equals("분") ? mb_hours_start.getText().replace("시", "") + ":00:00.0" : mb_hours_start.getText().replace("시", "") + ":" + mb_minute_start.getText().replace("분", "") + ":00.0");
			String end_time = mb_hours_end.getText().equals("시간") ? "23:59:00.0" : (mb_minute_end.getText().equals("분") ? mb_hours_end.getText().replace("시", "") + ":00:00.0" : mb_hours_end.getText().replace("시", "") + ":" + mb_minute_end.getText().replace("분", "") + ":00.0");
			
			System.out.println(date + start_time + "/" + date + end_time);
			
			ArrayList<TimeTableDTO> t_list = rDao.getTimeTableList(new TimeTableDTO(DTO.EMPTY_ID, mov_id, screen_id, date + start_time, date + end_time, "1", 0));
			Iterator<TimeTableDTO> t_iter = t_list.iterator();
			while (t_iter.hasNext())
			{
				custom_list.add(new CustomDTO(t_iter.next()));
			}
		}
		catch (DAOException e)
		{
			if (e.getMessage().equals("EMPTY_LIST"))
			{
				mainGUI.alert("에러", "상영시간표 리스트가 없습니다");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
		
		public StringProperty getEndTime()
		{
			return new SimpleStringProperty(timetable.getEndTime().toString());
		}
		
		public StringProperty getCurrent()
		{
			return new SimpleStringProperty(timetable.getCurrentRsv() + "/" + screen.getTotalCapacity());
		}
		
		public TimeTableDTO getTimeTable()
		{
			return timetable;
		}
		
		public ScreenDTO getScreenElem()
		{
			return screen;
		}
		
		public MovieDTO getMovieElem()
		{
			return movie;
		}
	}
}
