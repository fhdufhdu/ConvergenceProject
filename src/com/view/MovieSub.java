package com.view;

import com.db.model.MovieDTO;
import com.db.model.ReviewDAO;
import com.db.model.TimeTableDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MovieSub
{
    private MovieDTO movie;
    
    @FXML
    private ImageView iv_movie_poster;
    
    @FXML
    private Text t_movie_title;
    
    @FXML
    private Text t_rsv_rate;
    
    @FXML
    void clickedTheater(ActionEvent event)
    {
        System.out.println("테스트클릭");
    }
    
    public void initData(MovieDTO movie)
    {
        try
        {
            this.movie = movie;
            Image image = new Image(movie.getPosterPath());
            iv_movie_poster.setImage(image);
            t_movie_title.setText(movie.getTitle());
            
            TimeTableDAO tDao = new TimeTableDAO();
            double rsv_rate = tDao.getRsvRate(movie.getId());
            t_rsv_rate.setText("예매율 : " + String.format("%.2f", rsv_rate * 100));
            
            ReviewDAO rDao = new ReviewDAO();
            String aver_star = Integer.toString(rDao.getAverStarGrade(movie.getId()));
            t_rsv_rate.setText(t_rsv_rate.getText() + "% | 평점 : " + aver_star);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
}
