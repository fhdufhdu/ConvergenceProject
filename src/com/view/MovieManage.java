package com.view;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.db.model.DAOException;
import com.db.model.MovieDAO;
import com.db.model.MovieDTO;
import com.main.mainGUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class MovieManage implements Initializable
{
	
	private ObservableList<MovieDTO> movie_list;
	private MovieDTO table_row_data;
	private String is_current;
	private HashMap<String, String> info;
	
	@FXML
	private BorderPane bp_parent;
	
	@FXML
	private TableView<MovieDTO> tv_movie;
	
	@FXML
	private TableColumn<MovieDTO, String> tc_screening;
	
	@FXML
	private TableColumn<MovieDTO, String> tc_movie_title;
	
	@FXML
	private TableColumn<MovieDTO, String> tc_start_date;
	
	@FXML
	private TableColumn<MovieDTO, String> tc_director;
	
	@FXML
	private TableColumn<MovieDTO, String> tc_actor;
	
	@FXML
	private TableColumn<MovieDTO, String> tc_screening_time;
	
	@FXML
	private TableColumn<MovieDTO, String> tc_plot;
	
	@FXML
	private Text t_result;
	
	@FXML
	private TextField tf_title;
	
	@FXML
	private TextField tf_director;
	
	@FXML
	private TextField tf_actor;
	
	@FXML
	private CheckBox cb_current;
	
	@FXML
	private CheckBox cb_close;
	
	@FXML
	private CheckBox cb_soon;
	
	@FXML
	private DatePicker dp_start_date;
	
	@FXML
	private DatePicker dp_end_date;
	
	@FXML
	private Button btn_delete;
	
	@FXML
	private Button btn_change;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		try
		{
			// 테이블 뷰에 넣을 리스트 세팅
			movie_list = FXCollections.observableArrayList();
			
			// 리스트 초기화
			info = new HashMap<String, String>();
			info.put("title", "%");
			info.put("start_date", "1976-01-01");
			info.put("end_date", "2222-01-01");
			info.put("is_current", "%");
			info.put("director", "%");
			info.put("actor", "%");
			initList(info);
			
			// 테이블 뷰 초기화
			tv_movie.getItems().clear();
			
			// 각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
			tc_screening.setCellValueFactory(cellData -> cellData.getValue().getScreeningProperty());
			tc_movie_title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
			tc_start_date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReleaseDate().toString()));
			tc_director.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDirector()));
			tc_actor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getActor()));
			tc_screening_time.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getMin())));
			tc_plot.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlot()));
			
			// 테이블 뷰와 리스트를 연결
			tv_movie.setItems(movie_list);
			
			// 테이블 뷰 row 선택 시 발생하는 이벤트 지정
			tv_movie.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MovieDTO>()
			{
				@Override
				public void changed(ObservableValue<? extends MovieDTO> observable, MovieDTO oldValue, MovieDTO newValue)
				{
					table_row_data = tv_movie.getSelectionModel().getSelectedItem();
				}
			});
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML // 해당하는 영화 검색
	void getMovieSearch(ActionEvent event)
	{
		info = new HashMap<String, String>();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		info.put("title", tf_title.getText());
		info.put("start_date", dateFormat.format(dp_start_date.getValue()));
		info.put("end_date", dateFormat.format(dp_end_date.getValue()));
		info.put("is_current", is_current);
		info.put("director", tf_director.getText());
		info.put("actor", tf_actor.getText());
		
		tv_movie.getItems().clear();
		initList(info);
		tv_movie.setItems(movie_list);
	}
	
	@FXML // 상영종료 선택
	void selectClose(ActionEvent event)
	{
		cb_current.setSelected(false);
		cb_soon.setSelected(false);
		is_current = "0";
	}
	
	@FXML // 상영중 선택
	void selectCurrent(ActionEvent event)
	{
		cb_soon.setSelected(false);
		cb_close.setSelected(false);
		is_current = "1";
	}
	
	@FXML // 상영예정 선택
	void selectSoon(ActionEvent event)
	{
		cb_current.setSelected(false);
		cb_close.setSelected(false);
		is_current = "2";
	}
	
	@FXML // 영화 선택 후 영화 선택창으로 이동
	void changeNextMovie(ActionEvent event)
	{
		try
		{
			if (tv_movie.getSelectionModel().isEmpty())
			{
				mainGUI.alert("오류", "데이터를 선택해주세요");
				return;
			}
			FXMLLoader loader = new FXMLLoader(TheaterManage.class.getResource("./xml/admin_sub_page/movie_change.fxml"));
			Parent root = (Parent) loader.load();
			MovieChange controller = loader.<MovieChange>getController();
			controller.initData(table_row_data, bp_parent);
			
			bp_parent.setCenter(root);
		}
		catch (Exception e)
		{
			mainGUI.alert("오류", "창 로딩 실패");
			e.printStackTrace();
		}
	}
	
	@FXML // 영화 삭제
	void deleteMovie(ActionEvent event)
	{
		try
		{
			if (tv_movie.getSelectionModel().isEmpty())
			{
				mainGUI.alert("삭제오류", "삭제할 데이터를 선택해주세요");
				return;
			}
			
			// 삭제할 것인지 재 확인
			ButtonType btnType = mainGUI.confirm("삭제확인", "정말로 삭제하시겠습니까?");
			if (btnType != ButtonType.OK)
			{
				return;
			}
			
			String id = table_row_data.getId();
			MovieDAO mDao = new MovieDAO();
			mDao.removeMovie(table_row_data.getId());
			
			movie_list.clear();
			tv_movie.getItems().clear();
			initList(info);
			tv_movie.setItems(movie_list);
		}
		catch (DAOException e)
		{
			// 값의 중복 발생시
			t_result.setText("영화관 이름 및 주소가 중복됩니다!");
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// DB관련 문제 발생시
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{
			// 입력값 타입이 맞지 않을때
			t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
			e.printStackTrace();
		}
	}
	
	// 테이블뷰에 들어갈 리스트 초기화
	private void initList(HashMap<String, String> info)
	{
		try
		{
			MovieDAO tDao = new MovieDAO();
			ArrayList<MovieDTO> tlist = tDao.getMovieList(info);
			Iterator<MovieDTO> tIter = tlist.iterator();
			while (tIter.hasNext())
			{
				movie_list.add(tIter.next());
			}
		}
		catch (Exception e)
		{
			
		}
	}
}
