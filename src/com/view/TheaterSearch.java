package com.view;

import java.util.Iterator;

import com.db.model.ChargeDAO;
import com.db.model.ChargeDTO;
import com.db.model.TheaterDTO;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TheaterSearch
{
	@FXML
	private Text t_theater_name;
	
	@FXML
	private Text t_screen;
	
	@FXML
	private Text t_total_seat;
	
	@FXML
	private Text t_address;
	
	@FXML
	private WebView map;
	
	@FXML
	private Text t_type_1;
	
	@FXML
	private Text t_type_2;
	
	@FXML
	private Text t_type_3;
	
	public void initData(TheaterDTO theater)
	{
		try
		{
			WebEngine webEngine = map.getEngine();
			
			map.getEngine().getLoadWorker().stateProperty().addListener((obs, old, neww) ->
			{
				if (neww == Worker.State.SUCCEEDED)
				{
					webEngine.executeScript("loadMap('" + theater.getAddress() + "');");
				}
			});
			webEngine.load(TheaterSearch.class.getResource("./xml/user_sub_page/naver_map.html").toExternalForm());
			
			t_theater_name.setText(theater.getName());
			t_screen.setText(theater.getTotalScreen() + " 개관");
			t_total_seat.setText(theater.getTotalSeats() + "석");
			t_address.setText("○ " + theater.getAddress());
			
			ChargeDAO cDao = new ChargeDAO();
			Iterator<ChargeDTO> cIter = cDao.getChargeList().iterator();
			while (cIter.hasNext())
			{
				ChargeDTO temp = cIter.next();
				switch (temp.getType())
				{
					case "1":
					{
						t_type_1.setText(Integer.toString(temp.getPrice()));
						break;
					}
					case "2":
					{
						t_type_2.setText(Integer.toString(temp.getPrice()));
						break;
					}
					case "3":
					{
						t_type_3.setText(Integer.toString(temp.getPrice()));
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
