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

public class MovieSoon implements Initializable
{
	
	@FXML
	private GridPane gp_soon_movie;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		try
		{
			MovieDAO mDao = new MovieDAO();
			ArrayList<MovieDTO> m_list = mDao.getSoonMovieList();
			for (int i = 0; i < m_list.size(); i++)
			{
				FXMLLoader loader = new FXMLLoader(MovieTable.class.getResource("./xml/user_sub_page/movie_soon_sub.fxml"));
				Parent root = loader.load();
				MovieSoonSub controller = loader.<MovieSoonSub>getController();
				controller.initData(m_list.get(i));
				gp_soon_movie.add(root, i % 4, i / 4);
			}
			
			/*
			 * gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 2, 0); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 3, 0); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 0, 1); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 1, 1); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 2, 1); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 3, 1);
			 */
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
