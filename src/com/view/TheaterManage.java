//package com.view;
//
//import java.net.*;
//import java.util.*;
//import java.lang.*;
//import java.time.format.*;
//import java.sql.*;
//
//import com.db.model.*;
//
//import javafx.beans.value.*;
//import javafx.collections.*;
//import javafx.event.*;
//import javafx.fxml.*;
//import javafx.scene.*;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.scene.text.*;
//import javafx.stage.*;
//import javafx.application.*;
//import javafx.scene.control.Alert.*;
//import javafx.scene.input.*;
//
//public class TheaterManage implements Initializable {
//
//    private ObservableList<TheaterDTO> theater_list;
//    private TheaterDTO table_row_data;
//
//    @FXML
//    private BorderPane bp_parent;
//
//    @FXML
//    private TableView<TheaterDTO> tv_theater;
//
//    @FXML
//    private TableColumn<TheaterDTO, String> tc_name;
//
//    @FXML
//    private TableColumn<TheaterDTO, String> tc_address;
//
//    @FXML
//    private TableColumn<TheaterDTO, String> tc_screen;
//
//    @FXML
//    private TableColumn<TheaterDTO, String> tc_seat;
//
//    @FXML
//    private TextField tf_seat;
//
//    @FXML
//    private TextField tf_screen;
//
//    @FXML
//    private TextField tf_name;
//
//    @FXML
//    private TextField tf_address;
//
//    @FXML
//    private Button btn_add_theater;
//
//    @FXML
//    private Button btn_change_theater;
//
//    @FXML
//    private Button btn_delete_theater;
//
//    @FXML
//    private Text t_result;
//
//    @FXML
//    private Button btn_clear;
//
//    @FXML
//    private Button btn_screen_manage;
//
//    //뷰가 불러와지는 동시에 실행될 것들
//    @Override
//    public void initialize(URL location, ResourceBundle resources)
//    {
//        try
//        {
//            //테이블 뷰에 넣을 리스트 세팅
//            theater_list = FXCollections.observableArrayList();
//
//            //리스트 초기화
//            initList();
//
//            //테이블 뷰 초기화
//            tv_theater.getItems().clear();
//            
//            //각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
//            tc_name.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
//            tc_address.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
//            tc_screen.setCellValueFactory(cellData -> cellData.getValue().getTotalScreenProperty());
//            tc_seat.setCellValueFactory(cellData -> cellData.getValue().getTotalSeatsProperty());
//
//            //테이블 뷰와 리스트를 연결
//            tv_theater.setItems(theater_list);
//
//            //테이블 뷰 row 선택 시 발생하는 이벤트 지정
//            tv_theater.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TheaterDTO>() 
//            {
//                @Override
//                public void changed(ObservableValue<? extends TheaterDTO> observable, TheaterDTO oldValue, TheaterDTO newValue) 
//                {
//                    table_row_data = tv_theater.getSelectionModel().getSelectedItem();
//                    if(table_row_data != null)
//                    {
//                        tf_name.setText(table_row_data.getName());
//                        tf_address.setText(table_row_data.getAddress());
//                        tf_screen.setText(Integer.toString(table_row_data.getTotalScreen()));
//                        tf_seat.setText(Integer.toString(table_row_data.getTotalSeats()));
//                    }
//                }
//            });
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    //상영관 추가
//    @FXML
//    void addTheater(ActionEvent event) 
//    {
//        try
//        {
//            TheaterDAO tDao = new TheaterDAO();
//            TheaterDTO tDto = new TheaterDTO(DTO.EMPTY_ID, tf_name.getText(), tf_address.getText(), Integer.valueOf(tf_screen.getText()), Integer.valueOf(tf_seat.getText()));
//                                    
//            tDao.addTheater(tDto);
//
//            //값 추가 후 각 테이블 및 리스트 초기화
//            theater_list.clear();
//            tv_theater.getItems().clear();
//            initList();
//            tv_theater.setItems(theater_list);
//
//            //text field 초기화
//            clearText();
//        }
//        catch(DAOException e)
//        {
//            //값의 중복 발생시
//            t_result.setText("영화관 이름 및 주소가 중복됩니다!");
//            e.printStackTrace();
//        } 
//        catch(SQLException e)
//        {
//            //DB관련 문제 발생시
//            e.printStackTrace();
//        }
//        catch(NumberFormatException e)
//        {
//            //입력값 타입이 맞지 않을때
//            t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void changeTheater(ActionEvent event) 
//    {
//        try
//        {
//            //테이블 값이 선택되어 있는지 확인
//            if (tv_theater.getSelectionModel().isEmpty()) 
//            {
//                alert("수정오류", "수정할 데이터를 선택해주세요");
//                return;
//            }
//            TheaterDAO tDao = new TheaterDAO();
//            TheaterDTO tDto = new TheaterDTO(table_row_data.getId(), tf_name.getText(), tf_address.getText(), Integer.valueOf(tf_screen.getText()), Integer.valueOf(tf_seat.getText()));
//                                    
//            tDao.changeTheater(tDto);
//
//            theater_list.clear();
//            tv_theater.getItems().clear();
//            initList();
//            tv_theater.setItems(theater_list);
//
//            clearText();
//        }
//        catch(DAOException e)
//        {
//            //값의 중복 발생시
//            t_result.setText("영화관 이름 및 주소가 중복됩니다!");
//            e.printStackTrace();
//        } 
//        catch(SQLException e)
//        {
//            //DB관련 문제 발생시
//            e.printStackTrace();
//        }
//        catch(NumberFormatException e)
//        {
//            //입력값 타입이 맞지 않을때
//            t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void deleteTheater(ActionEvent event) 
//    {
//        try
//        {
//            if (tv_theater.getSelectionModel().isEmpty()) 
//            {
//                alert("삭제오류", "삭제할 데이터를 선택해주세요");
//                return;
//            }
//
//            //삭제할 것인지 재 확인
//            ButtonType btnType = confirm("삭제확인","정말로 삭제하시겠습니까?");
//            if(btnType != ButtonType.OK) 
//            {
//                return;
//            }
//
//            TheaterDAO tDao = new TheaterDAO();                  
//            tDao.removeTheater(table_row_data.getId());
//            t_result.setText("삭제되었습니다");
//
//            theater_list.clear();
//            tv_theater.getItems().clear();
//            initList();
//            tv_theater.setItems(theater_list);
//
//            clearText();
//        }
//        catch(DAOException e)
//        {
//            //값의 중복 발생시
//            t_result.setText("영화관 이름 및 주소가 중복됩니다!");
//            e.printStackTrace();
//        } 
//        catch(SQLException e)
//        {
//            //DB관련 문제 발생시
//            e.printStackTrace();
//        }
//        catch(NumberFormatException e)
//        {
//            //입력값 타입이 맞지 않을때
//            t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
//            e.printStackTrace();
//        }
//    }
//
//    //텍스트 필드 초기화
//    @FXML
//    void clearTextField(ActionEvent event) 
//    {
//        clearText();
//    }
//    
//    //선택한 영화관의 상영관 관리
//    @FXML
//    void manageScreen(ActionEvent event) 
//    {
//        try
//        {
//            if (tv_theater.getSelectionModel().isEmpty()) 
//            {
//                alert("오류", "데이터를 선택해주세요");
//                return;
//            }
//            FXMLLoader loader = new FXMLLoader(TheaterManage.class.getResource("./xml/admin_sub_page/screen_manage.fxml"));
//            Parent root = (Parent) loader.load();
//            ScreenManage controller = loader.<ScreenManage>getController();
//            controller.initData(table_row_data);
//
//            bp_parent.setCenter(root);
//
//            clearText();
//        }
//        catch(DAOException e)
//        {
//            //여긴 중복 발생
//            t_result.setText("영화관 이름 및 주소가 중복됩니다!");
//            e.printStackTrace();
//        } 
//        catch(NumberFormatException e)
//        {
//            //여긴 타입 안맞을때
//            t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
//            e.printStackTrace();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    private void alert(String head, String msg) 
//    {
//		Alert alert = new Alert(AlertType.WARNING);
//		alert.setTitle("경고");
//		alert.setHeaderText(head);
//		alert.setContentText(msg);
//		
//		alert.showAndWait(); //Alert창 보여주기
//    }
//
//    private ButtonType confirm(String head, String msg) 
//    {
//		Alert confirm = new Alert(AlertType.CONFIRMATION);
//		confirm.setTitle("확인");
//		confirm.setHeaderText(head);
//		confirm.setContentText(msg);
//		return confirm.showAndWait().get();
//
//	}
//    
//    private void initList()
//    {
//        try 
//        {
//            TheaterDAO tDao = new TheaterDAO();
//            ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
//            Iterator<TheaterDTO> tIter = tlist.iterator();
//            while(tIter.hasNext())
//            {
//                theater_list.add(tIter.next());
//            }
//        } 
//        catch (Exception e) 
//        {
//            //TODO: handle exception
//        }
//    }
//
//    private void clearText()
//    {
//        tf_name.clear();
//        tf_address.clear();
//        tf_screen.clear();
//        tf_seat.clear();
//        t_result.setText("");
//    }
//}

package com.view;

import java.net.*;
import java.util.*;
import java.lang.*;
import java.time.format.*;
import java.sql.*;

import com.db.model.*;
import com.main.mainGUI;
import com.protocol.Protocol;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.*;

public class TheaterManage implements Initializable
{
    
    private ObservableList<TheaterDTO> theater_list;
    private TheaterDTO table_row_data;
    
    @FXML
    private BorderPane bp_parent;
    
    @FXML
    private TableView<TheaterDTO> tv_theater;
    
    @FXML
    private TableColumn<TheaterDTO, String> tc_name;
    
    @FXML
    private TableColumn<TheaterDTO, String> tc_address;
    
    @FXML
    private TableColumn<TheaterDTO, String> tc_screen;
    
    @FXML
    private TableColumn<TheaterDTO, String> tc_seat;
    
    @FXML
    private TextField tf_seat;
    
    @FXML
    private TextField tf_screen;
    
    @FXML
    private TextField tf_name;
    
    @FXML
    private TextField tf_address;
    
    @FXML
    private Button btn_add_theater;
    
    @FXML
    private Button btn_change_theater;
    
    @FXML
    private Button btn_delete_theater;
    
    @FXML
    private Text t_result;
    
    @FXML
    private Button btn_clear;
    
    @FXML
    private Button btn_screen_manage;
    
    // 뷰가 불러와지는 동시에 실행될 것들
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            // 테이블 뷰에 넣을 리스트 세팅
            theater_list = FXCollections.observableArrayList();
            
            // 리스트 초기화
            initList();
            
            // 테이블 뷰 초기화
            tv_theater.getItems().clear();
            
            // 각 테이블뷰 컬럼에 어떠한 값이 들어갈 것인지 세팅
            tc_name.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
            tc_address.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
            tc_screen.setCellValueFactory(cellData -> cellData.getValue().getTotalScreenProperty());
            tc_seat.setCellValueFactory(cellData -> cellData.getValue().getTotalSeatsProperty());
            
            // 테이블 뷰와 리스트를 연결
            tv_theater.setItems(theater_list);
            
            // 테이블 뷰 row 선택 시 발생하는 이벤트 지정
            tv_theater.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TheaterDTO>()
            {
                @Override
                public void changed(ObservableValue<? extends TheaterDTO> observable, TheaterDTO oldValue, TheaterDTO newValue)
                {
                    table_row_data = tv_theater.getSelectionModel().getSelectedItem();
                    if (table_row_data != null)
                    {
                        tf_name.setText(table_row_data.getName());
                        tf_address.setText(table_row_data.getAddress());
                        tf_screen.setText(Integer.toString(table_row_data.getTotalScreen()));
                        tf_seat.setText(Integer.toString(table_row_data.getTotalSeats()));
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    // 상영관 추가
    @FXML
    void addTheater(ActionEvent event) throws Exception
    {
        try
        {
            String name = tf_name.getText();
            String address = tf_address.getText();
            String screen = tf_screen.getText();
            String seat = tf_seat.getText();
            
            mainGUI.writePacket(Protocol.CS_REQ_THEATER_ADD + "/" + name + "/" + address + "/" + screen + "/" + seat);
            
            while (true)
            {
                String packet = mainGUI.readLine();
                String packetArr[] = packet.split("/");
                String packetType = packetArr[0];
                
                if (packetType.equals(Protocol.SC_RES_THEATER_ADD))
                {
                    String result = packetArr[1];
                    switch (result)
                    {
                        case "1":
                            // 값 추가 후 각 테이블 및 리스트 초기화
                            theater_list.clear();
                            tv_theater.getItems().clear();
                            initList();
                            tv_theater.setItems(theater_list);
                            
                            // text field 초기화
                            clearText();
                            return;
                        case "2":
                            t_result.setText("영화관 등록 실패!");
                            return;
                    }
                }
            }
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText("영화관 이름 및 주소가 중복됩니다!");
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            // 입력값 타입이 맞지 않을때
            t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
            e.printStackTrace();
        }
    }
    
    @FXML
    void changeTheater(ActionEvent event) throws Exception
    {
        try
        {
            // 테이블 값이 선택되어 있는지 확인
            if (tv_theater.getSelectionModel().isEmpty())
            {
                alert("수정오류", "수정할 데이터를 선택해주세요");
                return;
            }
            String id = table_row_data.getId();
            String name = tf_name.getText();
            String address = tf_address.getText();
            String screen = tf_screen.getText();
            String seat = tf_seat.getText();
            
            mainGUI.writePacket(Protocol.CS_REQ_THEATER_CHANGE + "/" + id + "/" + name + "/" + address + "/" + screen + "/" + seat);
            
            while (true)
            {
                String packet = mainGUI.readLine();
                String packetArr[] = packet.split("/");
                String packetType = packetArr[0];
                
                if (packetType.equals(Protocol.SC_RES_THEATER_CHANGE))
                {
                    String result = packetArr[1];
                    switch (result)
                    {
                        case "1":
                            theater_list.clear();
                            tv_theater.getItems().clear();
                            initList();
                            tv_theater.setItems(theater_list);
                            
                            clearText();
                            return;
                        case "2":
                            t_result.setText("영화관 수정 실패!");
                            return;
                    }
                }
            }
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText("영화관 이름 및 주소가 중복됩니다!");
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
            t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
            e.printStackTrace();
        }
    }
    
    @FXML
    void deleteTheater(ActionEvent event) throws Exception
    {
        try
        {
            if (tv_theater.getSelectionModel().isEmpty())
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
            
            mainGUI.writePacket(Protocol.CS_REQ_THEATER_DELETE + "/" + id);
            
            while (true)
            {
                String packet = mainGUI.readLine();
                String packetArr[] = packet.split("/");
                String packetType = packetArr[0];
                
                if (packetType.equals(Protocol.SC_RES_THEATER_DELETE))
                {
                    String result = packetArr[1];
                    switch (result)
                    {
                        case "1":
                            t_result.setText("삭제되었습니다");
                            
                            theater_list.clear();
                            tv_theater.getItems().clear();
                            initList();
                            tv_theater.setItems(theater_list);
                            
                            clearText();
                            return;
                        case "2":
                            t_result.setText("영화관 삭제 실패!");
                            return;
                    }
                }
            }
        }
        catch (DAOException e)
        {
            // 값의 중복 발생시
            t_result.setText("영화관 이름 및 주소가 중복됩니다!");
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
            t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
            e.printStackTrace();
        }
    }
    
    // 텍스트 필드 초기화
    @FXML
    void clearTextField(ActionEvent event)
    {
        clearText();
    }
    
    // 선택한 영화관의 상영관 관리
    @FXML
    void manageScreen(ActionEvent event)
    {
        try
        {
            if (tv_theater.getSelectionModel().isEmpty())
            {
                alert("오류", "데이터를 선택해주세요");
                return;
            }
            FXMLLoader loader = new FXMLLoader(TheaterManage.class.getResource("./xml/admin_sub_page/screen_manage.fxml"));
            Parent root = (Parent) loader.load();
            ScreenManage controller = loader.<ScreenManage>getController();
            controller.initData(table_row_data);
            
            bp_parent.setCenter(root);
            
            clearText();
        }
        catch (DAOException e)
        {
            // 여긴 중복 발생
            t_result.setText("영화관 이름 및 주소가 중복됩니다!");
            e.printStackTrace();
        }
        catch (NumberFormatException e)
        {
            // 여긴 타입 안맞을때
            t_result.setText("총 상영관, 총 좌석에는 숫자만 입력해주세요!");
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
    
    private void initList()
    {
        try
        {
            TheaterDAO tDao = new TheaterDAO();
            ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
            Iterator<TheaterDTO> tIter = tlist.iterator();
            while (tIter.hasNext())
            {
                theater_list.add(tIter.next());
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
    }
    
    private void clearText()
    {
        tf_name.clear();
        tf_address.clear();
        tf_screen.clear();
        tf_seat.clear();
        t_result.setText("");
    }
}
