package com.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Payment
{
    
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
    private PasswordField pf_passwd;
    
    @FXML
    void getPayment(ActionEvent event)
    {
        
    }
    
    public void initData()
    {
        
    }
    
}
