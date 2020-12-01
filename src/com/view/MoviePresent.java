package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.db.model.MovieDAO;
import com.db.model.MovieDTO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class MoviePresent implements Initializable 
{
	@FXML
	private Button btn_detail;

	@FXML
	private Text text_movie;

	@FXML
	private Text text_rsv_ratio;

	@FXML
	private Text text_movie2;

	@FXML
	private Button btn_detail2;

	@FXML
	private Text text_rsv_ratio2;

	@FXML
	private Text text_movie3;

	@FXML
	private Button btn_detail3;

	@FXML
	private Text text_rsv_ratio3;

	@FXML
	private ImageView img_movie;

	@FXML
	private ImageView img_movie2;

	@FXML
	private ImageView img_movie3;

	@FXML
	void getDetail(ActionEvent event) 
	{

	}

	@FXML
	void getDetail2(ActionEvent event) 
	{

	}

	@FXML
	void getDetail3(ActionEvent event) 
	{

	}
	@FXML
	private GridPane gp_current_movie;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		try
		{
			// 현재 상영작을 모두 받아와서 girdview에 뿌리기, 각 girdview에는 MovieSub.java가 컨트롤함
			MovieDAO mDao = new MovieDAO();
			ArrayList<MovieDTO> m_list = mDao.getCurrentMovieList();
			for (int i = 0; i < m_list.size(); i++)
			{
				FXMLLoader loader = new FXMLLoader(MovieTable.class.getResource("./xml/user_sub_page/movie_present_sub.fxml"));
				Parent root = loader.load();
				MoviePresentSub controller = loader.<MoviePresentSub>getController();
				controller.initData(m_list.get(i));
				gp_current_movie.add(root, i % 4, i / 4);
			}
			
			/*
			 * gp_current_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 2, 0); gp_current_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 3, 0); gp_current_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 0, 1); gp_current_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 1, 1); gp_current_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 2, 1); gp_current_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 3, 1);
			 */
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
