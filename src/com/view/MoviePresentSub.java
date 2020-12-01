package com.view;

import com.db.model.MovieDTO;
import com.db.model.ReviewDAO;
import com.db.model.TimeTableDAO;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MoviePresentSub
{
    private MovieDTO movie;
    
    @FXML
    private ImageView iv_movie_poster;
    
    @FXML
    private Text t_movie_title;
    
    @FXML
    private Text t_rsv_rate;
    
    @FXML
    void clickedTheater(ActionEvent event) // 각 sub페이지 클릭시 영화 상세 페이지(MovieDetail.java)로 이동
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
    
    // MoviePresent에서 값을 받아와서 이미지와 글자등 설정
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
