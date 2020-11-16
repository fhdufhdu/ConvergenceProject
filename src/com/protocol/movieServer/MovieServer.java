package com.protocol.movieServer;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import com.db.model.*;
import com.main.mainGUI;
import com.protocol.*;

public class MovieServer extends Thread {
	Socket socket;
	private MovieDB movie_DB;
	private String currentID;
	private boolean admit;
	private String memberConfirm;
	private static int currUser = 0;
	public static int cnt = 1;
	BufferedReader br = null;
	BufferedWriter bw = null;

	public MovieServer(Socket socket) throws ClassNotFoundException, SQLException 
	{
		this.socket = socket;
		currentID = "NULL";
		admit = false;
		//movie_DB = new MovieDB("jdbc:oracle:thin:@192.168.224.250:1521:xe", "MT", "1234");
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

			writePacket(Protocol.SC_REQ_LOGIN);
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
					case Protocol.CS_REQ_LOGIN: // 로그인 정보 수신
					{
						System.out.println("클라이언트가  로그인 정보를 보냈습니다");
						String login_id = packetArr[1];
						String login_passwd = packetArr[2];
						try 
						{
							MemberDAO mDao = new MemberDAO();
							MemberDTO mDto = mDao.getMember(login_id, login_passwd);

							if (mDto.getRole().equals("1"))
								writePacket(Protocol.SC_RES_LOGIN + "/1");
							else
								writePacket(Protocol.SC_RES_LOGIN + "/2");
							System.out.println("로그인 성공"); // 성공시 인터페이스 홈 접속
							
						} 
						catch (Exception e) 
						{
							writePacket(Protocol.SC_RES_LOGIN + "/3");
							System.out.println("로그인 실패"); // 실패시 메시지 창 출력 및 재입력 유도
						}
						System.out.println("로그인 처리 결과 전송");
						break;
					}
					case Protocol.CS_REQ_SIGNUP:
					{
						System.out.println("클라이언트가  회원가입 정보를 보냈습니다");
						String role = packetArr[1]; // 구분(사용자, 관리자)
						String signUp_id = packetArr[2]; // 아이디
						String signUp_password = packetArr[3]; // 암호
						String name = packetArr[4]; // 이름
						String gender = packetArr[5]; // 성별
						String phone_number = packetArr[6]; // 연락처
						String birth = packetArr[7];
						String account = packetArr[8]; // 계좌 번호
						MemberDAO signUpDAO = new MemberDAO();
						signUpDAO.addMember(new MemberDTO(signUp_id, role, signUp_password, account, name, phone_number,
								birth, gender));
						writePacket(Protocol.SC_RES_SIGNUP);
						break;
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

	public void writePacket(String source) throws Exception {
		try 
		{
			bw.write(source);
			bw.newLine();
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