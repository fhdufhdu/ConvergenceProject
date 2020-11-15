package com.protocol.movieServer;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import com.protocol.movieTransaction.DBTransaction;
import com.protocol.*;

public class MovieServer extends Thread {
	Socket socket;
	private MovieDB movie_DB;
	private String currentID;
	private boolean admit;
	private String memberConfirm;
	private static int currUser = 0;
	public static byte[] data;
	public static int cnt = 1;

	public MovieServer(Socket socket) throws ClassNotFoundException, SQLException {
		this.socket = socket;
		currentID = "NULL";
		admit = false;
		movie_DB = new MovieDB("jdbc:oracle:thin:@192.168.224.250:1521:xe", "MT", "1234");

		System.out.println("현재 사용자 수 :" + ++currUser);
	}

	@Override
	public void run() {
		// 바이트 배열로 전송할 것이므로 필터 스트림 없이 Input/OutputStream만 사용해도 됨
		OutputStream os = null;
		InputStream is = null;

		try {
			os = socket.getOutputStream();
			is = socket.getInputStream();

			Protocol protocol = new Protocol(Protocol.SC_REQ_SIGNUP);
			os.write(protocol.getPacket());

			boolean program_stop = false;

			while (true) {
				protocol = new Protocol(); // 새 Protocol 객체 생성
				byte[] buf = protocol.getPacket(); // 기본 생성자로 생성할 때에는 바이트 배열의 길이가 1000바이트로 지정됨
				is.read(buf); // 클라이언트로부터 로그인정보 (ID, PWD) 수신
				int packetType = buf[0]; // 수신 데이터에서 패킷 타입 얻음
				protocol.setPacket(packetType, buf); // 패킷 타입을 Protocol 객체의 packet 멤버변수에 buf를 복사

				switch (packetType) {
				case Protocol.PT_EXIT: // 프로그램 종료 수신
					protocol = new Protocol(Protocol.PT_EXIT);
					os.write(protocol.getPacket());
					program_stop = true;
					System.out.println("서버종료");
					break;

				case Protocol.CS_REQ_LOGIN: // 로그인 정보 수신
					System.out.println("클라이언트가  로그인 정보를 보냈습니다");
					String login_id = protocol.getId();
					String login_password = protocol.getPassword();
					boolean check = false;

					protocol = new Protocol(Protocol.SC_RES_LOGIN);
					String login_query = "SELECT * FROM MEMBERS";
					ResultSet rs = movie_DB.getSelectResult(login_query);

					while (rs.next()) {
						String id = rs.getString("ID");
						if (login_id.equals(id)) { // DB Select Data 사용
							String password = rs.getString("PASSWORD");
							check = true;
							if (login_password.equals(password)) { // 로그인 성공
								protocol.setResult("1");
								memberConfirm = rs.getString("ROLE");
								userSet(protocol, login_id);
								System.out.println("로그인 성공"); // 성공시 인터페이스 홈 접속
							} else { // 암호 틀림
								protocol.setResult("2");
								System.out.println("암호 틀림"); // 실패시 메시지 창 출력 및 재입력 유도
							}
							break;
						}
					}
					if (check == false) { // 아이디 존재 안함
						protocol.setResult("3");
						System.out.println("아이디 존재안함");
					}

					System.out.println("로그인 처리 결과 전송");
					os.write(protocol.getPacket());
					break;

				case Protocol.CS_REQ_SIGNUP:
					System.out.println("클라이언트가  회원가입 정보를 보냈습니다");
					String data = protocol.getSignUpData();
					String dataList[] = data.split("/");
					String role = dataList[0]; // 구분(사용자, 관리자)
					String id = dataList[1]; // 아이디
					String password = dataList[2]; // 암호
					String name = dataList[3]; // 이름
					String gender = dataList[4]; // 성별
					String phone_number = dataList[5]; // 연락처
					Date birth = Date.valueOf(dataList[6]);
					String account = dataList[7]; // 계좌 번호
					String signUp_query = "INSERT INTO MEMBERS VALUES ('1', '1', '1', '1', '1', '1', birth, '1')";
					boolean signUp_result = movie_DB.InsertDB(signUp_query);
					String test = "";
					if (signUp_result)
						test = "1";
					else
						test = "0";
					protocol = new Protocol(Protocol.SC_RES_SIGNUP);
					protocol.setResult(test);
					os.write(protocol.getPacket());
					break;

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
			is.close();
			os.close();
			socket.close();
			System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "가 접속종료 하였습니다.");
			System.out.println("현재 사용자 수:" + currUser);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public Transaction createTransaction(int input, Protocol protocol) {
//		Transaction tran = null;
//		switch (input) {
//
//		}
//		return tran;
//	}
//
//	// 매개변수로 입력값을 받아서 사용 혹은 함수안에서 받는경우도 가능
//	public Protocol performTransaction(int input, Protocol protocol) throws ClassNotFoundException, SQLException {
//		Transaction tran = null;
//		switch (input) {
//
//		}
//		return tran.execute();
//	}

	public void userSet(Protocol input, String id) {
		admit = true;
		currentID = id;
		String classify;
		if (memberConfirm.equals("1")) {
			classify = "관리자";
		} else {
			classify = "사용자";
		}
		System.out.println("[Login IP :" + socket.getInetAddress() + ":" + socket.getPort() + ", 접속 권한 :" + classify
				+ ", ID:" + currentID + "]");

	}

	public Socket getSocket() {
		return socket;
	}
}