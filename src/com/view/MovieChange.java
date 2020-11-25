package com.view;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import com.db.model.DAOException;
import com.db.model.MovieDAO;
import com.db.model.MovieDTO;
import com.main.mainGUI;
import com.protocol.Protocol;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MovieChange
{
	private String is_current; // 현재 상영작 구분 용도
	private MovieDTO currentMov; // 현재 영화 객체 저장
	BorderPane parent; // 이전 parent를 기억하기 위한 용도
	
	@FXML
	private CheckBox cb_current;
	
	@FXML
	private CheckBox cb_close;
	
	@FXML
	private CheckBox cb_soon;
	
	@FXML
	private TextField tf_title;
	
	@FXML
	private DatePicker dp_release_date;
	
	@FXML
	private TextField tf_director;
	
	@FXML
	private TextArea ta_actor;
	
	@FXML
	private TextField tf_min;
	
	@FXML
	private TextField tf_poster;
	
	@FXML
	private TextField tf_stillcut;
	
	@FXML
	private TextField tf_trailer;
	
	@FXML
	private TextArea ta_plot;
	
	@FXML
	private Text result;
	
	@FXML
	private Button btn_movie_change;
	
	// 외부에서 받아오는 데이터들
	public void initData(MovieDTO mov, BorderPane parent)
	{
		this.parent = parent;
		currentMov = mov;
		is_current = currentMov.getIsCurrent();
		
		if (is_current.equals("0"))
		{
			cb_close.setSelected(true);
			cb_current.setSelected(false);
			cb_soon.setSelected(false);
		}
		else if (is_current.equals("1"))
		{
			cb_close.setSelected(false);
			cb_current.setSelected(true);
			cb_soon.setSelected(false);
		}
		else
		{
			cb_close.setSelected(false);
			cb_current.setSelected(false);
			cb_soon.setSelected(true);
		}
		
		tf_title.setPromptText(mov.getTitle());
		dp_release_date.setPromptText(mov.getReleaseDate().toString());
		tf_director.setPromptText(mov.getDirector());
		ta_actor.setPromptText(mov.getActor());
		tf_min.setPromptText(Integer.toString(mov.getMin()));
		tf_trailer.setPromptText(mov.getTrailerPath());
		ta_plot.setPromptText(mov.getPlot());
	}
	
	@FXML
	void changeMovie(ActionEvent event)
	{
		try
		{
			// // 각 필드들이 비어있는 지 판단한 후 데이터 집어넣음
			// currentMov.setIsCurrent(is_current == null ? currentMov.getIsCurrent() : is_current);
			// currentMov.setTitle(tf_title.getText().equals("") ? currentMov.getTitle() : tf_title.getText());
			// currentMov.setReleaseDate(dp_release_date.getValue() == null ? currentMov.getReleaseDate().toString() : dateFormat.format(dp_release_date.getValue()));
			// currentMov.setDirector(tf_director.getText().equals("") ? currentMov.getDirector() : tf_director.getText());
			// currentMov.setActor(ta_actor.getText().equals("") ? currentMov.getActor() : tf_trailer.getText());
			// currentMov.setMin(tf_min.getText().equals("") ? currentMov.getMin() : Integer.valueOf(tf_min.getText()));
			// currentMov.setPosterPath(tf_poster.getText().equals("") ? currentMov.getPosterPath() : tf_poster.getText());
			// currentMov.setStillCutPath(tf_stillcut.getText().equals("") ? currentMov.getStillCutPath() : tf_stillcut.getText());
			// currentMov.setTrailerPath(tf_trailer.getText().equals("") ? currentMov.getTrailerPath() : tf_trailer.getText());
			// currentMov.setPlot(ta_plot.getText().equals("") ? currentMov.getPlot() : ta_plot.getText());
			
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String id = currentMov.getId();
			String title = tf_title.getText().equals("") ? currentMov.getTitle() : tf_title.getText();
			String release_date = dp_release_date.getValue() == null ? currentMov.getReleaseDate().toString() : dateFormat.format(dp_release_date.getValue());
			String plot = ta_plot.getText().equals("") ? currentMov.getPlot() : ta_plot.getText();
			String poster = tf_poster.getText().equals("") ? currentMov.getPosterPath() : tf_poster.getText();
			String stillCut = tf_stillcut.getText().equals("") ? currentMov.getStillCutPath() : tf_stillcut.getText();
			String trailer = tf_trailer.getText().equals("") ? currentMov.getTrailerPath() : tf_trailer.getText();
			String director = tf_director.getText().equals("") ? currentMov.getDirector() : tf_director.getText();
			String actor = ta_actor.getText().equals("") ? currentMov.getActor() : tf_trailer.getText();
			String min = tf_min.getText().equals("") ? Integer.toString(currentMov.getMin()) : tf_min.getText();
			
			mainGUI.writePacket(Protocol.PT_REQ_RENEWAL + "/" + Protocol.CS_REQ_MOVIE_CHANGE + "/" + id + "/" + title + "/" + release_date + "/" + is_current + "/" + plot + "/" + poster + "/" + stillCut + "/" + trailer + "/" + director + "/" + actor + "/" + min);
			
			while (true)
			{
				String packet = mainGUI.readLine();
				String packetArr[] = packet.split("/");
				String packetType = packetArr[0];
				String packetCode = packetArr[1];
				
				if (packetType.equals(Protocol.PT_RES_RENEWAL) && packetCode.equals(Protocol.SC_RES_MOVIE_CHANGE))
				{
					String pakcet_result = packetArr[2];
					switch (pakcet_result)
					{
						case "1":
						{
							mainGUI.alert("수정완료", "수정완료 되었습니다!");
							
							FXMLLoader loader = new FXMLLoader(TheaterManage.class.getResource("./xml/admin_sub_page/movie_manage.fxml"));
							Parent root = (Parent) loader.load();
							parent.setCenter(root);
							return;
						}
						case "2":
						{
							mainGUI.alert("수정실패", "수정실패입니다!");
							return;
						}
					}
				}
			}
		}
		catch (NumberFormatException e)
		{
			mainGUI.alert("상영시간", "상영시간에는 숫자를 입력해주세요!");
		}
		catch (DAOException e)
		{
			mainGUI.alert("영화관 중복", "이미 존재하는 영화가 있습니다!");
		}
		catch (SQLException e)
		{
			mainGUI.alert("DB서버 연결오류", "잠시 후 다시 시도해주세요!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	void getPosterPath(ActionEvent event)
	{
		tf_poster.setText(getFile().getPath());
	}
	
	@FXML
	void getStillCutPath(ActionEvent event)
	{
		tf_stillcut.setText(getFile().getPath());
	}
	
	@FXML
	void selectClose(ActionEvent event)
	{
		cb_current.setSelected(false);
		cb_soon.setSelected(false);
		is_current = "0";
	}
	
	@FXML
	void selectCurrent(ActionEvent event)
	{
		cb_soon.setSelected(false);
		cb_close.setSelected(false);
		is_current = "1";
	}
	
	@FXML
	void selectSoon(ActionEvent event)
	{
		cb_current.setSelected(false);
		cb_close.setSelected(false);
		is_current = "2";
	}
	
	private File getFile()
	{
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog((Stage) tf_poster.getScene().getWindow());
		
		return selectedFile;
	}
	
	private byte[] getFileByteArray(File selectedFile)
	{
		try
		{
			byte arr[] = Files.readAllBytes(selectedFile.toPath());
			return arr;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
