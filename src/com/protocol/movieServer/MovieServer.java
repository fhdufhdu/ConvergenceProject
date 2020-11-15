package com.protocol.movieServer;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import com.protocol.movieTransaction.DBTransaction;;

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

		System.out.println("���� ����� �� :" + ++currUser);
	}

	@Override
	public void run() {
		// ����Ʈ �迭�� ������ ���̹Ƿ� ���� ��Ʈ�� ���� Input/OutputStream�� ����ص� ��
		OutputStream os = null;
		InputStream is = null;

		try {
			os = socket.getOutputStream();
			is = socket.getInputStream();

			Protocol protocol = new Protocol(Protocol.SC_REQ_SIGNUP);
			os.write(protocol.getPacket());

			boolean program_stop = false;

			while (true) {
				protocol = new Protocol(); // �� Protocol ��ü ����
				byte[] buf = protocol.getPacket(); // �⺻ �����ڷ� ������ ������ ����Ʈ �迭�� ���̰� 1000����Ʈ�� ������
				is.read(buf); // Ŭ���̾�Ʈ�κ��� �α������� (ID, PWD) ����
				int packetType = buf[0]; // ���� �����Ϳ��� ��Ŷ Ÿ�� ����
				protocol.setPacket(packetType, buf); // ��Ŷ Ÿ���� Protocol ��ü�� packet ��������� buf�� ����

				switch (packetType) {
				case Protocol.PT_EXIT: // ���α׷� ���� ����
					protocol = new Protocol(Protocol.PT_EXIT);
					os.write(protocol.getPacket());
					program_stop = true;
					System.out.println("��������");
					break;

				case Protocol.CS_REQ_LOGIN: // �α��� ���� ����
					System.out.println("Ŭ���̾�Ʈ��  �α��� ������ ���½��ϴ�");
					String login_id = protocol.getId();
					String login_password = protocol.getPassword();
					boolean check = false;

					protocol = new Protocol(Protocol.SC_RES_LOGIN);
					String login_query = "SELECT * FROM MEMBERS";
					ResultSet rs = movie_DB.getSelectResult(login_query);

					while (rs.next()) {
						String id = rs.getString("ID");
						if (login_id.equals(id)) { // DB Select Data ���
							String password = rs.getString("PASSWORD");
							check = true;
							if (login_password.equals(password)) { // �α��� ����
								protocol.setResult("1");
								memberConfirm = rs.getString("ROLE");
								userSet(protocol, login_id);
								System.out.println("�α��� ����"); // ������ �������̽� Ȩ ����
							} else { // ��ȣ Ʋ��
								protocol.setResult("2");
								System.out.println("��ȣ Ʋ��"); // ���н� �޽��� â ��� �� ���Է� ����
							}
							break;
						}
					}
					if (check == false) { // ���̵� ���� ����
						protocol.setResult("3");
						System.out.println("���̵� �������");
					}

					System.out.println("�α��� ó�� ��� ����");
					os.write(protocol.getPacket());
					break;

				case Protocol.CS_REQ_SIGNUP:
					System.out.println("Ŭ���̾�Ʈ��  ȸ������ ������ ���½��ϴ�");
					String data = protocol.getSignUpData();
					String dataList[] = data.split("/");
					String role = dataList[0]; // ����(�����, ������)
					String id = dataList[1]; // ���̵�
					String password = dataList[2]; // ��ȣ
					String name = dataList[3]; // �̸�
					String gender = dataList[4]; // ����
					String phone_number = dataList[5]; // ����ó
					Date birth = Date.valueOf(dataList[6]);
					String account = dataList[7]; // ���� ��ȣ
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
			System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "�� �������� �Ͽ����ϴ�.");
			System.out.println("���� ����� ��:" + currUser);
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
//	// �Ű������� �Է°��� �޾Ƽ� ��� Ȥ�� �Լ��ȿ��� �޴°�쵵 ����
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
			classify = "������";
		} else {
			classify = "�����";
		}
		System.out.println("[Login IP :" + socket.getInetAddress() + ":" + socket.getPort() + ", ���� ���� :" + classify
				+ ", ID:" + currentID + "]");

	}

	public Socket getSocket() {
		return socket;
	}
}