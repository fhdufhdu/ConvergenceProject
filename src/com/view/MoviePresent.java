package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.db.model.MovieDAO;
import com.db.model.MovieDTO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;


public class MoviePresent implements Initializable
{

	@FXML
	private GridPane gp_current_movie;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		try
		{
			MovieDAO mDao = new MovieDAO();
			ArrayList<MovieDTO> m_list = mDao.getCurrentMovieList();
			for (int i = 0; i < m_list.size(); i++)
			{
				FXMLLoader loader = new FXMLLoader(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml"));
				Parent root = loader.load();
				MovieSub controller = loader.<MovieSub>getController();
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
