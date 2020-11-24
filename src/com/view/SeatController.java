package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SeatController implements Initializable
{
    @FXML
    private VBox vbox;
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private Rectangle screen_shape;
    
    @FXML
    private Text screen_text;
    
    @FXML
    private Button btn_select;
    
    @FXML
    private Text selected_seat;
    
    private ToggleButton[][] tb_arr;
    
    private int row;
    private int col;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        /*
         * ArrayList<ButtonBar> a = new ArrayList<ButtonBar>(); ArrayList<Button> b = new ArrayList<Button>(); a.add(new ButtonBar()); a.add(new ButtonBar()); a.add(new ButtonBar()); b.add(new Button("1")); b.add(new Button("2")); b.add(new Button("3")); ButtonBar.setButtonData(b.get(0), ButtonData.APPLY); ButtonBar.setButtonData(b.get(1), ButtonData.APPLY); ButtonBar.setButtonData(b.get(2), ButtonData.APPLY); a.get(0).getButtons().addAll(b); vbox.getChildren().addAll(a.get(0));
         */
        
        ArrayList<ButtonBar> bar_list = new ArrayList<ButtonBar>();
        row = 7;
        col = 8;
        tb_arr = new ToggleButton[row + 1][col + 1];
        
        for (int i = 0; i < row + 1; i++)
        {
            bar_list.add(new ButtonBar());
            for (int j = 0; j < col + 1; j++)
            {
                tb_arr[i][j] = new ToggleButton();
                tb_arr[i][j].setOnAction(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        Iterator<String> iter = checkSelected().iterator();
                        String result = "선택한 좌석 : ";
                        while (iter.hasNext())
                        {
                            result += iter.next() + " / ";
                        }
                        selected_seat.setText(result);
                    }
                });
                tb_arr[i][j].setStyle("-fx-font-size: 8;");
                tb_arr[i][j].setTextFill(Color.BLACK);
                if (i == 0 && j == 0)
                {
                    tb_arr[i][j].setDisable(true);
                    tb_arr[i][j].setStyle("-fx-font-size: 8;-fx-background-color: transparent;");
                }
                else if (i == 0 && j != 0)
                {
                    tb_arr[i][j].setDisable(true);
                    tb_arr[i][j].setText(Integer.toString(j));
                    tb_arr[i][j].setStyle("-fx-font-size: 8;-fx-background-color: transparent;");
                }
                else if (i != 0 && j == 0)
                {
                    tb_arr[i][j].setDisable(true);
                    tb_arr[i][j].setText(Character.toString((char) (64 + i)));
                    tb_arr[i][j].setStyle("-fx-font-size: 8;-fx-background-color: transparent;");
                }
                tb_arr[i][j].setMaxSize(10, 10);
                ButtonBar.setButtonData(tb_arr[i][j], ButtonData.APPLY);
                bar_list.get(i).getButtons().addAll(tb_arr[i][j]);
            }
            bar_list.get(i).setMinSize(0, 0);
            bar_list.get(i).setPrefSize(25, 20);
            bar_list.get(i).setMaxSize(25, 20);
            bar_list.get(i).setButtonMinWidth(25);
        }
        vbox.getChildren().addAll(bar_list);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        
        root.setPrefSize((col + 1) * 36, (row + 1) * 36 + 180);
        vbox.setPrefSize((col + 1) * 35, (row + 1) * 35);
        double root_width, root_height;
        
        System.out.println(root.getPrefWidth());
        
        if (root.getPrefWidth() < root.getMinWidth())
        {
            root_width = root.getMinWidth();
            root_height = root.getMinHeight();
            vbox.setLayoutX(-30);
        }
        else
        {
            root_width = root.getPrefWidth();
            root_height = root.getPrefHeight();
            vbox.setLayoutX(-10);
        }
        screen_shape.setLayoutX(root_width / 2 - screen_shape.getWidth() / 2);
        screen_text.setLayoutX(root_width / 2 - screen_text.getWrappingWidth() / 2);
        btn_select.setLayoutX(root_width - 90);
    }
    
    @FXML
    void btn_action(ActionEvent event)
    {
        checkSelected();
    }
    
    private ArrayList<String> checkSelected()
    {
        ArrayList<String> result_list = new ArrayList<String>();
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                if (tb_arr[i][j].isSelected())
                {
                    result_list.add(Character.toString((char) (64 + i)) + Integer.toString(j));
                }
            }
        }
        return result_list;
    }
}
