package com.protocol.movieServer;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Date;
import com.db.model.*;
import com.main.mainGUI;
import com.protocol.*;

public class MovieServer extends Thread
{
	Socket socket;
	private String currentID;
	private boolean admit;
	private static int currUser = 0;
	public static int cnt = 1;
	BufferedReader br = null;
	BufferedWriter bw = null;
	
	public MovieServer(Socket socket) throws ClassNotFoundException, SQLException
	{
		this.socket = socket;
		currentID = "NULL";
		admit = false;
		DAO.connectDB();
		System.out.println("현재 사용자 수 :" + ++currUser);
	}
	
	@Override
	public void run()
	{
		try
		{
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			writePacket(Protocol.PT_REQ_LOGIN_INFO);
			boolean program_stop = false;
			
			while (true)
			{
				String packet = br.readLine();
				String packetArr[] = packet.split("/");
				String packetType = packetArr[0];
				
				switch (packetType)
				{
					case Protocol.PT_EXIT: // 프로그램 종료 수신
					{
						writePacket(Protocol.PT_EXIT);
						program_stop = true;
						System.out.println("서버종료");
						break;
					}
					
					case Protocol.PT_REQ_LOGIN: // 로그인 정보 수신
					{
						System.out.println("클라이언트가  로그인 정보를 보냈습니다");
						String login_id = packetArr[1];
						String login_passwd = packetArr[2];
						try
						{
							MemberDAO mDao = new MemberDAO();
							MemberDTO mDto = mDao.getMember(login_id, login_passwd);
							
							if (mDto.getRole().equals("1"))
								writePacket(Protocol.PT_RES_LOGIN + "/1");
							else
								writePacket(Protocol.PT_RES_LOGIN + "/2");
							System.out.println("로그인 성공"); // 성공시 인터페이스 홈 접속
							
						}
						catch (Exception e)
						{
							writePacket(Protocol.PT_RES_LOGIN + "/3");
							System.out.println("로그인 실패"); // 실패시 메시지 창 출력 및 재입력 유도
						}
						System.out.println("로그인 처리 결과 전송");
						break;
					}
					
					case Protocol.PT_REQ_VIEW:
					{
						String packetCode = packetArr[1];
						
						switch (packetCode)
						{
							case Protocol.CS_REQ_THEATER_VIEW:
							{
								try
								{
									System.out.println("클라이언트가 영화관 리스트 요청을 보냈습니다.");
									TheaterDAO tDao = new TheaterDAO();
									ArrayList<TheaterDTO> tlist = tDao.getTheaterList();
									Iterator<TheaterDTO> tIter = tlist.iterator();
									TheaterDTO tDto;
									while (tIter.hasNext())
									{
										tDto = tIter.next();
										if (tIter.hasNext())
											writePacket(Protocol.PT_RES_VIEW + "/" + Protocol.SC_RES_THEATER_VIEW + "/1/" + tDto.getId() + "/" + tDto.getName() + "/" + tDto.getAddress() + "/" + tDto.getTotalScreen() + "/" + tDto.getTotalSeats() + "/0");
										else
											writePacket(Protocol.PT_RES_VIEW + "/" + Protocol.SC_RES_THEATER_VIEW + "/1/" + tDto.getId() + "/" + tDto.getName() + "/" + tDto.getAddress() + "/" + tDto.getTotalScreen() + "/" + tDto.getTotalSeats() + "/1");
									}
									break;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_VIEW + "/" + Protocol.SC_RES_THEATER_VIEW + "/2");
									break;
								}
							}
							
							case Protocol.CS_REQ_SCREEN_VIEW:
							{
								try
								{
									System.out.println("클라이언트가 상영관 리스트 요청을 보냈습니다.");
									String id = packetArr[2];
									
									ScreenDAO sDao = new ScreenDAO();
									ArrayList<ScreenDTO> slist = sDao.getScreenList(id);
									Iterator<ScreenDTO> sIter = slist.iterator();
									
									if (sIter.hasNext() == false)
									{
										writePacket(Protocol.PT_RES_VIEW + "/" + Protocol.SC_RES_SCREEN_VIEW + "/2");
									}
									
									while (sIter.hasNext())
									{
										ScreenDTO sDto = sIter.next();
										if (sIter.hasNext())
											writePacket(Protocol.PT_RES_VIEW + "/" + Protocol.SC_RES_SCREEN_VIEW + "/1/" + sDto.getId() + "/" + id + "/" + sDto.getName() + "/" + sDto.getTotalCapacity() + "/" + sDto.getMaxRow() + "/" + sDto.getMaxCol() + "/0");
										else
											writePacket(Protocol.PT_RES_VIEW + "/" + Protocol.SC_RES_SCREEN_VIEW + "/1/" + sDto.getId() + "/" + id + "/" + sDto.getName() + "/" + sDto.getTotalCapacity() + "/" + sDto.getMaxRow() + "/" + sDto.getMaxCol() + "/1");
									}
								}
								catch (Exception e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SCREEN_VIEW + "/3");
									break;
								}
							}
						}
					}
					
					case Protocol.PT_REQ_RENEWAL:
					{
						String packetCode = packetArr[1];
						
						switch (packetCode)
						{
							case Protocol.CS_REQ_SIGNUP:
							{
								try
								{
									System.out.println("클라이언트가  회원가입 정보를 보냈습니다");
									String role = packetArr[2]; // 구분(사용자, 관리자)
									String signUp_id = packetArr[3]; // 아이디
									String signUp_password = packetArr[4]; // 암호
									String name = packetArr[5]; // 이름
									String phone_number = packetArr[6]; // 연락처
									String birth = packetArr[7];
									String gender = packetArr[8]; // 성별
									
									MemberDAO signUpDAO = new MemberDAO();
									signUpDAO.addMember(new MemberDTO(signUp_id, role, signUp_password, null, name, phone_number, birth, gender));
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SIGNUP + "/1");
									break;
								}
								catch (DAOException e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SIGNUP + "/2");
									break;
								}
							}
							
							case Protocol.CS_REQ_THEATER_ADD:
							{
								try
								{
									System.out.println("클라이언트가 영화관 등록 요청를 보냈습니다.");
									String name = packetArr[2]; // 영화관 id
									String address = packetArr[3]; // 영화관 주소
									String screen = packetArr[4]; // 총 스크린 수
									String seat = packetArr[5]; // 총 좌석 수
									
									TheaterDAO theaterDAO = new TheaterDAO();
									TheaterDTO theaterDTO = new TheaterDTO(DTO.EMPTY_ID, name, address, Integer.valueOf(screen), Integer.valueOf(seat));
									
									theaterDAO.addTheater(theaterDTO);
									
									System.out.println("영화관 등록 성공");
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_THEATER_ADD + "/1");
									break;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_THEATER_ADD + "/2");
									break;
								}
							}
							
							case Protocol.CS_REQ_THEATER_CHANGE:
							{
								try
								{
									System.out.println("클라이언트가 영화관 수정 요청를 보냈습니다.");
									String id = packetArr[2]; // 영화관 ID
									String name = packetArr[3]; // 영화관 이름
									String address = packetArr[4]; // 영화관 주소
									String screen = packetArr[5]; // 총 스크린 수
									String seat = packetArr[6]; // 총 좌석 수
									
									TheaterDAO theaterDAO = new TheaterDAO();
									TheaterDTO theaterDTO = new TheaterDTO(id, name, address, Integer.valueOf(screen), Integer.valueOf(seat));
									
									theaterDAO.changeTheater(theaterDTO);
									
									System.out.println("영화관 수정 성공");
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_THEATER_CHANGE + "/1");
									break;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_THEATER_CHANGE + "/2");
									break;
								}
							}
							
							case Protocol.CS_REQ_THEATER_DELETE:
							{
								try
								{
									System.out.println("클라이언트가 영화관 삭제 요청을 보냈습니다.");
									String id = packetArr[2]; // 영화관 ID
									
									TheaterDAO theaterDAO = new TheaterDAO();
									theaterDAO.removeTheater(id);
									
									System.out.println("영화관 삭제 성공");
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_THEATER_DELETE + "/1");
									break;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_THEATER_DELETE + "/2");
									break;
								}
							}
							
							case Protocol.CS_REQ_SCREEN_ADD:
							{
								try
								{
									System.out.println("클라이언트가 상영관 등록 요청을 보냈습니다.");
									String theater_id = packetArr[2];
									String name = packetArr[3];
									String capacity = packetArr[4];
									String row = packetArr[5];
									String col = packetArr[6];
									
									ScreenDAO sDao = new ScreenDAO();
									ScreenDTO sDto = new ScreenDTO(DTO.EMPTY_ID, theater_id, name, Integer.valueOf(capacity), Integer.valueOf(row), Integer.valueOf(col));
									
									sDao.addScreen(sDto);
									
									System.out.println("상영관 등록 성공");
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SCREEN_ADD + "/1");
									break;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SCREEN_ADD + "/2");
									break;
								}
							}
							
							case Protocol.CS_REQ_SCREEN_CHANGE:
							{
								try
								{
									System.out.println("클라이언트가 상영관 수정 요청을 보냈습니다.");
									String id = packetArr[2];
									String theater_id = packetArr[3];
									String name = packetArr[4];
									String capacity = packetArr[5];
									String row = packetArr[6];
									String col = packetArr[7];
									
									ScreenDAO sDao = new ScreenDAO();
									ScreenDTO sDto = new ScreenDTO(id, theater_id, name, Integer.valueOf(capacity), Integer.valueOf(row), Integer.valueOf(col));
									
									sDao.changeScreen(sDto);
									
									System.out.println("상영관 수정 성공");
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SCREEN_CHANGE + "/1");
									break;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SCREEN_CHANGE + "/2");
									break;
								}
							}
							
							case Protocol.CS_REQ_SCREEN_DELETE:
							{
								try
								{
									System.out.println("클라이언트가 상영관 삭제 요청을 보냈습니다.");
									String id = packetArr[2];
									
									ScreenDAO sDao = new ScreenDAO();
									sDao.removeScreen(id);
									
									System.out.println("상영관 삭제 성공");
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SCREEN_DELETE + "/1");
									break;
								}
								catch (Exception e)
								{
									e.printStackTrace();
									writePacket(Protocol.PT_RES_RENEWAL + "/" + Protocol.SC_RES_SCREEN_DELETE + "/2");
									break;
								}
							}
						}
					}
				}// end switch
				
				if (program_stop)
					break;
			} // end while
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			currUser--;
			socket.close();
			System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "가 접속종료 하였습니다.");
			System.out.println("현재 사용자 수:" + currUser);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writePacket(String source) throws Exception
	{
		try
		{
			bw.write(source + "\n");
			bw.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Socket getSocket()
	{
		return socket;
	}
}