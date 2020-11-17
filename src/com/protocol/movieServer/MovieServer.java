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
	private String currentID;
	private boolean admit;
	private static int currUser = 0;
	public static int cnt = 1;
	BufferedReader br = null;
	BufferedWriter bw = null;

	public MovieServer(Socket socket) throws ClassNotFoundException, SQLException {
		this.socket = socket;
		currentID = "NULL";
		admit = false;
		DAO.connectDB();
		System.out.println("현재 사용자 수 :" + ++currUser);
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			writePacket(Protocol.SC_REQ_LOGIN);
			boolean program_stop = false;

			while (true) {
				String packet = br.readLine();
				String packetArr[] = packet.split("/");
				String packetType = packetArr[0];

				switch (packetType) {
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
						try {
							MemberDAO mDao = new MemberDAO();
							MemberDTO mDto = mDao.getMember(login_id, login_passwd);

							if (mDto.getRole().equals("1"))
								writePacket(Protocol.SC_RES_LOGIN + "/1");
							else
								writePacket(Protocol.SC_RES_LOGIN + "/2");
							System.out.println("로그인 성공"); // 성공시 인터페이스 홈 접속

						} catch (Exception e) {
							writePacket(Protocol.SC_RES_LOGIN + "/3");
							System.out.println("로그인 실패"); // 실패시 메시지 창 출력 및 재입력 유도
						}
						System.out.println("로그인 처리 결과 전송");
						break;
					}
					case Protocol.CS_REQ_SIGNUP: {
						try {
							System.out.println("클라이언트가  회원가입 정보를 보냈습니다");
							String role = packetArr[1]; // 구분(사용자, 관리자)
							String signUp_id = packetArr[2]; // 아이디
							String signUp_password = packetArr[3]; // 암호
							String account = packetArr[4]; // 계좌 번호
							String name = packetArr[5]; // 이름
							String phone_number = packetArr[6]; // 연락처
							String birth = packetArr[7];
							String gender = packetArr[8]; // 성별

							MemberDAO signUpDAO = new MemberDAO();
							signUpDAO.addMember(new MemberDTO(signUp_id, role, signUp_password, account, name,
									phone_number, birth, gender));
							writePacket(Protocol.SC_RES_SIGNUP + "/1");
							break;
						} catch (Exception e) {
							e.printStackTrace();
							writePacket(Protocol.SC_RES_SIGNUP + "/2");
							break;
						}
					}
					case Protocol.CS_REQ_THEATER_ADD: {
						try {
							System.out.println("클라이언트가 영화관 등록 요청를 보냈습니다.");
							String name = packetArr[1]; // 영화관 이름
							String address = packetArr[2]; // 영화관 주소
							String screen = packetArr[3]; // 총 스크린 수
							String seat = packetArr[4]; // 총 좌석 수

							TheaterDAO theaterDAO = new TheaterDAO();
							TheaterDTO theaterDTO = new TheaterDTO(DTO.EMPTY_ID, name, address, Integer.valueOf(screen),
									Integer.valueOf(seat));

							theaterDAO.addTheater(theaterDTO);

							System.out.println("영화관 등록 성공");
							writePacket(Protocol.SC_RES_THEATER_ADD + "/1");
							break;
						} catch (Exception e) {
							e.printStackTrace();
							writePacket(Protocol.SC_RES_THEATER_ADD + "/2");
							break;
						}
					}

					case Protocol.CS_REQ_THEATER_CHANGE: {
						try {
							System.out.println("클라이언트가 영화관 수정 요청를 보냈습니다.");
							String id = packetArr[1]; // 영화관 ID
							String name = packetArr[2]; // 영화관 이름
							String address = packetArr[3]; // 영화관 주소
							String screen = packetArr[4]; // 총 스크린 수
							String seat = packetArr[5]; // 총 좌석 수

							TheaterDAO theaterDAO = new TheaterDAO();
							TheaterDTO theaterDTO = new TheaterDTO(id, name, address, Integer.valueOf(screen),
									Integer.valueOf(seat));

							theaterDAO.changeTheater(theaterDTO);

							System.out.println("영화관 수정 성공");
							writePacket(Protocol.SC_RES_THEATER_CHANGE + "/1");
							break;
						} catch (Exception e) {
							e.printStackTrace();
							writePacket(Protocol.SC_RES_THEATER_CHANGE + "/2");
							break;
						}
					}

					case Protocol.CS_REQ_THEATER_DELETE: {
						try {
							System.out.println("클라이언트가 영화관 삭제 요청을 보냈습니다.");
							String id = packetArr[1]; // 영화관 ID

							TheaterDAO theaterDAO = new TheaterDAO();
							theaterDAO.removeTheater(id);

							System.out.println("영화관 삭제 성공");
							writePacket(Protocol.SC_RES_THEATER_DELETE + "/1");
							break;
						} catch (Exception e) {
							e.printStackTrace();
							writePacket(Protocol.SC_RES_THEATER_DELETE + "/2");
							break;
						}
					}
				}// end switch

				if (program_stop)
					break;
			} // end while
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			currUser--;
			socket.close();
			System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "가 접속종료 하였습니다.");
			System.out.println("현재 사용자 수:" + currUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writePacket(String source) throws Exception {
		try {
			bw.write(source + "\n");
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}
}