package com.protocol.movieClient;

import java.net.*;
import java.io.*;
import com.protocol.*;

public class MovieClient {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if (args.length < 2)
			System.out.println("사용법 : " + "java LoginClient 주소 포트번호");

		Socket socket = new Socket(args[0], 5000); // 소켓 생성과 동시에 ip, port 할당 후 서버와 연결 요청

		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();

		Protocol protocol = new Protocol();
		byte[] buf = protocol.getPacket();

		while (true) {
			is.read(buf);
			int packetType = buf[0];
			protocol.setPacket(packetType, buf);
			if (packetType == Protocol.PT_EXIT) {
				System.out.println("클라이언트 종료");
				break;
			}

			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			switch (packetType) {
			case Protocol.SC_RES_CONNECT:
				System.out.println("서버가 로그인 정보 요청");
				System.out.print("아이디 : ");
				String id = userIn.readLine();
				System.out.print("암호 : ");
				String pwd = userIn.readLine();
				// 로그인 정보 생성 및 패킷 전송
				protocol = new Protocol(Protocol.CS_REQ_LOGIN);
				protocol.setId(id);
				protocol.setPassword(pwd);
				System.out.println("로그인 정보 전송");
				os.write(protocol.getPacket());
				break;

			case Protocol.SC_RES_LOGIN:
				System.out.println("서버가 로그인 결과 전송.");
				String result = protocol.getResult();
				if (result.equals("1")) {
					System.out.println("로그인 성공");
				} else if (result.equals("2")) {
					System.out.println("암호 틀림");
				} else if (result.equals("3")) {
					System.out.println("아이디가 존재하지 않음");
				}
				protocol = new Protocol(Protocol.PT_EXIT);
				System.out.println("종료 패킷 전송");
				os.write(protocol.getPacket());
				break;

			case Protocol.SC_REQ_SIGNUP:
				System.out.println("서버가 회원가입 정보 요청");
				System.out.print("구분(사용자: 1, 관리자: 0)");
				String role = userIn.readLine();
				System.out.print("아이디: ");
				String sign_id = userIn.readLine();
				System.out.print("암호: ");
				String sign_password = userIn.readLine();
				System.out.print("이름: ");
				String name = userIn.readLine();
				System.out.print("성별(남/여): ");
				String gender = userIn.readLine();
				System.out.print("연락처: ");
				String phone_number = userIn.readLine();
				System.out.print("생년월일(xxxx-xx-xx): ");
				String birth = userIn.readLine();
				System.out.print("계좌번호: ");
				String account = userIn.readLine();

				protocol = new Protocol(Protocol.CS_REQ_SIGNUP);
				protocol.setSighUpData(role + "/" + sign_id + "/" + sign_password + "/" + name + "/" + gender + "/"
						+ phone_number + "/" + birth + "/" + account);
				System.out.println("회원가입 정보 전송");
				os.write(protocol.getPacket());
				break;

			case Protocol.SC_RES_SIGNUP:
				System.out.println("서버가 회원가입 결과 전송.");
				String signUp_result = protocol.getResult();
				if (signUp_result.equals("0")) {
					System.out.println("회원가입 성공");
				} else if (signUp_result.equals("1")) {
					System.out.println("회원가입 실패");
				}
				protocol = new Protocol(Protocol.PT_EXIT);
				System.out.println("종료 패킷 전송");
				os.write(protocol.getPacket());
				break;
			}
		}
		os.close();
		is.close();
		socket.close();
	}
}