package com.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.db.model.DAO;
import com.db.model.DAOException;
import com.db.model.DTO;
import com.db.model.MovieDAO;
import com.db.model.MovieDTO;
import com.db.model.ReservationDAO;
import com.db.model.ScreenDAO;
import com.db.model.ScreenDTO;
import com.db.model.TheaterDAO;
import com.db.model.TheaterDTO;
import com.db.model.TimeTableDAO;
import com.db.model.TimeTableDTO;
import com.main.mainGUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieTable implements Initializable
{
	private ObservableList<TheaterDTO> theater_list;
	private ObservableList<MovieDTO> movie_list;
	private ObservableList<CustomDTO> custom_list;
	
	private TheaterDTO selectedThea;
	private MovieDTO selectedMovie;
	private CustomDTO selectedCustom;
	
	private ArrayList<Integer> row_list;
	private ArrayList<Integer> col_list;
	
	@FXML
	private BorderPane bp_parent;
	
	@FXML
	private TableView<TheaterDTO> tv_theater;
	
	@FXML
	private TableColumn<TheaterDTO, String> tc_theater;
	
	@FXML
	private TableView<MovieDTO> tv_movie;
	
	@FXML
	private TableColumn<MovieDTO, String> tc_movie;
	
	@FXML
	private DatePicker dp_date;
	
	@FXML
	private VBox vbox;
	
	@FXML
	private Text t_movie_title;
	
	@FXML
	private Button btn_reservation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		try
		{
			initTheaterList();
			initMovieList();
			dp_date.setValue(LocalDate.now());
			dp_date.valueProperty().addListener(new ChangeListener<LocalDate>()
			{
				@Override
				public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue)
				{
					try
					{
						setRsvButton();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
			});
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void initTheaterList() throws DAOException, SQLException
	{
		theater_list = FXCollections.observableArrayList();
		TheaterDAO tDao = new TheaterDAO();
		Iterator<TheaterDTO> t_iter = tDao.getTheaterList().iterator();
		while (t_iter.hasNext())
		{
			theater_list.add(t_iter.next());
		}
		// 테이블 뷰 초기화
		tv_theater.getItems().clear();
		
		// 각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
		tc_theater.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		
		// 테이블 뷰와 리스트를 연결
		tv_theater.setItems(theater_list);
		
		// 테이블 뷰 row 선택 시 발생하는 이벤트 지정
		tv_theater.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TheaterDTO>()
		{
			@Override
			public void changed(ObservableValue<? extends TheaterDTO> observable, TheaterDTO oldValue, TheaterDTO newValue)
			{
				selectedThea = tv_theater.getSelectionModel().getSelectedItem();
			}
		});
	}
	
	private void initMovieList() throws DAOException, SQLException
	{
		movie_list = FXCollections.observableArrayList();
		MovieDAO mDao = new MovieDAO();
		Iterator<MovieDTO> m_iter = mDao.getMovieListForType("1").iterator();
		while (m_iter.hasNext())
		{
			movie_list.add(m_iter.next());
		}
		// 테이블 뷰 초기화
		tv_movie.getItems().clear();
		
		// 각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
		tc_movie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
		
		// 테이블 뷰와 리스트를 연결
		tv_movie.setItems(movie_list);
		
		// 테이블 뷰 row 선택 시 발생하는 이벤트 지정
		tv_movie.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MovieDTO>()
		{
			@Override
			public void changed(ObservableValue<? extends MovieDTO> observable, MovieDTO oldValue, MovieDTO newValue)
			{
				try
				{
					if (selectedThea == null)
					{
						mainGUI.alert("경고", "영화관을 선택해주세요");
						return;
					}
					selectedMovie = tv_movie.getSelectionModel().getSelectedItem();
					
					setRsvButton();
				}
				catch (Exception e)
				{
					
				}
				
			}
		});
	}
	
	private void initCustomList() throws DAOException, SQLException
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		custom_list = FXCollections.observableArrayList();
		
		TimeTableDAO ttDao = new TimeTableDAO();
		ArrayList<TimeTableDTO> tList = ttDao.getTimeTableList(new TimeTableDTO(DTO.EMPTY_ID, selectedMovie.getId(), "%", dp_date.getValue().format(format) + " 00:00:00.0", dp_date.getValue().format(format) + " 23:59:00.0", "1", 0), selectedThea.getId());
		Iterator<TimeTableDTO> tIter = tList.iterator();
		while (tIter.hasNext())
		{
			TimeTableDTO temp = tIter.next();
			if (temp.getStartTime().after(new Timestamp(System.currentTimeMillis())))
				custom_list.add(new CustomDTO(temp));
		}
	}
	
	private void setRsvButton() throws DAOException, SQLException
	{
		initCustomList();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		
		int cnt = custom_list.size();
		if (cnt <= 0)
		{
			t_movie_title.setText("<해당하는 상영시간표가 없습니다>");
			Node temp = vbox.getChildren().get(0);
			vbox.getChildren().clear();
			vbox.getChildren().add(temp);
			return;
		}
		t_movie_title.setText("<" + selectedMovie.getTitle() + ">");
		ArrayList<ButtonBar> bar_list = new ArrayList<ButtonBar>();
		
		for (int i = 0; i < (cnt < 4 ? 1 : cnt / 4); i++)
		{
			bar_list.add(new ButtonBar());
			for (int j = 0; j < 4; j++)
			{
				if ((i + 1) * (j + 1) > cnt)
					break;
				selectedCustom = custom_list.get(((i + 1) * (j + 1) - 1));
				Button btn = new Button(selectedCustom.getScreen().getName() + "관\n" + selectedCustom.getTimeTable().getCurrentRsv() + "/" + selectedCustom.getScreen().getTotalCapacity() + "\n" + format.format(new Date(selectedCustom.getTimeTable().getStartTime().getTime())));
				btn.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						selectSeat();
					}
				});
				ButtonBar.setButtonData(btn, ButtonData.LEFT);
				bar_list.get(i).getButtons().addAll(btn);
			}
		}
		
		Node temp = vbox.getChildren().get(0);
		vbox.getChildren().clear();
		vbox.getChildren().add(temp);
		vbox.getChildren().addAll(bar_list);
		vbox.setSpacing(10);
	}
	
	private void selectSeat()
	{
		try
		{
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(MovieTable.class.getResource("./xml/user_sub_page/seat_choice.fxml"));
			Parent root = loader.load();
			SeatController controller = loader.<SeatController>getController();
			controller.initData(selectedCustom.getScreen(), selectedCustom.getTimeTable());
			stage.setScene(new Scene(root));
			stage.setTitle("좌석 선택");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(bp_parent.getScene().getWindow());
			stage.showAndWait();
			
			row_list = controller.getSelected().get(0);
			col_list = controller.getSelected().get(1);
			
			Connection conn = DAO.getConn();
			conn.setAutoCommit(false);
			Savepoint sp = conn.setSavepoint();
			ReservationDAO rDao = new ReservationDAO();
			int price = 0;
			
			if (!controller.getIsClickedClose())
			{
				return;
			}
			
			try
			{
				price = rDao.addPreRsv(Login.USER_ID, selectedCustom.getTimeTable().getId(), row_list, col_list);
				conn.commit();
			}
			catch (Exception e)
			{
				conn.rollback(sp);
				e.printStackTrace();
			}
			finally
			{
				conn.setAutoCommit(true);
			}
			
			Timer m_timer = new Timer();
			ClearTimer m_task = new ClearTimer(Login.USER_ID, selectedCustom.getTimeTable().getId(), row_list, col_list);
			
			m_timer.schedule(m_task, 60000);
			
			startPayment(price);
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
	}
	
	private void startPayment(int price)
	{
		try
		{
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(MovieTable.class.getResource("./xml/user_sub_page/payment.fxml"));
			Parent root = loader.load();
			Payment controller = loader.<Payment>getController();
			ArrayList<ArrayList<Integer>> seat_list = new ArrayList<ArrayList<Integer>>();
			seat_list.add(row_list);
			seat_list.add(col_list);
			controller.initData(selectedThea, selectedCustom.getScreen(), selectedMovie, selectedCustom.getTimeTable(), seat_list, price);
			stage.setScene(new Scene(root));
			stage.setTitle("좌석 선택");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(bp_parent.getScene().getWindow());
			stage.showAndWait();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private class CustomDTO
	{
		ScreenDTO screen;
		TimeTableDTO timetable;
		
		public CustomDTO(TimeTableDTO timetable)
		{
			try
			{
				this.timetable = timetable;
				
				ScreenDAO sDao = new ScreenDAO();
				screen = sDao.getScreenElem(timetable.getScreenId());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public ScreenDTO getScreen()
		{
			return screen;
		}
		
		public TimeTableDTO getTimeTable()
		{
			return timetable;
		}
	}
}
