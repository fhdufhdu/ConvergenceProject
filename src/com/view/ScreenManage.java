package com.view;

import java.sql.SQLException;

import com.db.model.DAOException;
import com.db.model.ScreenDTO;
import com.db.model.TheaterDTO;
import com.main.mainGUI;
import com.protocol.Protocol;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class ScreenManage
{
    private ObservableList<ScreenDTO> screen_list;
    private ScreenDTO table_row_data;
    private TheaterDTO theater;
    
    private String err_dao, err_type;
    
    @FXML
    private BorderPane bp_parent;
    
    @FXML
    private Text theater_name;
    
    @FXML
    private TableView<ScreenDTO> tv_screen;
    
    @FXML
    private TableColumn<ScreenDTO, String> tc_name;
    
    @FXML
    private TableColumn<ScreenDTO, String> tc_capacity;
    
    @FXML
    private TableColumn<ScreenDTO, String> tc_row;
    
    @FXML
    private TableColumn<ScreenDTO, String> tc_col;
    
    @FXML
    private Text t_result;
    
    @FXML
    private TextField tf_name;
    
    @FXML
    private TextField tf_capacity;
    
    @FXML
    private TextField tf_row;
    
    @FXML
    private TextField tf_col;
    
    @FXML
    private Button btn_add_screen;
    
    @FXML
    private Button btn_change_screen;
    
    @FXML
    private Button btn_delete_screen;
    
    @FXML
    private Button btn_clear;
    
    @FXML
    void addScreen(ActionEvent event) throws Exception
    {
        try
        {
            String name = tf_name.getText();
            String capacity = tf_capacity.getText();
            String row = tf_row.getText();
            String col = tf_col.getText();
            
            mainGUI.writePacket(Protocol.PT_REQ_RENEWAL + "/" + Protocol.CS_REQ_SCREEN_ADD + "/" + theater.getId() + "/" + name + "/" + capacity + "/" + row + "/" + col);
            
            while (true)
            {
                String packet = mainGUI.readLine();
                String packetArr[] = packet.split("/");
                String packetType = packetArr[1];
                
                if (packetType.equals(Protocol.SC_RES_THEATER_ADD))
                {
                    String result = packetArr[2];
                    switch (result)
                    {
                        case "1":
                        {
                            // 값 추가 후 각 테이블 및 리스트 초기화
                            screen_list.clear();
                            tv_screen.getItems().clear();
                            initList();
                            tv_screen.setItems(screen_list);
                            
                            // text field 초기화
                            clearText();
                            return;
                        }
                        case "2":
                        {
                            t_result.setText("상영관 등록 실패!");
                            return;
                        }
                    }
                }
            }
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText(err_dao);
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // DB관련 문제 발생시
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            // 입력값 타입이 맞지 않을때
            t_result.setText(err_type);
            e.printStackTrace();
        }
    }
    
    @FXML
    void changeScreen(ActionEvent event) throws Exception
    {
        try
        {
            // 테이블 값이 선택되어 있는지 확인
            if (tv_screen.getSelectionModel().isEmpty())
            {
                alert("수정오류", "수정할 데이터를 선택해주세요");
                return;
            }
            
            String name = tf_name.getText();
            String capacity = tf_capacity.getText();
            String row = tf_row.getText();
            String col = tf_col.getText();
            
            mainGUI.writePacket(Protocol.PT_REQ_RENEWAL + "/" + Protocol.CS_REQ_SCREEN_CHANGE + "/" + table_row_data.getId() + "/" + theater.getId() + "/" + name + "/" + capacity + "/" + row + "/" + col);
            
            while (true)
            {
                String packet = mainGUI.readLine();
                String packetArr[] = packet.split("/");
                String packetType = packetArr[1];
                
                if (packetType.equals(Protocol.SC_RES_THEATER_CHANGE))
                {
                    String result = packetArr[2];
                    switch (result)
                    {
                        case "1":
                        {
                            screen_list.clear();
                            tv_screen.getItems().clear();
                            initList();
                            tv_screen.setItems(screen_list);
                            
                            clearText();
                            return;
                        }
                        case "2":
                        {
                            t_result.setText("상영관 수정 실패!");
                            return;
                        }
                    }
                }
            }
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText(err_dao);
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // DB관련 문제 발생시
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            // 입력값 타입이 맞지 않을때
            t_result.setText(err_type);
            e.printStackTrace();
        }
    }
    
    @FXML
    void clearTextField(ActionEvent event)
    {
        clearText();
    }
    
    @FXML
    void deleteScreen(ActionEvent event) throws Exception
    {
        try
        {
            if (tv_screen.getSelectionModel().isEmpty())
            {
                alert("삭제오류", "삭제할 데이터를 선택해주세요");
                return;
            }
            
            // 삭제할 것인지 재 확인
            ButtonType btnType = confirm("삭제확인", "정말로 삭제하시겠습니까?");
            if (btnType != ButtonType.OK)
            {
                return;
            }
            
            String id = table_row_data.getId();
            
            mainGUI.writePacket(Protocol.PT_REQ_RENEWAL + "/" + Protocol.CS_REQ_SCREEN_DELETE + "/" + id);
            
            while (true)
            {
                String packet = mainGUI.readLine();
                String packetArr[] = packet.split("/");
                String packetType = packetArr[1];
                
                if (packetType.equals(Protocol.SC_RES_THEATER_DELETE))
                {
                    String result = packetArr[2];
                    switch (result)
                    {
                        case "1":
                        {
                            t_result.setText("삭제되었습니다");
                            
                            screen_list.clear();
                            tv_screen.getItems().clear();
                            initList();
                            tv_screen.setItems(screen_list);
                            
                            clearText();
                            return;
                        }
                        case "2":
                        {
                            t_result.setText("상영관 삭제 실패!");
                            return;
                        }
                    }
                }
            }
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText(err_dao);
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            // DB관련 문제 발생시
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            // 입력값 타입이 맞지 않을때
            t_result.setText(err_type);
            e.printStackTrace();
        }
    }
    
    // 리스트 초기화
    private void initList()
    {
        try
        {
            mainGUI.writePacket(Protocol.PT_REQ_VIEW + "/" + Protocol.CS_REQ_SCREEN_VIEW + "/" + theater.getId());
            
            while (true)
            {
                String packet = mainGUI.readLine();
                String packetArr[] = packet.split("/");
                String packetCode = packetArr[1];
                
                if (packetCode.equals(Protocol.SC_RES_SCREEN_VIEW))
                {
                    String result = packetArr[2];
                    String id = packetArr[3];
                    String theater_id = packetArr[4];
                    String name = packetArr[5];
                    String capacity = packetArr[6];
                    String row = packetArr[7];
                    String col = packetArr[8];
                    String last = packetArr[9];
                    
                    switch (result)
                    {
                        case "1":
                        {
                            screen_list.add(new ScreenDTO(id, theater_id, name, Integer.valueOf(capacity), Integer.valueOf(row), Integer.valueOf(col)));
                            break;
                        }
                        case "2":
                        {
                            t_result.setText("상영관 리스트가 없습니다.");
                            return;
                        }
                        case "3":
                        {
                            t_result.setText("상영관 리스트 요청 실패했습니다.");
                            return;
                        }
                    }
                    if (last.equals("1"))
                        break;
                }
            }
        }
        catch (Exception e)
        {
            t_result.setText("상영관 리스트 요청 실패했습니다.");
            e.printStackTrace();
        }
    }
    
    // 이전 컨트롤러에서 값 받아오기, 상영관 관리에는 해당하는 영화관 객체가 필요
    void initData(TheaterDTO t)
    {
        try
        {
            err_dao = "상영관 이름이 중복됩니다!";
            err_type = "총 좌석, 최대 행, 최대 열에는 숫자만 입력해주세요!";
            
            theater = t;
            theater_name.setText(theater.getName() + "의 상영관 리스트");
            screen_list = FXCollections.observableArrayList();
            
            initList();
            
            tv_screen.getItems().clear();
            
            tc_name.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
            tc_capacity.setCellValueFactory(cellData -> cellData.getValue().getTotalCapacityProperty());
            tc_row.setCellValueFactory(cellData -> cellData.getValue().getMaxRowProperty());
            tc_col.setCellValueFactory(cellData -> cellData.getValue().getMaxColProperty());
            
            tv_screen.setItems(screen_list);
            
            tv_screen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ScreenDTO>()
            {
                @Override
                public void changed(ObservableValue<? extends ScreenDTO> observable, ScreenDTO oldValue, ScreenDTO newValue)
                {
                    table_row_data = tv_screen.getSelectionModel().getSelectedItem();
                    if (table_row_data != null)
                    {
                        tf_name.setText(table_row_data.getName());
                        tf_capacity.setText(Integer.toString(table_row_data.getTotalCapacity()));
                        tf_row.setText(Integer.toString(table_row_data.getMaxRow()));
                        tf_col.setText(Integer.toString(table_row_data.getMaxCol()));
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void clearText()
    {
        tf_name.clear();
        tf_capacity.clear();
        tf_row.clear();
        tf_col.clear();
        t_result.setText("");
    }
    
    private void alert(String head, String msg)
    {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("경고");
        alert.setHeaderText(head);
        alert.setContentText(msg);
        
        alert.showAndWait(); // Alert창 보여주기
    }
    
    private ButtonType confirm(String head, String msg)
    {
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("확인");
        confirm.setHeaderText(head);
        confirm.setContentText(msg);
        return confirm.showAndWait().get();
        
    }
    
}
