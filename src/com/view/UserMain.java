package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.db.model.TheaterDAO;
import com.db.model.TheaterDTO;
import com.main.mainGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class UserMain implements Initializable
{
    private ArrayList<TheaterDTO> theater_list;
    private TheaterDTO selectedTheater;
    
    @FXML
    private MenuButton mb_theater;
    
    @FXML
    private BorderPane user_parent;
    
    @FXML
    private ScrollPane sp_user_main;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        try
        {
            TheaterDAO tDao = new TheaterDAO();
            theater_list = tDao.getTheaterList();
            for (int i = 0; i < theater_list.size(); i++)
            {
                MenuItem theater_name = new MenuItem(theater_list.get(i).getName());
                theater_name.setId(Integer.toString(i));
                theater_name.setOnAction(new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent event)
                    {
                        try
                        {
                            FXMLLoader loader = new FXMLLoader(UserMain.class.getResource("./xml/user_sub_page/theater_search.fxml"));
                            Parent root = (Parent) loader.load();
                            TheaterSearch controller = loader.<TheaterSearch>getController();
                            controller.initData(theater_list.get(Integer.valueOf(theater_name.getId())));
                            
                            user_parent.setCenter(root);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                mb_theater.getItems().add(theater_name);
            }
            final double SPEED = 0.005;
            sp_user_main.getContent().setOnScroll(scrollEvent ->
            {
                double deltaY = scrollEvent.getDeltaY() * SPEED;
                sp_user_main.setVvalue(sp_user_main.getVvalue() - deltaY);
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mainGUI.alert("오류", "DB서버 연결 오류");
        }
    }
}
