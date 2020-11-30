package com.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.db.model.MovieDTO;
import com.db.model.TimeTableDAO;
import com.db.model.MovieDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MovieSub2
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
            
            MovieDAO mDao = new MovieDAO();
            String remain_date = RemainDate(movie.getId());
            t_rsv_rate.setText(t_rsv_rate.getText() + "% | D - " + remain_date);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public double getRemainDate()
    {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
    	
    	Date time = new Date();
    	
        String date1 = format.format(time);
        String date2 = format.format(movie.getReleaseDate());
     
        try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
        	
            
            // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
            Date FirstDate = format.parse(date1);
            Date SecondDate = format.parse(date2);
            
            // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
            // 연산결과 -950400000. long type 으로 return 된다.
            long calDate = FirstDate.getTime() - SecondDate.getTime(); 
            
            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다. 
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            long calDateDays = calDate / ( 24*60*60*1000); 
     
            calDateDays = Math.abs(calDateDays);
            
            return (double)calDateDays;
            
            }
        
            catch(ParseException e)
            {
                // 예외 처리
            }
    }    
}
