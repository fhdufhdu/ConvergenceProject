package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.db.model.MovieDTO;
import com.main.mainGUI;
import com.protocol.Protocol;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class MovieSoon implements Initializable
{
	
	@FXML
	private GridPane gp_soon_movie;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		try
		{
			mainGUI.writePacket(Protocol.PT_REQ_VIEW + "`" + Protocol.CS_REQ_MOVIE_VIEW + "`%`1976-01-01`2222-01-01`%`%`%`2");
			ArrayList<MovieDTO> m_list = new ArrayList<MovieDTO>();
			
			while (true)
			{
				String packet = mainGUI.readLine();
				String packetArr[] = packet.split("!"); // 패킷 분할
				String packetType = packetArr[0];
				String packetCode = packetArr[1];
				
				if (packetType.equals(Protocol.PT_RES_VIEW) && packetCode.equals(Protocol.SC_RES_MOVIE_VIEW))
				{
					String result = packetArr[2];
					
					switch (result)
					{
						case "1":
						{
							String movieList = packetArr[3];
							String listArr[] = movieList.split(","); // 각 영화별로 리스트 분할
							
							for (String listInfo : listArr)
							{
								String infoArr[] = listInfo.split("`"); // 영화 별 정보 분할
								String mv_id = infoArr[0];
								String mv_title = infoArr[1];
								String mv_release_date = infoArr[2];
								String mv_is_current = infoArr[3];
								String mv_plot = infoArr[4];
								String mv_poster_path = infoArr[5];
								String mv_stillCut_path = infoArr[6];
								String mv_trailer_path = infoArr[7];
								String mv_director = infoArr[8];
								String mv_actor = infoArr[9];
								int mv_min = Integer.parseInt(infoArr[10]);
								
								m_list.add(new MovieDTO(mv_id, mv_title, mv_release_date, mv_is_current, mv_plot, mv_poster_path, mv_stillCut_path, mv_trailer_path, mv_director, mv_actor, mv_min));
							}

							for (int i = 0; i < m_list.size(); i++)
							{
								FXMLLoader loader = new FXMLLoader(MovieTable.class.getResource("./xml/user_sub_page/movie_soon_sub.fxml"));
								Parent root = loader.load();
								MovieSoonSub controller = loader.<MovieSoonSub>getController();
								controller.initData(m_list.get(i));
								gp_soon_movie.add(root, i % 4, i / 4);
							}
							return;
						}
						case "2":
						{
							mainGUI.alert("영화 리스트", "영화 리스트가 없습니다.");
							return;
						}
						case "3":
						{
							mainGUI.alert("영화 리스트", "영화 리스트 요청 실패했습니다.");
							return;
						}
					}
				}
			}
			/*
			 * gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 2, 0); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 3, 0); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 0, 1); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 1, 1); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 2, 1); gp_soon_movie.add(FXMLLoader.load(MovieTable.class.getResource("./xml/user_sub_page/movie_grid_sub.fxml")), 3, 1);
			 */
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
