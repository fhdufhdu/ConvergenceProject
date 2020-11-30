package com.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.db.model.MovieDTO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class MovieDetail
{
	private MovieDTO movie;
	
	@FXML
	private Button btn_reservation;
	
	@FXML
	private ImageView image_movie;
	
	@FXML
	private Text text_title;
	
	@FXML
	private Text text_open_date;
	
	@FXML
	private Text text_director;
	
	@FXML
	private Text text_actor;
	
	@FXML
	private TableView<?> tv_review;
	
	@FXML
	private TableColumn<?, ?> tc_reviewer;
	
	@FXML
	private TableColumn<?, ?> tc_review_score;
	
	@FXML
	private TableColumn<?, ?> tc_review_date;
	
	@FXML
	private TableColumn<?, ?> tc_review;
	
	@FXML
	private MenuButton mb_review;
	
	@FXML
	private TextField tf_review;
	
	@FXML
	private Button btn_review;
	
	@FXML
	private WebView web_trailer;
	
	@FXML
	private ImageView image_stillcut;
	
	@FXML
	private ImageView image_stillcut2;
	
	@FXML
	private ImageView image_stillcut3;
	
	@FXML
	private Text text_plot;
	
	public void initData(MovieDTO movie)
	{
		this.movie = movie;
		text_title.setText(movie.getTitle());
		text_open_date.setText(movie.getReleaseDate().toString());
		text_director.setText(movie.getDirector());
		text_actor.setText(movie.getActor());
		text_plot.wrappingWidthProperty().set(225);
		text_plot.setText(movie.getPlot());
		Image image = new Image(movie.getPosterPath());
		image_movie.setImage(image);
		String[] trailer = movie.getTrailerPath().split("watch?v=");
		web_trailer.getEngine().load("http://www.youtube.com/embed/" + trailer[1] + "?autoplay=1");
	}
	
	@FXML
	void addReview(ActionEvent event)
	{
		
	}
	
	@FXML
	void getReservation(ActionEvent event)
	{
		
	}
	
}
