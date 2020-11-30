package com.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.db.model.MovieDTO;
import com.db.model.TimeTableDAO;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MovieSoonSub
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
        try
        {
            FXMLLoader loader = new FXMLLoader(MovieTable.class.getResource("./xml/user_sub_page/movie_detail.fxml"));
            Parent root = loader.load();
            MovieDetail controller = loader.<MovieDetail>getController();
            controller.initData(movie);
            
            UserMain.user_sub_root.setCenter(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void initData(MovieDTO movie)
    {
        try
        {
            this.movie = movie;
            Thread image_load = new Thread()
            {
                @Override
                public void run()
                {
                    Platform.runLater(() ->
                    {
                        Image image = new Image(movie.getPosterPath());
                        iv_movie_poster.setImage(image);
                    });
                    return;
                }
            };
            image_load.start();
            
            t_movie_title.setText(movie.getTitle());
            
            TimeTableDAO tDao = new TimeTableDAO();
            double rsv_rate = tDao.getRsvRate(movie.getId());
            if (Double.isNaN(rsv_rate))
            {
                rsv_rate = 0;
            }
            t_rsv_rate.setText("예매율 : " + String.format("%.2f", rsv_rate * 100));
            
            String remain_date = Integer.toString(getRemainDate());
            t_rsv_rate.setText(t_rsv_rate.getText() + "% | D - " + remain_date);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public int getRemainDate()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        
        Date time = new Date(System.currentTimeMillis());
        
        String date1 = format.format(movie.getReleaseDate());
        String date2 = format.format(time);
        
        try
        {
            Date FirstDate = format.parse(date1);
            Date SecondDate = format.parse(date2);
            
            long calDate = FirstDate.getTime() - SecondDate.getTime();
            
            long calDateDays = calDate / (24 * 60 * 60 * 1000);
            
            calDateDays = Math.abs(calDateDays);
            
            return (int) calDateDays;
            
        }
        
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
