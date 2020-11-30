package com.view;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.ArrayList;

import com.db.model.DAO;
import com.db.model.DAOException;
import com.db.model.MovieDTO;
import com.db.model.ReservationDAO;
import com.db.model.ScreenDTO;
import com.db.model.TheaterDTO;
import com.db.model.TimeTableDTO;
import com.main.mainGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Payment
{
    private int price;
    private TheaterDTO theater;
    private ScreenDTO screen;
    private MovieDTO movie;
    private TimeTableDTO timetable;
    private ArrayList<Integer> row_list;
    private ArrayList<Integer> col_list;
    
    @FXML
    private TextField tf_bank;
    
    @FXML
    private TextField tf_account;
    
    @FXML
    private Button btn_payment;
    
    @FXML
    private Text t_theater;
    
    @FXML
    private Text t_movie;
    
    @FXML
    private Text t_screen;
    
    @FXML
    private Text t_date;
    
    @FXML
    private Text t_start_time;
    
    @FXML
    private Text t_seat;
    
    @FXML
    private Text t_price;
    
    @FXML
    private PasswordField pf_passwd;
    
    @FXML
    void getPayment(ActionEvent event) throws Exception
    {
        Connection conn = DAO.getConn();
        conn.setAutoCommit(false);
        Savepoint sp = conn.setSavepoint();
        try
        {
            ReservationDAO rDao = new ReservationDAO();
            rDao.addConfimRsv(Login.USER_ID, timetable.getId(), row_list, col_list, tf_account.getText(), tf_bank.getText());
            rDao.payment(tf_account.getText(), tf_bank.getText(), pf_passwd.getText(), price);
            
            conn.commit();
        }
        catch (DAOException e)
        {
            if (e.getMessage().equals("PAYMENT_ERR"))
            {
                mainGUI.alert("오류", "결제 오류 발생, 결제 정보를 확인해주세요");
            }
            else if (e.getMessage().equals("NOT_SELECTED"))
            {
                mainGUI.alert("오류", "좌석 정보 오류 발생");
            }
            e.printStackTrace();
            conn.rollback(sp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            conn.rollback(sp);
        }
        finally
        {
            conn.setAutoCommit(true);
            mainGUI.alert("결제 완료", "정상적으로 예매가 처리되었습니다");
            Stage stage = (Stage) btn_payment.getScene().getWindow();
            stage.close();
        }
        
    }
    
    public void initData(TheaterDTO theater, ScreenDTO screen, MovieDTO movie, TimeTableDTO timetable, ArrayList<ArrayList<Integer>> seat_list, int price)
    {
        String date[] = timetable.getStartTime().toString().split(" ");
        row_list = seat_list.get(0);
        col_list = seat_list.get(1);
        
        this.price = price;
        this.theater = theater;
        this.screen = screen;
        this.movie = movie;
        this.timetable = timetable;
        
        t_theater.setText(t_theater.getText() + theater.getName());
        t_movie.setText(t_movie.getText() + movie.getTitle());
        t_screen.setText(t_screen.getText() + screen.getName() + "관");
        t_date.setText(t_date.getText() + date[0]);
        t_start_time.setText(t_start_time.getText() + date[1]);
        t_price.setText(t_price.getText() + price + "원");
        for (int i = 0; i < row_list.size(); i++)
        {
            t_seat.setText(t_seat.getText() + row_list.get(i) + col_list.get(i) + "`");
        }
    }
    
}
